
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
    
    
    
    
    
    
}
