package com.example.as1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CounterActivity extends AppCompatActivity {

    Button attkBtn1;
    Button attkBtn2;
    Button fightBtn1;
    Button healthBtn1;
    Button healthBtn2;
    Button backBtn;
    TextView attkText1;
    TextView attkText2;
    TextView healthText1;
    TextView healthText2;
    TextView winMsg;

    int attk1 = 0;
    int attk2 = 0;
    int health1 = 0;
    int health2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        backBtn = findViewById(R.id.backBtn);

        attkBtn1 = findViewById(R.id.attkBtn);
        attkBtn2 = findViewById(R.id.attkBtn2);

        healthBtn1 = findViewById(R.id.healthBtn);
        healthBtn2 = findViewById(R.id.healthBtn2);

        fightBtn1 = findViewById(R.id.fightBtn);

        attkText1 = findViewById(R.id.attk);
        attkText2 = findViewById(R.id.attk2);
        healthText1 = findViewById(R.id.health);
        healthText2 = findViewById(R.id.health2);

        winMsg = findViewById(R.id.winMsg);

        attkBtn1.setOnClickListener(v -> attkText1.setText(String.valueOf(++attk1)));
        attkBtn2.setOnClickListener(v -> attkText2.setText(String.valueOf(++attk2)));
        healthBtn1.setOnClickListener(v -> healthText1.setText(String.valueOf(++health1)));
        healthBtn2.setOnClickListener(v -> healthText2.setText(String.valueOf(++health2)));

        fightBtn1.setOnClickListener(v -> {
            health1 -= attk2;
            health2 -= attk1;

            health1 = Math.max(health1, 0);
            health2 = Math.max(health2, 0);

            healthText1.setText(String.valueOf(health1));
            healthText2.setText(String.valueOf(health2));

            checkWin();
        });

        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(CounterActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    public void checkWin(){
        if(health1 == 0 && health2 == 0){
            winMsg.setText(R.string.drawMsg);
            winMsg.setVisibility(View.VISIBLE);
        } else if (health1 == 0){
            winMsg.setText(R.string.win2Msg);
            winMsg.setVisibility(View.VISIBLE);
        } else if (health2 == 0){
            winMsg.setText(R.string.win1Msg);
            winMsg.setVisibility(View.VISIBLE);
        } else {
            winMsg.setVisibility(View.INVISIBLE);
        }
    }
}