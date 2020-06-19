package de.spaceStudio.server;


import de.spaceStudio.server.repository.WeaponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerCore {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServerCore.class);

	public static void main(String[] args) {
		SpringApplication.run(ServerCore.class, args);
	}

}
