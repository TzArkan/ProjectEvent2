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

public class Inregistrare extends JFrame {
    private JButton b1, b2;
    private JTextField t1, t3;
    private JPasswordField t2;
    private JFrame parentFrame;

    public Inregistrare(JFrame parentFrame) {
        super("Inregistrare utilizator");
        this.parentFrame = parentFrame;

        JPanel p1 = new JPanel(new GridLayout(3, 2, 10, 10));
        p1.add(new JLabel("Nume utilizator"));
        t1 = new JTextField(20);
        p1.add(t1);

        p1.add(new JLabel("Adresa de email"));
        t3 = new JTextField(20);
        p1.add(t3);

        p1.add(new JLabel("Parola"));
        t2 = new JPasswordField(15);
        p1.add(t2);
        this.add(p1);

        JPanel p2 = new JPanel();
        b1 = new JButton("OK");
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                storeUserData();
            }
        });
        p2.add(b1);

        b2 = new JButton("Inapoi");
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.setVisible(true);
                dispose();
            }
        });
        p2.add(b2);

        this.add(p2, BorderLayout.SOUTH);

        this.setSize(300, 150);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
 // Stocheaza datele utilizatorului
    private void storeUserData() {
        DateUtilizator du=new DateUtilizator(t1.getText(),t3.getText(),new String(t2.getPassword()));
        // Verifica daca campurile sunt goale
        if (du.getNume().isEmpty() || du.getParola().isEmpty() || du.getEmail().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Introduceti numele contului, adresa de email si parola.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String conturiUtilizatoriDetalii = "loginDataUtilizator.txt";
        
        if (verificareUtilizatorUnic(du.getNume(),conturiUtilizatoriDetalii)==false) {
            return;
        }
// Inregistreaza utilizatorul in fisier
        try (FileWriter fw = new FileWriter(conturiUtilizatoriDetalii, true)) {
            fw.write(du.getNume() + "," + du.getParola() + "," + du.getEmail() + ",Utilizator\n");
            fw.flush(); // Ensure data is written immediately
            File numeFisierUtilizator = new File(du.getNume()+"Evenimente.txt");
        
        if (!numeFisierUtilizator.exists()) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(numeFisierUtilizator))) {
            writer.write("10000\n\n");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "A apărut o eroare la crearea fișierului.", "Eroare", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }
    }
            JOptionPane.showMessageDialog(this, "Utilizator inregistrat cu succes.", "Succes", JOptionPane.INFORMATION_MESSAGE);
            t1.setText("");
            t3.setText("");
            t2.setText("");
            parentFrame.setVisible(true);
            dispose();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "A aparut o eroare la inregistrarea datelor.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
 // Verifica daca utilizatorul este unic
    public boolean verificareUtilizatorUnic(String username, String numeFisier) {
        try (BufferedReader reader = new BufferedReader(new FileReader(numeFisier))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(","); // \\$ ca sa ia caracterul $
                if (parts[0].equals(username)) {
                    JOptionPane.showMessageDialog(this, "Acest nume a fost deja ales de catre altcineva. Alegeti alt nume", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}