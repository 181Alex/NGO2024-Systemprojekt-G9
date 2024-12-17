
package ngo2024;

/**
 *
 * @author alexanderabboud
 */
public class Validering {
    
    
    public boolean arAdmin(String ePost){
        boolean isAdmin=false;
        String sqlFraga= "SELECT behorighetsniva FROM admin WHERE aid = (SELECT aid FROM anstalld where epost = '"+ePost+ "')";
        if(sqlFraga.equals("1")){
            isAdmin=true;
        }
        return isAdmin;
    }
    
    
    public boolean arChef(String ePost){
        boolean isChef=false;
        String sqlFraga="SELECT projektchef FROM projekt WHERE projektchef IN(SELECT aid FROM anstalld where epost = '"+ePost+ "')";
        if(sqlFraga!=null){
            isChef=true;
        }
        return isChef;
    }
    
    
    
    
    
    
}
