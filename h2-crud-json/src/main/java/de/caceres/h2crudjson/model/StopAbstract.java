package de.caceres.h2crudjson.model;

import javax.persistence.*;
import java.util.List;

@Entity
public abstract class StopAbstract {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToMany
    private List<Ship> ship;

    @ManyToOne
    private Universe universe;
}
