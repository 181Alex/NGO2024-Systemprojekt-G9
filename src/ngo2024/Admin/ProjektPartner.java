/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ngo2024.Admin;

import java.util.HashMap;
import java.util.ArrayList;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author linneagottling
 */
public class ProjektPartner extends javax.swing.JFrame {

    private InfDB idb;
    private String pid;
    private HashMap<String, String> partnerLista;

    /**
     * Initierar ProjektPartner objekt låter administratören lägga till och ta
     * bort partner för ett projekt
     *
     * @param idb initierar fält för att interagera med databasen
     * @param pid partner ID
     */
    public ProjektPartner(InfDB idb, String pid) {
        this.idb = idb;
        this.pid = pid;
        initComponents();
        lblPid.setText(pid);
        setProjektnamn(pid);
        partnerLista = new HashMap<>();
        lblMeddelande.setVisible(false);
        btTaBort.setVisible(false);
        btLaggTill.setVisible(false);
        cbPartners.removeAllItems();
        lblFelmeddelande.setVisible(false);
        fyllHashMap();
    }

    /**
     * ger projektnamn på ett visst projekt namn
     *
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
     * fyller i Hash Map med alla partners namn
     */
    private void fyllHashMap() {

        try {
            String sqlFraga = "SELECT namn FROM partner";

            ArrayList<String> allaPartners = idb.fetchColumn(sqlFraga);

            for (String pNamn : allaPartners) {
                String sqlPid = "SELECT pid FROM partner WHERE "
                        + "namn = '" + pNamn + "'";
                String paid = idb.fetchSingle(sqlPid);
                partnerLista.put(paid, pNamn);
            }
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * fyller i combo box med alla partners namn
     */
    private void fyllCbLaggTill() {
        cbPartners.removeAllItems();

        try {
            String sqlFraga = "SELECT namn FROM partner";

            ArrayList<String> allaPartners = idb.fetchColumn(sqlFraga);

            //fyller i alla mål
            for (String pNamn : allaPartners) {
                String sqlHid = "SELECT pid FROM partner WHERE "
                        + "namn = '" + pNamn + "'";
                cbPartners.addItem(pNamn);
                String paid = idb.fetchSingle(sqlHid);
            }

        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * fyller i combo box för att ta bort
     */
    private void fyllCbTaBort() {
        cbPartners.removeAllItems();

        try {
            String sqlFraga = "SELECT namn FROM partner "
                    + "JOIN projekt_partner ON partner.pid = partner_pid "
                    + "WHERE projekt_partner.pid =" + pid;

            ArrayList<String> projektPartner = idb.fetchColumn(sqlFraga);

            for (String pNamn : projektPartner) {
                cbPartners.addItem(pNamn);
            }

        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * hämtar vald partner ID
     */
    private String getSelectedPaid() {
        String selectedMal = (String) cbPartners.getSelectedItem();
        String paid = " ";
        for (String id : partnerLista.keySet()) {
            String namn = partnerLista.get(id);
            if (selectedMal != null && selectedMal.equals(namn)) {
                paid = id;
            }
        }
        return paid;
    }

    /**
     * lägg till partner till ett projekt
     */
    private void laggTill() {
        String paid = getSelectedPaid();
        String sPid = lblPid.getText();

        String sqlLaggTill = "INSERT INTO projekt_partner VALUES( " + sPid
                + ", " + paid + ")";

        try {
            if (kontrollInteSamma(paid)) {
                idb.insert(sqlLaggTill);
                lblFelmeddelande.setVisible(false);
                lblMeddelande.setText("Tillagd");
                lblMeddelande.setVisible(true);
            } else {
                lblFelmeddelande.setText("Denna partner finns redan för detta projekt");
                lblFelmeddelande.setVisible(true);
                lblMeddelande.setVisible(false);
            }

        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }

    }

    /**
     * tar bort partner från ett projekt
     */
    private void taBort() {
        String paid = getSelectedPaid();
        String sPid = lblPid.getText();

        String sqlTaBort = "DELETE FROM projekt_partner WHERE pid = " + sPid + " AND partner_pid = " + paid;

        try {
            idb.delete(sqlTaBort);
            lblMeddelande.setText("Borttagen från projekt");
            lblMeddelande.setVisible(true);

        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }

    }

    /**
     * kontrollerar så att det finns inga dubletter av partner ID finns
     */
    private boolean kontrollInteSamma(String paid) {
        boolean finnsEj = true;
        String sPid = lblPid.getText();

        String sqlFraga = "SELECT COUNT(*) AS Antal "
                + "FROM projekt_partner "
                + "WHERE pid = " + sPid + " AND partner_pid = " + paid;

        try {
            String sqlAntal = idb.fetchSingle(sqlFraga);
            int antal = Integer.parseInt(sqlAntal);

            if (antal > 0) {
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
        lblRubrik = new javax.swing.JLabel();
        chTaBort = new javax.swing.JCheckBox();
        cbPartners = new javax.swing.JComboBox<>();
        lblPaid = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblProjektnamn = new javax.swing.JLabel();
        btLaggTill = new javax.swing.JButton();
        lblPid = new javax.swing.JLabel();
        btTaBort = new javax.swing.JButton();
        lblMeddelande = new javax.swing.JLabel();
        lblFelmeddelande = new javax.swing.JLabel();
        chLaggTill = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnClose.setText("X");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        lblRubrik.setText("Lägg till/ Ta bort samarbetspartners för projekt:");

        chTaBort.setText("Ta bort");
        chTaBort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chTaBortActionPerformed(evt);
            }
        });

        cbPartners.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbPartners.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPartnersActionPerformed(evt);
            }
        });

        lblPaid.setText("Pid");

        jLabel2.setText("Pid:");

        lblProjektnamn.setForeground(new java.awt.Color(102, 102, 102));
        lblProjektnamn.setText("Projektnamn");

        btLaggTill.setText("Lägg till i Projekt");
        btLaggTill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLaggTillActionPerformed(evt);
            }
        });

        lblPid.setForeground(new java.awt.Color(102, 102, 102));
        lblPid.setText("Pid");

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

        chLaggTill.setText("Lägg till");
        chLaggTill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chLaggTillActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(361, Short.MAX_VALUE)
                .addComponent(btnClose)
                .addGap(16, 16, 16))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(lblProjektnamn)
                                    .addGap(42, 42, 42)
                                    .addComponent(jLabel2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(lblPid))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(lblMeddelande)
                                    .addGap(18, 18, 18)
                                    .addComponent(lblFelmeddelande))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(btLaggTill)
                                    .addGap(18, 18, 18)
                                    .addComponent(btTaBort))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(cbPartners, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(lblPaid))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(chLaggTill)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(chTaBort)))
                            .addContainerGap(97, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(lblRubrik)
                            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(btnClose)
                .addContainerGap(255, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(24, 24, 24)
                    .addComponent(lblRubrik)
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
                        .addComponent(cbPartners, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblPaid))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblMeddelande, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblFelmeddelande))
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btLaggTill)
                        .addComponent(btTaBort))
                    .addGap(19, 19, 19)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

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

    private void cbPartnersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPartnersActionPerformed
        lblPaid.setText(getSelectedPaid());
    }//GEN-LAST:event_cbPartnersActionPerformed

    private void btLaggTillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLaggTillActionPerformed
        laggTill();
        fyllHashMap();
    }//GEN-LAST:event_btLaggTillActionPerformed

    private void btTaBortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btTaBortActionPerformed
        taBort();
        fyllHashMap();
    }//GEN-LAST:event_btTaBortActionPerformed

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
            java.util.logging.Logger.getLogger(ProjektPartner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProjektPartner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProjektPartner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProjektPartner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new ProjektPartner().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btLaggTill;
    private javax.swing.JButton btTaBort;
    private javax.swing.JButton btnClose;
    private javax.swing.JComboBox<String> cbPartners;
    private javax.swing.JCheckBox chLaggTill;
    private javax.swing.JCheckBox chTaBort;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblFelmeddelande;
    private javax.swing.JLabel lblMeddelande;
    private javax.swing.JLabel lblPaid;
    private javax.swing.JLabel lblPid;
    private javax.swing.JLabel lblProjektnamn;
    private javax.swing.JLabel lblRubrik;
    // End of variables declaration//GEN-END:variables
}
