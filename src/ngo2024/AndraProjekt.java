/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

package ngo2024;

import java.util.ArrayList;
import java.util.HashMap;
import oru.inf.InfDB;
import oru.inf.InfException;
import ngo2024.Admin.ProjektHallbarhet;
import ngo2024.Admin.ProjektPartner;
import ngo2024.Admin.ProjektHandlaggare;

/**
 *
 * @author linneagottling
 */
public class AndraProjekt extends javax.swing.JFrame {

    private InfDB idb;
    private String pid;
    private String epost;
    
    private HashMap<String, String> landLista;
    
    /** Creates new form AndraProjekt */
    public AndraProjekt(InfDB idb, String pid, String epost) {
        this.idb = idb;
        this.pid = pid;
        this.epost = epost;
        initComponents();
        this.setLocationRelativeTo(null);
        landLista = new HashMap<>();
        gomBad();
        fyllTabellAndra();
    }
    
private void gomBad(){
    //gömmer alla fel medelandena, används innan ett specefikt fel ska upplysas.
    lblFelNamn.setVisible(false);
    lblFelBeskrivning.setVisible(false);
    lblFelKostnad.setVisible(false);
    lblFelSlutdatum.setVisible(false);
    lblFelStartdatum.setVisible(false);
    lblFelmeddelande.setVisible(false);
    lblMeddelande.setVisible(false);
}     

private String getLid(String pid){
    String lid = " ";
    
    try{
     
        String sqlFraga = "SELECT lid FROM land "
                        + "JOIN projekt ON lid = land "
                        + "WHERE pid = " + pid;
        
        lid = idb.fetchSingle(sqlFraga);
    }
    catch(InfException ex) {
        System.out.println(ex.getMessage());
    }
    return lid;
}    
   
private void fyllCbLand(){
       
        cbxLand.removeAllItems();
        
        String sqlLand = "SELECT namn FROM land ";
        
        try{

        ArrayList<String> allaLanderLista =  idb.fetchColumn(sqlLand);
                                             
            for(String landNamn : allaLanderLista){
               String sqlLid = "SELECT lid FROM land WHERE "
                       + "namn = '" + landNamn + "'";
               String i = idb.fetchSingle(sqlLid);
               cbxLand.addItem(landNamn);
               landLista.put(i, landNamn);
            }
                      
            
        } catch (InfException ex){
            System.out.println(ex.getMessage());
        }
}

private void fyllCbPrio(){
    
    cbxPrioritet.removeAllItems();
            
    cbxPrioritet.addItem("Hög");
    cbxPrioritet.addItem("Låg");
    cbxPrioritet.addItem("Medel");
}

private void fyllCbStatus(){
    cbxStatus.removeAllItems();
    
            
    cbxStatus.addItem("Planerat");
    cbxStatus.addItem("Pågående");
    cbxStatus.addItem("Avslutat");
    cbxStatus.addItem("Pausad");
}

private void fyllTabellAndra(){
    fyllCbLand();
    fyllCbPrio();
    fyllCbStatus();

    String pLid = getLid(pid);
        
        String namn=" ";
        String beskrivning=" ";
        String startdatum=" ";
        String slutdatum=" ";
        String kostnad=" ";
        String nLand=" ";
        String nPrio = " ";
        String nStatus = " ";
        String nLid = " ";

        try{
            namn = idb.fetchSingle("SELECT projektnamn FROM projekt WHERE pid = " + pid);
            beskrivning = idb.fetchSingle("SELECT beskrivning FROM projekt WHERE pid = " + pid);
            startdatum = idb.fetchSingle("SELECT startdatum FROM projekt WHERE pid = " + pid);
            slutdatum = idb.fetchSingle("SELECT slutdatum FROM projekt WHERE pid = " + pid);
            kostnad = idb.fetchSingle("SELECT kostnad FROM projekt WHERE pid = " + pid);

            
            //hämtar ut nuvarande 
            nPrio = idb.fetchSingle("SELECT prioritet FROM projekt WHERE pid = " + pid);
            nStatus = idb.fetchSingle("SELECT status FROM projekt WHERE pid = " + pid);
            nLand = idb.fetchSingle("SELECT namn FROM land WHERE lid = "
                                + "(SELECT land FROM projekt WHERE pid = " + pid + ")");
            
            nLid = pLid;
            
        }
        catch(InfException ex){
            System.out.println(ex.getMessage());
        } 
            tfProjektnamn.setText(namn);
            tfBeskrivning.setText(beskrivning);
            tfStartdatum.setText(startdatum);
            tfSlutdatum.setText(slutdatum);
            tfKostnad.setText(kostnad);
            tfNLand.setText(nLand);
            lblPnamn.setText(namn);
            
            //skriver ut nuvarande
            tfNprio.setText(nPrio);
            tfNstatus.setText(nStatus);
            lblNLid.setText(nLid);
            lblPid.setText(pid);
            
            //sätter rätt värden i checkboxen
            cbxPrioritet.setSelectedItem(nPrio);
            cbxLand.setSelectedItem(nLand);
            cbxStatus.setSelectedItem(nStatus);
                    
}

private boolean namnKontroll(){
        Validering valid = new Validering(idb);
        String namn = tfProjektnamn.getText();
        // samma som alla andra kontroller men använder förnamns valideringen då de gör samma sak
    if (valid.checkMeningOSiffra(namn)&& valid.checkStorlek(255, namn)) {
            lblFelNamn.setVisible(false);
            return true;
    } else {
        lblFelNamn.setVisible(false);
            return false;
    }
}

private boolean inteSammaNamnKontroll(){
        boolean inteSamma=true;
        boolean sInteSamma=false;
        boolean retur=true;
        ArrayList<String> namnLista=new ArrayList<>();
        String sqlFraga="SELECT projektnamn FROM projekt";
        try{
            namnLista=idb.fetchColumn(sqlFraga);
        } catch(InfException ex){
            System.out.println(ex.getMessage());
        }
        String selectedProjekt = lblPnamn.getText();
        selectedProjekt = selectedProjekt.trim();
                
       
        if(tfProjektnamn.getText().equalsIgnoreCase(selectedProjekt)){
            sInteSamma=true;
        }
        
        
        for(String namn:namnLista){
            namn = namn.trim();
            if(namn.equalsIgnoreCase(tfProjektnamn.getText())){
                
                inteSamma=false;
            }
        }
        if(sInteSamma==true){
            retur=sInteSamma;
            lblFelNamn.setVisible(false);

        }
        else if (inteSamma==false){
            lblFelNamn.setVisible(true);
            retur=inteSamma;
        } 
        
        return retur;
}

private boolean beskrivningKontroll(){
        Validering valid = new Validering(idb);
        String besk = tfBeskrivning.getText();
        // samma som alla andra kontroller men använder förnamns valideringen då de gör samma sak
    if (valid.checkBeskrivning(besk)&& valid.checkStorlek(255, besk)) {
            lblFelBeskrivning.setVisible(false);
            return true;
    } else {
            lblFelBeskrivning.setVisible(true);
            return false;
    }
    }

private boolean stDatumKontroll(){
Validering valid = new Validering(idb); 
    
    // Hämta text från textfältet
    String datum = tfStartdatum.getText(); 
    
    // Kontrollera om e-postadressen är giltig
    if (valid.checkDatum(datum)) {
        lblFelStartdatum.setVisible(false); // Göm varning
        return true;
    } else {
        lblFelStartdatum.setVisible(true); // Visa varning
        return false;
    }
}

private boolean slDatumKontroll(){
Validering valid = new Validering(idb); 
    
    // Hämta text från textfältet
    String datum = tfSlutdatum.getText(); 
    
    // Kontrollera om e-postadressen är giltig
    if (valid.checkDatum(datum)) {
        lblFelSlutdatum.setVisible(false); // Göm varning
        return true;
    } else {
        lblFelSlutdatum.setVisible(true); // Visa varning
        return false;
    }
}

private boolean kostnadKontroll(){
    Validering enValidering = new Validering(idb);
    String kostnad = tfKostnad.getText();
    if(enValidering.checkKostnad(kostnad)){
        lblFelKostnad.setVisible(false);
        return true;
    } else {
        lblFelKostnad.setVisible(true);
        return false;

    }
}

private boolean mellanDatum(){
        Validering valid = new Validering(idb);
        String start = tfStartdatum.getText();
        String slut=tfSlutdatum.getText();
        // kontrollerar namn format
    if (valid.checkDatumSkillnad(start, slut)) {
            lblFelStartdatum.setVisible(false);
            lblFelSlutdatum.setVisible(false);
            return true;
    } else {
            lblFelStartdatum.setVisible(true);
            lblFelSlutdatum.setVisible(true);
            return false;
    }
}

private boolean totalKontroll(){
    boolean ok;
    
    if(namnKontroll() && inteSammaNamnKontroll() && beskrivningKontroll() && stDatumKontroll()
            && slDatumKontroll() && kostnadKontroll() && mellanDatum()){
        ok = true;
        gomBad();
        lblFelmeddelande.setVisible(false);
    } else {
        lblMeddelande.setVisible(false);
        ok = false;
    }
    return ok;
}

private String getSelectedLid(){
        String selectedLand = (String) cbxLand.getSelectedItem();
        String lid = " ";
        for(String id : landLista.keySet()){
            String namn = landLista.get(id);
            if(selectedLand != null && selectedLand.equals(namn)){
                lid = id;               
            }
        }
        return lid;
}

private void visaLid(){
   
        lblLid.setText(getSelectedLid());
}
    
private void gorAndring(){
        
        String namnS = tfProjektnamn.getText();
        String beskrivningS = tfBeskrivning.getText();
        String startdatumS = tfStartdatum.getText();
        String slutdatumS = tfSlutdatum.getText();
        String kostnadS = tfKostnad.getText();
        
        String landIdS = getSelectedLid();
        String statusS = (String) cbxStatus.getSelectedItem();
        String prioritetS = (String) cbxPrioritet.getSelectedItem();            
        
        String sqlUpdate="UPDATE projekt SET projektnamn='" + namnS + "', beskrivning= '" 
                + beskrivningS + "', kostnad = " + kostnadS
                + ", startdatum= '" + startdatumS + "', slutdatum= '" + slutdatumS + "', land = " 
                + landIdS + ", status = '" 
                + statusS + "', prioritet = '" + prioritetS 
                + "' WHERE pid = " + pid;
        try{
            idb.update(sqlUpdate);
            System.out.println(sqlUpdate);
        }
        
        catch(InfException ex){
            System.out.println(ex.getMessage());
        } 
}
   


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btClose = new javax.swing.JButton();
        lblPid = new javax.swing.JLabel();
        lblFelStartdatum = new javax.swing.JLabel();
        lblFelSlutdatum = new javax.swing.JLabel();
        lblFelKostnad = new javax.swing.JLabel();
        btTillbaka = new javax.swing.JButton();
        cbxPrioritet = new javax.swing.JComboBox<>();
        cbxStatus = new javax.swing.JComboBox<>();
        lblHallbarhet = new javax.swing.JLabel();
        lblMeddelande = new javax.swing.JLabel();
        lblProjektpartner = new javax.swing.JLabel();
        lblProjektnamn = new javax.swing.JLabel();
        btHallbarhet = new javax.swing.JButton();
        lblBeskrivning = new javax.swing.JLabel();
        btPartner = new javax.swing.JButton();
        lblStartdatum = new javax.swing.JLabel();
        lblSlutdatum = new javax.swing.JLabel();
        lblKostnad = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        lblPrioritet = new javax.swing.JLabel();
        lblLand = new javax.swing.JLabel();
        lblN1 = new javax.swing.JLabel();
        lblN2 = new javax.swing.JLabel();
        tfNprio = new javax.swing.JTextField();
        tfNstatus = new javax.swing.JTextField();
        lblFelmeddelande = new javax.swing.JLabel();
        cbxLand = new javax.swing.JComboBox<>();
        lblNLand = new javax.swing.JLabel();
        tfNLand = new javax.swing.JTextField();
        lblLid = new javax.swing.JLabel();
        lblNLid = new javax.swing.JLabel();
        lblHandlaggare = new javax.swing.JLabel();
        btHandlaggare = new javax.swing.JButton();
        tfProjektnamn = new javax.swing.JTextField();
        tfBeskrivning = new javax.swing.JTextField();
        tfStartdatum = new javax.swing.JTextField();
        tfSlutdatum = new javax.swing.JTextField();
        tfKostnad = new javax.swing.JTextField();
        btAndra = new javax.swing.JButton();
        lblFelNamn = new javax.swing.JLabel();
        lblFelBeskrivning = new javax.swing.JLabel();
        lblPnamn = new javax.swing.JLabel();

        btClose.setText("X");
        btClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCloseActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblPid.setText("pid");

        lblFelStartdatum.setForeground(new java.awt.Color(255, 0, 0));
        lblFelStartdatum.setText("!");

        lblFelSlutdatum.setForeground(new java.awt.Color(255, 0, 0));
        lblFelSlutdatum.setText("!");

        lblFelKostnad.setForeground(new java.awt.Color(255, 0, 0));
        lblFelKostnad.setText("!");

        btTillbaka.setText("<- Tillbaka");
        btTillbaka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btTillbakaActionPerformed(evt);
            }
        });

        cbxPrioritet.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {}));

        cbxStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {}));

        lblHallbarhet.setText("Hållbarhetsmål");

        lblMeddelande.setForeground(new java.awt.Color(0, 204, 51));
        lblMeddelande.setText("Meddelande");

        lblProjektpartner.setText("Projektpartner");

        lblProjektnamn.setText("Projektnamn");

        btHallbarhet.setText("Lägg till/ ta bort från projekt");
        btHallbarhet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btHallbarhetActionPerformed(evt);
            }
        });

        lblBeskrivning.setText("Beskrivning");

        btPartner.setText("Lägg till/ta bort från projekt");
        btPartner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPartnerActionPerformed(evt);
            }
        });

        lblStartdatum.setText("Startdatum");

        lblSlutdatum.setText("Slutdatum");

        lblKostnad.setText("Kostnad");

        lblStatus.setText("Status");

        lblPrioritet.setText("Prioritet");

        lblLand.setText("Land");

        lblN1.setText("Nuvarande:");

        lblN2.setText("Nuvarande:");

        tfNprio.setEditable(false);

        tfNstatus.setEditable(false);

        lblFelmeddelande.setForeground(new java.awt.Color(255, 0, 0));
        lblFelmeddelande.setText("Felmeddelande");

        cbxLand.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {}));
        cbxLand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxLandActionPerformed(evt);
            }
        });

        lblNLand.setText("Nuvarande:");

        tfNLand.setEditable(false);

        lblLid.setText("lid");

        lblNLid.setText("lid");

        lblHandlaggare.setText("Handläggare");

        btHandlaggare.setText("Lägg till/ta bort från projekt");
        btHandlaggare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btHandlaggareActionPerformed(evt);
            }
        });

        tfProjektnamn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfProjektnamnActionPerformed(evt);
            }
        });

        tfBeskrivning.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfBeskrivningActionPerformed(evt);
            }
        });

        tfStartdatum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfStartdatumActionPerformed(evt);
            }
        });

        tfKostnad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfKostnadActionPerformed(evt);
            }
        });

        btAndra.setText("Ändra");
        btAndra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAndraActionPerformed(evt);
            }
        });

        lblFelNamn.setForeground(new java.awt.Color(255, 0, 0));
        lblFelNamn.setText("!");

        lblFelBeskrivning.setForeground(new java.awt.Color(255, 0, 0));
        lblFelBeskrivning.setText("!");

        lblPnamn.setText("Projektnamn");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblProjektnamn)
                            .addComponent(lblBeskrivning)
                            .addComponent(lblStartdatum)
                            .addComponent(lblSlutdatum)
                            .addComponent(lblStatus)
                            .addComponent(lblLand)
                            .addComponent(lblKostnad)
                            .addComponent(lblPrioritet)
                            .addComponent(lblPnamn))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(tfKostnad, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblFelKostnad, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(cbxLand, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cbxPrioritet, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cbxStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addComponent(lblLid))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(tfSlutdatum, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblFelSlutdatum, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(93, 93, 93)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblHandlaggare)
                                    .addComponent(lblNLand)
                                    .addComponent(lblN1)
                                    .addComponent(lblN2)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(tfStartdatum, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblFelStartdatum, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(82, 82, 82)
                                .addComponent(lblProjektpartner))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(tfBeskrivning, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblFelBeskrivning, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(tfProjektnamn, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblFelNamn, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(91, 91, 91)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblMeddelande)
                                    .addComponent(lblHallbarhet)))
                            .addComponent(lblPid)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(btTillbaka)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addComponent(lblFelmeddelande))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfNprio, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(tfNLand, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblNLid))
                            .addComponent(btHandlaggare)
                            .addComponent(btPartner)
                            .addComponent(btHallbarhet)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btAndra)
                                .addComponent(tfNstatus, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(btTillbaka)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPnamn)
                    .addComponent(lblPid))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblFelNamn)
                            .addComponent(tfProjektnamn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblProjektnamn))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMeddelande)
                            .addComponent(lblFelmeddelande))
                        .addGap(33, 33, 33)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfBeskrivning, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBeskrivning)
                    .addComponent(lblFelBeskrivning)
                    .addComponent(lblHallbarhet)
                    .addComponent(btHallbarhet))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfStartdatum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStartdatum)
                    .addComponent(lblFelStartdatum)
                    .addComponent(lblProjektpartner)
                    .addComponent(btPartner))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfSlutdatum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSlutdatum)
                    .addComponent(lblFelSlutdatum)
                    .addComponent(lblHandlaggare)
                    .addComponent(btHandlaggare))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfKostnad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblKostnad)
                    .addComponent(lblFelKostnad))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxLand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLand)
                    .addComponent(lblLid)
                    .addComponent(lblNLand)
                    .addComponent(tfNLand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNLid))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxPrioritet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPrioritet)
                    .addComponent(lblN1)
                    .addComponent(tfNprio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStatus)
                    .addComponent(lblN2)
                    .addComponent(tfNstatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(106, 106, 106)
                .addComponent(btAndra)
                .addGap(44, 44, 44))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCloseActionPerformed
 
    }//GEN-LAST:event_btCloseActionPerformed

    private void btTillbakaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btTillbakaActionPerformed
        this.dispose();
        new OmProjekt_1(idb, epost, pid).setVisible(true);
    }//GEN-LAST:event_btTillbakaActionPerformed

    private void btHallbarhetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btHallbarhetActionPerformed
        new ProjektHallbarhet(idb, pid).setVisible(true);
    }//GEN-LAST:event_btHallbarhetActionPerformed

    private void btPartnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPartnerActionPerformed
        new ProjektPartner(idb, pid).setVisible(true);
    }//GEN-LAST:event_btPartnerActionPerformed

    private void cbxLandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxLandActionPerformed
        visaLid();
    }//GEN-LAST:event_cbxLandActionPerformed

    private void btHandlaggareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btHandlaggareActionPerformed
        new ProjektHandlaggare(idb, pid).setVisible(true);
    }//GEN-LAST:event_btHandlaggareActionPerformed

    private void tfBeskrivningActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfBeskrivningActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfBeskrivningActionPerformed

    private void tfStartdatumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfStartdatumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfStartdatumActionPerformed

    private void tfKostnadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfKostnadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfKostnadActionPerformed

    private void btAndraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAndraActionPerformed
        if(totalKontroll() == true){
            gorAndring();
            fyllTabellAndra();
            lblMeddelande.setText("Lyckades!");
            lblMeddelande.setVisible(true);
        } else {
            lblFelmeddelande.setText("Något gick fel");
            lblFelmeddelande.setVisible(true);
        }
    }//GEN-LAST:event_btAndraActionPerformed

    private void tfProjektnamnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfProjektnamnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfProjektnamnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AndraProjekt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AndraProjekt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AndraProjekt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AndraProjekt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new AndraProjekt().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAndra;
    private javax.swing.JButton btClose;
    private javax.swing.JButton btHallbarhet;
    private javax.swing.JButton btHandlaggare;
    private javax.swing.JButton btPartner;
    private javax.swing.JButton btTillbaka;
    private javax.swing.JComboBox<String> cbxLand;
    private javax.swing.JComboBox<String> cbxPrioritet;
    private javax.swing.JComboBox<String> cbxStatus;
    private javax.swing.JLabel lblBeskrivning;
    private javax.swing.JLabel lblFelBeskrivning;
    private javax.swing.JLabel lblFelKostnad;
    private javax.swing.JLabel lblFelNamn;
    private javax.swing.JLabel lblFelSlutdatum;
    private javax.swing.JLabel lblFelStartdatum;
    private javax.swing.JLabel lblFelmeddelande;
    private javax.swing.JLabel lblHallbarhet;
    private javax.swing.JLabel lblHandlaggare;
    private javax.swing.JLabel lblKostnad;
    private javax.swing.JLabel lblLand;
    private javax.swing.JLabel lblLid;
    private javax.swing.JLabel lblMeddelande;
    private javax.swing.JLabel lblN1;
    private javax.swing.JLabel lblN2;
    private javax.swing.JLabel lblNLand;
    private javax.swing.JLabel lblNLid;
    private javax.swing.JLabel lblPid;
    private javax.swing.JLabel lblPnamn;
    private javax.swing.JLabel lblPrioritet;
    private javax.swing.JLabel lblProjektnamn;
    private javax.swing.JLabel lblProjektpartner;
    private javax.swing.JLabel lblSlutdatum;
    private javax.swing.JLabel lblStartdatum;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JTextField tfBeskrivning;
    private javax.swing.JTextField tfKostnad;
    private javax.swing.JTextField tfNLand;
    private javax.swing.JTextField tfNprio;
    private javax.swing.JTextField tfNstatus;
    private javax.swing.JTextField tfProjektnamn;
    private javax.swing.JTextField tfSlutdatum;
    private javax.swing.JTextField tfStartdatum;
    // End of variables declaration//GEN-END:variables

}
