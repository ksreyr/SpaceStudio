package de.spaceStudio.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerCore {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerCore.class);

    public static void main(String[] args) {
        LOGGER.info("Starting server...");
        SpringApplication.run(ServerCore.class, args);
    }

}
