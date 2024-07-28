/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package f_exchange;


import java.sql.*;
/**
 *
 * @author williamkhant
 */
public class WithdrawalTransaction extends Transaction implements TransactionCreateable{
      
    @Override 
    public String createTransaction(int usr_id, int account_id, double amount){
        
        Connection con = DBConnection.connectDB();
        String debit_res;
        if(con != null) {
          
            try{
                AccountOperations acc = new AccountOperations();
                debit_res = acc.debit(account_id, amount, usr_id);
                if(debit_res.equals("Debited successfully!!")) {
                    insertTrn(usr_id, account_id,acc.getCurrency_id(), amount);
                    return "Withdrawal Transaction created successfully!!";
                } else {
                    System.out.println(debit_res);
                    return "Withdraw Transaction fails!!";
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
        PreparedStatement insert_query = con.prepareStatement("insert into transaction (user_id, user_fxc_acc_id, user_thb_acc_id, currency_id, transaction_desc, transaction_type, amount, rate) values (?, ?, ?, ?, 'Withdrawed', 'withdraw', ?, 1)");
        insert_query.setInt(1, usr_id);
        insert_query.setInt(2, account_id);
        insert_query.setInt(3, account_id);
        insert_query.setInt(4, currency_id);
        insert_query.setDouble(5, amount);
        
        insert_query.executeUpdate();
    }
}
