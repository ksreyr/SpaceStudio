package de.spaceStudio.server;

import de.spaceStudio.server.repository.WeaponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerCore {

	public static void main(String[] args) {
		SpringApplication.run(ServerCore.class, args);
	}

}
