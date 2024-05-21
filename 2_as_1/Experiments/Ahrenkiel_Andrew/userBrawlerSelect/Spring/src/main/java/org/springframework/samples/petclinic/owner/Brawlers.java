package org.springframework.samples.petclinic.owner;


import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.core.style.ToStringCreator;

import javax.persistence.*;

@Entity
@Table(name = "brawlers")
public class Brawlers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    private Integer id;

    @Column(name = "brawler_name")
    @NotFound(action = NotFoundAction.IGNORE)
    private String brawlerName;


    @Column(name = "health")
    @NotFound(action = NotFoundAction.IGNORE)
    private Integer health;

    @Column(name = "damage")
    @NotFound(action = NotFoundAction.IGNORE)
    private Integer damage;

    public Brawlers(){
    }
    public Brawlers(Integer id, String brawlerName, int health, int damage){
        this.id = id;
        this.brawlerName = brawlerName;
        this.health = health;
        this.damage = damage;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }



    public String getBrawlerName(){ return brawlerName; }
    public String getBrawlerStats(){
        return "Heath: " + health + "!  Damage: " + damage + "!";
    }

    public void setHealth(int health){
        this.health = health;
    }

    public String getHealth(){
        return "" +health;
    }

    public void setDamage(Integer damage) {
        this.damage = damage;
    }

    public Integer getDamage() {
        return damage;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)

                .append("id", this.getId())
                .append("brawler_name", this.getBrawlerName())
                .append("stats", this.getBrawlerStats()).toString();
    }
}


