package f_exchange;
public interface TransactionCreateable {
   
    String createTransaction(int usr_id, int currency_or_account_id, double amount);
}
