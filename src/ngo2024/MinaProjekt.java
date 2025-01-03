
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ngo2024;

import javax.swing.table.DefaultTableModel;
import ngo2024.Meny;
import ngo2024.Validering;
import oru.inf.InfDB;
import oru.inf.InfException;
import java.util.*;
import TabellDesign.MultiLineCellRenderer;
import java.awt.Component;

//ska ta bort
import javax.swing.Timer;
import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Frida Selin
 */
public class MinaProjekt extends javax.swing.JFrame {

    private InfDB idb;
    private String anvandarEpost;
    private ArrayList<String> anvandarEpost1;
    private String personAid;

    /**
     * Creates new form MinaProjekt
     */
    public MinaProjekt(InfDB idb, String inloggadAnvandare) {
        this.idb = idb;
        anvandarEpost = inloggadAnvandare;
        personAid = getAidString();

        initComponents();

        /*
        //ska ta bort
        Color[] colors = {Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE, Color.RED};
        int[] colorIndex = {0}; // Use an array for mutability inside the timer
        Timer timer = new Timer(500, e -> {
            jToggleButton1.setBackground(colors[colorIndex[0]]);
            colorIndex[0] = (colorIndex[0] + 1) % colors.length; // Cycle through colors
        });
        timer.start();
        
        Timer timer2 = new Timer(1000, e -> {
           getContentPane().setBackground(colors[colorIndex[0]]);
            colorIndex[0] = (colorIndex[0] + 1) % colors.length; // Cycle through colors
        });
        timer2.start();
        
        Timer timer3 = new Timer(200, e -> {
            lblMinaProj.setForeground(colors[colorIndex[0]]);
            colorIndex[0] = (colorIndex[0] + 1) % colors.length; // Cycle through colors
        });
        timer3.start();
        */
        
        setInfo();

    }

    private void setInfo() {
        getProjektnamn(); //loop här
        getProjektStatus();

        //lägg till kontrol av att det finns ledar projekt
        getLedarProjektnamn();
        getLedarProjektStatus();
        lblPersonIdText.setText(getAidString());
        
        textArea1.setText(String.join("\n", getAnvandarPid()));
    }

    private String getAidString() {
        String stringAid = "";
        try {
            String sqlFraga = "SELECT aid FROM anstalld WHERE epost = '" + anvandarEpost + "'";
            stringAid = idb.fetchSingle(sqlFraga);
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
        return stringAid;
    }
    
    
    private ArrayList getAnvandarPid(){
    ArrayList<String> pidLista = new ArrayList<>();
    ArrayList<String> projektnamnLista = new ArrayList<>();
    try {
        String sqlFragaPid = "SELECT projekt.pid "
                + "FROM projekt "
                + "JOIN ans_proj ON projekt.pid = ans_proj.pid "
                + "JOIN anstalld ON ans_proj.aid = anstalld.aid "
                + "WHERE anstalld.aid = '" + personAid + "'";
            
        pidLista = idb.fetchColumn(sqlFragaPid);
    
        for (String pid : pidLista) {
                //hämta namn från pid
                //String sqlFragaNamn =  idb.fetchSingle("SELECT projektnamn FROM projekt WHERE pid ='" + pid +"'");
                projektnamnLista.add(idb.fetchSingle("SELECT projektnamn FROM projekt WHERE pid ='" + pid +"'"));
            }
        }
        catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
    return projektnamnLista;
    
    }
    
    
    

    private String getProjektnamn() {
        StringBuilder allaNamn = new StringBuilder();
        try {
            String sqlFraga = "SELECT projekt.pid, projekt.projektnamn "
                    + "FROM projekt "
                    + "JOIN ans_proj ON projekt.pid = ans_proj.pid "
                    + "JOIN anstalld ON ans_proj.aid = anstalld.aid "
                    + "WHERE anstalld.aid = '" + personAid + "'";

            ArrayList<HashMap<String, String>> resultat = idb.fetchRows(sqlFraga);

            for (HashMap<String, String> rad : resultat) {
                String namn = rad.get("projektnamn");
                laggTillNyRad(namn);
                allaNamn.append(namn).append("<br>");
            }
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
        lblProjektLista.setText("<html>" + allaNamn.toString() + "</html>");
        return allaNamn.toString();
    }

    private void laggTillNyRad(String namn) {
        lblProjektLista.setText(lblProjektLista.getText() + namn + "\n");
    }

    private String getProjektStatus() {
        StringBuilder allaStatus = new StringBuilder();
        try {
            String sqlFraga = "SELECT projekt.pid, projekt.status "
                    + "FROM projekt "
                    + "JOIN ans_proj ON projekt.pid = ans_proj.pid "
                    + "JOIN anstalld ON ans_proj.aid = anstalld.aid "
                    + "WHERE anstalld.aid = '" + personAid + "'";

            ArrayList<HashMap<String, String>> resultat = idb.fetchRows(sqlFraga);

            for (HashMap<String, String> rad : resultat) {
                String status = rad.get("status");
                laggTillNyRadStatus(status);
                allaStatus.append(status).append("<br>");
            }
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
        lblProjektListaS.setText("<html>" + allaStatus.toString() + "</html>");
        return allaStatus.toString();
    }

    private void laggTillNyRadStatus(String status) {
        lblProjektListaS.setText(lblProjektListaS.getText() + status + "\n");
    }

    private String getLedarProjektnamn() {
        StringBuilder allaNamn = new StringBuilder();
        try {
            String sqlFraga = "SELECT projekt.pid, projekt.projektnamn "
                    + "FROM projekt WHERE projektchef = '" + personAid + "'";

            ArrayList<HashMap<String, String>> resultat = idb.fetchRows(sqlFraga);

            for (HashMap<String, String> rad : resultat) {
                String namn = rad.get("projektnamn");
                laggTillNyRadLP(namn);
                allaNamn.append(namn).append("<br>");
            }
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
        lblProjektListaL.setText("<html>" + allaNamn.toString() + "</html>");
        return allaNamn.toString();
    }

    private void laggTillNyRadLP(String namn) {
        lblProjektListaL.setText(lblProjektListaL.getText() + namn + "\n");
    }

    private String getLedarProjektStatus() {
        StringBuilder allaStatus = new StringBuilder();
        try {
            String sqlFraga = "SELECT projekt.pid, projekt.status "
                    + "FROM projekt WHERE projektchef = '" + personAid + "'";

            ArrayList<HashMap<String, String>> resultat = idb.fetchRows(sqlFraga);

            for (HashMap<String, String> rad : resultat) {
                String status = rad.get("status");
                laggTillNyRadLedarStatus(status);
                allaStatus.append(status).append("<br>");
            }
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
        lblProjektListaLS.setText("<html>" + allaStatus.toString() + "</html>");
        return allaStatus.toString();
    }

    private void laggTillNyRadLedarStatus(String status) {
        lblProjektListaLS.setText(lblProjektListaLS.getText() + status + "\n");
    }

    /*
    private void konstrueraTabell() {
        model = (DefaultTableModel) tblProjekts.getModel();
        model.setRowCount (0);
        tabellDesign();
    }
    
    private void tabellDesign() {
        tblProjekts.getColumnModel().getColumn(0).setPreferredWidth(100);
        tblProjekts.getColumnModel().getColumn(1).setPreferredWidth(80);

        tblProjekts.getColumnModel().getColumn(0).setCellRenderer(new MultiLineCellRenderer());
        tblProjekts.getColumnModel().getColumn(1).setCellRenderer(new MultiLineCellRenderer());
        
        for (int rad = 0; rad < tblProjekts.getColumnCount() ; rad++){
            int radHojd = tblProjekts.getRowHeight();
            for(int kolumn = 0; kolumn < tblProjekts.getColumnCount() ; kolumn++){
                Component comp = tblProjekts.prepareRenderer(tblProjekts.getCellRenderer(rad, kolumn), rad, kolumn);
                radHojd = Math.max(radHojd, comp.getPreferredSize().height);
            }
        }
    
    }
     */
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
        jScrollPane3 = new javax.swing.JScrollPane();
        lblProjektLista = new javax.swing.JLabel();
        btAndra = new javax.swing.JButton();
        jToggleButton1 = new javax.swing.JToggleButton();
        lblPersonIdText = new javax.swing.JLabel();
        lblProjektListaS = new javax.swing.JLabel();
        lblProjektListaL = new javax.swing.JLabel();
        lblProjektListaLS = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        textArea1 = new javax.swing.JTextArea();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Mina Projekt");
        setAlwaysOnTop(true);
        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setFont(new java.awt.Font("Calibri", 0, 10)); // NOI18N

        btnReturn.setText("<- tillbaka");
        btnReturn.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true));
        btnReturn.setCursor(new java.awt.Cursor(java.awt.Cursor.SW_RESIZE_CURSOR));
        btnReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReturnActionPerformed(evt);
            }
        });

        lblMinaProj.setFont(new java.awt.Font("Typo Round Light Demo", 1, 24)); // NOI18N
        lblMinaProj.setForeground(new java.awt.Color(51, 0, 102));
        lblMinaProj.setText("Mina Projekt");

        lblProjekt.setText("Projekt");

        lblLedareProj.setText("Ledar Projekt");

        lblProjektLista.setToolTipText("");
        lblProjektLista.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jScrollPane3.setViewportView(lblProjektLista);

        btAndra.setText("Ändra");
        btAndra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAndraActionPerformed(evt);
            }
        });

        jToggleButton1.setText("TEST KNAPP");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        lblPersonIdText.setText("AID");

        lblProjektListaS.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        lblProjektListaL.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        lblProjektListaLS.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        textArea1.setColumns(20);
        textArea1.setRows(5);
        jScrollPane2.setViewportView(textArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblProjektListaL, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblProjektListaLS, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(lblPersonIdText, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btAndra))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnReturn, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(8, 8, 8))
                        .addComponent(lblProjekt, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblLedareProj, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblMinaProj, javax.swing.GroupLayout.PREFERRED_SIZE, 487, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lblProjektListaS, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE))))
                .addContainerGap(104, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btnReturn))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMinaProj)
                .addGap(28, 28, 28)
                .addComponent(lblLedareProj)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblProjektListaLS, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                    .addComponent(lblProjektListaL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(lblProjekt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblProjektListaS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btAndra, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblPersonIdText, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(208, 208, 208))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void btnReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturnActionPerformed
        btnReturn.setBorderPainted(false);
        new Meny(idb, anvandarEpost).setVisible(true); //kanske inte m?ste vara new
        this.dispose();
    }//GEN-LAST:event_btnReturnActionPerformed

    private void btAndraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAndraActionPerformed
        // new ProjektChef(idb, anvandarEpost).setVisible(true);

    }//GEN-LAST:event_btAndraActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        new OmProjekt_1(idb, anvandarEpost, "3").setVisible(true);
        this.dispose();

    }//GEN-LAST:event_jToggleButton1ActionPerformed

    /*
    public void tblProjektModel() {
        DefaultTableModel tblModel = (DefaultTableModel) tblProjekt.getModel();
        tblModel.setRowCount(0);
        
        try {
            String sqlFraga = "SELECT projektnamn, status FROM projekt";
            
            List<HashMap<String, String>> resultat = idb.fetchRows(sqlFraga);
            
            if (resultat != null) {
                for (HashMap<String, String> rad : resultat) {
                    String projektNamn = rad.get("projektnamn");
                    String projektStatus = rad.get("status");
                    
                    tblModel.addRow(new Object[]{projektNamn, projektStatus});
                }
                
            }
            else {
                System.out.println("No rows found");
            } 
        } catch (InfException ex) {
                    System.out.println("Error " + ex.getMessage());
                    }
    }
     */
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
    private javax.swing.JButton btAndra;
    private javax.swing.JButton btnReturn;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JLabel lblLedareProj;
    private javax.swing.JLabel lblMinaProj;
    private javax.swing.JLabel lblPersonIdText;
    private javax.swing.JLabel lblProjekt;
    private javax.swing.JLabel lblProjektLista;
    private javax.swing.JLabel lblProjektListaL;
    private javax.swing.JLabel lblProjektListaLS;
    private javax.swing.JLabel lblProjektListaS;
    private javax.swing.JTextArea textArea1;
    // End of variables declaration//GEN-END:variables
}
