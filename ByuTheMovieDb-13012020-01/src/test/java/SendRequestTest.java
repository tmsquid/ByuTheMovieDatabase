/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import trm.mccarthycrew.byuthemoviedb.API.GetSearchMovie;
import trm.mccarthycrew.byuthemoviedb.API.JsonArrayRequest;

/**
 *
 * @author tmsqu
 */
public class SendRequestTest {

    private static final String GOOD_API_KEY = GetSearchMovie.API_KEY;
    private static final String BAD_API_KEY = "THISISBADAPIKEY";

    private static final String UNAUTHORIZED_ERROR = "Unauthorized";

    public SendRequestTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void SendRequestTest001() throws Exception {
        String test001Query = "Star Wars";
        int test001Page = 1;
        JsonArrayRequest sendRequest = GetSearchMovie.SendRequest(test001Query, GOOD_API_KEY, test001Page);
        assertFalse(sendRequest.isRequestError());
        assertTrue(sendRequest.getRespondeCode() == 200);
        assertTrue(sendRequest.getPage() == 1);
        assertTrue(sendRequest.getTotalResults() >= 152);
        assertTrue(sendRequest.getTotalPages() >= 2);
    }

    @Test
    public void SendRequestTest002() throws Exception {
        String test001Query = "Star Wars";
        int test001Page = 1;
        JsonArrayRequest sendRequest = GetSearchMovie.SendRequest(test001Query, BAD_API_KEY, test001Page);
        assertTrue(sendRequest.isRequestError());
        assertTrue(sendRequest.getRequestErrorString().contains(UNAUTHORIZED_ERROR));
        assertTrue(sendRequest.getRespondeCode() == 401);
    }

    @Test
    public void SendRequestTest003() throws Exception {
        String test001Query = "Star Wars";
        int test001Page = 0;
        JsonArrayRequest sendRequest = GetSearchMovie.SendRequest(test001Query, GOOD_API_KEY, test001Page);
        assertTrue(sendRequest.isRequestError());
        assertTrue(sendRequest.getRequestErrorString().contains("Unknown"));
        assertTrue(sendRequest.getRespondeCode() == 422);
    }

    @Test
    public void SendRequestTest004() throws Exception {
        String test001Query = "@#@##$#$";
        int test001Page = 1;
        JsonArrayRequest sendRequest = GetSearchMovie.SendRequest(test001Query, GOOD_API_KEY, test001Page);
        assertFalse(sendRequest.isRequestError());
        assertTrue(sendRequest.getTotalResults() == 0);
        assertTrue(sendRequest.getTotalPages() == 0);
        assertTrue(sendRequest.getPage() == 1);
//        assertTrue(sendRequest.getRequestErrorString().contains("Unknown"));
        assertTrue(sendRequest.getRespondeCode() == 200);
    }
    
     @Test
    public void SendRequestTest005() throws Exception {
        String test001Query = "What about Bob";
        int test001Page = 1;
        JsonArrayRequest sendRequest = GetSearchMovie.SendRequest(test001Query, GOOD_API_KEY, test001Page);
        assertFalse(sendRequest.isRequestError());
        assertTrue(sendRequest.getRespondeCode() == 200);
        assertTrue(sendRequest.getPage() == 1);
        assertTrue(sendRequest.getTotalResults() == 1);
        assertTrue(sendRequest.getTotalPages() >= 1);
    }

    
    @Test
    public void SendRequestTest006() throws Exception {
        String test001Query = "What about Bob";
        int test001Page = 2;
        JsonArrayRequest sendRequest = GetSearchMovie.SendRequest(test001Query, GOOD_API_KEY, test001Page);
        assertFalse(sendRequest.isRequestError());
        assertTrue(sendRequest.getRespondeCode() == 200);
        assertTrue(sendRequest.getPage() == 2);
        assertTrue(sendRequest.getTotalResults() == 1);
        assertTrue(sendRequest.getTotalPages() >= 1);
        assertTrue(sendRequest.getjSONArray().length() == 0);
        
    }
}
