/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ngo2024;

import TabellDesign.MultiLineCellRenderer;
import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import oru.inf.InfDB;
import oru.inf.InfException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author linneagottling
 */
public class Statistik extends javax.swing.JFrame {

    private InfDB idb;
    private String epost;
    private DefaultTableModel model;

    /**
     * Initierar Statistik objekt 
     * Visar statistik från alla projekt inloggad projektchef leder
     *
     * @param idb initierar fält för att interagera med databasen
     * @param epost inloggad användares epost
     */
    public Statistik(InfDB idb, String epost) {
        this.idb = idb;
        this.epost = epost;
        initComponents();
        konstrueraTabell();
        fyllTabell();
        tabellDesign();
        SetTotKostnad();
        SetMedelKostnad();
        SetTotalKostnadAvslutade();
    }

    /**
     * skapar tabell och definierar hur denna ska formateras
     */
    private void konstrueraTabell() {
        model = (DefaultTableModel) tbStatistik.getModel();
        model.setRowCount(0);
    }

    private void tabellDesign() {
        tbStatistik.getColumnModel().getColumn(0).setPreferredWidth(100);
        tbStatistik.getColumnModel().getColumn(1).setPreferredWidth(200);
        tbStatistik.getColumnModel().getColumn(2).setPreferredWidth(100);

        tbStatistik.getColumnModel().getColumn(0).setCellRenderer(new MultiLineCellRenderer());
        tbStatistik.getColumnModel().getColumn(1).setCellRenderer(new MultiLineCellRenderer());
        tbStatistik.getColumnModel().getColumn(2).setCellRenderer(new MultiLineCellRenderer());

        for (int row = 0; row < tbStatistik.getRowCount(); row++) {
            int rowHeight = tbStatistik.getRowHeight();
            for (int column = 0; column < tbStatistik.getColumnCount(); column++) {
                Component comp = tbStatistik.prepareRenderer(tbStatistik.getCellRenderer(row, column), row, column);
                rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
            }
            tbStatistik.setRowHeight(row, rowHeight);
        }
    }

    /**
     * retunerar inloggad användare ID (aid)
     *
     * @param epost inloggad användare epost
     */
    private String getChefId(String epost) {
        String aid = " ";
        try {

            String sqlFraga = "SELECT aid FROM anstalld WHERE epost = '" + epost + "'";
            aid = idb.fetchSingle(sqlFraga);

        } catch (InfException ex) {

            System.out.println(ex.getMessage());
        }
        return aid;
    }

    /**
     * Fyller statistik tabell med korrekt information om projekt
     */
    private void fyllTabell() {

        try {
            String sqlFraga = "SELECT kostnad, projektnamn, status FROM projekt "
                    + "WHERE projektchef = " + getChefId(epost);

            ArrayList<HashMap<String, String>> resultat = idb.fetchRows(sqlFraga);

            for (HashMap<String, String> rad : resultat) {

                String projekt = rad.get("projektnamn");
                String kostnad = rad.get("kostnad");
                String status = rad.get("status");

                model.addRow(new Object[]{projekt, kostnad, status});

            }
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * fyller i total konstnad för projektchefs alla projekt
     */
    private void SetTotKostnad() {
        String sqlFraga = "SELECT SUM(kostnad) FROM projekt WHERE projektchef = "
                + getChefId(epost);

        try {
            String tot = idb.fetchSingle(sqlFraga);
            tfTot.setText(tot);

        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * fyller i medelvärde av kostnad för projektchefs alla projekt
     */
    private void SetMedelKostnad() {
        String sqlFraga = "SELECT ROUND(AVG(kostnad), 2) as medelvärde"
                + " FROM projekt WHERE projektchef = "
                + getChefId(epost);

        try {
            String medel = idb.fetchSingle(sqlFraga);
            tfMedel.setText(medel);

        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * fyller i total kostnad för projektchefs alla avslutade projekt
     */
    private void SetTotalKostnadAvslutade() {
        String sqlFraga = "SELECT SUM(kostnad) FROM projekt "
                + "WHERE projektchef = " + getChefId(epost)
                + " AND status = 'Avslutat'";
        try {
            String totAvs = idb.fetchSingle(sqlFraga);
            if (totAvs != null) {
                tfTotAvs.setText(totAvs);
            } else {
                tfTotAvs.setText("0.00");
            }
        } catch (InfException ex) {
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

        btClose = new javax.swing.JButton();
        spPanel = new javax.swing.JScrollPane();
        tbStatistik = new javax.swing.JTable();
        lblRubrik = new javax.swing.JLabel();
        lblProjektchef = new javax.swing.JLabel();
        lblTot = new javax.swing.JLabel();
        lblMedel = new javax.swing.JLabel();
        tfTot = new javax.swing.JTextField();
        tfMedel = new javax.swing.JTextField();
        lbTotAvs = new javax.swing.JLabel();
        tfTotAvs = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btClose.setText("X");
        btClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCloseActionPerformed(evt);
            }
        });

        tbStatistik.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Projekt", "Kostnad", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbStatistik.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        spPanel.setViewportView(tbStatistik);

        lblRubrik.setText("Kostnaden för projekt:");

        lblProjektchef.setText("Projektchef");

        lblTot.setText("Totala kostnad:");

        lblMedel.setText("Medel:");

        tfTot.setEditable(false);

        tfMedel.setEditable(false);

        lbTotAvs.setText("Total avslutade:");

        tfTotAvs.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblRubrik)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(lblTot)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(tfTot, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblMedel)
                                    .addComponent(lbTotAvs))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tfTotAvs, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                                    .addComponent(tfMedel))))
                        .addGap(213, 213, 213)
                        .addComponent(btClose))
                    .addComponent(lblProjektchef, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(spPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(19, 19, 19))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(lblProjektchef)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btClose)
                    .addComponent(lblRubrik))
                .addGap(18, 18, 18)
                .addComponent(spPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTot)
                    .addComponent(tfTot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMedel)
                    .addComponent(tfMedel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbTotAvs)
                    .addComponent(tfTotAvs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(77, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_btCloseActionPerformed

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
            java.util.logging.Logger.getLogger(Statistik.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Statistik.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Statistik.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Statistik.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new Statistik().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btClose;
    private javax.swing.JLabel lbTotAvs;
    private javax.swing.JLabel lblMedel;
    private javax.swing.JLabel lblProjektchef;
    private javax.swing.JLabel lblRubrik;
    private javax.swing.JLabel lblTot;
    private javax.swing.JScrollPane spPanel;
    private javax.swing.JTable tbStatistik;
    private javax.swing.JTextField tfMedel;
    private javax.swing.JTextField tfTot;
    private javax.swing.JTextField tfTotAvs;
    // End of variables declaration//GEN-END:variables
}
