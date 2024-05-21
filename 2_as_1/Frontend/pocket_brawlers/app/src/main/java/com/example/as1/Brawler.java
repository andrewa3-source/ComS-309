package com.example.as1;

import androidx.annotation.NonNull;

/**
 * The Brawler class represents a character in a game with attributes such as attack, health, level,
 * name, and a unique ID.
 * @author Drew Kinneer
 */
public class Brawler {

    private static int uniqueIdCount = 1;
    private final int uniqueId;
    private int attack;
    private int health;
    private int id;
    private int level;
    private String name;
    private BrawlerEffect brawlerEffect;
    private String imageUrl;

    /**
     Creates a new instance of the Brawler class with the specified attack, health, id, level, name, brawler effect, and image URL.
     @param attack the attack power of the brawler.
     @param health the health points of the brawler.
     @param id the unique identifier of the brawler.
     @param level the level of the brawler.
     @param name the name of the brawler.
     @param brawlerEffect the brawler effect of the brawler.
     @param imageUrl the URL of the image representing the brawler.
     */
    Brawler(int attack, int health, int id, int level, String name, BrawlerEffect brawlerEffect, String imageUrl){
        this.attack = attack;
        this.health = health;
        this.id = id;
        this.level = level;
        this.name = name;
        this.brawlerEffect = brawlerEffect;
        this.imageUrl = imageUrl;

        this.uniqueId = uniqueIdCount;
        uniqueIdCount++;

        this.brawlerEffect.setUniqueBrawlerId(this.uniqueId);
    }

    /**
     * The function returns the value of the "attack" variable.
     *
     * @return The method is returning an integer value which represents the attack power of an object.
     */
    public int getAttack() {
        return attack;
    }
    /**
     * This is a Java function that sets the value of the "attack" variable.
     *
     * @param attack attack is a variable of type int that represents the attack value of an object.
     * The method setAttack(int attack) sets the value of the attack variable to the value passed as a
     * parameter.
     */
    public void setAttack(int attack) {this.attack = attack;}

    /**
     * The function adds a given value to the "attack" attribute of an object.
     *
     * @param add The parameter "add" is an integer value that represents the amount by which the
     * "attack" attribute of an object is to be increased. The method "addAttack" takes this parameter
     * and adds it to the current value of the "attack" attribute of the object.
     */
    public void addAttack(int add){
        this.attack += add;
    }

    /**
     * This Java function subtracts a given value from the "attack" attribute of an object.
     *
     * @param sub sub is an integer parameter representing the amount of attack points to be subtracted
     * from the current attack points of an object.
     */
    public void subtractAttack(int sub){
        this.attack -= sub;
    }

    /**
     * The function returns the value of the "health" variable.
     *
     * @return The method is returning the value of the variable "health".
     */
    public int getHealth() {
        return health;
    }
    /**
     * This is a Java function that sets the value of a variable called "health".
     *
     * @param health The "health" parameter is an integer value that represents the health of an object
     * or entity in a program. The "setHealth" method is used to set the value of the "health" variable
     * to a new value passed as an argument to the method.
     */
    public void setHealth(int health) {this.health = health;}

    /**
     * The function adds a specified amount to the current health value.
     *
     * @param add The "add" parameter is an integer value that represents the amount of health to be
     * added to the current health of an object.
     */
    public void addHealth(int add){
        this.health += add;
    }

    /**
     * This function subtracts a given value from the health of an object and sets it to 0 if it goes
     * below 0.
     *
     * @param sub The parameter "sub" is an integer value representing the amount of health to subtract
     * from the current health of an object.
     */
    public void subtractHealth(int sub){
        this.health -= sub;
        if (this.health < 0){
            this.health = 0;
        }
    }

    /**
     * The function returns the value of the "id" variable.
     *
     * @return The method `getId()` is returning an integer value which represents the ID of an object.
     */
    public int getId() {
        return id;
    }

    /**
     * The function returns the value of the "level" variable.
     *
     * @return The method is returning the value of the variable "level".
     */
    public int getLevel() {
        return level;
    }
    /**
     * This is a Java function that sets the value of a variable called "level".
     *
     * @param level The "level" parameter is an integer value that represents the level of something.
     * The method "setLevel" sets the value of the "level" instance variable to the value passed as a
     * parameter.
     */
    public void setLevel(int level) {this.level = level;}

    /**
     * The function returns the name.
     *
     * @return The method `getName()` is returning the value of the `name` variable, which is a
     * `String`.
     */
    public String getName() {
        return name;
    }
    /**
     * This function sets the value of the "name" variable in the current object to the input parameter
     * "name".
     *
     * @param name The parameter "name" is a String type variable that represents the name of an object
     * or entity. The method "setName" takes a String parameter "name" and sets the value of the
     * instance variable "name" to the value passed as the parameter.
     */
    public void setName(String name){this.name = name;}

    /**
     * The function returns a BrawlerEffect object.
     *
     * @return The method is returning an object of type BrawlerEffect.
     */
    public BrawlerEffect getBrawlerEffect() {
        return brawlerEffect;
    }

    public void setBrawlerEffect(BrawlerEffect effect){ this.brawlerEffect = effect;}

    /**
     * The function returns the image URL as a string.
     *
     * @return The method `getImageUrl()` is returning a `String` value, which is the value of the
     * variable `imageUrl`.
     */
    public String getImageUrl() {return imageUrl;}
    /**
     * This function sets the image URL for an object.
     *
     * @param imageUrl The parameter "imageUrl" is a String variable that represents the URL of an
     * image. The method "setImageUrl" sets the value of the instance variable "imageUrl" to the value
     * passed as a parameter.
     */
    public void setImageUrl(String imageUrl) {this.imageUrl = imageUrl;}

    /**
     * The function returns the unique ID.
     *
     * @return The method is returning an integer value which represents the unique ID.
     */
    public int getUniqueId() {
        return uniqueId;
    }

    public void setId(int id){
        this.id = id;
    }

    @NonNull
    @Override
    public String toString() {
        return name + " Attack: " + getAttack() + " Health: " + getHealth();
    }
}
