import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuPanel extends JPanel implements ActionListener{
    static final int SCREEN_WIDTH = 800;
    static final int SCREEN_HEIGHT = 600;

    JButton classicButton, speedButton, doubleButton;

    MainMenuPanel(){


        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);

        classicButton = new JButton();
        classicButton.setBounds(200,100,100,50);
        classicButton.setText("Classic Snake");
        classicButton.setFocusable(false);
        classicButton.addActionListener(this);
        this.add(classicButton);

        speedButton = new JButton();
        speedButton.setBounds(200,300,100,50);
        speedButton.setText("Speed Snake");
        speedButton.setFocusable(false);
        speedButton.addActionListener(this);
        this.add(speedButton);

        doubleButton = new JButton();
        doubleButton.setBounds(200,500,100,50);
        doubleButton.setText("Double Snake");
        doubleButton.setFocusable(false);
        doubleButton.addActionListener(this);
        this.add(doubleButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) { // checks for events
        if(e.getSource() == classicButton){
            //GameFrame.setContentPane("SnakeGame");
            MainMenuPanel
        }
    }

    private void classicButtonActionPerformed(java.awt.event.ActionEvent evt){

    }
}
