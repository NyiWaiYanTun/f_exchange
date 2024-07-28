/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package f_exchange.Frontend;

import f_exchange.User;
import java.io.Console;
import java.util.Scanner;

/**
 *
 * @author williamkhant
 */
public class Home {
    
    public static void show(User user) {
        
        String inp;
        int inp_validation_count = 0;
        
        Scanner keyboard = new Scanner(System.in);
        
        System.out.println("\nWelcome " +  user.getUsername());
            do{
                if(inp_validation_count > 4) {
                    System.out.println("\nYou crashed the app!!");
                    System.exit(0);
                }
                
                if(user.isIsAdmin()) {
                    System.out.print("\n(1)Manage Currency (2)Create new currecny (3)Log out\nEnter options: ");
                    inp = keyboard.nextLine();
                    switch(inp){
                        case "1": ManageCurrency.show(user); break;
                        case "2": AddCurrency.show(user);break;
                        case "3": Auth.show(); break;
                        default: System.out.println("Invalid Input!!"); break;
                    }
                
                } else {
                    System.out.print("\n(1)Exchange (2)My Accounts (3)Log out\nEnter options: ");
                    inp = keyboard.nextLine();
                    switch(inp){
                        case "1": Exchange.show(user); break;
                        case "2": ManageAccounts.show(user); break;
                        case "3": Auth.show(); break;
                        default: System.out.println("Invalid Input!!"); break;
                    }
                }
                
                inp_validation_count++;
            }while(!inp.equals("1") && !inp.equals("2") && !inp.equals("3"));

    }
}
