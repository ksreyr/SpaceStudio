package de.spaceStudio.server.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class CollectableItem  extends Ressource {
    @ManyToOne
    private StopAbstract stopAbstract;

}
