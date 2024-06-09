package try3;

import javax.swing.*;
import java.awt.*;
import java.text.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class ModificareEveniment extends JFrame implements IGesEveniment {
    private JLabel[] l;// Etichete pentru câmpurile de text
    private JLabel l0,pr;// Etichete suplimentare
    private JTextField[] t;// Câmpuri de text pentru datele evenimentului
    private JButton b1,b2;// Butoane pentru acțiuni
    private GridBagConstraints gbc;// Constrângeri pentru layout-ul GridBag
    private JPanel p;// Panou principal
    private JComboBox cb;// ComboBox pentru tipul evenimentului
    private AscultatorCampData acd;// Ascultător pentru câmpul de dată
    private AscultatorCampOra aco;// Ascultător pentru câmpul de oră
    
// Constructor pentru clasa ModificareEveniment
    public ModificareEveniment(JFrame parentFrame, int linie){
        super("Modificare Eveniment");
         // Etichete pentru câmpurile de text
        String[] etichete={"Numele evenimentului","Data evenimentului: zz/ll/yyyy", "Ora de incepere a evenimentului: xx:xx","Locatia evenimentului", "Pretul unui bilet","Numar de bilete disponibile"};
        l=new JLabel[6];
        t=new JTextField[6];
        Dimension textFieldDimension = new Dimension(200, 25);
           // Inițializare etichete și câmpuri de text    
        for(int i=0; i<6; i++) {
            l[i]=new JLabel(etichete[i]);
            t[i]=new JTextField();               
        }
         // Încarcă datele din fișier pentru linia specificată
        incarcaDateDinFisier(linie);
        // Setează dimensiunea preferată pentru câmpurile de text
        for(int i=0; i<6; i++) {
            t[i].setPreferredSize(new Dimension(100,100)); 
            t[i].setMinimumSize(new Dimension(100,25));
        }            
        
         // Adaugă ascultători pentru câmpurile de dată și oră
        acd=new AscultatorCampData();
        t[1].addFocusListener(acd);
        aco=new AscultatorCampOra();
        t[2].addFocusListener(aco);
        l0=new JLabel("Tipul evenimentului");
        pr=new JLabel("Lei");        
        cb=new JComboBox();
 // Încarcă opțiunile din ComboBox
        incarcaComboBox();
        
        GridBagLayout gbl=new GridBagLayout();
        gbc=new GridBagConstraints();
        p=new JPanel(gbl);
        // Adaugă componentele la panou folosind constrângeri
        adaugaConstrangeri(l0,0, 0, 1, 1, GridBagConstraints.WEST, 0, 10,0); 
        adaugaConstrangeri(cb,0, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,10,0);
        adaugaConstrangeri(l[0],1, 0, 1, 1, GridBagConstraints.WEST, 0, 10,0);
        adaugaConstrangeri(t[0],1, 1, 1, 1, GridBagConstraints.CENTER, 0, 10, 0);
        adaugaConstrangeri(l[1],1, 2, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, 10,0);
        adaugaConstrangeri(t[1],1, 3, 1, 1, GridBagConstraints.CENTER, 0, 10, 0);
        adaugaConstrangeri(l[2],2, 2, 1, 1, GridBagConstraints.WEST, 0,10,0);
        adaugaConstrangeri(t[2],2, 3, 1, 1, GridBagConstraints.CENTER, 0,10,0);
        adaugaConstrangeri(l[3],2, 0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH,10,0);
        adaugaConstrangeri(t[3],2, 1, 1, 1, GridBagConstraints.CENTER, 0,10,0);
        adaugaConstrangeri(l[4],3, 0, 1, 1, GridBagConstraints.WEST, 0,10,0);
        adaugaConstrangeri(t[4],3, 1, 1, 1, GridBagConstraints.CENTER, 0,10,0);
        adaugaConstrangeri(l[5],4, 0, 1, 1, GridBagConstraints.WEST, 0,10,0);
        adaugaConstrangeri(t[5],4, 1, 1, 1, GridBagConstraints.CENTER, 0,10,0);
        adaugaConstrangeri(pr,3, 2, 1, 1, GridBagConstraints.WEST, 0,10,0);
        //adaugaConstrangeri(l[5],3, 2, 1, 1, GridBagConstraints.WEST, 0,10,0);
        //adaugaConstrangeri(t[5],3, 3, 1, 1, GridBagConstraints.CENTER, 0,10,0);
        //adaugaConstrangeri(l[6],4, 2, 1, 1, GridBagConstraints.EAST, GridBagConstraints.BOTH,10,0);
        //adaugaConstrangeri(t[6],4, 3, 1, 1, GridBagConstraints.CENTER, 0,10,0);
        
        b1=new JButton("Modificati evenimentul");
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stocareDate(linie);
            }
        });
        
        b2 = new JButton("Inapoi");
        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parentFrame.setVisible(true);
                dispose();
            }
        });
        
        add(p);
        
        p=new JPanel(gbl);
        adaugaConstrangeri(b1, 0,0,1,1, GridBagConstraints.CENTER, GridBagConstraints.NONE,20,0);
        adaugaConstrangeri(b2, 0,1,1,1, GridBagConstraints.CENTER, GridBagConstraints.NONE,20,0);
          
        add(p, BorderLayout.SOUTH);
        
    }
// Încarcă datele evenimentului din fișier pentru linia specificată
    public void incarcaDateDinFisier(int linie) {
            
        try (BufferedReader reader = new BufferedReader(new FileReader("dateEvenimente.txt"))) {
            String line;
            for(int i=0; i<linie;i++) reader.readLine();
            line = reader.readLine();                
                String[] parts = line.split("\\$");     // \\$ ca sa ia caracterul $
                
                for(int i=0; i<6; i++) {
                    t[i]= new JTextField(parts[i+2]);
                }
                
                
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Ascultător pentru câmpul de oră
    private class AscultatorCampOra implements FocusListener{
        private Dialog o;
        private String ora;
        public void focusGained(FocusEvent e){}
        public void focusLost(FocusEvent e){
          ora=t[2].getText();
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
          try {
            LocalTime.parse(ora, formatter);
            } catch (DateTimeParseException z) {
                o=new JDialog(ModificareEveniment.this, "Eroare");
                o.add(new JLabel("   Introduceti ora dupa formatul: HH:mm"));
                o.setBounds(200,200, 270, 100);
                o.setVisible(true);
            }//catch
        }//metoda focusLost
        
       }
// Ascultător pentru câmpul de dată
       private class AscultatorCampData implements FocusListener {
        private Dialog d;
        private String data;

        public void focusGained(FocusEvent e) {}

        public void focusLost(FocusEvent e) {
            data = t[1].getText();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false); 
            try {
                sdf.parse(data); 
            } catch (ParseException nf) {
                d = new JDialog((JFrame) SwingUtilities.getWindowAncestor(t[1]), "Eroare");
                d.add(new JLabel("   Introduceti data dupa formatul: dd/MM/yyyy"));
                d.setBounds(250, 250, 270, 100);
                d.setVisible(true);
            }
        }
    }
 // Verifică dacă ora evenimentului este validă
        public boolean oraEvenimentValida(String ora) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                try {

                LocalTime.parse(ora, formatter);
                return true; 
                } catch (DateTimeParseException e) {
                return false; 
        }
    }
    // Încarcă opțiunile din ComboBox dintr-un fișier
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
    
// Verifică dacă data evenimentului este validă
    public boolean dataEvenimentValida(String dataEveniment) {
       
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        sdf.setLenient(false);
    
        try {
            
            sdf.parse(dataEveniment);
            return true;
        } catch (ParseException e) {
           
            return false;
        }
    }
    // Verifică dacă prețul evenimentului este valid
    public boolean pretEvenimentValid(String s) {
        try {
            Integer.parseInt(s);
            return true; 
        } catch (NumberFormatException e) {
            return false; 
        }
    }

    public boolean numarBilete(String s) {
        try {
            Integer.parseInt(s);
            return true; 
        } catch (NumberFormatException e) {
            return false; 
        }
    }

    public void stocareDate(int linie) {
        DateEveniment de = new DateEveniment(t[0].getText(), t[1].getText(), t[2].getText(), t[3].getText(), t[4].getText(), t[5].getText());
    
        if (!numarBilete(de.getNrBilete()) || cb.getSelectedIndex() == 0 || de.getNume().isEmpty() || !dataEvenimentValida(de.getData()) || !oraEvenimentValida(de.getOra()) || de.getLocatie().isEmpty() || !pretEvenimentValid(de.getPret())) {
            JOptionPane.showMessageDialog(this, "Introduceti toate datele corespunzatoare evenimentului", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        String fileName = "dateEvenimente.txt";
        File file = new File(fileName);
        int eventNumber;
        List<String> fileContents = new ArrayList<>();
    
        try {
            if (!file.exists()) {
                file.createNewFile();
                eventNumber = 1;
            } else {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String firstLine = br.readLine();
                if (firstLine == null) {
                    eventNumber = 1;
                } else {
                    eventNumber = Integer.parseInt(firstLine) + 1;
                    fileContents.add(firstLine);
                }
                
                String line;
                while ((line = br.readLine()) != null) {
                    fileContents.add(line);
                }
                br.close();
            }
    
            // Incrementați numărul evenimentului și actualizați prima linie
            if (fileContents.isEmpty()) {
                fileContents.add(String.valueOf(eventNumber));
            } else {
                fileContents.set(0, String.valueOf(eventNumber));
            }
    
            // Creați datele noului eveniment
            String eventData = eventNumber + "$" + cb.getSelectedItem() + "$" + de.getNume() + "$" + de.getData() + "$" + de.getOra() + "$" + de.getLocatie() + "$" + de.getPret() + "$" + de.getNrBilete() + "$" + "0";
    
            // Înlocuiți linia veche cu datele noului eveniment dacă indexul liniei este valid
            if (linie >= 1 && linie < fileContents.size()) {
                fileContents.set(linie, eventData);
            } else {
                // Dacă linia specificată nu există, adăugați datele noului eveniment
                fileContents.add(eventData);
            }
    
            // Scrieți înapoi în fișier
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for (String fileLine : fileContents) {
                bw.write(fileLine);
                bw.newLine();
            }
            bw.close();
    
            JOptionPane.showMessageDialog(this, "Datele evenimentului au fost salvate cu succes.", "Succes", JOptionPane.INFORMATION_MESSAGE);
    
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "A aparut o eroare la inregistrarea datelor.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
        // Adaugă constrângeri la componentă pentru layout-ul GridBag

    public void adaugaConstrangeri(JComponent c,int linie, int col, int latime, int inaltime, int anchor, int fill, int spatiuOX, int spatiuOY) {
        gbc.gridx = col;
        gbc.gridy = linie;
        gbc.gridwidth= latime;
        gbc.gridheight = inaltime;
        gbc.anchor = anchor;
        double weightx = 0.0;
        double weighty = 0.0;
        if(latime > 1)weightx = 1.0;
        if(inaltime > 1)weighty = 1.0;
        switch(fill){
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
        gbc.insets=new Insets(0,2*spatiuOX,0,2*spatiuOY);
        p.add(c, gbc);
}

    
}
