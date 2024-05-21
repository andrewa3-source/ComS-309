package backend.dbms.PocketBrawlers.Websocket.Battles;

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
 * @author Reid Coates
 *
 */
@ServerEndpoint("/websocket/matchmaking/{username}")
@Component
public class WebSocketServer {

    // Store all socket session and their corresponding username.
    private static Map < Session, Integer > sessionUsernameMap = new Hashtable < > ();
    private static Map < Integer, Session > usernameSessionMap = new Hashtable < > ();

    private static Map < Session, Integer > matchmakerMap = new Hashtable < > ();

    private final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    @OnOpen
    public void onOpen(Session session, @PathParam("username") Integer userId)
            throws IOException {
        logger.info("Entered into open for: " + userId);

        sessionUsernameMap.put(session, userId);
        usernameSessionMap.put(userId, session);

        String message = "User:" + userId + " has logged on.";

        for (Map.Entry<Session, Integer> user : sessionUsernameMap.entrySet()) {
            Integer matchedUserId = user.getValue();
            // Test by sending each play back to user
            if (matchedUserId != userId) {
                sendMessageToParticularUser(userId, "Found user: " + matchedUserId.toString());
            }
        }
    }

    @OnMessage
    public void onReady(Session session, String code) throws IOException, InterruptedException {
        // Handle code entries
        Integer userId = sessionUsernameMap.get(session);
        logger.info("Entered into Ready: Got code:" + code + " From: " + userId);

        if (code.equals("Ready")) {
            // there should only ever be one user in this hashtable
            if (matchmakerMap.size() > 0){
                for (Map.Entry<Session, Integer> user : matchmakerMap.entrySet()) {
                    Integer matchedUserId = user.getValue();
                    // if the found user is a different user
                    if (matchedUserId != userId) {

                        // send ids to correct players
                        sendMessageToParticularUser(userId, userId + " matched with " + matchedUserId);
                        sendMessageToParticularUser(matchedUserId, matchedUserId + " matched with " + userId);

                        // remove found player from matchmaking
                        matchmakerMap.remove(user.getKey());
                    } else {
                        // if this user is already in map, print "please wait:
                        sendMessageToParticularUser(userId, userId + ", please wait for another user to enter matchmaking...");
                    }
                }
            } else {
                matchmakerMap.put(session, userId);
                sendMessageToParticularUser(userId, userId + ", please wait for another user to enter matchmaking...");
            }
        } else
        {
            // if a failed code, print it in postman for debugging
            broadcast(userId + " says, \"" + code + ".\"");
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException {

        Integer userId = sessionUsernameMap.get(session);
        logger.info("Entered into Close for: " + userId);
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(userId);
        matchmakerMap.remove(session);

        String message = userId + " has exited Matchmaking..";
        broadcast(message);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
        Integer userId = sessionUsernameMap.get(session);
        logger.info("Entered into Error for: " + userId);
        logger.info(throwable.toString());
    }

    private void sendMessageToParticularUser(Integer userId, String message) {
        try {
            usernameSessionMap.get(userId).getBasicRemote().sendText(message);
        } catch (IOException e) {
            logger.info("Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void broadcast(String message) {
        sessionUsernameMap.forEach((session, userId) -> {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                logger.info("Exception: " + e.getMessage());
                e.printStackTrace();
            }

        });

    }
}
