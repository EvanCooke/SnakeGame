import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 800;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;

    static int DELAY = 75; // higher number = slower game and vice versa

    final int[] x = new int[GAME_UNITS]; // holds all x coordinates of snake
    final int[] y = new int[GAME_UNITS]; // holds all y coordinates of snake

    int bodyParts = 6; // starting body count for snake
    int applesEaten = 0;
    int appleX, appleY; // coordinates of apple
    char direction = 'R'; // start snake going right
    boolean running = false;
    int gameMode = 0; // 1 = classic, 2 = speed, 3 = double

    Timer timer;
    Random random;

    JButton classicButton, speedButton, doubleButton;


    GamePanel(){

        classicButton = new JButton();
        classicButton.setText("Classic Snake");
        classicButton.setFocusable(false);
        classicButton.addActionListener(this);
        this.add(classicButton);

        speedButton = new JButton();
        speedButton.setText("Speed Snake");
        speedButton.setFocusable(false);
        speedButton.addActionListener(this);
        this.add(speedButton);

        doubleButton = new JButton();
        doubleButton.setText("Double Snake");
        doubleButton.setFocusable(false);
        doubleButton.addActionListener(this);
        this.add(doubleButton);

        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
    }

    public void startGame(){
        spawnApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){

        if(running) {
            // only works for square to horizontally stretched rectangles.
            // Need two separate loops (i think?) if screen can be resized to either
            // horizontally stretched or vertically stretched
            for (int i = 0; i < SCREEN_WIDTH / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT); // draw grid column lines
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE); // draw grid row lines
            }

            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }

            // display score
            if(gameMode != 0) {
                g.setColor(Color.red);
                g.setFont(new Font("Arial", Font.BOLD, 40));
                FontMetrics metrics = getFontMetrics(g.getFont());
                g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
            }
        }
        else{
            gameOver(g);
        }
    }

    public void spawnApple(){ // newApple in tutorial
        appleX = random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
        appleY = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;

        // prevent apple from spawning on snake
        for(int i = 0; i < bodyParts; i++){
            if(x[i] == appleX && y[i] == appleY){
                spawnApple();
            }
        }

    }

    public void move(){
        for(int i = bodyParts; i > 0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch(direction){
            case 'U': // move up
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D': // move down
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L': // move left
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R': // move right
                x[0] = x[0] + UNIT_SIZE;
                break;
        }

    }

    public void checkApple(){
        if((x[0] == appleX) && (y[0] == appleY)){ // apple is eaten
            bodyParts += 3; // += 3 as opposed to ++ causes bug where snake appears in top right corner for a moment after eating apple
            applesEaten++;
            spawnApple();
            if(gameMode == 2){
                timer.setDelay(DELAY -= 5);
            }
        }
    }

    public void checkCollisions(){

        // checks if head collides with body
        for(int i = bodyParts; i > 0; i--){
            if((x[0] == x[i]) && (y[0] == y[i])){
                running = false; // game over
            }
        }

        // check if head touches left border
        if(x[0] < 0){
            running = false;
        }

        // check if head touches right border
        if(x[0] > SCREEN_WIDTH - UNIT_SIZE){
            running = false;
        }

        // check if head touches top border
        if(y[0] < 0){
            running = false;
        }

        // check if head touches bottom border
        if(y[0] > SCREEN_HEIGHT - UNIT_SIZE){
            running = false;
        }

        if(!running){
            timer.stop();
        }
    }

    public void gameOver(Graphics g){

        // Game over text
        if(gameMode != 0) {
            g.setColor(Color.red);
            g.setFont(new Font("Arial", Font.BOLD, 75));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Game Over!", (SCREEN_WIDTH - metrics.stringWidth("Game Over!")) / 2, SCREEN_HEIGHT / 2); // place string in center of screen

            // display score
            g.setColor(Color.red);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            FontMetrics metrics2 = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics2.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkApple();
            checkCollisions();
        }else{
            if(e.getSource() == classicButton){
                gameMode = 1;
            }
            if(e.getSource() == speedButton){
                gameMode = 2;
            }
            if(e.getSource() == doubleButton){
                gameMode = 3;

            }

            this.remove(classicButton);
            this.remove(speedButton);
            this.remove(doubleButton);
            startGame();
        }
        repaint();
    }


    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U'){
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
