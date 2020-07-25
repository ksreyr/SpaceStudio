package de.spaceStudio.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import de.spaceStudio.client.util.Global;

import java.util.logging.Logger;

import static de.spaceStudio.client.util.RequestUtils.setupRequest;

public class SinglePlayerGameService {

    private final static Logger LOG = Logger.getLogger(SinglePlayerGameService.class.getName());

    /**
     * Send request to start single player Game in Server
     *
     * @param requestObject to send
     */
    public static void initSinglePlayerGame(Object requestObject) {

        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        final String requestJson = json.toJson(requestObject);
        String url = Global.SERVER_URL + "/game/start/single-player/" + Global.currentPlayer.getName();

        Net.HttpRequest request = setupRequest(url, requestJson, Net.HttpMethods.POST);

        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {

            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode != HttpStatus.SC_ACCEPTED) {
                    LOG.info("Request Failed");
                }
                LOG.info("statusCode: " + statusCode);
                String responseJson = httpResponse.getResultAsString();
                LOG.info(responseJson);
            }

            public void failed(Throwable t) {
                LOG.warning("Request Failed Completely");
            }

            @Override
            public void cancelled() {
                LOG.warning("request cancelled");
            }
        });
    }

}
