package de.spaceStudio.server.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(
        strategy = InheritanceType.TABLE_PER_CLASS
)
public class StopAbstract {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToMany
    private List<Ship> ship;

    @ManyToOne
    private Universe universe;

    public StopAbstract() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Ship> getShip() {
        return ship;
    }

    public void setShip(List<Ship> ship) {
        this.ship = ship;
    }

    public Universe getUniverse() {
        return universe;
    }

    public void setUniverse(Universe universe) {
        this.universe = universe;
    }
     public static class StopAbstractBuilder{

     }
}
