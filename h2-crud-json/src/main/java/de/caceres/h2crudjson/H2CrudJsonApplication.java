package de.caceres.h2crudjson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class H2CrudJsonApplication {

	public static void main(String[] args) {
		SpringApplication.run(H2CrudJsonApplication.class, args);
	}

}
