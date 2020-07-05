package de.spaceStudio.server.service;

import de.spaceStudio.server.model.Lasser;
import de.spaceStudio.server.model.Player;

import de.spaceStudio.server.repository.*;

import de.spaceStudio.server.model.*;
import de.spaceStudio.server.repository.PlayerRepository;
import de.spaceStudio.server.repository.ShipRessourceRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Miguel Caceres, Santiago Rey
 * modified 06.08.2020
 */
@Component
public class InitializingBeanExampleBean implements InitializingBean {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private AIRepository aiRepository;

    @Autowired
    private LasserRepository lasserRepository;

    @Autowired
    private MissileRepository missileRepository;

    @Autowired
    public BeamRepository beamRepository;

    @Autowired
    private ShipRepository shipRepository;

    @Autowired
    private RessourceRepository ressourceRepository;

    @Autowired
    private PlanetRepository planetRepository;

    @Autowired
    private ShipRessourceRepository shipRessourceRepository;

    @Autowired
    private ShopRessourceRepository shopRessourceRepository;

    @Autowired
    private CrewMemberRepository crewMemberRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private UniverseRepository universeRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private DriveRepository driveRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        /*
        Player Creation
        */
        Player p1 = Player.builderPlayer()
                .name("Nick")
                .password("asd")
                .buildPlayer();
        playerRepository.save(p1);

        playerRepository.save(Player.builderPlayer()
                .name("Judy")
                .password("123")
                .buildPlayer());

        /*
         * AI
         */
        AI ai1 = AI.builderAI()
                .name("Jarvis")
                .buildAI();

        aiRepository.save(ai1);

        AI ai2 = AI.builderAI()
                .name("Cortana")
                .buildAI();

        aiRepository.save(ai2);

        /*
        Ship Creation
        */

        Ship ship1 = Ship.shipBluider()
                .hp(3)
                .name("Ship1")
                .owner(p1)
                .power(34)
                .shield(3)
                .shipForm(ShipForm.SHIP1)
                .buildShip();
        shipRepository.save(ship1);

        Ship ship2 = Ship.shipBluider()
                .hp(3)
                .name("Ship2")
                .owner(p1)
                .power(34)
                .shield(3)
                .shipForm(ShipForm.SHIP2)
                .buildShip();
        shipRepository.save(ship2);

        Ship ship3 = Ship.shipBluider()
                .hp(3)
                .name("Ship3")
                .owner(p1)
                .power(34)
                .shield(5)
                .shipForm(ShipForm.SHIP3)
                .buildShip();
        shipRepository.save(ship3);

        Ship ship4 = Ship.shipBluider()
                .hp(5)
                .name("Ship4")
                .owner(p1)
                .power(34)
                .shield(2)
                .shipForm(ShipForm.SHIP4)
                .buildShip();
        shipRepository.save(ship4);

        List<Ship> ships = new ArrayList<>();
        ships.add(ship1);
        ships.add(ship2);
        List<Ship> ships2 = new ArrayList<>();
        ships2.add(ship3);
        ships2.add(ship4);

        /*
         * Section
         */
        Section s1 = Section.sectionBuilder().img("file://img2")
                .oxygen(35).role(Role.FIGHTER)
                .powerCurrent(35)
                .usable(true).ship(ship1).sectionTyp(SectionTyp.DRIVE)
                .powerRequired(20)
                .buildSection();
        sectionRepository.save(s1);

        Section s2 = Section.sectionBuilder().img("file://img2")
                .oxygen(35).role(Role.FIGHTER)
                .powerCurrent(35)
                .usable(true).ship(ship1).sectionTyp(SectionTyp.WEAPONS)
                .powerRequired(20)
                .buildSection();
        sectionRepository.save(s2);

        Section s3 = Section.sectionBuilder().img("file://img3")
                .oxygen(55).role(Role.FIGHTER)
                .powerCurrent(35)
                .usable(true).ship(ship2).sectionTyp(SectionTyp.DRIVE)
                .powerRequired(20)
                .buildSection();
        sectionRepository.save(s3);

        Section s4 = Section.sectionBuilder().img("file://img4")
                .oxygen(55).role(Role.FIGHTER)
                .powerCurrent(35)
                .usable(true).ship(ship2).sectionTyp(SectionTyp.WEAPONS)
                .powerRequired(20)
                .buildSection();
        sectionRepository.save(s4);

        /*
         * CrewMember Creation
         */
        crewMemberRepository.save(CrewMember.crewMemberBuilder()
                .img("file://img1").name("Number0")
                .role(Role.FIGHTER)
                .health(100).currentSection(s1).buildCrewMember()
        );

        crewMemberRepository.save(CrewMember.crewMemberBuilder()
                .img("file://img2").name("Number1")
                .role(Role.TECHNICIAN)
                .health(260).currentSection(s1).buildCrewMember()
        );

        /*
        Lasser weapon Creation
        */
        lasserRepository.save(Lasser.builder()
                .name("V1")
                .hitRate(100)
                .damage(20)
                .img("file://img1")
                .section(s1)
                .build());

        /*
        Missile weapon Creation
        */
        missileRepository.save(Missile.builder()
                .name("Nuke rocket")
                .hitRate(300)
                .damage(50)
                .img("file://img1")
                .section(s2)
                .build());

        /**
         * Beam weapon Creation
         */
        beamRepository.save(Beam.builder()
                .name("light")
                .hitRate(50)
                .damage(15)
                .img("file://img")
                .section(s3)
                .build());
        /*
        ShipRessourcen Creation
        */
        shipRessourceRepository.save(ShipRessource
                .builderShipRessource()
                .amount(30)
                .name(RessourceName.ENERGIE)
                .ship(ship1)
                .build());

         /*
        ShipRessourcen Creation
        */
        shopRessourceRepository.save(ShopRessource
                .shopRessourceBuilder()
                .amount(150).name(RessourceName.GOLD)
                .prive(50).build());

        /*
        Ressource Creation
        */
        ressourceRepository.save(Ressource.builderRessource()
                .name(RessourceName.ENERGIE)
                .amount(32)
                .buildRessource());

        /**
         *Universe Creation
         */
        Universe u1 = Universe.universeBuilder()
                .name("univerise1").build();
        universeRepository.save(u1);

        universeRepository.save(Universe.universeBuilder()
                          .name("univerise2")
                          .build());

        /*
         * Planet creation
         */
        planetRepository.save(Planet.builder()
                                    .name("Terra")
                                    .ship(ships)
                                    .universe(u1)
                                    .img("file://data")
                                    .build());

        /**
         * Station*
         */
        stationRepository.save(Station.stationBuilder()
                .energyPrice(30).universe(u1).ship(ships).buildStation());

        stationRepository.save(Station.stationBuilder()
                .energyPrice(45).universe(u1).ship(ships2).buildStation());

        driveRepository.save(Drive.driveBuilder()
                       .speed(60)
                       .section(s1)
                       .build());
        /**
         * Drive
         * todo: Warum hier gibt es ein Roll?????
        driveRepository.save(Drive.driveBuilder().img("file//:")
                .oxygen(35)
                .powerCurrent(46)
                .usable(true)
                .ship(ship1)
                .role(Role.FIGHTER).build());
         */
    }
}