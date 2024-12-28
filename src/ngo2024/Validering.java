
package ngo2024;
import oru.inf.InfDB;
import oru.inf.InfException;
/**
 *
 * @author alexanderabboud
 */
public class Validering {
        private InfDB idb;
        
        public Validering(InfDB idb) {
        this.idb=idb;
    }
    
    
    public boolean arAdmin(String ePost){
        boolean isAdmin=false;
        try{
            String sqlFraga= "SELECT behorighetsniva FROM admin WHERE aid = (SELECT aid FROM anstalld where epost = '"+ ePost + "')";
        String behorighet = idb.fetchSingle(sqlFraga);
        if(behorighet!=null){
            isAdmin=true;
        }
        }
        catch(InfException ex){
            System.out.println(ex.getMessage());
        }
    
        
        return isAdmin;
    }
    
    
    public boolean arChef(String ePost){
        boolean isChef=false;
        try{
        String sqlFraga="SELECT projektchef FROM projekt WHERE projektchef IN(SELECT aid FROM anstalld where epost = '"+ePost+ "')";
        String behorighet = idb.fetchSingle(sqlFraga);
        if(behorighet!=null){
            isChef=true;
        }
        }
        catch(InfException ex){
            System.out.println(ex.getMessage());
        }
        return isChef;
    }
   
    
    public boolean checkEpost(String ePost){
        boolean matches=false;
        // måste skriva valfritt@xxxxx.com, alla special tecken msåte finnas där.
        String checker="^[\\w.-]+@[\\w.-]+\\.[a-z]{2,}$";
        if(ePost.matches(checker)){
            matches=true;
        }
        return matches;
        }
    

    public boolean checkDatum(String datum){
        boolean matches=false;
        // måste skrivas yyyy-mm-dd
        String checker="^(19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";
        if(datum.matches(checker)){
            matches=true;
        }
        return matches;
        }
    
    public boolean checkAdress(String adress) {
        boolean matches = false;
        // Adress: "123 Gatunamn, Stad" (tre siffror, gatunamn, kommatecken, stad).
        String checker = "^\\d{3}\\s[A-Za-zåäöÅÄÖ]+,\\s[A-Za-zåäöÅÄÖ]+$";
        if (adress.matches(checker)) {
        matches = true;
        }
        return matches;
}

    public boolean checkEfternamn(String efternamn) {
        boolean matches = false;
        //alla bokstäver ok inga siffror
        String checker = "^[A-Za-zåäöÅÄÖ]+$";
        if (efternamn.matches(checker)) {
        matches = true;
        }
     return matches;
}
    
    public boolean checkStad(String namn){
        boolean matches=false;
                //kontrollerar alla bokstäver i vanliga svenska alfabetet plus andra tecken som kan 
                //finnas i städers namn exempelvis: Çapakçur
            String checker="^[a-zA-ZåäöÅÄÖéèëêíìïîóòöôúùüûç-]+(?:[ -][a-zA-ZåäöÅÄÖéèëêíìïîóòöôúùüûç-]+)*$";
        if(namn.matches(checker)){
            matches=true;}
            return matches;
        }
    
    
    public boolean checkNamn(String namn) {
        boolean matches=false;
        if (namn == null || namn.trim().isEmpty()) {
            return matches; // Hantera null eller tomma strängar
        }
        namn = namn.trim(); // Ta bort inledande och avslutande mellanslag (endast mellan ok)
        // kräver först bokstver/- och mellanrum och sedan efternamn

        String regex = "^[A-Za-zåäöÅÄÖ-]+ [A-Za-zåäöÅÄÖ-]+$";
         if(namn.matches(regex)){
             matches=true;
         }
        return matches;
    }
    
    
    
    public boolean checkAnsvar(String Ansvar) {
        boolean matches = false;
        //alla bokstäver ok inga siffror
        String checker = "^[A-Za-zåäöÅÄÖ]+$";
        if (Ansvar.matches(checker)) {
        matches = true;
        }
     return matches;
}
    public boolean checkMentor(String mentor) {
        // kollar om en mentor finns
        if (mentor==null){
            return true;
        }
        else{
        boolean matches = false;
        //endast siffror
        String checker = "^\\d+$";
        if (mentor.matches(checker)) {
        matches = true;
        }
     return matches;}
    
    }

   public boolean checkTelefon(String telefon) {
        boolean matches = false;
        //7-15 siffror med binder streck efter 3 siffror, sen igen efter 3 siffror sen fyra i slutet
        String checker = "^\\d{3}-\\d{3}-\\d{4}$";
        if (telefon.matches(checker)) {
        matches = true;
        }
        return matches;
}
 
    public boolean checkFornamn(String fornamn) {
    boolean matches = false;
    // alla bokstäver ok inga siffror
    String checker = "^[A-ZÅÄÖ][a-zåäö]+(?:[- ][A-ZÅÄÖ][a-zåäö]+)?$";
    if (fornamn.matches(checker)) {
        matches = true;
    }
    return matches;
}
    
    public boolean tillhorAvdelning(int avdNummer, int aid){
        int hämtadI=0;
        String sqlFraga="SELECT aid FROM anstalld where avdelning =" + avdNummer + " AND aid= " + aid;
         try{
             String hämtadS=idb.fetchSingle(sqlFraga);
             if(hämtadS==null){
                 return false;
             }
             else{
             hämtadI=Integer.parseInt(hämtadS);
             }
         }
         catch(InfException ex){
             System.out.println(ex.getMessage());
         }
         if(hämtadI==aid){
             return true;
         }
         else{
             return false;
         }
         
    
    }
    
    
public boolean checkValuta(String valuta) {
    boolean matches = false;
    // Regex för siffror, ett komma och upp till fyra decimaler
    String checker = "^\\d+.(\\d{1,4})?$";
    if (valuta.matches(checker)) {
        matches = true;
    }
    return matches;
}


public boolean checkKostnad(String kostnad) {
    boolean matches = false;
    // Regex för upp till 12 siffror före kommatecknet och upp till 2 decimaler
    String checker = "^\\d{1,12}.(\\d{1,2})?$";
    if (kostnad.matches(checker)) {
        matches = true;
    }
    return matches;
}


public boolean checkMeningOSiffra(String mening){
    boolean matches = false;
    // en mening/ ord sedan en siffra i slutet
    String checker = "^[A-Za-zÅÄÖåäö]+\\s(1000|[1-9]\\d{0,2})$";
    if (mening.matches(checker)) {
        matches = true;
    }
    return matches;
}

public boolean checkDatumSkillnad(String franDatum, String tillDatum){
    boolean mindre=false;
    int startInt = Integer.parseInt(franDatum.replace("-", ""));
        int slutInt = Integer.parseInt(tillDatum.replace("-", ""));
        if(startInt<slutInt){
            mindre=true;
        }
        return mindre;
}

public boolean checkMellanDatumSkillnad(String franDatum, String tillDatum, String franSok, String tillSok){
    if (franSok == null) franSok = "1000-01-01";
    if (tillSok == null) tillSok = "9999-01-01";
    
    int startInt = Integer.parseInt(franDatum.replace("-", ""));
    int slutInt = Integer.parseInt(tillDatum.replace("-", ""));
    int fSokInt = Integer.parseInt(franSok.replace("-", ""));
    int tSokInt = Integer.parseInt(tillSok.replace("-", ""));
    boolean inomIntervall=false;
        
        if(fSokInt<=startInt && tSokInt>=slutInt){
            inomIntervall=true;
        }
        return inomIntervall;   
}
public boolean checkAvslutad(String status){
    boolean avslutad = false;
    if(status.equals("Avslutat")){
        avslutad = true;
    }
    return avslutad;
}
public boolean checkPlanerade(String status){
    boolean planerade = false;
    if(status.equals("Planerat")){
        planerade = true;
    }
    return planerade;
}

public boolean checkPagaende(String status){
    boolean pagaende = false;
    if(status.equals("Pågående")){
        pagaende = true;
    }
        return pagaende;
}

public boolean checkPausad(String status){
    boolean pausad = false;
    if(status.equals("Pausad")){
        pausad = true;
    }
    return pausad;
}

public boolean checkAktiv(String status){
    boolean aktiv = true;
    if(status.equals("Avslutat") || status.equals("Pausad")){
        aktiv = false;
    }
        return aktiv;
}

public boolean checkStorlek(int max, String inmatning){
    boolean mindre=false;
      int in=inmatning.length();
      if(in<=max){
          mindre=true;
      }
      return mindre;
}



}
