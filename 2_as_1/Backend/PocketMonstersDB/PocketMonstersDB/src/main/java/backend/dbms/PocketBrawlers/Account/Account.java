package backend.dbms.PocketBrawlers.Account;

import backend.dbms.PocketBrawlers.Brawler.User_Brawlers.User_Brawlers;
import backend.dbms.PocketBrawlers.Brawler.Brawler;
import backend.dbms.PocketBrawlers.CPU.CPU;
import backend.dbms.PocketBrawlers.Brawler.User_Brawlers.User_Brawlers;
import backend.dbms.PocketBrawlers.CPU.CPU;
import com.fasterxml.jackson.annotation.JsonIgnore;
import backend.dbms.PocketBrawlers.Leagues.League;
import org.json.simple.JSONObject;
import org.springframework.data.util.Lazy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Schema for user Account
 *
 * @author Reid Coates and Andrew Ahrenkiel
 */
@Entity
@Table(name="account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private int id;


    @Column(name="username")
    private String username;

    @Column(name="type")
    private int type;

    @Column(name="picture_url")
    private String picture_url;
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="doubloons")
    private int doubloons;

    @Column(name="password")
    private String password;

    /**
     * Relationship mapping for accounts and leagues
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "league")
    @JsonIgnore
    private League league;

    @Column(name="wins")
    private int wins;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="loss")
    private int loss;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="turn")
    private int turn;

    /**
     * Relationship mapping for accounts and brawlers to user_brawlers
     */
    @OneToMany(mappedBy = "account")
            @JsonIgnore
    List<User_Brawlers> user_brawlers;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cpu_id")
    @JsonIgnore
    private CPU cpu;


    public Account(){}

    /**
     * Constructor
     * @param username account usernam
     * @param password account pw
     * @param wins number of wins
     * @param loss number of losses
     * @param turn turn this player is on
     * @param type type of account
     * @param doubloons amount of money for shop
     * @param picture_url user pfp direct link
     */
    public Account(String username, String password, int wins, int loss, int turn, int type, int doubloons, String picture_url){
        this.username = username;
        this.password = password;
        this.wins = wins;
        this.loss = loss;
        this.turn = turn;
        this.type = type;
        this.doubloons = doubloons;
        this.picture_url = picture_url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDoubloons() {
        return doubloons;
    }

    public void setDoubloons(int doubloons) {
        this.doubloons = doubloons;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String n) {
        username = n;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String p) {
        password = p;
    }

    public int getWins(){return this.wins;}

    public void setWins(int wins){this.wins = wins;}

    public int getLoss(){return this.loss;}

    public void setLoss(int loss){this.loss = loss;}

    public int getTurn(){return this.turn;}

    public void setTurn(int turn){this.turn = turn;}

    public int getId(){return this.id;}

    public void setId(int id){this.id = id;}

    public void setBrawlers(User_Brawlers b1){
        user_brawlers.add(b1);
    }

    public List<User_Brawlers> getBrawlers(){
        return this.user_brawlers;
    }

    public CPU getCpu() {
        return cpu;
    }

    public List<User_Brawlers> getUser_brawlers() {
        return user_brawlers;
    }

    public void setUser_brawlers(List<User_Brawlers> user_brawlers) {
        this.user_brawlers = user_brawlers;
    }

    public League getLeague() {
        return league;
    }

    public int getLeagueId() {
        if (league == null){
            return 0;
        } else {
            return this.league.getId();
        }
    }

    /**
     * Turn the object instance into a JSON file
     *
     * @return JSONObject of the Account instance
     */
    public JSONObject toJSONObject() {
        JSONObject temp = new JSONObject();
        temp.put("username", this.username);
        temp.put("password", this.password);
        temp.put("type", Integer.toString(this.type));
        temp.put("doubloons", this.doubloons);
        temp.put("picture_url", this.picture_url);
        temp.put("loss", this.loss);
        temp.put("turn", this.turn);
        temp.put("id", this.id);
        temp.put("wins", this.wins);
        temp.put("league_id", getLeagueId());
        return temp;
    }

    public void setCpu(CPU cpu) {
        this.cpu = cpu;
    }

    public void setLeague(League league) {
        this.league = league;
    }
}
