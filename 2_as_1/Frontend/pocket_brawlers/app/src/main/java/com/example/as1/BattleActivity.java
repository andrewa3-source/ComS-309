package com.example.as1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.as1.app.AppController;
import com.example.as1.utils.Const;

import org.json.JSONObject;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * The BattleActivity is used for calculating animations and updating brawler lineups
 * during a battle in a game.
 * @author Jack Kelley
 */
public class BattleActivity extends AppCompatActivity {
    private String TAG = BattleActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    // This tag will be used to cancel the request
    private String tag_json_obj = "jobj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        PlayerFightLineup playerFightLineup = new PlayerFightLineup(this);
        OpponentFightLineup opponentFightLineup = new OpponentFightLineup(this);

        new CountDownTimer(1000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                playerFightLineup.setFrontBrawler(playerFightLineup.getFrontBrawlerFrame().getBrawler());
                opponentFightLineup.setFrontBrawler(opponentFightLineup.getFrontBrawlerFrame().getBrawler());

                if (!playerFightLineup.allDead() && !opponentFightLineup.allDead()) {
                    calculateAnimations(playerFightLineup, opponentFightLineup);
                }
            }

        }.start();
    }

    /**
     * This function calculates the animations and updates the brawler lineups during a battle in a
     * game.
     *
     * @param playerFightLineup An object representing the player's lineup of brawlers for the battle.
     * It contains information such as the brawlers' health, attack power, and images.
     * @param opponentFightLineup It is an object of the OpponentFightLineup class, which represents
     * the lineup of brawlers that the opponent has in a battle. It contains an array of
     * FightBrawlerFrame objects, each of which represents a brawler in the lineup and contains
     * information such as the brawler's health
     */
    private void calculateAnimations(PlayerFightLineup playerFightLineup, OpponentFightLineup opponentFightLineup) {

        //TODO add check for start of battle effects and also add effects in general
        Brawler playerBrawler = playerFightLineup.getFrontBrawlerFrame().getBrawler();
        Brawler opponentBrawler = opponentFightLineup.getFrontBrawlerFrame().getBrawler();
        ImageView playerBrawlerImage = playerFightLineup.getFrontBrawlerFrame().getBrawlerImage();
        ImageView opponentBrawlerImage = opponentFightLineup.getFrontBrawlerFrame().getBrawlerImage();

        playerBrawlerImage.setAnimation(getAnimation(1));
        opponentBrawlerImage.setAnimation(getAnimation(2));
        playerBrawler.subtractHealth(opponentBrawler.getAttack());
        opponentBrawler.subtractHealth(playerBrawler.getAttack());

        opponentBrawlerImage.getAnimation().setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                playerFightLineup.setFrontBrawler(playerBrawler);
                opponentFightLineup.setFrontBrawler(opponentBrawler);

                //Log.d("Test", "Player: " + playerFightLineup.toString());
                //Log.d("Test", "Opp: " + opponentFightLineup.toString());

                Log.d("Test", playerFightLineup.getFrontBrawlerFrame().getBrawler().getName() + " and " +
                        opponentFightLineup.getFrontBrawlerFrame().getBrawler().getName());

                if (playerBrawler.getHealth() <= 0 && opponentBrawler.getHealth() <= 0) {
                    playerBrawlerImage.setAnimation(getAnimation(3));
                    opponentBrawlerImage.setAnimation(getAnimation(3));
                    playerBrawlerImage.getAnimation().setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }
                        @Override
                        public void onAnimationEnd(Animation animation) {
                            for (int i=0; i<4; ++i){
                                playerFightLineup.setBrawler(i, playerFightLineup.lineup[i+1].getBrawler());
                            }
                            playerFightLineup.setBrawler(4, null);
                            for (int i=0; i<5; ++i){
                                if(playerFightLineup.lineup[i].getBrawler().getId() == 0){
                                    playerFightLineup.lineup[i].setDead(true);
                                }
                            }
                            for (int i=0; i<4; ++i){
                                opponentFightLineup.setBrawler(i, opponentFightLineup.lineup[i+1].getBrawler());
                            }
                            opponentFightLineup.setBrawler(4, null);
                            for (int i=0; i<5; ++i){
                                if(opponentFightLineup.lineup[i].getBrawler().getId() == 0){
                                    opponentFightLineup.lineup[i].setDead(true);
                                }
                            }
                            if(opponentFightLineup.getFrontBrawlerFrame() == null && playerFightLineup.getFrontBrawlerFrame() == null){
                                TextView message = findViewById(R.id.messageText);
                                message.setText("TIE");
                                sendResult("tie");
                                Intent intent = new Intent(BattleActivity.this, BrawlerActivity.class);
                                intent.putExtras(getIntent().getExtras());
                                startActivity(intent);
                                return;
                            }
                            else if(opponentFightLineup.getFrontBrawlerFrame() == null){
                                playerFightLineup.setFrontBrawler(playerFightLineup.getFrontBrawlerFrame().getBrawler());
                                playerFightLineup.getFrontBrawlerFrame().getBrawlerImage().setAnimation(getAnimation(7));
                                for(FightBrawlerFrame frame : playerFightLineup.lineup){
                                    frame.getBrawlerImage().startAnimation(getAnimation(4));
                                }
                                TextView message = findViewById(R.id.messageText);
                                message.setText("YOU WON");
                                sendResult("win");
                                Intent intent = new Intent(BattleActivity.this, BrawlerActivity.class);
                                intent.putExtras(getIntent().getExtras());
                                startActivity(intent);
                                return;
                            }
                            else if(playerFightLineup.getFrontBrawlerFrame() == null){
                                opponentFightLineup.setFrontBrawler(opponentFightLineup.getFrontBrawlerFrame().getBrawler());
                                opponentFightLineup.getFrontBrawlerFrame().getBrawlerImage().setAnimation(getAnimation(7));
                                for(FightBrawlerFrame frame : opponentFightLineup.lineup){
                                    frame.getBrawlerImage().startAnimation(getAnimation(4));
                                }
                                TextView message = findViewById(R.id.messageText);
                                message.setText("CPU WON");
                                sendResult("loss");
                                Intent intent = new Intent(BattleActivity.this, BrawlerActivity.class);
                                intent.putExtras(getIntent().getExtras());
                                startActivity(intent);
                                return;
                            }
                            else{
                                playerFightLineup.setFrontBrawler(playerFightLineup.getFrontBrawlerFrame().getBrawler());
                                playerFightLineup.getFrontBrawlerFrame().getBrawlerImage().setAnimation(getAnimation(7));
                                opponentFightLineup.setFrontBrawler(opponentFightLineup.getFrontBrawlerFrame().getBrawler());
                                opponentFightLineup.getFrontBrawlerFrame().getBrawlerImage().setAnimation(getAnimation(7));
                            }
                            playerFightLineup.getFrontBrawlerFrame().getBrawlerImage().getAnimation().setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {
                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    Log.d("Loc", "1");
                                    if(!playerFightLineup.allDead() && !opponentFightLineup.allDead()){
                                        calculateAnimations(playerFightLineup, opponentFightLineup);
                                    }
                                    else if(!playerFightLineup.allDead()){
                                        for(FightBrawlerFrame brawler : playerFightLineup.lineup){
                                            if(brawler.getBrawler().getId() != 0){
                                                brawler.getBrawlerImage().startAnimation(getAnimation(4));
                                            }
                                        }
                                    }
                                    else if(!opponentFightLineup.allDead()){
                                        for(FightBrawlerFrame brawler : opponentFightLineup.lineup){
                                            if(brawler.getBrawler().getId() != 0){
                                                brawler.getBrawlerImage().startAnimation(getAnimation(4));
                                            }
                                        }
                                    }
                                }
                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                            playerFightLineup.getFrontBrawlerFrame().getBrawlerImage().getAnimation().start();
                        }
                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                    });
                    playerBrawlerImage.getAnimation().start();
                }
                else if (opponentBrawler.getHealth() <= 0) {
                    opponentBrawlerImage.setAnimation(getAnimation(3));
                    opponentBrawlerImage.getAnimation().setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }
                        @Override
                        public void onAnimationEnd(Animation animation) {
                            for (int i=0; i<4; ++i){
                                opponentFightLineup.setBrawler(i, opponentFightLineup.lineup[i+1].getBrawler());
                            }
                            opponentFightLineup.setBrawler(4, null);
                            for (int i=0; i<5; ++i){
                                if(opponentFightLineup.lineup[i].getBrawler().getId() == 0){
                                    opponentFightLineup.lineup[i].setDead(true);
                                }
                            }
                            if(opponentFightLineup.getFrontBrawlerFrame() == null){
                                for(FightBrawlerFrame frame : playerFightLineup.lineup){
                                    frame.getBrawlerImage().startAnimation(getAnimation(4));
                                }
                                TextView message = findViewById(R.id.messageText);
                                message.setText("YOU WON");
                                sendResult("win");
                                Intent intent = new Intent(BattleActivity.this, BrawlerActivity.class);
                                intent.putExtras(getIntent().getExtras());
                                startActivity(intent);
                                return;
                            }
                            opponentFightLineup.setFrontBrawler(opponentFightLineup.getFrontBrawlerFrame().getBrawler());
                            opponentFightLineup.getFrontBrawlerFrame().getBrawlerImage().setAnimation(getAnimation(7));
                            opponentFightLineup.getFrontBrawlerFrame().getBrawlerImage().getAnimation().setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {
                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    Log.d("Loc", "2");
                                    if(!playerFightLineup.allDead() && !opponentFightLineup.allDead()){
                                        calculateAnimations(playerFightLineup, opponentFightLineup);
                                    }
                                    else if(!playerFightLineup.allDead()){
                                        for(FightBrawlerFrame brawler : playerFightLineup.lineup){
                                            if(brawler.getBrawler().getId() != 0){
                                                brawler.getBrawlerImage().startAnimation(getAnimation(4));
                                            }
                                        }
                                        TextView message = findViewById(R.id.messageText);
                                        message.setText("YOU WON");
                                        sendResult("win");
                                        Intent intent = new Intent(BattleActivity.this, BrawlerActivity.class);
                                        intent.putExtras(getIntent().getExtras());
                                        startActivity(intent);
                                    }
                                    else if(!opponentFightLineup.allDead()){
                                        for(FightBrawlerFrame brawler : opponentFightLineup.lineup){
                                            if(brawler.getBrawler().getId() != 0){
                                                brawler.getBrawlerImage().startAnimation(getAnimation(4));
                                            }
                                        }
                                        TextView message = findViewById(R.id.messageText);
                                        message.setText("CPU WON");
                                        sendResult("loss");
                                        Intent intent = new Intent(BattleActivity.this, BrawlerActivity.class);
                                        intent.putExtras(getIntent().getExtras());
                                        startActivity(intent);
                                    }
                                }
                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                            opponentFightLineup.getFrontBrawlerFrame().getBrawlerImage().getAnimation().start();
                        }
                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                    });
                    opponentBrawlerImage.getAnimation().start();
                }
                else if (playerBrawler.getHealth() <= 0) {
                    playerBrawlerImage.setAnimation(getAnimation(3));
                    playerBrawlerImage.getAnimation().setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }
                        @Override
                        public void onAnimationEnd(Animation animation) {
                            for (int i=0; i<4; ++i){
                                playerFightLineup.setBrawler(i, playerFightLineup.lineup[i+1].getBrawler());
                            }
                            playerFightLineup.setBrawler(4, null);
                            for (int i=0; i<5; ++i){
                                if(playerFightLineup.lineup[i].getBrawler().getId() == 0){
                                    playerFightLineup.lineup[i].setDead(true);
                                }
                            }
                            if(playerFightLineup.getFrontBrawlerFrame() == null){
                                for(FightBrawlerFrame frame : opponentFightLineup.lineup){
                                    frame.getBrawlerImage().startAnimation(getAnimation(4));
                                }
                                TextView message = findViewById(R.id.messageText);
                                message.setText("CPU WON");
                                sendResult("loss");
                                Intent intent = new Intent(BattleActivity.this, BrawlerActivity.class);
                                intent.putExtras(getIntent().getExtras());
                                startActivity(intent);
                                return;
                            }
                            playerFightLineup.setFrontBrawler(playerFightLineup.getFrontBrawlerFrame().getBrawler());
                            playerFightLineup.getFrontBrawlerFrame().getBrawlerImage().setAnimation(getAnimation(7));
                            playerFightLineup.getFrontBrawlerFrame().getBrawlerImage().getAnimation().setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {
                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    Log.d("Loc", "3");
                                    if(!playerFightLineup.allDead() && !opponentFightLineup.allDead()){
                                        calculateAnimations(playerFightLineup, opponentFightLineup);
                                    }
                                    else if(!playerFightLineup.allDead()){
                                        for(FightBrawlerFrame brawler : playerFightLineup.lineup){
                                            if(brawler.getBrawler().getId() != 0){
                                                brawler.getBrawlerImage().startAnimation(getAnimation(4));
                                            }
                                        }
                                        TextView message = findViewById(R.id.messageText);
                                        message.setText("YOU WON");
                                        sendResult("win");
                                        Intent intent = new Intent(BattleActivity.this, BrawlerActivity.class);
                                        intent.putExtras(getIntent().getExtras());
                                        startActivity(intent);
                                    }
                                    else if(!opponentFightLineup.allDead()){
                                        for(FightBrawlerFrame brawler : opponentFightLineup.lineup){
                                            if(brawler.getBrawler().getId() != 0){
                                                brawler.getBrawlerImage().startAnimation(getAnimation(4));
                                            }
                                        }
                                        TextView message = findViewById(R.id.messageText);
                                        message.setText("CPU WON");
                                        sendResult("loss");
                                        Intent intent = new Intent(BattleActivity.this, BrawlerActivity.class);
                                        intent.putExtras(getIntent().getExtras());
                                        startActivity(intent);
                                    }
                                }
                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                            playerFightLineup.getFrontBrawlerFrame().getBrawlerImage().getAnimation().start();
                        }
                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                    });
                    playerBrawlerImage.getAnimation().start();
                }
                else {
                    Log.d("Loc", "4");
                    if(!playerFightLineup.allDead() && !opponentFightLineup.allDead()){
                        calculateAnimations(playerFightLineup, opponentFightLineup);
                    }
                    else if(!playerFightLineup.allDead()){
                        for(FightBrawlerFrame brawler : playerFightLineup.lineup){
                            if(brawler.getBrawler().getId() != 0){
                                brawler.getBrawlerImage().startAnimation(getAnimation(4));
                            }
                        }
                        TextView message = findViewById(R.id.messageText);
                        message.setText("YOU WON");
                        sendResult("win");
                        Intent intent = new Intent(BattleActivity.this, BrawlerActivity.class);
                        intent.putExtras(getIntent().getExtras());
                        startActivity(intent);
                    }
                    else if(!opponentFightLineup.allDead()){
                        for(FightBrawlerFrame brawler : opponentFightLineup.lineup){
                            if(brawler.getBrawler().getId() != 0){
                                brawler.getBrawlerImage().startAnimation(getAnimation(4));
                            }
                        }
                        TextView message = findViewById(R.id.messageText);
                        message.setText("CPU WON");
                        sendResult("loss");
                        Intent intent = new Intent(BattleActivity.this, BrawlerActivity.class);
                        intent.putExtras(getIntent().getExtras());
                        startActivity(intent);
                    }
                }
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        playerFightLineup.getFrontBrawlerFrame().getBrawlerImage().getAnimation().start();
        opponentFightLineup.getFrontBrawlerFrame().getBrawlerImage().getAnimation().start();
    }
    /**
     * The function returns an animation based on the given animation ID.
     *
     * @param animationId The ID of the animation to be retrieved. It is used in the switch statement
     * to determine which animation to load and return.
     * @return The method returns an object of type Animation, which is loaded from a specific
     * animation resource file based on the input animationId parameter.
     */
    private Animation getAnimation(int animationId){
        // 1 = left attacks, 2 = right attacks, 3 = death, 4 = player win, 5 = player lose, 6 = tie, 7 = load
        switch(animationId){
            case 1: return AnimationUtils.loadAnimation(getApplicationContext(), R.anim.attack_left);
            case 2: return AnimationUtils.loadAnimation(getApplicationContext(), R.anim.attack_right);
            case 3: return AnimationUtils.loadAnimation(getApplicationContext(), R.anim.death_right);
            case 4: return AnimationUtils.loadAnimation(getApplicationContext(), R.anim.victory_left);
            case 5: return null;//AnimationUtils.loadAnimation(getApplicationContext(), R.anim.victory_left); //TODO Update for loss
            case 6: return null;//AnimationUtils.loadAnimation(getApplicationContext(), R.anim.victory_left); //TODO Update for tie
            case 7: return AnimationUtils.loadAnimation(getApplicationContext(), R.anim.load_right);
            default: return null;
        }
    }

    /**
     * The function sends a POST request to a specified URL with a JSON array as the request body and
     * logs the response.
     *
     * @param result The result parameter is a string that represents the result of a loop turn. It is
     * used as a parameter in the URL of a POST request to update the loop turn in the backend server.
     */
    private void sendResult(String result){
        JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.POST,
                Const.URL_ACCOUNTS + "/loopTurn/" + getIntent().getExtras().get("id") + "/" + result, null,
                response -> {
                    Log.d(TAG, response.toString());
                }, error -> {
            VolleyLog.d(TAG, "Error: " + error.getMessage());
            System.out.println(error.getMessage());
        }) {
            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        AppController.getInstance().addToRequestQueue(jsonObjReq,
                tag_json_obj);
    }
}
