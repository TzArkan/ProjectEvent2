package try3;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;

public class SchimbareParola extends JFrame{
    private JPasswordField t1, t2, t3;
    private JButton b1, b2;
    private JFrame parentFrame;

    public SchimbareParola(JFrame parentFrame){
        
        super("Schimbare parola administrator"); // Setează titlul ferestrei
        JPanel p1 = new JPanel(new GridLayout(3, 2)); // Creează un JPanel cu GridLayout
        p1.add(new JLabel("Introduceti parola curenta")); // Adaugă eticheta pentru parola curentă
        t1 = new JPasswordField(20); // Creează câmpul de parolă pentru parola curentă
        p1.add(t1); // Adaugă câmpul de parolă în JPanel
        p1.add(new JLabel("Introduceti parola noua")); // Adaugă eticheta pentru parola nouă
        t2 = new JPasswordField(20); // Creează câmpul de parolă pentru parola nouă
        p1.add(t2); // Adaugă câmpul de parolă în JPanel
        p1.add(new JLabel("Reintroduceti noua parola")); // Adaugă eticheta pentru reintroducerea parolei noi
        t3 = new JPasswordField(20); // Creează câmpul de parolă pentru reintroducerea parolei noi
        p1.add(t3); // Adaugă câmpul de parolă în JPanel


        JPanel p2 = new JPanel(); // Creează un alt JPanel
        b1 = new JButton("Schimba"); // Creează butonul "Schimba"
        b1.addActionListener(new ActionListener() { // Adaugă un ActionListener la buton
            public void actionPerformed(ActionEvent e) { // Definirea acțiunii la apăsarea butonului
                schimba(); // Apelează metoda schimba
            }
        });
        p2.add(b1);

        b2=new JButton("Inapoi");
        b2.addActionListener((new ActionListener() {
            public void actionPerformed(ActionEvent e){
                parentFrame.setVisible(true);
                dispose();
            }
        }));
        p2.add(b2);
        
        this.add(p1);
        this.add(p2, BorderLayout.SOUTH);
        this.setSize(300,150);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        
    }

    private void schimba(){// Metodă privată pentru schimbarea parolei

        DateParola dp=new DateParola(new String(t1.getPassword()), new String(t2.getPassword()), new String(t3.getPassword()));// Creează un obiect DateParola cu parolele introduse
        
        if (dp.getParolaVeche().isEmpty() || dp.getParolaNoua().isEmpty() || dp.getParolaNReintrodusa().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Toate campurile trebuie completate cu informatiile corespunzatoare", "Error", JOptionPane.ERROR_MESSAGE);
            return;// Opresc execuția metodei
        }
        
        String fileName = "loginDataAdmin.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line=reader.readLine();// Citește prima linie din fișier
            String[] parts = line.split(",");// Desparte linia în părți
            String password = parts[1];// Extrage parola actuală

                if (dp.getParolaVeche().equals(password)==false) {// Verifică dacă parola veche introdusă nu coincide cu parola actuală
                    JOptionPane.showMessageDialog(this,"Parola curenta introdusa nu coincide cu parola contului", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else if(dp.getParolaNoua().equals(dp.getParolaNReintrodusa())==false)// Verifică dacă parolele noi nu coincid
                {
                    JOptionPane.showMessageDialog(this, "Parolele noi introduse trebuie sa coincida intre ele");// Afișează mesaj de eroare
                    return;// Opresc execuția metodei
                }
            String linieNoua = parts[0] + "," + dp.getParolaNoua() + ",Admin";
              
                         
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                writer.write(linieNoua);// Scrie noua linie în fișier
                writer.newLine();// Scrie un separator de linie
                JOptionPane.showMessageDialog(this, "Parola a fost actualizata cu succes.", "Succes", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {// Prinde excepțiile de tip IOException
                JOptionPane.showMessageDialog(this, "A aparut o eroare la inregistrarea datelor.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {// Prinde excepțiile de tip IOException
            JOptionPane.showMessageDialog(this, "A aparut o eroare la citirea datelor.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        t1.setText("");// Golește câmpul de parolă t1
        t2.setText("");// Golește câmpul de parolă t2
        t3.setText("");// Golește câmpul de parolă t3
                
        
    }
}

