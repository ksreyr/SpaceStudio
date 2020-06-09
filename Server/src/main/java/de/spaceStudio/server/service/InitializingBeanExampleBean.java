package de.spaceStudio.server.service;

import de.spaceStudio.server.model.Lasser;
import de.spaceStudio.server.model.Player;

import de.spaceStudio.server.repository.*;

import de.spaceStudio.server.model.*;
import de.spaceStudio.server.repository.PlayerRepository;
import de.spaceStudio.server.repository.RessourceRepository;
import de.spaceStudio.server.repository.ShipRessourceRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitializingBeanExampleBean implements InitializingBean {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private LasserRepository lasserRepository;

    @Autowired
    private ShipRepository shipRepository;

    @Autowired
    private RessourceRepository ressourceRepository;

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
    @Override
    public void afterPropertiesSet() throws Exception {
        /*
        Player Creation
        */
        Player p1=Player.builderPlayer()
                .name("Nick")
                .password("ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f")
                .buildPlayer();
        playerRepository.save(p1);

        playerRepository.save(Player.builderPlayer()
                .name("Judy")
                .password("5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5")
                .buildPlayer());

       /*
        Ship Creation
        */
        Ship  ship1=Ship.shipBluider()
        .hp(3)
        .name("Ship1")
        .owner(p1)
        .power(34)
        .shield(3)
        .buildShip();
        shipRepository.save(ship1);
        /*
        * */
        /*
         * Section
         */
        Section s1=Section.sectionBuilder().img("file://img2")
                .oxygen(35).role(Role.FIGHTER)
                .powerCurrent(35)
                .usable(true).ship(ship1)
                .powerRequired(20)
                .buildSection();
        Section s2=Section.sectionBuilder().img("file://img2")
                .oxygen(35).role(Role.FIGHTER)
                .powerCurrent(35)
                .usable(true).ship(ship1)
                .powerRequired(20)
                .buildSection();
        sectionRepository.save(
                s1
        );
        sectionRepository.save(
                s2
        );
        /*
        * CrewMember Creation
        * */
        crewMemberRepository.save(CrewMember.crewMemberBuilder()
                .img("file://img1")
                .role(Role.FIGHTER)
                .health(100).currentSection(s1).buildCrewMember()
        ) ;
        crewMemberRepository.save(CrewMember.crewMemberBuilder()
                .img("file://img1")
                .role(Role.TECHNICIAN)
                .health(260).currentSection(s1).buildCrewMember()
        ) ;
        /*
        Lasser Creation
        */
        lasserRepository.save(Lasser.builder()
                .name("V1")
                .hitRate(100)
                .build());


        // TODO WEAPONS


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
        shopRessourceRepository.save(
                ShopRessource.shopRessourceBuilder()
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
        universeRepository.save(Universe.universeBuilder()
                .name("univerise1").build());

    }
}