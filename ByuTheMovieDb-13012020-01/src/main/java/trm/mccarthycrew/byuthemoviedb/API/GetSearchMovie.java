
package trm.mccarthycrew.byuthemoviedb.API;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author tmccarthy
 */
public class GetSearchMovie {

    //https://api.themoviedb.org/3/search/movie?api_key=8e10a8a858acaf16686e7b866f9bd703&language=en-US&query=Jaws&page=1&include_adult=false
    public static final String API_KEY = "8e10a8a858acaf16686e7b866f9bd703";

    public static JsonArrayRequest SendRequest(String queryString, String apiKey, int page) throws Exception {
        JSONArray jsonArrayResults = null;
        int moviePage = 0;
        int movieTotalResults = 0;
        int movieTotalPages = 0;
        String responseErrorString = "";
        boolean responseError = false;
        String newQueryString = queryString.replaceAll(" ", "%20");
        String url = "https://api.themoviedb.org/3/search/movie?api_key=" + apiKey + "&language=en-US&query=" + newQueryString + "&page=" + page + "&include_adult=false";
        URL urlObj = new URL(url);
        System.out.println("\nSending GET request to URL: " + url);
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int respCode = con.getResponseCode();
        System.out.println("Response Code: " + respCode);
        if (respCode != 200) {
            responseError = true;
            responseErrorString = con.getResponseMessage();
        } else {
            StringBuffer respBuffer;
            try (BufferedReader inBufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                respBuffer = new StringBuffer();
                while ((inputLine = inBufferedReader.readLine()) != null) {
                    respBuffer.append(inputLine);
                }
            }

            System.out.println(respBuffer.toString());

            JSONObject jsonResponse = new JSONObject(respBuffer.toString());
            moviePage = jsonResponse.getInt("page");
            movieTotalResults = jsonResponse.getInt("total_results");
            movieTotalPages = jsonResponse.getInt("total_pages");
            System.out.println("Page: " + moviePage);
            System.out.println("Total Results: " + movieTotalResults);
            System.out.println("Total Pages: " + movieTotalPages);
            jsonArrayResults = jsonResponse.getJSONArray("results");            
        }

        return new JsonArrayRequest(jsonArrayResults, responseError, responseErrorString, respCode, moviePage, movieTotalResults, movieTotalPages);
    }

    public static void main(String[] args) {
        try {
            String query = "What about Bob";
            int page = 2;
            JsonArrayRequest movieArrayRequest = GetSearchMovie.SendRequest(query, API_KEY, page);
            JSONArray jsonArray = movieArrayRequest.getjSONArray();
            int arrayLength = jsonArray.length();
            if(arrayLength > 0){
                String get = jsonArray.getJSONObject(0).getString("overview");
                if (movieArrayRequest.isRequestError()) {
                    System.out.println("Error: " + movieArrayRequest.getRequestErrorString());
                } else {

                }
            }else{
                System.out.println("No Results on page " + page);
            }
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
