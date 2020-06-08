package de.spaceStudio.server.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class ShopRessource extends Ressource {

    private int price;

    @ManyToOne
    private Station station;


}
