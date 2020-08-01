package de.spaceStudio.client.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.spaceStudio.server.model.*;

import java.util.List;
import java.util.logging.Logger;

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

    public static void genericRequest(String url, boolean shipRequest, Integer id, String method, Object payload) {
        ObjectMapper objectMapper = new ObjectMapper();
        Net.HttpRequest r = null;
        try {
            if (!payload.equals("")) {
                r = setupRequest(url, objectMapper.writeValueAsString(payload), method);
            } else {
                r = setupRequest(url, "", method);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        final String[] responseString = {null};

        LOG.info("Sending get Request to: " + url);
        Gdx.net.sendHttpRequest(r, new Net.HttpResponseListener() {
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int statusCode = httpResponse.getStatus().getStatusCode();
                LOG.info("statusCode: " + statusCode);
                responseString[0] = httpResponse.getResultAsString();

                if (url.contains("sections")) {
                    try {
                        Global.combatSections.put(id, objectMapper.readValue(responseString[0], new TypeReference<List<Section>>() {
                        }));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                } else if (url.contains("weapon")) {
                    try {
                        Global.combatWeapons.put(id, objectMapper.readValue(responseString[0], new TypeReference<List<Weapon>>() {
                        }));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                } else if (url.contains("crewMembers")) {
                    try {
                        List<CrewMember> crewMembers = objectMapper.readValue(responseString[0], new TypeReference<List<CrewMember>>() {
                        });
                        Global.combatCrew.put(id, crewMembers);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                } else if (url.contains(Global.CAN_LAND)) {
                    try {
                        Global.allReady = objectMapper.readValue(responseString[0], new TypeReference<Boolean>() {
                        });
                        if (Global.allReady) {
                            // Multiplayer Step 3
                            isMultiplayerFight();
                        }
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                } else if (shipRequest) {
                    try {
                        if (!responseString[0].equals("")) {
                            Global.currentShipPlayer = objectMapper.readValue(responseString[0], new TypeReference<Ship>() {
                            });
                    }
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                } else if (url.contains("actor")) {
                    try {
                        Actor actor = objectMapper.readValue(responseString[0], new TypeReference<Player>() {
                        });
                        if (actor.getState().getFightState().equals(FightState.WAITING_FOR_TURN) && method.equals("PUT")) {
                            if (Global.combatWeapons.size() == 2 && Global.combatSections.size() == 2 &&
                                    Global.combatWeapons.get(Global.currentShipGegner.getId()).size() > 0
                                    && Global.combatSections.get(Global.currentShipPlayer.getId()).size() > 0) { // Es muss gegner mit Waffne geben
                                Weapon w = Global.combatWeapons.get(Global.currentShipGegner.getId()).get(0);
                                w.setObjectiv(Global.combatSections.get(Global.currentShipPlayer.getId()).get(0));
                                endTurnRequestSinglePlayer(w);
                                crewMemeberByShip(Global.currentShipPlayer);
                            } else {
                                LOG.warning("There appears to be a problem with the global Maps. Please check if you always use the correct id");
                            }
                        }
                        Global.combatActors.put(id, actor);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                } else if (url.contains(Global.END_ROUND_SINGLE)) {
                    try {
                        List<Weapon> weaponsWhichHaveShot = objectMapper.readValue(responseString[0], new TypeReference<List<Weapon>>() {
                        });
                        Global.weaponsToProcess.addAll(weaponsWhichHaveShot);
                        getActor(Global.currentPlayer);
                        getShip(Global.currentShipPlayer);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                } else if (url.contains(Global.ROUNDS)) {
                    try {
                        Global.playerRounds = objectMapper.readValue(responseString[0], new TypeReference<List<GameRound>>() {
                        });
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                } else if (url.contains(Global.MULTIPLAYER + Global.FIGHT)) {
                    try {
                        Global.isOnlineFight = objectMapper.readValue(responseString[0], new TypeReference<Boolean>() {
                        });
                        if (Global.isOnlineFight) {
                            // Step Multiplayer a4 fight
                            getEnemyShipMultiplayer();
                        } else {
                            // Step Multiplayer a4 no Fight
                            Global.loadingFightLocation = false;
                        }
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                } else if (url.contains(Global.MULTIPLAYER + Global.ENEMYSHIP)) {
                    try {
                        // Multiplayer Step 5
                        Global.currentShipGegner = objectMapper.readValue(responseString[0], new TypeReference<Ship>() {
                        });
                        Global.currentGegner = Global.currentShipGegner.getOwner();

                        // Multiplayer Step 6
                        Global.loadingFightLocation  = false;
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void failed(Throwable t) {
                LOG.severe("Request Failed");
            }

            @Override
            public void cancelled() {
                LOG.severe("Request Canceled");
            }
        });

    }

    public static void weaponsByShip(Ship ship) {
        genericRequest(Global.SERVER_URL + Global.ASK_FOR_SHIP + "/" + ship.getId() + "/" + Global.WEAPONS,
                false, ship.getId(), Net.HttpMethods.GET, "");
    }

    public static void sectionsByShip(Ship ship) {
        genericRequest(Global.SERVER_URL + Global.ASK_FOR_SHIP + "/" + ship.getId() + "/" + Global.SECTIONS,
                false, ship.getId(), Net.HttpMethods.GET, "");
    }

    public static void crewMemeberByShip(Ship ship) {
        genericRequest(Global.SERVER_URL + Global.ASK_FOR_SHIP + "/" + ship.getId() + "/" + Global.CREWMEMBERS,
                false, ship.getId(), Net.HttpMethods.GET, "");
    }

    public static void hasLanded(Player player) {
        genericRequest(Global.SERVER_URL + Global.HAS_LANDED,
                false, 0, Net.HttpMethods.POST, player);
    }

    public static void canLand(Player player) {
        genericRequest(Global.SERVER_URL + Global.CAN_LAND + "/" + player.getId() ,
                false, 0, Net.HttpMethods.GET, "");
    }

    public static void getShip(Ship ship) {
        genericRequest(Global.SERVER_URL + Global.ASK_FOR_SHIP + "/" + ship.getId(),
                true, ship.getId(), Net.HttpMethods.GET, "");
    }

    public static void getActor(Actor actor) {
        genericRequest(Global.SERVER_URL + Global.ACTOR_ENDPOINT + "/" + actor.getId(), false,
                actor.getId(), Net.HttpMethods.GET, "");
    }

    public static void setActor(Actor actor) {
        genericRequest(Global.SERVER_URL + Global.ACTOR_ENDPOINT, false, actor.getId(),
                Net.HttpMethods.PUT, actor);
    }

    public static void updateEnergie(List<Section> sectionsToUpdate) {
        genericRequest(Global.SERVER_URL + "/" + Global.SECTIONS + Global.ENERGY, false,
                Global.currentShipPlayer.getId(),
                Net.HttpMethods.POST, sectionsToUpdate);
    }

    public static void endTurnRequestSinglePlayer(Weapon w) {
        genericRequest(Global.SERVER_URL + Global.GAME + Global.END_ROUND_SINGLE, false,
                Global.currentShipPlayer.getId(),
                Net.HttpMethods.POST, w);
    }

    public static void upgradeWeapon(List<Weapon> weapons) {
        genericRequest(Global.SERVER_URL + Global.WEAPON_CREATION_ENDPOINT, false,
                Global.currentShipPlayer.getId(), Net.HttpMethods.PUT, weapons);
    }

    public static void updateShip(Ship ship) {
        genericRequest(Global.SERVER_URL + Global.SHIP_ENDPOINT, true, ship.getId(), Net.HttpMethods.PUT, ship);
    }

    public static void findGameRoundsByActor(Actor actor) {
        genericRequest(Global.SERVER_URL + Global.PLAYER_ENDPOINT + "/" + actor.getId() + Global.ROUNDS, false, actor.getId(), Net.HttpMethods.GET, "");
    }

    public static void isMultiplayerFight() {
        genericRequest(Global.SERVER_URL + Global.MULTIPLAYER + Global.FIGHT + "/" + Global.multiPlayerSessionID,
                false, 0, Net.HttpMethods.GET, "")
        ;
    }
    public static void getEnemyShipMultiplayer() {
        genericRequest(Global.SERVER_URL + Global.MULTIPLAYER + Global.ENEMYSHIP + "/" + Global.multiPlayerSessionID,
                false ,0, Net.HttpMethods.GET, Global.currentPlayer );
    }

}
