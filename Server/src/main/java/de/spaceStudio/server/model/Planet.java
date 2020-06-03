package de.spaceStudio.server.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Planet extends StopAbstract {

    @NotNull
    @Column
    private String img;

}
