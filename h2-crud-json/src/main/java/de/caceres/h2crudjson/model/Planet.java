package de.caceres.h2crudjson.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Planet")
public class Planet extends StopAbstract {

    @NotNull
    @Column
    private String img;

}
