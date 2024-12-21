/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ngo2024;

import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author frida.selin
 */
public class OmProjekt extends javax.swing.JFrame {

    private InfDB idb;
    private String projektId;

    private String anvandarEpost;

    /**
     * Creates new form OmProjekt
     */
    public OmProjekt(InfDB idb, String anvandarEpost, String projektId) {
        initComponents();
        this.projektId = projektId;
        this.idb = idb;

        lblH1ProjNamn.setText(getProjNamn(projektId));
        lblBeskrivning.setText(getProjBeskrivning(projektId));
        lblPrioritet.setText(getProjPrio(projektId));
        lblStatus.setText(getProjStatus(projektId));
        lblSlutDatum.setText(getStartdatum(projektId));
        lblKostnad.setText(getKostnad(projektId));
        lblLand.setText(getLand(projektId));
        lblProjektChef.setText(getProjektChef(projektId));
    }

    private String getProjNamn(String projektId) {
        String projektNamn = " ";
        try {
            String sqlFraga = "SELECT projektnamn FROM projekt where pid = '" + projektId + "'";
            projektNamn = idb.fetchSingle(sqlFraga);
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }

        return projektNamn;
    }

    private String getProjBeskrivning(String projektId) {
        String projektBeskrivning = " ";
        try {
            String sqlFraga = "SELECT beskrivning FROM projekt where pid = '" + projektId + "'";
            projektBeskrivning = idb.fetchSingle(sqlFraga);
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }

        return projektBeskrivning;
    }

    private String getProjPrio(String projektId) {
        String projektPrio = " ";
        try {
            String sqlFraga = "SELECT prioritet FROM projekt where pid = '" + projektId + "'";
            projektPrio = idb.fetchSingle(sqlFraga);
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }

        return projektPrio;
    }

    private String getProjStatus(String projektId) {
        String projektStatus = " ";
        try {
            String sqlFraga = "SELECT status FROM projekt where pid = '" + projektId + "'";
            projektStatus = idb.fetchSingle(sqlFraga);
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }

        return projektStatus;
    }

    private String getStartdatum(String projektId) {
        String projektStart = " ";
        try {
            String sqlFraga = "SELECT startdatum FROM projekt where pid = '" + projektId + "'";
            projektStart = idb.fetchSingle(sqlFraga);
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }

        return projektStart;
    }

    private String getSlutdatum(String projektId) {
        String projektSlut = " ";
        try {
            String sqlFraga = "SELECT slutdatum FROM projekt where pid = '" + projektId + "'";
            projektSlut = idb.fetchSingle(sqlFraga);
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }

        return projektSlut;
    }

    private String getKostnad(String projektId) {
        String projektKostnad = " ";
        try {
            String sqlFraga = "SELECT kostnad FROM projekt where pid = '" + projektId + "'";
            projektKostnad = idb.fetchSingle(sqlFraga);
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }

        return projektKostnad;
    }

    private String getLand(String projektId) {
        String projektLand = " ";
        try {
            String sqlFraga = "SELECT namn FROM land JOIN projekt ON projekt.land = land.lid WHERE pid = '" + projektId + "'";
            projektLand = idb.fetchSingle(sqlFraga);
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }

        return projektLand;
    }

    private String getProjektChef(String projektId) {
        String projektProjektChef = " ";
        try {
            String sqlFragaFornamn = "SELECT fornamn FROM anstalld JOIN projekt ON projekt.projektchef = anstalld.aid WHERE pid = '" + projektId + "'";
            String sqlFragaEfternamn = "SELECT efternamn FROM anstalld JOIN projekt ON projekt.projektchef = anstalld.aid WHERE pid = '" + projektId + "'";
            projektProjektChef = idb.fetchSingle(sqlFragaFornamn) + " " + idb.fetchSingle(sqlFragaEfternamn);
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }

        return projektProjektChef;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblH1ProjNamn = new javax.swing.JLabel();
        lblBeskrivning = new javax.swing.JLabel();
        lblH2Prio = new javax.swing.JLabel();
        lblH2Status = new javax.swing.JLabel();
        lblPrioritet = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        lblH2Kostnad = new javax.swing.JLabel();
        lblH2Partners = new javax.swing.JLabel();
        lblSlutDatum = new javax.swing.JLabel();
        lblStartDatum = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblLand = new javax.swing.JLabel();
        lblKostnad = new javax.swing.JLabel();
        lblH2ProjChef = new javax.swing.JLabel();
        lblProjektChef = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblH1ProjNamn.setFont(new java.awt.Font("Typo Round Bold Demo", 1, 18)); // NOI18N
        lblH1ProjNamn.setForeground(new java.awt.Color(102, 0, 102));

        lblBeskrivning.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        lblBeskrivning.setText("Beskrivning");

        lblH2Prio.setText("Prioritet:");

        lblH2Status.setText("Status:");

        lblH2Kostnad.setText("Kostnad:");

        lblH2Partners.setText("Samarbetspartners:");

        lblSlutDatum.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        lblStartDatum.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("-");

        jLabel2.setText("Land:");

        lblH2ProjChef.setText("Projektledare:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblH2ProjChef, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblProjektChef, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblBeskrivning, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblH1ProjNamn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblStartDatum, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblSlutDatum, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblH2Status, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(lblH2Partners, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(lblLand, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(37, 37, 37)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblH2Kostnad, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblKostnad, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblH2Prio, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblPrioritet, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addGap(0, 23, Short.MAX_VALUE)))
                        .addGap(337, 337, 337))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(lblH1ProjNamn, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblStartDatum, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(lblSlutDatum, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblH2ProjChef)
                    .addComponent(lblProjektChef, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblBeskrivning, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblH2Status)
                                .addComponent(lblH2Prio)
                                .addComponent(lblPrioritet, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(176, 176, 176)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblH2Kostnad)
                                .addComponent(jLabel2))
                            .addComponent(lblLand, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblKostnad, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lblH2Partners)
                .addContainerGap(203, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(OmProjekt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OmProjekt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OmProjekt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OmProjekt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new OmProjekt().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblBeskrivning;
    private javax.swing.JLabel lblH1ProjNamn;
    private javax.swing.JLabel lblH2Kostnad;
    private javax.swing.JLabel lblH2Partners;
    private javax.swing.JLabel lblH2Prio;
    private javax.swing.JLabel lblH2ProjChef;
    private javax.swing.JLabel lblH2Status;
    private javax.swing.JLabel lblKostnad;
    private javax.swing.JLabel lblLand;
    private javax.swing.JLabel lblPrioritet;
    private javax.swing.JLabel lblProjektChef;
    private javax.swing.JLabel lblSlutDatum;
    private javax.swing.JLabel lblStartDatum;
    private javax.swing.JLabel lblStatus;
    // End of variables declaration//GEN-END:variables
}
