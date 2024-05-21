package backend.dbms.PocketBrawlers.Brawler.User_Brawlers;

import backend.dbms.PocketBrawlers.Account.Account;
import backend.dbms.PocketBrawlers.Brawler.Brawler;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.json.simple.JSONObject;
import springfox.documentation.spring.web.json.Json;

import javax.persistence.*;

@Entity
public class User_Brawlers {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    @ManyToOne
    @JoinColumn(name = "brawler_id")
    @JsonIgnore
    Brawler brawler;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonIgnore
    Account account;

    int brawler_dmg;

    int brawler_hp;

    int brawler_pos;


    public long getId(){return this.id;}
    public void setId(int id){this.id = id;}

    public int getBrawler_dmg(){return this.brawler_dmg;}
    public void setBrawler_dmg(int dmg){this.brawler_dmg = dmg;}

    public int getBrawler_hp(){return this.brawler_hp;}
    public void setBrawler_hp(int hp){this.brawler_hp = hp;}

    public int getBrawler_pos(){return this.brawler_pos;}
    public void setBrawler_pos(int pos){this.brawler_pos = pos;}

    public Brawler getBrawler(){return brawler;}
    public void setBrawler(Brawler brawler){this.brawler = brawler;}

    public Account getAccount(){return this.account;}
    public void setAccount(Account account){this.account = account;}

    public User_Brawlers(){}

    public User_Brawlers(Account account, Brawler brawler, int brawler_dmg, int brawler_hp, int brawler_pos){
        this.brawler = brawler;
        this.account = account;
        this.brawler_dmg = brawler_dmg;
        this.brawler_hp = brawler_hp;
        this.brawler_pos = brawler_pos;
    }

    public JSONObject toJSONObject(){
        JSONObject temp = new JSONObject();

        temp.put("name", this.brawler.getName());
        temp.put("url", this.brawler.getPircture_url());
        temp.put("damage", this.getBrawler_dmg());
        temp.put("id", this.brawler.getId());
        temp.put("health", this.getBrawler_hp());
        temp.put("level", this.brawler.getLevel());
        temp.put("a_time", this.brawler.getA_time());
        temp.put("alive", true);
        temp.put("a_id", this.brawler.getAttributeId());

        return temp;
    }

}
