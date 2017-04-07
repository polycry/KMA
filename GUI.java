/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

/**
 *
 * @author Jonas-Breitenberger
 */
public class GUI extends JFrame implements ActionListener {

    //private JFrame mainFrame = new JFrame();
    private JPanel mainPanel = new JPanel();
    private JTextArea actualProgram = new JTextArea("Aktuelles Programm");
    private JButton p1 = new JButton("P1");
    private JButton onoff = new JButton("Power");
    private JLabel background = new JLabel();
    private Geschirrspueler gs;
    private JProgressBar pb = new JProgressBar(0, 100);
    
    public GUI(Geschirrspueler gs) {
        this.gs = gs;
        
        this.setSize(520, 530);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        this.setTitle("Geschirrspueler Simulation - by Jonas & Felix");
        
        mainPanel.setLayout(null);
        
        background.setSize(500, 500);
        
        Icon bg = new ImageIcon("back.png");
        background.setIcon(bg);
        
        actualProgram.setBounds(295, 45, 130, 45);
        actualProgram.setFont(new Font("Courier New", Font.ITALIC, 12));
        actualProgram.setVisible(false);
        
        onoff.setBounds(3, 15, 100, 25);
        onoff.setOpaque(false);
        onoff.setContentAreaFilled(false);
        onoff.setBorderPainted(false);
        onoff.addActionListener(this);
        
        p1.setBounds(55, 15, 75, 25);
        p1.setOpaque(false);
        p1.setContentAreaFilled(false);
        p1.setBorderPainted(false);
        p1.addActionListener(this);
        
        pb.setBounds(120, 50, 150, 25);
        pb.setValue(0);
        pb.setStringPainted(true);
        pb.setVisible(false);
        
        this.add(mainPanel);
        mainPanel.add(actualProgram);
        mainPanel.add(p1);
        mainPanel.add(onoff);
        mainPanel.add(pb);
        mainPanel.add(background);
        this.setVisible(true);
        
    }
    
    public JTextArea getActualProgram() {
        return actualProgram;
    }
    
    public void setActualProgram(String text) {
        actualProgram.setText(text);
    }
    
    public JProgressBar getPb() {
        return pb;
    }
    
    public void setPb(int pb) {
        this.pb.setValue(pb);
        
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        
        if (ae.getSource() == p1) {
            if (gs.isOn()) {
                System.out.println("Test");
                gs.setProgram(1);
                gs.startProgram();
            }
            
        }
        
        if (ae.getSource() == onoff) {
            gs.changeOn();
        }
        
    }
    
}
