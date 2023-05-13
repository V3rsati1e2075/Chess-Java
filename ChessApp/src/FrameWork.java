package src;

import Game.GameEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameWork extends JFrame implements ActionListener {
    JButton start;
    JButton exit;

    //MainMenu menuscreen;


    public FrameWork() {

        ImageIcon board = new ImageIcon( "src/PieceImages/HomePic.png" );
        JLabel board1 = new JLabel(board);
        JLabel title = new JLabel();
        title.setText("CHESS GAME");

        title.setVisible(true);
        title.setFont(new Font("Calibri", 10, 150));
        title.setBackground(new Color(255,255,255));
        title.setForeground(Color.white);

        title.setBounds(600, 50, 1000, 200);
        board1.setBounds(800, 200, 600, 600);

        this.setSize(1920, 1080);
        this.setResizable(true);
        this.setLayout(null);
        this.setTitle("2D Game of Chess");
        this.getContentPane().setBackground(new Color(49,46,43));
        this.setVisible(true);
        this.add(board1);
        this.add(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        exit = new JButton();
        exit.setBackground(new Color(255,193,94));
        exit.setText("Exit to Desktop");
        exit.setBounds(200, 600, 250,50);
        exit.addActionListener(this);
        this.add(exit);

        start = new JButton();
        start.setText("Start Game");
        start.setBackground(new Color(255,193,94));
        start.setBounds(200, 400, 250, 50);
        start.addActionListener(this);
        this.add(start);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == start) {
            this.dispose();
            new MainMenu();

            System.out.println("Game has started");
        }
        if (e.getSource() == exit) {
            dispose();
        }
    }
}
