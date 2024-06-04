package try3;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class GestionareCategoriiEvenimente extends JFrame {
    
    private JFrame fereastraVeche;
    private JLabel l1, l2;
    private JTextField t1;
    private JButton b1, b2, b3;
    private GridBagConstraints gbc;
    private JPanel p;
    private JComboBox<String> cb;

    public void selectieEveniment(int alegere) {
        if (alegere == 1) {
            stocheazaCategorie();
        } else if (alegere == 2) {
            stergeCategorie();
        }
    }

    public void incarcaComboBox() {
    String numeFisier = "categoriiEvenimente.txt";
    try (BufferedReader reader = new BufferedReader(new FileReader(numeFisier))) {
        String line;
        while ((line = reader.readLine()) != null) {
            cb.addItem(line);
            
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    public void adaugaConstrangeri(JComponent c, int linie, int col, int latime, int inaltime, int anchor, int fill, int spatiuOX, int spatiuOY) {
        gbc.gridx = col;
        gbc.gridy = linie;
        gbc.gridwidth = latime;
        gbc.gridheight = inaltime;
        gbc.anchor = anchor;
        double weightx = 0.0;
        double weighty = 0.0;
        if (latime > 1) weightx = 1.0;
        if (inaltime > 1) weighty = 1.0;
        switch (fill) {
            case GridBagConstraints.HORIZONTAL -> {
                gbc.weightx = weightx;
                gbc.weighty = 0.0;
            }
            case GridBagConstraints.VERTICAL -> {
                gbc.weighty = weighty;
                gbc.weightx = 0.0;
            }
            case GridBagConstraints.BOTH -> {
                gbc.weightx = weightx;
                gbc.weighty = weighty;
            }
            case GridBagConstraints.NONE -> {
                gbc.weightx = 0.0;
                gbc.weighty = 0.0;
            }
        }
        gbc.fill = fill;
        gbc.insets = new Insets(0, 2 * spatiuOX, 0, 2 * spatiuOY);
        p.add(c, gbc);
    }

    public GestionareCategoriiEvenimente(JFrame fereastraVecheFrame) {
        super("Gestionare categorii de evenimente");
        this.fereastraVeche = fereastraVecheFrame;
        
        l1 = new JLabel("Adauga eveniment");
        l2 = new JLabel("Sterge eveniment");
        t1 = new JTextField();
        b1 = new JButton("Adauga");
        b2 = new JButton("Sterge");
        b3 = new JButton("Inapoi");
        cb = new JComboBox();
        incarcaComboBox();
        t1.setPreferredSize(new Dimension(150, 30));
        cb.setPreferredSize(new Dimension(150, 30));

        p = new JPanel(new GridBagLayout());
        JPanel p2=new JPanel();
        gbc = new GridBagConstraints();

        adaugaConstrangeri(l1, 0, 0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, 10, 0);
        adaugaConstrangeri(t1, 0, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 10, 0);
        adaugaConstrangeri(b1, 0, 2, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, 10, 0);

        adaugaConstrangeri(l2, 1, 0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, 10, 0);
        adaugaConstrangeri(cb, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 10, 0);
        adaugaConstrangeri(b2, 1, 2, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, 10, 0);

        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectieEveniment(1);
            }
        });

        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectieEveniment(2);
            }
        });

        b3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fereastraVeche.setVisible(true);
                dispose();
            }
        });
        p2.add(b3);

        this.add(p, BorderLayout.CENTER);
        this.add(p2, BorderLayout.SOUTH);
        this.pack(); // Adjust the frame size based on its content
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); // Center the frame
    }

    private void stocheazaCategorie() {
        String adCat = t1.getText();

        if (adCat.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Introduceti categoria dorita pentru adaugare", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String fileName = "categoriiEvenimente.txt";
        try (FileWriter fw = new FileWriter(fileName, true)) {
            fw.write(adCat + "\n");
            fw.flush(); // Ensure data is written immediately
            JOptionPane.showMessageDialog(this, "Categorie adaugata cu succes.", "Succes", JOptionPane.INFORMATION_MESSAGE);
            cb.addItem(adCat);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "A aparut o eroare la inregistrarea categoriei.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void stergeCategorie() {
    int numarLinieCBSelectat=cb.getSelectedIndex();
    String numeFisier = "categoriiEvenimente.txt";
    if(numarLinieCBSelectat!=0){
    try (BufferedReader reader = new BufferedReader(new FileReader(numeFisier))) {
        List<String> lines = new ArrayList<>();
        String linie;
        int numarLinieCurent = 0;
        
        while ((linie = reader.readLine()) != null) {
            if (numarLinieCurent != numarLinieCBSelectat) {
                lines.add(linie);
            }
            numarLinieCurent++;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(numeFisier))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            JOptionPane.showMessageDialog(this, "Categoria a fost stearsa cu succes.", "Succes", JOptionPane.INFORMATION_MESSAGE);
            cb.removeItemAt(numarLinieCBSelectat);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}
}