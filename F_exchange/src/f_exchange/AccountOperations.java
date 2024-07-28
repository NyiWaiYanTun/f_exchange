package f_exchange;

import java.sql.*;
import java.util.Vector;
public class AccountOperations extends Account{

    /**
     * @return the accounts
     */
    Vector<Account> accs = new Vector();
    @Override 
    public String createAccount(int usr_id, int currency_id) {
        return "Do nothing";
    }
    
    public String getAccounts(int usr_id) {
       Connection con = DBConnection.connectDB();
       int i = 0;
       if(con != null) {
            try{
                PreparedStatement select_query = con.prepareStatement("select id, user_id, name from account where user_id = ?");
                select_query.setInt(1, usr_id);
                ResultSet accounts = select_query.executeQuery();
                
                while(accounts.next()) {
                    if(accounts.getString("name").equals("THB ACCOUNT")){
                        accs.add(new THBAccount());
                    } else {
                        accs.add(new FXCAccount());
                    }
                    accs.get(i).setId(accounts.getInt("id"));
                    accs.get(i).setUser_id(accounts.getInt("user_id"));
                    accs.get(i).setName(accounts.getString("name"));
                    i++;   
                }
                if(!this.getAccs().isEmpty()) {
                    return "Accounts found successfully!!";
                } else {
                    return "Accounts not found!!";
                }
            }catch (SQLException ex) {
                //System.out.println(ex.getMessage());
                return DBConnectionStatus(false);
            }
        } else {
            return DBConnectionStatus(false);
        }
    }
    
    public String getAccount(int account_id, int usr_id) {
        Connection con = DBConnection.connectDB();
        
        if(con != null) {
            try{
                PreparedStatement select_query = con.prepareStatement("select * from account where id = ? and user_id = ?");
                select_query.setInt(1,account_id);
                select_query.setInt(2,usr_id);
                ResultSet account = select_query.executeQuery();
                if(account.next()) {
                    this.setId(account.getInt("id"));
                    this.setUser_id(account.getInt("user_id"));
                    this.setName(account.getString("name"));
                    this.setCurrency_id(account.getInt("currency_id"));
                    this.setBalance(account.getDouble("balance"));
                    this.setTotal_credit(account.getDouble("total_credit"));
                    this.setTotal_debit(account.getInt("total_debit"));
                    this.setTotal_transactions(account.getInt("total_transaction")); 
                    return "Account found successfully!!";
                }   else {
                    return "Account not found!!";
                }
            }catch (SQLException ex) {
                //System.out.println(ex.getMessage());
                return DBConnectionStatus(false);
            }
        } else {
            return DBConnectionStatus(false);
        }
    }
    
      
    public String debit(int account_id, double amount, int usr_id) {
        Connection con = DBConnection.connectDB();
        String account_res;
        if(con != null) {
            try{
               account_res = getAccount(account_id, usr_id);
               if(account_res.equals("Account found successfully!!")){
                   if(this.getBalance() - amount < 0) {
                        return this.getName() + " Balance Insufficient!!";
                   } 
                    updateBalance(account_id, amount, "debit");
                    updateTotalDebit(account_id, amount);
                    updateTotalTransaction(account_id);
                    return "Debited successfully!!";
               } else {
                   System.out.println(account_res);
                   return "Debit unsuccessful!";
               }
            }catch(SQLException ex) {
                System.out.println(ex.getMessage());
                return DBConnectionStatus(false);
            }
        } else {
              return DBConnectionStatus(false);
        }   
    }
    
    public String credit(int account_id, double amount, int usr_id) {
        Connection con = DBConnection.connectDB();
        String account_res;
        if(con != null) {
            try{ 
                account_res = getAccount(account_id, usr_id);
                if(account_res.equals("Account found successfully!!")){
                    updateBalance(account_id, amount, "credit");
                    updateTotalCredit(account_id, amount);
                    updateTotalTransaction(account_id);
                    return "Credited successfully!!";
                } else {
                    System.out.println(account_res);
                    return "Credit unsuccessful!!";
                }
            }catch(SQLException ex) {
                System.out.println(ex.getMessage());
                return DBConnectionStatus(false);
            }
        } else {
              return DBConnectionStatus(false);
        }   
    }
      
    public void updateBalance(int account_id, double amount, String debit_credit) throws SQLException{
        
       Connection con = DBConnection.connectDB(); 
       PreparedStatement update_query;
       if(debit_credit.equals("debit")){
            update_query = con.prepareStatement("update account set balance = balance - ? where id = ?");
       } else {
            update_query = con.prepareStatement("update account set balance = balance + ? where id = ?");
       }
       
       update_query.setDouble(1, amount);
       update_query.setInt(2, account_id);
       update_query.executeUpdate();
    }
     
    public void updateTotalDebit(int account_id, double amount) throws SQLException{
       Connection con = DBConnection.connectDB();
       PreparedStatement update_query = con.prepareStatement("update account set total_debit = total_debit + ? where id = ?");
       update_query.setDouble(1, amount);
       update_query.setInt(2, account_id);
       update_query.executeUpdate();
    }
       
    public void updateTotalCredit(int account_id, double amount) throws SQLException{
       Connection con = DBConnection.connectDB();
       PreparedStatement update_query = con.prepareStatement("update account set total_credit = total_credit + ? where id = ?");
       update_query.setDouble(1, amount);
       update_query.setInt(2, account_id);
       update_query.executeUpdate();
    }
    
     public void updateTotalTransaction(int account_id) throws SQLException{
       Connection con = DBConnection.connectDB();
       PreparedStatement update_query = con.prepareStatement("update account set total_transaction = total_transaction + ? where id = ?");
       update_query.setInt(1, 1);
       update_query.setInt(2, account_id);
       update_query.executeUpdate();
    }
      
    public Vector<Account> getAccs() {
        return accs;
    }
}
