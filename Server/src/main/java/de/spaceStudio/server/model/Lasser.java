package de.spaceStudio.server.model;

import javax.persistence.Entity;

@Entity
public class Lasser extends Weapon {


    public Lasser() {
    }

    public Lasser (LasserBuilder lasserBuilder){
        setName(lasserBuilder.name);
        setHitRate(lasserBuilder.hitRate);
    }

    public static LasserBuilder builder(){
        return new LasserBuilder();
    }

    public static class LasserBuilder {

        private String name;
        private  Integer hitRate;

        public LasserBuilder(){
        }
        public LasserBuilder(String name, Integer hitRate) {
            this.name = name;
            this.hitRate = hitRate;
        }

        public LasserBuilder name(String name) {
            this.name = name;
            return LasserBuilder.this;
        }

        public LasserBuilder hitRate(Integer hitRate){
            this.hitRate = hitRate;
            return LasserBuilder.this;
        }

        public Lasser build(){
            return new Lasser(this);
        }
    }
}
