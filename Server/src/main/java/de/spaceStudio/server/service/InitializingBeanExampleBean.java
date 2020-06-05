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
        playerRepository.save(new Player(1, "Nick", "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8"));
        playerRepository.save(new Player(2, "Judy", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5"));
    }
}