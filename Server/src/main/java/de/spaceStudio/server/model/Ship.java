package de.spaceStudio.server.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import org.springframework.lang.NonNull;

import javax.persistence.*;


@Entity
@JsonIdentityInfo(generator= JSOGGenerator.class)
public class Ship {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    @NonNull
    private ShipForm shipForm;

    @ManyToOne
    //@JsonManagedReference
    private Actor owner;

    @NonNull
    private int hp;

    @NonNull
    private int shield;

    @NonNull
    private int power;

    @NonNull
    private int money;

    public Ship() {
    }

    public Ship(ShipBluider builder) {
        setId(builder.id);
        setShipForm(builder.shipForm);
        setName(builder.name);
        setOwner(builder.owner);
        setHp(builder.hp);
        setShield(builder.shield);
        setPower(builder.power);
    }

    @NonNull
    public ShipForm getShipForm() {
        return shipForm;
    }

    public void setShipForm(@NonNull ShipForm shipForm) {
        this.shipForm = shipForm;
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

    public Actor getOwner() {
        return owner;
    }

    public void setOwner(Actor owner) {
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

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public static class ShipBluider {

        private Integer id;
        private String name;
        private Actor owner;
        private int hp;
        private int shield;
        private int power;
        private ShipForm shipForm;

        public ShipBluider() {
        }

        public ShipBluider(Integer id, String name,
                           Actor owner, int hp,
                           int shield, int power, ShipForm shipForm)
        {
            this.id = id;
            this.name = name;
            this.owner = owner;
            this.hp = hp;
            this.shipForm=shipForm;
            this.shield = shield;
            this.power = power;
        }
        public ShipBluider shipForm(ShipForm shipForm){
            this.shipForm=shipForm;
            return ShipBluider.this;
        }
        public ShipBluider id(int id) {
            this.id = id;
            return ShipBluider.this;
        }

        public ShipBluider name(String name) {
            this.name = name;
            return ShipBluider.this;
        }

        public ShipBluider owner(Actor owner) {
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
            Ship ship = new Ship(this);
            ship.setMoney(0);
            return ship;
        }
    }
}
