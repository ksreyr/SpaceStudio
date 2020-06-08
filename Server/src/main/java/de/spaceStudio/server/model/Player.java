package de.spaceStudio.server.model;

import javax.persistence.*;

/**
 * @author Miguel Caceres, Santiago Rey
 *         modified 06.08.2020
 */
@Entity
public class Player extends Actor {


    /**
     * Builder constructor
     * @param builder
     */
    public Player(PlayerBuilder builder) {
        setId(builder.id);
        setName(builder.name);
        setPassword(builder.password);
    }

    /**
     * Empty constructor
     */
    public Player() {

    }

    public static PlayerBuilder builder() {

        return new PlayerBuilder();
    }

    public static class PlayerBuilder {

        public PlayerBuilder() {
        }

        private Integer id;
        private String name;
        private String password;

        public PlayerBuilder(Integer id, String name, String password) {
            this.id = id;
            this.name = name;
            this.password = password;
        }


        public PlayerBuilder id(Integer id) {
            this.id = id;
            return PlayerBuilder.this;
        }

        public PlayerBuilder name(String name) {
            this.name = name;
            return PlayerBuilder.this;
        }

        public PlayerBuilder password(String password) {
            this.password = password;
            return PlayerBuilder.this;
        }

        public Player build() {
            return new Player(this);
        }

    }
}
