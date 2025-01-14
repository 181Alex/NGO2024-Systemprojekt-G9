/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ngo2024;

import javax.swing.JOptionPane;
import ngo2024.Admin.AdminMeny;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author Amali
 */
public class MinProfil extends javax.swing.JFrame {

    private InfDB idb;
    private String epost;
    private String aid;
    private String firstname;
    private String lastname;
    private String department;
    private String telefon;
    private String adress;
    private boolean isEditing = false;

    /**
     * Initierar MinProfil objekt 
     * Fönster med inloggad användares information
     * Möjlighet att redigera användar information
     *
     * @param idb initierar fält för att interagera med databasen
     * @param aid inloggad användar ID
     * @param epost inloggad användar epost
     */
    public MinProfil(InfDB idb, String aid, String epost) {
        this.idb = idb;
        this.epost = epost;
        this.aid = aid;
        this.firstname = getfirstname(aid);
        this.lastname = getlastname(aid);
        this.department = getdepartment(aid);
        this.adress = getAdress(aid);
        this.telefon = getTelefon(aid);

        String password = getPassword(aid);
        initComponents();

        txtFirstName.setText(firstname != null ? firstname : "");
        txtLastName.setText(lastname != null ? lastname : "");
        txtEmail.setText(epost != null ? epost : "");
        txtDepartment.setText(department != null ? department : "");
        txtPassword.setText(password != null ? password : "");
        txtPassword.setEchoChar('*');
        lblFelInmatning.setVisible(false);
        tfTelefon.setText(telefon != null ? telefon : "");
        tfAdress.setText(adress != null ? adress : "");
        gomBad();
    }

    public MinProfil(InfDB idb) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void gomBad() {
        lblFornamnBad1.setVisible(false);
        lblEfternamnBad.setVisible(false);
        lblTelefonBad.setVisible(false);
        lblEpostBad.setVisible(false);
        lblAdressBad.setVisible(false);
        lblLosenBad.setVisible(false);
    }

    private String getfirstname(String aid) {
        String txtFirstName = "";
        try {
            String sqlQuery = "SELECT fornamn FROM anstalld WHERE aid = '" + aid + "'";
            txtFirstName = idb.fetchSingle(sqlQuery);
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
        return txtFirstName;
    }

    private String getlastname(String aid) {
        String txtLastName = "";
        try {
            String sqlQuery = "SELECT efternamn FROM anstalld WHERE aid = '" + aid + "'";
            txtLastName = idb.fetchSingle(sqlQuery);
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
        return txtLastName;
    }

    private String getAdress(String aid) {
        String txtAdress = "";
        try {
            String sqlQuery = "SELECT adress FROM anstalld WHERE aid = '" + aid + "'";
            txtAdress = idb.fetchSingle(sqlQuery);
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
        return txtAdress;
    }

    private String getTelefon(String aid) {
        String txtTelefon = "";
        try {
            String sqlQuery = "SELECT telefon FROM anstalld WHERE aid = '" + aid + "'";
            txtTelefon = idb.fetchSingle(sqlQuery);
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
        return txtTelefon;
    }

    private boolean isEmailTaken(String email) {
        try {
            String sqlQuery = "SELECT epost FROM anstalld WHERE epost = '" + email + "'";
            String result = idb.fetchSingle(sqlQuery);
            return result != null;
        } catch (InfException ex) {
            System.out.println("Fel vid kontroll av eposten:" + ex.getMessage());
            return false;
        }
    }

    private String getdepartment(String aid) {
        String txtDepartment = "";
        try {
            String sqlQuery = "SELECT namn FROM avdelning WHERE avdid=" + "(SELECT avdelning FROM anstalld WHERE aid = '" + aid + "')";
            txtDepartment = idb.fetchSingle(sqlQuery);
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
        return txtDepartment;
    }

    private String getPassword(String aid) {
        String password = "";
        try {
            String sqlQuery = "SELECT losenord FROM anstalld WHERE aid ='" + aid + "'";
            password = idb.fetchSingle(sqlQuery);
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
        return password;
    }

    public MinProfil() {
        initComponents();
        txtFirstName.setText(firstname);
        txtLastName.setText(lastname);
        txtEmail.setText(epost);
        txtPassword.setText("");
        tfAdress.setText(adress);
        tfTelefon.setText(telefon);

        txtDepartment.setText(department);

        txtFirstName.setEditable(false);
        txtLastName.setEditable(false);
        txtEmail.setEditable(false);
        txtPassword.setEditable(false);
        tfAdress.setEditable(false);
        tfTelefon.setEditable(false);

    }

    private void onChangeClicked() {
        txtFirstName.setEditable(true);
        txtLastName.setEditable(true);
        txtEmail.setEditable(true);
        txtPassword.setEditable(true);
        tfAdress.setEditable(true);
        tfTelefon.setEditable(true);
        txtPassword.setText(getPassword(aid));

        Change.setText("Spara");
        Change.removeActionListener(Change.getActionListeners()[0]);
        Change.addActionListener(evt -> onSavedClicked());
    }

    private void onSavedClicked() {
        firstname = txtFirstName.getText();
        lastname = txtLastName.getText();
        epost = txtEmail.getText();
        telefon = tfTelefon.getText();
        adress = tfAdress.getText();

        txtFirstName.setEditable(false);
        txtLastName.setEditable(false);
        txtEmail.setEditable(false);
        txtPassword.setEditable(false);
        tfAdress.setEditable(false);
        tfTelefon.setEditable(false);

        Change.setText("Ändra");
        Change.removeActionListener(Change.getActionListeners()[0]);
        Change.addActionListener(evt -> onChangeClicked());

        JOptionPane.showMessageDialog(this, "Ändringar Sparade!");
    }

    /**
     * Anropar kontroll av att nytt förnamn innehåller accepterade tecken Ger
     * false om valideringen visar att fel uppstått
     *
     * @param idb databasen som kallas för validering
     */
    public boolean fornamnKontroll() {
        Validering valid = new Validering(idb);
        String fornamn = txtFirstName.getText();
        if (valid.checkFornamn(fornamn) && valid.checkStorlek(100, fornamn)) {
            lblFornamnBad1.setVisible(false);
            return true;
        } else {
            lblFornamnBad1.setVisible(true);
            return false;
        }
    }

    /**
     * Anropar kontroll av att nytt efternamn innehåller accepterade tecken Ger
     * false om valideringen visar att fel uppstått
     *
     * @param idb databasen som används för validering
     */
    public boolean efternamnKontroll() {
        Validering valid = new Validering(idb);
        String efternamn = txtLastName.getText();
        if (valid.checkEfternamn(efternamn) && valid.checkStorlek(100, efternamn)) {
            lblEfternamnBad.setVisible(false);
            return true;
        } else {
            lblEfternamnBad.setVisible(true);
            return false;
        }
    }

    /**
     * Anropar kontroll av att ny epost är korrekt formaterad, samt innehåller
     * accepterade tecken Ger false om valideringen visar att fel uppstått
     *
     * @param idb databasen som används för validering
     */
    public boolean kontroll() {
        Validering valid = new Validering(idb);

        // Hämta text från textfältet
        String epost = txtEmail.getText();

        // Kontrollera om e-postadressen är giltig
        if (valid.checkEpost(epost) && valid.checkStorlek(255, epost)) {
            lblEpostBad.setVisible(false);
            return true;
        } else {
            lblEpostBad.setVisible(true);
            return false;
        }
    }

    /**
     * Anropar kontroll av att ny lösenord är korrekt formaterad, samt
     * innehåller accepterade tecken Ger false om valideringen visar att fel
     * uppstått
     *
     * @param idb databasen som används för validering
     */
    public boolean losenKontroll() {
        Validering valid = new Validering(idb);

        // Hämta text från textfältet
        String losen = txtPassword.getText();

        // Kontrollera om e-postadressen är giltig
        if (valid.checkLosenord(losen) && valid.checkStorlek(255, losen)) {
            lblLosenBad.setVisible(false);
            return true;
        } else {
            lblLosenBad.setVisible(true);
            return false;
        }
    }

    /**
     * Anropar kontroll av att ny adress är korrekt formaterad samt innehåller
     * accepterade tecken Ger false om valideringen visar att fel uppstått
     *
     * @param idb databasen som används för validering
     */
    public boolean adressKontroll() {
        Validering valid = new Validering(idb);
        String adress = tfAdress.getText();
        if (valid.checkAdress(adress) && valid.checkStorlek(255, adress)) {
            lblAdressBad.setVisible(false);
            return true;
        } else {
            lblAdressBad.setVisible(true);
            return false;
        }
    }

    /**
     * Anropar kontroll av att nytt telefonnummer är korrekt formaterad, samt
     * innehåller accepterade tecken Ger false om valideringen visar att fel
     * uppstått
     *
     * @param idb databasen som används för validering
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

    public boolean totalKontroll() {
        Boolean totOk = true;

        if (kontroll() == false) {
            totOk = false;
        } else if (!fornamnKontroll()) {
            totOk = false;
        } else if (!efternamnKontroll()) {
            totOk = false;
        } else if (!telefonKontroll()) {
            totOk = false;
        } else if (!adressKontroll()) {
            totOk = false;
        } else if (!losenKontroll()) {
            totOk = false;
        }
        return totOk;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblFornamnBad = new javax.swing.JLabel();
        MinProfil = new javax.swing.JLabel();
        Namn = new javax.swing.JLabel();
        Epost = new javax.swing.JLabel();
        Avdelning = new javax.swing.JLabel();
        Change = new javax.swing.JButton();
        txtFirstName = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtDepartment = new javax.swing.JTextField();
        txtLastName = new javax.swing.JTextField();
        Losenord = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        Tillbaka = new javax.swing.JButton();
        lblFelInmatning = new javax.swing.JLabel();
        lblFornamnBad1 = new javax.swing.JLabel();
        lblEfternamnBad = new javax.swing.JLabel();
        lblEpostBad = new javax.swing.JLabel();
        tfTelefon = new javax.swing.JTextField();
        lblTelefon = new javax.swing.JLabel();
        lblTelefonBad = new javax.swing.JLabel();
        tfAdress = new javax.swing.JTextField();
        lblAdress = new javax.swing.JLabel();
        lblAdressBad = new javax.swing.JLabel();
        lblLosenBad = new javax.swing.JLabel();

        lblFornamnBad.setForeground(new java.awt.Color(255, 0, 51));
        lblFornamnBad.setText("!");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        MinProfil.setText("Min Profil");

        Namn.setText("Namn:");

        Epost.setText("Epost:");

        Avdelning.setText("Avdelning:");

        Change.setText("Ändra");
        Change.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChangeActionPerformed(evt);
            }
        });

        txtFirstName.setEnabled(false);
        txtFirstName.setFocusable(false);
        txtFirstName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFirstNameActionPerformed(evt);
            }
        });

        txtEmail.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtEmail.setEnabled(false);
        txtEmail.setFocusable(false);
        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        txtDepartment.setEditable(false);
        txtDepartment.setEnabled(false);
        txtDepartment.setFocusable(false);
        txtDepartment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDepartmentActionPerformed(evt);
            }
        });

        txtLastName.setEnabled(false);
        txtLastName.setFocusable(false);
        txtLastName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLastNameActionPerformed(evt);
            }
        });

        Losenord.setText("Lösenord:");

        txtPassword.setEditable(false);
        txtPassword.setEnabled(false);
        txtPassword.setFocusable(false);
        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });

        Tillbaka.setText("Tillbaka");
        Tillbaka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TillbakaActionPerformed(evt);
            }
        });

        lblFelInmatning.setForeground(new java.awt.Color(255, 0, 51));
        lblFelInmatning.setText("Fel i inmatning");

        lblFornamnBad1.setForeground(new java.awt.Color(255, 0, 51));
        lblFornamnBad1.setText("!");

        lblEfternamnBad.setForeground(new java.awt.Color(255, 0, 51));
        lblEfternamnBad.setText("!");

        lblEpostBad.setForeground(new java.awt.Color(255, 0, 51));
        lblEpostBad.setText("!");

        tfTelefon.setText("123-123-1234");
        tfTelefon.setEnabled(false);
        tfTelefon.setFocusable(false);
        tfTelefon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfTelefonActionPerformed(evt);
            }
        });

        lblTelefon.setText("Telefon");

        lblTelefonBad.setForeground(new java.awt.Color(255, 0, 51));
        lblTelefonBad.setText("!");

        tfAdress.setText("123 blabla, bobla");
        tfAdress.setEnabled(false);
        tfAdress.setFocusable(false);

        lblAdress.setText("Adress");

        lblAdressBad.setForeground(new java.awt.Color(255, 0, 51));
        lblAdressBad.setText("!");

        lblLosenBad.setForeground(new java.awt.Color(255, 0, 51));
        lblLosenBad.setText("!");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(MinProfil)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Tillbaka))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(Avdelning)
                                            .addComponent(Losenord))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(txtPassword)
                                            .addComponent(tfAdress, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                                            .addComponent(tfTelefon, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtDepartment)
                                            .addComponent(txtEmail, javax.swing.GroupLayout.Alignment.LEADING))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(57, 57, 57)
                                                .addComponent(lblEpostBad))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(lblAdressBad, javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(lblLosenBad, javax.swing.GroupLayout.Alignment.TRAILING)))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(Namn, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(5, 5, 5)
                                        .addComponent(lblFornamnBad1)
                                        .addGap(10, 10, 10)
                                        .addComponent(txtLastName)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblEfternamnBad))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(lblTelefonBad)))
                                .addGap(15, 15, 15)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblFelInmatning)
                        .addGap(90, 90, 90)
                        .addComponent(Change, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblAdress, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTelefon, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(Epost, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(299, 299, 299)))
                        .addGap(0, 163, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(MinProfil)
                    .addComponent(Tillbaka))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Namn)
                    .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFornamnBad1)
                    .addComponent(lblEfternamnBad))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Epost)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEpostBad))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTelefon)
                    .addComponent(tfTelefon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTelefonBad))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAdress)
                    .addComponent(tfAdress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAdressBad))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Avdelning)
                    .addComponent(txtDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Losenord)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLosenBad))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Change)
                    .addComponent(lblFelInmatning))
                .addGap(29, 29, 29))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtFirstNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFirstNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFirstNameActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void txtDepartmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDepartmentActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDepartmentActionPerformed

    private void ChangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChangeActionPerformed
        
        if (!isEditing) {
            
            txtFirstName.setEditable(true);
            txtFirstName.setFocusable(true);
            txtFirstName.setEnabled(true);

            txtLastName.setEditable(true);
            txtLastName.setFocusable(true);
            txtLastName.setEnabled(true);

            txtEmail.setEditable(true);
            txtEmail.setFocusable(true);
            txtEmail.setEnabled(true);

            txtPassword.setEditable(true);
            txtPassword.setFocusable(true);
            txtPassword.setEnabled(true);
            
            String currentPassword =getPassword(aid);
            txtPassword.setText(currentPassword);
            txtPassword.setEchoChar('\u0000');
            
            tfAdress.setEditable(false);
            tfAdress.setFocusable(false);
            tfAdress.setEnabled(false);

            tfTelefon.setEditable(false);
            tfTelefon.setFocusable(false);
            tfTelefon.setEnabled(false);
            
            Change.setText("Spara");
            
            isEditing = true;
           }
           else {
            boolean ok = totalKontroll();
            
            if (ok) {
                lblFelInmatning.setVisible(false);
            
            firstname = txtFirstName.getText();
            lastname = txtLastName.getText();
            adress = tfAdress.getText();
            telefon = tfTelefon.getText();
            String newEmail = txtEmail.getText();
            String newPassword = new String(txtPassword.getPassword());

            if (!newEmail.equals(this.epost) && isEmailTaken(newEmail)) {
                JOptionPane.showMessageDialog(this, "Eposten används redan av en annnan användare", "Fel", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                String updateQuery = "UPDATE anstalld"
                        + "SET fornamn = '" + firstname
                        +"', efternamn = '" + lastname
                        +"', epost = '" + newEmail
                        +"', losenord = '" + newPassword
                        +"', telefon = '" + telefon
                        +"', adress = '" + adress
                        + "' WHERE anstalld.epost = '" +this.epost + "'";
                                
                idb.update(updateQuery);
                
                this.epost = newEmail;
               }catch (InfException ex) {
                JOptionPane.showMessageDialog(this, "Fel vid uppdatering av databasen:", "Fel", JOptionPane.ERROR_MESSAGE);
                return;
               } 
               
            txtFirstName.setEditable(false);
            txtFirstName.setFocusable(false);
            txtFirstName.setEnabled(false);

            txtLastName.setEditable(false);
            txtLastName.setFocusable(false);
            txtLastName.setEnabled(false);

            txtEmail.setEditable(false);
            txtEmail.setFocusable(false);
            txtEmail.setEnabled(false);

            txtPassword.setEditable(false);
            txtPassword.setFocusable(false);
            txtPassword.setEnabled(false);
            txtPassword.setEchoChar('*');

            tfAdress.setEditable(false);
            tfAdress.setFocusable(false);
            tfAdress.setEnabled(false);

            tfTelefon.setEditable(false);
            tfTelefon.setFocusable(false);
            tfTelefon.setEnabled(false);

            Change.setText("Ändra");
            isEditing=false;
                
            JOptionPane.showMessageDialog(this, "Ändringar sparade!");
          }else {
            lblFelInmatning.setVisible(true);
            }  
        }
    }//GEN-LAST:event_ChangeActionPerformed

    private void txtLastNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLastNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLastNameActionPerformed

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordActionPerformed

    private void TillbakaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TillbakaActionPerformed
        Validering valid = new Validering(idb);
        boolean admins = valid.isAdmin(epost);
        if (admins == true) {
            this.dispose();
            new AdminMeny(idb, epost, aid).setVisible(true);
        } else {
            this.dispose();
            new Meny(idb, epost, aid).setVisible(true);
        }

        this.dispose();
    }//GEN-LAST:event_TillbakaActionPerformed

    private void tfTelefonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfTelefonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfTelefonActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Avdelning;
    private javax.swing.JButton Change;
    private javax.swing.JLabel Epost;
    private javax.swing.JLabel Losenord;
    private javax.swing.JLabel MinProfil;
    private javax.swing.JLabel Namn;
    private javax.swing.JButton Tillbaka;
    private javax.swing.JLabel lblAdress;
    private javax.swing.JLabel lblAdressBad;
    private javax.swing.JLabel lblEfternamnBad;
    private javax.swing.JLabel lblEpostBad;
    private javax.swing.JLabel lblFelInmatning;
    private javax.swing.JLabel lblFornamnBad;
    private javax.swing.JLabel lblFornamnBad1;
    private javax.swing.JLabel lblLosenBad;
    private javax.swing.JLabel lblTelefon;
    private javax.swing.JLabel lblTelefonBad;
    private javax.swing.JTextField tfAdress;
    private javax.swing.JTextField tfTelefon;
    private javax.swing.JTextField txtDepartment;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtLastName;
    private javax.swing.JPasswordField txtPassword;
    // End of variables declaration//GEN-END:variables
}
