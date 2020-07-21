package de.spaceStudio.server.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(
        strategy = InheritanceType.TABLE_PER_CLASS
)
@JsonIdentityInfo(generator= JSOGGenerator.class)
public class StopAbstract {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToMany
    private List<Ship> ships;

    @ManyToOne
    private Universe universe;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StopAbstract() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public void setShips(List<Ship> ship) {
        this.ships = ship;
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
