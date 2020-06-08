package de.spaceStudio.server.service;

import de.spaceStudio.server.model.Lasser;
import de.spaceStudio.server.model.Player;

import de.spaceStudio.server.repository.*;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitializingBeanExampleBean implements InitializingBean {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private LasserRepository lasserRepository;


    @Override
    public void afterPropertiesSet() throws Exception {

        playerRepository.save(Player.builder().id(1)
                .name("Nick")
                .password("ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f")
                .build());

        playerRepository.save(Player.builder().id(2)
                .name("Judy")
                .password("5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5")
                .build());

        // TODO Universe

        // TODO Statio

        // TODO Ship

        // TODO Sections
        lasserRepository.save(Lasser.builder()
                .name("V1")
                .hitRate(100)
                .build());


        // TODO WEAPONS

        // TODO Crew
    }
}