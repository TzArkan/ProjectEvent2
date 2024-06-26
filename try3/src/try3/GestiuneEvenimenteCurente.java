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

public class GestiuneEvenimenteCurente extends JFrame {
 private JTable t;
 private DefaultTableModel tabel;
 private JButton b1,b2,b3,b4;
 private JFrame parentFrame;
 private JPanel dedicatButoane,capeteTabelPanel,checkboxPanel,checkboxPanel0,PanouTabelCheckboxCombinat;
 private JCheckBox selectAllCheckbox;
 private JFrame f;
 /* Metodă pentru ștergerea unei linii din fișierul text*/
 private void stergeLinie(int numarLinie) {
    String numeFisier = "dateEvenimente.txt";
    try (BufferedReader reader = new BufferedReader(new FileReader(numeFisier))) {
        List<String> lines = new ArrayList<>();
        String linie;
        int numarLinieCurent = 0;

       // Citirea primei linii pentru a obține numărul de evenimente
        String firstLine = reader.readLine();
        if (firstLine != null) {
            lines.add(firstLine); // adăugarea primei linii în listă
            numarLinieCurent++; // incrementarea contorului de linii
        }

        // Citirea restului liniilor
        while ((linie = reader.readLine()) != null) {
            if (numarLinieCurent != numarLinie + 1) { // +1 to account for the first line
                lines.add(linie);
            }
            numarLinieCurent++;
        }

       // Scrierea înapoi a conținutului actualizat
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(numeFisier))) {
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
/* Metodă pentru abonarea unui utilizator la un eveniment*/
public void abonareEveniment(int linie, String username) {
    String numeFisierEvenimente = "dateEvenimente.txt";  
    File numeFisierUtilizator = new File(username + "Evenimente.txt");  
    List<String> lines = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(numeFisierEvenimente))) {
        String line;
        int numarLinieCurent = 0;

        // Citirea tuturor liniilor și stocarea lor
        while ((line = reader.readLine()) != null) {
            lines.add(line);
            numarLinieCurent++;
        }
    } catch (IOException e) {
        e.printStackTrace();
        return;
    }

    // Verificarea dacă linia specificată există
    if (linie >= lines.size()) {
        JOptionPane.showMessageDialog(null, "Linia specificată nu există.", "Eroare", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Obținerea liniei care trebuie copiată din fișierul text
    String linieDeCopiat = lines.get(linie+1);

    // Adăugarea liniei la fișierul utilizatorului
    try (FileWriter fw = new FileWriter(numeFisierUtilizator, true);
         BufferedWriter writer = new BufferedWriter(fw)) {  // Open BufferedWriter in try-with-resources
        writer.write(linieDeCopiat + "$0" + "\n");
// Nu este nevoie să apelăm fw.flush(), BufferedWriter va face asta automat când este închis        JOptionPane.showMessageDialog(null, "Datele evenimentului au fost salvate cu succes.", "Succes", JOptionPane.INFORMATION_MESSAGE);
    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "A apărut o eroare la înregistrarea datelor.", "Eroare", JOptionPane.ERROR_MESSAGE);
    }
}
/* Metodă pentru copierea unei linii din fișierul text*/
public void copiazaLinie(int linie) {
    String numeFisier = "dateEvenimente.txt";
    List<String> lines = new ArrayList<>();
    int eventCount = 0;

    try (BufferedReader reader = new BufferedReader(new FileReader(numeFisier))) {
        String line;
        
          // Citirea primei linii pentru a obține numărul de evenimente
        String firstLine = reader.readLine();
        if (firstLine != null) {
            eventCount = Integer.parseInt(firstLine);
        }
        
        // Citirea restului liniilor și stocarea lor
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
    } catch (IOException e) {
        e.printStackTrace();
        return;
    }

    // Verificarea dacă linia specificată există
    if (linie >= lines.size()) {
        JOptionPane.showMessageDialog(null, "Linia specificată nu există.", "Eroare", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Incrementarea numărului de evenimente
    eventCount++;

    // Obținerea liniei care trebuie copiată și modificarea ei
    String linieDeCopiat = lines.get(linie);
    String[] segments = linieDeCopiat.split("\\$", 2);
    String modifiedLine = eventCount + "$" + (segments.length > 1 ? segments[1] : "");

    // Adăugarea liniei modificate la lista de linii
    lines.add(modifiedLine);

     // Scrierea înapoi a conținutului actualizat în fișier
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(numeFisier))) {
        writer.write(String.valueOf(eventCount));
        writer.newLine();
        for (String fileLine : lines) {
            writer.write(fileLine);
            writer.newLine();
        }
        JOptionPane.showMessageDialog(null, "Datele evenimentului au fost salvate cu succes.", "Succes", JOptionPane.INFORMATION_MESSAGE);
    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "A apărut o eroare la înregistrarea datelor.", "Eroare", JOptionPane.ERROR_MESSAGE);
    }
}

        

// Metodă pentru selecția și gestionarea evenimentelor
public void selectieEveniment(int alegere){
    int rowCount = tabel.getRowCount();
    if (alegere==1){
        for(int linie = rowCount - 1; linie >= 0; linie--) {
            JCheckBox checkBox = (JCheckBox)checkboxPanel.getComponent(linie);
            if(checkBox.isSelected()) { //verifica daca checkboxu de pe linie e selectat
                f = new ModificareEveniment(GestiuneEvenimenteCurente.this,linie);
                f.setLocation(0, 0);
                f.setSize(1300, 300);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setVisible(true);
                f=null;
            }
        }
    }
    
    else if (alegere==2)
    for(int linie = rowCount - 1; linie >= 0; linie--) {
        JCheckBox checkBox = (JCheckBox)checkboxPanel.getComponent(linie);
        if(checkBox.isSelected()) { //verifica daca checkboxu de pe linie e selectat

            
            copiazaLinie(linie);
            
        }
    }

    else if (alegere==3){
    for(int linie = rowCount - 1; linie >= 0; linie--) {
        JCheckBox checkBox = (JCheckBox)checkboxPanel.getComponent(linie);
        if(checkBox.isSelected()) { //verifica daca checkboxu de pe linie e selectat
            
            // sterge linia din fisierul text, corespunzatoare liniei din tabel
            stergeLinie(linie);
            // sterge checkboxu corespunzator din tabel
            checkboxPanel.remove(linie);
            // daca e selectat, sterge randul din tabel
            tabel.removeRow(linie);
        }
    }
}

    
    // reface partea de checkbox din tabel
    //checkboxPanel.revalidate();
  
  //checkboxPanel.repaint();
}

 public void incarcaDinFisier() {
    tabel.setRowCount(0); 
    try (BufferedReader reader = new BufferedReader(new FileReader("dateEvenimente.txt"))) {
        String line;
        line = reader.readLine();
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\\$");     // \\$ ca sa ia caracterul $
            
                tabel.addRow(parts); // adauga date in tabel
            
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

 public GestiuneEvenimenteCurente(JFrame parentFrame, String username, String rol){
    
    super("Gestionarea evenimentelor curente");
    setLayout(new BorderLayout());
    tabel=new DefaultTableModel(50,8);
    String[] NumeColoane = {"Cod eveniment","Categorie eveniment", "Nume eveniment", "Data eveniment", "Ora eveniment", "Locatie eveniment","Pret bilet eveniment","Numar bilete disponibile","Numar bilete vandute"};
    tabel.setColumnIdentifiers(NumeColoane);
    incarcaDinFisier();

    

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
    if(rol.equals("Admin")){
    b1=new JButton("Modifica eveniment");
    b1.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            selectieEveniment(1);
        }
    });
    dedicatButoane.add(b1);


    b2=new JButton("Copiaza eveniment");
    b2.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            selectieEveniment(2);
        }
    });
    dedicatButoane.add(b2);

    b3=new JButton("Sterge eveniment");
    b3.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            selectieEveniment(3);
        }
    });
    dedicatButoane.add(b3);
}
    if(rol.equals("Utilizator")){
        b1=new JButton("Aboneaza-ma la eveniment");
    b1.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            int rowCount = tabel.getRowCount();
            for(int linie = rowCount - 1; linie >= 0; linie--) {
                    JCheckBox checkBox = (JCheckBox)checkboxPanel.getComponent(linie);
                    if(checkBox.isSelected()) { //verifica daca checkboxu de pe linie e selectat
                        // sterge linia din fisierul text, corespunzatoare liniei din tabel
                        abonareEveniment(linie,username);
                    
                    }
            }
        }
        });
    dedicatButoane.add(b1);

}


    b4=new JButton("Inapoi");
    b4.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            parentFrame.setVisible(true);
            dispose();
        }
    });
    dedicatButoane.add(b4);
    this.add(dedicatButoane, BorderLayout.SOUTH);


}
}
