package backend.dbms.PocketBrawlers.Brawler.Attributes;

import backend.dbms.PocketBrawlers.Brawler.Brawler;

import java.util.Random;

/**
 * @author Andrew Ahrenkiel
 */
public class Attributes implements AttributeEffect {
    public Attributes(){

    }

    /**
     * Used to calculate Attribute effects on the backend
     * @param b1 - Array of brawlers on team 1
     * @param b2 - Arrau of brawlers on team 2
     * @param attributeIndex - Attribute id for given
     * @param brawlerPos - Position of Brawler doing the effect
     * @return 1 for success, 2 for fail
     * @throws Exception
     */
    @Override
    public int doAffect(Brawler[] b1, Brawler[] b2, int attributeIndex, int brawlerPos) throws Exception {
        switch(attributeIndex) {
            case 0:
                //give all current brawlers +1 on buy
                for (int i = 0; i <= 4; ++i) {
                    if (i == brawlerPos) {
                    } else {
                        if (b1[i] != null) {
                            b1[i].setHealth(b1[i].getHealth() + 1);
                        }
                    }
                }
                break;
            case 1:
                //damage opponents first brawler on start of battle
                b2[0].setHealth(b2[0].getHealth() - 1);
                break;
            case 2:
                //plus 2 damage to a random brawler on buy
                Random rand = new Random();
                //int length = b.length;
                int position;
                while(true) {
                    position = rand.nextInt(5);
                    if (position != brawlerPos) {
                        b1[position].setDamage(b1[position].getDamage() + 2);
                        break;
                    }
                }


            case 3:
                //On sell, give all brawlers +1 hp;
                break;


            case 4:
                //on battle start +1 health and damage
                break;
            default:
                throw new Exception("No Attribute Corrilated");



        }
        return 1;
    }
}
