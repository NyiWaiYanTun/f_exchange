/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package f_exchange.Frontend;

import f_exchange.BuyExchangeTransaction;
import f_exchange.Currency;
import f_exchange.SellExchangeTransaction;
import f_exchange.User;
import java.util.*;
import java.util.Vector;

/**
 *
 * @author williamkhant
 */
public class Exchange {
    
      static Scanner keyboard = new Scanner(System.in);
    
      public static void show(User user) {
        
       String currs_res;
       Vector<Currency> currs;
       
       int inp_val_count = 0;
       String inp = "";
       
       Home homePage = new Home();
       
       System.out.println("\n=====Available Currency=====");
       
       Currency currencies = new Currency();
       currs_res = currencies.getAllCurrencies();
       if(currs_res.equals("Server Error!!")) {
           System.out.println(currs_res);
           System.exit(0);
       } else if (currs_res.equals("Currency not found!")){
           System.out.println("No currencies to be exchanged!!");
       } else {
           currs = currencies.getCurrencies();
           System.out.println("Currency ID/Currnecy Name/Buy Rate/Sell Rate");
           for(Currency curr : currs) {
               System.out.println(curr.getId()+ "           " + curr.getName() + "           " + curr.getBuy_rate() + "        " + curr.getSell_rate());
           }
       }
       do {
           if(inp_val_count > 4) {
               System.out.println("\nYou crashed the app!!");
               System.exit(0);
           } else {
                System.out.print("\nEnter options: (1)Buy (2)Sell (3)Back to Home: ");
                    inp = keyboard.nextLine();
                    switch(inp) {
                        case "1": buy(user); break;
                        case "2": sell(user); break;
                        case "3": homePage.show(user);break;
                        default: System.out.println("Invalid Input!!"); break;
                        }
                    inp_val_count++;
           }
       } while (!inp.equals("1") && !inp.equals("2") && !inp.equals("3"));
    }
      
   public static void buy(User user) {
       
        int inp_validation_count = 0;
        int curr_validation_count = 0;
        boolean inp_type_check = true;
        String inp;
        String trn_res;
        String amount;
        String curr_res = "";
        
        Currency curr = new Currency();
        
        do {
            if(inp_validation_count > 1 || curr_validation_count > 1){
                    System.out.println("\nYou crashed the app!!");
                    System.exit(0);
                }
            System.out.print("Enter currency id: ");
            try {
                inp = keyboard.nextLine();
                curr_res = curr.getCurrency(Integer.valueOf(inp));
                if(curr_res.equals("Currency not found!!")) {
                    System.out.println("Invalid Currency!!");
                }
                curr_validation_count++;
          
            } catch (NumberFormatException ex) {
                System.out.println("Invalid Input!!");
                inp_type_check = false;
                inp_validation_count++;
            }
            if(curr_res.equals("Currency found successfully!!")) {
               inp_type_check = true;
            }
        } while (!inp_type_check || curr_res.equals("Currency not found!!"));
            
        if(curr_res.equals("Currency found successfully!!")) {
            
            System.out.print("Enter amount: ");
            amount = keyboard.nextLine();
                
            BuyExchangeTransaction trn = new BuyExchangeTransaction();
            trn_res = trn.createTransaction(user.getId(), curr.getId(), Double.valueOf(amount));
            if(trn_res.equals("Server Error!!")){
                System.out.println(trn_res);
                System.exit(0);
            } else {
                System.out.println(trn_res);
                if(trn_res.equals("Exchange Transaction fails!! Some accounts do not have enough balance!!") || trn_res.equals("Exchange cannot be performed!!") 
                  || trn_res.equals("Exchange Transaction fails!! Some accounts do not exist to do exchange!!") || trn_res.equals("Exchange Transaction fails!! Invalid Currency!!") ) {
                    System.out.println("Try Again!!");
            }
                show(user);
            }
        } else{
            System.out.println(curr_res);
            System.exit(0);
        }
    }
   
      public static void sell(User user) {
       
        int inp_validation_count = 0;
        int curr_validation_count = 0;
        boolean inp_type_check = true;
        String inp;
        String trn_res;
        String amount;
        String curr_res = "";
        
        Currency curr = new Currency();
        
        do {
            
            if(inp_validation_count > 1 || curr_validation_count > 1){
                    System.out.println("\nYou crashed the app!!");
                    System.exit(0);
                }
            System.out.print("Enter currency id: ");
            try {
                inp = keyboard.nextLine();
                curr_res = curr.getCurrency(Integer.valueOf(inp));
                if(curr_res.equals("Currency not found!!")) {
                    System.out.println("Invalid Currency!!");
                }
                curr_validation_count++;
          
            } catch (NumberFormatException ex) {
                System.out.println("Invalid Input!!");
                inp_type_check = false;
                inp_validation_count++;
            }
            if(curr_res.equals("Currency found successfully!!")) {
               inp_type_check = true;
            }
        } while (!inp_type_check || curr_res.equals("Currency not found!!"));
            
        if(curr_res.equals("Currency found successfully!!")) {
            
            System.out.print("Enter amount: ");
            amount = keyboard.nextLine();
                
            SellExchangeTransaction trn = new SellExchangeTransaction();
            trn_res = trn.createTransaction(user.getId(), curr.getId(), Double.valueOf(amount));
            if(trn_res.equals("Server Error!!")){
                System.out.println(trn_res);
                System.exit(0);
            } else {
                System.out.println(trn_res);
                if(trn_res.equals("Exchange Transaction fails!! Some accounts do not have enough balance!!") || trn_res.equals("Exchange cannot be performed!!") 
                  ||trn_res.equals("Exchange Transaction fails!! Some accounts do not exist to do exchange!!") || trn_res.equals("Exchange Transaction fails!! Invalid Currency!!") ) {
                    System.out.println("Try Again!!");
            }   
                show(user);
            }
        } else{
            System.out.println(curr_res);
            System.exit(0);
        }
    }
}
