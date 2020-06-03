package de.spaceStudio.server.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Station extends StopAbstract{

    private int energyPrice;
    private int hpPrice;

    //@ManyToMany
    //private List<ShopItem> market = new ArrayList<ShopItem>();


    // FIXME Santiago, https://www.baeldung.com/hibernate-persisting-maps
    //@OneToMany
    //private  List<CollectableItem> collectables = new ArrayList<CollectableItem>();

    // Extra Objekt mit Tabelle Statt mit Map
}
