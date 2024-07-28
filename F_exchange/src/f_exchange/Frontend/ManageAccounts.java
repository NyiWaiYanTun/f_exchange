/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package f_exchange.Frontend;

import f_exchange.Account;
import f_exchange.AccountOperations;
import f_exchange.Currency;
import f_exchange.DepositTransaction;
import f_exchange.FXCAccount;
import f_exchange.Transaction;
import f_exchange.User;
import f_exchange.WithdrawalTransaction;
import java.util.*;

/**
 *
 * @author williamkhant
 */
public class ManageAccounts {
    static Scanner keyboard = new Scanner(System.in);
    
    public static void show(User user) { 
        
        String inp;
        int inp_validation_count = 0; 
        String accounts_res;
        
        Home homePage = new Home();
        AccountOperations accounts = new AccountOperations();
        Vector<Account> accs;
        
        accounts_res = accounts.getAccounts(user.getId());
        accs = accounts.getAccs();
        
        if(accounts_res.equals("Server Error!!")) {
            System.out.println(accounts_res);
            System.exit(0);
        } else if (accounts_res.equals("Accounts not found!!")){
            System.out.println("Invalid User account!!");
            System.exit(0);
        }
        
        System.out.println("\n===My Accounts===");
        if(accounts_res.equals("Accounts found successfully!!")) {
            System.out.println("Account_ID/       Account Name");
            for(Account acc : accs) {
            System.out.println(acc.getId()+ "               " + acc.getName());
            }
                do {
                    if(inp_validation_count > 4) {
                        System.out.println("\nYou crashed the app!!");
                        System.exit(0);
                    }
                    System.out.print("\nEnter options: (1)Create Account (2)Select Account to view (3)Back to Home: ");
                    inp = keyboard.nextLine();
                    switch(inp) {
                        case "1": createAccount(user); break;
                        case "2": toViewAccount(user);
                        break;
                        case "3": homePage.show(user);break;
                        default: System.out.println("Invalid Input!!"); break;
                        }
                    inp_validation_count++;  
                } while(!inp.equals("1") && !inp.equals("2") && !inp.equals("3"));
        }
        
    }
    
    public static void toViewAccount(User user) {
        String inp;
        String account_res = "";
        int account_validation_count = 0;
        int id = -1;
        boolean inp_type_check = true; 
        int inp_validation_count = 0; 
        
        AccountOperations account = new AccountOperations();
        
        do {
            if(inp_validation_count > 2 || account_validation_count > 2) {
                System.out.println("\nYou crashed the app!!");
                System.exit(0);
            }
            System.out.print("Enter account id to view: ");
            try {
                inp = keyboard.nextLine();
                id = Integer.parseInt(inp);
                account_res = account.getAccount(id, user.getId());
                
                if(account_res.equals("Account not found!!")) {
                    System.out.println(account_res);
                }
                account_validation_count++;
                
            } catch(NumberFormatException ex){
                 System.out.println("Invalid Input!!");
                 inp_type_check = false;
                 inp_validation_count++;
            }    
            
            if(account_res.equals("Account found successfully!!")) {
                    inp_type_check = true;
            }
        } while(account_res.equals("Account not found!!") || !inp_type_check );
        
        if(account_res.equals("Account found successfully!!")){
         
            AccountDetails(account, user);
            
        } else {
            System.out.println(account_res);
            System.exit(0);
        } 
    }
    
    
    public static void createAccount(User user) {
        String currs_res;
        String acc_res;
        String curr_res = "";
        int inp_validation_count = 0;
        int curr_validation_count = 0;
        String inp = "";
        boolean inp_type_check = true;
        Vector<Currency> currs = new Vector();
        Currency currencies = new Currency();
        FXCAccount account = new FXCAccount();
       
        
        System.out.println("=====Please enter the following currency id to create account=====");
        currs_res = currencies.getAllCurrencies();
        currs = currencies.getCurrencies();
        
        if(currs_res.equals("Server Error!!")) {
            System.out.println(currs_res);
            System.exit(0);
        } else if (currs_res.equals("Currency not found!!")){
            System.out.println(currs_res);
            show(user);
        } else {
           System.out.println("Currency ID/Currnecy Name");
           for(Currency curr : currs) {
                System.out.println(curr.getId() + "             " + curr.getName());
           }
           
            do {
                System.out.print("Enter currency id: ");
                if(inp_validation_count > 2 || curr_validation_count > 2){
                    System.out.println("\nYou crashed the app!!");
                    System.exit(0);
                }
                
                try {
                    inp = keyboard.nextLine();
                    curr_res = currencies.getCurrency(Integer.valueOf(inp));
                    
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
            
            if(currs_res.equals("Currency found successfully!!")) {
                acc_res =account.createAccount(user.getId(), Integer.valueOf(inp));
                if(acc_res.equals("Server Error!!")){
                    System.out.println(acc_res);
                    System.exit(0);
                } else {
                    System.out.println(acc_res);
                    if(acc_res.equals(currencies.getName() + " Account already exists!!")) {
                        System.out.println("Try Again!!");
                    }
                    show(user);
                }       
            } else{
                System.out.println(currs_res);
                System.exit(0);
            } 
        }
    }
    
    public static void AccountDetails(AccountOperations account, User user) {
        
        String inp;
        int inp_validation_count = 0;
               
        Currency currency = new Currency();
        currency.getCurrency(account.getCurrency_id());
        System.out.println("\n=====" + account.getName() + "=====");
        System.out.println("Currency: " + currency.getName());
        System.out.println("Balance: " + account.getBalance());
        System.out.println("Total Credit: " + account.getTotal_credit());
        System.out.println("Total Debit: " + account.getTotal_debit());
        System.out.println("Total transactions: " + account.getTotal_transactions());
        
        
        do {
            if(inp_validation_count > 4) {
                System.out.println("\nYou crushed the app!!");
                System.exit(0);
            }
            
            System.out.print("\nEnter options: (1)View Transactions (2)Deposit (3)Withdraw (4)Back to Accounts: ");
            inp = keyboard.nextLine();
            
            switch(inp) {
                case"1" : viewTransactions(account, user);break;
                case"2" : Deposit(account, user);break;
                case"3" : Withdraw(account, user);break;
                case"4" : show(user);
            }
           inp_validation_count++;
        } while (!inp.equals("1") || !inp.equals("2") || !inp.equals("3") || !inp.equals("4"));
    
    }
    
    public static void viewTransactions(AccountOperations account, User user) {
        
        
        String trns_res;
        double amount;
        Vector<Transaction> transactions = new Vector();
        Transaction trns = new Transaction();
        Currency currency = new Currency();
        
        
        trns_res = trns.getTransactions(account.getId(), account.getCurrency_id());
        transactions = trns.getTrns();
        
        
        
        if(trns_res.equals("Server Error!!")){
            System.out.println(trns_res);
            System.exit(0);
        } else if (trns_res.equals("Transactions not found!!")){
            System.out.println(trns_res);
            AccountDetails(account, user);
        } else {
            System.out.println("Created At/     Currency/     Description/         Type/        Rate/      Amount");
            for(Transaction trn : transactions) {
                currency.getCurrency(trn.getCurrency_id());
                if(account.getName().equals("THB ACCOUNT") && trn.getTransaction_type().equals("Exchange Buy")){
                    amount = trn.getAmount() * currency.getBuy_rate();
                } else if (account.getName().equals("THB ACCOUNT") && trn.getTransaction_type().equals("Exchange Sell")){
                    amount = trn.getAmount() * currency.getSell_rate();
                } else {
                    amount = trn.getAmount();
                }
                System.out.println(trn.getCreated_at().toString().substring(0,10)+ "           " + currency.getName() + "       " + trn.getTransaction_des() + "        " +trn.getTransaction_type() +"      " +trn.getRate() + "            "+ amount);
            }
            System.out.print("Enter any key to go back: ");
            keyboard.nextLine();
            show(user);
        }
}
    
    public static void Deposit(AccountOperations account, User user) {
        
        boolean inp_type_check = true;
        int inp_validation_count = 0;
        String deposit_res;
        String inp;
        
        DepositTransaction deposit_trn = new DepositTransaction();
        
        System.out.println("\n=====Deposit======");
        do {
            if(inp_validation_count > 4){
                System.out.println("\nYou crashed the app!!");
                System.exit(0);
            }
            System.out.print("Enter deposit amount: ");
            
            try {
                inp = keyboard.nextLine();
                deposit_res = deposit_trn.createTransaction(user.getId(), account.getId(),Double.valueOf(inp));
                if(deposit_res.equals("Server Error!!")){
                    System.out.println(deposit_res);
                    System.exit(0);
                } else {
                    System.out.println(deposit_res);
                    if(deposit_res.equals("Deposit Transaction fails!!")) {
                        System.out.println("Try Again!!");
                    }
                    show(user);
                }
                
            }catch(NumberFormatException ex) {
                System.out.println("Invalid Input!!");
                inp_type_check = false;
                inp_validation_count++;
            }
        } while (!inp_type_check);
    }
    
    public static void Withdraw(AccountOperations account, User user) {
        
        boolean inp_type_check = true;
        int inp_validation_count = 0;
        String withdraw_res;
        String inp;
        
        WithdrawalTransaction withdraw_trn = new WithdrawalTransaction();
        
        System.out.println("\n=====Withdraw======");
        do {
            if(inp_validation_count > 4){
                System.out.println("\nYou crashed the app!!");
                System.exit(0);
            }
            System.out.print("Enter withdraw amount: ");
            
            try {
                inp = keyboard.nextLine();
                withdraw_res = withdraw_trn.createTransaction(user.getId(), account.getId(), Double.valueOf(inp));
                if(withdraw_res.equals("Server Error!!")){
                    System.out.println(withdraw_res);
                    System.exit(0);
                } else {
                    System.out.println(withdraw_res);
                    if(withdraw_res.equals("Withdraw Transaction fails!!")) {
                        System.out.println("Try Again!!");
                    }
                    show(user);
                }
            }catch(NumberFormatException ex) {
                System.out.println("Invalid Input!!");
                inp_type_check = false;
                inp_validation_count++;
            }
        }while (!inp_type_check);
    }
}
