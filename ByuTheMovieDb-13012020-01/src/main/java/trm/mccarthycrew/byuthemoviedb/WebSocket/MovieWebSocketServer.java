
package trm.mccarthycrew.byuthemoviedb.WebSocket;

import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import trm.mccarthycrew.byuthemoviedb.API.GetSearchMovie;
import trm.mccarthycrew.byuthemoviedb.API.JsonArrayRequest;

/**
 *
 * @author tmccarthy
 */
@ApplicationScoped
@ServerEndpoint("/actions")
public class MovieWebSocketServer {

    @Inject
    private MovieSessionHandler sessionHandler;

    @OnOpen
    public void open(Session session) {
        sessionHandler.addSession(session);
        System.out.println("Session ID open: " + session.getId());
    }

    @OnClose
    public void close(Session session) {
        sessionHandler.removeSession(session);
        System.out.println("Session ID closed: " + session.getId());
    }

    @OnError
    public void onError(Throwable error) {
        Logger.getLogger(MovieWebSocketServer.class.getName()).log(Level.SEVERE, null, error);
    }

    //On Messages recieved by session (byuthemoviedb.js)
    @OnMessage
    public void handleMessage(String message, Session session) throws Exception {
        try (JsonReader reader = Json.createReader(new StringReader(message))) {
            JsonObject jsonMessage = reader.readObject();
            System.out.println("Mesage Recieved: " + jsonMessage.getString("action"));
            if ("searchMovies".equals(jsonMessage.getString("action"))) {
                String queryString = jsonMessage.getString("queryText");
                int page = jsonMessage.getInt("page");
                if (queryString == null || queryString.equals("")) {

                } else {
                    JsonArrayRequest sendMovieRequest = GetSearchMovie.SendRequest(queryString, GetSearchMovie.API_KEY, page);
                    sessionHandler.updateMovieSearch(sendMovieRequest);
                }
            } else {
                System.out.println("Unknown Action: " + jsonMessage.getString("action"));
            }
        }
    }
}
