package backend.dbms.PocketBrawlers.CPU;

import backend.dbms.PocketBrawlers.Account.Account;
import org.json.simple.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import springfox.documentation.spring.web.json.Json;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Reid Coates
 */
@Entity
@Table(name="cpu")
public class CPU {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private int id;

    @Column(name="url")
    private String url;

    @Column(name="level")
    private int level;

//    @OneToMany(mappedBy = "brawlerCPU", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonIgnore
//    List<CPUBrawler> cpuBrawlers;

    @OneToOne(mappedBy = "cpu", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    Account accountCPU;

    @JsonIgnore
    @OneToMany(mappedBy = "brawlerCPU")
    List<CPUBrawler> cpuBrawlers;

    public CPU(String url, int level){
        this.url = url;
        this.level = level;
        cpuBrawlers = new ArrayList<CPUBrawler>();
    }

    public CPU(){
        cpuBrawlers = new ArrayList<CPUBrawler>();
    };

    public int getLevel() {return level;}

    public int getId() {return id;}

    public List<CPUBrawler> getCPUBrawlers() {
        return cpuBrawlers;
    }

    public void modifyBrawler(int hp, int dmg, int effect, int index){
        // change a brawler in the lineup
    }

    public Account getAccountCPU() {
        return accountCPU;
    }

    public String getUrl() {
        return url;
    }

    public List<CPUBrawler> getCpuBrawlers() {
        return cpuBrawlers;
    }

    public void setAccount(Account account) {
        this.accountCPU = account;
    }

    public void setCpuBrawlers(List<CPUBrawler> cpuBrawlers) {
        this.cpuBrawlers = cpuBrawlers;
    }

    public void setAccountCPU(Account accountCPU) {
        this.accountCPU = accountCPU;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public JSONObject toJSONObject() {
        JSONObject temp = new JSONObject();


//        temp.put("account", accountCPU);
//        temp.put("url", url);
//        temp.put("level", level);
        for (int i = 0; i < cpuBrawlers.size(); i++){
            temp.put("id", cpuBrawlers.get(i).getB_id());
            temp.put("health", cpuBrawlers.get(i).getBrawler_hp());
            temp.put("damage", cpuBrawlers.get(i).getBrawler_dmg());
            temp.put("alive", true);
            temp.put("a_time", cpuBrawlers.get(i).getA_time());
            temp.put("a_id", cpuBrawlers.get(i).getAttribute_id());
            temp.put("url", cpuBrawlers.get(i).getUrl());
        }

        return temp;
    }

    public List<CPUBrawler> getBrawlers() {
        return this.cpuBrawlers;
    }

    public void addBrawler(CPUBrawler brawler){
        cpuBrawlers.add(brawler);
        brawler.setBrawlerCPU(this);
    }
}
