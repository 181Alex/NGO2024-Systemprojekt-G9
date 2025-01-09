/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ngo2024.Admin;

import oru.inf.InfDB;
import oru.inf.InfException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author linneagottling
 */
public class ProjektHandlaggare extends javax.swing.JFrame {   
    
    private InfDB idb;
    private String pid;
    private HashMap<String, String> handlaggarLista;

    /**
     * Initierar ProjektHandlaggare objekt 
     * låter administratören lägga till och ta bort handläggare för ett projekt
     *
     * @param idb initierar fält för att interagera med databasen
     * @param pid projekt ID
     */
    
    public ProjektHandlaggare(InfDB idb, String pid) {
        this.idb = idb;
        this.pid = pid;
        initComponents();
        lblPid.setText(pid);
        setProjektnamn(pid);
        handlaggarLista = new HashMap<>();
        lblMeddelande.setVisible(false);
        btTaBort.setVisible(false);
        btLaggTill.setVisible(false);
        cbHandlaggare.removeAllItems();
        lblFelmeddelande.setVisible(false);
        fyllHashMap();
    }
    
    /**
     * fyller i Hash Map med alla namn och anställd ID för alla handläggare
     */
    
        private void fyllHashMap(){
            
            try{
             String sqlFraga = "SELECT CONCAT(fornamn, ' ', efternamn) as namn"
                     + " FROM anstalld WHERE aid IN(SELECT aid FROM handlaggare)";

            ArrayList<String> allaHandlaggare = idb.fetchColumn(sqlFraga);

            //fyller i alla mål
            for (String hNamn : allaHandlaggare) {
                String sqlAid = "SELECT aid FROM anstalld WHERE "
                        + "CONCAT(fornamn, ' ', efternamn) = '" + hNamn + "'";
                String aid = idb.fetchSingle(sqlAid);
                handlaggarLista.put(aid, hNamn);
            }
            } catch (InfException ex) {
                System.out.println(ex.getMessage());
            }
        }
        
     /**
     * ger projektnamn på ett visst projekt namn
     * @param pid projekt ID
     */

        private void setProjektnamn(String pid) {
        try {
            String sqlFraga = "SELECT projektnamn FROM projekt WHERE pid =" + pid;
            String projektnamn = idb.fetchSingle(sqlFraga);
            lblProjektnamn.setText(projektnamn);

        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
     /**
     * fyller i combo box med alla namn och anställd ID för alla handläggare
     */   
  
            private void fyllCbLaggTill() {
        cbHandlaggare.removeAllItems();

        try {
            String sqlFraga = "SELECT CONCAT(fornamn, ' ', efternamn) as namn"
                     + " FROM anstalld WHERE aid IN (SELECT aid FROM handlaggare)";

            ArrayList<String> allaHandlaggare = idb.fetchColumn(sqlFraga);

            //fyller i alla mål
            for (String hNamn : allaHandlaggare) {
                cbHandlaggare.addItem(hNamn);
            }

        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    /**
     * ???
     */
            
                private void fyllCbTaBort() {
        cbHandlaggare.removeAllItems();

        try {
            String sqlFraga = "SELECT CONCAT(fornamn, ' ', efternamn) FROM anstalld "
                    + "JOIN ans_proj ON anstalld.aid = ans_proj.aid "
                    + "WHERE pid =" + pid;

            ArrayList<String> handlaggare = idb.fetchColumn(sqlFraga);

            for (String hNamn : handlaggare) {
                cbHandlaggare.addItem(hNamn);
            }

        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
    }
                
    /**
     * ger valda anställd ID???
     */
                
private String getSelectedAid() {
        String selectedPerson = (String) cbHandlaggare.getSelectedItem();
        String hid = " ";
        for (String id : handlaggarLista.keySet()) {
            String namn = handlaggarLista.get(id);
            if (selectedPerson != null && selectedPerson.equals(namn)) {
                hid = id;
            }
        }
        return hid;
    }

    /**
     * lägg till handläggare till ett projekt
     */
            
    private void laggTill(){
        String aid = getSelectedAid();
        String sPid = lblPid.getText();
        
        String sqlLaggTill = "INSERT INTO ans_proj VALUES( " + sPid
                + ", " + aid + ")";
        
        try {
            if(kontrollInteSamma(aid)){
            idb.insert(sqlLaggTill);
            lblFelmeddelande.setVisible(false);
            lblMeddelande.setText("Tillagd");
            lblMeddelande.setVisible(true);
            } else {
                lblFelmeddelande.setText("Denna handläggare finns redan för detta projekt");
                lblFelmeddelande.setVisible(true);
                lblMeddelande.setVisible(false);
            }
            
            
        } catch (InfException ex){
            System.out.println(ex.getMessage());
        }     
        
    }
    
    /**
     * tar bort handläggare från ett projekt
     */
    
        private void taBort(){
        String aid = getSelectedAid();
        String sPid = lblPid.getText();
        
        String sqlTaBort = "DELETE FROM ans_proj WHERE pid = " + sPid + " AND aid = " + aid;
        
        try{
            idb.delete(sqlTaBort);
            lblMeddelande.setText("Borttagen från projekt");
            lblMeddelande.setVisible(true);       
            
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
        
    }
       
    /**
     * kontrollerar så att det finns inga dubletter av anställnings ID finns
     */
            
    private boolean kontrollInteSamma(String aid){
        boolean finnsEj = true;
        String sPid = lblPid.getText();
        
        String sqlFraga = "SELECT COUNT(*) AS Antal "
                + "FROM ans_proj "
                + "WHERE pid = " + sPid + " AND aid = " + aid;
        
        try{
           String sqlAntal = idb.fetchSingle(sqlFraga);
           int antal = Integer.parseInt(sqlAntal);
           
           if(antal> 0){
               finnsEj = false;
           }

        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
        return finnsEj;
    }
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblFelmeddelande = new javax.swing.JLabel();
        lblPid = new javax.swing.JLabel();
        chLaggTill = new javax.swing.JCheckBox();
        lblRubrik = new javax.swing.JLabel();
        chTaBort = new javax.swing.JCheckBox();
        cbHandlaggare = new javax.swing.JComboBox<>();
        lblAid = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btLaggTill = new javax.swing.JButton();
        btTaBort = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        lblMeddelande = new javax.swing.JLabel();
        lblProjektnamn = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblFelmeddelande.setForeground(new java.awt.Color(255, 0, 0));
        lblFelmeddelande.setText("Felmeddelande");

        lblPid.setForeground(new java.awt.Color(102, 102, 102));
        lblPid.setText("Pid");

        chLaggTill.setText("Lägg till");
        chLaggTill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chLaggTillActionPerformed(evt);
            }
        });

        lblRubrik.setText("Lägg till/ Ta bort handläggare för projekt:");

        chTaBort.setText("Ta bort");
        chTaBort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chTaBortActionPerformed(evt);
            }
        });

        cbHandlaggare.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbHandlaggare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbHandlaggareActionPerformed(evt);
            }
        });

        lblAid.setText("aid");

        jLabel2.setText("Pid:");

        btLaggTill.setText("Lägg till i Projekt");
        btLaggTill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLaggTillActionPerformed(evt);
            }
        });

        btTaBort.setText("Ta bort från projekt");
        btTaBort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btTaBortActionPerformed(evt);
            }
        });

        btnClose.setText("X");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        lblMeddelande.setForeground(new java.awt.Color(51, 204, 0));
        lblMeddelande.setText("Meddelande");

        lblProjektnamn.setForeground(new java.awt.Color(102, 102, 102));
        lblProjektnamn.setText("Projektnamn");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblProjektnamn)
                        .addGap(42, 42, 42)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPid)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblRubrik)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 104, Short.MAX_VALUE)
                        .addComponent(btnClose)
                        .addGap(16, 16, 16))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblMeddelande)
                                .addGap(18, 18, 18)
                                .addComponent(lblFelmeddelande))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btLaggTill)
                                .addGap(18, 18, 18)
                                .addComponent(btTaBort))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cbHandlaggare, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblAid))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(chLaggTill)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(chTaBort)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblRubrik)
                    .addComponent(btnClose))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProjektnamn)
                    .addComponent(lblPid)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chLaggTill)
                    .addComponent(chTaBort))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbHandlaggare, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAid))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMeddelande, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFelmeddelande))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btLaggTill)
                    .addComponent(btTaBort))
                .addGap(17, 17, 17))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chLaggTillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chLaggTillActionPerformed
        if (chLaggTill.isSelected()) {
            chTaBort.setSelected(false);
            fyllCbLaggTill();
            btTaBort.setVisible(false);
            btLaggTill.setVisible(true);
            lblMeddelande.setVisible(false);
            lblFelmeddelande.setVisible(false);
        }
    }//GEN-LAST:event_chLaggTillActionPerformed

    private void chTaBortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chTaBortActionPerformed
        if (chTaBort.isSelected()) {
            chLaggTill.setSelected(false);
            fyllCbTaBort();
            btLaggTill.setVisible(false);
            btTaBort.setVisible(true);
            lblMeddelande.setVisible(false);
            lblFelmeddelande.setVisible(false);
        }
    }//GEN-LAST:event_chTaBortActionPerformed

    private void cbHandlaggareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbHandlaggareActionPerformed
        lblAid.setText(getSelectedAid());
    }//GEN-LAST:event_cbHandlaggareActionPerformed

    private void btLaggTillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLaggTillActionPerformed
        laggTill();
        fyllHashMap();
    }//GEN-LAST:event_btLaggTillActionPerformed

    private void btTaBortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btTaBortActionPerformed
        taBort();
        fyllHashMap();
    }//GEN-LAST:event_btTaBortActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

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
            java.util.logging.Logger.getLogger(ProjektHandlaggare.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProjektHandlaggare.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProjektHandlaggare.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProjektHandlaggare.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new ProjektHandlaggare().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btLaggTill;
    private javax.swing.JButton btTaBort;
    private javax.swing.JButton btnClose;
    private javax.swing.JComboBox<String> cbHandlaggare;
    private javax.swing.JCheckBox chLaggTill;
    private javax.swing.JCheckBox chTaBort;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblAid;
    private javax.swing.JLabel lblFelmeddelande;
    private javax.swing.JLabel lblMeddelande;
    private javax.swing.JLabel lblPid;
    private javax.swing.JLabel lblProjektnamn;
    private javax.swing.JLabel lblRubrik;
    // End of variables declaration//GEN-END:variables
}
