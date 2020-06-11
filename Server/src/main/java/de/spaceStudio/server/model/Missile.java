package de.spaceStudio.server.model;

import javax.persistence.Entity;

@Entity
public class Missile extends Weapon {


    public Missile() {

    }

    public Missile(MissileBuilder builder) {
        setName(builder.name);
        setHitRate(builder.hitRate);
        setDamage(builder.damage);
        setImg(builder.img);
        setShip(builder.ship);
    }

    public static MissileBuilder builder() {
        return new MissileBuilder();
    }

    public static class MissileBuilder {

        private String name;
        private Integer hitRate;
        private int damage;
        private String img;
        private Ship ship;

        /**
         * Empty constructor
         */
        public MissileBuilder() {
        }

        public MissileBuilder(String name, Integer hitRate, int damage, String img, Ship ship) {
            this.name = name;
            this.hitRate = hitRate;
            this.damage = damage;
            this.img = img;
            this.ship = ship;
        }

        public MissileBuilder name(String name) {
            this.name = name;
            return MissileBuilder.this;
        }

        public MissileBuilder hitRate(Integer hitRate) {
            this.hitRate = hitRate;
            return MissileBuilder.this;
        }

        public MissileBuilder damage(int damage) {
            this.damage = damage;
            return MissileBuilder.this;
        }

        public MissileBuilder img(String img) {
            this.img = img;
            return MissileBuilder.this;
        }

        public MissileBuilder ship(Ship ship) {
            this.ship = ship;
            return MissileBuilder.this;
        }

        public Missile build() {
            return new Missile(this);
        }

    }

}
