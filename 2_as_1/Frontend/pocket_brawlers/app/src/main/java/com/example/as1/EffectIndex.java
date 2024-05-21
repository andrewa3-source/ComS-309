package com.example.as1;

import android.util.Log;

import java.util.Random;

/**
 * The EffectIndex class contains a static method that returns a Runnable object based on the effectId
 * and uniqueBrawlerId parameters.
 *
 * @author Drew Kinneer
 */
public class EffectIndex {
    /**
     * The function returns a Runnable object that applies different effects to brawlers in a game
     * based on the effectId and uniqueBrawlerId parameters.
     *
     * @param effectId        An integer representing the type of effect to be applied. The possible values
     *                        are 0, 2, 3, and 5, each corresponding to a different effect.
     * @param uniqueBrawlerId uniqueBrawlerId is an integer value that represents the unique identifier
     *                        of a specific brawler in the game. It is used in the code to ensure that the effect is not
     *                        applied to the brawler that triggered the effect.
     * @return A `Runnable` object is being returned.
     */
    public static Runnable getEffect(int effectId, int uniqueBrawlerId) {
        switch (effectId) {
            case 0: // Umbasaur All current brawlers +1 health
                return () -> {
                    for (int i = 0; i < ActiveLineup.lineup.length; i++) {
                        Brawler brawler = ActiveLineup.getBrawler(i);
                        if (brawler != null) {
                            if (brawler.getUniqueId() != uniqueBrawlerId) { //Don't give effect to the Umbasaur who triggered the effect
                                brawler.addHealth(1);
                                ActiveLineup.setBrawler(i, brawler);
                            }
                        }
                    }
                };
            case 1: //Corpmander +2 attack for every corpmander already in lineup
                return () -> {
                    int attack = 0;
                    int indexOfCorp = -1;
                    for (int i = 0; i < ActiveLineup.lineup.length; i++) {
                        Brawler brawler = ActiveLineup.getBrawler(i);
                        if (brawler != null) {
                            if (brawler.getName().equals("Corpmander") &&
                                    brawler.getUniqueId() != uniqueBrawlerId) { //Find all other Corpmanders
                                attack += 2;
                            }
                            if (brawler.getUniqueId() == uniqueBrawlerId) { //Find the Corpmander that used the effect
                                indexOfCorp = i;
                            }
                        }
                    }
                    if (indexOfCorp != -1) {
                        Brawler corp = ActiveLineup.getBrawler(indexOfCorp);
                        corp.addAttack(attack);
                        ActiveLineup.setBrawler(indexOfCorp, corp);
                    }
                };
            case 2: //Lickivee Gives +2 attack to random brawler
                return () -> {
                    Random rand = new Random();
                    int index = rand.nextInt(5);
                    Brawler brawler = ActiveLineup.getBrawler(index);
                    if (ActiveLineup.numBrawlersInLineup() > 1) { // If there's a brawler to give the effect to
                        if (brawler != null) {
                            if (brawler.getUniqueId() == uniqueBrawlerId) { //Don't give effect to the Lickivee who triggered the effect
                                brawler = null;
                            }
                        }
                        while (brawler == null) {
                            index = rand.nextInt(5);
                            brawler = ActiveLineup.getBrawler(index);
                            if (brawler != null) {
                                if (brawler.getUniqueId() == uniqueBrawlerId) { //Don't give effect to the Lickvee who triggered the effect
                                    brawler = null;
                                }
                            }
                        }
                        brawler.addAttack(2);
                        ActiveLineup.setBrawler(index, brawler);
                    }
                };
            case 3: //Lorporeon plus 2 health and damage to all brawlers on sell
                return () -> {
                    for (int i = 0; i < ActiveLineup.lineup.length; i++) {
                        if (ActiveLineup.getBrawler(i) != null) {
                            Brawler brawler = ActiveLineup.getBrawler(i);
                            brawler.addAttack(2);
                            brawler.addHealth(2);
                            ActiveLineup.setBrawler(i, brawler);
                        }
                    }
                };
            case 4: //Cofados gives -1 Attack and -1 Health to all brawlers
                return () -> {
                    for (int i = 0; i < ActiveLineup.lineup.length; i++) {
                        Brawler brawler = ActiveLineup.getBrawler(i);
                        if (brawler != null) {
                            if (brawler.getUniqueId() != uniqueBrawlerId) {
                                brawler.subtractHealth(1);
                                brawler.subtractAttack(1);
                                if (brawler.getHealth() < 1){
                                    brawler.setHealth(1);
                                }
                                if (brawler.getAttack() < 1){
                                    brawler.setAttack(1);
                                }
                                ActiveLineup.setBrawler(i, brawler);
                            }
                        }
                    }
                };
            case 5: //Mimetuff plus 1 health and plus 1 damage for every turn its in your team
                return () -> {
                    for (int i = 0; i < ActiveLineup.lineup.length; i++) {
                        Brawler brawler = ActiveLineup.getBrawler(i);
                        if (brawler != null) {
                            if (brawler.getUniqueId() == uniqueBrawlerId) {
                                brawler.addHealth(1);
                                brawler.addAttack(1);
                                ActiveLineup.setBrawler(i, brawler);
                            }
                        }
                    }
                };
            case 6: //Seatric gives the brawler behind +3 health and +1 damage on battle start
                return () -> {
                    for (int i = ActiveLineup.lineup.length - 1; i >= 0; i--) {
                        Brawler seatric = ActiveLineup.getBrawler(i);
                        if (seatric != null) {
                            if (seatric.getUniqueId() == uniqueBrawlerId && i != 4) {
                                Brawler brawler = ActiveLineup.getBrawler(i + 1);
                                if (brawler != null) {
                                    brawler.addAttack(1);
                                    brawler.addHealth(3);
                                    ActiveLineup.setBrawler(i + 1, brawler);
                                }
                            }
                        }
                    }
                };
            case 7: //Osharim Every turn for every other osharim in the current line up each osharim will recieve +1 health
                return () -> {
                    for (int i = 0; i < ActiveLineup.lineup.length; i++) {
                        Brawler brawler = ActiveLineup.getBrawler(i);
                        if (brawler != null) {
                            if (brawler.getUniqueId() != uniqueBrawlerId && brawler.getId() == 3457) {
                                brawler.addHealth(1);
                                ActiveLineup.setBrawler(i, brawler);
                            }
                        }
                    }
                };

            case 8: //Pikaunter If there is already a Pikaunter on your team, attack and defense automatically go to 1, 1 for the new bought Pikaunter
                return () -> {
                    boolean pikaExists = false;
                    for (int i = 0; i < ActiveLineup.lineup.length; i++) {
                        Brawler brawler = ActiveLineup.getBrawler(i);
                        if (brawler != null) {
                            if (brawler.getId() == 3458 && brawler.getUniqueId() != uniqueBrawlerId) {
                                pikaExists = true;
                            }
                        }
                    }
                    for (int i = 0; i < ActiveLineup.lineup.length; i++) {
                        Brawler brawler = ActiveLineup.getBrawler(i);
                        if (brawler != null) {
                            if (brawler.getUniqueId() == uniqueBrawlerId && pikaExists) {
                                brawler.setAttack(1);
                                brawler.setHealth(1);
                                ActiveLineup.setBrawler(i, brawler);
                            }
                        }
                    }
                };
            case 9: //Pipsqueak All brawlers in front of Pipsqueak gain +1 hp +1 attack on battle start
                return () -> {
                    boolean startEffect = false;
                    for (int i = ActiveLineup.lineup.length - 1; i >= 0; i--) {
                        Brawler brawler = ActiveLineup.getBrawler(i);
                        if (brawler != null) {
                            if (startEffect) {
                                brawler.addAttack(1);
                                brawler.addHealth(1);
                                ActiveLineup.setBrawler(i, brawler);
                            }
                            if (brawler.getUniqueId() == uniqueBrawlerId) {
                                startEffect = true;
                            }
                        }
                    }
                };
            case 10: //Reggoro On sell, give random brawler all of his attack
                return () -> {
                    Random rand = new Random();
                    int index = rand.nextInt(5);
                    int attack = 0;
                    Brawler brawler = ActiveLineup.getBrawler(index);
                    if (ActiveLineup.numBrawlersInLineup() > 1) { // If there's a brawler to give the effect to
                        if (brawler != null) {
                            if (brawler.getUniqueId() == uniqueBrawlerId) { //Don't give effect to the Lickivee who triggered the effect
                                brawler = null;
                            }
                        }
                        while (brawler == null) {
                            index = rand.nextInt(5);
                            brawler = ActiveLineup.getBrawler(index);
                            if (brawler != null) {
                                if (brawler.getUniqueId() == uniqueBrawlerId) { //Don't give effect to the Reggoro who triggered the effect
                                    brawler = null;
                                }
                            }
                        }
                        for (int i = 0; i < ActiveLineup.lineup.length; i++) {
                            Brawler reggoro = ActiveLineup.getBrawler(i);
                            if (reggoro != null) {
                                if (reggoro.getUniqueId() == uniqueBrawlerId) {
                                    attack = reggoro.getAttack();
                                }
                            }
                        }
                        System.out.println(brawler);
                        brawler.addAttack(attack);
                        ActiveLineup.setBrawler(index, brawler);
                    }
                };
            case 11: //FILTHY BALL Toxic waste to the team, all brawlers on current team -2 health and damage for each turn you have the dirty bubble
                return () -> {
                    for (int i = 0; i < ActiveLineup.lineup.length; i++) {
                        Brawler brawler = ActiveLineup.getBrawler(i);
                        if (brawler != null) {
                            if (brawler.getUniqueId() != uniqueBrawlerId) {
                                brawler.subtractHealth(2);
                                brawler.subtractAttack(2);
                                if (brawler.getHealth() < 1){
                                    brawler.setHealth(1);
                                }
                                if (brawler.getAttack() < 1){
                                    brawler.setAttack(1);
                                }
                                ActiveLineup.setBrawler(i, brawler);
                            }
                        }
                    }
                };
            case 12: //Dituffet If you have 5 dituffets in your lineup, they each get +2 attack and +1 health per turn
                return () -> {
                    boolean runEffect = true;
                    for (int i = 0; i < ActiveLineup.lineup.length; i++) {
                        Brawler brawler = ActiveLineup.getBrawler(i);
                        if (brawler != null) {
                            if (brawler.getId() != 3462) {
                                runEffect = false;
                            }
                        } else {
                            runEffect = false;
                        }
                    }
                    if (runEffect) {
                        for (int i = 0; i < ActiveLineup.lineup.length; i++) {
                            Brawler brawler = ActiveLineup.getBrawler(i);
                            if (brawler != null) {
                                if (brawler.getUniqueId() == uniqueBrawlerId) {
                                    brawler.addAttack(2);
                                    brawler.addHealth(1);
                                    ActiveLineup.setBrawler(i, brawler);
                                }
                            }
                        }
                    }
                };
            case 13: //GMO Plus 5 attack for every turn
                return () -> {
                    for (int i = 0; i < ActiveLineup.lineup.length; i++) {
                        Brawler brawler = ActiveLineup.getBrawler(i);
                        if (brawler != null) {
                            if (brawler.getUniqueId() == uniqueBrawlerId) {
                                brawler.addAttack(5);
                                ActiveLineup.setBrawler(i, brawler);
                            }
                        }
                    }
                };
        }
        return () -> {
        };
    }
}
