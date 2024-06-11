/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package try3;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AdminOptiuni extends JFrame{// Declară clasa AdminOptiuni care extinde JFrame
    private JFrame fereastraVeche,f,i,j;
    private JButton b1, b2, b3, b4, b5, b6;
    private ControlButoane cb;
   

    public class ControlButoane implements ActionListener {// Definire clasă internă pentru controlul butoanelor
        private JFrame g,h;// Declară ferestrele

        public void actionPerformed(ActionEvent e) {// Definirea acțiunii la apăsarea butonului
            

            if (e.getSource() == b2) {// Dacă sursa evenimentului este butonul b2
                if (g == null) g = new GestionareCategoriiEvenimente (AdminOptiuni.this);
                int oX=350;// Setează coordonata X
                int oY=350;// Setează coordonata Y
                g.setSize(600,150);// Setează dimensiunea ferestrei
                g.setLocation(oX, oY);// Setează locația ferestrei
                g.setVisible(true);// Face fereastra vizibilă
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// Setează comportamentul la închiderea ferestrei
            }

            if (e.getSource() == b3) {
                if (h == null) h = new AdaugareEveniment(AdminOptiuni.this);
                int oX=350;
                int oY=350;
                h.setSize(800,200);
                h.setLocation(oX, oY);
                h.setVisible(true);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
            
 
        }
        
    }

    public AdminOptiuni(JFrame fereastraVeche, String username, String rol) {
        super(rol+": "+username);// Setează titlul ferestrei
        this.fereastraVeche = fereastraVeche;
        JPanel p1 = new JPanel(new GridLayout(5, 1));
        
        JPanel p2 = new JPanel();
        
        cb = new ControlButoane();
        b1 = new JButton("Gestionare evenimente curente");
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if (f == null) f = new GestiuneEvenimenteCurente(AdminOptiuni.this,username,rol);
                f.setLocation(0, 0);
                f.setSize(1300, 300);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setVisible(true);
                f=null;
            }
        });
        p1.add(b1);      

        
        b2 = new JButton("Adauga sau sterge categorii de evenimente");
        b2.addActionListener(cb);
        p1.add(b2);

        b3 = new JButton("Adaugare evenimente");
        b3.addActionListener(cb);
        p1.add(b3);

        b6 = new JButton("Vezi biletele cumparate");
        b6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if (j == null) j = new UtilizatorBilete(AdminOptiuni.this,username,rol);
                int oX=350;
                int oY=350;
                j.setSize(400,200);
                j.setLocation(oX, oY);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                j.setVisible(true);
                j=null;
            }
        });
        p1.add(b6);

        b4 = new JButton("Schimba parola");
        b4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                
            
                if (i == null) i = new SchimbareParola(AdminOptiuni.this);
                int oX=350;
                int oY=350;
                i.setSize(800,200);
                i.setLocation(oX, oY);
                i.setVisible(true);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            }
        });

        p1.add(b4);
        
        b5 = new JButton("Inapoi");
        b5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fereastraVeche.setVisible(true);
                dispose();
            }
        });
        p2.add(b5);
        

        this.add(p1);
        this.add(p2, BorderLayout.SOUTH);
    }
}
