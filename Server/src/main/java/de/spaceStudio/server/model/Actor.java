package de.spaceStudio.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;


@Entity
@Inheritance(
        strategy = InheritanceType.TABLE_PER_CLASS
)
public  class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String name;

    private String password;

    @OneToOne
    private ActorState state;


    @ManyToMany
    private List<StopAbstract> visitedStops;
    @Column(nullable = true)
    @JsonIgnore
    private String savedGame;

    public Actor() {

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ActorState getState() {
        return state;
    }

    public void setState(ActorState state) {
        this.state = state;
    }

    public List<StopAbstract> getVisitedStops() {
        return visitedStops;
    }

    public void setVisitedStops(List<StopAbstract> visitedStops) {
        this.visitedStops = visitedStops;
    }

    public String getSavedGame() {
        return savedGame;
    }

    public void setSavedGame(String savedGame) {
        this.savedGame = savedGame;
    }
}
