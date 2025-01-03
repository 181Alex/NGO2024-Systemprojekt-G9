/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ngo2024;
//

import oru.inf.InfDB;
import oru.inf.InfException;

import java.util.ArrayList;
import java.util.HashMap;
/**
  * @author frida.selin
  */
public class OmProjekt_1 extends javax.swing.JFrame {

    private InfDB idb;
    private String projektId;
    private String anvandarEpost;
    private String aid;
    private boolean projektLedare;

    /**
     * Creates new form OmProjekt
     */
    public OmProjekt_1(InfDB idb, String anvandarEpost, String projektId) {
        initComponents();
        this.projektId = projektId;
        this.anvandarEpost = anvandarEpost;
        this.idb = idb;
        this.aid = getAidString();

        //initierar rätt text vid rätt fält
        setAllTextFeilds();
        
        //anropar kontroll för att se hurvida användaren är projektledare för projektet
        projektLedare = projektLedarValidering();
        
        //projektLedare.arChef(anvandarEpost);
        andraButton();
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
    
     /**
      * Kontrolerar om användaren är projektledare för projektet. 
      * Metoden anropas i konstruktorn.
    */ 
    private boolean projektLedarValidering(){
        String valideringsPid = null;
        //String sqlFraga = "SELECT pid FROM projekt WHERE projektchef = '" + aid + "'";
        try {
            String sqlFraga = "SELECT pid FROM projekt WHERE projektchef = '" + aid + "'";
            valideringsPid = idb.fetchSingle(sqlFraga);
        
            if (valideringsPid != null && valideringsPid.equals(projektId)) {
                projektLedare = true;
            }

            else {
                projektLedare = false;
            }
        
        }
        catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
        return projektLedare;
    }
    
    
    private void andraButton() {
        
        if (projektLedare){
            btnEdit.setVisible(true);
        }
        else {
            btnEdit.setVisible(false);
        }
    }

    /**
     *
     * Tar in parametervärdet för önskade projekts ID i datatyp String
     *
     * public String getAnstallda(String projektId) { String anstallda = " ";
     * try { String sqlAnstallda = "SELECT fornamn, efternamn FROM anstalld JOIN
     * ans_proj on ans_proj.aid = anstalld.aid JOIN projekt on ans_proj.pid =
     * projekt.pid WHERE projekt.pid = '" + projektId + "'"; // anstallda =
     * idb.fetchSingle(sqlAnstallda); //namnLista =
     * idb.fetchColumn(sqlAnstallda); // change to hasmap. Get both names. Make
     * list appear namnLista = idb.fetchSingle(sqlAnstallda); } catch
     * (InfException ex) { System.out.println(ex.getMessage()); } return
     * anstallda; }
     *
     *
     */
    /**
     * Metod som kan kallas i konstruktorn i syte att göra konstruktorn lättare
     * att tyda tillkallar alla set text metoder för att hämta rätt information
     * från sql basen och initierar rätt text vid rätt fält
     */
    private void setAllTextFeilds() {
        lblH1ProjNamn.setText(setProjNamnUpperCase());
        lblBeskrivning.setText(getFromProjekt(projektId, projBeskrivning()));
        lblPrioritet.setText(getFromProjekt(projektId, prioritet()));
        lblStatus.setText(getFromProjekt(projektId, status()));
        lblSlutDatum.setText(getFromProjekt(projektId, slutdatum()));
        lblStartDatum.setText(getFromProjekt(projektId, startdatum()));
        lblKostnad.setText(getFromProjekt(projektId, kostnad()));
        lblLand.setText(getLand(projektId));
        lblProjektChef.setText(getProjektChef(projektId));
        // lblProjAnstallda.setText(getAnstallda(projektId));
        getAnstallda(projektId);
        getPartners(projektId);
    }

    //private String getAnstallda()
    private String getAnstallda(String pid) {
        StringBuilder allaNamn = new StringBuilder();
        try {
            String sqlFraga = "SELECT a.aid, CONCAT(a.fornamn, ' ', a.efternamn) AS namn "
                    + "FROM anstalld a "
                    + "JOIN ans_proj on ans_proj.aid = a.aid "
                    + "JOIN projekt on ans_proj.pid = projekt.pid "
                    + "WHERE projekt.pid = '" + pid + "'";
            
            ArrayList<HashMap<String, String>> resultat = idb.fetchRows(sqlFraga);

            for (HashMap<String, String> rad : resultat) {
                String namn = rad.get("namn");
                laggTillNyRad(namn);
                //allaNamn.append(namn).append("\n"); 
                allaNamn.append(namn).append("<br>");
            }
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
        lblProjAnstallda.setText("<html>" + allaNamn.toString() + "</html>");
        return allaNamn.toString();
    }

    private void laggTillNyRad(String namn) {
        lblProjAnstallda.setText(lblProjAnstallda.getText() + namn + "\n");

        //lblProjAnstallda.setText("<html>" + namn.replace("\n", "<br>") + "</html>");
    }

    private String getPartners(String pid) {
        StringBuilder allaObjekt = new StringBuilder();
        try {
            String sqlFraga = "SELECT namn FROM partner "
                    + "JOIN projekt_partner ON partner.pid = projekt_partner.partner_pid "
                    + "JOIN projekt ON projekt_partner.pid = projekt.pid "
                    + "WHERE projekt.pid = '" + pid + "'";

            ArrayList<HashMap<String, String>> resultat = idb.fetchRows(sqlFraga);

            for (HashMap<String, String> rad : resultat) {
                String namn = rad.get("namn");
                laggTillNyRad2(namn);

                allaObjekt.append(namn).append("<br>");
            }
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
        lblPartners.setText("<html>" + allaObjekt.toString() + "</html>");
        return allaObjekt.toString();
    }

    private void laggTillNyRad2(String namn) {
        lblPartners.setText(lblPartners.getText() + namn + "\n");
    }

    /**
     * Metod som inehåller en SQL bas fråga som återanvänds ofta för att hämta
     * önskad String
     *
     * Parameter 1: önskade projekts ID i datatyp String Parameter 2: String
     * hämtar önskad kolumn i databasen som vill hämtas. Ex. 'projektnamn',
     * 'beskrivning'
     */
    private String getFromProjekt(String projektId, String specifikInfo) {
        String minInfo = " ";
        try {
            String sqlFraga = "SELECT " + specifikInfo + " FROM projekt where pid = '" + projektId + "'";
            minInfo = idb.fetchSingle(sqlFraga);
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
        return minInfo;
    }

    /**
     * Metod som förändrar rubriken med projekt namnet till upper case
     */
    private String setProjNamnUpperCase() {
        String UpperCaseName = getFromProjekt(projektId, projNamn()).toUpperCase();
        return UpperCaseName;
    }

    /**
     * Metod hämtar String med innehåll 'projektnamn' vilket är en kolumn i
     * tabellen projekt i databasen
     */
    private String projNamn() {
        String projNamn = "projektnamn";
        return projNamn;
    }

    private String projBeskrivning() {
        String projBeskrivning = "beskrivning";
        return projBeskrivning;
    }

    private String prioritet() {
        String prioritet = "prioritet";
        return prioritet;
    }

    private String status() {
        String status = "status";
        return status;
    }

    private String startdatum() {
        String startdatum = "startdatum";
        return startdatum;
    }

    private String slutdatum() {
        String slutdatum = "slutdatum";
        return slutdatum;
    }

    private String kostnad() {
        String kostnad = "kostnad";
        return kostnad;
    }

    /**
     * Metod hämtar landet ett specifikt porjekt utförs i Parametervärde String
     * av önskade projekts ID
     */
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

    /**
     * Metod hämtar projektchef för specifikt projekt ifrån databasen
     * Parametervärde tar String av önskat porjekts ID Skriver samman för och
     * efternamn från databasen till utskriften
     */
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
        btnTillbaka = new javax.swing.JButton();
        lblProjAnstallda = new javax.swing.JLabel();
        lblPartners = new javax.swing.JLabel();
        lblH2Anstallda = new javax.swing.JLabel();
        btnEdit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblH1ProjNamn.setFont(new java.awt.Font("Typo Round Bold Demo", 1, 24)); // NOI18N
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

        btnTillbaka.setText("<- tillbaka");
        btnTillbaka.setToolTipText("");
        btnTillbaka.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true));
        btnTillbaka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTillbakaActionPerformed(evt);
            }
        });

        lblProjAnstallda.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        lblH2Anstallda.setText("Anställda inom projektet:");

        btnEdit.setText("Ändra projekt");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnTillbaka, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(137, 137, 137)
                .addComponent(btnEdit)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblH2Partners, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblBeskrivning, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblH1ProjNamn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(280, 280, 280))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblH2ProjChef, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblProjektChef, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblPartners, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblH2Anstallda, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(319, 319, 319))
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
                                        .addComponent(lblPrioritet, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(lblProjAnstallda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(317, 317, 317))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTillbaka)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
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
                .addComponent(lblBeskrivning, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblH2Prio, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(lblPrioritet, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblH2Status))
                    .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addComponent(lblH2Anstallda)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblProjAnstallda, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblH2Kostnad)
                        .addComponent(jLabel2))
                    .addComponent(lblLand, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblKostnad, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lblH2Partners)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPartners, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
                .addGap(221, 221, 221))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTillbakaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTillbakaActionPerformed
        new MinaProjekt(idb, anvandarEpost).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnTillbakaActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        new AndraProjekt(idb, projektId).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnEditActionPerformed

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
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnTillbaka;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblBeskrivning;
    private javax.swing.JLabel lblH1ProjNamn;
    private javax.swing.JLabel lblH2Anstallda;
    private javax.swing.JLabel lblH2Kostnad;
    private javax.swing.JLabel lblH2Partners;
    private javax.swing.JLabel lblH2Prio;
    private javax.swing.JLabel lblH2ProjChef;
    private javax.swing.JLabel lblH2Status;
    private javax.swing.JLabel lblKostnad;
    private javax.swing.JLabel lblLand;
    private javax.swing.JLabel lblPartners;
    private javax.swing.JLabel lblPrioritet;
    private javax.swing.JLabel lblProjAnstallda;
    private javax.swing.JLabel lblProjektChef;
    private javax.swing.JLabel lblSlutDatum;
    private javax.swing.JLabel lblStartDatum;
    private javax.swing.JLabel lblStatus;
    // End of variables declaration//GEN-END:variables
}
