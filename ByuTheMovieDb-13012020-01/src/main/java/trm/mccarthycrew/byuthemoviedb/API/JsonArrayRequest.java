/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trm.mccarthycrew.byuthemoviedb.API;

import org.json.JSONArray;

/**
 *
 * @author tmsqu
 */
public class JsonArrayRequest {
    private final JSONArray jSONArray;
    private final boolean requestError;
    private final String requestErrorString;
    private final int page;
    private final int totalResults;
    private final int totalPages;
    private final int respondeCode;

    public JsonArrayRequest(JSONArray jSONArray, boolean requestError, String requestErrorString, int respondeCode, int page, int totalResults, int totalPages) {
        this.jSONArray = jSONArray;
        this.requestError = requestError;
        this.requestErrorString = requestErrorString;
        this.page = page;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
        this.respondeCode = respondeCode;
        
    }

    public JSONArray getjSONArray() {
        return jSONArray;
    }

    public boolean isRequestError() {
        return requestError;
    }

    public String getRequestErrorString() {
        return requestErrorString;
    }

    public int getPage() {
        return page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getRespondeCode() {
        return respondeCode;
    }
    
    
    
}
