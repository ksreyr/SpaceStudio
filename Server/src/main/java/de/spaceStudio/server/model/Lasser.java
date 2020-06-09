package de.spaceStudio.server.model;

import javax.persistence.Entity;

@Entity
public class Lasser extends Weapon {


    public Lasser() {
    }

    public Lasser (LasserBuilder lasserBuilder){
        setName(lasserBuilder.name);
        setHitRate(lasserBuilder.hitRate);
        setDamage(lasserBuilder.damage);
        setImg(lasserBuilder.img);
    }

    public static LasserBuilder builder(){
        return new LasserBuilder();
    }

    public static class LasserBuilder {

        private String name;
        private  Integer hitRate;
        private int damage;
        private String img;

        public LasserBuilder(){
        }
        public LasserBuilder(String name, Integer hitRate, int damage, String img) {
            this.name = name;
            this.hitRate = hitRate;
            this.damage = damage;
            this.img = img;
        }

        public LasserBuilder name(String name) {
            this.name = name;
            return LasserBuilder.this;
        }

        public LasserBuilder hitRate(Integer hitRate){
            this.hitRate = hitRate;
            return LasserBuilder.this;
        }

        public LasserBuilder damage(int damage){
            this.damage = damage;
            return LasserBuilder.this;
        }

        public LasserBuilder img(String img){
            this.img = img;
            return LasserBuilder.this;
        }

        public Lasser build(){
            return new Lasser(this);
        }
    }
}
