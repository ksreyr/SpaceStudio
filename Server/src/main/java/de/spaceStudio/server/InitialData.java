package de.spaceStudio.server;

import de.spaceStudio.server.model.StopAbstract;
import de.spaceStudio.server.repository.StopAbstractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class InitialData implements ApplicationRunner {

    private StopAbstractRepository stopAbstractRepository;

    @Autowired
    public void DataLoader(StopAbstractRepository stopAbstractRepository) {
        this.stopAbstractRepository = stopAbstractRepository;
    }

    /**
     * Create data for Planet9
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        StopAbstract stop = new StopAbstract();
        stop.setName("p9");
        stopAbstractRepository.save(stop);
    }
}