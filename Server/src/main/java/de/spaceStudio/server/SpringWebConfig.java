package de.spaceStudio.server;

import de.spaceStudio.server.model.StopAbstract;
import de.spaceStudio.server.repository.StopAbstractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SpringWebConfig {

    /*
    @Autowired
    StopAbstractRepository stopAbstractRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void afterPropertiesSet() {
        StopAbstract stop = new StopAbstract();
        stop.setName("p9");
        stopAbstractRepository.save(stop);
    }
     */
}
