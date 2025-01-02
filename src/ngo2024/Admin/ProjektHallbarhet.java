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
public class ProjektHallbarhet extends javax.swing.JFrame {

    private InfDB idb;
    private String pid;
    private HashMap<String, String> malLista;

    /**
     * Creates new form ProjektHallbarhet
     */
    public ProjektHallbarhet(InfDB idb, String pid) {
        this.idb = idb;
        this.pid = pid;
        initComponents();
        lblPid.setText(pid);
        setProjektnamn(pid);
        malLista = new HashMap<>();
        lblMeddelande.setVisible(false);
        btTaBort.setVisible(false);
        btLaggTill.setVisible(false);
        cbHallbarhetsMal.removeAllItems();
        lblFelmeddelande.setVisible(false);
    }

    private void setProjektnamn(String pid) {
        try {
            String sqlFraga = "SELECT projektnamn FROM projekt WHERE pid =" + pid;
            String projektnamn = idb.fetchSingle(sqlFraga);
            lblProjektnamn.setText(projektnamn);

        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
    }
    

    private void fyllCbLaggTill() {
        cbHallbarhetsMal.removeAllItems();

        try {
            String sqlFraga = "SELECT namn FROM hallbarhetsmal";

            ArrayList<String> allaMal = idb.fetchColumn(sqlFraga);

            //fyller i alla mål
            for (String malNamn : allaMal) {
                String sqlHid = "SELECT hid FROM hallbarhetsmal WHERE "
                        + "namn = '" + malNamn + "'";
                cbHallbarhetsMal.addItem(malNamn);
                String hid = idb.fetchSingle(sqlHid);
                malLista.put(hid, malNamn);
            }

        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void fyllCbTaBort() {
        cbHallbarhetsMal.removeAllItems();

        try {
            String sqlFraga = "SELECT namn FROM hallbarhetsmal "
                    + "JOIN proj_hallbarhet ON hallbarhetsmal.hid = proj_hallbarhet.hid "
                    + "WHERE pid =" + pid;

            ArrayList<String> allaMal = idb.fetchColumn(sqlFraga);

            for (String malNamn : allaMal) {
                cbHallbarhetsMal.addItem(malNamn);
            }

        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private String getSelectedHid() {
        String selectedMal = (String) cbHallbarhetsMal.getSelectedItem();
        String hid = " ";
        for (String id : malLista.keySet()) {
            String namn = malLista.get(id);
            if (selectedMal != null && selectedMal.equals(namn)) {
                hid = id;
            }
        }
        return hid;
    }
    
    private void laggTill(){
        String hid = getSelectedHid();
        
        String sqlLaggTill = "INSERT INTO proj_hallbarhet VALUES( " + pid
                + ", " + hid + ")";
        
        try {
            if(kontrollInteSamma(hid)){
            idb.insert(sqlLaggTill);
            lblFelmeddelande.setVisible(false);
            lblMeddelande.setText("Tillagd");
            lblMeddelande.setVisible(true);
            } else {
                lblFelmeddelande.setText("Detta mål finns redan för detta projekt");
                lblFelmeddelande.setVisible(true);
                lblMeddelande.setVisible(false);
            }
            
            
        } catch (InfException ex){
            System.out.println(ex.getMessage());
        }
        
        
    }
    
    private void taBort(){
        String hid = getSelectedHid();
        
        String sqlTaBort = "DELETE FROM proj_hallbarhet WHERE pid = " + pid + " AND hid = " + hid;
        
        try{
            idb.delete(sqlTaBort);
            lblMeddelande.setText("Borttagen från projekt");
            lblMeddelande.setVisible(true);       
            
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
        
    }
    
    private boolean kontrollInteSamma(String hid){
        boolean finnsEj = true;
        
        String sqlFraga = "SELECT COUNT(*) AS Antal "
                + "FROM proj_hallbarhet "
                + "WHERE pid = " + pid + " AND hid = " + hid;
        
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

        btnClose = new javax.swing.JButton();
        lblProjektnamn = new javax.swing.JLabel();
        lblPid = new javax.swing.JLabel();
        chLaggTill = new javax.swing.JCheckBox();
        lblRubrik = new javax.swing.JLabel();
        chTaBort = new javax.swing.JCheckBox();
        cbHallbarhetsMal = new javax.swing.JComboBox<>();
        lblHid = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btLaggTill = new javax.swing.JButton();
        btTaBort = new javax.swing.JButton();
        lblMeddelande = new javax.swing.JLabel();
        lblFelmeddelande = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnClose.setText("X");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        lblProjektnamn.setForeground(new java.awt.Color(102, 102, 102));
        lblProjektnamn.setText("Projektnamn");

        lblPid.setForeground(new java.awt.Color(102, 102, 102));
        lblPid.setText("Pid");

        chLaggTill.setText("Lägg till");
        chLaggTill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chLaggTillActionPerformed(evt);
            }
        });

        lblRubrik.setText("Lägg till/ Ta bort hållbarhetsmål för projekt:");

        chTaBort.setText("Ta bort");
        chTaBort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chTaBortActionPerformed(evt);
            }
        });

        cbHallbarhetsMal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbHallbarhetsMal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbHallbarhetsMalActionPerformed(evt);
            }
        });

        lblHid.setText("hid");

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

        lblMeddelande.setForeground(new java.awt.Color(51, 204, 0));
        lblMeddelande.setText("Meddelande");

        lblFelmeddelande.setForeground(new java.awt.Color(255, 0, 0));
        lblFelmeddelande.setText("Felmeddelande");

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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
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
                                .addComponent(cbHallbarhetsMal, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblHid))
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
                    .addComponent(cbHallbarhetsMal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHid))
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

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

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

    private void cbHallbarhetsMalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbHallbarhetsMalActionPerformed
        lblHid.setText(getSelectedHid());
    }//GEN-LAST:event_cbHallbarhetsMalActionPerformed

    private void btLaggTillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLaggTillActionPerformed
        laggTill();
    }//GEN-LAST:event_btLaggTillActionPerformed

    private void btTaBortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btTaBortActionPerformed
        taBort();
    }//GEN-LAST:event_btTaBortActionPerformed

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
            java.util.logging.Logger.getLogger(ProjektHallbarhet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProjektHallbarhet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProjektHallbarhet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProjektHallbarhet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new ProjektHallbarhet().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btLaggTill;
    private javax.swing.JButton btTaBort;
    private javax.swing.JButton btnClose;
    private javax.swing.JComboBox<String> cbHallbarhetsMal;
    private javax.swing.JCheckBox chLaggTill;
    private javax.swing.JCheckBox chTaBort;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblFelmeddelande;
    private javax.swing.JLabel lblHid;
    private javax.swing.JLabel lblMeddelande;
    private javax.swing.JLabel lblPid;
    private javax.swing.JLabel lblProjektnamn;
    private javax.swing.JLabel lblRubrik;
    // End of variables declaration//GEN-END:variables
}
