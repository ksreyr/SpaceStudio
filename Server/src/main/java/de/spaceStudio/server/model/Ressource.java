package de.spaceStudio.server.model;

import javax.persistence.*;

@Entity
@Inheritance(
        strategy = InheritanceType.TABLE_PER_CLASS
)
public abstract class Ressource {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private RessourceName name;

    private int Amount;

}
