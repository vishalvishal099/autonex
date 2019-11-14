package com.oracle.babylon.Utils.helper;


import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.Base64;

/**
 * Helper class where we create functions required to carry out api operations
 */
public class APIRequest {

    private HttpClient client = new DefaultHttpClient();

    /**
     * HTTPClient and HTTP Get clients are used to create a method for constructing and executing GET API Request
     *
     * @param url           target server
     * @param authorization Header to gain access
     * @return response
     */
    public HttpResponse getRequest(String url, String authorization) {
        try {

            HttpGet getRequest = new HttpGet(url);
            getRequest.setHeader("Authorization", authorization);
            return client.execute(getRequest);
        } catch (ClientProtocolException cpe) {
            System.out.println("Error in client protocol");
            cpe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * HTTPClient and HTTP Post clients are used to create a method for constructing and executing POST API Request
     *
     * @param url           target server
     * @param authorization Header to gain access
     * @param body          request body that needs to be sent
     * @return response
     */
    public HttpResponse postRequest(String url, String authorization, String body) {
        return postRequest(url, authorization, "application/json", body);
    }

    /**
     * HTTPClient and HTTP Post clients are used to create a method for constructing and executing POST API Request
     *
     * @param url           target server
     * @param authorization Header to gain access
     * @param body          request body that needs to be sent
     * @param contentType   the format of the request body
     * @return response
     */

    public HttpResponse postRequest(String url, String authorization, String contentType, String body) {
        try {
            HttpPost postRequest = new HttpPost(url);
            postRequest.setHeader("Authorization", authorization);
            postRequest.setHeader("Content-Type", contentType);
            StringEntity stringEntity = new StringEntity(body);
            postRequest.getRequestLine();
            postRequest.setEntity(stringEntity);
            return client.execute(postRequest);
        } catch (ClientProtocolException cpe) {
            System.out.println("Error in client protocol");
            cpe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * HTTPClient and HTTP Delete clients are used to create a method for constructing and executing DELETE API Request
     *
     * @param url           target server
     * @param authorization Header to gain access
     * @return response
     */
    public HttpResponse deleteRequest(String url, String authorization) {
        try {
            HttpDelete deleteRequest = new HttpDelete(url);
            deleteRequest.setHeader("Authorization", authorization);
            return client.execute(deleteRequest);
        } catch (ClientProtocolException cpe) {
            System.out.println("Error in client protocol");
            cpe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Convert username and password to a 64 bit string
     *
     * @param username Value is fetched from config file
     * @param password Value is fetched from config file
     * @return encoded string
     */
    public String base64Encoder(String username, String password) {
        String authString = username + ":" + password;
        Base64.Encoder encoder = Base64.getEncoder();
        String encodedString = encoder.encodeToString(authString.getBytes());
        return encodedString;

    }

    /**
     * Function to create the basic auth token string
     *
     * @param username Value is fetched from config file
     * @param password Value is fetched from config file
     * @return basic auth token string
     */
    public String basicAuthGenerator(String username, String password) {
        String authorization = "Basic " + base64Encoder(username, password);
        if (authorization != null) {
            return authorization;
        }
        return null;
    }
}
