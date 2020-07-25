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
    String responseJson;

    public String sendRequestAddShip(Object requestObject, String method) {
        final Json json = new Json();

        json.setOutputType(JsonWriter.OutputType.json);
        final String requestJson = json.toJson(requestObject);

        final Net.HttpRequest request = new Net.HttpRequest(method);
        final String url = Global.SERVER_URL + Global.SHIP_ENDPOINT;
        request.setUrl(url);
        request.setContent(requestJson);
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json");
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    System.out.println("Request Failed sendRequestAddShip");
                    responseJson = httpResponse.getResultAsString();
                }
                System.out.println("statusCode sendRequestAddShip: " + statusCode);
            }

            public void failed(Throwable t) {
                System.out.println("Request Failed Completely");
            }

            @Override
            public void cancelled() {
                System.out.println("request cancelled");
            }
        });

        return responseJson;
    }

    public void sendRequestAddSection(Object requestObject, String method) {
        final Json json = new Json();

        json.setOutputType(JsonWriter.OutputType.json);
        final String requestJson = json.toJson(requestObject);
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
                    System.out.println("Request Failed sendRequestAddSection");
                }
                System.out.println("statusCode sendRequestAddSection: " + statusCode);
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
                    System.out.println("Request Failed AddPLanet");
                }
                System.out.println("statusCode AddPLanet: " + statusCode);
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
    }

    public void sendRequestAddStation(Object requestObject, String method) {
        final Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        final String requestJson = json.toJson(requestObject);
        Net.HttpRequest request = new Net.HttpRequest(method);
        final String url = Global.SERVER_URL + Global.STATION_CREATION_ENDPOINT;
        request.setUrl(url);
        request.setContent(requestJson);
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json");
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    System.out.println("Request Failed AddStation");
                }
                System.out.println("statusCode AddStation: " + statusCode);
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
                    System.out.println("Request Failed AddUniverise");
                }
                System.out.println("statusCode AddUniverise: " + statusCode);
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
                    System.out.println("Request Failed sendRequestAddCrew");
                }
                System.out.println("statusCode sendRequestAddCrew: " + statusCode);
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
    }

    public void sendRequestAddWeapon(Object requestObject, String method) {
        final Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        final String requestJson = json.toJson(requestObject);
        Net.HttpRequest request = new Net.HttpRequest(method);
        final String url = Global.SERVER_URL + Global.WEAPON_CREATION_ENDPOINT;
        request.setUrl(url);
        request.setContent(requestJson);
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json");
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    System.out.println("Request Failed sendRequestAddWeapon");
                }
                System.out.println("statusCode sendRequestAddWeapon: " + statusCode);
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
                    System.out.println("Request Failed validatedName");
                }
                System.out.println("statusCode validatedName: " + statusCode);
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

    public void sendRequestAddShipRessource(Object requestObject, String method) {
        final Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        final String requestJson = json.toJson(requestObject);
        Net.HttpRequest request = new Net.HttpRequest(method);
        final String url = Global.SERVER_URL + Global.RESSOURCE_SHIP_CREATION_ENDPOINT;
        request.setUrl(url);
        request.setContent(requestJson);
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json");
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    System.out.println("Request Failed AddShipRessource");
                }
                System.out.println("statusCode AddShipRessource: " + statusCode);
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
    }

    public void sendRequestAddShopRessource(Object requestObject, String method) {
        final Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        final String requestJson = json.toJson(requestObject);
        Net.HttpRequest request = new Net.HttpRequest(method);
        final String url = Global.SERVER_URL + Global.RESSOURCE_SHOP_CREATION_ENDPOINT;
        request.setUrl(url);
        request.setContent(requestJson);
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json");
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    System.out.println("Request Failed AddShopRessource");
                }
                System.out.println("statusCode AddShopRessource: " + statusCode);
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
    }

    public String aiCreation(Object requestObject, String method) {

        final Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        final String requestJson = json.toJson(requestObject);
        Net.HttpRequest request = new Net.HttpRequest(method);
        final String url = Global.SERVER_URL + Global.AI_CREATION_ENDPOINT;
        request.setUrl(url);
        request.setContent(requestJson);
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "application/json");
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    System.out.println("Request Failed aiCreation");
                }
                System.out.println("statusCode of aiCreation: " + statusCode);
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
