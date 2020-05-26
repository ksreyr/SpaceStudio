package de.caceres.h2crudjson.model;

import org.springframework.lang.NonNull;

import javax.persistence.*;


@Entity(name = "Ship")
//@Table(name = "ship") // please fix error: cannot find data source and document how to set it up in intelij
public class Ship {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    // https://stackoverflow.com/questions/18594234/hibernate-best-practices-avoiding-many-to-many-and-exotic-relationships
    // https://invidious.snopyta.org/watch?v=808pWqjuhMo&autoplay=0&continue=0&dark_mode=true&listen=0&local=1&loop=0&nojs=0&player_style=youtube&quality=dash&thin_mode=false
    // https://vladmihalcea.com/manytoone-jpa-hibernate/

    // There is nothing intrinsically wrong with using @ManyToMany but in practice, you rarely have the opportunity to use it
    // Many to Many würde in Player stehen, ist aber falsch. Bitte so implementieren das wir uns an Hibernate best practices orientieren.
    @ManyToOne// Many Ships belongs to one Owner. Required for mapping mapping the foreign key column
    // @JoinColumn(name = "ship_id") // The @JoinColumn annotation allows you to specify the Foreign Key column name. Can be omitted.
    private Actor owner;

    @NonNull
    private int geld;

    @NonNull
    private int energy;

    @NonNull
    private int hp;

    @NonNull
    private int shield;

    @NonNull
    private int power;

    public Actor getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public int getGeld() {
        return geld;
    }

    public void setGeld(int geld) {
        this.geld = geld;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

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
