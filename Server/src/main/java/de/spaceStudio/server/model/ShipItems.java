package de.spaceStudio.server.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;


@Entity
public class ShipItems extends Ressource{
    @ManyToOne
private Ship ship;
}
