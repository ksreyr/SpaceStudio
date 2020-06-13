package de.spaceStudio.server.model;

import javax.persistence.*;

/**
 * @author Miguel Caceres, Santiago Rey
 *         modified 06.08.2020
 */
@Entity
public class Player extends Actor {




	private String name;

    /**
     * Empty constructor
     */
    public Player() {


    }
    /**
     * Builder constructor
     * @param builder
     */
    public Player(PlayerBuilder builder) {
        setName(builder.name);
        setPassword(builder.password);
    }

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static PlayerBuilder builderPlayer() {
        return new PlayerBuilder();
    }

    public static class PlayerBuilder {

        private String name;
        private String password;

        public PlayerBuilder() {
        }

        public PlayerBuilder(String name,
                             String password)
        {
            this.name = name;
            this.password = password;
        }


        public PlayerBuilder name(String name) {
            this.name = name;
            return PlayerBuilder.this;
        }

        public PlayerBuilder password(String password) {
            this.password = password;
            return PlayerBuilder.this;
        }

        public Player buildPlayer() {
            return new Player(this);
        }

    }
}
