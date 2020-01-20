package trm.mccarthycrew.byuthemoviedb.WebSocket;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.spi.JsonProvider;
import javax.websocket.Session;
import org.json.JSONException;
import trm.mccarthycrew.byuthemoviedb.API.JsonArrayRequest;

/**
 *
 * @author tmccarthy
 */
@ApplicationScoped
public class MovieSessionHandler {

    private final Set<Session> sessions = new HashSet<>();

    public void addSession(Session session) {
        sessions.add(session);
    }

    public void removeSession(Session session) {
        sessions.remove(session);
    }

    private void sendToAllConnectedSessions(JsonObject message) {
        for (Session session : sessions) {
            sendToSession(session, message);
        }
    }

    private void sendToSession(Session session, JsonObject message) {
        try {
            session.getBasicRemote().sendText(message.toString());
        } catch (IOException ex) {
            sessions.remove(session);
            Logger.getLogger(MovieSessionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateMovieSearch(JsonArrayRequest jsonArrayRequest, Session session) {
        JsonProvider jsonProvider = JsonProvider.provider();
        JsonObject updateMovieSearch;
        if (!jsonArrayRequest.isRequestError()) {
            updateMovieSearch = createMovieSearchJsonObject(jsonProvider, jsonArrayRequest);
        } else {
            updateMovieSearch = jsonProvider.createObjectBuilder()
                    .add("action", "movieSearchUpdate")
                    .add("moviesFound", 0)
                    .add("error", true)
                    .add("errorMsg", jsonArrayRequest.getRequestErrorString())
                    .build();
        }
        sendToSession(session, updateMovieSearch);
//        sendToAllConnectedSessions(updateMovieSearch);
    }

    private JsonObject createMovieSearchJsonObject(JsonProvider provider, JsonArrayRequest jsonArrayRequest) {
        //popularity, id, vote_count, title, release_date, overview, poster_path
        int jsonArrayLength = jsonArrayRequest.getjSONArray().length();
        JsonObjectBuilder movieSearchJsonObjectBuilder = provider.createObjectBuilder();
        movieSearchJsonObjectBuilder.add("action", "movieSearchUpdate");
        movieSearchJsonObjectBuilder.add("moviesFound", jsonArrayRequest.getTotalResults());
        movieSearchJsonObjectBuilder.add("error", false);
        movieSearchJsonObjectBuilder.add("page", jsonArrayRequest.getPage());
        movieSearchJsonObjectBuilder.add("totalPage", jsonArrayRequest.getTotalPages());
        movieSearchJsonObjectBuilder.add("movieOnPage", jsonArrayRequest.getjSONArray().length());
        
        //Arrays        
        //popularity
        JsonArrayBuilder popularityArrayBuilder = Json.createArrayBuilder();
        for (int i = 0; i < jsonArrayLength; i++) {
            int popularity = jsonArrayRequest.getjSONArray().getJSONObject(i).getInt("popularity");
            popularityArrayBuilder.add(popularity);
        }
        movieSearchJsonObjectBuilder.add("popularitys", popularityArrayBuilder);
        
        //id
        JsonArrayBuilder idArrayBuilder = Json.createArrayBuilder();
        for (int i = 0; i < jsonArrayLength; i++) {
            int id = jsonArrayRequest.getjSONArray().getJSONObject(i).getInt("id");
            idArrayBuilder.add(id);
        }
        movieSearchJsonObjectBuilder.add("ids", idArrayBuilder);
        
        //vote_count
        JsonArrayBuilder voteCountArrayBuilder = Json.createArrayBuilder();
        for (int i = 0; i < jsonArrayLength; i++) {
            int voteCount = jsonArrayRequest.getjSONArray().getJSONObject(i).getInt("vote_count");
            voteCountArrayBuilder.add(voteCount);
        }
        movieSearchJsonObjectBuilder.add("vote_counts", voteCountArrayBuilder);

        //title
        JsonArrayBuilder titleArrayBuilder = createJsonArrayBuilderString(jsonArrayLength, jsonArrayRequest, "title");
        movieSearchJsonObjectBuilder.add("titles", titleArrayBuilder);
        
        //release_date
        JsonArrayBuilder releaseDateArrayBuilder = createJsonArrayBuilderString(jsonArrayLength, jsonArrayRequest, "release_date");
        movieSearchJsonObjectBuilder.add("release_dates", releaseDateArrayBuilder);

        //overview        
        JsonArrayBuilder overviewArrayBuilder = createJsonArrayBuilderString(jsonArrayLength, jsonArrayRequest, "overview");
        movieSearchJsonObjectBuilder.add("overviews", overviewArrayBuilder);

        //poster_path
        JsonArrayBuilder posterPathArrayBuilder = createJsonArrayBuilderString(jsonArrayLength, jsonArrayRequest, "poster_path");
        movieSearchJsonObjectBuilder.add("poster_paths", posterPathArrayBuilder);

        return movieSearchJsonObjectBuilder.build();

    }
    
    private JsonArrayBuilder createJsonArrayBuilderString(int arrayCnt, JsonArrayRequest jsonArrayRequest, String nameString){
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for (int i = 0; i < arrayCnt; i++) {
            Object rdObject = null;
            try {
                rdObject = jsonArrayRequest.getjSONArray().getJSONObject(i).get(nameString);
            } catch (JSONException je) {
            }
            String valString = "";
            if (rdObject != null && rdObject instanceof String) {
                valString = jsonArrayRequest.getjSONArray().getJSONObject(i).getString(nameString);                
            }
            jsonArrayBuilder.add(valString);
        }
        return jsonArrayBuilder;
    }
}
