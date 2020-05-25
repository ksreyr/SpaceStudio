package de.caceres.h2crudjson.model;

import javax.persistence.*;

@Entity
@Table(name = "Weapon")
public class Weapon {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private int power;

    private int hitrate;

    @ManyToOne
    private Ship ship;
}
