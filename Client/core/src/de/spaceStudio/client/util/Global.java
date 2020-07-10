package de.spaceStudio.client.util;

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
     * Server logged player endpoint
     */
    public static final String AI_CREATION_ENDPOINT = "/AI";

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
     * Planet Cretion
     */
    public static final String RESSOURCE_SHIP_CREATION_ENDPOINT = "/shipressource";
    /**
     * Planet Cretion
     */
    public static final String RESSOURCE_SHOP_CREATION_ENDPOINT = "/shopressource";

    /**
     * Global player, this data will be downloaded from server at login
     */
    public static Player currentPlayer;
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
    public static  List<Section> listShip0ForSection1=new ArrayList<Section>(){{
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
    }};
    /*
     *SHIP1
     * */
    public static  List<Section> listShip1ForSection1=new ArrayList<Section>(){{
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
    }};
    /*
     *SHIP2
     * */
    public static  List<Section> listShip2ForSection1=new ArrayList<Section>(){{
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
    }};
    /*
     *SHIP3
     * */
    public static  List<Section> listShip3ForSection1=new ArrayList<Section>(){{
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
    }};


    /**
     * CrewMember Variables
     */
    public static CrewMember crewMember0= CrewMember.crewMemberBuilder().health(100)
            .img("Robot").role(Role.FIGHTER).buildCrewMember();
    public static CrewMember crewMember1= CrewMember.crewMemberBuilder().health(200).
            img("Human").role(Role.TECHNICIAN).buildCrewMember();
    public static CrewMember crewMember2= CrewMember.crewMemberBuilder().health(300).
            img("Batman").role(Role.FIGHTER).buildCrewMember();

    /**
     * Sections Variables
     */
    public static Section section1= Section
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
    public static Section section6= Section
            .sectionBuilder()
            .sectionTyp(SectionTyp.NORMAL)
            .img("Section6")
            .oxygen(100).
                    powerCurrent(100)
            .usable(true)
            .connectingTo(null)
            .powerRequired(10)
            .buildSection();
    /*
     * Gegner
     * */
    public static AI ai1= AI.builderAI().name("gegner1").buildAI();
    public static AI ai2= AI.builderAI().name("gegner2").buildAI();

    /*
     * Weapon
     * */
    public static Weapon weapon = Weapon.WeaponBuilder().damage(50).hitRate(100).img("Image1").name("Rocket").build();
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
            name("Shipgegner3").owner(ai2).
            buildShip();


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


    /**
     * planete Univerise 1
     */
    public static Planet planet1 = Planet.builder().name("p1").img("null").ship(null).build();
    public static Planet planet2 = Planet.builder().name("p2").img("null").ship(null).build();
    public static Planet planet3 = Planet.builder().name("p3").img("null").ship(null).build();
    public static Planet planet4 = Planet.builder().name("p4").img("null").ship(null).build();
    public static Planet planet5 = Planet.builder().name("p5").img("null").ship(null).build();
    /**
     *  Station
     */
    public static Station station1 = Station.stationBuilder().name("station1").energyPrice(0).buildStation();
    /**
     *  Station
     */
    public static Station station2 = Station.stationBuilder().name("station2").energyPrice(1).buildStation();

    public static ShopRessource shopRessource1 = ShopRessource.shopRessourceBuilder().name(RessourceName.GOLD).amount(100).build();
    public static ShopRessource shopRessource2 = ShopRessource.shopRessourceBuilder().name(RessourceName.ENERGIE).amount(100).build();
    public static ShipRessource shipRessource1 = ShipRessource.builderShipRessource().amount(100).name(RessourceName.GOLD).build();
    /**
     * Universe Univerise 1
     */
    public static Universe universe1 = Universe.universeBuilder().name("Easy").build();
    public static Universe universe2 = Universe.universeBuilder().name("Normal").build();


    public static ArrayList<Section> sectionofShip(Ship ship) {
        if (ship.getName().equals("shipGegner1")) {
            ArrayList<Section> sectionList = new ArrayList<Section>() {{
                add(section1Gegner);
                add(section2Gegner);
                add(section3Gegner);
            }};
            return sectionList;
        } else if (ship.getName().equals("shipGegner1")) {
            ArrayList<Section> sectionList = new ArrayList<Section>() {{
                add(section1Gegner2);
                add(section2Gegner2);
                add(section3Gegner2);
            }};
            return sectionList;
        } else {
            ArrayList<Section> sectionList = new ArrayList<Section>() {{
                add(section1Gegner3);
                add(section2Gegner3);
                add(section3Gegner3);
            }};
            return sectionList;
        }
    }


}
