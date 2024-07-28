package f_exchange.Frontend;

import f_exchange.*;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author williamkhant
 */
public class Login {
    
    
    public static void show() {
        String inp;
        int login_validation_count = 0;
        int inp_validation_count = 0;
       
        String result;
        String username;
        String password; 
        
        User user = new User();
      
        Scanner keyboard = new Scanner(System.in);
        
        do{
            if(inp_validation_count > 4) {
                System.out.println("\nYou crushed the app!!");
                System.exit(0);
            }
            System.out.print("\nEnter options: (1) Go back (2) Continue login: ");
                inp = keyboard.nextLine();
                switch(inp) {
                    case "1" : Auth.show();break;
                    case "2" : 
                    do{
                        if(login_validation_count > 4) {
                            System.out.println("\nYou crushed the app!!");
                            System.exit(0);
                        }
                        
                        System.out.println("\n==Login==");
                        System.out.print("Username: ");
                        username = keyboard.nextLine();
                        System.out.print("Password: ");
                        password = keyboard.nextLine();  
                   
                        result = user.login(username, password);
                        if(result.equals("Username not found!!") || result.equals("Wrong Password!!")){
                            System.out.println(result);
                        }
                        
                        login_validation_count++;
                    }while(result.equals("Username not found!!") || result.equals("Wrong Password!!"));
                    if(result.equals("Login Successful!!")){
                        System.out.println(result);
                        Home.show(user);
                    } else {
                        System.out.println(result);
                        System.exit(0);
                    } 
                    break;
                    default: System.out.println("Invalid input!!");break;      
                }
                inp_validation_count++;
            }while(!inp.equals("1") && !inp.equals("2"));
        
    }
}
