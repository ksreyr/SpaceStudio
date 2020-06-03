package de.spaceStudio.server.model;

import javax.persistence.*;

@MappedSuperclass
public abstract class Ressource {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private RessourceName name;

    private int Amount;

}
