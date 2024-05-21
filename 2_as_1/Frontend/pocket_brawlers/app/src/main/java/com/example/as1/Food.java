package com.example.as1;

/**
 * @author Drew Kinneer
 * The `Food` class represents a food item with an ID, photo URL, name, effect description, and an
 * effect that can be applied to a `Brawler` object.
 */
public class Food {
    private int id;
    private String photoUrl;
    private String name;
    private String effectDesc;
    private FoodEffect effect;

    /**
     Constructs a Food object with the given parameters.
     @param id the ID of the food
     @param photoUrl the URL of the food's photo
     @param name the name of the food
     @param effectDesc a description of the effect the food has on a brawler
     @param effect the FoodEffect object that represents the effect of the food
     */
    Food(int id, String photoUrl, String name, String effectDesc, FoodEffect effect){
        this.id = id;
        this.photoUrl = photoUrl;
        this.effect = effect;
        this.name = name;
        this.effectDesc =  effectDesc;
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
     * The function returns the name.
     *
     * @return The method `getName()` is returning the value of the `name` variable, which is a
     * `String`.
     */
    public String getName() {
        return name;
    }

    /**
     * The function returns the effect description as a string.
     *
     * @return The method `getEffectDesc()` is returning a `String` value, which is the value of the
     * variable `effectDesc`.
     */
    public String getEffectDesc() {
        return effectDesc;
    }

    /**
     * The function returns a string representing a photo URL.
     *
     * @return The method `getPhotoUrl()` is returning a `String` value which is the `photoUrl`.
     */
    public String getPhotoUrl() {
        return photoUrl;
    }

    /**
     * The function applies an effect to a Brawler and returns the modified Brawler.
     *
     * @param brawler The "brawler" parameter is an object of the class "Brawler". It is being passed
     * as an argument to the "doEffect" method.
     * @return The method is returning a Brawler object.
     */
    Brawler doEffect(Brawler brawler){
        return effect.doEffect(brawler);
    }
}
