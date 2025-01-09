/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ngo2024.Admin;

import java.util.ArrayList;
import java.util.HashMap;
import ngo2024.Validering;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author alexanderabboud
 */
public class LaggTillAvdelning extends javax.swing.JFrame {

    private InfDB idb;
    private String epost;  
    boolean kontrollOk;
    private HashMap<String, String> anstalldLista;
    private HashMap<String, String> stadLista;
    
     /**
     * Initierar LaggTillAvdelning objekt 
     * Låter administratör lägga till en ny avdelning
     *
     * @param idb initierar fält för att interagera med databasen
     * @param epost eposten som den inloggande användaren använder
     */

    
    public LaggTillAvdelning(InfDB idb, String epos) {
        this.idb=idb;
        this.epost=epost;
        kontrollOk=false;
        initComponents();
        anstalldLista = new HashMap<>();
        stadLista = new HashMap<>();
        instansering();
        gomBad();
    }
    
     /**
     * ger projektchef och stad
     */
    
    private void instansering(){
        fyllProjektChef();
        fyllStad();
        lblError.setVisible(false);
        lblSkapad.setVisible(false);
    }
    
    /**
     * fyller i combo box för projektchefer
     */
    
    private void fyllProjektChef(){
        cbChef.removeAllItems();
        String sqlFraga="SELECT CONCAT(fornamn, ' ', efternamn) FROM anstalld WHERE aid in (SELECT aid FROM handlaggare)";
        
        try{
            ArrayList<String> chefLista=idb.fetchColumn(sqlFraga);
            
            for(String anstNamn : chefLista){
                String sqlAid = "SELECT aid from anstalld WHERE "
                        + "CONCAT(fornamn, ' ', efternamn) = '" + anstNamn + "'";
                String i = idb.fetchSingle(sqlAid);
                cbChef.addItem(anstNamn);
                anstalldLista.put(i, anstNamn);
                
            }
            
        }catch(InfException ex){
            System.out.println(ex.getMessage());
        }}
    
    /**
     * fyller i combo box för städer
     */
    
    private void fyllStad(){
        cbStad.removeAllItems();
        //fyller stads listan
        String sqlLand = "SELECT namn FROM stad ";
        
        try{

        ArrayList<String> allaStaderLista =  idb.fetchColumn(sqlLand);
                                             
            for(String stadNamn : allaStaderLista){
               String sqlLid = "SELECT sid FROM stad WHERE "
                       + "namn = '" + stadNamn + "'";
               String i = idb.fetchSingle(sqlLid);
               cbStad.addItem(stadNamn);
               stadLista.put(i, stadNamn);
            }
        }catch(InfException ex){
            System.out.println(ex.getMessage());
        }
    }
    public void gomBad(){
        lblNamnBad.setVisible(false);
        lblBeskrivningBad.setVisible(false);
        lblAdressBad.setVisible(false);
        lblEpostBad.setVisible(false);
        lblTelefonBad.setVisible(false);
    }
    
    /**
     * hämtar högsta avdelnings ID
     */
    
    private int hogstaAvdid(){
        int hogsta=0;
        String sqlFraga="Select MAX(avdid) FROM avdelning";
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
    
    /**
     * kontrollerar så att allt är valid
     */
    
    private void totalKontroll() {
    Boolean totOk = true;

    if (!namnKontroll()) {
        totOk = false;
        lblNamnBad.setVisible(true);
    } else if (!beskrivningKontroll()) {  
        totOk = false;
        lblBeskrivningBad.setVisible(true);
    } else if (!adressKontroll(idb)) {  
        totOk = false;
        lblAdressBad.setVisible(true);
    } else if (!telefonKontroll(idb)) {  
        totOk = false;
        lblTelefonBad.setVisible(true);
    } else if (!epostKontroll(idb)) {  
        totOk = false;
        lblEpostBad.setVisible(true);
    }else if(!sammaEpostKontroll()){
        totOk=false;
    }else if(!sammaTelefonKontroll()){
        totOk=false;
    }


    kontrollOk = totOk;
}

    /**
     *  kontrollerar adress för att se att det är valid
     * @param idb initierar fält för att interagera med databasen
     */
    
    public boolean adressKontroll(InfDB idb) {
        Validering valid = new Validering(idb);
        String adress = tfAdress.getText();
        if (valid.checkAdress(adress)&& valid.checkStorlek(255, adress)) {
            lblAdressBad.setVisible(false);
            return true;
        } else {
            lblAdressBad.setVisible(true);
            return false;
        }}
    
    /**
     *  kontrollerar telefon nummer för att se att det är valid
     * @param idb initierar fält för att interagera med databasen
     */
    
    public boolean telefonKontroll(InfDB idb) {
        Validering valid = new Validering(idb);
        String telefon = tfTelefon.getText();
    if (valid.checkTelefon(telefon)&& valid.checkStorlek(20, telefon)) {
            lblTelefonBad.setVisible(false);
            return true;
    } else {
            lblTelefonBad.setVisible(true);
            return false;
    }
    }
    
    /**
     *  kontrollerar eposten för att se att det är valid
     * @param idb initierar fält för att interagera med databasen
     */
    
    public boolean epostKontroll(InfDB idb){
    Validering valid = new Validering(idb); 
    
    String epost = tfEpost.getText(); 

    if (valid.checkEpost(epost)&& valid.checkStorlek(255, epost)) {
        lblEpostBad.setVisible(false); // Göm varning
        return true;
    } else {
        lblEpostBad.setVisible(true); // Visa varning
        return false;
        
    }}
    
    /**
     * Anropar kontroll av att ny telefonNr inte är som någon annans
     * Ger false om valideringen visar att fel uppstått
     * 
     * @param idb databasen som används för validering
     */
    private boolean sammaTelefonKontroll(){
        Validering valid = new Validering(idb); 
    
    // Hämta text från textfältet
    String telefon = tfTelefon.getText(); 
    if(valid.checkInteSammaTelefon(telefon)){
      lblTelefonBad.setVisible(false); // Göm varning
        return true;
    } else {
        lblTelefonBad.setVisible(true); // Visa varning
        return false;
        
    }
    }
    
    /**
     * Anropar kontroll av att ny epost inte är som någon annans
     * Ger false om valideringen visar att fel uppstått
     * 
     * @param idb databasen som används för validering
     */
    private boolean sammaEpostKontroll(){
        Validering valid = new Validering(idb); 
    
    // Hämta text från textfältet
    String epost = tfEpost.getText(); 
    if(valid.checkInteSammaEpost(epost)){
      lblEpostBad.setVisible(false); // Göm varning
        return true;
    } else {
        lblEpostBad.setVisible(true); // Visa varning
        return false;
        
    }
    }
    
    /**
     *  kontrollerar namn för att se att det är valid
     */
    
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
    
    /**
     *  kontrollerar beskrivningen för att se att det är valid
     */
    
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
   
    /**
     *  Ger anställds ID till den valda projektchefen
     */
    
   private String getPChef(){
        String selectedPerson = (String) cbChef.getSelectedItem();
        String aid = " ";
        for(String id : anstalldLista.keySet()){
            String namn = anstalldLista.get(id);
            if(selectedPerson != null && selectedPerson.equals(namn)){
                aid = id;               
            }
        } 
        return aid;
    }
   
    /**
     *  Ger stads ID
     */
   
   private String getStad(){
       String namn= (String) cbStad.getSelectedItem();
        String sid = " ";
        for(String id : stadLista.keySet()){
            String ssStad = stadLista.get(id);
            if(namn != null && namn.equals(ssStad)){
                sid = id;               
            }
        } 
        return sid;
   }
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        lblNamn = new javax.swing.JLabel();
        lblBeskrivning = new javax.swing.JLabel();
        lblStad = new javax.swing.JLabel();
        lblChef = new javax.swing.JLabel();
        tfNamn = new javax.swing.JTextField();
        tfBeskrivning = new javax.swing.JTextField();
        cbStad = new javax.swing.JComboBox<>();
        cbChef = new javax.swing.JComboBox<>();
        btnSkapa = new javax.swing.JButton();
        lblError = new javax.swing.JLabel();
        lblSkapad = new javax.swing.JLabel();
        lblNamnBad = new javax.swing.JLabel();
        lblBeskrivningBad = new javax.swing.JLabel();
        lblAdressBad = new javax.swing.JLabel();
        lblEpostBad = new javax.swing.JLabel();
        lblTelefonBad = new javax.swing.JLabel();
        lblEpost = new javax.swing.JLabel();
        lblAdress = new javax.swing.JLabel();
        tfAdress = new javax.swing.JTextField();
        tfEpost = new javax.swing.JTextField();
        tfTelefon = new javax.swing.JTextField();
        lblTelefon = new javax.swing.JLabel();
        btnTillbaka = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Lägg till avdelning");

        lblNamn.setText("Namn");

        lblBeskrivning.setText("Beskrivning");

        lblStad.setText("Stad");

        lblChef.setText("Chef");

        tfNamn.setText("Namn");

        tfBeskrivning.setText("Beskrivning");

        cbStad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {  }));

        cbChef.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {  }));

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

        lblNamnBad.setForeground(new java.awt.Color(255, 0, 51));
        lblNamnBad.setText("!");

        lblBeskrivningBad.setForeground(new java.awt.Color(255, 0, 51));
        lblBeskrivningBad.setText("!");

        lblAdressBad.setForeground(new java.awt.Color(255, 0, 51));
        lblAdressBad.setText("!");

        lblEpostBad.setForeground(new java.awt.Color(255, 0, 51));
        lblEpostBad.setText("!");

        lblTelefonBad.setForeground(new java.awt.Color(255, 0, 51));
        lblTelefonBad.setText("!");

        lblEpost.setText("Epost");

        lblAdress.setText("Adress");

        tfAdress.setText("123 blabla, bobla");

        tfEpost.setText("example@mail.com");
        tfEpost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfEpostActionPerformed(evt);
            }
        });

        tfTelefon.setText("123-123-1234");
        tfTelefon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfTelefonActionPerformed(evt);
            }
        });

        lblTelefon.setText("Telefon");

        btnTillbaka.setText("X");
        btnTillbaka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTillbakaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEpost)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblChef)
                                .addGap(70, 70, 70)
                                .addComponent(cbChef, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblAdress)
                                .addGap(56, 56, 56)
                                .addComponent(tfAdress, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblAdressBad))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblBeskrivning)
                                    .addComponent(lblNamn))
                                .addGap(29, 29, 29)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfBeskrivning, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfNamn, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNamnBad)
                                    .addComponent(lblBeskrivningBad)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnSkapa)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(97, 97, 97)
                                        .addComponent(lblSkapad)))
                                .addGap(18, 18, 18)
                                .addComponent(lblError))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblStad)
                                .addGap(70, 70, 70)
                                .addComponent(cbStad, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTelefon)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(97, 97, 97)
                                        .addComponent(tfTelefon, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblTelefonBad))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(97, 97, 97)
                                .addComponent(tfEpost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblEpostBad)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnTillbaka)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(btnTillbaka))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNamn)
                    .addComponent(tfNamn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNamnBad))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBeskrivning)
                    .addComponent(tfBeskrivning, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBeskrivningBad))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfAdress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblAdressBad))
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEpostBad)
                            .addComponent(tfEpost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEpost))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTelefonBad)
                            .addComponent(tfTelefon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTelefon)))
                    .addComponent(lblAdress))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbStad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStad))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblChef)
                    .addComponent(cbChef, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSkapa)
                    .addComponent(lblSkapad)
                    .addComponent(lblError))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSkapaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSkapaActionPerformed
        totalKontroll();
        int hogsta=hogstaAvdid();
        String sqlFraga="INSERT INTO avdelning VALUES(" + hogstaAvdid() + ", '" + tfNamn.getText() + "', '" + tfBeskrivning.getText()
                + "', '" + tfAdress.getText() + "', '" + tfEpost.getText() + "', '" + tfTelefon.getText()
                + "', " + getStad() + ", " + getPChef() + ")";
        System.out.println(sqlFraga);
        if(kontrollOk==true){
            try{

                idb.insert(sqlFraga);
                System.out.println(sqlFraga);

            }catch(Exception ex){
                System.out.println(ex.getMessage());
            }
            lblError.setVisible(false);
            lblSkapad.setVisible(true);
        }
        else{
            lblError.setVisible(true);
        }
    }//GEN-LAST:event_btnSkapaActionPerformed

    private void tfEpostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfEpostActionPerformed

    }//GEN-LAST:event_tfEpostActionPerformed

    private void tfTelefonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfTelefonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfTelefonActionPerformed

    private void btnTillbakaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTillbakaActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnTillbakaActionPerformed

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
            java.util.logging.Logger.getLogger(LaggTillAvdelning.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LaggTillAvdelning.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LaggTillAvdelning.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LaggTillAvdelning.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new LaggTillAvdelning().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSkapa;
    private javax.swing.JButton btnTillbaka;
    private javax.swing.JComboBox<String> cbChef;
    private javax.swing.JComboBox<String> cbStad;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblAdress;
    private javax.swing.JLabel lblAdressBad;
    private javax.swing.JLabel lblBeskrivning;
    private javax.swing.JLabel lblBeskrivningBad;
    private javax.swing.JLabel lblChef;
    private javax.swing.JLabel lblEpost;
    private javax.swing.JLabel lblEpostBad;
    private javax.swing.JLabel lblError;
    private javax.swing.JLabel lblNamn;
    private javax.swing.JLabel lblNamnBad;
    private javax.swing.JLabel lblSkapad;
    private javax.swing.JLabel lblStad;
    private javax.swing.JLabel lblTelefon;
    private javax.swing.JLabel lblTelefonBad;
    private javax.swing.JTextField tfAdress;
    private javax.swing.JTextField tfBeskrivning;
    private javax.swing.JTextField tfEpost;
    private javax.swing.JTextField tfNamn;
    private javax.swing.JTextField tfTelefon;
    // End of variables declaration//GEN-END:variables
}
