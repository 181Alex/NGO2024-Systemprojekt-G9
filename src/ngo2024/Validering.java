package ngo2024;

import java.util.ArrayList;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author alexanderabboud
 */
public class Validering {

    private InfDB idb;

     /**
     * Initierar Validering objekt 
     * Validerings klass för validering av behörighet och inmatade Strings
     *
     * @param idb initierar fält för att interagera med databasen
     */
    public Validering(InfDB idb) {
        this.idb = idb;
    }

    public boolean isAdmin(String ePost) {
        boolean isAdmin = false;
        try {
            String sqlFraga = "SELECT behorighetsniva FROM admin WHERE aid = (SELECT aid FROM anstalld where epost = '" + ePost + "')";
            String behorighet = idb.fetchSingle(sqlFraga);
            if (behorighet != null) {
                isAdmin = true;
            }
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }

        return isAdmin;
    }

    /**
     * Kontrollerar om inloggad användare är chef över ett eller fler projekt
     * 
     * @param ePost inloggad användares epost som validering sker utifrån
     */
    public boolean isChef(String ePost) {
        boolean isChef = false;
        try {
            String sqlFraga = "SELECT projektchef FROM projekt WHERE projektchef IN(SELECT aid FROM anstalld where epost = '" + ePost + "')";
            String behorighet = idb.fetchSingle(sqlFraga);
            if (behorighet != null) {
                isChef = true;
            }
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
        return isChef;
    }

    /**
     * Kontrolerar om användaren är projektledare för ett specifikt projekt
     * 
     * @param aid inloggad användares ID
     * @param projektId ID på projekt som uppgift ska kontrolleras mot
     */
    public boolean isProjektetsChef(String aid, String projektId) {
        boolean isProjektetsChef = false;
        try {
            String sqlFraga = "SELECT a.aid FROM anstalld a JOIN projekt p ON a.aid = p.projektchef WHERE p.pid ='" + projektId + "'";
            String valideringsID = idb.fetchSingle(sqlFraga);

            if (valideringsID != null && valideringsID.equals(aid)) {
                isProjektetsChef = true;
            }

        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
        return isProjektetsChef;
    }
    /**
     * Kontrollerar att String är korrekt formaterad som epost
     * 
     * @param ePost epost String som ska kontrolleras
     */
    public boolean checkEpost(String ePost) {
        boolean matches = false;
        // måste skriva valfritt@xxxxx.com, alla special tecken msåte finnas där.
        String checker = "^[\\w.-]+@[\\w.-]+\\.[a-z]{2,}$";
        if (ePost.matches(checker)) {
            matches = true;
        }
        return matches;
    }
    
    
    //kontrollerar så att man inte kan ha samma epost som någon annan anställd, avdelning eller partner
     public boolean checkInteSammaEpost(String epost){
        String sqlFraga = "SELECT epost FROM anstalld "
                + "UNION SELECT epost FROM avdelning "
                + "UNION SELECT kontaktepost FROM partner";
        boolean ok = true;
        
        try {
            ArrayList<String> epostLista = idb.fetchColumn(sqlFraga);
            
            for(String aEpost: epostLista){
                if(epost.equalsIgnoreCase(aEpost)){
                    ok = false;
               }                   
            }
            
        } catch (InfException ex){
            System.out.println(ex.getMessage());
        }
        return ok;
        
    }
       
      //kontrollerar så att man inte kan ha samma telefon som någon annan anställd, avdelning eller partner
public boolean checkInteSammaTelefon(String telefon){
           String sqlFraga = "SELECT telefon FROM anstalld                                                                "
                   + "UNION SELECT telefon FROM avdelning "
                   + "UNION SELECT telefon FROM partner ";
        boolean ok = true;
        
        try {
            ArrayList<String> telefonLista = idb.fetchColumn(sqlFraga);
            
            for(String tel: telefonLista){
                if(telefon.equalsIgnoreCase(tel)){
                    ok = false;
               }                   
            }
            
        } catch (InfException ex){
            System.out.println(ex.getMessage());
        }
        return ok;
        
    }


    /**
     * Kontrollerar att datum String är korrekt formaterad
     * 
     * @param datum datum String som ska kontrolleras
     */
    public boolean checkDatum(String datum) {
        boolean matches = false;
        // måste skrivas yyyy-mm-dd
        String checker = "^(19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";
        if (datum.matches(checker)) {
            matches = true;
        }
        return matches;
    }
    
    /**
 * Kontrollerar att ett lösenord är korrekt formaterat.
 * 
 * @param losenord Lösenordet som ska kontrolleras.
 * @return true om lösenordet uppfyller kriterierna, annars false.
 */
    public boolean checkLosenord(String losen){
        boolean matches = false;
        // Minst 8 tecken, måste innehålla minst en bokstav. Siffror och specialtecken är valfria.
        String checker = "^(\\d{8,}|(?=.*[A-Za-z]).{8,})$";
        if (losen.matches(checker)) {
            matches = true;
        }
        return matches;
    }

    /**
     * Kontrollerar att adress String är korrekt formaterad
     * 
     * @param adress adress String som ska kontrolleras
     */
    public boolean checkAdress(String adress) {
        boolean matches = false;
        // Adress: "123 Gatunamn, Stad" (tre siffror, gatunamn, kommatecken, stad).
        String checker = "^\\d{1,4}\\s[A-Za-zåäöÅÄÖ]+,\\s[A-Za-zåäöÅÄÖ]+$";
        if (adress.matches(checker)) {
            matches = true;
        }
        return matches;
    }
    /**
     * kontrollerar så att adress antingen skrivs "123 Gatunamn, Stad" eller "gatu-namn 123"
     * @param adress
     * 
     */
    public boolean checkAvdAdress(String adress) {
        boolean matches = false;
        // Adress: "123 Gatunamn, Stad" (tre siffror, gatunamn, kommatecken, stad).
        String checker = "^\\d{1,4}\\s[A-Za-zåäöÅÄÖ]+,\\s[A-Za-zåäöÅÄÖ]+$";
        String checkers="^[a-zA-ZåäöÅÄÖ-]+ [1-9][0-9]{0,2}$";
        if (adress.matches(checker) || adress.matches(checkers)) {
            matches = true;
        }
        return matches;
    }
    
    

    /**
     * Kontrollerar att efternamn String är korrekt formaterad
     * 
     * @param efternamn String som ska kontrolleras
     */
    public boolean checkEfternamn(String efternamn) {
        boolean matches = false;
        //alla bokstäver ok inga siffror
        String checker = "^[A-Za-zåäöÅÄÖ]+$";
        if (efternamn.matches(checker)) {
            matches = true;
        }
        return matches;
    }

    /**
     * Kontrollerar att namn på stad String är korrekt formaterad
     * 
     * @param namn String av namn på stad som ska kontrolleras
     */
    public boolean checkStad(String namn) {
        boolean matches = false;
        //kontrollerar alla bokstäver i vanliga svenska alfabetet plus andra tecken som kan 
        //finnas i städers namn exempelvis: Çapakçur
        String checker = "^[a-zA-ZåäöÅÄÖéèëêíìïîóòöôúùüûç-]+(?:[ -][a-zA-ZåäöÅÄÖéèëêíìïîóòöôúùüûç-]+)*$";
        if (namn.matches(checker)) {
            matches = true;
        }
        return matches;
    }

    /**
     * Kontrollerar att namn String är korrekt formaterad
     * 
     * @param namn String som ska kontrolleras
     */
    public boolean checkNamn(String namn) {
        boolean matches = false;
        if (namn == null || namn.trim().isEmpty()) {
            return matches; // Hantera null eller tomma strängar
        }
        namn = namn.trim(); // Ta bort inledande och avslutande mellanslag (endast mellan ok)
        // kräver först bokstver/- och mellanrum och sedan efternamn

        String regex = "^[A-Za-zåäöÅÄÖ]+([ .-][A-Za-zåäöÅÄÖ]+)*$";
        if (namn.matches(regex)) {
            matches = true;
        }
        return matches;
    }

    /**
     * Kontrollerar att String enbart innehåller bokstäver
     * 
     * @param ansvar String som kontrolleras
     */
    public boolean checkAnsvar(String ansvar) {
        boolean matches = false;
        //alla bokstäver ok inga siffror
        String checker = "^[A-Za-zåäöÅÄÖ]+$";
        if (ansvar.matches(checker)) {
            matches = true;
        }
        return matches;
    }

    /**
     * Kontrollerar om mentor finns samt om parameter String enbart innehåller siffror
     * 
     * @param mentor String som kontrolleras
     */
    public boolean checkMentor(String mentor) {
        // kollar om en mentor finns
        if (mentor == null) {
            return true;
        } else {
            boolean matches = false;
            //endast siffror
            String checker = "^\\d+$";
            if (mentor.matches(checker)) {
                matches = true;
            }
            return matches;
        }
    }

    /**
     * Kontrollerar att String för telefonnummer är korrekt formaterad
     * 
     * @param telefon String som kontrolleras
     */
    public boolean checkTelefon(String telefon) {
        boolean matches = false;
        //7-15 siffror med binder streck efter 3 siffror, sen igen efter 3 siffror sen fyra i slutet
        String checker = "^\\d{3}-\\d{3}-\\d{4}$";
        if (telefon.matches(checker)) {
            matches = true;
        }
        return matches;
    }
    /**
     * Kontrollerar så att det inmatningen antingen är 123-123-1234 eller max 20 valfria siffror
     * @param telefon
     */
    
    public boolean checkAvdTelefon(String telefon){
        boolean matches=false;
        String checker="^\\d{0,20}$";
        String checkers="^\\d{3}-\\d{3}-\\d{4}$";
        if(telefon.matches(checker) || telefon.matches(checkers)){
            matches=true;
        }
        return matches;
    }

    /**
     * Kontrollerar att String med förnamn är korrekt formaterad (bara bokstäver)
     * 
     * @param fornamn String med förnamn som kontrolleras
     */
    public boolean checkFornamn(String fornamn) {
        boolean matches = false;
        // alla bokstäver ok inga siffror
        String checker = "^[A-ZÅÄÖ][a-zåäö]+(?:[- ][A-ZÅÄÖ][a-zåäö]+)?$";
        if (fornamn.matches(checker)) {
            matches = true;
        }
        return matches;
    }

    /**
     * Kontrollerar om en anställd tillhör en viss avdelning
     * 
     * @param avdNummer ID (avdid) nummer för avdelningen som ska kontrolleras
     * @param aid ID för användare för kontroll om användare tillhör avdelning
     */
    public boolean tillhorAvdelning(int avdNummer, int aid) {
        int hämtadI = 0;
        String sqlFraga = "SELECT aid FROM anstalld where avdelning =" + avdNummer + " AND aid= " + aid;
        try {
            String hämtadS = idb.fetchSingle(sqlFraga);
            if (hämtadS == null) {
                return false;
            } else {
                hämtadI = Integer.parseInt(hämtadS);
            }
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
        if (hämtadI == aid) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Kontrollerar att valuta String är korrekt formaterad
     * 
     * @param valuta String som ska kontrolleras
     */
    public boolean checkValuta(String valuta) {
        boolean matches = false;
        // Regex för siffror, en punkt och upp till fyra decimaler
        String checker = "^\\d+(\\.\\d{1,4})?$";
        if (valuta.matches(checker)) {
            matches = true;
        }
        return matches;
    }

    /**
     * Kontrollerar att kostnad String är korrekt formaterad
     * 
     * @param kostnad String som ska kontrolleras
     */
    public boolean checkKostnad(String kostnad) {
        boolean matches = false;
        // Regex för upp till 12 siffror före punkt och 1-4 decimaler
        String checker = "^\\d{1,12}\\.\\d{1,4}$";
        if (kostnad.matches(checker)) {
            matches = true;
        }
        return matches;
    }

    /**
     * Kontrollerar att String är korrekt formaterad. Kan ta in flera ord, siffra, etc.
     * 
     * @param mening String som ska kontrolleras
     */
    public boolean checkMeningOSiffra(String mening) {
        boolean matches = false;
        // en mening/ ord sedan en siffra i slutet, vissa tecken, som t.ex komma och bindesstreck är också ok
        String checker = "^[A-Za-zÅÄÖåäö]+([\\s,\\-][A-Za-zÅÄÖåäö]+)*(\\s(1000|[1-9]\\d{0,2}))?$";
        if (mening.matches(checker)) {
            matches = true;
        }
        return matches;
    }

    /**
     * Kontrollerar att beskrivning String är korrekt formaterad
     * 
     * @param beskrivning String som ska kontrolleras
     */
    public boolean checkBeskrivning(String beskrivning) {
        boolean matches = false;
        // Regex för att tillåta text med tillåtna tecken
        String checker = "^[A-Za-zÅÄÖåäö0-9.,\\-!?\\s]+$";
        if (beskrivning.matches(checker)) {
            matches = true;
        }
        return matches;
    }

    /**
     * Kontrollerar att start datum är före slutdatum
     * 
     * @param franDatum Startdatum String som ska kontrolleras
     * @param tillDatum Slutdatum String som ska kontrolleras
     */
    public boolean checkDatumSkillnad(String franDatum, String tillDatum) {
        boolean mindre = false;
        // start måste vara mindre än slut
        int startInt = Integer.parseInt(franDatum.replace("-", ""));
        int slutInt = Integer.parseInt(tillDatum.replace("-", ""));
        if (startInt < slutInt) {
            mindre = true;
        }
        return mindre;
    }

    /**
     * Kontrollerar att start datum är före slutdatum
     * 
     * @param franDatum Startdatum String  för projekt
     * @param tillDatum Slutdatum String för projekt
     * @param franSok Söker från och med datum String
     * @param tillSok Söker till och med datum String
     */
    public boolean checkMellanDatumSkillnad(String franDatum, String tillDatum, String franSok, String tillSok) {
        if (franSok == null) {
            franSok = "1000-01-01";
        }
        if (tillSok == null) {
            tillSok = "9999-01-01";
        }

        int startInt = Integer.parseInt(franDatum.replace("-", ""));
        int slutInt = Integer.parseInt(tillDatum.replace("-", ""));
        int fSokInt = Integer.parseInt(franSok.replace("-", ""));
        int tSokInt = Integer.parseInt(tillSok.replace("-", ""));
        boolean inomIntervall = false;

        if (fSokInt <= startInt && tSokInt >= slutInt) {
            inomIntervall = true;
        }
        return inomIntervall;
    }

    /**
     * Kontrollerar om projekt är avslutat
     * 
     * @param status String som vill kontrolleras
     */
    public boolean checkAvslutad(String status) {
        boolean avslutad = false;
        if (status.equals("Avslutat")) {
            avslutad = true;
        }
        return avslutad;
    }

    /**
     * Kontrollerar om projekt är planerat
     * 
     * @param status String som vill kontrolleras
     */
    public boolean checkPlanerade(String status) {
        boolean planerade = false;
        if (status.equals("Planerat")) {
            planerade = true;
        }
        return planerade;
    }

    /**
     * Kontrollerar om projekt är pågående
     * 
     * @param status String som vill kontrolleras
     */
    public boolean checkPagaende(String status) {
        boolean pagaende = false;
        if (status.equals("Pågående")) {
            pagaende = true;
        }
        return pagaende;
    }

    /**
     * Kontrollerar om projekt är pausat
     * 
     * @param status String som vill kontrolleras
     */
    public boolean checkPausad(String status) {
        boolean pausad = false;
        if (status.equals("Pausad")) {
            pausad = true;
        }
        return pausad;
    }

    /**
     * Kontrollerar om projekt är aktivt
     * 
     * @param status String som vill kontrolleras
     */
    public boolean checkAktiv(String status) {
        boolean aktiv = true;
        if (status.equals("Avslutat") || status.equals("Pausad")) {
            aktiv = false;
        }
        return aktiv;
    }

    /**
     * Kontrollerar att String inte överskridet tecken antal
     * 
     * @param max int med max antal tecken
     * @param inmatning String som kontrolleras
     */
    public boolean checkStorlek(int max, String inmatning) {
        boolean mindre = false;
        int in = inmatning.length();
        if (in <= max) {
            mindre = true;
        }
        return mindre;
    }

}
