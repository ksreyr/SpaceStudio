package de.spaceStudio.client.util;

import de.spaceStudio.server.handler.SinglePlayerGame;
import de.spaceStudio.server.model.*;

import java.util.*;

/**
 * This class contains global variables, they can be access in the whole project
 *
 * @author Miguel Caceres
 * created on 6.17.2020
 */
public class Global {


    public static final String WEAPONS = "weapons";
    public static final String SECTIONS = "sections";
    public static final String CREWMEMBERS = "crewMembers";
    public static final String FIGHT_STATE = "/fightState";
    public static final String GAME = "/game";
    public static final String ENERGY = "/energy";
    public static final String ACTOR_ENDPOINT = "/actor";
    public static final String END_ROUND_SINGLE = "/endSingleRound";
    public static final String FIGHT_ENDPOINT = "/game";
    /**
     * Server player endpoint
     */
    public static final String PLAYER_ENDPOINT = "/player";
    //public static String SERVER_URL = "http://192.168.178.106:8080";
    /**
     * Server logged player endpoint
     */
    public static final String PLAYER_LOGGED_ENDPOINT = "/player/logged-players";
    /**
     *
     */
    public static final String MULTIPLAYER_INIT = "/game/start/multiplayer";
    public static final String MULTIPLAYER_GET_PLAYERS = "/player/multiplayer-list";
    public static final String MULTIPLAYER_LOGOUT = "/player/multiplayer/logout";
    public static final String MULTIPLAYER_ROOM_ID = "/game/multiplayer/room";
    public static final String MULTIPLAYER_JOIN_ROOM = "/game/multiplayer/";
    public static final String MULTIPLAYER_SYNCHRO_ROOM = "/game/multiplayer/synchronize/";
    /**
     *
     */
    public static final String MULTIPLAYER_UNJOIN = "/game/multiplayer/unjoin";
    /**
     * Server logged player endpoint
     */
    public static final String NAME_VALIDATION = "/shipname";
    /**
     * ENd point Combat
     */
    public static final String MAKE_SHOT_VALIDATION = "/fire";
    /**
     * ENd point Combat
     */
    public static final String SHOT_VALIDATION_VALIDATION = "/shotvalidation";
    /**
     * ENd point Combat
     */
    public static final String ASK_FOR_SHIP = "/ship";
    /**
     * Server logged player endpoint
     */
    public static final String AI_CREATION_ENDPOINT = "/AI";
    /**
     * Server logged player endpoint
     */
    public static final String AIS_CREATION_ENDPOINT = "/AIs";
    /**
     * Server login endpoint
     */
    public static final String PLAYER_LOGIN_ENDPOINT = "/player/login";
    /**
     * Server logout endpoint
     */
    public static final String PLAYER_LOGOUT_ENDPOINT = "/player/logout";
    /**
     * Ship Cretion
     */
    public static final String SHIP_ENDPOINT = "/ship";
    /**
     * Ship Cretion
     */
    public static final String SHIPS_CREATION_ENDPOINT = "/shipstoadd";
    /**
     * Crew Cretion
     */
    public static final String CREWMEMBER_CREATION_ENDPOINT = "/crewMember";

    /**
     * Update Crew Member position
     */
    public static final String CREWMEMBER_UPDATE_ENDPOINT = "/crew";

    /**
     * Update Ship
     */
    public static final String SHIP_UPDATE = "/ship/energy";
    /**
     * Weapon Cretion
     */
    public static final String WEAPON_CREATION_ENDPOINT = "/weapon";
    /**
     * Planet Cretion
     */
    public static final String PLANET_CREATION_ENDPOINT = "/planet";
    /**
     * Planet Cretion
     */
    public static final String PLANETS_CREATION_ENDPOINT = "/listplanet";
    /**
     * Planet Cretion
     */
    public static final String STATIONS_CREATION_ENDPOINT = "/liststation";
    /**
     * Planet Cretion
     */
    public static final String RESSOURCES_SHOP_CREATION_ENDPOINT = "/listressourcen";
    /**
     * Planet Cretion
     */
    public static final String RESSOURCE_SHOP_CREATION_ENDPOINT = "/shopressource";
    /**
     * Planet Cretion
     */
    public static final String STATION_CREATION_ENDPOINT = "/station";
    /**
     * Universe Cretion
     */
    public static final String UNIVERSE_CREATION_ENDPOINT = "/universe";
    /**
     * Section Cretion
     */
    public static final String SECTION_CREATION_ENDPOINT = "/section";
    /**
     * Section Cretion
     */
    public static final String SECTIONS_CREATION_ENDPOINT = "/sectiontoadd";
    /**
     * Section Cretion
     */
    public static final String CREWMEMBERS_CREATION_ENDPOINT = "/crewmemberstoadd";
    /**
     * Planet Cretion
     */
    public static final String RESSOURCE_SHIP_CREATION_ENDPOINT = "/shipressource";
    /**
     * Planet Cretion
     */
    public static final String WEAPONS_SHIP_CREATION_ENDPOINT = "/listweapons";
    /**
     * Player load game endpoint
     */
    public static final String PLAYER_CONTINUE_ENDPOINT = "/game/load/";
    /**
     * Player save game endpoint
     */
    public static final String PLAYER_SAVE_GAME = "/game/save/";
    /**
     * Player save game endpoint
     */
    public static final String SHIP_RESSORUCE_ENDPOINT = "/shipressource";
    /**
     * Player save game endpoint
     */
    public static final String GET_RESSOURCE_BY_SHIP = "/shipressourcebyship";
    /**
     * Player save game endpoint
     */
    public static final String GET_RESSOURCE_BY_STOP = "/getshopressourcebystop";
    /**
     * Player save game endpoint
     */
    public static final String BUY_RESSOURCE = "/buyitem";
    /**
     * Player save game endpoint
     */
    public static final String BUY_CREWMEMBER = "/buycrewmember";
    /**
     * Player save game endpoint
     */
    public static final String BUY_WEAPONS = "/buyweapon";

    /**
     * Player save game endpoint
     */
    public static final String SECTION_BY_SHIP_END_POINT = "/ship/{id}/sections";
    /**
     * Server logged player endpoint
     */
    public static final String MAKEJUMP_CREATION_ENDPOINT = "/makejump";
    /**
     * Server logged player endpoint
     */
    public static final String ROUNDS = "rounds";
    public static final String PLAYER_CLEAN_ENDPOINT = "/cleanuser";
    public static final String CAN_LAND = "/canLand";
    public static final String HAS_LANDED = "/hasLanded";
    /**
     * Game Constants
     */
    public static final int OXYGEN = 100;
    public static final int POWER_CURRENT = 1;
    public static final int POWER_REQUIRED = 1;
    public static final int HP = 1000;
    public static final int SHIELD = 1000;
    public static final int DAMAGE = 10;
    public static final int MAGAZIN_SIZE = 10;
    private static final int rocketWarmUp = 1;
    private static final int lasserWarmUp = 3;
    private static final int droneWarmUp = 1;
    private static final int magazinSizeLaser = 2;
    private static final float HIT_RATE = 0.8f;
    private static final float HIT_RATE_LASER = 0.97f;
    /**
     * Hardcoded server URL
     */
    public static String SERVER_URL = "http://localhost:8080";
    /**
     * boolean value for single player disable lobby
     */
    public static boolean IS_SINGLE_PLAYER = false;
    /**
     * @return true if level easy
     */
    public static boolean ISEASY = true;
    public static String multiPlayerSessionID;
    /**
     * Global player, this data will be downloaded from server at login
     */
    public static Player currentPlayer;
    /**
     * Global player, this data will be downloaded from server at login
     */
    public static Actor currentGegner;
    /**
     * Global player, this data will be downloaded from server at login
     */
    public static Ship currentShipGegner;
    /**
     * Global player, this data will be downloaded from server at login
     */
    public static Ship currentShipPlayer;
    /**
     * Global player, this data will be downloaded from server at login
     */
    public static StopAbstract currentStop;
    /**
     * Global player, this data will be downloaded from server at login
     */
    public static Universe currentUniverse;
    /**
     * Current Ressourcen Player
     */
    public static ShipRessource currentshipressourcen;
    /**
     * Global player, this data will be downloaded from server at login
     */
    public static Weapon currentWeapon;
    /**
     * Gets all Users
     */
    public static List<String> playersOnline = new ArrayList<>();
    /**
     * Default is onlineGame false
     */
    public static boolean isOnlineGame = false;

    public static boolean killMultiPlayerTimeoutTimer = false;

    public static SinglePlayerGame singlePlayerGame;

    public static int seedTimer = 0;

    public static boolean multiPlayerGameStarted = false;


    // FIXME Power ist doppelt
    public static Ship ship0 = Ship.shipBluider().hp(3 * HP).shipForm(ShipForm.SHIP1).name("ship0").power(9).shield(SHIELD).buildShip();
    public static Ship ship1 = Ship.shipBluider().hp(HP).shipForm(ShipForm.SHIP2).name("ship1").power(9).shield(SHIELD).buildShip();
    public static Ship ship2 = Ship.shipBluider().hp(2 * HP).shipForm(ShipForm.SHIP3).name("ship2").power(9).shield(2 * SHIELD).buildShip();
    public static Ship ship3 = Ship.shipBluider().hp(3 * HP).shipForm(ShipForm.SHIP4).name("ship3").power(9).shield(SHIELD).buildShip();
    /**
     * CrewMember Variables
     */
    public static CrewMember crewMember0 = CrewMember.crewMemberBuilder().health(100)
            .img("female_human.png").role(Role.FIGHTER).buildCrewMember();
    public static CrewMember crewMember1 = CrewMember.crewMemberBuilder().health(200).
            img("male_human.png").role(Role.TECHNICIAN).buildCrewMember();
    public static CrewMember crewMember2 = CrewMember.crewMemberBuilder().health(300).
            img("male_human.png").role(Role.TECHNICIAN).buildCrewMember();
    public static List<CrewMember> crewMemberList = new ArrayList<>() {{
        add(crewMember0);
        add(crewMember1);
        add(crewMember2);
    }};
    /*
    Crewmember gegner
     */
    public static CrewMember crewMember1gegner1 = CrewMember.crewMemberBuilder().health(100)
            .img("Robot").role(Role.FIGHTER).buildCrewMember();
    public static CrewMember crewMember2gegner1 = CrewMember.crewMemberBuilder().health(200).
            img("Human").role(Role.TECHNICIAN).buildCrewMember();
    public static List<CrewMember> crewMemberListGegner1 = new ArrayList<>() {{
        add(crewMember1gegner1);
        add(crewMember2gegner1);
    }};

    public static CrewMember crewMember1gegner2 = CrewMember.crewMemberBuilder().health(100)
            .img("Robot").role(Role.FIGHTER).buildCrewMember();
    public static CrewMember crewMember2gegner2 = CrewMember.crewMemberBuilder().health(200).
            img("Human").role(Role.TECHNICIAN).buildCrewMember();
    public static List<CrewMember> crewMemberListGegner2 = new ArrayList<>() {{
        add(crewMember1gegner2);
        add(crewMember2gegner2);
    }};

    public static CrewMember crewMember1gegner3 = CrewMember.crewMemberBuilder().health(100)
            .img("Robot").role(Role.FIGHTER).buildCrewMember();
    public static CrewMember crewMember2gegner3 = CrewMember.crewMemberBuilder().health(200).
            img("Human").role(Role.TECHNICIAN).buildCrewMember();
    public static List<CrewMember> crewMemberListGegner3 = new ArrayList<>() {{
        add(crewMember1gegner3);
        add(crewMember2gegner3);
    }};
    public static boolean allReady = false;  // FIXME change to true when all Player will jump
    public static int currentStopNumber = 0;

    // These Maps use the Ship id
    public static HashMap<Integer, List<Section>> combatSections = new HashMap<>();
    public static HashMap<Integer, List<Weapon>> combatWeapons = new HashMap<>();
    public static HashMap<Integer, List<CrewMember>> combatCrew = new HashMap<>();


    public static Section section1 = Section
            .sectionBuilder()
            .img("S-1")
            .oxygen(OXYGEN)
            .powerCurrent(0)
            .sectionTyp(SectionTyp.NORMAL)
            .role(Role.TECHNICIAN)
            .usable(true)
            .connectingTo(null)
            .powerRequired(POWER_REQUIRED)
            .pos(90, 350)
            .buildSection();
    public static Section section2 = Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.HEALTH)
            .role(Role.FIGHTER)
            .img("S-2")
            .oxygen(OXYGEN)
            .powerCurrent(POWER_CURRENT)
            .usable(true)
            .connectingTo(null)
            .powerRequired(POWER_REQUIRED)
            .pos(240, 336)
            .buildSection();
    public static Section section3 = Section
            .sectionBuilder()
            .img("S-3")
            .sectionTyp(SectionTyp.NORMAL)
            .oxygen(OXYGEN)
            .powerCurrent(0)
            .usable(true)
            .connectingTo(null)
            .powerRequired(POWER_REQUIRED)
            .pos(320, 270)
            .buildSection();
    public static Section section4 = Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.WEAPONS)
            .img("S-4").oxygen(OXYGEN)
            .powerCurrent(POWER_CURRENT)
            .usable(true)
            .connectingTo(null)
            .powerRequired(POWER_REQUIRED)
            .pos(320, 210)
            .buildSection();
    public static Section section5 = Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.NORMAL)
            .img("S-5")
            .oxygen(OXYGEN)
            .powerCurrent(0)
            .usable(true)
            .connectingTo(null)
            .powerRequired(POWER_REQUIRED)
            .pos(240, 135)
            .buildSection();
    public static Section section6 = Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.ENGINE)
            .img("S-6")
            .oxygen(OXYGEN)
            .powerCurrent(POWER_CURRENT)
            .usable(true)
            .connectingTo(null)
            .powerRequired(POWER_REQUIRED)
            .pos(90, 124)
            .buildSection();
    public static List<Section> sectionsPlayerList = new ArrayList<>() {{
        add(section1);
        add(section2);
        add(section3);
        add(section4);
        add(section5);
        add(section6);
    }};
    /*
     * Gegner
     * */
    public static AI ai1 = AI.builderAI().name("gegner1").buildAI();
    public static AI ai2 = AI.builderAI().name("gegner2").buildAI();
    public static AI ai3 = AI.builderAI().name("gegner3").buildAI();
    public static AI ai4 = AI.builderAI().name("gegner4").buildAI();
    public static AI ai5 = AI.builderAI().name("gegner5").buildAI();
    public static AI ai6 = AI.builderAI().name("gegner6").buildAI();
    public static List<AI> aisU2 = new ArrayList<>() {{
        add(ai1);
        add(ai2);
        add(ai3);
        add(ai4);
        add(ai5);
        add(ai6);
    }};
    /*
    Gegner Ship
     */
    public static Ship shipGegner1 = Ship.shipBluider().hp(HP).
            power(9).shield(1).
            name("Shipgegner1").owner(ai1).
            buildShip();
    public static Ship shipGegner2 = Ship.shipBluider().hp(HP).
            power(9).shield(1).
            name("Shipgegner2").owner(ai2).
            buildShip();
    public static Ship shipGegner3 = Ship.shipBluider().hp(HP).
            power(9).shield(1).
            name("Shipgegner3").owner(ai3).
            buildShip();
    public static Ship shipGegner4 = Ship.shipBluider().hp(HP).
            power(9).shield(1).
            name("Shipgegner4").owner(ai4).
            buildShip();
    public static Ship shipGegner5 = Ship.shipBluider().hp(HP).
            power(9).shield(1).
            name("Shipgegner5").owner(ai5).
            buildShip();
    public static Ship shipGegner6 = Ship.shipBluider().hp(HP).
            power(9).shield(5).
            name("Shipgegner6").owner(ai6).
            buildShip();
    // Ship Universe 2
    public static List<Ship> shipsgegneru2 = new ArrayList<>() {{
        add(shipGegner1);
        add(shipGegner2);
        add(shipGegner3);
        add(shipGegner4);
        add(shipGegner5);
        add(shipGegner6);

    }};
    public static Section section1Gegner = Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.COCKPIT)
            .img("Section1Gegner1")
            .oxygen(OXYGEN)
            .powerCurrent(POWER_CURRENT)
            .usable(true)
            .connectingTo(null)
            .powerRequired(POWER_REQUIRED)
            .buildSection();
    public static Section section2Gegner = Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.ENGINE)
            .role(Role.TECHNICIAN)
            .img("Section2Gegner1")
            .oxygen(OXYGEN)
            .powerCurrent(POWER_CURRENT)
            .usable(true)
            .connectingTo(null)
            .powerRequired(POWER_REQUIRED)
            .buildSection();
    public static Section section3Gegner = Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.WEAPONS)
            .role(Role.FIGHTER)
            .img("Section3Gegner1")
            .oxygen(OXYGEN)
            .powerCurrent(POWER_CURRENT)
            .usable(true)
            .connectingTo(null)
            .powerRequired(POWER_REQUIRED)
            .buildSection();
    public static List<Section> sectionsgegner1 = new ArrayList<>() {{
        add(section1Gegner);
        add(section2Gegner);
        add(section3Gegner);
    }};
    public static Section section1Gegner2 = Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.COCKPIT)
            .img("Section1Gegner2")
            .oxygen(OXYGEN)
            .powerCurrent(POWER_CURRENT)
            .usable(true)
            .connectingTo(null)
            .powerRequired(POWER_REQUIRED)
            .buildSection();
    public static Section section2Gegner2 = Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.ENGINE)
            .role(Role.TECHNICIAN)
            .img("Section2Gegner2")
            .oxygen(OXYGEN)
            .powerCurrent(POWER_CURRENT)
            .usable(true)
            .connectingTo(null)
            .powerRequired(POWER_REQUIRED)
            .buildSection();
    public static Section section3Gegner2 = Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.WEAPONS)
            .role(Role.FIGHTER)
            .img("Section3Gegner2")
            .oxygen(OXYGEN)
            .powerCurrent(POWER_CURRENT)
            .usable(true)
            .connectingTo(null)
            .powerRequired(POWER_REQUIRED)
            .buildSection();
    public static Section section4Gegner2 = Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.O2)
            .img("Section4Gegner2")
            .oxygen(OXYGEN)
            .powerCurrent(POWER_CURRENT)
            .usable(true)
            .connectingTo(null)
            .powerRequired(POWER_REQUIRED)
            .buildSection();
    public static List<Section> sectionsgegner2 = new ArrayList<>() {{
        add(section1Gegner2);
        add(section2Gegner2);
        add(section3Gegner2);
        add(section4Gegner2);
    }};
    public static Section section1Gegner3 = Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.COCKPIT)
            .img("Section1Gegner3")
            .oxygen(OXYGEN)
            .powerCurrent(POWER_CURRENT)
            .usable(true)
            .connectingTo(null)
            .powerRequired(POWER_REQUIRED)
            .buildSection();
    public static Section section2Gegner3 = Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.ENGINE)
            .role(Role.TECHNICIAN)
            .img("Section2Gegner3")
            .oxygen(OXYGEN)
            .powerCurrent(POWER_CURRENT)
            .usable(true)
            .connectingTo(null)
            .powerRequired(POWER_REQUIRED)
            .buildSection();
    public static Section section3Gegner3 = Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.WEAPONS)
            .role(Role.FIGHTER)
            .img("Section3Gegner3")
            .oxygen(OXYGEN)
            .powerCurrent(POWER_CURRENT)
            .usable(true)
            .connectingTo(null)
            .powerRequired(POWER_REQUIRED)
            .buildSection();
    public static Section section4Gegner3 = Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.O2)
            .img("Section4Gegner3")
            .oxygen(OXYGEN)
            .powerCurrent(POWER_CURRENT)
            .usable(true)
            .connectingTo(null)
            .powerRequired(POWER_REQUIRED)
            .buildSection();
    public static Section section5Gegner3 = Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.HEALTH)
            .img("Section5Gegner3")
            .oxygen(OXYGEN)
            .powerCurrent(POWER_CURRENT)
            .usable(true)
            .connectingTo(null)
            .powerRequired(POWER_REQUIRED)
            .buildSection();
    public static List<Section> sectionsgegner3 = new ArrayList<>() {{
        add(section1Gegner3);
        add(section2Gegner3);
        add(section3Gegner3);
        add(section4Gegner3);
        add(section5Gegner3);
    }};
    public static Section section1Gegner4 = Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.NORMAL)
            .img("Section1Gegner4")
            .oxygen(OXYGEN)
            .powerCurrent(POWER_CURRENT)
            .usable(true)
            .connectingTo(null)
            .powerRequired(POWER_REQUIRED)
            .buildSection();
    public static Section section2Gegner4 = Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.ENGINE)
            .role(Role.TECHNICIAN)
            .img("Section2Gegner4")
            .oxygen(OXYGEN)
            .powerCurrent(POWER_CURRENT)
            .usable(true)
            .connectingTo(null)
            .powerRequired(POWER_REQUIRED)
            .buildSection();
    public static Section section3Gegner4 = Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.WEAPONS)
            .role(Role.FIGHTER)
            .img("Section3Gegner4")
            .oxygen(OXYGEN)
            .powerCurrent(POWER_CURRENT)
            .usable(true)
            .connectingTo(null)
            .powerRequired(POWER_REQUIRED)
            .buildSection();
    public static List<Section> sectionsgegner4 = new ArrayList<>() {{
        add(section1Gegner4);
        add(section2Gegner4);
        add(section3Gegner4);
    }};
    public static Section section1Gegner5 = Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.NORMAL)
            .img("Section1Gegner5")
            .oxygen(OXYGEN)
            .powerCurrent(POWER_CURRENT)
            .usable(true)
            .connectingTo(null)
            .powerRequired(POWER_REQUIRED)
            .buildSection();
    public static Section section2Gegner5 = Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.ENGINE)
            .role(Role.TECHNICIAN)
            .img("Section2Gegner5")
            .oxygen(OXYGEN)
            .powerCurrent(POWER_CURRENT)
            .usable(true)
            .connectingTo(null)
            .powerRequired(POWER_REQUIRED)
            .buildSection();
    public static Section section3Gegner5 = Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.WEAPONS)
            .role(Role.FIGHTER)
            .img("Section3Gegner5")
            .oxygen(OXYGEN)
            .powerCurrent(POWER_CURRENT)
            .usable(true)
            .connectingTo(null)
            .powerRequired(POWER_REQUIRED)
            .buildSection();
    public static List<Section> sectionsgegner5 = new ArrayList<>() {{
        add(section1Gegner5);
        add(section2Gegner5);
        add(section3Gegner5);
    }};
    public static Section section1Gegner6 = Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.NORMAL)
            .img("Section1Gegner6")
            .oxygen(OXYGEN)
            .powerCurrent(POWER_CURRENT)
            .usable(true)
            .connectingTo(null)
            .powerRequired(POWER_REQUIRED)
            .buildSection();
    public static Section section2Gegner6 = Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.ENGINE)
            .role(Role.TECHNICIAN)
            .img("Section2Gegner6")
            .oxygen(OXYGEN)
            .powerCurrent(POWER_CURRENT)
            .usable(true)
            .connectingTo(null)
            .powerRequired(POWER_REQUIRED)
            .buildSection();
    public static Section section3Gegner6 = Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.WEAPONS)
            .role(Role.FIGHTER)
            .img("Section3Gegner6")
            .oxygen(OXYGEN)
            .powerCurrent(POWER_CURRENT)
            .usable(true)
            .connectingTo(null)
            .powerRequired(POWER_REQUIRED)
            .buildSection();
    public static List<Section> sectionsgegner6 = new ArrayList<>() {{
        add(section1Gegner6);
        add(section2Gegner6);
        add(section3Gegner6);
    }};
    /**
     * planete Univerise 1
     */
    public static Planet planet1 = Planet.builder().name("p1").img("null").build();
    public static Planet planet2 = Planet.builder().name("p2").img("null").build();
    public static Planet planet3 = Planet.builder().name("p3").img("null").build();
    public static Planet planet4 = Planet.builder().name("p4").img("null").build();
    public static Planet planet5 = Planet.builder().name("p5").img("null").build();
    public static Planet planet6 = Planet.builder().name("p6").img("null").build();

    /*Weapon Universe2*/
    public static Planet planet7 = Planet.builder().name("p7").img("null").build();
    public static Planet planet8 = Planet.builder().name("p8").img("null").build();
    public static Planet planet9 = Planet.builder().name("p9").img("null").build();
    /*
    public static List<Ship> shipsgegneru1 =new ArrayList<Ship>(){{
        add(shipGegner1);
        add(shipGegner2);
        add(shipGegner3);

    }};
    public static void updateShipsVariabelgegneru1(){
        shipGegner1=shipsgegneru1.get(0);
        shipGegner2=shipsgegneru1.get(1);
        shipGegner3=shipsgegneru1.get(2);
    }
    public static void updateShipsListgegneru1(){
        List<Ship> shipsgegneru1new =new ArrayList<Ship>(){{
            add(shipGegner1);
            add(shipGegner2);
            add(shipGegner3);
        }};
        shipsgegneru1=shipsgegneru1new;
    }*/
    //**Weapons Universe **//
    public static Planet planet10 = Planet.builder().name("p10").img("null").build();
    public static List<Planet> planetListU2 = new ArrayList<>() {{
        add(planet1);
        add(planet2);
        add(planet3);
        add(planet4);
        add(planet5);
        add(planet6);
        add(planet7);
        add(planet8);
        add(planet9);
        add(planet10);
    }};
    /**
     * Station
     */
    public static Station station1 = Station.stationBuilder().name("station1").energyPrice(0).buildStation();
    public static Station station2 = Station.stationBuilder().name("station2").energyPrice(1).buildStation();
    public static Station station3 = Station.stationBuilder().name("station3").energyPrice(0).buildStation();
    public static Station station4 = Station.stationBuilder().name("station4").energyPrice(1).buildStation();

    public static List<Station> stationListU2 = new ArrayList<>() {{
        add(station1);
        add(station2);
        add(station3);
        add(station4);
    }};
    //Sections Gegner  // FIXME was ist prive
    public static ShopRessource shopRessource1 = ShopRessource.shopRessourceBuilder().name(RessourceName.ENERGIE).prive(10).amount(100).build();
    public static ShopRessource shopRessource2 = ShopRessource.shopRessourceBuilder().name(RessourceName.ENERGIE).prive(10).amount(100).build();
    public static ShopRessource shopRessource3 = ShopRessource.shopRessourceBuilder().name(RessourceName.GOLD).prive(0).amount(100).build();

    public static ShopRessource shopRessourcep1Gold = ShopRessource.shopRessourceBuilder().name(RessourceName.GOLD).prive(0).amount(100).build();
    public static ShopRessource shopRessourcep1Energie = ShopRessource.shopRessourceBuilder().name(RessourceName.ENERGIE).prive(20).amount(100).build();

    public static ShopRessource shopRessourcep2Gold = ShopRessource.shopRessourceBuilder().name(RessourceName.GOLD).prive(0).amount(100).build();
    public static ShopRessource shopRessourcep2Energie = ShopRessource.shopRessourceBuilder().name(RessourceName.ENERGIE).prive(20).amount(100).build();

    public static ShopRessource shopRessourcep3Gold = ShopRessource.shopRessourceBuilder().name(RessourceName.GOLD).prive(0).amount(100).build();
    public static ShopRessource shopRessourcep3Energie = ShopRessource.shopRessourceBuilder().name(RessourceName.ENERGIE).prive(20).amount(100).build();

    public static List<ShopRessource> shopRessourceList = new ArrayList<>() {{
        add(shopRessource1);
        add(shopRessource2);
        add(shopRessource3);

        add(shopRessourcep1Gold);
        add(shopRessourcep1Energie);

        add(shopRessourcep2Gold);
        add(shopRessourcep2Energie);

        add(shopRessourcep3Gold);
        add(shopRessourcep3Energie);
    }};
    public static ShipRessource shipRessource = ShipRessource.builderShipRessource().amount(100).name(RessourceName.GOLD).build();
    /*
     * Universe Univerise 1
     */
    public static Universe universe1 = Universe.universeBuilder().name("Easy").build();
    public static Universe universe2 = Universe.universeBuilder().name("Normal").build();
    public static List<Ship> shipsP1 = new ArrayList<>();
    public static List<Ship> shipsP2 = new ArrayList<>();
    public static List<Ship> shipsP3 = new ArrayList<>();
    public static List<Ship> shipsP4 = new ArrayList<>();
    public static List<Ship> shipsP5 = new ArrayList<>();
    public static List<Ship> shipsP6 = new ArrayList<>();

    /*
     * Weapon
     * */
    public static Weapon weapon1Player = Weapon.WeaponBuilder().damage(DAMAGE).hitRate(HIT_RATE).img("Player").name("Rocket Left").warmUp(rocketWarmUp).magazinSize(MAGAZIN_SIZE).build();
    public static Weapon weapon2Player = Weapon.WeaponBuilder().damage(30).hitRate(HIT_RATE_LASER).img("Player").name("Lasser Right").warmUp(lasserWarmUp).magazinSize(magazinSizeLaser).build();

    /*
     * Weapon Gegner1
     * */
    public static Weapon weapon1Gegner1 = Weapon.WeaponBuilder().damage(10).hitRate(HIT_RATE).img("Enemy1").name("Rocket Left").warmUp(rocketWarmUp).magazinSize(MAGAZIN_SIZE).build();
    public static Weapon weapon2Gegner1 = Weapon.WeaponBuilder().damage(10).hitRate(HIT_RATE).img("Enemy1").name("Rocket Right").warmUp(rocketWarmUp).magazinSize(MAGAZIN_SIZE).build();
    public static Weapon weapon3Gegner1 = Weapon.WeaponBuilder().damage(30).hitRate(HIT_RATE_LASER).img("Enemy1").name("Lasser Right").warmUp(lasserWarmUp).magazinSize(magazinSizeLaser).build();

    public static List<Weapon> weaponListGegner1 = new ArrayList<>() {{
        add(weapon1Gegner1);
        add(weapon2Gegner1);
        add(weapon3Gegner1);
    }};
    /*
     * Weapons Gegner2
     * */
    public static Weapon weapon1Gegner2 = Weapon.WeaponBuilder().damage(10).hitRate(HIT_RATE).img("Enemy2").name("Rocket Left").warmUp(rocketWarmUp).magazinSize(MAGAZIN_SIZE).build();
    public static Weapon weapon2Gegner2 = Weapon.WeaponBuilder().damage(10).hitRate(HIT_RATE).img("Enemy2").name("Rocket Right").warmUp(rocketWarmUp).magazinSize(MAGAZIN_SIZE).build();
    public static Weapon weapon3Gegner2 = Weapon.WeaponBuilder().damage(10).hitRate(HIT_RATE).img("Enemy2").name("DRONE").warmUp(droneWarmUp).build();

    public static List<Weapon> weaponListGegner2 = new ArrayList<>() {{
        add(weapon1Gegner2);
        add(weapon2Gegner2);
        add(weapon3Gegner2);
    }};
    /*
     * Weapons Gegner3
     * */
    public static Weapon weapon1Gegner3 = Weapon.WeaponBuilder().damage(10).hitRate(HIT_RATE).img("Enemy3").name("Rocket Left").warmUp(rocketWarmUp).magazinSize(MAGAZIN_SIZE).build();
    public static Weapon weapon2Gegner3 = Weapon.WeaponBuilder().damage(10).hitRate(HIT_RATE).img("Enemy3").name("Rocket Right").warmUp(rocketWarmUp).magazinSize(MAGAZIN_SIZE).build();
    public static Weapon weapon3Gegner3 = Weapon.WeaponBuilder().damage(10).hitRate(HIT_RATE).img("Enemy3").name("Laser").warmUp(lasserWarmUp).magazinSize(magazinSizeLaser).build();

    public static List<Weapon> weaponListGegner3 = new ArrayList<>() {{
        add(weapon1Gegner3);
        add(weapon2Gegner3);
        add(weapon3Gegner3);
    }};

    /*
     * Weapons Gegner3
     * */
    public static Weapon weapon1Gegner4 = Weapon.WeaponBuilder().damage(10).hitRate(HIT_RATE).img("Enemy4").name("Rocket Left").warmUp(rocketWarmUp).magazinSize(MAGAZIN_SIZE).build();
    public static Weapon weapon2Gegner4 = Weapon.WeaponBuilder().damage(10).hitRate(HIT_RATE).img("Enemy4").name("Rocket Right").warmUp(rocketWarmUp).magazinSize(MAGAZIN_SIZE).build();
    public static Weapon weapon3Gegner4 = Weapon.WeaponBuilder().damage(10).hitRate(HIT_RATE).img("Enemy4").name("Laser").warmUp(lasserWarmUp).magazinSize(magazinSizeLaser).build();

    public static List<Weapon> weaponListGegner4 = new ArrayList<>() {{
        add(weapon1Gegner4);
        add(weapon2Gegner4);
        add(weapon3Gegner4);
    }};
    public static List<Weapon> weaponListPlayer = new ArrayList<>() {{
        add(weapon1Player);
        add(weapon2Player);
    }};
    public static List<Weapon> weaponListUniverse2 = new ArrayList<>() {{
        add(weapon1Player);
        add(weapon2Player);
        add(weapon1Gegner1);
        add(weapon2Gegner1);
        add(weapon1Gegner2);
        add(weapon2Gegner2);
        add(weapon1Gegner3);
        add(weapon2Gegner3);
        add(weapon1Gegner4);
        add(weapon2Gegner4);
        add(weapon3Gegner4);

    }};
    public static List<Pair> ExplosionsToRender = new ArrayList<>();
    // Id is the Player
    public static Map<Integer, Actor> combatActors = new HashMap<>();
    public static List<Weapon> weaponsToProcess = new LinkedList<>();
    public static List<GameRound> playerRounds = new ArrayList<>();

    public static void updateVariableCrewMembersPlayer() {
        crewMember0 = crewMemberList.get(0);
        crewMember1 = crewMemberList.get(1);
        crewMember2 = crewMemberList.get(2);
    }

    /**
     * Sections Variables
     */

    private static Map<Integer, List<Pair>> getPos() {

        var res = new HashMap<Integer, List<Pair>>();


        List<Pair> pairs = new ArrayList<>();
        pairs.add(new Pair(124f, 90f));
        pairs.add(new Pair(0f, 293f));
        pairs.add(new Pair(647f, 307f));
        pairs.add(new Pair(320f, 210f));
        pairs.add(new Pair(646f, 461f)); // Fehlt
        pairs.add(new Pair(336f, 240f));

        res.put(0, pairs);

        return res;
    }

    public static void updateVariableSectionShipPlayer() {
        section1 = sectionsPlayerList.get(0);
        section2 = sectionsPlayerList.get(1);
        section3 = sectionsPlayerList.get(2);
        section4 = sectionsPlayerList.get(3);
        section5 = sectionsPlayerList.get(4);
        section6 = sectionsPlayerList.get(5);
    }

    public static void updateVariableaiu2() {
        ai1 = aisU2.get(0);
        ai2 = aisU2.get(1);
        ai3 = aisU2.get(2);
        ai4 = aisU2.get(3);
        ai5 = aisU2.get(4);
        ai6 = aisU2.get(5);
    }

    public static void updateweaponPlayerVariabel() {
        weapon1Player = weaponListPlayer.get(0);
        weapon2Player = weaponListPlayer.get(1);
    }

    public static void actualiziertweaponListPlayer() {
        weaponListPlayer = new ArrayList<>() {{
            add(weapon1Player);
            add(weapon2Player);
        }};
    }

    public static void actualizierungSectionInWeapons() {
        weapon1Player.setSection(section4);
        weapon2Player.setSection(section4);
        actualiziertweaponListPlayer();
    }

    public static void updateweaponGegner1Variabel() {
        weapon1Gegner1 = weaponListGegner1.get(0);
        weapon2Gegner1 = weaponListGegner1.get(1);
    }

    public static void actualiziertweaponListGegner1() {
        weaponListGegner1 = new ArrayList<>() {{
            add(weapon1Gegner1);
            add(weapon2Gegner1);
        }};
    }

    public static void updateweaponGegner2Variabel() {
        weapon1Gegner2 = weaponListGegner2.get(0);
        weapon2Gegner2 = weaponListGegner2.get(1);
    }

    public static void actualiziertweaponListGegner2() {
        weaponListGegner2 = new ArrayList<>() {{
            add(weapon1Gegner2);
            add(weapon2Gegner2);
        }};
    }

    public static void updateweaponGegner3Variabel() {
        weapon1Gegner3 = weaponListGegner3.get(0);
        weapon2Gegner3 = weaponListGegner3.get(1);
    }

    public static void actualiziertweaponListGegner3() {
        weaponListGegner3 = new ArrayList<>() {{
            add(weapon1Gegner3);
            add(weapon2Gegner3);
        }};
    }
    public static void updateweaponGegner4Variabel() {
        weapon1Gegner4 = weaponListGegner4.get(0);
        weapon2Gegner4 = weaponListGegner4.get(1);
    }

    public static void actualiziertweaponListGegner4() {
        weaponListGegner4 = new ArrayList<>() {{
            add(weapon1Gegner4);
            add(weapon2Gegner4);
        }};
    }

    public static void updateweaponVariabelUniverse2() {
        weapon1Player = weaponListUniverse2.get(0);
        weapon2Player = weaponListUniverse2.get(1);
        weapon1Gegner1 = weaponListUniverse2.get(2);
        weapon2Gegner2 = weaponListUniverse2.get(3);
        weapon1Gegner2 = weaponListUniverse2.get(4);
        weapon2Gegner2 = weaponListUniverse2.get(5);
        weapon1Gegner3 = weaponListUniverse2.get(6);
        weapon2Gegner3 = weaponListUniverse2.get(7);
        weapon1Gegner4 = weaponListUniverse2.get(8);
        weapon2Gegner4 = weaponListUniverse2.get(9);
        weapon3Gegner4 = weaponListUniverse2.get(10);

    }

    public static void aktualizierenweaponListUniverse2() {
        weapon1Player.setSection(section4);
        weapon2Player.setSection(section4);
        weapon1Gegner1.setSection(section3Gegner);
        weapon2Gegner1.setSection(section3Gegner);
        weapon1Gegner2.setSection(section3Gegner2);
        weapon2Gegner2.setSection(section3Gegner2);
        weapon1Gegner3.setSection(section3Gegner3);
        weapon2Gegner3.setSection(section3Gegner3);

        weaponListUniverse2 = new ArrayList<>() {{
            add(weapon1Player);
            add(weapon2Player);
            add(weapon1Gegner1);
            add(weapon2Gegner1);
            add(weapon1Gegner2);
            add(weapon2Gegner2);
            add(weapon1Gegner3);
            add(weapon2Gegner3);

        }};
    }

    public static void updateShipsVariabelgegneru2() {
        shipGegner1 = shipsgegneru2.get(0);
        shipGegner2 = shipsgegneru2.get(1);
        shipGegner3 = shipsgegneru2.get(2);
        shipGegner4 = shipsgegneru2.get(3);
        shipGegner5 = shipsgegneru2.get(4);
        shipGegner6 = shipsgegneru2.get(5);
    }

    public static void updateShipsListgegneru2() {
        shipsgegneru2 = new ArrayList<>() {{
            add(shipGegner1);
            add(shipGegner2);
            add(shipGegner3);
            add(shipGegner4);
            add(shipGegner5);
            add(shipGegner6);
        }};
    }

    public static void updateVariblesSectionsGegner1() {
        section1Gegner = sectionsgegner1.get(0);
        section2Gegner = sectionsgegner1.get(1);
        section3Gegner = sectionsgegner1.get(2);
    }

    public static void updateVariblesSectionsGegner2() {
        section1Gegner2 = sectionsgegner2.get(0);
        section2Gegner2 = sectionsgegner2.get(1);
        section3Gegner2 = sectionsgegner2.get(2);
        section4Gegner2 = sectionsgegner2.get(3);
    }

    public static void updateVariblesSectionsGegner3() {
        section1Gegner3 = sectionsgegner3.get(0);
        section2Gegner3 = sectionsgegner3.get(1);
        section3Gegner3 = sectionsgegner3.get(2);
        section4Gegner3 = sectionsgegner3.get(3);
        section5Gegner3 = sectionsgegner3.get(4);
    }

    public static void updateVariblesSectionsGegner4() {
        section1Gegner4 = sectionsgegner4.get(0);
        section2Gegner4 = sectionsgegner4.get(1);
        section3Gegner4 = sectionsgegner4.get(2);
    }

    public static void updateVariblesSectionsGegner5() {
        section1Gegner5 = sectionsgegner5.get(0);
        section2Gegner5 = sectionsgegner5.get(1);
        section3Gegner5 = sectionsgegner5.get(2);
    }

    public static void updateVariblesSectionsGegner6() {
        section1Gegner6 = sectionsgegner6.get(0);
        section2Gegner6 = sectionsgegner6.get(1);
        section3Gegner6 = sectionsgegner6.get(2);
    }


    public static void updateVariblesPlanetsU2() {
        planet1 = planetListU2.get(0);
        planet2 = planetListU2.get(1);
        planet3 = planetListU2.get(2);
        planet4 = planetListU2.get(3);
        planet5 = planetListU2.get(4);
        planet6 = planetListU2.get(5);
        planet7 = planetListU2.get(6);
        planet8 = planetListU2.get(7);
        planet9 = planetListU2.get(8);
        planet10 = planetListU2.get(9);
    }



    public static void updateVariblesStationsU2() {
        station1 = stationListU2.get(0);
        station2 = stationListU2.get(1);
        station3 = stationListU2.get(2);
        station4 = stationListU2.get(3);
    }

    public static void updateVariblesshopRessource() {
        shopRessource1 = shopRessourceList.get(0);
        shopRessource2 = shopRessourceList.get(1);
        shopRessource3 = shopRessourceList.get(2);
        shopRessourcep1Gold =shopRessourceList.get(3);
        shopRessourcep1Energie=shopRessourceList.get(4);
        shopRessourcep2Gold =shopRessourceList.get(5);
        shopRessourcep2Energie=shopRessourceList.get(6);
        shopRessourcep3Gold =shopRessourceList.get(7);
        shopRessourcep3Energie=shopRessourceList.get(8);
    }


    /**
     * Sum the current Energy of the Ship
     *
     * @param sections to to be comptued
     * @return current energy required
     */
    public static int sumCurrentPower(List<Section> sections) {
        return sections.stream()
                .mapToInt(Section::getPowerCurrent)
                .sum();
    }

    /**
     * Sum the required Power for the Ship
     *
     * @param sections to compute the Power Required
     * @return the sum of the required Power
     */
    public static int sumRequiredPower(List<Section> sections) {
        return sections.stream()
                .mapToInt(Section::getPowerRequired)
                .sum();
    }


}

