package Board;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.* ;

public class Pop extends JDialog implements ActionListener {
    public Popup p ;
    JFrame f;
    JButton b1 ;
    JButton b2 ;
    JButton b3 ;
    JButton b4 ;
    public String choice;
    public Pop(){
        this.setModal(true);
        f = new JFrame ("popup") ;
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);
        JLabel l = new JLabel ("Promotion");
        f.setSize (400,400) ;
        PopupFactory pf = new PopupFactory() ;
        JPanel p2 = new JPanel() ;
        p2.setBackground(Color.green);
        p = pf.getPopup(f , p2 , 180,100) ;
        JButton b1 = new JButton("Queen") ;
        JButton b2 = new JButton ("Rook") ;
        JButton b3 = new JButton ("Knight") ;
        JButton b4 = new JButton ("Bishop") ;
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        JPanel p1 = new JPanel() ;
        p1.add(b1) ;
        p1.add(b2) ;
        p1.add (b3) ;
        p1.add (b4) ;
        f.add(p1);
        f.show() ;
        this.choice = "";
    }

    public void actionPerformed (ActionEvent e){
        p.show() ;
        System.out.println(((JButton) e.getSource()).getText());
        this.choice = ((JButton) e.getSource()).getText();
        f.setVisible(false);
    }

    public int getChoice() {
        switch (this.choice) {
            case "Knight":
                return 3;
            case "Rook":
                return 4;
            case "Bishop":
                return 2;
            default:
                return 5;
        }
    }

}
