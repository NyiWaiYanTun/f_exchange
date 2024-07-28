/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package f_exchange;

import java.sql.*;
import java.util.*;

/**
 *
 * @author williamkhant
 */
public class Transaction {

    private int id;
    private Timestamp created_at;
    private int user_id;
    private int user_account_id;
    private int currency_id;
    private String transaction_des;
    private String transaction_type;
    private double amount;
    private double rate;
    
    private Vector<Transaction> trns = new Vector(); 
   
    public String DBConnectionStatus(boolean status) {
        if(!status) {
            return "Server Error!!";
        } else {
            return "Connection Successful!!";
        }  
    }
    
    public String getTransactions(int account_id, int acc_currency_id) {
        Connection con = DBConnection.connectDB();
        
        if(con != null) {
            int i = 0; 
            String acc;
            if(acc_currency_id == 5) {
                acc = "user_thb_acc_id";
            } else {
                acc = "user_fxc_acc_id";
            }  
            
            try {
                PreparedStatement select_query;
                if(acc_currency_id == 5) {
                    select_query = con.prepareStatement("select * from transaction where user_thb_acc_id = ?");
                } else {
                    select_query = con.prepareStatement("select * from transaction where user_fxc_acc_id = ?");

                }
                select_query.setInt(1, account_id);
                ResultSet trns_res = select_query.executeQuery();
                
                while(trns_res.next()) {
                    trns.add(new Transaction());
                    trns.get(i).setId(trns_res.getInt("id"));
                    trns.get(i).setCreated_at(trns_res.getTimestamp("created_at"));
                    trns.get(i).setUser_id(trns_res.getInt("user_id"));
                    trns.get(i).setUser_account_id(trns_res.getInt(acc));
                    trns.get(i).setCurrency_id(trns_res.getInt("currency_id"));
                    trns.get(i).setTransaction_des(trns_res.getString("transaction_desc"));
                    trns.get(i).setTransaction_type(trns_res.getString("transaction_type"));
                    trns.get(i).setAmount(trns_res.getInt("amount"));
                    trns.get(i).setRate(trns_res.getInt("rate"));
                    i++;
                }
                if(!this.getTrns().isEmpty()) {
                    return "Transactions found successfully!!";
                } else {
                    return "Transactions not found!!";
                }            
            } catch(SQLException ex) {
                System.out.println(ex.getMessage());
                return DBConnectionStatus(false);
            }
        } else {
            return DBConnectionStatus(false);
        }
    }
     /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the user_id
     */
    public int getUser_id() {
        return user_id;
    }

    /**
     * @param user_id the user_id to set
     */
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    /**
     * @return the created_at
     */
    public Timestamp getCreated_at() {
        return created_at;
    }

    /**
     * @param created_at the created_at to set
     */
    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    /**
     * @return the user_account_id
     */
    public int getUser_account_id() {
        return user_account_id;
    }

    /**
     * @param user_account_id the user_account_id to set
     */
    public void setUser_account_id(int user_account_id) {
        this.user_account_id = user_account_id;
    }

    /**
     * @return the currency_id
     */
    public int getCurrency_id() {
        return currency_id;
    }

    /**
     * @param currency_id the currency_id to set
     */
    public void setCurrency_id(int currency_id) {
        this.currency_id = currency_id;
    }

    /**
     * @return the transaction_des
     */
    public String getTransaction_des() {
        return transaction_des;
    }

    /**
     * @param transaction_des the transaction_des to set
     */
    public void setTransaction_des(String transaction_des) {
        this.transaction_des = transaction_des;
    }

    /**
     * @return the transaction_type
     */
    public String getTransaction_type() {
        return transaction_type;
    }

    /**
     * @param transaction_type the transaction_type to set
     */
    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    /**
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * @return the rate
     */
    public double getRate() {
        return rate;
    }

    /**
     * @param rate the rate to set
     */
    public void setRate(double rate) {
        this.rate = rate;
    }
    
     /**
     * @return the trns
     */
    public Vector<Transaction> getTrns() {
        return trns;
    }   
}
