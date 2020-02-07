package com.oracle.babylon.Utils.helper;


import com.oracle.babylon.Utils.setup.utils.ConfigFileReader;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

//Rest assured imports
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


/**
 * Helper class where we create methods required to carry out api operations
 * Author : susgopal, visingsi
 */
public class APIRequest {

    private CloseableHttpClient client = HttpClients.createDefault();
    public static RequestSpecification httpRequest;
    public static Response response;
    public static JsonPath extractor;

    /**
     * Method to set the proxy to the HTTP Client
     */
    public void setHttpClient(){
        ConfigFileReader configFileReader = new ConfigFileReader();
        String proxyStr = configFileReader.getProxyURL();
        String proxyUrl = proxyStr.split(":")[0];
        int proxyPort = Integer.parseInt(proxyStr.split(":")[1]);
        Boolean checkProxy = configFileReader.getAPIProxySetStatus();
        if(checkProxy) {
            HttpHost proxy = new HttpHost(proxyUrl, proxyPort, "http");
            DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
            client = HttpClients.custom()
                    .setRoutePlanner(routePlanner)
                    .build();
        }
    }


    /**
     * HTTPClient and HTTP Get clients are used to create a method for constructing and executing GET API Request
     *
     * @param url           target server
     * @param authorization Header to gain access
     * @return response
     */
    public HttpResponse getRequest(String url, String authorization) {
        try {
            setHttpClient();
            HttpGet getRequest = new HttpGet(url);
            getRequest.setHeader("Authorization", authorization);
           // getRequest.setHeader("Cookie", "JSESSIONID=4E4620DED353CCE987DBCF28531E916A");
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
            setHttpClient();
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
            setHttpClient();
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

    /**
     * Function to execute http methods using rest assured code
     * @param url of the web service api
     * @param headersList headers to authenticate
     * @param body only specific to put and post methods
     * @return response of the api call
     */
    public Response execRequest(Method method, String url, List<Header> headersList, String body){
        httpRequest = RestAssured.given();
       return execRequest(httpRequest, method, url, headersList, body);
    }

    /**
     * Overloaded method to handle a custom httpRequest
     * @param httpRequest custom httprequest to handle multipart/mixed request body
     * @param method http method
     * @param url request url
     * @param headersList headers to authenticate
     * @param body only specific to put and post methods
     * @return response of the api call
     */
    public Response execRequest(RequestSpecification httpRequest, Method method, String url, List<Header> headersList, String body){

        Headers header = new Headers(headersList);
        httpRequest.headers(header);
        if((method.equals(Method.PUT) || method.equals(Method.POST)) && body != null){
            httpRequest.body(body);
        }
        response = httpRequest.request(method, url);
        return response;
    }
}
