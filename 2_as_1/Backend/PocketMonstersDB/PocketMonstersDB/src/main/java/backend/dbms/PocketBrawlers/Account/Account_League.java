package backend.dbms.PocketBrawlers.Account;

import javax.persistence.*;

@Entity
@Table(name="Account_League")
public class Account_League {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int id;

    @Column(name = "account_id")
    int account_id;

    @Column(name = "username")
    String username;

    public Account_League(){}

    public Account_League(int id, String username){
        this.account_id = id;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public int getAccount_id() {
        return account_id;
    }

    public String getUsername() {
        return username;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
