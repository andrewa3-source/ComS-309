package org.springframework.samples.petclinic.owner;


import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

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
    private int health;

    @Column(name = "damage")
    @NotFound(action = NotFoundAction.IGNORE)
    private int damage;

    public Brawlers(){
    }
    public Brawlers(Integer id, String brawlerName, int health, int damage){

    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
