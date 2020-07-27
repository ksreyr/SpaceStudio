package de.spaceStudio.server.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class GameRound {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToMany
    private List<Player> fighters;

    @OneToMany
    private List<CombatRound> combatRounds;

    public List<Player> getFighters() {
        return fighters;
    }

    public void setFighters(List<Player> fighters) {
        this.fighters = fighters;
    }

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
}
