package com.example.as1;


/** @author Drew Kinneer
* This class defines the method related to food effects on a Brawler object. */
public interface FoodEffect {
    /**
     * The function "doEffect" takes a Brawler object as input and returns a Brawler object as output.
     *
     * @param brawler The "brawler" parameter is an object of the class "Brawler".
     * @return A Brawler object is being returned.
     */
    Brawler doEffect(Brawler brawler);
}
