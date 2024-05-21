package com.example.as1;

/**
 * @author Drew Kinneer
 * The `FoodIndex` class contains a static method `getFood` that returns a `Food` object based on the
 * given id and url, with different effects on a Brawler depending on the id.
 */
public class FoodIndex {
    /**
     * The function returns a Food object based on the given id and url, with different effects on a
     * Brawler depending on the id.
     *
     * @param id  The id parameter is an integer value that is used to identify a specific type of food.
     *            It is used in the switch statement to determine which food object to create and return.
     * @param url The URL of the image associated with the food item.
     * @return The method `getFood` returns an instance of the `Food` class based on the `id` parameter
     * passed to it. If the `id` matches one of the cases in the switch statement, a new `Food` object
     * is created with the corresponding properties and effect. If the `id` does not match any of the
     * cases, a default `Food` object is returned with the message
     */
    public static Food getFood(int id, String url) {
        switch (id) {
            case 0:
                return new Food(2, url, "Apple", "Give +2 Health to a Brawler", brawler -> {
                    brawler.addHealth(2);
                    return brawler;
                });
            case 3474:
                return new Food(1, url, "Watermelon", "Give +2 Attack to a Brawler", brawler -> {
                    brawler.addAttack(2);
                    return brawler;
                });
            case 3475:
                return new Food(2, url, "Chocolate", "Give +4 Health to a Brawler", brawler -> {
                    brawler.addHealth(4);
                    return brawler;
                });

            case 30:
                return new Food(1, url, "Home Fried Potatoes", "Give +3 Attack and +3 Health to a Brawler", brawler -> {
                    brawler.addAttack(3);
                    brawler.addHealth(3);
                    return brawler;
                });

        }
        return new Food(0, url, "Food not Found", "", null);
    }
}