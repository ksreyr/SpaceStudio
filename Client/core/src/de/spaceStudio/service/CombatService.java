package de.spaceStudio.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import de.spaceStudio.client.util.Global;

public class CombatService {
    String valid;
    public String makeAShot(Object requestObject, String method) {

        final Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        final String requestJson = json.toJson(requestObject);
        Net.HttpRequest request = new Net.HttpRequest(method);
        final String url = Global.SERVER_URL + Global.MAKE_SHOT_VALIDATION;
        request.setUrl(url);
        request.setContent(requestJson);
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json");
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    System.out.println("Request Failed makeAShot");
                }
                System.out.println("statusCode makeAShot: " + statusCode);
                String responseJson = httpResponse.getResultAsString();

            }
            public void failed(Throwable t) {
                System.out.println("Request Failed Completely");
            }
            @Override
            public void cancelled() {
                System.out.println("request cancelled");
            }
        });
        return valid;
    }
    public String shipUser(Object requestObject, String method) {

        final Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        final String requestJson = json.toJson(requestObject);
        Net.HttpRequest request = new Net.HttpRequest(method);
        final String url = Global.SERVER_URL + Global.MAKE_SHOT_VALIDATION;
        request.setUrl(url);
        request.setContent(requestJson);
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json");
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    System.out.println("Request Failed makeAShot");
                }
                System.out.println("statusCode makeAShot: " + statusCode);
                String responseJson = httpResponse.getResultAsString();

            }
            public void failed(Throwable t) {
                System.out.println("Request Failed Completely");
            }
            @Override
            public void cancelled() {
                System.out.println("request cancelled");
            }
        });
        return valid;
    }
    public String shipGegner(Object requestObject, String method) {

        final Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        final String requestJson = json.toJson(requestObject);
        Net.HttpRequest request = new Net.HttpRequest(method);
        final String url = Global.SERVER_URL + Global.MAKE_SHOT_VALIDATION;
        request.setUrl(url);
        request.setContent(requestJson);
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json");
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    System.out.println("Request Failed makeAShot");
                }
                System.out.println("statusCode makeAShot: " + statusCode);
                String responseJson = httpResponse.getResultAsString();

            }
            public void failed(Throwable t) {
                System.out.println("Request Failed Completely");
            }
            @Override
            public void cancelled() {
                System.out.println("request cancelled");
            }
        });
        return valid;
    }
    public String weapon(Object requestObject, String method) {

        final Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        final String requestJson = json.toJson(requestObject);
        Net.HttpRequest request = new Net.HttpRequest(method);
        final String url = Global.SERVER_URL + Global.MAKE_SHOT_VALIDATION;
        request.setUrl(url);
        request.setContent(requestJson);
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json");
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    System.out.println("Request Failed makeAShot");
                }
                System.out.println("statusCode makeAShot: " + statusCode);
                String responseJson = httpResponse.getResultAsString();

            }
            public void failed(Throwable t) {
                System.out.println("Request Failed Completely");
            }
            @Override
            public void cancelled() {
                System.out.println("request cancelled");
            }
        });
        return valid;
    }
}
