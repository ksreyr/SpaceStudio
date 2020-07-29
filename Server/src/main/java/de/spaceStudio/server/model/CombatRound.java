package de.spaceStudio.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class CombatRound {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    @ManyToMany private List<Weapon> weaponsWhichHaveAttacked = new ArrayList<>();

    @ManyToMany private List<CrewMember> crewMembers = new ArrayList<>();


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Weapon> getWeaponsWhichHaveAttacked() {
        return weaponsWhichHaveAttacked;
    }

    public void setWeaponsWhichHaveAttacked(List<Weapon> weaponsWhichHaveAttacked) {
        this.weaponsWhichHaveAttacked = weaponsWhichHaveAttacked;
    }

    public List<CrewMember> getCrewMembers() {
        return crewMembers;
    }

    public void setCrewMembers(List<CrewMember> crewMembers) {
        this.crewMembers = crewMembers;
    }
}
