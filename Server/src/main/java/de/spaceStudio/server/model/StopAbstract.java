package de.spaceStudio.server.model;

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
