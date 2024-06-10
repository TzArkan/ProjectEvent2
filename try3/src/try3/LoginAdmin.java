/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package try3;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;

public class LoginAdmin extends JFrame implements ILogin {
    private JTextField t1;// Câmp pentru introducerea numelui de utilizator
    private JPasswordField t2;// Câmp pentru introducerea parolei
    private JButton b1, b2;// Butoane pentru autentificare și întoarcere
    private JFrame parentFrame;// Referință la fereastra părinte
    
// Constructor pentru inițializarea ferestrei de login pentru administrator
    public LoginAdmin(JFrame parentFrame) {
        super("Login Administrator");
        this.parentFrame = parentFrame;        
        JPanel p1 = new JPanel(new GridLayout(2, 2, 10, 10));
        
        p1.add(new JLabel("Nume administrator"));// Label pentru numele administratorului
        t1 = new JTextField(20);// Câmp de text pentru numele administratorului
        p1.add(t1);
        p1.add(new JLabel("Parola"));// Label pentru parolă
        t2 = new JPasswordField(15);// Câmp de text pentru parolă
        p1.add(t2);
        this.add(p1); // Adăugarea panoului în fereastra principală

        JPanel p2 = new JPanel();// Panou pentru butoane
        b1 = new JButton("OK");// Buton pentru autentificare
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();// Apelarea metodei de login la apăsarea butonului
            }
        });
        p2.add(b1);

        b2 = new JButton("Inapoi");// Buton pentru întoarcere
        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parentFrame.setVisible(true);// Afișează fereastra părinte
                dispose();// Închide fereastra curentă
            }
        });
        p2.add(b2);
        this.add(p2, BorderLayout.SOUTH);

        

        this.setSize(300, 150);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    // Metodă pentru autentificarea administratorului
    private void login() {
        String enteredUsername = t1.getText();
        String enteredPassword = new String(t2.getPassword());
        
        String rol;
        // Verifică dacă câmpurile sunt goale
        if (enteredUsername.isEmpty() || enteredPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Introduceti numele contului si parola.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String fileName = "loginDataAdmin.txt"; // Fișierul care conține datele de login
        boolean found = false;
// Citirea datelor din fișier
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();// Citește prima linie
                String[] parts = line.split(",");// Împarte linia în părți
                String username = parts[0];// Numele de utilizator din fișier
                String password = parts[1];// Parola din fișier
                rol= parts[2];// Rolul din fișier
 // Verifică dacă numele de utilizator și parola introduse sunt corecte
                if (enteredUsername.equals(username) && enteredPassword.equals(password)) {
                    found = true;
                }

            if (found) {
                                // Afișează mesaj de succes
                
                JOptionPane.showMessageDialog(this, "Autentificare reusita.", "Succes", JOptionPane.INFORMATION_MESSAGE);
                // Creează fereastra cu opțiuni pentru administrator
                AdminOptiuni adOpt= new AdminOptiuni(LoginAdmin.this,enteredUsername,rol);
                t1.setText("");// Resetează câmpul de text pentru numele de utilizator
                t2.setText(""); // Resetează câmpul de text pentru parolă
                adOpt.setLocationRelativeTo(null);// Centrează noua fereastră pe ecran
                adOpt.setVisible(true);// Afișează noua fereastră
                adOpt.setSize(300,200);// Dimensiunea noii ferestre
                setVisible(false);// Ascunde fereastra curentă
                dispose();// Închide fereastra curentă
            } else {
                // Afișează mesaj de eroare
                JOptionPane.showMessageDialog(this, "Nume utilizator sau parola incorecta.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
             // Afișează mesaj de eroare în cazul unei probleme la citirea fișierului
            JOptionPane.showMessageDialog(this, "A aparut o eroare la citirea datelor.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
