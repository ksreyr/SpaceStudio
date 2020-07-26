package de.spaceStudio.server.handler;

import de.spaceStudio.server.model.LobbyState;
import de.spaceStudio.server.model.RoundState;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ActorState {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private LobbyState lobbyState;
    private RoundState roundState;

    public ActorState() {
        this.roundState = RoundState.EXPLORING;
        this.lobbyState = LobbyState.WAITING;
    }

    public LobbyState getLobbyState() {
        return lobbyState;
    }

    public void setLobbyState(LobbyState lobbyState) {
        this.lobbyState = lobbyState;
    }

    public RoundState getRoundState() {
        return roundState;
    }

    public void setRoundState(RoundState roundState) {
        this.roundState = roundState;
    }
}
