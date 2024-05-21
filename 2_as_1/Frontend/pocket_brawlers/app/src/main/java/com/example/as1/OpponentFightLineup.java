package com.example.as1;

import android.app.Activity;
import android.os.CountDownTimer;
import android.util.Log;
import android.util.Log;
import android.widget.ImageView;

import com.example.as1.utils.Const;

/**
 * @author Drew Kinneer
 * @author Jack Kelley
 * The `OpponentFightLineup` class initializes a lineup of `FightBrawlerFrame` objects for an
 * opponent's team in a fighting game, either by retrieving the team from a URL using websockets or
 * generating a CPU team.
 */
public class OpponentFightLineup extends FightLineup{
    /**
     OpponentFightLineup class represents the opponent's lineup in a fight.
     It extends the FightLineup class and initializes the lineup with opponent's brawlers.
     If the useWebsockets boolean in the activity's Intent extras is true, it retrieves the opponent's lineup
     from the server using the URL_ACCOUNTS and setLineup() method from the FightLineup class.
     If useWebsockets is false, it generates the opponent's lineup using the generateCPU() method from the same class.
     @param activity The activity where the opponent's lineup will be displayed.
     */
    OpponentFightLineup(Activity activity) {
        ImageView spot1 = activity.findViewById(R.id.opponentSpot1);
        ImageView spot2 = activity.findViewById(R.id.opponentSpot2);
        ImageView spot3 = activity.findViewById(R.id.opponentSpot3);
        ImageView spot4 = activity.findViewById(R.id.opponentSpot4);
        ImageView spot5 = activity.findViewById(R.id.opponentSpot5);

        lineup[0] = new FightBrawlerFrame(spot1, activity, 0);
        lineup[1] = new FightBrawlerFrame(spot2, activity, 1);
        lineup[2] = new FightBrawlerFrame(spot3, activity, 2);
        lineup[3] = new FightBrawlerFrame(spot4, activity, 3);
        lineup[4] = new FightBrawlerFrame(spot5, activity, 4);
        if(activity.getIntent().getExtras().getBoolean("useWebsockets")){
            setLineup(activity.getIntent().getExtras().getInt("opponent_id"), Const.URL_ACCOUNTS + "/formattedBrawlers/");
        }
        else{
            setLineup(activity.getIntent().getExtras().getInt("id"), Const.URL_ACCOUNTS + "/generateCPU/");
        }
    }
}
