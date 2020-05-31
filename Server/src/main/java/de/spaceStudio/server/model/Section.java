package de.spaceStudio.server.model;


import javax.persistence.*;
import java.util.List;

@Entity(name = "Section")
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    private Ship ship;

    @Enumerated(EnumType.STRING)
    private Role role;


    private String img;

    // If there are no connection. One cannot enter this section
    // Eg: One cannot enter a weapon
    // allows for pathfinding in the ship
    @ManyToMany
    // GGF OneToMany weniger probleme wegen bidirectionalität
    private List<Section> connectingTo;

    private float oxygen;

    private int powerRequired;

    // Negativ for Consumption, Positive for Generating
    private  int powerCurrent;

    private boolean usable;

    public List<Section> getConnectingTo() {
        return connectingTo;
    }

    public void setConnectingTo(List<Section> connectingTo) {
        this.connectingTo = connectingTo;
    }

    public int getPowerCurrent() {
        return powerCurrent;
    }

    public void setPowerCurrent(int powerCurrent) {
        this.powerCurrent = powerCurrent;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public float getOxygen() {
        return oxygen;
    }

    public void setOxygen(float oxygen) {
        this.oxygen = oxygen;
    }

    public int getPowerRequired() {
        return powerRequired;
    }

    public void setPowerRequired(int powerRequired) {
        this.powerRequired = powerRequired;
    }

    public boolean isUsable() {
        return usable;
    }

    public void setUsable(boolean usable) {
        this.usable = usable;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

}
