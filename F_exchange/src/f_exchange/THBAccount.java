package f_exchange;
import java.sql.*;
public class THBAccount extends AccountOperations {
    
    @Override
    public String createAccount(int usr_id, int currency_id) {
        Connection con = DBConnection.connectDB();
        String account_existed;
        if(con != null) {
            try{
                account_existed = getAccountForExchange(usr_id, 5);
                if(!account_existed.equals(" THB Account found sucessfully!!")) {
                    PreparedStatement insert_query = con.prepareStatement("insert into account(user_id, name, currency_id) values (?, 'THB ACCOUNT', ?)");
                    insert_query.setInt(1, usr_id);
                    insert_query.setInt(2, currency_id);
                    insert_query.executeUpdate();
                    return "THB Account created successfully!!";
                } else {
                    return "THB Account already exists!!";
                }
            }catch(SQLException ex) {
                System.out.println(ex.getMessage());
                return DBConnectionStatus(false);
            }
        } 
        else {
           return DBConnectionStatus(false); 
        }
    }
    
    
    public String getAccountForExchange(int usr_id, int currency_id) {
        Connection con = DBConnection.connectDB();
        if(con != null) {
            try{
                PreparedStatement select_query = con.prepareStatement("select id from account where user_id = ? and currency_id = ?");
                select_query.setInt(1, usr_id);
                select_query.setInt(2, currency_id);
                ResultSet acc_id = select_query.executeQuery();
                if(acc_id.next()){
                    this.setId(acc_id.getInt("id"));
                    return "THB Account found successfully!!";
                } else {
                    return "THB Account not found!!";
                }                
            }catch(SQLException ex) {
                System.out.println(ex.getMessage());
                return DBConnectionStatus(false);
            }
        } 
        else {
           return DBConnectionStatus(false); 
        }
    }
 

}
