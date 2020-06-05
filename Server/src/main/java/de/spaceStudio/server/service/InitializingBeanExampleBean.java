package de.spaceStudio.server.service;

import de.spaceStudio.server.model.Player;
import de.spaceStudio.server.repository.PlayerRepository;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.logging.Logger;

@Component
public class InitializingBeanExampleBean implements InitializingBean {

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public void afterPropertiesSet() throws Exception {

        playerRepository.save(new Player(1, "Nick", "ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f"));
        playerRepository.save(new Player(2, "Judy", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5"));

        // TODO Universe

        // TODO Statio

        // TODO Ship

        // TODO Sections

        // TODO WEAPONS

        // TODO Crew
    }
}