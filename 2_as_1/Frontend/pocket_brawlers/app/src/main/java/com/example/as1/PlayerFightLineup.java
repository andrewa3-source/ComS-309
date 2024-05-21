package com.example.as1;

import android.app.Activity;
import android.os.CountDownTimer;
import android.widget.ImageView;

import com.example.as1.utils.Const;

/**
 * @author Drew Kinneer
 * @author Jack Kelley
 * The `PlayerFightLineup` class is a constructor that initializes five `ImageView` objects and creates
 * five `FightBrawlerFrame` objects for a player's fight lineup.
 */
public class PlayerFightLineup extends FightLineup{
    /**
     PlayerFightLineup class represents the play's lineup in a fight.
     It extends the FightLineup class and initializes the lineup with the player's brawlers.
     @param activity The activity where the player's lineup will be displayed.
     */
    PlayerFightLineup(Activity activity) {
        ImageView spot1 = activity.findViewById(R.id.playerSpot1);
        ImageView spot2 = activity.findViewById(R.id.playerSpot2);
        ImageView spot3 = activity.findViewById(R.id.playerSpot3);
        ImageView spot4 = activity.findViewById(R.id.playerSpot4);
        ImageView spot5 = activity.findViewById(R.id.playerSpot5);

        lineup[0] = new FightBrawlerFrame(spot1, activity, 0);
        lineup[1] = new FightBrawlerFrame(spot2, activity, 1);
        lineup[2] = new FightBrawlerFrame(spot3, activity, 2);
        lineup[3] = new FightBrawlerFrame(spot4, activity, 3);
        lineup[4] = new FightBrawlerFrame(spot5, activity, 4);
        setLineup(activity.getIntent().getExtras().getInt("id"), Const.URL_ACCOUNTS + "/formattedBrawlers/");
    }
}
