package de.spaceStudio.server.model;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.websocket.OnMessage;
import java.util.ArrayList;
import java.util.List;

@Entity
public class GameRound {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToMany
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
}
