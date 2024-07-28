package f_exchange.Frontend;
import f_exchange.User;
import java.io.Console;
import java.util.Scanner;
public class Register {
    
     public static void show() {
         
        int register_validation_count = 0;
        int inp_validation_count = 0;
        String inp;
        
        String result;
        String username;
        String password;
        
        User user = new User();
        Scanner keyboard = new Scanner(System.in);
        
        do{
            if(inp_validation_count > 4) {
                System.out.println("\nYou crashed the app!!");
                System.exit(0);
            }
            System.out.print("\nEnter options: (1) Go back (2) Continue register: ");
            
            inp = keyboard.nextLine();
                    switch (inp) {
                        case "1": Auth.show();
                        case "2":
                        do {
                            if(register_validation_count > 4) {
                                System.out.println("\nYou crushed the app!!");
                                System.exit(0);
                            }
                            
                            System.out.println("\n==Register==");
                            System.out.print("Username: ");
                            username = keyboard.nextLine();
                            System.out.print("Password: ");
                            password = keyboard.nextLine();
                                                                   
                            result = user.register(username, password); 
                            if(result.equals("Username is not available!!") || result.equals("Username and password cannot be empty!!")){
                                System.out.println(result);
                            } 
                            
                            register_validation_count++;
                        }while(result.equals("Username is not available!!") || result.equals("Username and password cannot be empty!!")); 
                        if(result.equals("Registeration Successful!!")){
                            System.out.println(result);
                            Login.show();
                        } else {
                            System.out.println(result);
                            System.exit(0);
                        }
                        break;
                        default: System.out.println("Invalid Input!!");break;
                    }
                    inp_validation_count++;
                }while (!inp.equals("1")  && !inp.equals("2"));  
          }
}
