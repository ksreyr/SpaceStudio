package de.spaceStudio.client.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.google.gson.Gson;
import de.spaceStudio.screens.MenuScreen;
import de.spaceStudio.server.handler.SinglePlayerGame;
import de.spaceStudio.server.model.Ship;
import de.spaceStudio.server.model.Weapon;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import static de.spaceStudio.client.util.Global.currentPlayer;
import static de.spaceStudio.client.util.Global.singlePlayerGame;

public final class RequestUtils {

    private final static Logger LOG = Logger.getLogger(RequestUtils.class.getName());

    /**
     * Prepares the headers and other configurations
     *
     * @param url
     * @param payload
     * @param httpMethod
     * @return
     */
    public static Net.HttpRequest setupRequest(String url, String payload, String httpMethod) {
        Net.HttpRequest request = new Net.HttpRequest(httpMethod);
        request.setTimeOut(0);
        request.setUrl(url);
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json");
        request.setContent(payload);
        return request;
    }

    public static String genericGetRequest(String url) {
        Net.HttpRequest r = setupRequest(url, "", Net.HttpMethods.GET);

        final String[] responseString = {null};
        Gdx.net.sendHttpRequest(r, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                LOG.info("statusCode: " + statusCode);
                responseString[0] = httpResponse.getResultAsString();

            }

            @Override
            public void failed(Throwable t) {
            }

            @Override
            public void cancelled() {
                LOG.severe("Request Canceled");
            }
        });

//        while (responseString[0] == null) {
//            // NOP for locking  // FIXME load this
//        }
        return responseString[0];
    }

    public static List<Weapon> weaponsByShip(Ship ship) {
        String response = genericGetRequest(Global.ASK_FOR_SHIP +"/" + ship.getId() + "/" + Global.WEAPONS);
        Gson gson = new Gson();
        return Arrays.asList(gson.fromJson(response, Weapon[].class));
    }

}
