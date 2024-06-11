package try3;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;

public class AdaugaFonduri extends JFrame {// Declară clasa AdaugaFonduri care extinde JFrame
    private JButton b1, b2;// Declară butoanele
    private JTextField t1;// Declară câmpul text
    private JFrame parentFrame;// Declară referința la fereastra părinte

    public AdaugaFonduri(JFrame parentFrame,String username) {// Constructorul clasei
        super("Adauga fonduri: "+username);// Setează titlul ferestrei
        this.parentFrame = parentFrame;// Inițializează referința la fereastra părinte

        JPanel p1 = new JPanel(new GridLayout(1, 1, 10, 10));// Creează un JPanel cu GridLayout
        p1.add(new JLabel("Adauga fonduri"));// Adaugă o etichetă în JPanel
        t1 = new JTextField(20);// Creează câmpul text cu lungimea 20
        p1.add(t1);// Adaugă câmpul text în JPanel

        JPanel p2 = new JPanel();// Creează un alt JPanel
        b1 = new JButton("Adauga");// Creează butonul "Adauga"
        b1.addActionListener(new ActionListener() {// Adaugă un ActionListener la buton
            @Override
            public void actionPerformed(ActionEvent e) {// Definirea acțiunii la apăsarea butonului
                adaugaBani(username,t1);// Apelează metoda adaugaBani
            }
        });
        p2.add(b1);// Adaugă butonul în JPanel

        b2 = new JButton("Inapoi");// Creează butonul "Inapoi"
        b2.addActionListener(new ActionListener() {// Adaugă un ActionListener la buton
            @Override
            public void actionPerformed(ActionEvent e) {// Definirea acțiunii la apăsarea butonului
                parentFrame.setVisible(true);// Setează fereastra părinte vizibilă
                dispose();// Închide fereastra curentă
            }
        });
        p2.add(b2);// Adaugă butonul în JPanel
        this.add(p1);// Adaugă primul JPanel în JFrame
        this.add(p2, BorderLayout.SOUTH);// Adaugă al doilea JPanel în JFrame la sud

        this.setSize(300, 150);// Setează dimensiunea ferestrei
        this.setLocationRelativeTo(null);// Centrează fereastra
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// Setează comportamentul la închiderea ferestrei
    }

    private void adaugaBani(String username, JTextField t1) {
        File fisier = new File(username + "Evenimente.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(fisier))) {// Creează un BufferedReader pentru citirea fișierului
        
            String firstLine = reader.readLine();// Citește prima linie din fișier
            if (firstLine == null) {
               // Verifică dacă fișierul este gol sau nu conține date
                // Gestionează acest caz după cum este necesar
                return;
            }
            
           
            String fieldValue = t1.getText();// Parsează valoarea din câmpul text
            if (!sumaValida(fieldValue)) {
               // Verifică dacă valoarea din câmpul text este un întreg valid
               
                return;// Gestionează acest caz după cum este necesar
            }
            
            int incrementValue = Integer.parseInt(fieldValue);// Converteste valoarea din câmpul text în int
            int currentValue = Integer.parseInt(firstLine);// Converteste prima linie din fișier în int
            
            
           // Incrementează numărul inițial cu valoarea parsată
            int newValue = currentValue + incrementValue;
            
           
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fisier))) {// Creează un BufferedWriter pentru scrierea în fișier
                writer.write(String.valueOf(newValue));// Scrie noua valoare incrementată în fișier
                writer.newLine(); // Write a new line separator
               // Scrie un separator de linie nouă
                JOptionPane.showMessageDialog(this, "Fonduri adaugate cu succes.", "Succes", JOptionPane.INFORMATION_MESSAGE);
    
                String line;// Declară o variabilă pentru linia curentă
                while ((line = reader.readLine()) != null) {// Citește liniile rămase din fișierul original
                    writer.write(line);// Scrie linia în fișierul nou
                    writer.newLine(); // Scrie un separator de linie nouă
                }
            } catch (IOException e) {// Prinde excepțiile de tip IOException
                e.printStackTrace();// Printează stiva de apeluri pentru depanare
                 // Gestionează excepția IO
            }
        } catch (IOException e) {// Prinde excepțiile de tip IOException
            e.printStackTrace();// Printează stiva de apeluri pentru depanare
           // Gestionează excepția IO
        }
    }

    public boolean sumaValida(String s) { // Metodă pentru verificarea validității sumei

        try {
            Integer.parseInt(s);// Încearcă să parseze șirul ca int
            return true;// Dacă reușește, întoarce true
        } catch (NumberFormatException e) {// Prinde excepțiile de tip NumberFormatException
            return false;// Dacă parsează eșuează, întoarce false
        }
    }
}