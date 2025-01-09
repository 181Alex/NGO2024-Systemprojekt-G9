/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ngo2024.Admin;

import ngo2024.Validering;
import oru.inf.InfDB;
import oru.inf.InfException;
import java.util.ArrayList;

/**
 *
 * @author alexanderabboud
 */
public class AndraPartner extends javax.swing.JFrame {

    private InfDB idb;
    private String epost;
    boolean kontrollOk;
    private String ogEpost;
    private String ogTelefon;

    /**
     * Initierar AndraPartner objekt 
     * låter administratör ändra information om en partner
     *
     * @param idb initierar fält för att interagera med databasen
     * @param epost eposten för den inloggade användaren
     */
    public AndraPartner(InfDB idb, String epost) {
        this.idb = idb;
        this.epost = epost;
        initComponents();
        kontrollOk = false;
        fyllStad();
        fyllPartner();
        gomAlla();
    }

    /**
     * fyller i combo box med namn på städer
     */
    private void fyllStad() {
        cbStad.removeAllItems();
        String sqlFraga = "SELECT namn FROM stad";

        ArrayList<String> namnLista = new ArrayList<>();

        try {
            namnLista = idb.fetchColumn(sqlFraga);

            for (String namn : namnLista) {
                cbStad.addItem(namn);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * fyller i combo box med namn på partners
     */
    private void fyllPartner() {
        cbValjPartner.removeAllItems();
        String sqlFraga = "SELECT namn FROM partner";

        ArrayList<String> namnLista = new ArrayList<>();

        try {
            namnLista = idb.fetchColumn(sqlFraga);

            for (String namn : namnLista) {
                cbValjPartner.addItem(namn);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * hämtar ut partner ID
     */
    public String selectPid() {
        String pid = "111";
        String vald = (String) cbValjPartner.getSelectedItem();
        try {
            String sqlFraga = "SELECT pid FROM partner where namn='" + vald + "'";
            pid = idb.fetchSingle(sqlFraga);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return pid;
    }

    /**
     * gömmer allt
     */
    private void gomAlla() {
        tfTelefon.setVisible(false);
        tfNamn.setVisible(false);
        tfKPerson.setVisible(false);
        tfEpost.setVisible(false);
        tfBransch.setVisible(false);
        tfAdress.setVisible(false);
        lblTelefon.setVisible(false);
        lblNamn.setVisible(false);
        lblKPerson.setVisible(false);
        lblNamnTf.setVisible(false);
        lblBransch.setVisible(false);
        lblAdress.setVisible(false);
        lblStad.setVisible(false);
        cbStad.setVisible(false);
        lblTelefonBad.setVisible(false);
        lblNamnBad.setVisible(false);
        lblKPersonBad.setVisible(false);
        lblAdressBad.setVisible(false);
        lblBranschBad.setVisible(false);
        lblEpostBad.setVisible(false);
        lblEpost.setVisible(false);
        lblLyckades.setVisible(false);
        lblError.setVisible(false);
        lblBorttagen.setVisible(false);
        btnAndra.setVisible(false);
        btnTaBort.setVisible(false);
        lblNamn.setVisible(false);
        lblPid.setVisible(false);
        cbValjPartner.setVisible(false);
        btnValj.setVisible(false);

    }

    /**
     * kontrollerar allt
     */
    public void totalKontroll() {
        Boolean totOk = true;

        if (!kPersonKontroll()) {
            totOk = false;
            lblKPersonBad.setVisible(true);

        } else if (!namnKontroll()) {
            totOk = false;
            lblNamnBad.setVisible(true);

        } else if (!epostKontroll()) {
            totOk = false;
            lblEpostBad.setVisible(true);
        } else if (!telefonKontroll()) {
            totOk = false;
            lblTelefonBad.setVisible(true);
        } else if (!adressKontroll()) {
            totOk = false;
            lblAdressBad.setVisible(true);
        } else if (!branschKontroll()) {
            totOk = false;
            lblBranschBad.setVisible(true);
        } else if (!sammaEpostKontroll()) {
            totOk = false;
        }

        kontrollOk = totOk;
    }

    private boolean sammaEpostKontroll() {
        Validering valid = new Validering(idb);

        // Hämta text från textfältet
        String epost = tfEpost.getText();
        if (ogEpost.equals(epost)) {
            lblEpostBad.setVisible(false); // Göm varning
            return true;
        } else if (valid.checkInteSammaEpost(epost)) {
            lblEpostBad.setVisible(false); // Göm varning
            return true;
        } else {
            lblEpostBad.setVisible(true); // Visa varning
            return false;

        }
    }

    /**
     * kontrollerar att kontaktpersones namn är valid
     */
    public boolean kPersonKontroll() {
        Validering valid = new Validering(idb);
        String namn = tfKPerson.getText();
        // kontrollerar kPerson vilekt är ett namn
        if (valid.checkNamn(namn) && valid.checkStorlek(255, namn)) {
            lblKPersonBad.setVisible(false);
            return true;
        } else {
            lblKPersonBad.setVisible(true);
            return false;
        }
    }

    /**
     * kontrollerar namn formatet
     */
    public boolean namnKontroll() {
        Validering valid = new Validering(idb);
        String namn = tfNamn.getText();
        if (valid.checkNamn(namn) && valid.checkStorlek(255, namn)) {
            lblNamnBad.setVisible(false);
            return true;
        } else {
            lblNamnBad.setVisible(true);
            return false;
        }
    }

    /**
     * kontrollerar att epost är valid
     */
    public boolean epostKontroll() {
        Validering valid = new Validering(idb);
        String epost = tfEpost.getText();
        if (valid.checkEpost(epost) && valid.checkStorlek(255, epost)) {
            lblEpostBad.setVisible(false);
            return true;
        } else {
            lblEpostBad.setVisible(true);
            return false;
        }
    }

    /**
     * kontrollerar att telefonnummer är valid
     */
    public boolean telefonKontroll() {
        Validering valid = new Validering(idb);
        String telefon = tfTelefon.getText();
        if (valid.checkTelefon(telefon) && valid.checkStorlek(20, telefon)) {
            lblTelefonBad.setVisible(false);
            return true;
        } else {
            lblTelefonBad.setVisible(true);
            return false;
        }
    }

    /**
     * kontrollerar att adressen är valid
     */
    public boolean adressKontroll() {
        Validering valid = new Validering(idb);
        String adress = tfAdress.getText();
        // kontrolelrar adress format
        if (valid.checkAdress(adress) && valid.checkStorlek(255, adress)) {
            lblAdressBad.setVisible(false);
            return true;
        } else {
            lblAdressBad.setVisible(true);
            return false;
        }
    }

    /**
     * kontrollerar att bransch namn är valid
     */
    public boolean branschKontroll() {
        Validering valid = new Validering(idb);
        String bransch = tfBransch.getText();
        if (valid.checkFornamn(bransch) && valid.checkStorlek(255, bransch)) {
            lblBranschBad.setVisible(false);
            return true;
        } else {
            lblBranschBad.setVisible(true);
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

        cbValjPartner = new javax.swing.JComboBox<>();
        cbhTaBort = new javax.swing.JCheckBox();
        cbhAndra = new javax.swing.JCheckBox();
        btnTaBort = new javax.swing.JButton();
        lblNamn = new javax.swing.JLabel();
        btnValj = new javax.swing.JButton();
        lblPid = new javax.swing.JLabel();
        lblBorttagen = new javax.swing.JLabel();
        lblEpost = new javax.swing.JLabel();
        lblNamnTf = new javax.swing.JLabel();
        lblBransch = new javax.swing.JLabel();
        lblAdress = new javax.swing.JLabel();
        lblTelefon = new javax.swing.JLabel();
        lblKPerson = new javax.swing.JLabel();
        tfBransch = new javax.swing.JTextField();
        tfAdress = new javax.swing.JTextField();
        tfKPerson = new javax.swing.JTextField();
        tfTelefon = new javax.swing.JTextField();
        tfEpost = new javax.swing.JTextField();
        tfNamn = new javax.swing.JTextField();
        cbStad = new javax.swing.JComboBox<>();
        lblStad = new javax.swing.JLabel();
        btClose = new javax.swing.JButton();
        lblError = new javax.swing.JLabel();
        lblLyckades = new javax.swing.JLabel();
        btnAndra = new javax.swing.JButton();
        lblBranschBad = new javax.swing.JLabel();
        lblAdressBad = new javax.swing.JLabel();
        lblTelefonBad = new javax.swing.JLabel();
        lblEpostBad = new javax.swing.JLabel();
        lblKPersonBad = new javax.swing.JLabel();
        lblNamnBad = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        cbValjPartner.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {}));

        cbhTaBort.setText("Ta bort");
        cbhTaBort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbhTaBortActionPerformed(evt);
            }
        });

        cbhAndra.setText("Ändra");
        cbhAndra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbhAndraActionPerformed(evt);
            }
        });

        btnTaBort.setText("Ta bort");
        btnTaBort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaBortActionPerformed(evt);
            }
        });

        lblNamn.setText("Namn");

        btnValj.setText("Välj");
        btnValj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnValjActionPerformed(evt);
            }
        });

        lblPid.setText("Pid");

        lblBorttagen.setForeground(new java.awt.Color(0, 204, 0));
        lblBorttagen.setText("Partner borttagen");
        lblBorttagen.setBorder(new javax.swing.border.MatteBorder(null));
        lblBorttagen.setMaximumSize(new java.awt.Dimension(211, 17));
        lblBorttagen.setSize(new java.awt.Dimension(100, 17));

        lblEpost.setText("Kontakt epost");

        lblNamnTf.setText("Namn");

        lblBransch.setText("Bransch");

        lblAdress.setText("Adress");

        lblTelefon.setText("Telefon");

        lblKPerson.setText("Kontaktperson");

        tfBransch.setText("Bransch");

        tfAdress.setText("123 exempel, bobla");

        tfKPerson.setText("KontaktPerson");

        tfTelefon.setText("123-123-1234");

        tfEpost.setText("example@mail.com");

        tfNamn.setText("Namn");
        tfNamn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfNamnActionPerformed(evt);
            }
        });

        cbStad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {}));

        lblStad.setText("Stad");

        btClose.setText("X");
        btClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCloseActionPerformed(evt);
            }
        });

        lblError.setForeground(new java.awt.Color(255, 51, 0));
        lblError.setText("Error!");

        lblLyckades.setForeground(new java.awt.Color(0, 204, 0));
        lblLyckades.setText("Lyckades");

        btnAndra.setText("Ändra");
        btnAndra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAndraActionPerformed(evt);
            }
        });

        lblBranschBad.setForeground(new java.awt.Color(255, 0, 51));
        lblBranschBad.setText("!");

        lblAdressBad.setForeground(new java.awt.Color(255, 0, 51));
        lblAdressBad.setText("!");

        lblTelefonBad.setForeground(new java.awt.Color(255, 0, 51));
        lblTelefonBad.setText("!");

        lblEpostBad.setForeground(new java.awt.Color(255, 0, 51));
        lblEpostBad.setText("!");

        lblKPersonBad.setForeground(new java.awt.Color(255, 0, 51));
        lblKPersonBad.setText("!");

        lblNamnBad.setForeground(new java.awt.Color(255, 0, 51));
        lblNamnBad.setText("!");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(cbValjPartner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnValj))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblPid)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblNamn, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cbhAndra)
                                .addGap(18, 18, 18)
                                .addComponent(cbhTaBort)
                                .addGap(23, 23, 23)
                                .addComponent(btnTaBort)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)))
                        .addComponent(btClose)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNamnTf, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(lblKPerson, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblAdress, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblBransch, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblTelefon, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblEpost, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblStad))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cbStad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(tfBransch)
                                        .addComponent(tfAdress)
                                        .addComponent(tfTelefon)
                                        .addComponent(tfEpost)
                                        .addComponent(tfKPerson, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(tfNamn, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNamnBad, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblAdressBad, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblBranschBad, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblTelefonBad, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblEpostBad, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblKPersonBad, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addContainerGap())))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAndra)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblLyckades)
                        .addGap(47, 47, 47)
                        .addComponent(lblError)
                        .addGap(18, 18, 18)
                        .addComponent(lblBorttagen, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbhAndra)
                            .addComponent(cbhTaBort)
                            .addComponent(btnTaBort))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbValjPartner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnValj))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPid)
                            .addComponent(lblNamn)))
                    .addComponent(btClose))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNamnTf)
                            .addComponent(tfNamn, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNamnBad))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblKPerson, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfKPerson, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblKPersonBad))
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEpost)
                            .addComponent(tfEpost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEpostBad))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTelefon)
                            .addComponent(tfTelefon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTelefonBad))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblAdress)
                            .addComponent(tfAdress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblAdressBad))
                        .addGap(18, 18, 18)
                        .addComponent(lblBransch))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tfBransch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblBranschBad)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblStad)
                    .addComponent(cbStad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAndra)
                    .addComponent(lblLyckades)
                    .addComponent(lblError)
                    .addComponent(lblBorttagen, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbhTaBortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbhTaBortActionPerformed
        if (cbhTaBort.isSelected()) {
            cbhAndra.setSelected(false);
            gomAndra();
        }
    }//GEN-LAST:event_cbhTaBortActionPerformed

    private void gomAndra() {
        // tar bort allt som tillhör ändra och visar ta bort fälten
        tfTelefon.setVisible(false);
        tfNamn.setVisible(false);
        tfKPerson.setVisible(false);
        tfEpost.setVisible(false);
        tfBransch.setVisible(false);
        tfAdress.setVisible(false);
        lblTelefon.setVisible(false);
        lblNamn.setVisible(false);
        lblKPerson.setVisible(false);
        lblNamnTf.setVisible(false);
        lblBransch.setVisible(false);
        lblAdress.setVisible(false);
        lblStad.setVisible(false);
        cbStad.setVisible(false);
        lblTelefonBad.setVisible(false);
        lblNamnBad.setVisible(false);
        lblKPersonBad.setVisible(false);
        lblAdressBad.setVisible(false);
        lblBranschBad.setVisible(false);
        lblEpostBad.setVisible(false);
        lblEpost.setVisible(false);
        lblLyckades.setVisible(false);
        lblError.setVisible(false);
        lblBorttagen.setVisible(false);
        btnAndra.setVisible(false);
        btnTaBort.setVisible(true);
        lblNamn.setVisible(true);
        lblPid.setVisible(true);
        cbValjPartner.setVisible(true);
        btnValj.setVisible(true);
    }


    private void cbhAndraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbhAndraActionPerformed
        if (cbhAndra.isSelected()) {
            cbhTaBort.setSelected(false);
            gomTaBort();
        }
    }//GEN-LAST:event_cbhAndraActionPerformed

    private void gomTaBort() {
        // tar bort allt som tillhör tabort
        tfTelefon.setVisible(true);
        tfNamn.setVisible(true);
        tfKPerson.setVisible(true);
        tfEpost.setVisible(true);
        tfBransch.setVisible(true);
        tfAdress.setVisible(true);
        lblTelefon.setVisible(true);
        lblNamn.setVisible(true);
        lblKPerson.setVisible(true);
        lblNamnTf.setVisible(true);
        lblBransch.setVisible(true);
        lblAdress.setVisible(true);
        lblStad.setVisible(true);
        cbStad.setVisible(true);
        lblTelefonBad.setVisible(false);
        lblNamnBad.setVisible(false);
        lblKPersonBad.setVisible(false);
        lblAdressBad.setVisible(false);
        lblBranschBad.setVisible(false);
        lblEpostBad.setVisible(false);
        lblEpost.setVisible(true);
        lblLyckades.setVisible(false);
        lblError.setVisible(false);
        lblBorttagen.setVisible(false);
        btnAndra.setVisible(true);
        btnTaBort.setVisible(false);
        lblNamn.setVisible(false);
        lblPid.setVisible(false);
        cbValjPartner.setVisible(true);
        btnValj.setVisible(true);
    }


    private void btnTaBortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaBortActionPerformed
        String sqlFraga = "DELETE FROM partner WHERE pid=" + lblPid.getText();
        try {
            idb.delete(sqlFraga);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        lblBorttagen.setVisible(true);
        fyllPartner();
    }//GEN-LAST:event_btnTaBortActionPerformed

    public void setAndra() {
        // fyller fälten som användaren ska ändra med det valda landet.
        String pidL = selectPid();
        String namnS = " ";
        String kPS = " ";
        String telefonS = " ";
        String adressS = " ";
        String branschS = " ";
        String ePostS = " ";
        String stad = " ";

        try {
            namnS = idb.fetchSingle("SELECT namn FROM partner WHERE pid = " + pidL);
            kPS = idb.fetchSingle("SELECT kontaktperson FROM partner WHERE pid = " + pidL);
            telefonS = idb.fetchSingle("SELECT telefon FROM partner WHERE pid = " + pidL);
            adressS = idb.fetchSingle("SELECT adress FROM partner WHERE pid = " + pidL);
            branschS = idb.fetchSingle("SELECT branch FROM partner WHERE pid = " + pidL);
            ePostS = idb.fetchSingle("SELECT kontaktepost FROM partner WHERE pid = " + pidL);
            stad = idb.fetchSingle("SELECT stad from partner where pid= " + pidL);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        int stadS = Integer.parseInt(stad);
        tfNamn.setText(namnS);
        tfKPerson.setText(kPS);
        tfTelefon.setText(telefonS);
        tfAdress.setText(adressS);
        tfBransch.setText(branschS);
        tfEpost.setText(ePostS);
        cbStad.setSelectedIndex(stadS);
        ogEpost = tfEpost.getText();
        ogTelefon = tfTelefon.getText();
    }


    private void btnValjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnValjActionPerformed
        if (cbhAndra.isSelected()) {
            setAndra();
        } else if (cbhTaBort.isSelected()) {
            lblPid.setText(selectPid());
            String namn = (String) cbValjPartner.getSelectedItem();
            lblNamn.setText(namn);
        }
    }//GEN-LAST:event_btnValjActionPerformed

    private void tfNamnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfNamnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfNamnActionPerformed

    private void btCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_btCloseActionPerformed

    public String getStad() {
        int i = cbStad.getSelectedIndex();
        String aid = "";
        String sql = "SELECT sid FROM stad WHERE namn='" + cbStad.getItemAt(i) + "'";
        System.out.println(sql);
        try {
            String sAid = idb.fetchSingle(sql);
            aid = sAid;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return aid;
    }

    public void gorAndring() {
        //hämta vilket objekt som ska ändras
        String pidL = selectPid();
        String stad = getStad();

        String namn = tfNamn.getText();
        String kp = tfKPerson.getText();
        String epost = tfEpost.getText();
        String telefon = tfTelefon.getText();
        String adress = tfAdress.getText();
        String branch = tfBransch.getText();
        String sqlFraga = "UPDATE partner SET namn = '" + namn + "', kontaktperson= '" + kp
                + "', kontaktepost = '" + epost + "' , telefon= '" + telefon + "', adress= '"
                + adress + "', branch= '" + branch + "', stad= " + stad + " WHERE pid= " + pidL;
        System.out.println(sqlFraga);
        try {
            idb.update(sqlFraga);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    private void btnAndraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAndraActionPerformed
        totalKontroll();
        if (kontrollOk == true) {
            gorAndring();
            lblLyckades.setVisible(true);
            lblError.setVisible(false);
            fyllPartner();
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
            java.util.logging.Logger.getLogger(AndraPartner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AndraPartner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AndraPartner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AndraPartner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // new AndraPartner().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btClose;
    private javax.swing.JButton btnAndra;
    private javax.swing.JButton btnTaBort;
    private javax.swing.JButton btnValj;
    private javax.swing.JComboBox<String> cbStad;
    private javax.swing.JComboBox<String> cbValjPartner;
    private javax.swing.JCheckBox cbhAndra;
    private javax.swing.JCheckBox cbhTaBort;
    private javax.swing.JLabel lblAdress;
    private javax.swing.JLabel lblAdressBad;
    private javax.swing.JLabel lblBorttagen;
    private javax.swing.JLabel lblBransch;
    private javax.swing.JLabel lblBranschBad;
    private javax.swing.JLabel lblEpost;
    private javax.swing.JLabel lblEpostBad;
    private javax.swing.JLabel lblError;
    private javax.swing.JLabel lblKPerson;
    private javax.swing.JLabel lblKPersonBad;
    private javax.swing.JLabel lblLyckades;
    private javax.swing.JLabel lblNamn;
    private javax.swing.JLabel lblNamnBad;
    private javax.swing.JLabel lblNamnTf;
    private javax.swing.JLabel lblPid;
    private javax.swing.JLabel lblStad;
    private javax.swing.JLabel lblTelefon;
    private javax.swing.JLabel lblTelefonBad;
    private javax.swing.JTextField tfAdress;
    private javax.swing.JTextField tfBransch;
    private javax.swing.JTextField tfEpost;
    private javax.swing.JTextField tfKPerson;
    private javax.swing.JTextField tfNamn;
    private javax.swing.JTextField tfTelefon;
    // End of variables declaration//GEN-END:variables
}
