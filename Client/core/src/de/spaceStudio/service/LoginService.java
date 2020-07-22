package de.spaceStudio.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import de.spaceStudio.client.util.Global;
import de.spaceStudio.client.util.RequestUtils;

import java.util.List;

import static de.spaceStudio.client.util.Global.currentPlayer;
import static de.spaceStudio.client.util.Global.playersOnline;

public class LoginService {

    public static void logout(Object requestObject) {

        Json json = new Json();

        json.setOutputType(JsonWriter.OutputType.json);

        final String requestJson = json.toJson(requestObject);

        Net.HttpRequest request = new Net.HttpRequest("POST");
        request.setUrl(Global.SERVER_URL + Global.PLAYER_LOGOUT_ENDPOINT);

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

    public static void fetchLoggedUsers() {
        Net.HttpRequest request = RequestUtils.setupRequest(Global.SERVER_URL + Global.PLAYER_LOGGED_ENDPOINT, "", Net.HttpMethods.GET);
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    System.out.println("Request Failed");
                }
                System.out.println("statusCode: " + statusCode);

                try {
                    List<String> dataFromServer = new Gson().fromJson(httpResponse.getResultAsString(), new TypeToken<List<String>>() {
                    }.getType());
                    playersOnline.clear();
                    for (String onlinePlayer : dataFromServer) {
                        if(currentPlayer != null) {
                            if (!currentPlayer.getName().equals(onlinePlayer)) {
                                playersOnline.add(onlinePlayer);
                            }
                        }else{
                            playersOnline.add(onlinePlayer);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
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
