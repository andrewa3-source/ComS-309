package com.example.as1;

/**
 * The BrawlerIndex class provides a static method to create Brawler objects based on their ID, attack,
 * health, effect, type, and image URL.
 * @author Drew Kinneer
 */
public class BrawlerIndex {
    /**
     * The function returns a Brawler object based on the given parameters and ID, with a default
     * "Unknown ID Brawler" if the ID is not recognized.
     *
     * @param id The unique identifier for the brawler.
     * @param attack The attack value of the brawler. It represents how much damage the brawler can
     * deal to an opponent.
     * @param health The health parameter represents the initial health value of the brawler.
     * @param effectId The ID of the brawler effect that the brawler has.
     * @param effectType The type of effect that the brawler has. It is of type BrawlerEffectType,
     * which is an enum that defines different types of effects such as DAMAGE, HEAL, and STUN.
     * @param imageUrl The URL of the image associated with the brawler.
     * @return A Brawler object is being returned based on the input parameters. The specific Brawler
     * object returned depends on the value of the "id" parameter, which is used in a switch statement
     * to determine which Brawler to create. If the "id" parameter does not match any of the cases in
     * the switch statement, a default "Unknown ID Brawler" is returned.
     */
    public static Brawler getBrawler(int id, int attack, int health, int effectId, BrawlerEffectType effectType, String imageUrl) {
        BrawlerEffect brawlerEffect = new BrawlerEffect(effectId, effectType);
        switch (id) {
            case 1:
                return new Brawler(attack, health, 1, 1, "Umbasaur", brawlerEffect, imageUrl);
            case 2:
                return new Brawler(attack, health, 2, 1, "Corpmander", brawlerEffect, imageUrl);
            case 3:
                return new Brawler(attack, health, 3, 1, "Lickivee", brawlerEffect, imageUrl);
            case 4:
                return new Brawler(attack, health, 4, 3, "Loporeon", brawlerEffect, imageUrl);
            case 154:
                return new Brawler(attack, health, 154, 2, "Cofados", brawlerEffect, imageUrl);
            case 155:
                return new Brawler(attack, health, 155, 2, "Mimetuff", brawlerEffect, imageUrl);
            case 3456:
                return new Brawler(attack, health, 3456, 2, "Seatric", brawlerEffect, imageUrl);
            case 3457:
                return new Brawler(attack, health, 3457, 2, "Osharim", brawlerEffect, imageUrl);
            case 3458:
                return new Brawler(attack, health, 3458, 3, "Pikaunter", brawlerEffect, imageUrl);
            case 3459:
                return new Brawler(attack, health, 3459, 2, "Pipsqueak", brawlerEffect, imageUrl);
            case 3460:
                return new Brawler(attack, health, 3460, 2, "Reggoro", brawlerEffect, imageUrl);
            case 3461:
                return new Brawler(attack, health, 3461, 4, "FILTHY BALL", brawlerEffect, imageUrl);
            case 3462:
                return new Brawler(attack, health, 3462, 1, "Dituffet", brawlerEffect, imageUrl);
            case 3463:
                return new Brawler(attack, health, 3463, 4, "GMO", brawlerEffect, imageUrl);
        }
        return new Brawler(attack, health, 0, 0, "Unknown ID Brawler", brawlerEffect, imageUrl);
    }

}
