package de.spaceStudio.server.model;

import javax.persistence.*;
import java.util.List;


@Entity
public class CombatRound {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    @ManyToMany List<Weapon> attackPlayer1;

    @ManyToMany List<Weapon> attackPlayer2;

    @ManyToMany List<CrewMember> crewMemberPlayer1;

    @ManyToMany List<CrewMember> crewMemberPlayer2;

}
