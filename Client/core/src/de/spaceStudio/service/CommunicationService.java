package de.spaceStudio.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import de.spaceStudio.client.util.Global;

public class CommunicationService {


    int count=0;

    boolean isValid;

    public boolean sendRequest(Object requestObject, String method) {
        final Json json = new Json();

        json.setOutputType(JsonWriter.OutputType.json);
        System.out.println("JSON to send " + json.toJson(requestObject));
        final String requestJson = json.toJson(requestObject);

        Net.HttpRequest request = new Net.HttpRequest(method);
        final String url = Global.SERVER_URL + Global.PLAYER_LOGIN_ENDPOINT;
        request.setUrl(url);

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
