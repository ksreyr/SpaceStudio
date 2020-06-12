package de.bremen.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

public class RegistrationService  {


    private boolean isValid;



    public boolean createUser(Object requestObject, String method) {

           final String createUserURL = "http://localhost:8080/player";
            final Json json = new Json();

            json.setOutputType(JsonWriter.OutputType.json);
            System.out.println("JSON to send " + json.toJson(requestObject));
            final String requestJson = json.toJson(requestObject);

            Net.HttpRequest request = new Net.HttpRequest(method);
            request.setUrl(createUserURL);

            request.setContent(requestJson);

            request.setHeader("Content-Type", "application/json");
            request.setHeader("Accept", "application/json");
            Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {

                public void handleHttpResponse(Net.HttpResponse httpResponse) {
                    int statusCode = httpResponse.getStatus().getStatusCode();
                    if (statusCode != HttpStatus.SC_OK) {
                        System.out.println("Request Failed");
                    }
                    System.out.println("statusCode: " + statusCode);
                    String responseJson = httpResponse.getResultAsString();
                    try {
                        System.out.println("Response: " + responseJson);
                        isValid = Boolean.parseBoolean(responseJson);

                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
                public void failed(Throwable t) {
                    System.out.println("Request Failed Completely");
                }

                @Override
                public void cancelled() {
                    System.out.println("request cancelled");
                }
            });

            return isValid;
        }


    }

