package de.spaceStudio.server.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class CollectableItem  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private int price;
    private float percentage;

    @ManyToOne
    private  Ressource ressource;

}
