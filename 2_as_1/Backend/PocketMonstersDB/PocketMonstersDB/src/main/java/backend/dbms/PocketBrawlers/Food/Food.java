package backend.dbms.PocketBrawlers.Food;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Andrew Ahrenkiel
 */
@Entity
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int id;

    String picture;

    int level;

    /**
     * Default Constructor
     */
    public Food(){}

    /**
     * Food Constructor using Picture URL
     * @param picture String of the direct picture url
     */
    public Food(String picture, int level){
        this.picture = picture;
        this.level = level;
    }


    /**
     * Get the picture url for this food
     * @return String picture url
     */
    public String getPicture(){return this.picture;}

    /**
     * Set the picture url for this food
     * @param picture String picture url
     */
    public void setPicture(String picture){this.picture = picture;}

    /**
     * Get the id of this food
     * @return int food id
     */
    public int getId(){return this.id;}

    /**
     * Set the id for this food (warning auto generated)
     * @param id int food id
     */
    public void setId(int id){this.id = id;}

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
