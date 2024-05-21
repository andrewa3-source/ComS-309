package com.example.as1;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ChatActivity extends AppCompatActivity implements ChatWebSocketListener {

    Button backButton;
    Button sendButton;
    EditText textEnter;
    TextView chat;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        backButton = findViewById(R.id.backBtn);
        sendButton = findViewById(R.id.sendBtn);
        textEnter = findViewById(R.id.textEnter);
        chat = findViewById(R.id.chat);
        scrollView = findViewById(R.id.scrollView);

        String url = "ws://coms-309-023.class.las.iastate.edu:8080/websocket/" + getIntent().getExtras().getString("username");

        ChatWebSocketManager.getInstance().connect(url);
        ChatWebSocketManager.getInstance().setWebSocketListener(ChatActivity.this);

        sendButton.setOnClickListener(view -> {
            ChatWebSocketManager.getInstance().sendMessage(textEnter.getText().toString());
            textEnter.setText("");
        });

        backButton.setOnClickListener(view -> {
            Intent intent;
            intent = new Intent(ChatActivity.this, MainActivity.class);
            intent.putExtras(getIntent().getExtras());
            startActivity(intent);
        });
    }

    @Override
    public void onWebSocketMessage(String message) {
        runOnUiThread(() -> {
            String s = chat.getText().toString();
            chat.setText(s + "\n" + message);
            scrollToBottom();
        });
    }

    private void scrollToBottom() {
        scrollView.post(() -> scrollView.smoothScrollTo(0, chat.getBottom()));
    }
}
