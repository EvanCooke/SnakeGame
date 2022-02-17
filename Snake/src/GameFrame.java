import javax.swing.*;

public class GameFrame extends JFrame {

    GameFrame(){
        //this.add(new GamePanel()); // add new GamePanel to GameFrame
        this.add(new MainMenuPanel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack(); // size frame around all elements inside of it
        this.setVisible(true);
        this.setLocationRelativeTo(null); // set frame to center of computer screen
    }

}


