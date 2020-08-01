package de.spaceStudio.server.model;

import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class GameRound {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    @ManyToOne
    private Actor actor;

    @OneToMany(cascade=CascadeType.ALL)
    private List<CombatRound> combatRounds = new ArrayList<>();

    @ManyToOne
    private StopAbstract currentStop;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<CombatRound> getCombatRounds() {
        return combatRounds;
    }

    public void setCombatRounds(List<CombatRound> combatRounds) {
        this.combatRounds = combatRounds;
    }

    public StopAbstract getCurrentStop() {
        return currentStop;
    }

    public void setCurrentStop(StopAbstract currentStop) {
        this.currentStop = currentStop;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }
}
