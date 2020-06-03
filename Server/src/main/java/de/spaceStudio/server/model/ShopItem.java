package de.spaceStudio.server.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class ShopItem  extends Ressource {
//public class ShopItem  implements Serializable {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Integer id;

    private int price;

    @ManyToOne
    private Station station;

//    @ManyToOne
//    private Ressource item;

}
