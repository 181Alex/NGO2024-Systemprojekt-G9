/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ngo2024.Admin;

import oru.inf.InfDB;
import oru.inf.InfException;
import java.util.ArrayList;
import java.util.HashMap;
import ngo2024.Validering;

/**
 *
 * @author linneagottling
 */

public class AndraProjekt extends javax.swing.JFrame {
    
    private InfDB idb;
    private String epost;
    private HashMap<String, String> anstalldLista;
    private HashMap<String, String> landLista;

    /**
     * Creates new form AndraProjekt
     */
    public AndraProjekt(InfDB idb, String epost) {
        this.idb = idb;
        this.epost = epost;
        anstalldLista = new HashMap<>();
        landLista = new HashMap<>();

        initComponents();
        fyllCbProjekt();
        gomAlla();

        
    }
    
private void fyllCbProjekt(){
        cbxProjekt.removeAllItems();
        String sqlFraga = "SELECT projektnamn FROM projekt";
        
        ArrayList<String> namnLista = new ArrayList<>();
        
        try{
            namnLista = idb.fetchColumn(sqlFraga);
            
            for(String namn : namnLista){
                cbxProjekt.addItem(namn);
            }
            
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
            
        }
        
    }
    
    
private void gomAlla(){
        
        //göm allt innan man klickat i ett alternativ
        btAndra.setVisible(false);
        btTaBort.setVisible(false);
        btValj.setVisible(false);
        cbxPrioritet.setVisible(false);
        cbxProjekt.setVisible(false);
        cbxProjektchef.setVisible(false);
        cbxStatus.setVisible(false);
        lblAid.setVisible(false);
        lblBeskrivning.setVisible(false);
        lblFelBeskrivning.setVisible(false);
        lblFelKostnad.setVisible(false);
        lblFelSlutdatum.setVisible(false);
        lblFelStartdatum.setVisible(false);
        lblFelNamn.setVisible(false);
        lblKostnad.setVisible(false);
        lblLand.setVisible(false);
        lblMeddelande.setVisible(false);
        lblPid.setVisible(false);
        lblPnamn.setVisible(false);
        lblPrioritet.setVisible(false);
        lblProjektchef.setVisible(false);
        lblProjektnamn.setVisible(false);
        lblSlutdatum.setVisible(false);
        lblStartdatum.setVisible(false);
        lblStatus.setVisible(false);
        tfBeskrivning.setVisible(false);
        tfKostnad.setVisible(false);
        tfProjektnamn.setVisible(false);
        tfSlutdatum.setVisible(false);
        tfStartdatum.setVisible(false);  
        lblProjektpartner.setVisible(false);
        lblHallbarhet.setVisible(false);
        btHallbarhet.setVisible(false);
        btPartner.setVisible(false);
        lblN1.setVisible(false);
        lblN2.setVisible(false);
        lblN3.setVisible(false);
        lblNaid.setVisible(false);
        tfNprio.setVisible(false);
        tfNstatus.setVisible(false);
        tfNprojektchef.setVisible(false);
        lblFelmeddelande.setVisible(false);
        lblNLand.setVisible(false);
        tfNLand.setVisible(false);
        cbxLand.setVisible(false);
        lblLid.setVisible(false);
        lblNLid.setVisible(false);
    }   

    
private String getPid(){
    String pid = " ";
    
    try{
     
        String sqlFraga = "SELECT pid FROM projekt WHERE projektnamn = '" + cbxProjekt.getSelectedItem() + "'";
        pid = idb.fetchSingle(sqlFraga);
    }
    catch(InfException ex) {
        System.out.println(ex.getMessage());
    }
    return pid;
}
private String getChefAid(String pid){
    String aid = " ";
    
    try{
     
        String sqlFraga = "SELECT aid FROM anstalld "
                           + "JOIN projekt ON aid = projektchef "
                           + "WHERE pid = "+ pid;
        
        aid = idb.fetchSingle(sqlFraga);
    }
    catch(InfException ex) {
        System.out.println(ex.getMessage());
    }
    return aid;
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
    
private void taBortProjekt(String pid){
    
        String sqlFraga = "DELETE FROM projekt WHERE pid = " + pid;
        try{
            idb.delete(sqlFraga);
            lblMeddelande.setText(cbxProjekt.getSelectedIndex()+ "är borttagen");
            lblMeddelande.setVisible(true);
        } catch (InfException ex) {
            
        }
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

private void fyllCbProjektchef(){
    cbxProjektchef.removeAllItems();

    String sqlPerson = "SELECT CONCAT(fornamn, ' ', efternamn) FROM anstalld"
                        + " WHERE aid in (SELECT aid FROM handlaggare)";
      
    try {
        ArrayList<String> personLista = idb.fetchColumn(sqlPerson);
                    
            for(String anstNamn : personLista){
                String sqlAid = "SELECT aid from anstalld WHERE "
                        + "CONCAT(fornamn, ' ', efternamn) = '" + anstNamn + "'";
                String i = idb.fetchSingle(sqlAid);
                cbxProjektchef.addItem(anstNamn);
                anstalldLista.put(i, anstNamn);
                
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
    fyllCbProjektchef();
    
    
    String pid=getPid();
    String pAid = getChefAid(pid);
    String pLid = getLid(pid);
        
        String namn=" ";
        String beskrivning=" ";
        String startdatum=" ";
        String slutdatum=" ";
        String kostnad=" ";
        String nLand=" ";
        String nPrio = " ";
        String nStatus = " ";
        String nChef = " ";
        String nAid = " ";
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
            nChef = idb.fetchSingle("SELECT CONCAT(fornamn, ' ', efternamn) "
                                 + "FROM anstalld WHERE aid = " + pAid);
            nLand = idb.fetchSingle("SELECT namn FROM land WHERE lid = "
                                + "(SELECT land FROM projekt WHERE pid = " + pid + ")");
            
            nAid = pAid;
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
            
            //skriver ut nuvarande
            tfNprio.setText(nPrio);
            tfNstatus.setText(nStatus);
            tfNprojektchef.setText(nChef);
            lblNaid.setText(nAid);  
            lblNLid.setText(nLid);
            
            //sätter rätt värden i checkboxen
            cbxPrioritet.setSelectedItem(nPrio);
            cbxLand.setSelectedItem(nLand);
            cbxStatus.setSelectedItem(nStatus);
            cbxProjektchef.setSelectedItem(nChef);
                    
}

private void gomTaBort(){
    // Visa allt om Ändra
    btAndra.setVisible(true);
    btHallbarhet.setVisible(true);
    btPartner.setVisible(true);
    btValj.setVisible(true);
    cbxPrioritet.setVisible(true);
    cbxProjekt.setVisible(true);
    cbxStatus.setVisible(true);
    cbxProjektchef.setVisible(true);
    lblAid.setVisible(true);
    lblBeskrivning.setVisible(true);
    lblHallbarhet.setVisible(true);
    lblKostnad.setVisible(true);
    lblLand.setVisible(true);
    lblN1.setVisible(true);
    lblN2.setVisible(true);
    lblN3.setVisible(true);
    lblNaid.setVisible(true);
    lblPrioritet.setVisible(true);
    lblProjektchef.setVisible(true);
    lblProjektnamn.setVisible(true);
    lblProjektpartner.setVisible(true);
    lblSlutdatum.setVisible(true);
    lblStartdatum.setVisible(true);
    lblStatus.setVisible(true);
    tfKostnad.setVisible(true);
    tfNprio.setVisible(true);
    tfNprojektchef.setVisible(true);
    tfNstatus.setVisible(true);
    tfProjektnamn.setVisible(true);
    tfSlutdatum.setVisible(true);
    tfStartdatum.setVisible(true);
    tfNLand.setVisible(true);
    cbxLand.setVisible(true);
    lblNLand.setVisible(true);
    lblLid.setVisible(true);
    lblNLid.setVisible(true);
    tfBeskrivning.setVisible(true);
    

    // Göm det som tillhör ta bort
    lblPid.setVisible(false);
    lblPnamn.setVisible(false);
    btTaBort.setVisible(false);
   
    //göm felgrejor
    lblFelBeskrivning.setVisible(false);
    lblFelKostnad.setVisible(false);
    lblFelNamn.setVisible(false);
    lblFelSlutdatum.setVisible(false);
    lblFelStartdatum.setVisible(false);
    
    //gömmer meddelande
    lblMeddelande.setVisible(false);
    lblFelmeddelande.setVisible(false);
}

private void gomAndra(){
    // Göm allt om Ändra
    btAndra.setVisible(false);
    btHallbarhet.setVisible(false);
    btPartner.setVisible(false);
    cbxPrioritet.setVisible(false);  
    cbxStatus.setVisible(false);
    cbxProjektchef.setVisible(false);
    lblAid.setVisible(false);
    lblBeskrivning.setVisible(false);
    lblHallbarhet.setVisible(false);
    lblKostnad.setVisible(false);
    lblLand.setVisible(false);
    lblN1.setVisible(false);
    lblN2.setVisible(false);
    lblN3.setVisible(false);
    lblNaid.setVisible(false);
    lblPrioritet.setVisible(false);
    lblProjektchef.setVisible(false);
    lblProjektnamn.setVisible(false);
    lblProjektpartner.setVisible(false);
    lblSlutdatum.setVisible(false);
    lblStartdatum.setVisible(false);
    lblStatus.setVisible(false);
    tfKostnad.setVisible(false);
    tfNprio.setVisible(false);
    tfNprojektchef.setVisible(false);
    tfNstatus.setVisible(false);
    tfProjektnamn.setVisible(false);
    tfSlutdatum.setVisible(false);
    tfStartdatum.setVisible(false);
    tfBeskrivning.setVisible(false);
    tfNLand.setVisible(false);
    cbxLand.setVisible(false);
    lblNLand.setVisible(false);
    lblLid.setVisible(false);
    lblNLid.setVisible(false);
    

    // Visa det som tillhör ta bort
    lblPid.setVisible(true);
    lblPnamn.setVisible(true);
    btTaBort.setVisible(true);
    btValj.setVisible(true);
    cbxProjekt.setVisible(true);
    
   
    //göm felgrejor
    lblFelBeskrivning.setVisible(false);
    lblFelKostnad.setVisible(false);
    lblFelNamn.setVisible(false);
    lblFelSlutdatum.setVisible(false);
    lblFelStartdatum.setVisible(false);
    
    //göm meddelande
    lblMeddelande.setVisible(false);
    lblFelmeddelande.setVisible(false);
}

private void gomBad(){
    //gömmer alla fel medelandena, används innan ett specefikt fel ska upplysas.
    lblFelNamn.setVisible(false);
    lblFelBeskrivning.setVisible(false);
    lblFelKostnad.setVisible(false);
    lblFelSlutdatum.setVisible(false);
    lblFelStartdatum.setVisible(false);
    lblFelmeddelande.setVisible(false);
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
        String selectedProjekt = (String) cbxProjekt.getSelectedItem();
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
        lblFelmeddelande.setVisible(false);
    } else {
        lblMeddelande.setVisible(false);
        ok = false;
    }
    return ok;
}

private void visaAid(){
        
    lblAid.setText(getSelectedAid());
}

private String getSelectedAid(){
    String selectedPerson = (String) cbxProjektchef.getSelectedItem();
        String aid = " ";
        for(String id : anstalldLista.keySet()){
            String namn = anstalldLista.get(id);
            if(selectedPerson != null && selectedPerson.equals(namn)){
                aid = id;               
            }
        } 
        return aid;
}

private void visaLid(){
   
        lblLid.setText(getSelectedLid());
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

private void gorAndring(){
    String pid=getPid();
        
        String namnS = tfProjektnamn.getText();
        String beskrivningS = tfBeskrivning.getText();
        String startdatumS = tfStartdatum.getText();
        String slutdatumS = tfSlutdatum.getText();
        String kostnadS = tfKostnad.getText();
        
        String landIdS = getSelectedLid();
        String statusS = (String) cbxStatus.getSelectedItem();
        String prioritetS = (String) cbxPrioritet.getSelectedItem();
        String chefIdS = getSelectedAid();              
        
        String sqlUpdate="UPDATE projekt SET projektnamn='" + namnS + "', beskrivning= '" 
                + beskrivningS + "', kostnad = " + kostnadS
                + ", startdatum= '" + startdatumS + "', slutdatum= '" + slutdatumS + "', land = " 
                + landIdS + ", status = '" 
                + statusS + "', prioritet = '" + prioritetS 
                + "', projektchef = " + chefIdS 
                + " WHERE pid = " + pid;
        try{
            idb.update(sqlUpdate);
            System.out.println(sqlUpdate);
        }
        
        catch(InfException ex){
            System.out.println(ex.getMessage());
        } 
}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        chbAndra = new javax.swing.JCheckBox();
        chbTaBort = new javax.swing.JCheckBox();
        btTaBort = new javax.swing.JButton();
        cbxProjekt = new javax.swing.JComboBox<>();
        btValj = new javax.swing.JButton();
        lblPid = new javax.swing.JLabel();
        lblPnamn = new javax.swing.JLabel();
        lblMeddelande = new javax.swing.JLabel();
        lblProjektnamn = new javax.swing.JLabel();
        lblBeskrivning = new javax.swing.JLabel();
        lblStartdatum = new javax.swing.JLabel();
        lblSlutdatum = new javax.swing.JLabel();
        lblKostnad = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        lblPrioritet = new javax.swing.JLabel();
        lblProjektchef = new javax.swing.JLabel();
        lblLand = new javax.swing.JLabel();
        tfProjektnamn = new javax.swing.JTextField();
        tfBeskrivning = new javax.swing.JTextField();
        tfStartdatum = new javax.swing.JTextField();
        tfSlutdatum = new javax.swing.JTextField();
        tfKostnad = new javax.swing.JTextField();
        cbxProjektchef = new javax.swing.JComboBox<>();
        btAndra = new javax.swing.JButton();
        lblAid = new javax.swing.JLabel();
        lblFelNamn = new javax.swing.JLabel();
        lblFelBeskrivning = new javax.swing.JLabel();
        lblFelStartdatum = new javax.swing.JLabel();
        lblFelSlutdatum = new javax.swing.JLabel();
        lblFelKostnad = new javax.swing.JLabel();
        btClose = new javax.swing.JButton();
        cbxPrioritet = new javax.swing.JComboBox<>();
        cbxStatus = new javax.swing.JComboBox<>();
        lblHallbarhet = new javax.swing.JLabel();
        lblProjektpartner = new javax.swing.JLabel();
        btHallbarhet = new javax.swing.JButton();
        btPartner = new javax.swing.JButton();
        lblN1 = new javax.swing.JLabel();
        lblN2 = new javax.swing.JLabel();
        lblN3 = new javax.swing.JLabel();
        tfNprio = new javax.swing.JTextField();
        tfNstatus = new javax.swing.JTextField();
        tfNprojektchef = new javax.swing.JTextField();
        lblNaid = new javax.swing.JLabel();
        lblFelmeddelande = new javax.swing.JLabel();
        cbxLand = new javax.swing.JComboBox<>();
        lblNLand = new javax.swing.JLabel();
        tfNLand = new javax.swing.JTextField();
        lblLid = new javax.swing.JLabel();
        lblNLid = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        chbAndra.setText("Ändra");
        chbAndra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbAndraActionPerformed(evt);
            }
        });

        chbTaBort.setText("Ta bort");
        chbTaBort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbTaBortActionPerformed(evt);
            }
        });

        btTaBort.setText("Ta bort");
        btTaBort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btTaBortActionPerformed(evt);
            }
        });

        cbxProjekt.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btValj.setText("Välj");
        btValj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btValjActionPerformed(evt);
            }
        });

        lblPid.setText("pid");

        lblPnamn.setText("namn");

        lblMeddelande.setForeground(new java.awt.Color(0, 204, 51));
        lblMeddelande.setText("Meddelande");

        lblProjektnamn.setText("Projektnamn");

        lblBeskrivning.setText("Beskrivning");

        lblStartdatum.setText("Startdatum");

        lblSlutdatum.setText("Slutdatum");

        lblKostnad.setText("Kostnad");

        lblStatus.setText("Status");

        lblPrioritet.setText("Prioritet");

        lblProjektchef.setText("Projektchef");

        lblLand.setText("Land");

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

        cbxProjektchef.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {}));
        cbxProjektchef.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxProjektchefActionPerformed(evt);
            }
        });

        btAndra.setText("Ändra");
        btAndra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAndraActionPerformed(evt);
            }
        });

        lblAid.setText("aid");

        lblFelNamn.setForeground(new java.awt.Color(255, 0, 0));
        lblFelNamn.setText("!");

        lblFelBeskrivning.setForeground(new java.awt.Color(255, 0, 0));
        lblFelBeskrivning.setText("!");

        lblFelStartdatum.setForeground(new java.awt.Color(255, 0, 0));
        lblFelStartdatum.setText("!");

        lblFelSlutdatum.setForeground(new java.awt.Color(255, 0, 0));
        lblFelSlutdatum.setText("!");

        lblFelKostnad.setForeground(new java.awt.Color(255, 0, 0));
        lblFelKostnad.setText("!");

        btClose.setText("X");
        btClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCloseActionPerformed(evt);
            }
        });

        cbxPrioritet.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {}));

        cbxStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {}));

        lblHallbarhet.setText("Hållbarhetsmål");

        lblProjektpartner.setText("Projektpartner");

        btHallbarhet.setText("Lägg till/ ta bort från projekt");
        btHallbarhet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btHallbarhetActionPerformed(evt);
            }
        });

        btPartner.setText("Lägg till/ta bort från projekt");
        btPartner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPartnerActionPerformed(evt);
            }
        });

        lblN1.setText("Nuvarande:");

        lblN2.setText("Nuvarande:");

        lblN3.setText("Nuvarande:");

        tfNprio.setEditable(false);

        tfNstatus.setEditable(false);

        tfNprojektchef.setEditable(false);

        lblNaid.setText("aid");

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(chbAndra)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chbTaBort)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btTaBort)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btClose))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                            .addComponent(lblProjektnamn)
                                                            .addGap(35, 35, 35))
                                                        .addGroup(layout.createSequentialGroup()
                                                            .addComponent(lblBeskrivning)
                                                            .addGap(39, 39, 39)))
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(lblStartdatum)
                                                        .addGap(43, 43, 43)))
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(lblSlutdatum)
                                                    .addGap(48, 48, 48)))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblKostnad)
                                                .addGap(58, 58, 58)))
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(lblPrioritet)
                                                .addComponent(lblLand))
                                            .addGap(63, 63, 63)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblProjektchef)
                                            .addComponent(lblStatus))
                                        .addGap(42, 42, 42)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(tfStartdatum, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfSlutdatum, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfKostnad, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbxProjektchef, javax.swing.GroupLayout.Alignment.LEADING, 0, 179, Short.MAX_VALUE)
                                    .addComponent(tfProjektnamn)
                                    .addComponent(tfBeskrivning)
                                    .addComponent(cbxPrioritet, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbxStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbxLand, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cbxProjekt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btValj, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblPid)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblPnamn)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblAid)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(lblLid)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblFelBeskrivning, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblFelNamn))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(62, 62, 62)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblN1)
                                            .addComponent(lblN2)
                                            .addComponent(lblN3)
                                            .addComponent(lblNLand))
                                        .addGap(26, 26, 26)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(tfNprio)
                                            .addComponent(tfNstatus, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                                            .addComponent(tfNprojektchef, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                                            .addComponent(tfNLand))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblNaid)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btAndra))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblNLid)
                                                .addGap(0, 0, Short.MAX_VALUE))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(122, 122, 122)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblHallbarhet)
                                            .addComponent(lblProjektpartner))
                                        .addGap(34, 34, 34)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btPartner)
                                            .addComponent(btHallbarhet))
                                        .addGap(0, 6, Short.MAX_VALUE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblFelStartdatum, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblFelSlutdatum, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblFelKostnad, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(102, 102, 102)
                                .addComponent(lblMeddelande)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblFelmeddelande)
                                .addGap(85, 85, 85)))))
                .addGap(19, 19, 19))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chbAndra)
                    .addComponent(chbTaBort)
                    .addComponent(btTaBort)
                    .addComponent(btClose))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxProjekt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btValj)
                    .addComponent(lblPid)
                    .addComponent(lblPnamn)
                    .addComponent(lblMeddelande)
                    .addComponent(lblFelmeddelande))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblProjektnamn)
                            .addComponent(tfProjektnamn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblFelNamn))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblBeskrivning)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(tfBeskrivning, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblFelBeskrivning)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblStartdatum)
                            .addComponent(tfStartdatum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblFelStartdatum))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblSlutdatum)
                            .addComponent(tfSlutdatum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblFelSlutdatum))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblKostnad)
                            .addComponent(tfKostnad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblFelKostnad))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblLand)
                            .addComponent(cbxLand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblLid)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblHallbarhet)
                            .addComponent(btHallbarhet))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblProjektpartner)
                            .addComponent(btPartner))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNLand)
                            .addComponent(tfNLand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNLid))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrioritet)
                    .addComponent(cbxPrioritet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblN1)
                    .addComponent(tfNprio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStatus)
                    .addComponent(cbxStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblN2)
                    .addComponent(tfNstatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProjektchef)
                    .addComponent(cbxProjektchef, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btAndra)
                    .addComponent(lblAid)
                    .addComponent(lblN3)
                    .addComponent(tfNprojektchef, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNaid))
                .addGap(19, 19, 19))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tfStartdatumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfStartdatumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfStartdatumActionPerformed

    private void tfKostnadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfKostnadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfKostnadActionPerformed

    private void btCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_btCloseActionPerformed

    private void tfBeskrivningActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfBeskrivningActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfBeskrivningActionPerformed

    private void btValjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btValjActionPerformed
        if(chbTaBort.isSelected()){
         lblPid.setText(getPid());
          int i=cbxProjekt.getSelectedIndex();
          lblPnamn.setText(cbxProjekt.getItemAt(i));
      }else if(chbAndra.isSelected()){         
           fyllTabellAndra();
           visaAid();
           visaLid();
       }
        
    }//GEN-LAST:event_btValjActionPerformed

    private void btHallbarhetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btHallbarhetActionPerformed
       
    }//GEN-LAST:event_btHallbarhetActionPerformed

    private void btPartnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPartnerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btPartnerActionPerformed

    private void cbxProjektchefActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxProjektchefActionPerformed
        visaAid();
    }//GEN-LAST:event_cbxProjektchefActionPerformed

    private void chbAndraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbAndraActionPerformed
        if(chbAndra.isSelected()){
            chbTaBort.setSelected(false);
            gomTaBort();
        }
    }//GEN-LAST:event_chbAndraActionPerformed

    private void chbTaBortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbTaBortActionPerformed
        if(chbTaBort.isSelected()){
           chbAndra.setSelected(false);
           gomAndra();
       }
    }//GEN-LAST:event_chbTaBortActionPerformed

    private void btTaBortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btTaBortActionPerformed
    String pid=lblPid.getText();
    taBortProjekt(pid);
    fyllCbProjekt();
    lblMeddelande.setText("Borttagen");
    lblMeddelande.setVisible(true);
    }//GEN-LAST:event_btTaBortActionPerformed

    private void cbxLandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxLandActionPerformed
       visaLid();
    }//GEN-LAST:event_cbxLandActionPerformed

    private void btAndraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAndraActionPerformed
    if(totalKontroll() == true){
           gorAndring();
           fyllTabellAndra();
           lblMeddelande.setText("Lyckades!");
           lblMeddelande.setVisible(true);
           fyllCbProjekt();
       } else {
           lblFelmeddelande.setText("Något gick fel");
           lblFelmeddelande.setVisible(true);
       }
    }//GEN-LAST:event_btAndraActionPerformed

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
    private javax.swing.JButton btPartner;
    private javax.swing.JButton btTaBort;
    private javax.swing.JButton btValj;
    private javax.swing.JComboBox<String> cbxLand;
    private javax.swing.JComboBox<String> cbxPrioritet;
    private javax.swing.JComboBox<String> cbxProjekt;
    private javax.swing.JComboBox<String> cbxProjektchef;
    private javax.swing.JComboBox<String> cbxStatus;
    private javax.swing.JCheckBox chbAndra;
    private javax.swing.JCheckBox chbTaBort;
    private javax.swing.JLabel lblAid;
    private javax.swing.JLabel lblBeskrivning;
    private javax.swing.JLabel lblFelBeskrivning;
    private javax.swing.JLabel lblFelKostnad;
    private javax.swing.JLabel lblFelNamn;
    private javax.swing.JLabel lblFelSlutdatum;
    private javax.swing.JLabel lblFelStartdatum;
    private javax.swing.JLabel lblFelmeddelande;
    private javax.swing.JLabel lblHallbarhet;
    private javax.swing.JLabel lblKostnad;
    private javax.swing.JLabel lblLand;
    private javax.swing.JLabel lblLid;
    private javax.swing.JLabel lblMeddelande;
    private javax.swing.JLabel lblN1;
    private javax.swing.JLabel lblN2;
    private javax.swing.JLabel lblN3;
    private javax.swing.JLabel lblNLand;
    private javax.swing.JLabel lblNLid;
    private javax.swing.JLabel lblNaid;
    private javax.swing.JLabel lblPid;
    private javax.swing.JLabel lblPnamn;
    private javax.swing.JLabel lblPrioritet;
    private javax.swing.JLabel lblProjektchef;
    private javax.swing.JLabel lblProjektnamn;
    private javax.swing.JLabel lblProjektpartner;
    private javax.swing.JLabel lblSlutdatum;
    private javax.swing.JLabel lblStartdatum;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JTextField tfBeskrivning;
    private javax.swing.JTextField tfKostnad;
    private javax.swing.JTextField tfNLand;
    private javax.swing.JTextField tfNprio;
    private javax.swing.JTextField tfNprojektchef;
    private javax.swing.JTextField tfNstatus;
    private javax.swing.JTextField tfProjektnamn;
    private javax.swing.JTextField tfSlutdatum;
    private javax.swing.JTextField tfStartdatum;
    // End of variables declaration//GEN-END:variables
}
