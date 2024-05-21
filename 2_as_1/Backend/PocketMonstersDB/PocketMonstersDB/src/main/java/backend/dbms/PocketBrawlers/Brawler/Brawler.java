package backend.dbms.PocketBrawlers.Brawler;

import backend.dbms.PocketBrawlers.Account.Account;
import backend.dbms.PocketBrawlers.CPU.CPUBrawler;
import backend.dbms.PocketBrawlers.Brawler.Attributes.Attributes;
import backend.dbms.PocketBrawlers.Brawler.User_Brawlers.User_Brawlers;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Andrew Ahrenkiel
 */

@Entity
@Transactional
@Table(name="brawler")
public class Brawler extends Attributes {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id")

    private int id;


    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="name")
    private String name;


    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="health")
    private int health;


    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="damage")
    private int damage;


    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "alive")
    private  boolean alive;


    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "attribute_id")
    private int attribute_id;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "level")
    private int level;


    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pos")
    private int pos;

    public enum attributeTime {BATTLE, FAINT, BUY, SELL, HURT};
    
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "a_time")
    private attributeTime a_time;

    @Column(name = "picture_url")
    private String pircture_url;

    @OneToMany(mappedBy = "brawler")
            @JsonIgnore
    List<User_Brawlers> user_brawlers;


    public Brawler(){

    }

    /**
     * Brawler Constructor
     * @param name brawler name
     * @param health brawler health
     * @param damage brawler damage
     * @param attribute_id unique attribute id
     * @param level brawler level
     * @param pos line up position between 0 and 4
     * @param a attribute time enum, BATTLE, SELL, or BUY
     * @param pircture_url direct url for picture
     */
    public Brawler(String name, int health, int damage, int attribute_id, int level, int pos, attributeTime a, String pircture_url){
        this.health = health;
        this.damage = damage;
        this.alive = true;
        this.name = name;
        this.attribute_id = attribute_id;
        this.a_time = a;
        this.level = level;
        this.pos = pos;
        this.pircture_url = pircture_url;
    }

    /**
     * Brawler Constructor using a brawler onject
     * @param brawler brawler to be constructed
     */
    public Brawler(Brawler brawler){
        this.health = brawler.health;
        this.damage = brawler.damage;
        this.alive = brawler.alive;
        this.name = brawler.name;
        this.attribute_id = brawler.attribute_id;
        this.a_time = brawler.a_time;
        this.level = brawler.level;
        this.pos = brawler.pos;
        this.pircture_url = brawler.pircture_url;
    }

    /**
     * Add a brawler to the DB
     * @param id brawler id
     * @param health brawler health
     * @param damage brawler damage
     * @param alive is the brawler alive or faint
     * @param name brawlers name
     * @param attribute_id brawlers attribute id
     * @param pos brawlers line up position
     * @param a brawlers attribute time enum, BATTLE, SELL, BUY,
     * @return The added Brawler
     */
    public Brawler addBrawler(int id, int health, int damage, boolean alive, String name, int attribute_id, int pos, attributeTime a){
        Brawler b = new Brawler();
        b.setId(id);
        b.setHealth(health);
        b.setDamage(damage);
        b.setAlive(alive);
        b.setName(name);
        b.setAttributeId(attribute_id);
        b.setPos(pos);
        b.setA_time(a);
        return b;
    }

    /**
     * Get the position of this brawlers
     * @return int of line-up position
     */
    public int getPos(){
        return this.pos;
    }

    /**
     * Set the position of this brawler
     * @param pos int brawler position
     */
    public void setPos(int pos){
        this.pos = pos;
    }

    /**
     * Get this brawlers id
     * @return int brawler id
     */
    public int getId(){
        return this.id;
    }

    /**
     * set brawler id (warning, auto generated id's are used by default)
     * @param id int brawler id
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     *  Get this brawlers name
     * @return String brawler name
     */
    public String getName(){
        return this.name;
    }

    /**
     * Set this brawlers name
     * @param name String brawler name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Get this brawlers health points
     * @return int brawler hp
     */
    public int getHealth(){
        return this.health;
    }

    /**
     * Set this brawlers health points
     * @param health int brawler hp
     */
    public void setHealth(int health){
        this.health = health;
    }

    /**
     * Get this brawler damage points
     * @return int brawler dmg
     */
    public int getDamage(){
        return this.damage;
    }

    /**
     * Set this brawlers damage points
     * @param damage int brawler dmg
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * Get this brawlers life status
     * @return bool brawler life status
     */
    public boolean getAlive(){
        return this.alive;
    }

    /**
     * Set this brawlers life status
     * @param t bool brawler life status
     */
    public void setAlive(boolean t){
        this.alive = t;
    }

    /**
     * Get this brawlers attribute time
     * @return This brawlers Brawler.attributeTime
     */
    public attributeTime getA_time(){
        return this.a_time;
    }

    /**
     * Set this brawlers attribute time
     * @param a brawlers Brawler.attributeTime
     */
    public void setA_time(attributeTime a){
        this.a_time = a;
    }

    /**
     * Get this brawlers attribute ID
     * @return int brawler a_id
     */
    public int getAttributeId(){
        return this.attribute_id;
    }

    /**
     * Set this brawlers attribute ID
     * @param a int brawler a_id
     */
    public void setAttributeId(int a){
        this.attribute_id = a;
    }

    /**
     * Get this brawlers level
     * @return int brawler level
     */
    public int getLevel(){return this.level;}

    /**
     * Set this brawlers level
     * @param level int brawler level
     */
    public void setLevel(int level){this.level = level;}

    /**
     * Get this brawler's direct picture url
     * @return String picture url
     */
    public String getPircture_url(){return this.pircture_url;}

    /**
     * Set this brawler's direct picture url
     * @param picture_url String picture url
     */
    public void setPircture_url(String picture_url){this.pircture_url = picture_url;}

    /**
     * Get the list of stored user brawler team
     * @return List of the User_Brawlers of the current team
     */
    public List<User_Brawlers> getAccounts(){
        return this.user_brawlers;
    }

    /**
     * this function takes in two brawlers and fights until one of them is rendered knocked out
     * @param b1 - user 1 brawler
     * @param b2 - user 2 brawler
     */
    public void clashBrawlers(@NotNull Brawler b1, @NotNull Brawler b2){

        int damageb1 = b1.getDamage();
        int damageb2 = b2.getDamage();
        while(b1.getAlive() == true && b2.getAlive() == true) {
            b1.setHealth(this.health - damageb2);
            b2.setHealth(this.health - damageb1);
            if (b1.getHealth() <= 0) {
                b1.setAlive(false);
            }
            if (b2.getHealth() <= 0) {
                b2.setAlive(false);
            }

        }
        return;
    }


    /**
     * Used to move all brawlers up a square in battle when front brawler is knocked out
     * @param b - users brawler line up
     */
    public void moveBrawlersInFight(Brawler[] b){
        int i;
        for (i = 0; i < 4; ++i){
            b[i] = b[i + 1];
        }
        b[4] = null;
    }

    /**
     * Get the needed information for this brawler to turn into a JSON
     * @return JSONObject of the brawlers information including name, url, damage, alive, health, level
     */
    public JSONObject toJSONObject(){
        JSONObject temp = new JSONObject();

        temp.put("name", this.name);
        temp.put("url", this.pircture_url);
        temp.put("damage", this.damage);
        temp.put("alive", this.alive);
        temp.put("health", this.health);
        temp.put("level", this.level);

        return temp;
    }
}
