package de.spaceStudio.server.model;

import javax.persistence.*;

/**
 * @author Miguel Caceres 09.05.2020
 */
@Entity
public class Player extends Actor{

	private String name;

	private String password;

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

}
