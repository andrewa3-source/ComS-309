package com.example.as1;

/**
 * The BrawlerEffect class defines the properties and methods of a brawler effect in a game.
 * @author Drew Kinneer
 */
public class BrawlerEffect {
    int effectId;

    int uniqueBrawlerId;
    BrawlerEffectType effectType;

    String effectDesc = "";
    Runnable effect;

    /**
     Creates a new instance of the BrawlerEffect class with the specified effect ID and effect type.
     @param effectId the ID of the effect associated with the brawler.
     @param effectType the type of effect associated with the brawler.
     */
    BrawlerEffect(int effectId, BrawlerEffectType effectType){
        this.effectId = effectId;
        this.effectType = effectType;

        this.effectDesc = setEffectDesc(this.effectId);

        this.effect = EffectIndex.getEffect(effectId, uniqueBrawlerId);
    }

    /**
     * This function returns the effect type of a Brawler in Java.
     *
     * @return The method is returning an object of type `BrawlerEffectType`.
     */
    public BrawlerEffectType getEffectType() {
        return effectType;
    }

    /**
     * The function returns the effect ID.
     *
     * @return The method is returning an integer value which represents the effect ID.
     */
    public int getEffectId() {
        return effectId;
    }

    /**
     * This function runs an effect.
     */
    public void doEffect(){
        effect.run();
    }

    /**
     * This function sets a unique ID for a brawler and retrieves its corresponding effect.
     *
     * @param uniqueBrawlerId uniqueBrawlerId is an integer variable that represents the unique
     * identifier of a brawler. It is used to differentiate between different instances of the same
     * brawler type.
     */
    public void setUniqueBrawlerId(int uniqueBrawlerId) {
        this.uniqueBrawlerId = uniqueBrawlerId;
        this.effect = EffectIndex.getEffect(effectId, this.uniqueBrawlerId);
    }

    /**
     * The function returns a description of the effect of a given effect ID for a game.
     *
     * @param effectId The effectId is an integer value that represents the unique identifier of a
     * specific effect in the game. The switch statement in the code uses this value to determine which
     * effect description to return.
     * @return A string that describes the effect of a certain brawler based on its effect ID.
     */
    private String setEffectDesc(int effectId){
        switch (effectId){
            case 0: //Umbasaur
                return "All current brawlers +1 health";
            case 1: //Corpmander
                return "+2 attack for every corpmander already in lineup";
            case 2: //Lickvee
                return "Gives +2 attack to random brawler";
            case 3: //Lorporeon
                return "plus 2 health and damage to all brawlers on sell";
            case 4: //Cofados
                return "Gives -1 Attack and -1 Health to all brawlers";
            case 5: //Mimetuff
                return "Plus 1 health and plus 1 damage for every turn its in your team";
            case 6: //Seatric
                return "gives the brawler behind Seatric +3 health and +1 damage on battle start";
            case 7: //Osharim
                return "Every turn for every other osharim in the current line up each osharim will recieve +1 health";
            case 8: //Pikaunter
                return "If there is already a Pikaunter on your team, attack and defense automatically go to 1, 1 for the newly bought Pikaunter";
            case 9: //Pipsqueak
                return "All brawlers in front of Pipsqueak gain +1 hp +1 attack on battle start";
            case 10: //Reggoro
                return "On sell, give random brawler all of his attack";
            case 11: //FILTHY BALL
                return "Toxic waste to the team, all brawlers on current team -2 health and damage for each turn you have the dirty bubble";
            case 12: //Dituffet
                return "If you have 5 dituffets in your lineup, they each get +2 attack and +1 health per turn";
            case 13: //GMO
                return "Plus 5 attack for every turn";
        }
        return "";
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
}
