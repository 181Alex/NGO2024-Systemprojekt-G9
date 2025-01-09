/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ngo2024.Admin;

import ngo2024.Validering;
import oru.inf.InfDB;
import oru.inf.InfException;
import java.util.ArrayList;

/**
 *
 * @author alexanderabboud
 */
public class StadForandring extends javax.swing.JFrame {
    private InfDB idb;
    private String epost;  
    boolean kontrollOk;
    
     /**
     * Initierar StadForandring objekt 
     * administratör kan lägga till och ändra information om städer
     *
     * @param idb initierar fält för att interagera med databasen
     * @param epost eposten för den inloggade användaren
     */

    public StadForandring(InfDB idb, String epost) {
        this.idb=idb;
        this.epost=epost;
        initComponents();
        fyllValjLand();
        fyllValjStad();
        gomAlla();
        kontrollOk=true;
    }
    
    /**
     * fyller i combo box med namn på städer
     */
    
    public void fyllValjStad(){
        cbValjStad.removeAllItems();
        String sqlFraga="SELECT namn FROM stad";
        
        ArrayList<String> namnLista=new ArrayList<>();
        
        try{
            namnLista=idb.fetchColumn(sqlFraga);
            
            for(String namn:namnLista){
                cbValjStad.addItem(namn);
            }
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    
    /**
     * fyller combo box med namn på länder
     */

    public void fyllValjLand(){
        cbValjLand.removeAllItems();
        String sqlFraga="SELECT namn FROM land";
        
        ArrayList<String> namnLista=new ArrayList<>();
        
        try{
            namnLista=idb.fetchColumn(sqlFraga);
            
            for(String namn:namnLista){
                cbValjLand.addItem(namn);
            }
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    
    /**
     * kontrollerar  so att namn på städer är valid
     */

    public boolean kontrollStadText(){
         Validering valid = new Validering(idb);
        String namn = tfNamn.getText();
    if (valid.checkStad(namn)&& valid.checkStorlek(255, namn)) {
            lblNamnBad.setVisible(false);
            return true;
    } else {
            lblNamnBad.setVisible(true);
            return false;
    }
    }
    
    /**
     * hämtar namn på ett land
     */
    
    public String selectLand(){
      String sqlFraga="SELECT namn FROM land where lid= (Select land from stad where namn='" 
              + cbValjStad.getSelectedItem() + "')";
      String namn=" ";
      try{
          namn=idb.fetchSingle(sqlFraga);
          System.out.println(sqlFraga);
      }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
      
      return namn;
      
    }
    
    /**
     * kontrollerar allt
     */
    
    public void totalKontroll() {
    Boolean totOk = true;
    
     if(!kontrollStadText()){
        totOk=false;
        }
    kontrollOk=totOk;
    }
    
    /**
     * hämtar ut land ID
     */

    public int selectLid(){
        String slid="111";
        try{
            String sqlFraga="SELECT lid FROM land where namn='" + cbValjLand.getSelectedItem() + "'";
            slid=idb.fetchSingle(sqlFraga);
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        int lid=Integer.parseInt(slid);
        return lid;
        }
        
    /**
     * hämtar högsta land id
     */

    public int hogstaSid(){
        int hogsta=0;
        String sqlFraga="Select MAX(sid) FROM stad";
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
     * hämtar ut stads ID
     */

    public int selectSid(){
        String sSid="111";
        try{
            String sqlFraga="SELECT sid FROM stad where namn='" + cbValjStad.getSelectedItem() + "'";
            sSid=idb.fetchSingle(sqlFraga);
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        int sid=Integer.parseInt(sSid);
        return sid;
        }
    
    /**
     * gömmer allt
     */

    public void gomAlla(){
        btnAndra.setVisible(false);
        btnTaBort.setVisible(false);
        btnLaggTill.setVisible(false);
        lblStadNamn.setVisible(false);
        lblLand.setVisible(false);
        cbValjLand.setVisible(false);
        tfNamn.setVisible(false);
        cbValjStad.setVisible(false);
        lblNamnBad.setVisible(false);
        lblError.setVisible(false);
        lblLyckades.setVisible(false);
        lblNuLand.setVisible(false);
    }
    
    public void visaLaggTill(){
        if(cbhLaggTill.isSelected()){
            cbhTaBort.setSelected(false);
            cbhAndra.setSelected(false);
            lblStadNamn.setVisible(true);
            lblLand.setVisible(true);
            cbValjLand.setVisible(true);
            tfNamn.setVisible(true);
            btnAndra.setVisible(false);
            btnTaBort.setVisible(false);
            btnLaggTill.setVisible(true);
            cbValjStad.setVisible(false);
            btnValj.setVisible(false);
            lblNuLand.setVisible(false);
        }
    }
    
    /**
     * visa ändra
     */

    public void visaAndra(){
        if(cbhAndra.isSelected()){
            cbhTaBort.setSelected(false);
            cbhLaggTill.setSelected(false);
            cbValjStad.setVisible(true);
            lblStadNamn.setVisible(true);
            lblLand.setVisible(true);
            lblNuLand.setVisible(true);
            cbValjLand.setVisible(false);
            tfNamn.setVisible(true);
            btnAndra.setVisible(true);
            btnTaBort.setVisible(false);
            btnLaggTill.setVisible(false);
            btnValj.setVisible(true);
        }
    }
    
    /**
     * Visar allt för att ta bort
     */

    public void visaTaBort(){
        if(cbhTaBort.isSelected()){
            cbhAndra.setSelected(false);
            cbhLaggTill.setSelected(false);
            cbValjStad.setVisible(true);
            lblStadNamn.setVisible(false);
            lblLand.setVisible(true);
            cbValjLand.setVisible(false);
            tfNamn.setVisible(false);
            btnAndra.setVisible(false);
            btnTaBort.setVisible(true);
            btnLaggTill.setVisible(false);
            lblNuLand.setVisible(true);
            
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

        cbhLaggTill = new javax.swing.JCheckBox();
        cbhAndra = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        cbhTaBort = new javax.swing.JCheckBox();
        btnTaBort = new javax.swing.JButton();
        cbValjStad = new javax.swing.JComboBox<>();
        lblStadNamn = new javax.swing.JLabel();
        lblLand = new javax.swing.JLabel();
        cbValjLand = new javax.swing.JComboBox<>();
        tfNamn = new javax.swing.JTextField();
        btnAndra = new javax.swing.JButton();
        btnLaggTill = new javax.swing.JButton();
        btnTillbaka = new javax.swing.JButton();
        lblNamnBad = new javax.swing.JLabel();
        lblLyckades = new javax.swing.JLabel();
        lblError = new javax.swing.JLabel();
        btnValj = new javax.swing.JButton();
        lblNuLand = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        cbhLaggTill.setText("Lägg till");
        cbhLaggTill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbhLaggTillActionPerformed(evt);
            }
        });

        cbhAndra.setText("Ändra");
        cbhAndra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbhAndraActionPerformed(evt);
            }
        });

        jLabel1.setText("Städer");

        cbhTaBort.setText("Ta bort");
        cbhTaBort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbhTaBortActionPerformed(evt);
            }
        });

        btnTaBort.setText("Ta bort");
        btnTaBort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaBortActionPerformed(evt);
            }
        });

        cbValjStad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {}));

        lblStadNamn.setText("Stad");

        lblLand.setText("Land");

        cbValjLand.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { }));

        tfNamn.setText("Namn");

        btnAndra.setText("Ändra");
        btnAndra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAndraActionPerformed(evt);
            }
        });

        btnLaggTill.setText("Lägg till");
        btnLaggTill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLaggTillActionPerformed(evt);
            }
        });

        btnTillbaka.setText("X");
        btnTillbaka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTillbakaActionPerformed(evt);
            }
        });

        lblNamnBad.setForeground(new java.awt.Color(255, 0, 51));
        lblNamnBad.setText("!");

        lblLyckades.setForeground(new java.awt.Color(0, 204, 0));
        lblLyckades.setText("Lyckades");

        lblError.setForeground(new java.awt.Color(255, 51, 0));
        lblError.setText("Error!");

        btnValj.setText("Välj");
        btnValj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnValjActionPerformed(evt);
            }
        });

        lblNuLand.setText("Land");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cbValjStad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnValj))
                            .addComponent(btnLaggTill))
                        .addGap(180, 299, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(cbhLaggTill)
                        .addGap(18, 18, 18)
                        .addComponent(cbhAndra)
                        .addGap(18, 18, 18)
                        .addComponent(cbhTaBort)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnTillbaka)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblLand)
                                    .addComponent(lblStadNamn))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblNuLand)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbValjLand, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(tfNamn, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblNamnBad))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnTaBort)
                                .addGap(18, 18, 18)
                                .addComponent(btnAndra))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblLyckades)
                                .addGap(47, 47, 47)
                                .addComponent(lblError)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbhLaggTill)
                    .addComponent(cbhAndra)
                    .addComponent(cbhTaBort)
                    .addComponent(btnTillbaka))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbValjStad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnValj))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStadNamn)
                    .addComponent(tfNamn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNamnBad))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLand)
                    .addComponent(cbValjLand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNuLand))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTaBort, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAndra))
                .addGap(18, 18, 18)
                .addComponent(btnLaggTill)
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLyckades)
                    .addComponent(lblError))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbhLaggTillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbhLaggTillActionPerformed
        visaLaggTill();
        lblLyckades.setVisible(false);
        lblError.setVisible(false);
    }//GEN-LAST:event_cbhLaggTillActionPerformed

    private void cbhAndraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbhAndraActionPerformed
        visaAndra();
        lblNuLand.setVisible(false);
        lblLyckades.setVisible(false);
        lblError.setVisible(false);
        fyllValjStad();
    }//GEN-LAST:event_cbhAndraActionPerformed

    private void cbhTaBortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbhTaBortActionPerformed
        visaTaBort();
        lblNuLand.setVisible(false);
        lblLyckades.setVisible(false);
        lblError.setVisible(false);
        fyllValjStad();
    }//GEN-LAST:event_cbhTaBortActionPerformed

    private void btnTillbakaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTillbakaActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnTillbakaActionPerformed

    private void btnLaggTillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLaggTillActionPerformed
       String sqlFraga="INSERT INTO stad VALUES(" + hogstaSid() + ", '" + tfNamn.getText() + "', " + selectLid() + ")";
        //Lägger till staden
       if(kontrollOk){
            try{
          System.out.println(sqlFraga);;
          idb.insert(sqlFraga);
            }
            catch(Exception ex){
            System.out.println(ex.getMessage());
        }
          lblLyckades.setVisible(true);
          lblError.setVisible(false);
       }
       else{
           lblLyckades.setVisible(false);
          lblError.setVisible(true);
       }
    }//GEN-LAST:event_btnLaggTillActionPerformed

    private void btnAndraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAndraActionPerformed
        String sqlFraga="UPDATE stad SET namn= '"+ tfNamn.getText() + "' WHERE sid= " + selectSid() ;
        //uppdaterar staden
       if(kontrollOk){
            try{
          System.out.println(sqlFraga);;
          idb.update(sqlFraga);
            }
            catch(Exception ex){
            System.out.println(ex.getMessage());
        }
          lblLyckades.setVisible(true);
          lblError.setVisible(false);
       }
       else{
           lblLyckades.setVisible(false);
          lblError.setVisible(true);
       }
    }//GEN-LAST:event_btnAndraActionPerformed

    private void btnTaBortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaBortActionPerformed
     String sqlFraga="DELETE FROM stad WHERE sid=" + selectSid();
    

        //tar bort den valda staden
       if(kontrollOk){
            try{
          System.out.println(sqlFraga);;
          idb.delete(sqlFraga);
            }
            catch(Exception ex){
            System.out.println(ex.getMessage());
        }
          lblLyckades.setVisible(true);
          lblError.setVisible(false);
       }
       else{
           lblLyckades.setVisible(false);
          lblError.setVisible(true);
       }
    }//GEN-LAST:event_btnTaBortActionPerformed

    private void btnValjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnValjActionPerformed

       // fyller ut vilket land det är
       if(cbhAndra.isSelected()){
       int i=cbValjStad.getSelectedIndex();
       tfNamn.setText(cbValjStad.getItemAt(i));
       lblNuLand.setVisible(true);
       lblNuLand.setText(selectLand());
       }
       else if(cbhTaBort.isSelected()){
        int i=cbValjStad.getSelectedIndex();
        tfNamn.setText(cbValjStad.getItemAt(i));
        lblNuLand.setVisible(true);
        lblNuLand.setText(selectLand());
       }
    }//GEN-LAST:event_btnValjActionPerformed

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
            java.util.logging.Logger.getLogger(StadForandring.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StadForandring.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StadForandring.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StadForandring.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               // new StadForandring().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAndra;
    private javax.swing.JButton btnLaggTill;
    private javax.swing.JButton btnTaBort;
    private javax.swing.JButton btnTillbaka;
    private javax.swing.JButton btnValj;
    private javax.swing.JComboBox<String> cbValjLand;
    private javax.swing.JComboBox<String> cbValjStad;
    private javax.swing.JCheckBox cbhAndra;
    private javax.swing.JCheckBox cbhLaggTill;
    private javax.swing.JCheckBox cbhTaBort;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblError;
    private javax.swing.JLabel lblLand;
    private javax.swing.JLabel lblLyckades;
    private javax.swing.JLabel lblNamnBad;
    private javax.swing.JLabel lblNuLand;
    private javax.swing.JLabel lblStadNamn;
    private javax.swing.JTextField tfNamn;
    // End of variables declaration//GEN-END:variables
}
