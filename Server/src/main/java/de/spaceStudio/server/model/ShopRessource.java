package de.spaceStudio.server.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class ShopRessource extends Ressource {

    private int price;

    @ManyToOne
    private Station station;

    public ShopRessource(){
    }

    public ShopRessource(ShopRessourceBuilder builder){
        setPrice(builder.price);
        setStation(builder.station);
        setAmount(builder.amount);
        setId(builder.id);
        setName(builder.name);
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public static ShopRessourceBuilder shopRessourceBuilder(){
        return new ShopRessourceBuilder();
    }

    public static class ShopRessourceBuilder{

        private int price;
        private Station station;
        private Integer id;
        private RessourceName name;
        private int amount;

        public ShopRessourceBuilder id( Integer id){
            this.id=id;
            return ShopRessourceBuilder.this;
        }
        public ShopRessourceBuilder name( RessourceName name){
            this.name=name;
            return ShopRessourceBuilder.this;
        }
        public ShopRessourceBuilder amount(int amount){
            this.amount=amount;
            return ShopRessourceBuilder.this;
        }
        public ShopRessourceBuilder prive( int price){
            this.price=price;
            return ShopRessourceBuilder.this;
        }
        public ShopRessourceBuilder station(Station station){
            this.station=station;
            return ShopRessourceBuilder.this;
        }
        public ShopRessource build(){
            return new ShopRessource(this);
        }
    }
}
