package de.spaceStudio.client.util;

import de.spaceStudio.server.handler.SinglePlayerGame;
import de.spaceStudio.server.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains global variables, they can be access in the whole project
 *
 * @author Miguel Caceres
 * created on 6.17.2020
 */
public class Global {


    /**
     * boolean value for single player disable lobby
     */
    public static boolean IS_SINGLE_PLAYER = false;

    /**
     * Hardcoded server URL
     */
    public static final String SERVER_URL = "http://localhost:8080";

    /**
     * Server player endpoint
     */
    public static final String PLAYER_ENDPOINT = "/player";

    /**
     * Server logged player endpoint
     */
    public static final String PLAYER_LOGGED_ENDPOINT = "/player/logged-players";

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
    public static final String SHIP_CREATION_ENDPOINT = "/ship";
    /**
     * Ship Cretion
     */
    public static final String SHIPS_CREATION_ENDPOINT = "/shipstoadd";
    /**
     * Crew Cretion
     */
    public static final String CREWMEMBER_CREATION_ENDPOINT = "/crewMember";
    /**
     * Crew Cretion
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
     * Global player, this data will be downloaded from server at login
     */
    public static Player currentPlayer;
    /**
     * Global player, this data will be downloaded from server at login
     */
    public static AI currentGegner;
    /**
     * Global player, this data will be downloaded from server at login
     */
    public static Ship currentShipGegner;
    /**
     * Global player, this data will be downloaded from server at login
     */
    public static Ship currentShip;
    /**
     * Global player, this data will be downloaded from server at login
     */
    public static Planet currentPlanet;
    /**
     * Global player, this data will be downloaded from server at login
     */
    public static Universe currentUniverse;
    /**
     * Global player, this data will be downloaded from server at login
     */
    public static Weapon currentWeapon;
    /**
     * Server logged player endpoint
     */
    public static final String MAKEJUMP_CREATION_ENDPOINT = "/makejump";
    /**
     * Server logged player endpoint
     */
    public static final String PLAYER_CLEAN_ENDPOINT = "/cleanuser";

    /**
     * Gets all Users
     */
    public static List<String> playersOnline = new ArrayList<>();

    /**
     * Default is onlineGame false
     */
    public static boolean isOnlineGame = false;



    public static SinglePlayerGame singlePlayerGame;

    /**
     * Ship Variables
     */
    public static Ship ship0= Ship.shipBluider().hp(10).shipForm(ShipForm.SHIP1).name("ship0").power(10).shield(1).buildShip();
    public static Ship ship1= Ship.shipBluider().hp(100).shipForm(ShipForm.SHIP2).name("ship1").power(100).shield(1).buildShip();
    public static Ship ship2= Ship.shipBluider().hp(200).shipForm(ShipForm.SHIP3).name("ship2").power(50).shield(2).buildShip();
    public static Ship ship3= Ship.shipBluider().hp(300).shipForm(ShipForm.SHIP4).name("ship3").power(200).shield(1).buildShip();
    /**
     * ship0
     * */
    /*public static  List<Section> listShip0ForSection1=new ArrayList<Section>(){{
        add(section2);
    }};
    public static  List<Section> listShip0ForSection2=new ArrayList<Section>(){{
        add(section1);
        add(section3);
        add(section4);
    }};
    public static  List<Section> listShip0ForSection3=new ArrayList<Section>(){{
        add(section2);
    }};
    public static  List<Section> listShip0ForSection4=new ArrayList<Section>(){{
        add(section2);
        add(section5);
        add(section6);
    }};
    public static  List<Section> listShip0ForSection5=new ArrayList<Section>(){{
        add(section4);
    }};
    public static  List<Section> listShip0ForSection6=new ArrayList<Section>(){{
        add(section4);
    }};*/
    /*
     *SHIP1
     * */
    /*public static  List<Section> listShip1ForSection1=new ArrayList<Section>(){{
        add(section2);
    }};
    public static  List<Section> listShip1ForSection2=new ArrayList<Section>(){{
        add(section1);
        add(section4);
        add(section6);
    }};
    public static  List<Section> listShip1ForSection3=new ArrayList<Section>(){{
        add(section3);
        add(section5);
    }};
    public static  List<Section> listShip1ForSection4=new ArrayList<Section>(){{
        add(section2);
        add(section3);
        add(section5);
    }};
    public static  List<Section> listShip1ForSection5=new ArrayList<Section>(){{
        add(section4);
        add(section3);
    }};
    public static  List<Section> listShip1ForSection6=new ArrayList<Section>(){{
        add(section2);
    }};*/
    /*
     *SHIP2
     * */
    /*public static  List<Section> listShip2ForSection1=new ArrayList<Section>(){{
        add(section2);
        add(section3);
    }};
    public static  List<Section> listShip2ForSection2=new ArrayList<Section>(){{
        add(section1);
        add(section3);
        add(section4);
    }};
    public static  List<Section> listShip2ForSection3=new ArrayList<Section>(){{
        add(section1);
        add(section6);
    }};
    public static  List<Section> listShip2ForSection4=new ArrayList<Section>(){{
        add(section2);
        add(section5);
    }};
    public static  List<Section> listShip2ForSection5=new ArrayList<Section>(){{
        add(section4);
        add(section6);
    }};
    public static  List<Section> listShip2ForSection6=new ArrayList<Section>(){{
        add(section5);
        add(section3);
    }};*/
    /*
     *SHIP3
     * */
    /*public static  List<Section> listShip3ForSection1=new ArrayList<Section>(){{
        add(section4);
    }};
    public static  List<Section> listShip3ForSection2=new ArrayList<Section>(){{
        add(section3);
        add(section4);
        add(section5);
    }};
    public static  List<Section> listShip3ForSection3=new ArrayList<Section>(){{
        add(section2);
        add(section6);
    }};
    public static  List<Section> listShip3ForSection4=new ArrayList<Section>(){{
        add(section1);
        add(section2);
        add(section6);
    }};
    public static  List<Section> listShip3ForSection5=new ArrayList<Section>(){{
        add(section2);
    }};
    public static  List<Section> listShip3ForSection6=new ArrayList<Section>(){{
        add(section4);
    }};*/


    /**
     * CrewMember Variables
     */
    public static CrewMember crewMember0= CrewMember.crewMemberBuilder().health(100)
            .img("Robot").role(Role.FIGHTER).buildCrewMember();
    public static CrewMember crewMember1= CrewMember.crewMemberBuilder().health(200).
            img("Human").role(Role.TECHNICIAN).buildCrewMember();
    public static CrewMember crewMember2= CrewMember.crewMemberBuilder().health(300).
            img("Batman").role(Role.FIGHTER).buildCrewMember();

    public static List<CrewMember> crewMemberList=new ArrayList<CrewMember>(){{
        add(crewMember0);
        add(crewMember1);
        add(crewMember2);
    }};
    public static void updateVariableCrewMembersPlayer(){
        crewMember0=crewMemberList.get(0);
        crewMember1=crewMemberList.get(1);
        crewMember2=crewMemberList.get(2);
    }
    /**
     * Sections Variables
     */
    public static Section section1=Section
            .sectionBuilder()
            .img("Section1")
            .oxygen(100)
            .powerCurrent(100)
            .sectionTyp(SectionTyp.DRIVE)
            .usable(true)
            .connectingTo(null)
            .powerRequired(10)
            .buildSection();
    public static Section section2= Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.WEAPONS)
            .img("Section2")
            .oxygen(100).
            powerCurrent(100)
            .usable(true)
            .connectingTo(null)
            .powerRequired(10)
            .buildSection();
    public static Section section3= Section
            .sectionBuilder()
            .img("Section3")
            .sectionTyp(SectionTyp.NORMAL)
            .oxygen(100)
            .powerCurrent(100)
            .usable(true)
            .connectingTo(null)
            .powerRequired(10)
            .buildSection();
    public static Section section4= Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.NORMAL)
            .img("Section4").oxygen(100).
            powerCurrent(100)
            .usable(true)
            .connectingTo(null)
            .powerRequired(10)
            .buildSection();
    public static Section section5= Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.NORMAL)
            .img("Section5")
            .oxygen(100)
            .powerCurrent(100)
            .usable(true)
            .connectingTo(null)
            .powerRequired(10)
            .buildSection();
    public static Section section6 = Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.NORMAL)
            .img("Section6")
            .oxygen(100).
                    powerCurrent(100)
            .usable(true)
            .connectingTo(null)
            .powerRequired(10)
            .buildSection();

    public  static List<Section> sectionsPlayerList = new ArrayList<Section>() {{
        add(section1);
        add(section2);
        add(section3);
        add(section4);
        add(section5);
        add(section6);
    }};
    public static void updateVariableSectionShipPlayer(){
        section1= sectionsPlayerList.get(0);
        section2= sectionsPlayerList.get(1);
        section3= sectionsPlayerList.get(2);
        section4= sectionsPlayerList.get(3);
        section5= sectionsPlayerList.get(4);
        section6= sectionsPlayerList.get(5);
    }
    /*
     * Gegner
     * */
    public static AI ai1 = AI.builderAI().name("gegner1").buildAI();
    public static AI ai2 = AI.builderAI().name("gegner2").buildAI();

    public static AI ai3 = AI.builderAI().name("gegner3").buildAI();
    public static AI ai4 = AI.builderAI().name("gegner4").buildAI();
    public static AI ai5 = AI.builderAI().name("gegner5").buildAI();
    public static AI ai6 = AI.builderAI().name("gegner6").buildAI();

    public static List<AI> aisU1=new ArrayList<AI>(){{
        add(ai1);
        add(ai2);
    }};
    public static void updateVariableaiu1(){
        ai1=aisU1.get(0);
        ai2=aisU1.get(1);
    }
    public static List<AI> aisU2=new ArrayList<AI>(){{
        add(ai1);
        add(ai2);
        add(ai3);
        add(ai4);
        add(ai5);
        add(ai6);
    }};
    public static void updateVariableaiu2(){
        ai1=aisU2.get(0);
        ai2=aisU2.get(1);
        ai3=aisU2.get(2);
        ai4=aisU2.get(3);
        ai5=aisU2.get(4);
        ai6=aisU2.get(5);
    }
    /*
     * Weapon
     * */
    public static Weapon weapon1Player = Weapon.WeaponBuilder().damage(50).hitRate(100).img("Image1").name("Rocket").build();

    public static Weapon weapon2Player = Weapon.WeaponBuilder().damage(50).hitRate(100).img("Image1").name("Rocket").build();
    public static List<Weapon> weaponListPlayer = new ArrayList<Weapon>(){{
        add(weapon1Player);
        add(weapon2Player);
    }};
    public static void updateweaponPlayerVariabel(){
        weapon1Player= weaponListPlayer.get(0);
        weapon2Player= weaponListPlayer.get(1);
    }

    /*
    Gegner Ship
     */
    public static Ship shipGegner1 = Ship.shipBluider().hp(100).
            power(100).shield(1).
            name("Shipgegner1").owner(ai1).
            buildShip();

    public static Ship shipGegner2 = Ship.shipBluider().hp(100).
            power(100).shield(1).
            name("Shipgegner2").owner(ai2).
            buildShip();
    public static Ship shipGegner3 = Ship.shipBluider().hp(100).
            power(100).shield(1).
            name("Shipgegner3").owner(ai3).
            buildShip();
    public static Ship shipGegner4 = Ship.shipBluider().hp(100).
            power(100).shield(1).
            name("Shipgegner4").owner(ai4).
            buildShip();

    public static Ship shipGegner5 = Ship.shipBluider().hp(100).
            power(100).shield(1).
            name("Shipgegner5").owner(ai5).
            buildShip();
    public static Ship shipGegner6 = Ship.shipBluider().hp(100).
            power(100).shield(1).
            name("Shipgegner6").owner(ai6).
            buildShip();

    public static List<Ship> shipsgegneru1 =new ArrayList<Ship>(){{
        add(shipGegner1);
        add(shipGegner2);

    }};
    public static void updateShipsVariabelgegneru1(){
        shipGegner1=shipsgegneru1.get(0);
        shipGegner2=shipsgegneru1.get(1);
    }
    public static void updateShipsListgegneru1(){
        List<Ship> shipsgegneru1new =new ArrayList<Ship>(){{
            add(shipGegner1);
            add(shipGegner2);
        }};
        shipsgegneru1=shipsgegneru1new;
    }
    // Ship Universe 2
    public static List<Ship> shipsgegneru2 =new ArrayList<Ship>(){{
        add(shipGegner1);
        add(shipGegner2);
        add(shipGegner3);
        add(shipGegner4);
        add(shipGegner5);
        add(shipGegner6);

    }};

    public static void updateShipsVariabelgegneru2(){
        shipGegner1=shipsgegneru2.get(0);
        shipGegner2=shipsgegneru2.get(1);
        shipGegner3=shipsgegneru2.get(2);
        shipGegner4=shipsgegneru2.get(3);
        shipGegner5=shipsgegneru2.get(4);
        shipGegner6=shipsgegneru2.get(5);
    }
    public static void updateShipsListgegneru2(){
        List<Ship> shipsgegneru2new =new ArrayList<Ship>(){{
            add(shipGegner1);
            add(shipGegner2);
            add(shipGegner3);
            add(shipGegner4);
            add(shipGegner5);
            add(shipGegner6);
        }};
        shipsgegneru2=shipsgegneru2new;
    }
    //Sections Gegner

    public static Section section1Gegner = Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.NORMAL)
            .img("Section1Gegner1")
            .oxygen(100)
            .powerCurrent(100)
            .usable(true)
            .connectingTo(null)
            .powerRequired(10)
            .buildSection();
    public static Section section2Gegner = Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.DRIVE)
            .img("Section2Gegner1")
            .oxygen(100)
            .powerCurrent(100)
            .usable(true)
            .connectingTo(null)
            .powerRequired(10)
            .buildSection();
    public static Section section3Gegner = Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.WEAPONS)
            .img("Section3Gegner1")
            .oxygen(100)
            .powerCurrent(100)
            .usable(true)
            .connectingTo(null)
            .powerRequired(10)
            .buildSection();

    public static List<Section> sectionsgegner1 =new ArrayList<Section>(){{
        add(section1Gegner);
        add(section2Gegner);
        add(section3Gegner);
    }};
    public static void updateVariblesSectionsGegner1(){
        section1Gegner=sectionsgegner1.get(0);
        section2Gegner=sectionsgegner1.get(1);
        section3Gegner=sectionsgegner1.get(2);
    }

    public static Section section1Gegner2 = Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.NORMAL)
            .img("Section1Gegner2")
            .oxygen(100)
            .powerCurrent(100)
            .usable(true)
            .connectingTo(null)
            .powerRequired(10)
            .buildSection();
    public static Section section2Gegner2 = Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.DRIVE)
            .img("Section2Gegner2")
            .oxygen(100)
            .powerCurrent(100)
            .usable(true)
            .connectingTo(null)
            .powerRequired(10)
            .buildSection();
    public static Section section3Gegner2 = Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.WEAPONS)
            .img("Section3Gegner2")
            .oxygen(100)
            .powerCurrent(100)
            .usable(true)
            .connectingTo(null)
            .powerRequired(10)
            .buildSection();
    public static List<Section> sectionsgegner2 =new ArrayList<Section>(){{
        add(section1Gegner2);
        add(section2Gegner2);
        add(section3Gegner2);
    }};
    public static void updateVariblesSectionsGegner2(){
        section1Gegner2=sectionsgegner2.get(0);
        section2Gegner2=sectionsgegner2.get(1);
        section3Gegner2=sectionsgegner2.get(2);
    }

    public static Section section1Gegner3 = Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.NORMAL)
            .img("Section1Gegner3")
            .oxygen(100)
            .powerCurrent(100)
            .usable(true)
            .connectingTo(null)
            .powerRequired(10)
            .buildSection();
    public static Section section2Gegner3 = Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.DRIVE)
            .img("Section2Gegner3")
            .oxygen(100)
            .powerCurrent(100)
            .usable(true)
            .connectingTo(null)
            .powerRequired(10)
            .buildSection();
    public static Section section3Gegner3 = Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.WEAPONS)
            .img("Section3Gegner3")
            .oxygen(100)
            .powerCurrent(100)
            .usable(true)
            .connectingTo(null)
            .powerRequired(10)
            .buildSection();

    public static List<Section> sectionsgegner3 =new ArrayList<Section>(){{
        add(section1Gegner3);
        add(section2Gegner3);
        add(section3Gegner3);
    }};
    public static void updateVariblesSectionsGegner3(){
        section1Gegner3=sectionsgegner3.get(0);
        section2Gegner3=sectionsgegner3.get(1);
        section3Gegner3=sectionsgegner3.get(2);
    }

    public static Section section1Gegner4 = Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.NORMAL)
            .img("Section1Gegner4")
            .oxygen(100)
            .powerCurrent(100)
            .usable(true)
            .connectingTo(null)
            .powerRequired(10)
            .buildSection();
    public static Section section2Gegner4 = Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.DRIVE)
            .img("Section2Gegner4")
            .oxygen(100)
            .powerCurrent(100)
            .usable(true)
            .connectingTo(null)
            .powerRequired(10)
            .buildSection();
    public static Section section3Gegner4 = Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.WEAPONS)
            .img("Section3Gegner4")
            .oxygen(100)
            .powerCurrent(100)
            .usable(true)
            .connectingTo(null)
            .powerRequired(10)
            .buildSection();

    public static List<Section> sectionsgegner4 =new ArrayList<Section>(){{
        add(section1Gegner4);
        add(section2Gegner4);
        add(section3Gegner4);
    }};
    public static void updateVariblesSectionsGegner4(){
        section1Gegner4=sectionsgegner4.get(0);
        section2Gegner4=sectionsgegner4.get(1);
        section3Gegner4=sectionsgegner4.get(2);
    }

    public static Section section1Gegner5 = Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.NORMAL)
            .img("Section1Gegner5")
            .oxygen(100)
            .powerCurrent(100)
            .usable(true)
            .connectingTo(null)
            .powerRequired(10)
            .buildSection();
    public static Section section2Gegner5 = Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.DRIVE)
            .img("Section2Gegner5")
            .oxygen(100)
            .powerCurrent(100)
            .usable(true)
            .connectingTo(null)
            .powerRequired(10)
            .buildSection();
    public static Section section3Gegner5 = Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.WEAPONS)
            .img("Section3Gegner5")
            .oxygen(100)
            .powerCurrent(100)
            .usable(true)
            .connectingTo(null)
            .powerRequired(10)
            .buildSection();

    public static List<Section> sectionsgegner5 =new ArrayList<Section>(){{
        add(section1Gegner5);
        add(section2Gegner5);
        add(section3Gegner5);
    }};
    public static void updateVariblesSectionsGegner5(){
        section1Gegner5=sectionsgegner5.get(0);
        section2Gegner5=sectionsgegner5.get(1);
        section3Gegner5=sectionsgegner5.get(2);
    }

    public static Section section1Gegner6 = Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.NORMAL)
            .img("Section1Gegner3")
            .oxygen(100)
            .powerCurrent(100)
            .usable(true)
            .connectingTo(null)
            .powerRequired(10)
            .buildSection();
    public static Section section2Gegner6 = Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.DRIVE)
            .img("Section2Gegner3")
            .oxygen(100)
            .powerCurrent(100)
            .usable(true)
            .connectingTo(null)
            .powerRequired(10)
            .buildSection();
    public static Section section3Gegner6 = Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.WEAPONS)
            .img("Section3Gegner3")
            .oxygen(100)
            .powerCurrent(100)
            .usable(true)
            .connectingTo(null)
            .powerRequired(10)
            .buildSection();

    public static List<Section> sectionsgegner6 =new ArrayList<Section>(){{
        add(section1Gegner6);
        add(section2Gegner6);
        add(section3Gegner6);
    }};
    public static void updateVariblesSectionsGegner6(){
        section1Gegner6=sectionsgegner6.get(0);
        section2Gegner6=sectionsgegner6.get(1);
        section3Gegner6=sectionsgegner6.get(2);
    }
    //*ALL SECTIONS*///
    public static List<Section> sectionsallgame =new ArrayList<Section>(){{
        add(section1);
        add(section2);
        add(section3);
        add(section4);
        add(section5);
        add(section6);
        add(section1Gegner);
        add(section2Gegner);
        add(section3Gegner);
        add(section1Gegner2);
        add(section2Gegner2);
        add(section3Gegner2);
        add(section1Gegner3);
        add(section2Gegner3);
        add(section3Gegner3);
        add(section1Gegner4);
        add(section2Gegner4);
        add(section3Gegner4);
        add(section1Gegner5);
        add(section2Gegner5);
        add(section3Gegner5);
        add(section1Gegner6);
        add(section2Gegner6);
        add(section3Gegner6);
    }};

    public static void updateVariblesAllSections(){
        section1=sectionsallgame.get(0);
        section2=sectionsallgame.get(1);
        section3=sectionsallgame.get(2);
        section4=sectionsallgame.get(3);
        section5=sectionsallgame.get(4);
        section6=sectionsallgame.get(5);
        section1Gegner=sectionsallgame.get(6);
        section2Gegner=sectionsallgame.get(7);
        section3Gegner=sectionsallgame.get(8);
        section1Gegner2=sectionsallgame.get(9);
        section2Gegner2=sectionsallgame.get(10);
        section3Gegner2=sectionsallgame.get(11);
        section1Gegner3=sectionsallgame.get(12);
        section2Gegner3=sectionsallgame.get(13);
        section3Gegner3=sectionsallgame.get(14);
        section1Gegner4=sectionsallgame.get(15);
        section2Gegner4=sectionsallgame.get(16);
        section3Gegner4=sectionsallgame.get(17);
        section1Gegner5=sectionsallgame.get(18);
        section2Gegner5=sectionsallgame.get(19);
        section3Gegner5=sectionsallgame.get(20);
        section1Gegner6=sectionsallgame.get(21);
        section2Gegner6=sectionsallgame.get(22);
        section3Gegner6=sectionsallgame.get(23);

    }

    /**
     * planete Univerise 1
     */
    public static Planet planet1 = Planet.builder().name("p1").img("null").build();
    public static Planet planet2 = Planet.builder().name("p2").img("null").build();
    public static Planet planet3 = Planet.builder().name("p3").img("null").build();
    public static Planet planet4 = Planet.builder().name("p4").img("null").build();
    public static Planet planet5 = Planet.builder().name("p5").img("null").build();
    public static Planet planet6 = Planet.builder().name("p6").img("null").build();
    public static Planet planet7 = Planet.builder().name("p7").img("null").build();
    public static Planet planet8 = Planet.builder().name("p8").img("null").build();
    public static Planet planet9 = Planet.builder().name("p9").img("null").build();
    public static Planet planet10 = Planet.builder().name("p10").img("null").build();
    public static List<Planet> planetListU1 =new ArrayList<Planet>(){{
        add(planet1);
        add(planet2);
        add(planet3);
        add(planet4);
        add(planet5);
    }};
    public static void updateVariblesPlanetsU1(){
        planet1= planetListU1.get(0);
        planet2= planetListU1.get(1);
        planet3= planetListU1.get(2);
        planet4= planetListU1.get(3);
        planet5= planetListU1.get(4);
    }
    public static List<Planet> planetListU2 =new ArrayList<Planet>(){{
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
    public static void updateVariblesPlanetsU2(){
        planet1= planetListU2.get(0);
        planet2= planetListU2.get(1);
        planet3= planetListU2.get(2);
        planet4= planetListU2.get(3);
        planet5= planetListU2.get(4);
        planet6= planetListU2.get(5);
        planet7= planetListU2.get(6);
        planet8= planetListU2.get(7);
        planet9= planetListU2.get(8);
        planet10= planetListU2.get(9);
    }
    /**
     *  Station
     */
    public static Station station1 = Station.stationBuilder().name("station1").energyPrice(0).buildStation();
    public static Station station2 = Station.stationBuilder().name("station2").energyPrice(1).buildStation();
    public static Station station3 = Station.stationBuilder().name("station3").energyPrice(0).buildStation();
    public static Station station4 = Station.stationBuilder().name("station4").energyPrice(1).buildStation();
    public static List<Station> stationListU1 =new ArrayList<Station>(){{
        add(station1);
        add(station2);
    }};
    public static void updateVariblesStationsU1(){
        station1= stationListU1.get(0);
        station2= stationListU1.get(1);
    }
    public static List<Station> stationListU2 =new ArrayList<Station>(){{
        add(station1);
        add(station2);
        add(station3);
        add(station4);
    }};
    public static void updateVariblesStationsU2(){
        station1= stationListU2.get(0);
        station2= stationListU2.get(1);
        station3= stationListU2.get(2);
        station4= stationListU2.get(3);
    }

    public static ShopRessource shopRessource1 = ShopRessource.shopRessourceBuilder().name(RessourceName.GOLD).amount(100).build();
    public static ShopRessource shopRessource2 = ShopRessource.shopRessourceBuilder().name(RessourceName.ENERGIE).amount(100).build();
    public static List<ShopRessource> shopRessourceList =new ArrayList<ShopRessource>(){{
        add(shopRessource1);
        add(shopRessource2);
    }};
    public static void updateVariblesshopRessource(){
        shopRessource1= shopRessourceList.get(0);
        shopRessource2= shopRessourceList.get(1);
    }
    public static ShipRessource shipRessource = ShipRessource.builderShipRessource().amount(100).name(RessourceName.GOLD).build();
    /**
     * Universe Univerise 1
     */
    public static Universe universe1 = Universe.universeBuilder().name("Easy").build();
    public static Universe universe2 = Universe.universeBuilder().name("Normal").build();

    public static List<Ship> shipsP1=new ArrayList<Ship>();
    public static List<Ship> shipsP2=new ArrayList<Ship>();
    public static List<Ship> shipsP3=new ArrayList<Ship>();
    public static List<Ship> shipsP4=new ArrayList<Ship>();
    public static List<Ship> shipsP5=new ArrayList<Ship>();
    public static List<Ship> shipsP6=new ArrayList<Ship>();



}
