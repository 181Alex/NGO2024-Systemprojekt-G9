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
public class LaggTillLand extends javax.swing.JFrame {
    private InfDB idb;
    private String inloggadAnvandare;  
    boolean kontrollOk;
    /**
     * Creates new form LaggTillLand
     */
    public LaggTillLand(InfDB idb, String inloggadAnvandare) {
        this.idb=idb;
        this.inloggadAnvandare=inloggadAnvandare;
        initComponents();
        kontrollOk=false;
    }
    
    public void totalKontroll(InfDB idb) {
    Boolean totOk = true;

    if (!valutaKontroll()) {
        totOk = false;
    } else if (!namnKontroll()) {
        totOk = false;
    } else if (!sprakKontroll()) {
        totOk = false;
    } else if (!politikKontroll()) {
        totOk = false;
    } else if (!ekonomiKontroll()) {
        totOk = false;
    } else if (!tidzonKontroll()) {
        totOk = false;
    }else if (sammaNamnKontroll()) {
        totOk = false;
    }

    kontrollOk = totOk;
}
    
    
    
    public int hogstaLid(){
        // hämtar ut högsta lid
        int hogsta=0;
        String sqlFraga="Select MAX(lid) FROM Land";
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
    
    public boolean valutaKontroll(){
        Validering valid = new Validering(idb);
        String valuta = tfValuta.getText();
        // kotnroll av valuta så att endast nummer och komma skrivs
    if (valid.checkTelefon(valuta)) {
            lblValutaBad.setVisible(false);
            return true;
    } else {
            lblValutaBad.setVisible(true);
            return false;
    }
    }
    
    
    
    public double getValuta(){
        String skriv=tfValuta.getText();
        double returer=0;
            if(valutaKontroll()){
            returer=Double.parseDouble(skriv);}
        return returer;
    }
    
    
    public boolean namnKontroll(){
        Validering valid = new Validering(idb);
        String namn = tfNamn.getText();
        // samma som alla andra kontroller men använder förnamns valideringen då de gör samma sak
    if (valid.checkFornamn(namn)) {
            lblNamnBad.setVisible(false);
            return true;
    } else {
            lblNamnBad.setVisible(true);
            return false;
    }
    }
    
    public boolean sammaNamnKontroll(){
        boolean samma=false;
        ArrayList<String> namnLista=new ArrayList<>();
        String sqlFraga="SELECT namn FROM land";
        try{
            namnLista=idb.fetchColumn(sqlFraga);
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        
        for(String namn:namnLista){
            if(namn.equals(tfNamn.getText())){
                samma=true;
            }
        }
        if (samma==true){
            lblNamnBad.setVisible(true);
        }
        return samma;
    }
    
    
    
    public boolean sprakKontroll(){
        Validering valid = new Validering(idb);
        String sprak = tfSprak.getText();
        // samma som alla andra kontroller men använder förnamns valideringen då de gör samma sak
    if (valid.checkFornamn(sprak)) {
            lblSprakBad.setVisible(false);
            return true;
    } else {
            lblSprakBad.setVisible(true);
            return false;
    }
    }
    
    public boolean politikKontroll(){
        Validering valid = new Validering(idb);
        String politik = tfPolitik.getText();
        // liknande politik 1, en mening sedan siffra
    if (valid.checkMeningOSiffra(politik)) {
            lblPolitikBad.setVisible(false);
            return true;
    } else {
            lblPolitikBad.setVisible(true);
            return false;
    }
    }
    
    public boolean ekonomiKontroll(){
        Validering valid = new Validering(idb);
        String ekonomi = tfEkonomi.getText();
        // liknande ekonomi 1 dvs en mening sedan en siffra
    if (valid.checkMeningOSiffra(ekonomi)) {
            lblEkonomiBad.setVisible(false);
            return true;
    } else {
            lblEkonomiBad.setVisible(true);
            return false;
    }
    }
    
    public boolean tidzonKontroll(){
        Validering valid = new Validering(idb);
        String tidzon = tfTidzon.getText();
        // liknande ekonomi 1 dvs en mening sedan en siffra
    if (valid.checkMeningOSiffra(tidzon)) {
            lblTidzonBad.setVisible(false);
            return true;
    } else {
            lblTidzonBad.setVisible(true);
            return false;
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

        jLabel1 = new javax.swing.JLabel();
        btnTillbaka = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnAndraStader = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        tfNamn = new javax.swing.JTextField();
        tfSprak = new javax.swing.JTextField();
        tfValuta = new javax.swing.JTextField();
        tfTidzon = new javax.swing.JTextField();
        tfPolitik = new javax.swing.JTextField();
        tfEkonomi = new javax.swing.JTextField();
        btnSkapa = new javax.swing.JButton();
        lblNamnBad = new javax.swing.JLabel();
        lblSprakBad = new javax.swing.JLabel();
        lblValutaBad = new javax.swing.JLabel();
        lblTidzonBad = new javax.swing.JLabel();
        lblPolitikBad = new javax.swing.JLabel();
        lblEkonomiBad = new javax.swing.JLabel();
        lblSkapad = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        lblError = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Namn");

        btnTillbaka.setText("X");
        btnTillbaka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTillbakaActionPerformed(evt);
            }
        });

        jLabel2.setText("Språk");

        jLabel3.setText("Valuta");

        jLabel4.setText("Tidzon");

        jLabel5.setText("Politisk Struktur");

        jLabel6.setText("Ekonomi");

        btnAndraStader.setText("Ändra städer");

        jLabel7.setText("Lägg till Länder");

        tfNamn.setText("Namn");
        tfNamn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfNamnActionPerformed(evt);
            }
        });

        tfSprak.setText("Språk");

        tfValuta.setText("Valuta");

        tfTidzon.setText("Tidzon");

        tfPolitik.setText("Politisk Struktur");

        tfEkonomi.setText("Ekonomi");

        btnSkapa.setText("Skapa");
        btnSkapa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSkapaActionPerformed(evt);
            }
        });

        lblNamnBad.setForeground(new java.awt.Color(255, 0, 51));
        lblNamnBad.setText("!");

        lblSprakBad.setForeground(new java.awt.Color(255, 0, 51));
        lblSprakBad.setText("!");

        lblValutaBad.setForeground(new java.awt.Color(255, 0, 51));
        lblValutaBad.setText("!");

        lblTidzonBad.setForeground(new java.awt.Color(255, 0, 51));
        lblTidzonBad.setText("!");

        lblPolitikBad.setForeground(new java.awt.Color(255, 0, 51));
        lblPolitikBad.setText("!");

        lblEkonomiBad.setForeground(new java.awt.Color(255, 0, 51));
        lblEkonomiBad.setText("!");

        lblSkapad.setForeground(new java.awt.Color(0, 204, 51));
        lblSkapad.setText("Skapad");

        jTextField1.setText("jTextField1");

        lblError.setForeground(new java.awt.Color(255, 0, 51));
        lblError.setText("Error!");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(btnSkapa))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 104, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnTillbaka, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblError))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnAndraStader))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfNamn))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblSkapad)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(tfEkonomi)
                                        .addComponent(tfPolitik, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                                        .addComponent(tfTidzon)
                                        .addComponent(tfValuta)
                                        .addComponent(tfSprak)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNamnBad)
                            .addComponent(lblSprakBad)
                            .addComponent(lblValutaBad)
                            .addComponent(lblTidzonBad)
                            .addComponent(lblPolitikBad)
                            .addComponent(lblEkonomiBad))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTillbaka)
                    .addComponent(jLabel7))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(tfNamn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNamnBad))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfSprak, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSprakBad))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(tfValuta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblValutaBad))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(tfTidzon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTidzonBad))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(tfPolitik, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPolitikBad))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(tfEkonomi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEkonomiBad))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAndraStader)
                            .addComponent(btnSkapa)
                            .addComponent(lblSkapad)
                            .addComponent(lblError))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTillbakaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTillbakaActionPerformed
        new AdminMeny(idb, inloggadAnvandare).setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnTillbakaActionPerformed

    private void tfNamnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfNamnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfNamnActionPerformed

    private void btnSkapaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSkapaActionPerformed
        totalKontroll(idb);
        String sqlFraga="INSERT INTO land VALUES(" + hogstaLid() + ",'" + tfNamn.getText() + "','"
               + tfSprak.getText() + "'," + getValuta() + ", '" + tfTidzon.getText() + "','" 
               + tfPolitik.getText() + "','" + tfEkonomi.getText() + "')";
       
    }//GEN-LAST:event_btnSkapaActionPerformed

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
            java.util.logging.Logger.getLogger(LaggTillLand.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LaggTillLand.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LaggTillLand.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LaggTillLand.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               // new LaggTillLand().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAndraStader;
    private javax.swing.JButton btnSkapa;
    private javax.swing.JButton btnTillbaka;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblEkonomiBad;
    private javax.swing.JLabel lblError;
    private javax.swing.JLabel lblNamnBad;
    private javax.swing.JLabel lblPolitikBad;
    private javax.swing.JLabel lblSkapad;
    private javax.swing.JLabel lblSprakBad;
    private javax.swing.JLabel lblTidzonBad;
    private javax.swing.JLabel lblValutaBad;
    private javax.swing.JTextField tfEkonomi;
    private javax.swing.JTextField tfNamn;
    private javax.swing.JTextField tfPolitik;
    private javax.swing.JTextField tfSprak;
    private javax.swing.JTextField tfTidzon;
    private javax.swing.JTextField tfValuta;
    // End of variables declaration//GEN-END:variables
}
