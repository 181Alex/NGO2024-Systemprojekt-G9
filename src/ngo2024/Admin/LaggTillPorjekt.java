/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ngo2024.Admin;

import ngo2024.Validering;
import oru.inf.InfDB;
import oru.inf.InfException;
import java.util.ArrayList;
import java.util.HashMap;



/**
 *
 * @author alexanderabboud & linneagottling
 */
public class LaggTillPorjekt extends javax.swing.JFrame {

    private InfDB idb;
    private String inloggadAnvandare;  
    boolean kontrollOk;
    private HashMap<String, String> anstalldLista;
    private HashMap<String, String> landLista;
    
    /**
     * Creates new form LaggTillPorjekt
     */
    public LaggTillPorjekt(InfDB idb, String inloggadAnvandare) {
        this.idb=idb;
        this.inloggadAnvandare=inloggadAnvandare;
        anstalldLista = new HashMap<>();
        landLista = new HashMap<>();
        initComponents();
        kontrollOk=false;
        fyllAllt();
        lblError.setVisible(false);
        lblSkapad.setVisible(false);
        gomBad();
        
    }
    
    private void gomBad(){
        lblNamnBad.setVisible(false);
        lblBeskrivningBad.setVisible(false);
        lblStartDatumBad.setVisible(false);
        lblSlutDatumBad.setVisible(false);
        lblKostnadBad.setVisible(false);
    }
        
private void fyllStatus(){
        cbStatus.removeAllItems();
        
        cbStatus.addItem("Planerat");
        cbStatus.addItem("Pågående");
        cbStatus.addItem("Avslutat");
        cbStatus.addItem("Pausad");
    }
       
private void fyllProjektChef(){
        cbProjektChef.removeAllItems();
        
        String sqlFraga="SELECT CONCAT(fornamn, ' ', efternamn) FROM anstalld WHERE aid in (SELECT aid FROM handlaggare)";
        
        try{
            ArrayList<String> chefLista=idb.fetchColumn(sqlFraga);
            
            for(String anstNamn : chefLista){
                String sqlAid = "SELECT aid from anstalld WHERE "
                        + "CONCAT(fornamn, ' ', efternamn) = '" + anstNamn + "'";
                String i = idb.fetchSingle(sqlAid);
                cbProjektChef.addItem(anstNamn);
                anstalldLista.put(i, anstNamn);
                
            }
            
        }catch(InfException ex){
            System.out.println(ex.getMessage());
        }}
        
private void fyllPrioritet(){
        cbPrioritet.removeAllItems();
        
        cbPrioritet.addItem("låg");
        cbPrioritet.addItem("Medel");
        cbPrioritet.addItem("hög");
    }
    
private void fyllLand(){
        cbLand.removeAllItems();
        
        String sqlLand = "SELECT namn FROM land ";
        
        try{

        ArrayList<String> allaLanderLista =  idb.fetchColumn(sqlLand);
                                             
            for(String landNamn : allaLanderLista){
               String sqlLid = "SELECT lid FROM land WHERE "
                       + "namn = '" + landNamn + "'";
               String i = idb.fetchSingle(sqlLid);
               cbLand.addItem(landNamn);
               landLista.put(i, landNamn);
            }
        }catch(InfException ex){
            System.out.println(ex.getMessage());
        }
    }
    
private void fyllAllt(){
        fyllStatus();
        fyllProjektChef();
        fyllPrioritet();
        fyllLand();   
        lblAid.setText(getPChef());
        lblLid.setText(getLandid());
}
    
private int hogstaPid(){
        // hämtar ut högsta pid
        int hogsta=0;
        String sqlFraga="Select MAX(pid) FROM projekt";
        try{
            String max=idb.fetchSingle(sqlFraga);
            hogsta=Integer.parseInt(max);
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        System.out.println(hogsta);
        return hogsta+1;
}
    
private boolean namnKontroll(){
        Validering valid = new Validering(idb);
        String namn = tfNamn.getText();
        // kontrollerar namn format
    if (valid.checkNamn(namn) && valid.checkStorlek(255, namn)) {
            lblNamnBad.setVisible(false);
            return true;
    } else {
            lblNamnBad.setVisible(true);
            return false;
    }
}

private boolean mellanDatum(){
        Validering valid = new Validering(idb);
        String start = tfStartDatum.getText();
        String slut=tfSlutDatum.getText();
        // kontrollerar namn format
    if (valid.checkDatumSkillnad(start, slut)) {
            lblStartDatumBad.setVisible(true);
            lblSlutDatumBad.setVisible(true);
            return true;
    } else {
            lblStartDatumBad.setVisible(true);
            lblSlutDatumBad.setVisible(true);
            return false;
    }
}
    
    
private boolean beskrivningKontroll(){
           Validering valid = new Validering(idb);
        String besk = tfBeskrivning.getText();
        // samma som alla andra kontroller men använder förnamns valideringen då de gör samma sak
    if (valid.checkBeskrivning(besk)&& valid.checkStorlek(255, besk)) {
            lblBeskrivningBad.setVisible(false);
            return true;
    } else {
            lblBeskrivningBad.setVisible(true);
            return false;
    }
}
    
    
private boolean stDatumKontroll(){
Validering valid = new Validering(idb); 
    
    // Hämta text från textfältet
    String datum = tfStartDatum.getText(); 
    
    // Kontrollera om e-postadressen är giltig
    if (valid.checkDatum(datum)) {
        lblStartDatumBad.setVisible(false); // Göm varning
        return true;
    } else {
        lblStartDatumBad.setVisible(true); // Visa varning
        return false;
    }
}

private boolean slDatumKontroll(){
Validering valid = new Validering(idb); 
    
    // Hämta text från textfältet
    String datum = tfSlutDatum.getText(); 
    
    // Kontrollera om e-postadressen är giltig
    if (valid.checkDatum(datum)) {
        lblSlutDatumBad.setVisible(false); // Göm varning
        return true;
    } else {
        lblSlutDatumBad.setVisible(true); // Visa varning
        return false;
    }
}

private boolean kostnadKontroll(){
    Validering enValidering = new Validering(idb);
    String kostnad = tfKostnad.getText();
    if(enValidering.checkKostnad(kostnad)){
        lblKostnadBad.setVisible(false);
        return true;
    } else {
        lblKostnadBad.setVisible(true);
        return false;

    }
}
    
private void totalKontroll() {
    Boolean totOk = true;

    if (!namnKontroll()) {
        totOk = false;
        lblNamnBad.setVisible(true);
    } else if (!beskrivningKontroll()) {  
        totOk = false;
        lblBeskrivningBad.setVisible(true);
    } else if (!stDatumKontroll()) {  
        totOk = false;
        lblStartDatumBad.setVisible(true);
    } else if (!slDatumKontroll()) {  
        totOk = false;
        lblSlutDatumBad.setVisible(true);
    } else if (!kostnadKontroll()) {  
        totOk = false;
        lblKostnadBad.setVisible(true);
    }
    else if(!mellanDatum()){
        totOk=false;
    }
    kontrollOk=totOk;
    }

private String getLandid(){
        String selectedLand = (String) cbLand.getSelectedItem();
        String lid = " ";
        for(String id : landLista.keySet()){
            String namn = landLista.get(id);
            if(selectedLand != null && selectedLand.equals(namn)){
                lid = id;               
            }
        }
        return lid;
}
    
private String getPChef(){
        String selectedPerson = (String) cbProjektChef.getSelectedItem();
        String aid = " ";
        for(String id : anstalldLista.keySet()){
            String namn = anstalldLista.get(id);
            if(selectedPerson != null && selectedPerson.equals(namn)){
                aid = id;               
            }
        } 
        return aid;
    }

public void skapaNu(){
        String status=(String) cbStatus.getSelectedItem();
        String prioritet=(String) cbPrioritet.getSelectedItem();
        String sqlFraga= "INSERT INTO projekt VALUES(" + hogstaPid() + ", '" + tfNamn.getText() + "', '"
                + tfBeskrivning.getText() + "', '" + tfStartDatum.getText() + "', '" + tfSlutDatum.getText()
                + "', " + tfKostnad.getText() + ", '" + status + "', '" + prioritet + "', " + getPChef() 
                + ", " + getLandid() + ")";
        System.out.println(sqlFraga);
        try{
            idb.insert(sqlFraga);
        } catch(InfException ex){
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

        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        tfNamn = new javax.swing.JTextField();
        tfBeskrivning = new javax.swing.JTextField();
        tfStartDatum = new javax.swing.JTextField();
        tfSlutDatum = new javax.swing.JTextField();
        tfKostnad = new javax.swing.JTextField();
        cbProjektChef = new javax.swing.JComboBox<>();
        cbLand = new javax.swing.JComboBox<>();
        cbStatus = new javax.swing.JComboBox<>();
        cbPrioritet = new javax.swing.JComboBox<>();
        btnTillbaka = new javax.swing.JButton();
        lblNamnBad = new javax.swing.JLabel();
        lblBeskrivningBad = new javax.swing.JLabel();
        lblStartDatumBad = new javax.swing.JLabel();
        lblSlutDatumBad = new javax.swing.JLabel();
        lblKostnadBad = new javax.swing.JLabel();
        btnSkapa = new javax.swing.JButton();
        lblError = new javax.swing.JLabel();
        lblSkapad = new javax.swing.JLabel();
        lblLid = new javax.swing.JLabel();
        lblAid = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel7.setText("Lägg till projekt");

        jLabel1.setText("Namn");

        jLabel2.setText("Beskrivning");

        jLabel3.setText("Start-datum");

        jLabel4.setText("Slut-datum");

        jLabel5.setText("Kostnad");

        jLabel6.setText("Status");

        jLabel8.setText("Prioritet");

        jLabel9.setText("Projektchef");

        jLabel10.setText("Land");

        tfNamn.setText("Namn");
        tfNamn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfNamnActionPerformed(evt);
            }
        });

        tfBeskrivning.setText("Beskrivning");

        tfStartDatum.setText("yyyy-mm-dd");

        tfSlutDatum.setText("yyyy-mm-dd");

        tfKostnad.setText("1234567,12");
        tfKostnad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfKostnadActionPerformed(evt);
            }
        });

        cbProjektChef.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { }));

        cbLand.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {  }));
        cbLand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbLandActionPerformed(evt);
            }
        });

        cbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { }));

        cbPrioritet.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {  }));

        btnTillbaka.setText("X");
        btnTillbaka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTillbakaActionPerformed(evt);
            }
        });

        lblNamnBad.setForeground(new java.awt.Color(255, 0, 51));
        lblNamnBad.setText("!");

        lblBeskrivningBad.setForeground(new java.awt.Color(255, 0, 51));
        lblBeskrivningBad.setText("!");

        lblStartDatumBad.setForeground(new java.awt.Color(255, 0, 51));
        lblStartDatumBad.setText("!");

        lblSlutDatumBad.setForeground(new java.awt.Color(255, 0, 51));
        lblSlutDatumBad.setText("!");

        lblKostnadBad.setForeground(new java.awt.Color(255, 0, 51));
        lblKostnadBad.setText("!");

        btnSkapa.setText("Skapa");
        btnSkapa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSkapaActionPerformed(evt);
            }
        });

        lblError.setForeground(new java.awt.Color(255, 0, 51));
        lblError.setText("Error!");

        lblSkapad.setForeground(new java.awt.Color(0, 204, 51));
        lblSkapad.setText("Skapad");

        lblLid.setText("lid");

        lblAid.setText("aid");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSkapa)
                        .addGap(33, 33, 33)
                        .addComponent(lblSkapad)
                        .addGap(51, 51, 51)
                        .addComponent(lblError))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tfNamn)
                            .addComponent(tfBeskrivning)
                            .addComponent(tfStartDatum, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                            .addComponent(tfSlutDatum)
                            .addComponent(tfKostnad)
                            .addComponent(cbProjektChef, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbLand, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbPrioritet, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNamnBad)
                                    .addComponent(lblBeskrivningBad)
                                    .addComponent(lblStartDatumBad)
                                    .addComponent(lblSlutDatumBad)
                                    .addComponent(lblKostnadBad)
                                    .addComponent(btnTillbaka)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblAid)
                                    .addComponent(lblLid)))))
                    .addComponent(jLabel7))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(btnTillbaka))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tfNamn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblNamnBad)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tfBeskrivning, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblBeskrivningBad)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tfStartDatum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblStartDatumBad)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tfSlutDatum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblSlutDatumBad)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tfKostnad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblKostnadBad)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cbPrioritet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbProjektChef, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblAid)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbLand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblLid)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSkapa)
                    .addComponent(lblSkapad)
                    .addComponent(lblError))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tfNamnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfNamnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfNamnActionPerformed

    private void tfKostnadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfKostnadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfKostnadActionPerformed

    private void btnTillbakaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTillbakaActionPerformed
        new AdminMeny(idb, inloggadAnvandare).setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnTillbakaActionPerformed

    private void btnSkapaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSkapaActionPerformed
       totalKontroll();
        if(kontrollOk){
            lblSkapad.setVisible(true);
            skapaNu();
        }
        else{
            lblError.setVisible(true);
        }
    }//GEN-LAST:event_btnSkapaActionPerformed

    private void cbLandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbLandActionPerformed
     lblLid.setText(getLandid());
    }//GEN-LAST:event_cbLandActionPerformed

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
            java.util.logging.Logger.getLogger(LaggTillPorjekt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LaggTillPorjekt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LaggTillPorjekt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LaggTillPorjekt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new LaggTillPorjekt().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSkapa;
    private javax.swing.JButton btnTillbaka;
    private javax.swing.JComboBox<String> cbLand;
    private javax.swing.JComboBox<String> cbPrioritet;
    private javax.swing.JComboBox<String> cbProjektChef;
    private javax.swing.JComboBox<String> cbStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblAid;
    private javax.swing.JLabel lblBeskrivningBad;
    private javax.swing.JLabel lblError;
    private javax.swing.JLabel lblKostnadBad;
    private javax.swing.JLabel lblLid;
    private javax.swing.JLabel lblNamnBad;
    private javax.swing.JLabel lblSkapad;
    private javax.swing.JLabel lblSlutDatumBad;
    private javax.swing.JLabel lblStartDatumBad;
    private javax.swing.JTextField tfBeskrivning;
    private javax.swing.JTextField tfKostnad;
    private javax.swing.JTextField tfNamn;
    private javax.swing.JTextField tfSlutDatum;
    private javax.swing.JTextField tfStartDatum;
    // End of variables declaration//GEN-END:variables
}
