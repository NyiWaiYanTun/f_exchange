package f_exchange.Frontend;

import java.util.Scanner;

public class Auth {
 
   
    public static void main (String[] args) {
            show();
    }
    
    public static void show () {
        String inp;
        int inp_validation_count = 0;
        
        
       
        Scanner keyboard = new Scanner(System.in);
        
        System.out.println("\n==Welcome to F_Exchange==");
        System.out.println("(1) Register\n(2) Login \n(3) Exit");
        do{
           if(inp_validation_count > 4) {
                System.out.println("\nYou crashed the app!!");
                System.exit(0);
            }
            System.out.print("Enter options: (1) Register (2) Login (3) Exit: ");
            
            inp = keyboard.nextLine();
            switch(inp){
                case "1": Register.show();
                break;
                case "2": Login.show();
                break;
                case "3": 
                System.out.println("\nThanks for using F_Exchnage!!");
                System.exit(0);
                break;
                default: System.out.println("Invalid Input!!");break; 
            }
            inp_validation_count++;  
        } while(!inp.equals("1") && !inp.equals("2") && !inp.equals("3"));   
    }
}
