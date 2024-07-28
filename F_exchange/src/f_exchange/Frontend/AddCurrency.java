package f_exchange.Frontend;

import f_exchange.Currency;
import f_exchange.User;
import java.util.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author williamkhant
 */
public class AddCurrency {
    
    static Scanner keyboard = new Scanner(System.in);
    public static void show(User user) {
        
        int inp_val_count = 0;
        String name;
        String buy_rate;
        String sell_rate;
        String curr_res = "";
        int curr_res_val_count = 0;
        Currency curr = new Currency();
            
        do{
            if(inp_val_count > 2 || curr_res_val_count > 2) {
                System.out.println("\nYou crashed the app!!");
                System.exit(0);
            }
            System.out.print("Enter Currency Name: ");
            name = keyboard.nextLine();
            try{
                System.out.print("Enter Buy rate: ");
                buy_rate = keyboard.nextLine();
                System.out.print("Enter Sell Rate: ");
                sell_rate = keyboard.nextLine();
                curr_res = curr.createCurrency(user.getId(), name, Double.valueOf(sell_rate),Double.valueOf(buy_rate) );
            } catch (NumberFormatException ex) {
                System.out.println("Invalid Input!!");
                inp_val_count++;
            }
            if(curr_res.equals("Sell rate cannot be lower than buy rate!!") || curr_res.equals("Buy rate cannot be higher than sell rate!!")) {
                System.out.println(curr_res);
                curr_res_val_count++;
            }
        } while (curr_res.equals("Sell rate cannot be lower than buy rate!!") || curr_res.equals("Buy rate cannot be higher than sell rate!!"));
        
        if(curr_res.equals("Server Error!!") || curr_res.equals("Access Denied!!!")){
            System.out.println(curr_res);
            System.exit(0);
        } else {
            System.out.println(curr_res);
            ManageCurrency.show(user);
        }        
    }
}
