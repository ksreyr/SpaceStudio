package de.spaceStudio.server.model;

import javax.persistence.*;

@MappedSuperclass
public abstract class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    public Actor(int id) {
        this.id = id;
    }

    public Actor() {

    }
}
