package try3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class UtilizatorAbonamente extends JFrame {
 private JTable t;
 private DefaultTableModel tabel;
 private JButton b1,b2,b3;
 private JFrame parentFrame;
 private JPanel dedicatButoane,capeteTabelPanel,checkboxPanel,checkboxPanel0,PanouTabelCheckboxCombinat;
 private JCheckBox selectAllCheckbox;
 
 private void stergeLinie(int numarLinie, String username) {
    File fisier = new File(username+"Evenimente.txt");
    try (BufferedReader reader = new BufferedReader(new FileReader(fisier))) {
        List<String> lines = new ArrayList<>();
        String linie;
        int numarLinieCurent = 0;

        linie = reader.readLine();  // ca sa introduca prima linie cu numele categoriilor, fara sa verifice nimic
        lines.add(linie);
        linie = reader.readLine();
        lines.add(linie);
        while ((linie = reader.readLine()) != null) {
            if (numarLinieCurent != numarLinie) {
                lines.add(linie);
            }
            numarLinieCurent++;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fisier))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
// Metoda pentru achiziționarea unui bilet pentru un eveniment

public void cumparaBilet(int linie, String username) {
    File fisier1 = new File(username + "Evenimente.txt");// Inițializarea unui obiect File pentru fișierul de evenimente asociat utilizatorului
    
    String newLine;// Declarația unei variabile pentru stocarea noii linii de înlocuit în fișier
    int nrBileteDisponibile=0; // Inițializarea unei variabile pentru numărul de bilete disponibile
    int nrBileteVandute=0;// Inițializarea unei variabile pentru numărul de bilete vândute
    String codBilet=""; // Inițializarea unei variabile pentru codul biletului
    try (BufferedReader reader1 = new BufferedReader(new FileReader(fisier1))) {
        StringBuilder fileContent = new StringBuilder();
        String line;
        
        // Citirea primei linii (categorii) și adăugarea acesteia în StringBuilder
        line = reader1.readLine();
        fileContent.append(line).append("\n");
        int budget = Integer.parseInt(line.trim());// Parsarea bugetului utilizatorului

        // Citirea celei de-a doua linii (buget) și adăugarea acesteia în StringBuilder
        line = reader1.readLine();
        fileContent.append(line).append("\n");
        
        // Initialize line counter
        int currentLine = 1;
        
        while ((line = reader1.readLine()) != null) {// Citirea liniilor rămase din fișier
            currentLine++;
            
            if (currentLine == linie + 2) {  // Ajustarea pentru compensarea offsetului (primele două linii)
                String[] parts = line.split("\\$");// Separarea liniei în componente folosind caracterul $
                
                               // Actualizarea bugetului prin scăderea valorii din coloana 6
                int cost = Integer.parseInt(parts[6].trim());
                budget -= cost;
               // Actualizarea coloanelor 7 și 8
                nrBileteDisponibile = Integer.parseInt(parts[7].trim());
                nrBileteVandute = Integer.parseInt(parts[8].trim());
                int nrBileteAchizitionate = Integer.parseInt(parts[9].trim());
                if(nrBileteDisponibile<=0) throw new IOException("Bilete Indisponibile");
                if(budget<=0) throw new IOException("Buget insuficient");
                nrBileteDisponibile--;
                nrBileteVandute++;
                nrBileteAchizitionate++;
                codBilet=parts[0];
                salveazaBilet(username,nrBileteVandute,parts[0]);
                
                // Construirea noii linii
                newLine = parts[0] + "$" + parts[1] + "$" + parts[2] + "$" + parts[3] + "$" + parts[4] + "$" + parts[5] + "$" +  parts[6] + "$" + nrBileteDisponibile + "$" + nrBileteVandute + "$" + nrBileteAchizitionate;
                fileContent.append(newLine).append("\n");
            } else {
                fileContent.append(line).append("\n");
            }
        }
        
        fileContent.delete(0, fileContent.indexOf("\n") + 1);// Ștergerea primei linii din StringBuilder (bugetul)
        fileContent.insert(0, budget + "\n");// Actualizarea bugetului în șirul de caractere
        String finalContent = fileContent.toString();// Obținerea conținutului final al fișierului sub formă de șir de caractere

       // Scrierea conținutului actualizat înapoi în fișier
        try (BufferedWriter writer1 = new BufferedWriter(new FileWriter(fisier1))) {
            writer1.write(finalContent);
            JOptionPane.showMessageDialog(null, "Bilet cumparat", "Succes", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "A aparut o eroare la inregistrarea datelor.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "A aparut o eroare la cumparare", "Error", JOptionPane.ERROR_MESSAGE);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Formatul numerelor din fisier este incorect.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    File fisier2 = new File("dateEvenimente.txt");// Inițializarea unui obiect File pentru fișierul de dateEvenimente
try (BufferedReader reader2 = new BufferedReader(new FileReader(fisier2))) {
    StringBuilder fileContent = new StringBuilder();
    String line;
    
 // Citirea primei linii și adăugarea acesteia în StringBuilder
    line = reader2.readLine();
    fileContent.append(line).append("\n");
    
    // Initialize line counter
    while ((line = reader2.readLine()) != null) {// Citirea liniilor rămase din fișier
        String[] parts = line.split("\\$");
        if (codBilet.equals(parts[0])) {
             // Construirea noii linii
            newLine = parts[0] + "$" + parts[1] + "$" + parts[2] + "$" + parts[3] + "$" + parts[4] + "$" + parts[5] + "$" +  parts[6] + "$" + nrBileteDisponibile + "$" + nrBileteVandute;
            fileContent.append(newLine).append("\n");
        } else {
            fileContent.append(line).append("\n");
        }
    }

    String finalContent = fileContent.toString();

     // Scrierea conținutului actualizat înapoi în fișier
    try (BufferedWriter writer2 = new BufferedWriter(new FileWriter(fisier2))) { 
        writer2.write(finalContent);
        } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "A aparut o eroare la inregistrarea datelor.", "Error", JOptionPane.ERROR_MESSAGE);
    }
    
} catch (IOException e) {
    JOptionPane.showMessageDialog(null, "A aparut o eroare la cumparare", "Error", JOptionPane.ERROR_MESSAGE);
} catch (NumberFormatException e) {
    JOptionPane.showMessageDialog(null, "Formatul numerelor din fisier este incorect.", "Error", JOptionPane.ERROR_MESSAGE);
}
}
// Metoda pentru încărcarea datelor din fișier asociat utilizatorului în tabel

 public void incarcaDinFisier(String username) {
    File fisier = new File(username+"Evenimente.txt");
    tabel.setRowCount(0); 
    try (BufferedReader reader = new BufferedReader(new FileReader(fisier))) {
        String line;
        line = reader.readLine();   // ca sa sara peste prima linie, care e pentru categorii, citim prina linie inainte de while
        line = reader.readLine();   // sa sara si peste a doua, ca are banii acolo
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\\$");     // \\$ ca sa ia caracterul $
            
                tabel.addRow(parts); // adauga date in tabel
            
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

public static void salveazaBilet(String username, int nrBileteVandute, String codEv) {
    File fisier1 = new File("bileteEvenimente.txt");

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fisier1, true))) {
        String newLine = username + "$" + codEv + "$" + nrBileteVandute;
        writer.write(newLine);
        writer.newLine();
    } catch (IOException e) {
        System.err.println("Error writing to file: " + e.getMessage());
    }
}

 public UtilizatorAbonamente(JFrame parentFrame, String username, String rol){
    
    super("Gestionarea abonamentelor: "+username);
    setLayout(new BorderLayout());
    tabel=new DefaultTableModel(50,9);
    String[] NumeColoane = {"Cod eveniment","Categorie eveniment", "Nume eveniment", "Data eveniment", "Ora eveniment", "Locatie eveniment","Pret bilet eveniment","Numar bilete disponibile","Numar bilete vandute","Bilet achizitionat"};
    tabel.setColumnIdentifiers(NumeColoane);
    incarcaDinFisier(username);

    

    t=new JTable(tabel);
    t.setRowHeight(20);
    int inaltimeLinie = t.getRowHeight();
    checkboxPanel = new JPanel();
    checkboxPanel.setLayout(new GridBagLayout()); 
    
    GridBagConstraints gbc=new GridBagConstraints();
    gbc.anchor=GridBagConstraints.WEST;
    
    for (int i = 0; i < tabel.getRowCount(); i++) {
            JCheckBox checkBox = new JCheckBox();
            checkBox.setPreferredSize(new Dimension(20, inaltimeLinie));
            gbc.gridy=i;
            checkboxPanel.add(checkBox,gbc);
        }

    
    
    
    PanouTabelCheckboxCombinat= new JPanel();
    PanouTabelCheckboxCombinat.setLayout(new BorderLayout());

    capeteTabelPanel = new JPanel(new BorderLayout());
    selectAllCheckbox = new JCheckBox();
    capeteTabelPanel.add(selectAllCheckbox, BorderLayout.WEST);
    capeteTabelPanel.add(t.getTableHeader(), BorderLayout.CENTER);

    checkboxPanel0 = new JPanel();
    checkboxPanel0.add(checkboxPanel,BorderLayout.NORTH);    
    PanouTabelCheckboxCombinat.add(capeteTabelPanel,BorderLayout.NORTH);
    PanouTabelCheckboxCombinat.add(checkboxPanel0,BorderLayout.WEST);
    PanouTabelCheckboxCombinat.add(t,BorderLayout.CENTER);
    
    JScrollPane scroll=new JScrollPane(PanouTabelCheckboxCombinat);
    
    
    add(scroll,BorderLayout.CENTER);

    
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    selectAllCheckbox.addItemListener(new ItemListener() {
        
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                // selectarea tuturor checkboxurilor
                for (int i = 0; i < checkboxPanel.getComponentCount(); i++) {
                    JCheckBox checkBox = (JCheckBox) checkboxPanel.getComponent(i);
                    checkBox.setSelected(true);
                }
            } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                // deselectarea tuturor checkboxurilor
                for (int i = 0; i < checkboxPanel.getComponentCount(); i++) {
                    JCheckBox checkBox = (JCheckBox) checkboxPanel.getComponent(i);
                    checkBox.setSelected(false);
                }
            }
        }
    });
    
    
    dedicatButoane=new JPanel();

    b1=new JButton("Dezaboneaza-ma");
    b1.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            int rowCount = tabel.getRowCount();
            for(int linie = rowCount - 1; linie >= 0; linie--) {
                JCheckBox checkBox = (JCheckBox)checkboxPanel.getComponent(linie);
                if(checkBox.isSelected()) { //verifica daca checkboxu de pe linie e selectat
                    
                    // sterge linia din fisierul text, corespunzatoare liniei din tabel
                    stergeLinie(linie,username);
                    // sterge checkboxu corespunzator din tabel
                    checkboxPanel.remove(linie);
                    // daca e selectat, sterge randul din tabel
                    tabel.removeRow(linie);
                }
            }
        }
    });
    dedicatButoane.add(b1);

    b3=new JButton("Cumpara bilet");
    b3.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            int rowCount = tabel.getRowCount();
            for(int linie = rowCount - 1; linie >= 0; linie--) {
                    JCheckBox checkBox = (JCheckBox)checkboxPanel.getComponent(linie);
                    if(checkBox.isSelected()) { //verifica daca checkboxu de pe linie e selectat
                        cumparaBilet(linie,username);
                    
                    }
            }
        }
        });
    dedicatButoane.add(b3);

    b2=new JButton("Inapoi");
    b2.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            parentFrame.setVisible(true);
            dispose();
        }
    });
    dedicatButoane.add(b2);
    this.add(dedicatButoane, BorderLayout.SOUTH);


}
}
