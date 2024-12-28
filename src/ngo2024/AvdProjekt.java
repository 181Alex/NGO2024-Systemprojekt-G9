/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ngo2024;

import TabellDesign.MultiLineCellRenderer;
import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author linneagottling
 */
public class AvdProjekt extends javax.swing.JFrame {

    private InfDB idb;
    private int avdNmr;
    private DefaultTableModel model;
    
    //Listor för att spara värdena i tabellen
    private ArrayList<String> projektLista = new ArrayList<>();
    private ArrayList<String> statusLista = new ArrayList<>();
    private ArrayList<String> chefLista = new ArrayList<>();
    private ArrayList<String> landLista = new ArrayList<>();
    private ArrayList<String> prioritetLista = new ArrayList<>();
    private ArrayList<String> startdatumLista = new ArrayList<>();
    private ArrayList<String> slutdatumLista = new ArrayList<>();
    private ArrayList<Integer> pidLista = new ArrayList<>();
    
    
    /**
     * Creates new form AvdProjekt
     */
    public AvdProjekt(InfDB idb, int avdNmr) {
        this.idb = idb;
        this.avdNmr = avdNmr;
        initComponents();
        konstrueraTabell();
        projektTabell();
    }

    private void konstrueraTabell() {
        model = (DefaultTableModel) tblProjekt.getModel();
        model.setRowCount(0);
        lblFelmeddelande.setVisible(false);
        tabellDesign();
    }
    
    
    private void tabellDesign() {
        tblProjekt.getColumnModel().getColumn(0).setPreferredWidth(90);
        tblProjekt.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblProjekt.getColumnModel().getColumn(2).setPreferredWidth(100);
        tblProjekt.getColumnModel().getColumn(3).setPreferredWidth(70);
        tblProjekt.getColumnModel().getColumn(4).setPreferredWidth(90);
        tblProjekt.getColumnModel().getColumn(5).setPreferredWidth(90);
        tblProjekt.getColumnModel().getColumn(6).setPreferredWidth(90);

        tblProjekt.getColumnModel().getColumn(0).setCellRenderer(new MultiLineCellRenderer());
        tblProjekt.getColumnModel().getColumn(1).setCellRenderer(new MultiLineCellRenderer());
        tblProjekt.getColumnModel().getColumn(2).setCellRenderer(new MultiLineCellRenderer());
        tblProjekt.getColumnModel().getColumn(4).setCellRenderer(new MultiLineCellRenderer());
        tblProjekt.getColumnModel().getColumn(5).setCellRenderer(new MultiLineCellRenderer());
        tblProjekt.getColumnModel().getColumn(6).setCellRenderer(new MultiLineCellRenderer());


        for (int row = 0; row < tblProjekt.getRowCount(); row++) {
            int rowHeight = tblProjekt.getRowHeight();
            for (int column = 0; column < tblProjekt.getColumnCount(); column++) {
                Component comp = tblProjekt.prepareRenderer(tblProjekt.getCellRenderer(row, column), row, column);
                rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
            }
            tblProjekt.setRowHeight(row, rowHeight);
        }
    }

    private void projektTabell() {

        try {
            String sqlFraga = "SELECT p.projektnamn, p.startdatum, p.slutdatum, p.pid, "
                    + "p.status, p.prioritet, "
                    + "CONCAT(pc.fornamn, ' ', pc.efternamn) AS helanamnet, l.namn "
                    + "FROM projekt p "
                    + "JOIN land l ON p.land = l.lid "
                    + "JOIN ans_proj ap ON ap.pid = p.pid "
                    + "JOIN anstalld a ON ap.aid = a.aid "
                    + "JOIN avdelning avd ON a.avdelning = avd.avdid "
                    + "JOIN anstalld pc ON p.projektchef = pc.aid "
                    + "WHERE avd.avdid = " + avdNmr;

            ArrayList<HashMap<String, String>> resultat = idb.fetchRows(sqlFraga);

            for (HashMap<String, String> rad : resultat) {
                
                    String projekt = rad.get("projektnamn");
                    String status = rad.get("status");
                    String chef = rad.get("helanamnet");
                    String land = rad.get("namn");
                    String prioritet = rad.get("prioritet");
                    String startdatum = rad.get("startdatum");
                    String slutdatum = rad.get("slutdatum");  
                    String p_id = rad.get("pid");
                    int pid = Integer.parseInt(p_id);

                    //lägger till rad i tabellen
                    laggTillNyRad(projekt, status, chef, land, prioritet, startdatum, slutdatum);
                    
                    //lägger till infon i separata listor
                    laggTillRadInfo(projekt, status, chef, land, prioritet, startdatum, slutdatum, pid);               

            }
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
    }
    private void laggTillNyRad(String projekt, String status, String chef, String land,
            String prioritet, String startdatum, String slutdatum){
        model.addRow(new Object[]{projekt, status, chef, land, prioritet, startdatum, slutdatum});
    }
    private void laggTillRadInfo(String projekt, String status, String chef, String land,
            String prioritet, String startdatum, String slutdatum, int pid){
        projektLista.add(projekt);
        statusLista.add(status);
        chefLista.add(chef);
        landLista.add(land);
        prioritetLista.add(prioritet);
        startdatumLista.add(startdatum);
        slutdatumLista.add(slutdatum);
        pidLista.add(pid);
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblAvdProjekt = new javax.swing.JLabel();
        spPanel = new javax.swing.JScrollPane();
        tblProjekt = new javax.swing.JTable();
        btReturn = new javax.swing.JButton();
        btSok = new javax.swing.JButton();
        cbxStatus = new javax.swing.JComboBox<>();
        tfSokTill = new javax.swing.JTextField();
        lblFranDat = new javax.swing.JLabel();
        lblTillDat = new javax.swing.JLabel();
        tfSokFran = new javax.swing.JTextField();
        lblFelmeddelande = new javax.swing.JLabel();
        lblFiltrera = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblAvdProjekt.setText("Projekt på avdelning");

        tblProjekt.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Projekt", "Status", "Chef", "Land", "Prioritet", "Startdatum", "Slutdatum"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblProjekt.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        spPanel.setViewportView(tblProjekt);

        btReturn.setText("X");
        btReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btReturnActionPerformed(evt);
            }
        });

        btSok.setText("Sök");
        btSok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSokActionPerformed(evt);
            }
        });

        cbxStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Alla", "Planerade", "Pågående", "Avslutade", "Aktiva", "Pausad" }));
        cbxStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxStatusActionPerformed(evt);
            }
        });

        lblFranDat.setForeground(new java.awt.Color(102, 102, 102));
        lblFranDat.setText("Startdatum:");

        lblTillDat.setForeground(new java.awt.Color(102, 102, 102));
        lblTillDat.setText("Slutdatum:");

        lblFelmeddelande.setForeground(new java.awt.Color(255, 51, 51));
        lblFelmeddelande.setText("Felmeddelande");

        lblFiltrera.setForeground(new java.awt.Color(102, 102, 102));
        lblFiltrera.setText("Filtrera på status");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblAvdProjekt)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(spPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 632, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblFranDat)
                                    .addComponent(tfSokFran, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfSokTill, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblTillDat))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btSok)
                                    .addComponent(lblFelmeddelande))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblFiltrera)
                                        .addGap(27, 27, 27)
                                        .addComponent(btReturn))
                                    .addComponent(cbxStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(30, 30, 30))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(lblAvdProjekt)
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblFranDat)
                            .addComponent(lblTillDat)
                            .addComponent(lblFelmeddelande)
                            .addComponent(lblFiltrera)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(btReturn)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tfSokTill, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btSok)
                        .addComponent(tfSokFran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cbxStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(spPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btReturnActionPerformed
        this.dispose();
    }//GEN-LAST:event_btReturnActionPerformed

    private void btSokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSokActionPerformed
       filtreraTabell();
    }//GEN-LAST:event_btSokActionPerformed

    private void cbxStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxStatusActionPerformed
       filtreraTabell();
    }//GEN-LAST:event_cbxStatusActionPerformed

    private void filtreraTabell() {
    String sokFran = tfSokFran.getText();
    String sokTill = tfSokTill.getText();
    String selectedItem = (String) cbxStatus.getSelectedItem();

    konstrueraTabell();
    lblFelmeddelande.setVisible(false);
    
    Validering enValidering = new Validering(idb);

    // Validera datum
    if ((!sokFran.isEmpty() && !enValidering.checkDatum(sokFran)) ||
        (!sokTill.isEmpty() && !enValidering.checkDatum(sokTill))) {
        lblFelmeddelande.setText("Vänligen ange datum i formatet yyyy-mm-dd");
        lblFelmeddelande.setVisible(true);
        return;
    }

    if (!sokFran.isEmpty() && !sokTill.isEmpty() &&
        !enValidering.checkDatumSkillnad(sokFran, sokTill)) {
        lblFelmeddelande.setText("Slutdatum måste vara senare än startdatum");
        lblFelmeddelande.setVisible(true);
        return;
    }

    for (int i = 0; i < pidLista.size(); i++) {
        String startdatum = startdatumLista.get(i);
        String slutdatum = slutdatumLista.get(i);
        String status = statusLista.get(i);
        
        boolean datumMatchar = true;
        
        //villkor för att statusMatchar = true om selectedItem "Alla" är vald
        boolean statusMatchar = "Alla".equals(selectedItem);

        // Kontrollera datumfilter
        if (!sokFran.isEmpty() && !sokTill.isEmpty()) {
            datumMatchar = enValidering.checkMellanDatumSkillnad(startdatum, slutdatum, sokFran,  sokTill);
        } else if(!sokFran.isEmpty() && sokTill.isEmpty()){
            datumMatchar = enValidering.checkMellanDatumSkillnad(startdatum, slutdatum, sokFran, null); 
        } else if(sokFran.isEmpty() && !sokTill.isEmpty()){
            datumMatchar = enValidering.checkMellanDatumSkillnad(startdatum, slutdatum, null, sokTill);
        }

        // Kontrollera statusfilter
        if (!"Alla".equals(selectedItem)) {
            switch (selectedItem) {
                case "Planerade":
                    statusMatchar = enValidering.checkPlanerade(status);
                    break;
                case "Pågående":
                    statusMatchar = enValidering.checkPagaende(status);
                    break;
                case "Avslutade":
                    statusMatchar = enValidering.checkAvslutad(status);
                    break;
                case "Aktiva":
                    statusMatchar = enValidering.checkAktiv(status);
                    break;
                case"Pausad":
                    statusMatchar = enValidering.checkPausad(status);
            }
        }

        // Lägg till rad om både datum och status matchar
        if (datumMatchar && statusMatchar) {
            laggTillNyRad(projektLista.get(i), status, chefLista.get(i), 
                          landLista.get(i), prioritetLista.get(i), startdatum, slutdatum);
        }
    }
}    
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
            java.util.logging.Logger.getLogger(AvdProjekt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AvdProjekt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AvdProjekt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AvdProjekt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new AvdProjekt().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btReturn;
    private javax.swing.JButton btSok;
    private javax.swing.JComboBox<String> cbxStatus;
    private javax.swing.JLabel lblAvdProjekt;
    private javax.swing.JLabel lblFelmeddelande;
    private javax.swing.JLabel lblFiltrera;
    private javax.swing.JLabel lblFranDat;
    private javax.swing.JLabel lblTillDat;
    private javax.swing.JScrollPane spPanel;
    private javax.swing.JTable tblProjekt;
    private javax.swing.JTextField tfSokFran;
    private javax.swing.JTextField tfSokTill;
    // End of variables declaration//GEN-END:variables
}
