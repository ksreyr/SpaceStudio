package de.spaceStudio.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import de.spaceStudio.client.util.Global;

public class InitialDataGameService {
    String response;
    String valid;
    public String sendRequestAddShip(Object requestObject, String method) {
        final Json json = new Json();

        json.setOutputType(JsonWriter.OutputType.json);
        final String requestJson = json.toJson(requestObject);

        final Net.HttpRequest request = new Net.HttpRequest(method);
        final String url = Global.SERVER_URL + Global.SHIP_CREATION_ENDPOINT;
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
                    response = responseJson.toString();
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
        return response;
    }

    public void sendRequestAddSection(Object requestObject, String method) {
        final Json json = new Json();

        json.setOutputType(JsonWriter.OutputType.json);
        final String requestJson = json.toJson(requestObject);
        System.out.println(requestJson);
        Net.HttpRequest request = new Net.HttpRequest(method);
        final String url = Global.SERVER_URL + Global.SECTION_CREATION_ENDPOINT;
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
    public void sendRequestAddPlanet(Object requestObject, String method) {
        final Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        final String requestJson = json.toJson(requestObject);
        Net.HttpRequest request = new Net.HttpRequest(method);
        final String url = Global.SERVER_URL + Global.PLANET_CREATION_ENDPOINT;
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
                System.out.println("statusCode AddPLanet: " + statusCode);
                String responseJson = httpResponse.getResultAsString();
                try {
                    System.out.println("Response: " + responseJson);
                    //isValid = Boolean.parseBoolean(responseJson);
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

    public void sendRequestAddUniverse(Object requestObject, String method) {
        final Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        final String requestJson = json.toJson(requestObject);
        Net.HttpRequest request = new Net.HttpRequest(method);
        final String url = Global.SERVER_URL + Global.UNIVERSE_CREATION_ENDPOINT;
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
                System.out.println("statusCode AddUniverise: " + statusCode);
                String responseJson = httpResponse.getResultAsString();
                try {
                    System.out.println("Response: " + responseJson);
                    //isValid = Boolean.parseBoolean(responseJson);
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

    public void sendRequestAddCrew(Object requestObject, String method) {
        final Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        final String requestJson = json.toJson(requestObject);
        Net.HttpRequest request = new Net.HttpRequest(method);
        final String url = Global.SERVER_URL + Global.CREWMEMBER_CREATION_ENDPOINT;
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
                    //isValid = Boolean.parseBoolean(responseJson);
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
    public String validatedName(Object requestObject, String method) {

        final Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        final String requestJson = json.toJson(requestObject);
        Net.HttpRequest request = new Net.HttpRequest(method);
        final String url = Global.SERVER_URL + Global.NAME_VALIDATION;
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
                    valid = responseJson;

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
        return valid;
    }
}
