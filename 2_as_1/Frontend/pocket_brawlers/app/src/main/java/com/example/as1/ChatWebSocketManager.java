package com.example.as1;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class ChatWebSocketManager {
    private static ChatWebSocketManager instance;
    private WebSocketClient webSocketClient;
    private String serverUrl;

    private ChatWebSocketManager() {}

    public static synchronized ChatWebSocketManager getInstance() {
        if (instance == null) {
            instance = new ChatWebSocketManager();
        }
        return instance;
    }

    private ChatWebSocketListener webSocketListener;

    public void setWebSocketListener(ChatWebSocketListener listener) {
        this.webSocketListener = listener;
    }

    public void removeWebSocketListener() {
        this.webSocketListener = null;
    }

    public void connect(String serverUrl) {
        this.serverUrl = serverUrl;

        URI uri;
        try {
            uri = new URI(serverUrl);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        webSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                Log.d("WebSocket", "Connected");
            }

            @Override
            public void onMessage(String message) {
                Log.d("WebSocket", "Received message: " + message);
                if (webSocketListener != null) {
                    webSocketListener.onWebSocketMessage(message);
                }
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                Log.d("WebSocket", "Closed");
            }

            @Override
            public void onError(Exception ex) {
                ex.printStackTrace();
            }
        };

        webSocketClient.connect();
    }

    public void sendMessage(String message) {
        if (webSocketClient != null && webSocketClient.isOpen()) {
            webSocketClient.send(message);
        }
    }

    public void disconnect() {
        if (webSocketClient != null && webSocketClient.isOpen()) {
            webSocketClient.close();
        }
    }

    public String getServerUrl() {
        return serverUrl;
    }
}