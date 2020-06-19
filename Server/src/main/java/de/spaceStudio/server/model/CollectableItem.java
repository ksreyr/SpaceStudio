package de.spaceStudio.server.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class CollectableItem  extends Ressource {
    @ManyToOne
    private StopAbstract stopAbstract;

}
