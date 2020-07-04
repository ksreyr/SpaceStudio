package de.spaceStudio.client.util;

import de.spaceStudio.server.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * This class contains global variables, they can be access in the whole project
 *
 * @author Miguel Caceres
 * created on 6.17.2020
 */
public class Global {

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
     * Planet Cretion
     */
    public static final String PLANET_CREATION_ENDPOINT = "/planet";
    /**
     * Universe Cretion
     */
    public static final String UNIVERSE_CREATION_ENDPOINT = "/universe";
    /**
     * Section Cretion
     */
    public static final String SECTION_CREATION_ENDPOINT = "/section";

    /**
     * Global player, this data will be downloaded from server at login
     */
    public static Player currentPlayer;
    /**
     * Server logged player endpoint
     */
    public static final String MAKEJUMP_CREATION_ENDPOINT = "/makejump";

    /**
     * Global player, this data will be downloaded from server at login
     */
    public static Ship currentShip;

    /**
     * Gets all Users
     */
    public static List<String> playersOnline = new ArrayList<>();

    /**
     * Ship Variables
     */
    public static Ship ship0= Ship.shipBluider().hp(10).name("ship0").power(10).shield(1).buildShip();
    public static Ship ship1= Ship.shipBluider().hp(100).name("ship1").power(100).shield(1).buildShip();
    public static Ship ship2= Ship.shipBluider().hp(200).name("ship2").power(50).shield(2).buildShip();
    public static Ship ship3= Ship.shipBluider().hp(300).name("ship3").power(200).shield(1).buildShip();
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
    public static Section section1= Section.sectionBuilder().img("Section1").oxygen(100).
            powerCurrent(100).usable(true).connectingTo(null).powerRequired(10).buildSection();
    public static Section section2= Section.sectionBuilder().img("Section2").oxygen(100).
            powerCurrent(100).usable(true).connectingTo(null).powerRequired(10).buildSection();
    public static Section section3= Section.sectionBuilder().img("Section3").oxygen(100).
            powerCurrent(100).usable(true).connectingTo(null).powerRequired(10).buildSection();
    public static Section section4= Section.sectionBuilder().img("Section4").oxygen(100).
            powerCurrent(100).usable(true).connectingTo(null).powerRequired(10).buildSection();
    public static Section section5= Section.sectionBuilder().img("Section5").oxygen(100).
            powerCurrent(100).usable(true).connectingTo(null).powerRequired(10).buildSection();
    public static Section section6= Section.sectionBuilder().img("Section6").oxygen(100).
            powerCurrent(100).usable(true).connectingTo(null).powerRequired(10).buildSection();

    /**
     * planete Univerise 1
     * */
    public static Planet planet1= Planet.builder().name("p1").img("null").ship(null).build();
    public static Planet planet2= Planet.builder().name("p2").img("null").ship(null).build();
    public static Planet planet3= Planet.builder().name("p3").img("null").ship(null).build();
    public static Planet planet4= Planet.builder().name("p4").img("null").ship(null).build();
    public static Planet planet5= Planet.builder().name("p5").img("null").ship(null).build();

    /**
     * Universe Univerise 1
     * */
    public static Universe universe1= Universe.universeBuilder().name("Easy").build();
    public static Universe universe2= Universe.universeBuilder().name("Normal").build();


}
