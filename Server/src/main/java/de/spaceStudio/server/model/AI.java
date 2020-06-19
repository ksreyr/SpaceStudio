package de.spaceStudio.server.model;

import javax.persistence.Entity;

@Entity
public class AI extends Actor {


    /**
     * Empty constructor
     */
    public AI() {

    }

    /**
     * Builder constructor
     *
     * @param builder
     */
    public AI(AIBuilder builder) {
        setId(builder.id);
        setName(builder.name);
    }

    public static AIBuilder builderAI() {
        return new AIBuilder();
    }

    /**
     * Builder class
     */
    public static class AIBuilder {

        private Integer id;
        private String name;

        public AIBuilder() {
        }

        public AIBuilder(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        public AIBuilder id(Integer id) {
            this.id = id;
            return AIBuilder.this;
        }

        public AIBuilder name(String name) {
            this.name = name;
            return AIBuilder.this;
        }

        public AI buildAI() {
            return new AI(this);
        }

    }
}
