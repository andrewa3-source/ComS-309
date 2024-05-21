package backend.dbms.PocketBrawlers.CPU;


import backend.dbms.PocketBrawlers.Account.Account;
import backend.dbms.PocketBrawlers.CPU.CPU;
import backend.dbms.PocketBrawlers.Brawler.Brawler;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.json.simple.JSONObject;

import javax.persistence.*;

/**
 * @author Reid Coates and Andrew Ahrenkiel
 */
@Entity
public class CPUBrawler {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    @ManyToOne
    @JoinColumn(name = "cpu_id")
    @JsonIgnore
    CPU brawlerCPU;

    String name;

    String url;
    int brawler_dmg;

    int brawler_hp;

    int brawler_pos;

    int b_id;

    int a_id;

    public enum attributeTime {BATTLE, FAINT, BUY, SELL, HURT};

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "a_time")
    private Brawler.attributeTime a_time;

    public long getId(){return this.id;}
    public void setId(int id){this.id = id;}

    public int getBrawler_dmg(){return this.brawler_dmg;}
    public void setBrawler_dmg(int dmg){this.brawler_dmg = dmg;}

    public int getBrawler_hp(){return this.brawler_hp;}
    public void setBrawler_hp(int hp){this.brawler_hp = hp;}

    public int getBrawler_pos(){return this.brawler_pos;}
    public void setBrawler_pos(int pos){this.brawler_pos = pos;}

    public CPU getAccount(){return this.brawlerCPU;}
    public void setAccount(CPU cpu){this.brawlerCPU = cpu;}


    public CPU getBrawlerCpu() {
        return brawlerCPU;
    }

    public void setBrawlerCPU(CPU brawlerCPU) {
        this.brawlerCPU = brawlerCPU;
    }

    public void setCpu(CPU cpu) {
        this.brawlerCPU = cpu;
    }

    public int getB_id(){return this.b_id;}

    public Brawler.attributeTime getA_time(){
        return this.a_time;
    }

    public int getAttribute_id(){
        return this.a_id;
    }

    public String getUrl(){
        return this.url;
    }

    public CPUBrawler(){};

    public CPUBrawler(int b_id, CPU cpu, int brawler_dmg, int brawler_hp, int brawler_pos, String url, String name, Brawler.attributeTime a_time, int a_id){
        this.b_id = b_id;
        this.brawlerCPU = cpu;
        this.brawler_dmg = brawler_dmg;
        this.brawler_hp = brawler_hp;
        this.brawler_pos = brawler_pos;
        this.url = url;
        this.name = name;
        this.a_time = a_time;
        this.a_id = a_id;
    }


    public JSONObject toJSONObject() {
        JSONObject temp = new JSONObject();
        temp.put("id", this.b_id);
        temp.put("health", this.brawler_hp);
        temp.put("damage", this.brawler_dmg);
        temp.put("position", this.brawler_pos);
        temp.put("url", this.url);
        temp.put("name", this.name);
        temp.put("a_id", this.a_id);
        temp.put("a_time", this.a_time);
        return temp;
    }
}