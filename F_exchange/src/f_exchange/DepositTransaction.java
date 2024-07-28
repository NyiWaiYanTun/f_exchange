package f_exchange;
import java.sql.*;
public class DepositTransaction extends Transaction implements TransactionCreateable {

      
    @Override 
    public String createTransaction(int usr_id, int account_id, double amount){
        
        Connection con = DBConnection.connectDB();
        String credit_res;
        if(con != null) {
            try{
                AccountOperations acc = new AccountOperations();
                credit_res = acc.credit(account_id, amount, usr_id);
                if(credit_res.equals("Credited successfully!!")) {
                    insertTrn(usr_id, account_id, acc.getCurrency_id(), amount);
                    return "Deposit Transaction created successfully!!";
                } else {
                    //System.out.println(credit_res);
                    return "Deposit Transaction fails!!";
                }
            }catch(SQLException ex) {
                //System.out.println(ex.getMessage());
                return DBConnectionStatus(false);
            }
        } 
        else {
           return DBConnectionStatus(false); 
        }
    }
    
    public void insertTrn(int usr_id, int account_id, int currency_id, double amount) throws SQLException {
        Connection con = DBConnection.connectDB();
        PreparedStatement insert_query = con.prepareStatement("insert into transaction (user_id, user_fxc_acc_id, user_thb_acc_id, currency_id, transaction_desc, transaction_type, amount, rate) values (?, ?, ?, ?, 'Deposited', 'deposit', ?, 1)");
        insert_query.setInt(1, usr_id);
        insert_query.setInt(2, account_id);
        insert_query.setInt(3, account_id);
        insert_query.setInt(4, currency_id);
        insert_query.setDouble(5, amount);
        
        insert_query.executeUpdate();
    }
}
