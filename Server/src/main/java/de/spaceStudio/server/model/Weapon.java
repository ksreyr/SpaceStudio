package de.spaceStudio.server.model;

import javax.persistence.*;

@Entity
public class Weapon extends Section {

    private String name;

    private int hitRate;

    private int dammage;

    private String img;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHitRate() {
        return hitRate;
    }

    public void setHitRate(int hitRate) {
        this.hitRate = hitRate;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getDammage() {
        return dammage;
    }

    public void setDammage(int dammage) {
        this.dammage = dammage;
    }
}
