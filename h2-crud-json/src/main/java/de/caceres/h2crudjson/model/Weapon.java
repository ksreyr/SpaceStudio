package de.caceres.h2crudjson.model;

import javax.persistence.*;

@Entity
public class Weapon {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private int power;

    private int hitRate;

    private String img;

    @ManyToOne
    private Ship ship;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getHitRate() {
        return hitRate;
    }

    public void setHitRate(int hitRate) {
        this.hitRate = hitRate;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
