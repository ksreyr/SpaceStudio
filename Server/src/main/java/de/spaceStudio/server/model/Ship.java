package de.spaceStudio.server.model;

import org.springframework.lang.NonNull;

import javax.persistence.*;


@Entity
//@Table(name = "ship") // please fix error: cannot find data source and document how to set it up in intelij
public class Ship {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    // https://stackoverflow.com/questions/18594234/hibernate-best-practices-avoiding-many-to-many-and-exotic-relationships
    // https://invidious.snopyta.org/watch?v=808pWqjuhMo&autoplay=0&continue=0&dark_mode=true&listen=0&local=1&loop=0&nojs=0&player_style=youtube&quality=dash&thin_mode=false
    // https://vladmihalcea.com/manytoone-jpa-hibernate/
    @ManyToOne
    private Player owner;

    //@NonNull
    //private int geld;

    //@NonNull
    //private int energy;

    @NonNull
    private int hp;

    @NonNull
    private int shield;

    @NonNull
    private int power;

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    //public int getGeld() {
        //return geld;
    //}

    //public void setGeld(int geld) {
      //  this.geld = geld;
    //}

    //public int getEnergy() {
      //  return energy;
    //}

    //public void setEnergy(int energy) {
      //  this.energy = energy;
    //}

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getShield() {
        return shield;
    }

    public void setShield(int shield) {
        this.shield = shield;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }
}
