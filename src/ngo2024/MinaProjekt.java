
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ngo2024;

import ngo2024.Meny;
import ngo2024.Validering;
import oru.inf.InfDB;
import oru.inf.InfException;
import java.util.*;

/**
 *
 * @author Frida Selin
 */
public class MinaProjekt extends javax.swing.JFrame {

    private InfDB idb;
    private String anvandarEpost;
    private String aid;
    private ArrayList<String> projNamnLista;

    /**
     * Creates new form MinaProjekt
     * @param idb
     * @param aid
     */
    public MinaProjekt(InfDB idb, String aid) {
        this.idb = idb;
        this.aid = aid;
        projNamnLista = new ArrayList<String>();

        initComponents();
        this.setLocationRelativeTo(null);
        setInfo();
    }

    private void setInfo() {
        txtAreaProj.setEnabled(false);
        txtAreaChefsProj.setEditable(false);
        txtAreaProj.setText(String.join("\n", getAnvandarPid()));
        getCbxInfo();
        
        Validering valid = new Validering(idb);
        if (valid.arChef2(aid)){
            txtAreaChefsProj.setText(String.join("\n", getChefsProjekt()));
        }    
        else {
            txtAreaChefsProj.setText("Du är inte projektledare inom något projekt.");
            
            btnStats.setVisible(false);
            lblStats.setVisible(false); 
        }
        
    }

    public void getCbxInfo() {
        cbxValjProj.removeAllItems();
        String sqlFraga = "SELECT projektnamn FROM projekt "
                + "LEFT JOIN ans_proj ON projekt.pid = ans_proj.pid "
                + "WHERE projektchef = '" + aid + "' "
                + "OR ans_proj.aid = '" + aid + "' "
                + "GROUP BY projekt.projektnamn";

        try {
            projNamnLista = idb.fetchColumn(sqlFraga);
            for (String projektnamn : projNamnLista) {
                cbxValjProj.addItem(projektnamn);
            }
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }

        cbxValjProj.setVisible(true);
        lblValjProj.setVisible(true);
        cbxValjProj.addActionListener(e -> {
            String valtProjekt = (String) cbxValjProj.getSelectedItem();

            if (valtProjekt != null) {
                try {
                    String projektPid = idb.fetchSingle("SELECT pid FROM projekt WHERE projektnamn = '" + valtProjekt + "'");

                    if (projektPid != null) {
                        new OmProjekt(idb, aid, projektPid).setVisible(true);
                        this.dispose();
                    } else {
                        System.out.println("Inget pid hittades för projektet: " + valtProjekt);
                    }
                } catch (InfException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
    }

    private ArrayList getAnvandarPid() {
        ArrayList<String> pidLista = new ArrayList<>();
        ArrayList<String> projektnamnLista = new ArrayList<>();
        try {
            String sqlFragaPid = "SELECT projekt.pid "
                    + "FROM projekt "
                    + "JOIN ans_proj ON projekt.pid = ans_proj.pid "
                    + "JOIN anstalld ON ans_proj.aid = anstalld.aid "
                    + "WHERE anstalld.aid = '" + aid + "'";

            pidLista = idb.fetchColumn(sqlFragaPid);

            for (String pid : pidLista) {
                //hämta namn från pid
                //String sqlFragaNamn =  idb.fetchSingle("SELECT projektnamn FROM projekt WHERE pid ='" + pid +"'");

                projektnamnLista.add(" " + idb.fetchSingle("SELECT projektnamn FROM projekt WHERE pid ='" + pid + "'") + "\t" + idb.fetchSingle("SELECT status FROM projekt WHERE pid ='" + pid + "'"));
            }
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
        return projektnamnLista;

    }

    private ArrayList getChefsProjekt() {
        ArrayList<String> pidLista = new ArrayList<>();
        ArrayList<String> chefProjektLista = new ArrayList<>();
        try {
            String sqlFragaPid = "SELECT projekt.pid FROM projekt "
                    + "WHERE projektchef = '" + aid + "'";

            pidLista = idb.fetchColumn(sqlFragaPid);

            for (String pid : pidLista) {
                //hämta namn från pid
                //String sqlFragaNamn =  idb.fetchSingle("SELECT projektnamn FROM projekt WHERE pid ='" + pid +"'");
                chefProjektLista.add(idb.fetchSingle("SELECT projektnamn FROM projekt WHERE pid ='" + pid + "'") + "\t" + idb.fetchSingle("SELECT status FROM projekt WHERE pid ='" + pid + "'"));
            }
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
        return chefProjektLista;

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        btnReturn = new javax.swing.JButton();
        lblMinaProj = new javax.swing.JLabel();
        lblProjekt = new javax.swing.JLabel();
        lblLedareProj = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAreaProj = new javax.swing.JTextArea();
        cbxValjProj = new javax.swing.JComboBox<>();
        lblValjProj = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtAreaChefsProj = new javax.swing.JTextArea();
        btnStats = new javax.swing.JButton();
        lblStats = new javax.swing.JLabel();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Mina Projekt");
        setAlwaysOnTop(true);
        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setFont(new java.awt.Font("Calibri", 0, 10)); // NOI18N
        setPreferredSize(new java.awt.Dimension(900, 600));

        btnReturn.setText("<- tillbaka");
        btnReturn.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true));
        btnReturn.setCursor(new java.awt.Cursor(java.awt.Cursor.SW_RESIZE_CURSOR));
        btnReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReturnActionPerformed(evt);
            }
        });

        lblMinaProj.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblMinaProj.setForeground(new java.awt.Color(51, 0, 102));
        lblMinaProj.setText("Mina Projekt");

        lblProjekt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblProjekt.setText("Projekt");

        lblLedareProj.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblLedareProj.setText("Ledar Projekt");

        txtAreaProj.setColumns(20);
        txtAreaProj.setRows(5);
        txtAreaProj.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jScrollPane2.setViewportView(txtAreaProj);

        cbxValjProj.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxValjProj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxValjProjActionPerformed(evt);
            }
        });

        lblValjProj.setText("Välj det projekt du vill öppna:");

        txtAreaChefsProj.setColumns(20);
        txtAreaChefsProj.setRows(5);
        txtAreaChefsProj.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jScrollPane4.setViewportView(txtAreaChefsProj);

        btnStats.setText("Statisikt");
        btnStats.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStatsActionPerformed(evt);
            }
        });

        lblStats.setText("Se statistik över projekt jag leder:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblMinaProj, javax.swing.GroupLayout.PREFERRED_SIZE, 487, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblLedareProj, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(53, 53, 53)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
                                .addGap(130, 130, 130))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblProjekt, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxValjProj, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblValjProj, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnStats)
                            .addComponent(lblStats, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(btnReturn, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(btnReturn)
                .addGap(18, 18, 18)
                .addComponent(lblMinaProj)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblValjProj)
                    .addComponent(lblStats))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxValjProj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnStats))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblProjekt)
                    .addComponent(lblLedareProj))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(259, 259, 259))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void btnReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturnActionPerformed
        btnReturn.setBorderPainted(false);
        this.dispose();
    }//GEN-LAST:event_btnReturnActionPerformed

    private void cbxValjProjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxValjProjActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxValjProjActionPerformed

    private void btnStatsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStatsActionPerformed
        Validering valid = new Validering(idb);
        if (valid.arChef2(aid)){
            new Statistik(idb, aid).setVisible(true);
            this.dispose();
        }
        
    }//GEN-LAST:event_btnStatsActionPerformed

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
            java.util.logging.Logger.getLogger(MinaProjekt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MinaProjekt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MinaProjekt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MinaProjekt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new MinaProjekt().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnReturn;
    private javax.swing.JButton btnStats;
    private javax.swing.JComboBox<String> cbxValjProj;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lblLedareProj;
    private javax.swing.JLabel lblMinaProj;
    private javax.swing.JLabel lblProjekt;
    private javax.swing.JLabel lblStats;
    private javax.swing.JLabel lblValjProj;
    private javax.swing.JTextArea txtAreaChefsProj;
    private javax.swing.JTextArea txtAreaProj;
    // End of variables declaration//GEN-END:variables
}
