package f_exchange;
import java.sql.*;
public class User {

       
    private int id;
    private String username;
    private String password;
    private boolean isAdmin;
    private Account generated_account;
    
  
    
    public String DBConnectionStatus(boolean status) {
        if(!status) {
            return "Server Error!!";
        } else {
            return "Connection Successful!!";
        }  
    }
    
     public String encryptOrDecrypt(String password, boolean encypOrDecyp) {
        StringBuilder res = new StringBuilder();
        int key = 1;
        char[] cs = password.toCharArray();
        
        if(encypOrDecyp){
            for(char c : cs) {
            c += key;
            res.append(c);
        }
            return res.toString();
        }else {
            for(char c : cs) {
            c -= key;
            res.append(c);
        }
            return res.toString();
        }  
    }
    
    
    public String login(String username, String password) {
        Connection con = DBConnection.connectDB();
       
        if(con != null) {
            try{
                ResultSet login_user = getUserWithUsername(username, con);
                if(login_user.next()) {
                    String decrypted_password = encryptOrDecrypt(login_user.getString("password"), false);
          
                    if(!decrypted_password.equals(password)) {
                        return "Wrong Password!!";
                    }
                    
                    this.setId(login_user.getInt("id"));
                    this.setUsername(login_user.getString("username"));
                    this.setIsAdmin(login_user.getBoolean("isAdmin"));
                    return "Login Successful!!";
                } else {
                    return "Username not found!!";
                }
            }catch(SQLException ex) {
                System.out.println(ex.getMessage());
                return DBConnectionStatus(false);
            }
        } else {
            return DBConnectionStatus(false);
        }
    }
    
     public String register(String username, String password) {
        Connection con = DBConnection.connectDB();
        
        if(username.equals("")|| password.equals("")) {
            return "Username and password cannot be empty!!";
        }
        
        if(con != null) {
            try{
                ResultSet user = getUserWithUsername(username, con);
                if(user.next()) {
                    return "Username is not available!!";
                } else {
                   String encrypted_password = encryptOrDecrypt(password, true);
                   CreateUser(username, encrypted_password, con);
                   ResultSet created_user = getUserWithUsername(username, con);
                    if(created_user.next()) {
                        this.setId(created_user.getInt("id"));
                        this.setUsername(created_user.getString("username"));
                        this.setIsAdmin(created_user.getBoolean("isAdmin"));
                        CreateTHBAccount();
                    } else {
                        //System.out.println("Username not found!!");
                    }                 
                   return "Registeration Successful!!";
                }
            }catch(SQLException ex) {
                System.out.println("" + ex.getMessage());
                return DBConnectionStatus(false);
            }
        } else {
            return DBConnectionStatus(false);
        }
    }
        
     
    public ResultSet getUserWithUsername (String username, Connection con)throws SQLException {
        PreparedStatement select_query = con.prepareStatement("select * from users where username = ?");
        select_query.setString(1, username);
        return select_query.executeQuery(); 
    }
    
     public String getUserWithID (int id) {
        Connection con = DBConnection.connectDB();
        if(con != null) {
            try{
                PreparedStatement select_query = con.prepareStatement("select * from users where id = ?");
                select_query.setInt(1, id);
                ResultSet user = select_query.executeQuery();
                if(user.next()) {
                    this.setId(user.getInt("id"));
                    this.setUsername(user.getString("username"));
                    this.setIsAdmin(user.getBoolean("isAdmin"));
                    
                    return "User found successfully!!";
                } else {
                    return "User not found!!";
                }
           } catch(SQLException ex) {
                System.out.println(ex.getMessage());
                return DBConnectionStatus(false);
            }
        } else {
            return DBConnectionStatus(false);
        }
        
    }
     
    public String getAdmin() {
        Connection con = DBConnection.connectDB();
        try{
            PreparedStatement select_query = con.prepareStatement("select * from users where isAdmin = true");
            ResultSet admin = select_query.executeQuery();
            if(admin.next()) {
                this.setId(admin.getInt("id"));
                this.setUsername(admin.getString("username"));
                this.setIsAdmin(admin.getBoolean("isAdmin"));
                
                return "Admin found successfully!!";
            }else {
                return "Admin not found!!";
            }
            
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
            return DBConnectionStatus(false);
        }
    }    
    public void CreateUser (String username, String password, Connection con)throws SQLException {
        PreparedStatement insert_query = con.prepareStatement("insert into users(username, password, isAdmin) values (?, ?, ?)");
        insert_query.setString(1, username);
        insert_query.setString(2, password);
        insert_query.setBoolean(3, false);
        insert_query.executeUpdate();
    }
     
     
     public void CreateTHBAccount(){
         generated_account = new THBAccount();
         generated_account.createAccount(this.getId(), 5);
     }
     
     
    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

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
     * @return the isAdmin
     */
    public boolean isIsAdmin() {
        return isAdmin;
    }

    /**
     * @param isAdmin the isAdmin to set
     */
    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

      /**
     * @return the generated_account
     */
    public Account getGenerated_account() {
        return generated_account;
    }

    /**
     * @param generated_account the generated_account to set
     */
    public void setGenerated_account(Account generated_account) {
        this.generated_account = generated_account;
    }
 
}
