package de.spaceStudio.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.spaceStudio.server.handler.ActorState;

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
    @JsonIgnore
    private ActorState state;


    @ManyToMany
    private List<StopAbstract> visitedStops;

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
}
