package backend.dbms.PocketBrawlers.Websocket.Chat;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author Andrew Ahrenkiel
 *
 */

@ServerEndpoint("/websocket/{username}")
@Component
public class WebSocketChat {


    // Store all socket session and their corresponding username.
    private static Map < Session, String > sessionUsernameMap = new Hashtable < > ();
    private static Map < String, Session > usernameSessionMap = new Hashtable < > ();

    private final Logger logger = LoggerFactory.getLogger(WebSocketChat.class);


    /**
     * Opens and puts the user in the session
     * @param session
     * @param username
     * @throws IOException
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username)
            throws IOException {
        logger.info("Entered into Open");

        sessionUsernameMap.put(session, username);
        usernameSessionMap.put(username, session);

        String message = "User:" + username + " has Joined the Chat";
        broadcast(message);
    }

    /**
     * On user message, brodcasts the message
     * @param session given session user is in
     * @param message the String message to send
     * @throws IOException
     */
    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        // Handle new messages
        logger.info("Entered into Message: Got Message:" + message);
        String username = sessionUsernameMap.get(session);

        if (message.startsWith("@")) // Direct message to a user using the format "@username <message>"
        {
            String destUsername = message.split(" ")[0].substring(1); // don't do this in your code!
            sendMessageToPArticularUser(destUsername, "[DM] " + username + ": " + message);
            sendMessageToPArticularUser(username, "[DM] " + username + ": " + message);
        } else // Message to whole chat
        {
            broadcast(username + ": " + message);
        }
    }

    /**
     * When user exits the chat, removes them from session
     * @param session
     * @throws IOException
     */
    @OnClose
    public void onClose(Session session) throws IOException {
        logger.info("Entered into Close");

        String username = sessionUsernameMap.get(session);
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);

        String message = username + " disconnected";
        broadcast(message);
    }

    /**
     * Error throw on incorrect values
     * @param session given session
     * @param throwable thrown error
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
        logger.info("Entered into Error");
    }

    /**
     * Used to send a message to a certain person based on @username
     * @param username username to send to
     * @param message message to send
     */
    private void sendMessageToPArticularUser(String username, String message) {
        try {
            usernameSessionMap.get(username).getBasicRemote().sendText(message);
        } catch (IOException e) {
            logger.info("Exception: " + e.getMessage().toString());
            e.printStackTrace();
        }
    }

    /**
     * brodcast the message to the session
     * @param message message to send
     */
    private void broadcast(String message) {
        sessionUsernameMap.forEach((session, username) -> {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                logger.info("Exception: " + e.getMessage().toString());
                e.printStackTrace();
            }

        });

    }
}
