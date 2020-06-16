package de.bremen.server.model;

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
        setSection(builder.section);
    }

    public static MissileBuilder builder() {
        return new MissileBuilder();
    }

    public static class MissileBuilder {

        private String name;
        private Integer hitRate;
        private int damage;
        private String img;
        private Section section;

        /**
         * Empty constructor
         */
        public MissileBuilder() {
        }

        public MissileBuilder(String name, Integer hitRate, int damage, String img, Section section) {
            this.name = name;
            this.hitRate = hitRate;
            this.damage = damage;
            this.img = img;
            this.section = section;
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

        public MissileBuilder section(Section section) {
            this.section = section;
            return MissileBuilder.this;
        }

        public Missile build() {
            return new Missile(this);
        }

    }

}
