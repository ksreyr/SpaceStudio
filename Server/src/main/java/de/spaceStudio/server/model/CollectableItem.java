package de.spaceStudio.server.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
//public class CollectableItem  implements Serializable {
public class CollectableItem  extends Ressource {
    //@Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    //private Integer id;

    //private int price;

    //private float percentage;

    @ManyToOne
    private StopAbstract stopAbstract;

    //@ManyToOne
    //private  Ressource ressource;

}
