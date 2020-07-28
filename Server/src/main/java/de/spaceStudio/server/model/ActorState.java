package de.spaceStudio.server.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public class ActorState {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private LobbyState lobbyState;
    private StopState stopState;
    private FightState fightState;


    public ActorState() {
        this.stopState = StopState.EXPLORING;
        this.lobbyState = LobbyState.WAITING;
        this.fightState = FightState.WAITING_FOR_TURN;
    }

    public LobbyState getLobbyState() {
        return lobbyState;
    }

    public void setLobbyState(LobbyState lobbyState) {
        this.lobbyState = lobbyState;
    }

    public StopState getStopState() {
        return stopState;
    }

    public void setStopState(StopState stopState) {
        this.stopState = stopState;
    }

    public FightState getFightState() {
        return fightState;
    }

    public void setFightState(FightState fightState) {
        this.fightState = fightState;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
