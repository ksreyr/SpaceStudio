package de.spaceStudio.server.model;

import org.springframework.lang.NonNull;

import javax.persistence.*;


@Entity
public class Ship {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    @ManyToOne
    private Player owner;

    @NonNull
    private int hp;

    @NonNull
    private int shield;

    @NonNull
    private int power;

    public Ship() {
    }

    public Ship(ShipBluider builder) {
        setId(builder.id);
        setName(builder.name);
        setOwner(builder.owner);
        setHp(builder.hp);
        setShield(builder.shield);
        setPower(builder.power);
    }

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

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
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

    public static ShipBluider shipBluider(){
        return new ShipBluider();
    }

    public static class ShipBluider {

        private Integer id;
        private String name;
        private Player owner;
        private int hp;
        private int shield;
        private int power;

        public ShipBluider() {
        }

        public ShipBluider(Integer id, String name,
                           Player owner, int hp,
                           int shield, int power)
        {
            this.id = id;
            this.name = name;
            this.owner = owner;
            this.hp = hp;
            this.shield = shield;
            this.power = power;
        }

        public ShipBluider id(int id) {
            this.id = id;
            return ShipBluider.this;
        }

        public ShipBluider name(String name) {
            this.name = name;
            return ShipBluider.this;
        }

        public ShipBluider owner(Player owner) {
            this.owner = owner;
            return ShipBluider.this;
        }

        public ShipBluider hp(int hp) {
            this.hp = hp;
            return ShipBluider.this;
        }

        public ShipBluider shield(int shield) {
            this.shield = shield;
            return ShipBluider.this;
        }

        public ShipBluider power(int power) {
            this.power = power;
            return ShipBluider.this;
        }

        public Ship buildShip() {
            return new Ship(this);
        }
    }
}
