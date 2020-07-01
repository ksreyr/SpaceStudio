package de.spaceStudio.server.model;

import javax.persistence.*;

@Entity
public class CollectableItem  extends Ressource {
    @ManyToOne
    private StopAbstract stopAbstract;

}
