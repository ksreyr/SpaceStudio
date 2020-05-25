package de.caceres.h2crudjson.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "planet")
public class Planet extends StopAbstract {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Column
    private String img;
}
