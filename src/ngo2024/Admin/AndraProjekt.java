/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ngo2024.Admin;

import oru.inf.InfDB;
import oru.inf.InfException;
import java.util.ArrayList;

/**
 *
 * @author linneagottling
 */

public class AndraProjekt extends javax.swing.JFrame {
    
    private InfDB idb;
    private String epost;

    /**
     * Creates new form AndraProjekt
     */
    public AndraProjekt(InfDB idb, String epost) {
        this.idb = idb;
        this.epost = epost;

        initComponents();
        fyllCb();
        //gomAlla();
        
    }
    
    private void fyllCb(){
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
        lblFelLand.setVisible(false);
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
        tfLand.setVisible(false);
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
private String getAid(){
    String sPid = getPid();
    int pid = Integer.parseInt(sPid);
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
    
private void taBortProjekt(int pid){
    
        String sqlFraga = "DELETE FROM projekt WHERE pid = " + pid;
        try{
            idb.delete(sqlFraga);
            lblMeddelande.setText(cbxProjekt.getSelectedIndex()+ "är borttagen");
            lblMeddelande.setVisible(true);
        } catch (InfException ex) {
            
        }
}

private void fyllTabellAndra(){
     String sPid=getPid();
        int pid=Integer.parseInt(sPid);
    String sAid = getAid();
        int aid = Integer.parseInt(sAid);
        
        String namn=" ";
        String beskrivning=" ";
        String startdatum=" ";
        String slutdatum=" ";
        String kostnad=" ";
        String land=" ";
        String nPrio = " ";
        String nStatus = " ";
        String nChef = " ";
        String nAid = " ";

        //uppdaterar alla comboboxes
        cbxPrioritet.removeAllItems();
        cbxStatus.removeAllItems();
        cbxProjektchef.removeAllItems();

        ArrayList<String> prioritetLista = new ArrayList<>();
        ArrayList<String> statusLista = new ArrayList<>();
        ArrayList<String> projektchefLista = new ArrayList<>();

        String sqlPrio = "SELECT prioritet FROM projekt ";
        String sqlStat = "SELECT status FROM projekt";
        String sqlPrCh = "SELECT namn FROM anstalld WHERE aid = " + aid;

        try{
            namn = idb.fetchSingle("SELECT projektnamn FROM projekt WHERE pid = " + pid);
            beskrivning = idb.fetchSingle("SELECT beskrivning FROM projekt WHERE pid = " + pid);
            startdatum = idb.fetchSingle("SELECT startdatum FROM projekt WHERE pid = " + pid);
            slutdatum = idb.fetchSingle("SELECT slutdatum FROM projekt WHERE pid = " + pid);
            kostnad = idb.fetchSingle("SELECT kostnad FROM projekt WHERE pid = " + pid);
            land = idb.fetchSingle("SELECT land FROM projekt WHERE pid = " + pid);
            
            //hämtar ut nuvarande 
            nPrio = idb.fetchSingle("SELECT prioritet FROM projekt WHERE pid = " + pid);
            nStatus = idb.fetchSingle("SELECT status FROM projekt WHERE pid = " + pid);
            nChef = idb.fetchSingle("SELECT namn FROM projekt WHERE projektchef = " + aid);
            nAid = idb.fetchSingle("SELECT projektchef FROM projekt WHERE aid = " + aid);

            prioritetLista = idb.fetchColumn(sqlPrio);
            statusLista = idb.fetchColumn(sqlStat);
            projektchefLista = idb.fetchColumn(sqlPrCh);

            for(String prio : prioritetLista){
                cbxPrioritet.addItem(prio);
            }
            for(String stat: statusLista){
                cbxStatus.addItem(stat);
            }
            for(String pnamn : projektchefLista){
                cbxProjektchef.addItem(pnamn);
            }         
            
        }
        catch(InfException ex){
            System.out.println(ex.getMessage());
        } 
            tfProjektnamn.setText(namn);
            tfBeskrivning.setText(beskrivning);
            tfStartdatum.setText(startdatum);
            tfSlutdatum.setText(slutdatum);
            tfKostnad.setText(kostnad);
            tfLand.setText(land);
            
            //skriver ut nuvarande
            tfNprio.setText(nPrio);
            tfNstatus.setText(nStatus);
            tfNprojektchef.setText(nChef);
            lblNaid.setText(nAid);
                    
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
        tfLand = new javax.swing.JTextField();
        cbxProjektchef = new javax.swing.JComboBox<>();
        btAndra = new javax.swing.JButton();
        lblAid = new javax.swing.JLabel();
        lblFelNamn = new javax.swing.JLabel();
        lblFelBeskrivning = new javax.swing.JLabel();
        lblFelStartdatum = new javax.swing.JLabel();
        lblFelSlutdatum = new javax.swing.JLabel();
        lblFelKostnad = new javax.swing.JLabel();
        lblFelLand = new javax.swing.JLabel();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        chbAndra.setText("Ändra");

        chbTaBort.setText("Ta bort");

        btTaBort.setText("Ta bort");

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

        cbxProjektchef.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btAndra.setText("Ändra");

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

        lblFelLand.setForeground(new java.awt.Color(255, 0, 0));
        lblFelLand.setText("!");

        btClose.setText("X");
        btClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCloseActionPerformed(evt);
            }
        });

        cbxPrioritet.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbxStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
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
                            .addComponent(tfLand, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxProjektchef, javax.swing.GroupLayout.Alignment.LEADING, 0, 179, Short.MAX_VALUE)
                            .addComponent(tfProjektnamn)
                            .addComponent(tfBeskrivning)
                            .addComponent(cbxPrioritet, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbxStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblFelSlutdatum, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblFelKostnad, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblFelLand, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(lblFelNamn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(lblFelBeskrivning, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(lblFelStartdatum, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(71, 71, 71)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblHallbarhet)
                                            .addComponent(lblProjektpartner))
                                        .addGap(34, 34, 34)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btPartner)
                                            .addComponent(btHallbarhet))))
                                .addGap(0, 14, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblAid)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(62, 62, 62)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblN1)
                                            .addComponent(lblN2)
                                            .addComponent(lblN3))
                                        .addGap(26, 26, 26)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(tfNprio)
                                                .addComponent(tfNstatus, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE))
                                            .addComponent(tfNprojektchef, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblNaid)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btAndra))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMeddelande)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cbxProjekt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btValj, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblPid)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblPnamn)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(chbAndra)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chbTaBort)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btTaBort)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btClose)))
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
                    .addComponent(lblPnamn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMeddelande)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblProjektnamn)
                            .addComponent(tfProjektnamn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblFelNamn))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblBeskrivning)
                            .addComponent(lblFelBeskrivning)
                            .addComponent(tfBeskrivning, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                            .addComponent(lblFelLand)
                            .addComponent(tfLand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblLand))
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
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblHallbarhet)
                            .addComponent(btHallbarhet))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblProjektpartner)
                            .addComponent(btPartner))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
       }
        
    }//GEN-LAST:event_btValjActionPerformed

    private void btHallbarhetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btHallbarhetActionPerformed
       
    }//GEN-LAST:event_btHallbarhetActionPerformed

    private void btPartnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPartnerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btPartnerActionPerformed

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
    private javax.swing.JLabel lblFelLand;
    private javax.swing.JLabel lblFelNamn;
    private javax.swing.JLabel lblFelSlutdatum;
    private javax.swing.JLabel lblFelStartdatum;
    private javax.swing.JLabel lblHallbarhet;
    private javax.swing.JLabel lblKostnad;
    private javax.swing.JLabel lblLand;
    private javax.swing.JLabel lblMeddelande;
    private javax.swing.JLabel lblN1;
    private javax.swing.JLabel lblN2;
    private javax.swing.JLabel lblN3;
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
    private javax.swing.JTextField tfLand;
    private javax.swing.JTextField tfNprio;
    private javax.swing.JTextField tfNprojektchef;
    private javax.swing.JTextField tfNstatus;
    private javax.swing.JTextField tfProjektnamn;
    private javax.swing.JTextField tfSlutdatum;
    private javax.swing.JTextField tfStartdatum;
    // End of variables declaration//GEN-END:variables
}
