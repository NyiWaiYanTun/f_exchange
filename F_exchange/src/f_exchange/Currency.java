/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package f_exchange;

import java.sql.*;
import java.util.Vector;

/**
 *
 * @author williamkhant
 */
public class Currency {


    private int id; 
    private String name;
    private double sell_rate;
    private double buy_rate;
    private Vector<Currency> currencies = new Vector();
   
    
     public String DBConnectionStatus(boolean status) {
        if(!status) {
            return "Server Error!!";
        } else {
            return "Connection Successful!!";
        }  
    }
    
    public String getCurrency(int id) {
       Connection con = DBConnection.connectDB();
       
       if(con != null) {
           try {
               PreparedStatement select_query = con.prepareStatement("select * from currency where id = ?");
               select_query.setInt(1, id);
               ResultSet currency = select_query.executeQuery();
               if(currency.next()){
                   this.setId(currency.getInt("id"));
                   this.setName(currency.getString("name"));
                   this.setSell_rate(currency.getDouble("sell_rate"));
                   this.setBuy_rate(currency.getDouble("buy_rate"));
                   return "Currency found successfully!!";
               }else {
                   return "Currency not found!!";
               }
           } catch(SQLException ex) {
               //System.out.println(ex.getMessage());
               return DBConnectionStatus(false);
           } 
       } else {
           return DBConnectionStatus(false);
       }
    }
    
    public String getAllCurrencies() {
       Connection con = DBConnection.connectDB();
       int i = 0;
       if(con != null) {
           try {
               PreparedStatement select_query = con.prepareStatement("select * from currency where id != 5");
               ResultSet currs = select_query.executeQuery();
               
               while(currs.next()){
                    currencies.add(new Currency());
                    currencies.get(i).setId(currs.getInt("id"));
                    currencies.get(i).setName(currs.getString("name"));
                    currencies.get(i).setSell_rate(currs.getDouble("sell_rate"));
                    currencies.get(i).setBuy_rate(currs.getDouble("buy_rate"));
                   i++;
               }
               if(this.getCurrencies().isEmpty()) {
                   return "Currency not found!!";
               } else {
                   return "Currency found successfully!!";
               }
               
           } catch(SQLException ex) {
               //System.out.println(ex.getMessage());
               return DBConnectionStatus(false);
           } 
       } else {
           return DBConnectionStatus(false);
       }
    }
    
    public String createCurrency(int usr_id, String name, double sell_rate, double buy_rate) {
       Connection con = DBConnection.connectDB();
       
       if(con != null) {
           try {
               User user = new User();
               user.getUserWithID(usr_id);
               if(!user.isIsAdmin()) {
                   return "Access Denied";
               }
               
               if(buy_rate > sell_rate) {
                   return "Buy rate cannot be higher than sell rate!!";
               } 
               
               if(sell_rate < buy_rate){
                   return "Sell rate cannot be lower than buy rate!!";
               }
               PreparedStatement update_query = con.prepareStatement("insert into currency (name, sell_rate, buy_rate)values(?, ?, ?)");
               update_query.setString(1, name);
               update_query.setDouble(2, sell_rate);
               update_query.setDouble(3, buy_rate);
               update_query.executeUpdate();
               return "Currency created successfully!!";
           } catch (SQLException ex) {
               System.out.println(ex.getMessage());
               return DBConnectionStatus(false);
           }
       } else {
           return DBConnectionStatus(false);
       }
    }
    
    public String UpdateBuyRate(int id, int usr_id, double rate) {
       Connection con = DBConnection.connectDB();
       
       if(con != null) {
           try {
               User user = new User();
               user.getUserWithID(usr_id);
               if(!user.isIsAdmin()) {
                   return "Access Denied!!";
               }
               
                if(this.getSell_rate() < rate) {
                   return "Buy rate cannot be higher than sell rate!!";
               }
               PreparedStatement update_query = con.prepareStatement("update currency set buy_rate = ? where id = ?");
               update_query.setDouble(1, rate);
               update_query.setDouble(2, id);
               update_query.executeUpdate();
               return "Buy rate updated successfully";
           } catch (SQLException ex) {
               //System.out.println(ex.getMessage());
               return DBConnectionStatus(false);
           }
       } else {
           return DBConnectionStatus(false);
       }
    }
    
     public String UpdateSellRate(int id, int usr_id, double rate) {
       Connection con = DBConnection.connectDB();
       
       if(con != null) {
           try {
               User user = new User();
               user.getUserWithID(usr_id);
               if(!user.isIsAdmin()) {
                   return "Access Denied!!";
               }
               if(this.getBuy_rate() > rate) {
                   return "Sell rate cannot be lower than buy rate!!";
               }
               PreparedStatement update_query = con.prepareStatement("update currency set sell_rate = ? where id = ?");
               update_query.setDouble(1, rate);
               update_query.setDouble(2, id);
               update_query.executeUpdate();
               return "Buy rate updated successfully";
           } catch (SQLException ex) {
               //System.out.println(ex.getMessage());
               return DBConnectionStatus(false);
           }
       } else {
           return DBConnectionStatus(false);
       }
    }
    
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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the rate
     */
    public double getSell_rate() {
        return sell_rate;
    }

    /**
     * @param rate the rate to set
     */
    public void setSell_rate(double rate) {
        this.sell_rate = rate;
    }
     
    
       /**
     * @return the buy_rate
     */
    public double getBuy_rate() {
        return buy_rate;
    }

    /**
     * @param buy_rate the buy_rate to set
     */
    public void setBuy_rate(double buy_rate) {
        this.buy_rate = buy_rate;
    }

        /**
     * @return the currencies
     */
    public Vector<Currency> getCurrencies() {
        return currencies;
    }
}
