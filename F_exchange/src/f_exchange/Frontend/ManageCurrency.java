/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package f_exchange.Frontend;

import f_exchange.Currency;
import static f_exchange.Frontend.Exchange.buy;
import static f_exchange.Frontend.Exchange.keyboard;
import static f_exchange.Frontend.Exchange.sell;
import f_exchange.User;
import java.util.Vector;
/**
 *
 * @author williamkhant
 */
public class ManageCurrency {
    
    
    public static void show (User user) {
                
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
           System.out.println("No currencies to be managed!!");
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
                System.out.print("\nEnter options: (1)Update Buy Rate (2)Update Sell Rate (3)Back to Home: ");
                    inp = keyboard.nextLine();
                    switch(inp) {
                        case "1": updateBuyRate(user); break;
                        case "2": updateSellRate(user); break;
                        case "3": homePage.show(user);break;
                        default: System.out.println("Invalid Input!!"); break;
                        }
                    inp_val_count++;
           }
       } while (!inp.equals("1") && !inp.equals("2") && !inp.equals("3"));
    }
    
    public static void updateBuyRate(User user) {
        int inp_validation_count = 0;
        int curr_validation_count = 0;
        int buy_rate_res_val_count = 0;
        boolean inp_type_check = true;
        String inp;
        String buy_rate_res;
        String buy_rate;
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
            do {
                if(buy_rate_res_val_count > 4){
                    System.out.println("\nYou crashed the app!!");
                    System.exit(0);
                }
                System.out.print("Enter buy rate: ");
                buy_rate = keyboard.nextLine();
                
                buy_rate_res = curr.UpdateBuyRate(curr.getId(), user.getId(), Double.valueOf(buy_rate));
                buy_rate_res_val_count++;
                if(buy_rate_res.equals("Buy rate cannot be higher than sell rate!!")) {
                    System.out.println(buy_rate_res);
                }
            } while (buy_rate_res.equals("Buy rate cannot be higher than sell rate!!"));
            
            if(buy_rate_res.equals("Server Error!!") || buy_rate_res.equals("Access Denied!!!")){
                System.out.println(buy_rate_res);
                System.exit(0);
            } else {
                System.out.println(buy_rate_res);
                show(user);
            }
        } else{
            System.out.println(curr_res);
            System.exit(0);
        }
    }
    
    
        public static void updateSellRate(User user) {
        int inp_validation_count = 0;
        int curr_validation_count = 0;
        int sell_rate_res_val_count = 0;
        boolean inp_type_check = true;
        String inp;
        String sell_rate_res;
        String sell_rate;
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
            do {
                if(sell_rate_res_val_count > 4){
                    System.out.println("\nYou crashed the app!!");
                    System.exit(0);
                }
                System.out.print("Enter buy rate: ");
                sell_rate = keyboard.nextLine();
                
                sell_rate_res = curr.UpdateSellRate(curr.getId(), user.getId(), Double.valueOf(sell_rate));
                
                if(sell_rate_res.equals("Sell rate cannot be lower than buy rate!!")) {
                    System.out.println(sell_rate_res);
                }
                sell_rate_res_val_count++;
            } while (sell_rate_res.equals("Sell rate cannot be lower than buy rate!!"));
            if(sell_rate_res.equals("Server Error!!") || sell_rate_res.equals("Access Denied!!!")){
                System.out.println(sell_rate_res);
                System.exit(0);
            } else {
                System.out.println(sell_rate_res);
                show(user);
            }
        } else{
            System.out.println(curr_res);
            System.exit(0);
        }
    }
}
