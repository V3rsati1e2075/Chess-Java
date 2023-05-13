package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class AddPieces implements ActionListener {

    JButton button;
    JButton button2;
    public AddPieces(){
        JFrame frame = new JFrame();
        frame.setSize(1920, 1080);
        frame.setResizable(true);
        frame.setLayout(null);
        frame.setTitle("2D Game of Chess");
        frame.getContentPane().setBackground(new Color(189,136,51));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel sample = new JLabel("go");

        ImageIcon bb = new ImageIcon("src/PieceImages/bb.png", "bb");
        this.button = new JButton(bb);
        this.button2 = new JButton(bb);
        button2.addActionListener(this);
        button.addActionListener(this);
        button2.setVisible(true);
        button.setVisible(true);
        button.setBounds(0,0,200,300);
        button2.setBounds(300,400,200,300);
        frame.add(button2);
        frame.add(button);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button){
            System.out.println(button.getIcon());
        }
        if(e.getSource() == button2){
            System.out.println(button2.getIcon());
        }
    }
}
