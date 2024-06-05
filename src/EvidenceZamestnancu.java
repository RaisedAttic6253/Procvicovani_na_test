import javax.swing.*;
import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EvidenceZamestnancu extends JFrame {
    private JPanel HlavniPanel;
    private JTable table1;
    private JTextField jmeno;
    private JTextField prijmeni;
    private JTextField datum;
    private JCheckBox pojisteni;
    private JRadioButton rb1;
    private JRadioButton rb2;
    private JRadioButton rb3;
    private JButton save;
    private JButton btnNext;
    private JButton btnPrev;
    private JButton saveT;
    private File selectedFile;
    private int index = 0;

    private List<Zamestnanec> zamestnanci = new ArrayList<>();
    private Tabulka tabulka;

    public EvidenceZamestnancu() {
        add(HlavniPanel);
        setTitle("Evidence zaměstnanců");
        setBounds(500, 200, 500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tabulka = new Tabulka(zamestnanci);
        table1.setModel(tabulka);
        table1.getColumnModel().getColumn(1).setMinWidth(100);
        saveT.addActionListener(e -> pridejZamestnance());
        btnNext.addActionListener(e -> nactiDalsi());
        btnPrev.addActionListener(e -> nactiPredchozi());
        save.addActionListener(e -> ulozSoubor());

        ButtonGroup group = new ButtonGroup();
        group.add(rb1);
        group.add(rb2);
        group.add(rb3);
        initMenu();



    }
    private void pridejZamestnance() {
        // Získání hodnot z komponent
        String jmenoZamestnance = jmeno.getText();
        String prijmeniZamestnance = prijmeni.getText();
        LocalDate datumNarozeniZamestnance = LocalDate.parse(datum.getText());
        boolean pojisteniZamestnance = pojisteni.isSelected();
        int hodnoceniZamestnance;
        if (rb1.isSelected()) {
            hodnoceniZamestnance = 1;
        } else if (rb2.isSelected()) {
            hodnoceniZamestnance = 2;
        } else {
            hodnoceniZamestnance = 3;
        }

        // Vytvoření nového zaměstnance
        Zamestnanec novyZamestnanec = new Zamestnanec(jmenoZamestnance, prijmeniZamestnance, pojisteniZamestnance, datumNarozeniZamestnance, hodnoceniZamestnance);

        // Přidání zaměstnance do seznamu a tabulky

        tabulka.pridejZamestnance(novyZamestnanec);
    }
    public void display(){
        Zamestnanec aktualniZamestnanec = zamestnanci.get(index);
        jmeno.setText(aktualniZamestnanec.getJmeno());
        prijmeni.setText(aktualniZamestnanec.getPrijmeni());
        datum.setText(aktualniZamestnanec.getNarozeni().toString());
        pojisteni.setSelected(aktualniZamestnanec.isPojisteni());
        rb1.setSelected(aktualniZamestnanec.getHodnoceni() == 1);
        rb2.setSelected(aktualniZamestnanec.getHodnoceni() == 2);
        rb3.setSelected(aktualniZamestnanec.getHodnoceni() == 3);

    }
    public void nactiDalsi(){
        if(index < zamestnanci.size()-1){
            index++;
            display();
        }
    }

    public void nactiPredchozi(){
        if(index > 0){
            index--;
            display();
        }
    }

    public void ulozSoubor(){
        JFileChooser fc = new JFileChooser(".");
        int reslult = fc.showOpenDialog(this);
        if(reslult == JFileChooser.APPROVE_OPTION){
            selectedFile = fc.getSelectedFile();
            ulozDoSouboru(); // Zavoláme metodu bez argumentu
        }
    }
    public void ulozDoSouboru() {
        zamestnanci.set(index, getInfo());
        try (PrintWriter wr = new PrintWriter(new BufferedWriter(new FileWriter(selectedFile)))) {
            for (Zamestnanec zamestnanec : zamestnanci) {
                wr.println(zamestnanec.getJmeno() + ";" + zamestnanec.getPrijmeni() + ";" + zamestnanec.getNarozeni() + ";" + (zamestnanec.isPojisteni() ? "ano" : "ne") + ";" + zamestnanec.getHodnoceni());


            }
            JOptionPane.showMessageDialog(null, "Soubor byl uložen", "Informace", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Při ukládání do souboru došlo k chybě: " + e.getMessage(), "Chyba", JOptionPane.ERROR_MESSAGE);
        }
    }
    public Zamestnanec getInfo(){
        return new Zamestnanec(jmeno.getText(), (prijmeni.getText()), pojisteni.isSelected(), LocalDate.parse(datum.getText()), rb1.isSelected() ? 1 : rb2.isSelected() ? 2 : 3);
    }



    public void initMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Soubor");
        JMenuItem menuItem = new JMenuItem("Načti ze souboru");
        menuItem.addActionListener(e -> nactiZeSouboru());
        menu.add(menuItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }
    public void nactiZeSouboru() {
        JFileChooser fc = new JFileChooser(".");
        int result = fc.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fc.getSelectedFile();
            nactiSoubor(selectedFile);
            display();
        }
    }

    public void nactiSoubor(File selectedFile){
        zamestnanci.clear(); // Vyprázdníme seznam zaměstnanců
        try(Scanner sc = new Scanner(new BufferedReader(new FileReader(selectedFile)))){
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                String[] parts = line.split(";");
                String jmeno = parts[0];
                String prijmeni = parts[1];
                LocalDate narozeni = LocalDate.parse(parts[2]);
                boolean pojisteni = parts[3].equals("ano");
                int hodnoceni = Integer.parseInt(parts[4]);
                Zamestnanec zamestnanec = new Zamestnanec(jmeno, prijmeni, pojisteni, narozeni, hodnoceni);
                zamestnanci.add(zamestnanec);
                switch (hodnoceni) {
                    case 1:
                        rb1.setSelected(true);
                        break;
                    case 2:
                        rb2.setSelected(true);
                        break;
                    case 3:
                        rb3.setSelected(true);
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Soubor nebyl nalezen" + e.getLocalizedMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Chyba při čtení souboru" + e.getLocalizedMessage());
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EvidenceZamestnancu().setVisible(true));
    }
}


