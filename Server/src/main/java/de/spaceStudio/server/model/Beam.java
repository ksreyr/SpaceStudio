package de.spaceStudio.server.model;

import javax.persistence.Entity;

@Entity
public class Beam extends Weapon {

    public Beam() {

    }

    public Beam(BeamBuilder builder) {
        setName(builder.name);
        setHitRate(builder.hitRate);
        setDamage(builder.damage);
        setImg(builder.img);
        setShip(builder.ship);
    }

    public static BeamBuilder builder() {
        return new BeamBuilder();
    }

    public static class BeamBuilder {

        private String name;
        private Integer hitRate;
        private int damage;
        private String img;
        private Ship ship;

        /**
         * Empty constructor
         */
        public BeamBuilder() {
        }

        public BeamBuilder(String name, Integer hitRate, int damage, String img, Ship ship) {
            this.name = name;
            this.hitRate = hitRate;
            this.damage = damage;
            this.img = img;
            this.ship = ship;
        }

        public BeamBuilder name(String name) {
            this.name = name;
            return BeamBuilder.this;
        }

        public BeamBuilder hitRate(Integer hitRate) {
            this.hitRate = hitRate;
            return BeamBuilder.this;
        }

        public BeamBuilder damage(int damage) {
            this.damage = damage;
            return BeamBuilder.this;
        }

        public BeamBuilder img(String img) {
            this.img = img;
            return BeamBuilder.this;
        }

        public BeamBuilder ship(Ship ship) {
            this.ship = ship;
            return BeamBuilder.this;
        }

        public Beam build() {
            return new Beam(this);
        }

    }

}
