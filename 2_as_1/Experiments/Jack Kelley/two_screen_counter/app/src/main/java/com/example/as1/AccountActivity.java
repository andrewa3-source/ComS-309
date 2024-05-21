package com.example.as1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AccountActivity extends AppCompatActivity {
    Button backBtn;
    Button loginBtn;
    EditText usernameInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        backBtn = findViewById(R.id.backBtn);
        loginBtn = findViewById(R.id.loginBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AccountActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AccountActivity.this, MainActivity.class);
                usernameInput = findViewById(R.id.usernameInput);
                intent.putExtra("username", usernameInput.getText().toString());
                startActivity(intent);
            }
        });
    }
}
