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
     * Validerings klass f�r validering av beh�righet och inmatade Strings
     *
     * @param idb initierar f�lt f�r att interagera med databasen
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
     * Kontrollerar om inloggad anv�ndare �r chef �ver ett eller fler projekt
     * 
     * @param ePost inloggad anv�ndares epost som validering sker utifr�n
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
     * Kontrolerar om anv�ndaren �r projektledare f�r ett specifikt projekt
     * 
     * @param aid inloggad anv�ndares ID
     * @param projektId ID p� projekt som uppgift ska kontrolleras mot
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
     * Kontrollerar att String �r korrekt formaterad som epost
     * 
     * @param ePost epost String som ska kontrolleras
     */
    public boolean checkEpost(String ePost) {
        boolean matches = false;
        // m�ste skriva valfritt@xxxxx.com, alla special tecken ms�te finnas d�r.
        String checker = "^[\\w.-]+@[\\w.-]+\\.[a-z]{2,}$";
        if (ePost.matches(checker)) {
            matches = true;
        }
        return matches;
    }
    
    
    //kontrollerar s� att man inte kan ha samma epost som n�gon annan anst�lld, avdelning eller partner
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
       
      //kontrollerar s� att man inte kan ha samma telefon som n�gon annan anst�lld, avdelning eller partner
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
     * Kontrollerar att datum String �r korrekt formaterad
     * 
     * @param datum datum String som ska kontrolleras
     */
    public boolean checkDatum(String datum) {
        boolean matches = false;
        // m�ste skrivas yyyy-mm-dd
        String checker = "^(19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";
        if (datum.matches(checker)) {
            matches = true;
        }
        return matches;
    }
    
    /**
 * Kontrollerar att ett l�senord �r korrekt formaterat.
 * 
 * @param losenord L�senordet som ska kontrolleras.
 * @return true om l�senordet uppfyller kriterierna, annars false.
 */
    public boolean checkLosenord(String losen){
        boolean matches = false;
        // Minst 8 tecken, m�ste inneh�lla minst en bokstav. Siffror och specialtecken �r valfria.
        String checker = "^(\\d{8,}|(?=.*[A-Za-z]).{8,})$";
        if (losen.matches(checker)) {
            matches = true;
        }
        return matches;
    }

    /**
     * Kontrollerar att adress String �r korrekt formaterad
     * 
     * @param adress adress String som ska kontrolleras
     */
    public boolean checkAdress(String adress) {
        boolean matches = false;
        // Adress: "123 Gatunamn, Stad" (tre siffror, gatunamn, kommatecken, stad).
        String checker = "^\\d{1,4}\\s[A-Za-z������]+,\\s[A-Za-z������]+$";
        if (adress.matches(checker)) {
            matches = true;
        }
        return matches;
    }
    /**
     * kontrollerar s� att adress antingen skrivs "123 Gatunamn, Stad" eller "gatu-namn 123"
     * @param adress
     * 
     */
    public boolean checkAvdAdress(String adress) {
        boolean matches = false;
        // Adress: "123 Gatunamn, Stad" (tre siffror, gatunamn, kommatecken, stad).
        String checker = "^\\d{1,4}\\s[A-Za-z������]+,\\s[A-Za-z������]+$";
        String checkers="^[a-zA-Z������-]+ [1-9][0-9]{0,2}$";
        if (adress.matches(checker) || adress.matches(checkers)) {
            matches = true;
        }
        return matches;
    }
    
    

    /**
     * Kontrollerar att efternamn String �r korrekt formaterad
     * 
     * @param efternamn String som ska kontrolleras
     */
    public boolean checkEfternamn(String efternamn) {
        boolean matches = false;
        //alla bokst�ver ok inga siffror
        String checker = "^[A-Za-z������]+$";
        if (efternamn.matches(checker)) {
            matches = true;
        }
        return matches;
    }

    /**
     * Kontrollerar att namn p� stad String �r korrekt formaterad
     * 
     * @param namn String av namn p� stad som ska kontrolleras
     */
    public boolean checkStad(String namn) {
        boolean matches = false;
        //kontrollerar alla bokst�ver i vanliga svenska alfabetet plus andra tecken som kan 
        //finnas i st�ders namn exempelvis: �apak�ur
        String checker = "^[a-zA-Z�����������������������-]+(?:[ -][a-zA-Z�����������������������-]+)*$";
        if (namn.matches(checker)) {
            matches = true;
        }
        return matches;
    }

    /**
     * Kontrollerar att namn String �r korrekt formaterad
     * 
     * @param namn String som ska kontrolleras
     */
    public boolean checkNamn(String namn) {
        boolean matches = false;
        if (namn == null || namn.trim().isEmpty()) {
            return matches; // Hantera null eller tomma str�ngar
        }
        namn = namn.trim(); // Ta bort inledande och avslutande mellanslag (endast mellan ok)
        // kr�ver f�rst bokstver/- och mellanrum och sedan efternamn

        String regex = "^[A-Za-z������]+([ .-][A-Za-z������]+)*$";
        if (namn.matches(regex)) {
            matches = true;
        }
        return matches;
    }

    /**
     * Kontrollerar att String enbart inneh�ller bokst�ver
     * 
     * @param ansvar String som kontrolleras
     */
    public boolean checkAnsvar(String ansvar) {
        boolean matches = false;
        //alla bokst�ver ok inga siffror
        String checker = "^[A-Za-z������]+$";
        if (ansvar.matches(checker)) {
            matches = true;
        }
        return matches;
    }

    /**
     * Kontrollerar om mentor finns samt om parameter String enbart inneh�ller siffror
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
     * Kontrollerar att String f�r telefonnummer �r korrekt formaterad
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
     * Kontrollerar s� att det inmatningen antingen �r 123-123-1234 eller max 20 valfria siffror
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
     * Kontrollerar att String med f�rnamn �r korrekt formaterad (bara bokst�ver)
     * 
     * @param fornamn String med f�rnamn som kontrolleras
     */
    public boolean checkFornamn(String fornamn) {
        boolean matches = false;
        // alla bokst�ver ok inga siffror
        String checker = "^[A-Z���][a-z���]+(?:[- ][A-Z���][a-z���]+)?$";
        if (fornamn.matches(checker)) {
            matches = true;
        }
        return matches;
    }

    /**
     * Kontrollerar om en anst�lld tillh�r en viss avdelning
     * 
     * @param avdNummer ID (avdid) nummer f�r avdelningen som ska kontrolleras
     * @param aid ID f�r anv�ndare f�r kontroll om anv�ndare tillh�r avdelning
     */
    public boolean tillhorAvdelning(int avdNummer, int aid) {
        int h�mtadI = 0;
        String sqlFraga = "SELECT aid FROM anstalld where avdelning =" + avdNummer + " AND aid= " + aid;
        try {
            String h�mtadS = idb.fetchSingle(sqlFraga);
            if (h�mtadS == null) {
                return false;
            } else {
                h�mtadI = Integer.parseInt(h�mtadS);
            }
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
        if (h�mtadI == aid) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Kontrollerar att valuta String �r korrekt formaterad
     * 
     * @param valuta String som ska kontrolleras
     */
    public boolean checkValuta(String valuta) {
        boolean matches = false;
        // Regex f�r siffror, en punkt och upp till fyra decimaler
        String checker = "^\\d+(\\.\\d{1,4})?$";
        if (valuta.matches(checker)) {
            matches = true;
        }
        return matches;
    }

    /**
     * Kontrollerar att kostnad String �r korrekt formaterad
     * 
     * @param kostnad String som ska kontrolleras
     */
    public boolean checkKostnad(String kostnad) {
        boolean matches = false;
        // Regex f�r upp till 12 siffror f�re punkt och 1-4 decimaler
        String checker = "^\\d{1,12}\\.\\d{1,4}$";
        if (kostnad.matches(checker)) {
            matches = true;
        }
        return matches;
    }

    /**
     * Kontrollerar att String �r korrekt formaterad. Kan ta in flera ord, siffra, etc.
     * 
     * @param mening String som ska kontrolleras
     */
    public boolean checkMeningOSiffra(String mening) {
        boolean matches = false;
        // en mening/ ord sedan en siffra i slutet, vissa tecken, som t.ex komma och bindesstreck �r ocks� ok
        String checker = "^[A-Za-z������]+([\\s,\\-][A-Za-z������]+)*(\\s(1000|[1-9]\\d{0,2}))?$";
        if (mening.matches(checker)) {
            matches = true;
        }
        return matches;
    }

    /**
     * Kontrollerar att beskrivning String �r korrekt formaterad
     * 
     * @param beskrivning String som ska kontrolleras
     */
    public boolean checkBeskrivning(String beskrivning) {
        boolean matches = false;
        // Regex f�r att till�ta text med till�tna tecken
        String checker = "^[A-Za-z������0-9.,\\-!?\\s]+$";
        if (beskrivning.matches(checker)) {
            matches = true;
        }
        return matches;
    }

    /**
     * Kontrollerar att start datum �r f�re slutdatum
     * 
     * @param franDatum Startdatum String som ska kontrolleras
     * @param tillDatum Slutdatum String som ska kontrolleras
     */
    public boolean checkDatumSkillnad(String franDatum, String tillDatum) {
        boolean mindre = false;
        // start m�ste vara mindre �n slut
        int startInt = Integer.parseInt(franDatum.replace("-", ""));
        int slutInt = Integer.parseInt(tillDatum.replace("-", ""));
        if (startInt < slutInt) {
            mindre = true;
        }
        return mindre;
    }

    /**
     * Kontrollerar att start datum �r f�re slutdatum
     * 
     * @param franDatum Startdatum String  f�r projekt
     * @param tillDatum Slutdatum String f�r projekt
     * @param franSok S�ker fr�n och med datum String
     * @param tillSok S�ker till och med datum String
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
     * Kontrollerar om projekt �r avslutat
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
     * Kontrollerar om projekt �r planerat
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
     * Kontrollerar om projekt �r p�g�ende
     * 
     * @param status String som vill kontrolleras
     */
    public boolean checkPagaende(String status) {
        boolean pagaende = false;
        if (status.equals("P�g�ende")) {
            pagaende = true;
        }
        return pagaende;
    }

    /**
     * Kontrollerar om projekt �r pausat
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
     * Kontrollerar om projekt �r aktivt
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
     * Kontrollerar att String inte �verskridet tecken antal
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
