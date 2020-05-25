package de.caceres.h2crudjson.model;

import javax.persistence.*;

public abstract class StopAbstract {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToMany
    private Ship ship;

    @ManyToOne
    private Universe universe;
}
