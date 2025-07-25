package Service;


import Model.Account;
import DAO.AccountDAO;


public class AccountService {

    private AccountDAO accountDAO; //declaring an instance of AccountDAO
    
    /**
     * no-args constructor for creating a new AccountService with a new AccountDAO.
     */
    public AccountService(){
        accountDAO = new AccountDAO();
    }

    /**
     * Constructor for a AccountService when a AccountDAO is provided.
     * @param accountDAO
     */
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }
    
    /**
     * addAccount(Account account)
     * The registration will be successful if and only if the username is not blank, 
     * the password is at least 4 characters long, and an Account with that username does not already exist. 
     *
     * @param account an account object.
     * @return The persisted account if the persistence is successful.
     */
    public Account addAccount(Account account) {
        if(accountDAO.getAccountByUsername(account.getUsername())!=null){
            return null;//if it exists then return null because you do not want to create the same account twice
        }else if(account.getUsername()== ""){
            return null;
        }else if(account.getPassword().length()<4){
            return null;
        }
        return accountDAO.insertAccount(account);
    }


    /** getAccount(account)
     * 
     * Username and password already should be on the database
     * @param account an account object.
     * @return The retrieved account if it exists.
     */
    public Account getAccount(Account account) {
        if(accountDAO.getAccountByUsernamePassword(account.getUsername(),account.getPassword())==null){
            return null; //Username and password not on the database return null
        }
        return accountDAO.getAccountByUsernamePassword(account.getUsername(),account.getPassword());
    }

    
}
