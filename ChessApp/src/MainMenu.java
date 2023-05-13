package src;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame implements ActionListener {
    public JLabel user1;
    public JLabel user2 ;
    boolean multi;
    JTextField Username;
    JTextField Username2;
    JButton submit;
    JButton single;
    JButton multiple;
    JFrame input;
    MainMenu(){
        user1 = new JLabel();
        user2 = new JLabel();

        user1.setOpaque(true);
        user1.setVisible(true);
        user1.setSize(200,20);
        this.setSize(1920, 1080);
        this.setResizable(true);
        this.setLayout(null);
        this.setTitle("2D Game of Chess");
        this.getContentPane().setBackground(new Color(49,46,43));
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);




        single = new JButton("Single Player");
        single.setBackground(new Color(255, 193, 94));
        single.setVisible(true);
        single.addActionListener(this);
        single.setBounds(600, 300, 200, 100 );
        single.setFocusable(false);
        this.add(single);


        multiple = new JButton("Multiple Player");
        multiple.setBackground(new Color(255, 193, 94));
        multiple.setVisible(true);
        multiple.addActionListener(this);
        multiple.setBounds(600, 500, 200, 100 );
        multiple.setFocusable(false);
        this.add(multiple);

    }
    public void Data(int players){
        input = new JFrame();
        input.setLayout(new GridLayout(2,3));
        input.getContentPane().setBackground(new Color(255, 193, 94));
        input.setBounds(400,200,200,100);
        input.setTitle("2D Game of Chess");
        input.setResizable(false);
        input.setVisible(true);
        input.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        if(players ==1){
            submit = new JButton();
            submit.setFocusable(false);
            submit.setText("Enter your name");
            submit.addActionListener(this);

            Username = new JTextField();
            Username.setPreferredSize(new Dimension(200,50));
            Username.setBackground(new Color(255, 193, 94));
            Username.setBounds(200,100,200,50);

            input.add(Username);
            input.add(submit);
            input.pack();
        }
        else{
            submit = new JButton();
            submit.setFocusable(false);
            submit.setText("Enter your name");
            submit.addActionListener(this);

            Username = new JTextField();
            JLabel white = new JLabel("White");
            Username.setName("White");
            Username.setPreferredSize(new Dimension(200,50));
            Username.setBackground(new Color(255, 193, 94));
            Username.setBounds(200,100,200,50);

            Username2 = new JTextField();
            JLabel black = new JLabel("Black");
            Username2.setPreferredSize(new Dimension(200,50));
            Username2.setBackground(new Color(255, 193, 94));
            Username2.setBounds(200,100,200,50);

            input.add(Username);
            input.add(Username2);
            input.add(submit);
            input.add(white);
            input.add(black);
            input.pack();

        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == single) {
            Data(1);
        }
        if(e.getSource() == multiple){
            multi = true;
            Data(2);
        }
        if(e.getSource() == submit  && !multi){
            this.user1.setText(Username.getText());
            System.out.println(this.user1.getText());
            System.out.println("Welcome " + user1);
            new Game_Board_Singleplayer("Computer", user1.getText());
            input.dispose();
            this.dispose();
        }
        if(e.getSource() == submit  && multi){
            this.user1.setText(Username.getText());
            this.user2.setText(Username2.getText());
            System.out.println("Welcome " + user1 + " and " + user2);
            new Game_Board_Multiplayer(user1.getText(), user2.getText());
            input.dispose();
            this.dispose();
        }

    }
}
