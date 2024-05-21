package backend.dbms.PocketBrawlers.Websocket.Chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;

/**
 * @author Andrew Ahrenkiel
 */

@ServerEndpoint("/websocket/{league}/{username}")
@Component
public class WebSocketLeagueChat {

    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());

    /**
     * Open session for particular league
     * @param session this session
     * @param league league identifier
     * @param username username of user
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("league") String league, @PathParam("username") String username) {
        // Add the session to the set of active sessions for this chat room
        sessions.add(session);
        // Broadcast a message to all sessions in this chat room
        broadcast(league, session, username + " joined the chat!");
    }

    /**
     * Send a message to the current league session
     * @param session this session
     * @param message message to send
     * @param league league identifier
     * @param username username of user
     */
    @OnMessage
    public void onMessage(Session session, String message, @PathParam("league") String league, @PathParam("username") String username) {
        // Broadcast the message to all sessions in this chat room
        broadcast(league, session, username + ": " + message);
    }

    /**
     * Close the session when no users remain
     * @param session session to close
     * @param league league identifier
     */
    @OnClose
    public void onClose(Session session, @PathParam("league") String league) {
        // Remove the session from the set of active sessions for this chat room
        sessions.remove(session);
        // Broadcast a message to all sessions in this chat room
        broadcast(league, session, "left the chat");
    }

    /**
     * Brodcast the message to the current session
     * @param league league identifier
     * @param sender user sending the message
     * @param message message to send
     */
    private void broadcast(String league, Session sender, String message) {
        synchronized (sessions) {
            for (Session session : sessions) {
                if (session.isOpen() && league.equals(session.getPathParameters().get("league")) && !sender.equals(session)) {
                    session.getAsyncRemote().sendText(message);
                }
            }
        }
    }
}