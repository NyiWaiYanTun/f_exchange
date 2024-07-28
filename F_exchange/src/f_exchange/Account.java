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
public abstract class Account {
    
    private int id; 
    private int user_id;
    private String name;
    private int currency_id;
    private double balance;
    private double total_credit;
    private double total_debit;
    private int total_transactions;
       
    public String DBConnectionStatus(boolean status) {
        if(!status) {
            return "Server Error!!";
        } else {
            return "Connection Successful!!";
        }  
    }
     
    public abstract String createAccount(int usr_id, int currency_id);
    
    
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
     * @return the balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * @param balance the balance to set
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * @return the total_credit
     */
    public double getTotal_credit() {
        return total_credit;
    }

    /**
     * @param total_credit the total_credit to set
     */
    public void setTotal_credit(double total_credit) {
        this.total_credit = total_credit;
    }

    /**
     * @return the total_debit
     */
    public double getTotal_debit() {
        return total_debit;
    }

    /**
     * @param total_debit the total_debit to set
     */
    public void setTotal_debit(double total_debit) {
        this.total_debit = total_debit;
    }

    /**
     * @return the total_transactions
     */
    public int getTotal_transactions() {
        return total_transactions;
    }

    /**
     * @param total_transactions the total_transactions to set
     */
    public void setTotal_transactions(int total_transactions) {
        this.total_transactions = total_transactions;
    }
       
}
