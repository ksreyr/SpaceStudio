package de.spaceStudio.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import de.spaceStudio.client.util.Global;

public class SinglePlayerGameService {

    public static void initSinglePlayerGame(Object requestObject) {

        Json json = new Json();

        json.setOutputType(JsonWriter.OutputType.json);

        final String requestJson = json.toJson(requestObject);

        Net.HttpRequest request = new Net.HttpRequest("POST");
        request.setUrl(Global.SERVER_URL + "/game/start/single-player/" + Global.currentPlayer.getName());

        request.setContent(requestJson);

        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json");
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {

            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode != HttpStatus.SC_ACCEPTED) {
                    System.out.println("Request Failed");
                }
                System.out.println("statusCode: " + statusCode);
                String responseJson = httpResponse.getResultAsString();
                System.out.println(responseJson);
            }
            public void failed(Throwable t) {
                System.out.println("Request Failed Completely");
            }

            @Override
            public void cancelled() {
                System.out.println("request cancelled");
            }
        });
    }

}
