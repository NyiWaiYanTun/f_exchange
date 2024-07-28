package f_exchange;
import java.sql.*;

public class FXCAccount extends AccountOperations {

    private String account_currency;
    private Currency currency;
    
   
    @Override
    public String createAccount(int usr_id, int currency_id) {
        String account_existed;
        Connection con = DBConnection.connectDB();
        if(con != null) {
            try{
                getCurrencyName(currency_id);
                account_existed = getAccountForExchange(usr_id, currency_id, con);
                
                if(!account_existed.equals("FXC Account found successfully!!")) {
                    PreparedStatement insert_query = con.prepareStatement("insert into account(user_id, name, currency_id) values (?, ?, ?)");
                    insert_query.setInt(1, usr_id);
                    insert_query.setString(2, this.getAccount_currency() + " ACCOUNT");
                    insert_query.setInt(3, currency_id);
                    insert_query.executeUpdate();
                    return this.getAccount_currency() + " Account created successfully!!";
                } else {
                    return this.getAccount_currency() + " Account already exists!!";
                }
            }catch(SQLException ex) {
                //System.out.println(ex.getMessage());
                return DBConnectionStatus(false);
            }
        } 
        else {
           return DBConnectionStatus(false); 
        }
    }
    
    
    //For Exchange
    public String getAccountForExchange(int usr_id, int currency_id, Connection con) {
        
            try{
                getCurrencyName(currency_id);
                PreparedStatement select_query = con.prepareStatement("select id from account where user_id = ? and currency_id = ?");
                select_query.setInt(1, usr_id);
                select_query.setInt(2, currency_id);
                ResultSet acc_id = select_query.executeQuery();
                if(acc_id.next()){
                    this.setId(acc_id.getInt("id"));
                    //this.setName(acc_id.getString("name"));
                    return "FXC Account found successfully!!";
                } else {
                    return "FXC Account not found!!";
                }                
            }catch(SQLException ex) {
                //System.out.println(ex.getMessage());
                return DBConnectionStatus(false);
            }
    }
    
    
    private void getCurrencyName(int currency_id){
        currency = new Currency();
        String res = currency.getCurrency(currency_id);
        if (res.equals("Currency found successfully!!")) {
            this.setAccount_currency(currency.getName());
        } else {
            System.out.println(res);
        }
    }
    
     /**
     * @return the currency
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
     * @param currency the currency to set
     */
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
    
     /**
     * @return the account_currency
     */
    public String getAccount_currency() {
        return account_currency;
    }

    /**
     * @param account_currency the account_currency to set
     */
    public void setAccount_currency(String account_currency) {
        this.account_currency = account_currency;
    }

   
}
