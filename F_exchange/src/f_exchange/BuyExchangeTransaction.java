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
public class BuyExchangeTransaction extends Transaction implements TransactionCreateable{
    
    
      
    private Currency currency;
    
    @Override
    public String createTransaction(int usr_id, int currency_id, double amount) {
        
        Connection con = DBConnection.connectDB();
        String currency_exist;
        
        String user_thb_acc_exist;
        String admin_thb_acc_exist;
        String user_fxc_acc_exist;
        String admin_fxc_acc_exist;
        
        String usr_thb_acc_debit_res;
        String admin_thb_acc_credit_res;
        String usr_fxc_acc_credit_res;
        String admin_fxc_acc_debit_res;
        
        User admin = new User();
        User user = new User();
        String user_res = user.getUserWithID(usr_id);
        String admin_res = admin.getAdmin();
        if(!admin_res.equals("Admin found successfully!!") && !user_res.equals("User not found!!")) {
            System.out.println(admin_res + "\n" + user_res);
            return "Exchange cannot be performed!!";
        }

        
        if(con != null) {
            try {
                currency_exist = checkCurrencyExists(currency_id);
                if(currency_exist.equals("Currency found successfully!!")) {
                    
                   THBAccount user_thb_acc = new THBAccount();
                   THBAccount admin_thb_acc = new THBAccount();
                   FXCAccount user_fxc_acc = new FXCAccount();
                   FXCAccount admin_fxc_acc = new FXCAccount();
                   
                   user_thb_acc_exist = user_thb_acc.getAccountForExchange(usr_id, 5);
                   admin_thb_acc_exist = admin_thb_acc.getAccountForExchange(admin.getId(), 5);
                   
                   user_fxc_acc_exist = user_fxc_acc.getAccountForExchange(usr_id, currency_id, con);
                   admin_fxc_acc_exist = admin_fxc_acc.getAccountForExchange(admin.getId(), currency_id,con);
                   
                   if(user_thb_acc_exist.equals("THB Account found successfully!!") && admin_thb_acc_exist.equals("THB Account found successfully!!")
                      && user_fxc_acc_exist.equals("FXC Account found successfully!!") && admin_fxc_acc_exist.equals("FXC Account found successfully!!")){
                       
                       usr_thb_acc_debit_res = user_thb_acc.debit(user_thb_acc.getId(), this.getCurrency().getBuy_rate() * amount, usr_id);
                       admin_thb_acc_credit_res = admin_thb_acc.credit(admin_thb_acc.getId(), this.getCurrency().getBuy_rate() * amount, admin.getId());
                      
                       usr_fxc_acc_credit_res = user_fxc_acc.credit(user_fxc_acc.getId(), amount,usr_id);
                       admin_fxc_acc_debit_res = admin_fxc_acc.debit(admin_fxc_acc.getId(), amount, admin.getId());
                       
                       if(usr_thb_acc_debit_res.equals("Debited successfully!!") && admin_thb_acc_credit_res.equals("Credited successfully!!")
                          && usr_fxc_acc_credit_res.equals("Credited successfully!!") && admin_fxc_acc_debit_res.equals("Debited successfully!!")){
                          insertTrn(usr_id, user_fxc_acc.getId(), currency_id, user.getUsername(), amount, this.getCurrency().getBuy_rate(), user_thb_acc.getId());
                          return "Exchange Sell Transaction created successfully!!";
                           
                       } else {
                            //System.out.println(usr_thb_acc_debit_res + "\n" + admin_thb_acc_credit_res);
                            //System.out.println(usr_fxc_acc_credit_res + "\n" + admin_fxc_acc_debit_res);
                            return "Exchange Transaction fails!! Some accounts do not have enough balance!!";
                       }
                     
                   } else {
                        //System.out.println(user_thb_acc_exist + "\n" + admin_thb_acc_exist);
                        //System.out.println(user_fxc_acc_exist + "\n" + admin_fxc_acc_exist);
                        return "Exchange Transaction fails!! Some accounts do not exist to do exchange!!";
                   }
                   
                } else {
                    //System.out.println(currency_exist);
                    return "Exchange Transaction fails!! Invalid Currency!!";
                }
            }catch(SQLException ex) {
                System.out.println(ex.getMessage());
                return DBConnectionStatus(false);
            }
        }else {
            return DBConnectionStatus(false);
        }
        
    }
    
    public String checkCurrencyExists(int currency_id) {
        String currency_res;
        currency = new Currency();
        currency_res = currency.getCurrency(currency_id);
        if(currency_res.equals("Currency found successfully!!")){
            return "Currency found successfully!!";
        } else {
            return currency_res;
        }
    }
    
    public void insertTrn(int usr_id, int user_fxc_acc_id, int currency_id, String user_username, double amount, double rate, int user_thb_acc_id) throws SQLException {
        Connection con = DBConnection.connectDB();
        PreparedStatement insert_query = con.prepareStatement("insert into transaction (user_id, user_fxc_acc_id, currency_id, transaction_desc, transaction_type, amount, rate, user_thb_acc_id) values (?, ?, ?, ?, 'Exchange Buy', ?, ?, ?)");
        insert_query.setInt(1, usr_id);
        insert_query.setInt(2, user_fxc_acc_id);
        insert_query.setInt(3, currency_id);
        insert_query.setString(4, user_username + " Buys from F_Exchange");
        insert_query.setDouble(5, amount);
        insert_query.setDouble(6, rate);
        insert_query.setInt(7, user_thb_acc_id);
        insert_query.executeUpdate();
    }
    
    
    /**
     * @return the currency
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
     * @param currency the currency to set
     */
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
