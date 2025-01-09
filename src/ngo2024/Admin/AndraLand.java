/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ngo2024.Admin;

import java.util.ArrayList;
import ngo2024.Validering;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author alexanderabboud
 */
public class AndraLand extends javax.swing.JFrame {

    private InfDB idb;
    private String epost;
    private boolean kontrollOk;

    /**
     * Initierar AndraLand objekt 
     * låter administratör ändra information om ett land
     *
     * @param idb initierar fält för att interagera med databasen
     * @param epost eposten för den inloggade användaren
     */
    public AndraLand(InfDB idb, String epost) {
        this.idb = idb;
        this.epost = epost;
        kontrollOk = true;

        initComponents();
        fyllCb();
        gomAlla();
        gomBad();
        lblLyckades.setVisible(false);
        lblError.setVisible(false);

    }

    /**
     * fyller i combo box med namn på länder
     */
    public void fyllCb() {
        cbValjLand.removeAllItems();
        String sqlFraga = "SELECT namn FROM land";

        ArrayList<String> namnLista = new ArrayList<>();

        try {
            namnLista = idb.fetchColumn(sqlFraga);

            for (String namn : namnLista) {
                cbValjLand.addItem(namn);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * gömmer allt som har med valen att göra
     */
    public void gomAlla() {
        lblEkonomiBad.setVisible(false);
        lblNamnBad.setVisible(false);
        lblPolitikBad.setVisible(false);
        lblSprakBad.setVisible(false);
        lblTidzonBad.setVisible(false);
        lblValutaBad.setVisible(false);
        tfEkonomi.setVisible(false);
        tfNamn.setVisible(false);
        tfPolitik.setVisible(false);
        tfSprak.setVisible(false);
        tfTidzon.setVisible(false);
        tfValuta.setVisible(false);
        jLabel1.setVisible(false);
        jLabel2.setVisible(false);
        jLabel3.setVisible(false);
        jLabel4.setVisible(false);
        jLabel5.setVisible(false);
        jLabel6.setVisible(false);
        btnAndra.setVisible(false);

        lblLid.setVisible(false);
        lblNamn.setVisible(false);
        lblBorttagen.setVisible(false);
        btnTaBort.setVisible(false);

        cbValjLand.setVisible(false);
    }

    /**
     * hämtar ut land ID
     */
    public String selectLid() {
        String lid = "111";
        try {
            String sqlFraga = "SELECT lid FROM land where namn='" + cbValjLand.getSelectedItem() + "'";
            lid = idb.fetchSingle(sqlFraga);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return lid;
    }

    /**
     * tar bort ett land
     *
     * @param lid Land ID
     */
    public void taBortLand(int lid) {

        String sqlFraga = "DELETE FROM land WHERE lid=" + lid;
        try {
            idb.delete(sqlFraga);
            lblBorttagen.setVisible(true);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * fyller i fälten som en användaren sla ändra med det valda landet
     */
    public void fyllTabellAndra() {
        String sLid = selectLid();
        int lidL = Integer.parseInt(sLid);
        String namnS = " ";
        String sprakS = " ";
        String valutaS = " ";
        String politikS = " ";
        String ekonomiS = " ";
        String tidzonS = " ";

        try {
            namnS = idb.fetchSingle("SELECT namn FROM land WHERE lid = " + lidL);
            sprakS = idb.fetchSingle("SELECT sprak FROM land WHERE lid = " + lidL);
            valutaS = idb.fetchSingle("SELECT valuta FROM land WHERE lid = " + lidL);
            politikS = idb.fetchSingle("SELECT politisk_struktur FROM land WHERE lid = " + lidL);
            ekonomiS = idb.fetchSingle("SELECT ekonomi FROM land WHERE lid = " + lidL);
            tidzonS = idb.fetchSingle("SELECT tidszon FROM land WHERE lid = " + lidL);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        tfNamn.setText(namnS);
        tfSprak.setText(sprakS);
        tfValuta.setText(valutaS);
        tfPolitik.setText(politikS);
        tfEkonomi.setText(ekonomiS);
        tfTidzon.setText(tidzonS);

    }

    /**
     * låter användaren göra ändringar på land
     */
    public void gorAndring() {
        String sLid = selectLid();
        int lidL = Integer.parseInt(sLid);
        String namnS = tfNamn.getText();
        String sprakS = tfSprak.getText();
        String valutaS = tfValuta.getText();
        String politikS = tfPolitik.getText();
        String ekonomiS = tfEkonomi.getText();
        String tidzonS = tfTidzon.getText();
        String sqlUpdate = "UPDATE land SET namn='" + namnS + "', sprak= '" + sprakS + "', valuta= " + valutaS
                + ", tidszon= '" + tidzonS + "', politisk_struktur= '" + politikS + "', ekonomi= '"
                + ekonomiS + "' where lid=" + lidL;
        try {
            idb.update(sqlUpdate);
            System.out.println(sqlUpdate);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * gömmer alla felmeddelanden
     */
    public void gomBad() {
        lblEkonomiBad.setVisible(false);
        lblNamnBad.setVisible(false);
        lblPolitikBad.setVisible(false);
        lblSprakBad.setVisible(false);
        lblTidzonBad.setVisible(false);
        lblValutaBad.setVisible(false);
    }

    /**
     * kontrollerar allt
     */
    public void totalKontroll() {
        Boolean totOk = true;

        if (!valutaKontroll()) {
            totOk = false;
        } else if (!namnKontroll()) {
            totOk = false;
        } else if (!sprakKontroll()) {
            totOk = false;
            lblSprakBad.setVisible(true);
        } else if (!politikKontroll()) {
            totOk = false;
            lblPolitikBad.setVisible(true);
        } else if (!ekonomiKontroll()) {
            totOk = false;
            lblEkonomiBad.setVisible(true);
        } else if (!tidzonKontroll()) {
            totOk = false;
            lblTidzonBad.setVisible(true);
        } else if (sammaNamnKontroll()) {
            totOk = false;
        }
        kontrollOk = totOk;
    }

    /**
     * kontrollerar namn
     */
    public boolean namnKontroll() {
        Validering valid = new Validering(idb);
        String namn = tfNamn.getText();
        if (valid.checkMeningOSiffra(namn) && valid.checkStorlek(100, namn)) {
            lblNamnBad.setVisible(false);
            return true;
        } else {
            lblNamnBad.setVisible(true);
            return false;
        }
    }

    /**
     * kontrollera ???
     */
    public boolean sammaNamnKontroll() {
        boolean samma = false;
        boolean sSamma = true;
        boolean retur = false;
        ArrayList<String> namnLista = new ArrayList<>();
        String sqlFraga = "SELECT namn FROM land";
        try {
            namnLista = idb.fetchColumn(sqlFraga);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        String valtLand = (String) cbValjLand.getSelectedItem();

        if (tfNamn.getText().equals(valtLand)) {
            sSamma = false;
        }

        for (String namn : namnLista) {
            if (namn.equals(tfNamn.getText())) {
                samma = true;
            }
        }
        if (sSamma == false) {
            retur = sSamma;
        } else if (samma == true) {
            lblNamnBad.setVisible(true);
            retur = samma;
        }
        return retur;
    }

    /**
     * kontrollera språket i ett land
     */
    public boolean sprakKontroll() {
        Validering valid = new Validering(idb);
        String sprak = tfSprak.getText();
        if (valid.checkMeningOSiffra(sprak) && valid.checkStorlek(100, sprak)) {
            lblSprakBad.setVisible(false);
            return true;
        } else {
            lblSprakBad.setVisible(true);
            return false;
        }
    }

    /**
     * kontrollerar politik???
     */
    public boolean politikKontroll() {
        Validering valid = new Validering(idb);
        String politik = tfPolitik.getText();
        if (valid.checkMeningOSiffra(politik) && valid.checkStorlek(255, politik)) {
            lblPolitikBad.setVisible(false);
            return true;
        } else {
            lblPolitikBad.setVisible(true);
            return false;
        }
    }

    /**
     * kontrollerar ett lands ekonomiska status
     */
    public boolean ekonomiKontroll() {
        Validering valid = new Validering(idb);
        String ekonomi = tfEkonomi.getText();
        if (valid.checkMeningOSiffra(ekonomi) && valid.checkStorlek(255, ekonomi)) {
            lblEkonomiBad.setVisible(false);
            return true;
        } else {
            lblEkonomiBad.setVisible(true);
            return false;
        }
    }

    /**
     * kontrollerar vilken tidzon ett land ligger i
     */
    public boolean tidzonKontroll() {
        Validering valid = new Validering(idb);
        String tidzon = tfTidzon.getText();
        if (valid.checkMeningOSiffra(tidzon) && valid.checkStorlek(20, tidzon)) {
            lblTidzonBad.setVisible(false);
            return true;
        } else {
            lblTidzonBad.setVisible(true);
            return false;
        }
    }

    /**
     * kontrollerar vilken valuta ett land har
     */
    public boolean valutaKontroll() {
        Validering valid = new Validering(idb);
        String valuta = tfValuta.getText();
        if (valid.checkValuta(valuta)) {
            lblValutaBad.setVisible(false);
            return true;
        } else {
            lblValutaBad.setVisible(true);
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

        cbhAndra = new javax.swing.JCheckBox();
        cbhTaBort = new javax.swing.JCheckBox();
        cbValjLand = new javax.swing.JComboBox<>();
        tfNamn = new javax.swing.JTextField();
        tfSprak = new javax.swing.JTextField();
        tfValuta = new javax.swing.JTextField();
        tfTidzon = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        tfPolitik = new javax.swing.JTextField();
        tfEkonomi = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblNamnBad = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblSprakBad = new javax.swing.JLabel();
        lblValutaBad = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblTidzonBad = new javax.swing.JLabel();
        lblPolitikBad = new javax.swing.JLabel();
        lblEkonomiBad = new javax.swing.JLabel();
        lblNamn = new javax.swing.JLabel();
        btnTaBort = new javax.swing.JButton();
        lblBorttagen = new javax.swing.JLabel();
        lblLid = new javax.swing.JLabel();
        btnTillbaka = new javax.swing.JButton();
        btnAndra = new javax.swing.JButton();
        btnValj = new javax.swing.JButton();
        lblLyckades = new javax.swing.JLabel();
        lblError = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        cbhAndra.setText("Ändra");
        cbhAndra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbhAndraActionPerformed(evt);
            }
        });

        cbhTaBort.setText("Ta bort");
        cbhTaBort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbhTaBortActionPerformed(evt);
            }
        });

        cbValjLand.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {}));

        tfNamn.setText("Namn");
        tfNamn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfNamnActionPerformed(evt);
            }
        });

        tfSprak.setText("Språk");

        tfValuta.setText("12.123");

        tfTidzon.setText("Tidzon x");

        jLabel1.setText("Namn");

        tfPolitik.setText("Politisk Struktur x");

        tfEkonomi.setText("Ekonomi x");
        tfEkonomi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfEkonomiActionPerformed(evt);
            }
        });

        jLabel2.setText("Språk");

        jLabel3.setText("Valuta");

        jLabel4.setText("Tidzon");

        lblNamnBad.setForeground(new java.awt.Color(255, 0, 51));
        lblNamnBad.setText("!");

        jLabel5.setText("Politisk Struktur");

        lblSprakBad.setForeground(new java.awt.Color(255, 0, 51));
        lblSprakBad.setText("!");

        lblValutaBad.setForeground(new java.awt.Color(255, 0, 51));
        lblValutaBad.setText("!");

        jLabel6.setText("Ekonomi");

        lblTidzonBad.setForeground(new java.awt.Color(255, 0, 51));
        lblTidzonBad.setText("!");

        lblPolitikBad.setForeground(new java.awt.Color(255, 0, 51));
        lblPolitikBad.setText("!");

        lblEkonomiBad.setForeground(new java.awt.Color(255, 0, 51));
        lblEkonomiBad.setText("!");

        lblNamn.setText("Namn");

        btnTaBort.setText("Ta bort");
        btnTaBort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaBortActionPerformed(evt);
            }
        });

        lblBorttagen.setForeground(new java.awt.Color(0, 204, 0));
        lblBorttagen.setText("Användare borttagen");
        lblBorttagen.setBorder(new javax.swing.border.MatteBorder(null));
        lblBorttagen.setMaximumSize(new java.awt.Dimension(211, 17));
        lblBorttagen.setSize(new java.awt.Dimension(100, 17));

        lblLid.setText("lid");

        btnTillbaka.setText("X");
        btnTillbaka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTillbakaActionPerformed(evt);
            }
        });

        btnAndra.setText("Ändra");
        btnAndra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAndraActionPerformed(evt);
            }
        });

        btnValj.setText("Välj");
        btnValj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnValjActionPerformed(evt);
            }
        });

        lblLyckades.setForeground(new java.awt.Color(0, 204, 0));
        lblLyckades.setText("Lyckades");

        lblError.setForeground(new java.awt.Color(255, 51, 0));
        lblError.setText("Error!");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cbhAndra)
                        .addGap(18, 18, 18)
                        .addComponent(cbhTaBort)
                        .addGap(23, 23, 23)
                        .addComponent(btnTaBort)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnTillbaka))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblLid)
                                .addGap(22, 22, 22)
                                .addComponent(lblNamn, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cbValjLand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnValj)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblBorttagen, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfNamn, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblNamnBad))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(btnAndra))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblLyckades)
                                        .addGap(47, 47, 47)
                                        .addComponent(lblError))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(tfEkonomi)
                                            .addComponent(tfPolitik)
                                            .addComponent(tfTidzon)
                                            .addComponent(tfValuta)
                                            .addComponent(tfSprak, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblSprakBad)
                                            .addComponent(lblValutaBad)
                                            .addComponent(lblTidzonBad)
                                            .addComponent(lblPolitikBad)
                                            .addComponent(lblEkonomiBad))))))
                        .addGap(0, 56, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbhAndra)
                    .addComponent(cbhTaBort)
                    .addComponent(btnTaBort)
                    .addComponent(btnTillbaka))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbValjLand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnValj))
                    .addComponent(lblBorttagen, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLid)
                    .addComponent(lblNamn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tfNamn, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNamnBad))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfSprak, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSprakBad))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblValutaBad, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(tfValuta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAndra)
                    .addComponent(lblLyckades)
                    .addComponent(lblError))
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tfNamnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfNamnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfNamnActionPerformed

    private void btnTaBortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaBortActionPerformed
        String stringInt = lblLid.getText();
        int iInt = Integer.parseInt(stringInt);
        taBortLand(iInt);
        fyllCb();

    }//GEN-LAST:event_btnTaBortActionPerformed

    private void btnTillbakaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTillbakaActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnTillbakaActionPerformed

    private void cbhTaBortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbhTaBortActionPerformed
        if (cbhTaBort.isSelected()) {
            cbhAndra.setSelected(false);
            gomAndra();
        }
    }//GEN-LAST:event_cbhTaBortActionPerformed
    public void gomAndra() {

        //göm allt om Ändra
        lblEkonomiBad.setVisible(false);
        lblNamnBad.setVisible(false);
        lblPolitikBad.setVisible(false);
        lblSprakBad.setVisible(false);
        lblTidzonBad.setVisible(false);
        lblValutaBad.setVisible(false);
        tfEkonomi.setVisible(false);
        tfNamn.setVisible(false);
        tfPolitik.setVisible(false);
        tfSprak.setVisible(false);
        tfTidzon.setVisible(false);
        tfValuta.setVisible(false);
        jLabel1.setVisible(false);
        jLabel2.setVisible(false);
        jLabel3.setVisible(false);
        jLabel4.setVisible(false);
        jLabel5.setVisible(false);
        jLabel6.setVisible(false);
        btnAndra.setVisible(false);

        // så att man kan se de som tillhör ta bort
        lblLid.setVisible(true);
        lblNamn.setVisible(true);
        btnTaBort.setVisible(true);
        cbValjLand.setVisible(true);
    }

    public void gomTaBort() {
        // Visa allt om Ändra

        tfEkonomi.setVisible(true);
        tfNamn.setVisible(true);
        tfPolitik.setVisible(true);
        tfSprak.setVisible(true);
        tfTidzon.setVisible(true);
        tfValuta.setVisible(true);
        jLabel1.setVisible(true);
        jLabel2.setVisible(true);
        jLabel3.setVisible(true);
        jLabel4.setVisible(true);
        jLabel5.setVisible(true);
        jLabel6.setVisible(true);
        btnAndra.setVisible(true);
        cbValjLand.setVisible(true);

        // Göm det som tillhör ta bort
        lblLid.setVisible(false);
        lblNamn.setVisible(false);
        lblBorttagen.setVisible(false);
        btnTaBort.setVisible(false);
    }


    private void cbhAndraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbhAndraActionPerformed
        if (cbhAndra.isSelected()) {
            cbhTaBort.setSelected(false);
            gomTaBort();
        }
    }//GEN-LAST:event_cbhAndraActionPerformed

    private void btnValjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnValjActionPerformed
        if (cbhTaBort.isSelected()) {
            lblLid.setText(selectLid());
            int i = cbValjLand.getSelectedIndex();
            lblNamn.setText(cbValjLand.getItemAt(i));
        } else if (cbhAndra.isSelected()) {
            fyllTabellAndra();
        }


    }//GEN-LAST:event_btnValjActionPerformed

    private void tfEkonomiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfEkonomiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfEkonomiActionPerformed

    private void btnAndraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAndraActionPerformed
        totalKontroll();
        if (kontrollOk == true) {
            gorAndring();
            lblLyckades.setVisible(true);
            lblError.setVisible(false);
            fyllCb();
        } else {
            lblError.setVisible(true);
            lblLyckades.setVisible(false);
        }


    }//GEN-LAST:event_btnAndraActionPerformed

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
            java.util.logging.Logger.getLogger(AndraLand.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AndraLand.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AndraLand.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AndraLand.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // new AndraLand().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAndra;
    private javax.swing.JButton btnTaBort;
    private javax.swing.JButton btnTillbaka;
    private javax.swing.JButton btnValj;
    private javax.swing.JComboBox<String> cbValjLand;
    private javax.swing.JCheckBox cbhAndra;
    private javax.swing.JCheckBox cbhTaBort;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel lblBorttagen;
    private javax.swing.JLabel lblEkonomiBad;
    private javax.swing.JLabel lblError;
    private javax.swing.JLabel lblLid;
    private javax.swing.JLabel lblLyckades;
    private javax.swing.JLabel lblNamn;
    private javax.swing.JLabel lblNamnBad;
    private javax.swing.JLabel lblPolitikBad;
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
