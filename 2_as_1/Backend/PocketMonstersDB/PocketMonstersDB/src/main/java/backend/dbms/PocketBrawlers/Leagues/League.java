package backend.dbms.PocketBrawlers.Leagues;

import backend.dbms.PocketBrawlers.Account.Account;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;

import javax.persistence.*;
import javax.websocket.Session;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * League schema for storing league info.
 *
 * @author Reid Coates
 */
@Entity
@Table(name="leagues")
public class League {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "admin_id")
    private int admin_id;

    @Column(name = "picture_url")
    private String picture_url;

    @Column(name = "user_num")
    private int user_num;

    /**
     * Stores the account and is mapped to by Accounts' many to one relationship
     */
    @OneToMany(mappedBy = "league", cascade = CascadeType.ALL)
    private List<Account> accounts = new ArrayList<Account>();

    public League(){}

    public League(String name, int admin_id){
        this.name = name;
        this.admin_id = admin_id;
        this.user_num = 1;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getUser_num() {
        return user_num;
    }

    public void addAccount(Account account) {
        accounts.add(account);
        account.setLeague(this);
    }

    public void removeAccount(Account account) {
        accounts.remove(account);
        account.setLeague(null);
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    /**
     * Turn the accounts into a string array of usernames
     *
     * @return String array of member usernames
     */
    public String getAccountsAsString(){
        String accountsString = "[";

        for (Account account: accounts) {
            accountsString.concat("\"" + account.getUsername() + "\",");
        }

        if (accounts.size() > 0){
            StringUtils.substring(accountsString, 0, accountsString.length() - 1);
        }

        accountsString.concat("]");

        return accountsString;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    /**
     * Turn selected league into a properly formatted JSONObject
     *
     * @return JSONObject of league
     */
    public JSONObject toJSONObject(){
        JSONObject temp = new JSONObject();
        temp.put("name", this.name);
        temp.put("url", this.picture_url);
        temp.put("accounts", this.accounts);
        temp.put("user_num", this.user_num);
        temp.put("admin_id", this.admin_id);
        temp.put("id", this.id);
        temp.put("league_members", this.accounts);

        return temp;
    }




}