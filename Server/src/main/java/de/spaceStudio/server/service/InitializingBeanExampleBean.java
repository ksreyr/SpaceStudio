package de.spaceStudio.server.service;

import de.spaceStudio.server.model.Planet;
import de.spaceStudio.server.model.StopAbstract;
import de.spaceStudio.server.repository.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Miguel Caceres, Santiago Rey
 * modified 06.08.2020
 */
@Component
public class InitializingBeanExampleBean implements InitializingBean {


    @Autowired
    StopAbstractRepository stopAbstractRepository;

    @Override
    public void afterPropertiesSet() {
        StopAbstract stop = new StopAbstract();
        stop.setName("p9");
        stopAbstractRepository.save(stop);
    }
}