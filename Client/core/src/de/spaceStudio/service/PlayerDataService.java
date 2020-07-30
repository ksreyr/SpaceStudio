package de.spaceStudio.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import de.spaceStudio.client.util.Global;
import de.spaceStudio.client.util.RequestUtils;

public class PlayerDataService {

    public void cleaningData(Object requestObject, String method) {
        final Json json = new Json();

        json.setOutputType(JsonWriter.OutputType.json);
        final String requestJson = json.toJson(requestObject);

        String url = Global.SERVER_URL + Global.PLAYER_CLEAN_ENDPOINT;
        final Net.HttpRequest request = RequestUtils.setupRequest(url, requestJson ,method);
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    System.out.println("Request Failed");
                }
                System.out.println("statusCode of data clean: " + statusCode);
                String responseJson = httpResponse.getResultAsString();
                try {
                    System.out.println("Response: " + responseJson);
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
    }
}
