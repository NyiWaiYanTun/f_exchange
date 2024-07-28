
package f_exchange;
import java.sql.*;
public class DBConnection {
    static Connection con = null;
    static final String DB_URL = "jdbc:mysql://localhost:3306/f_exchange";
    static final String USERNAME = "root";
    static final String PASSWORD = "";
    
    public static Connection connectDB(){
        
        try{
            con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            return con;
            
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
