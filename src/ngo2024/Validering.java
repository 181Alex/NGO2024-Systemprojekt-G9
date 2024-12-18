
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
        String checker="^[\\w.-]+@[\\w.-]+\\.[a-z]{2,}$";
        if(ePost.matches(checker)){
            matches=true;
        }
        return matches;
        }
    

    public boolean checkDatum(String datum){
        boolean matches=false;
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
    
    
    
    
    
    
}
