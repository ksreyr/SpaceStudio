package de.spaceStudio.server.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;



@Entity
public class ShipRessource extends Ressource{

    @ManyToOne
    private Ship ship;

    public ShipRessource(){}

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public ShipRessource(ShipRessourceBluilder builder){
        setId(builder.id);
        setShip(builder.ship);
        setAmount(builder.amount);
        setName(builder.name);
    }

    public static ShipRessourceBluilder builderShipRessource(){
        return new ShipRessourceBluilder();
    }

    public static class ShipRessourceBluilder{
        private Ship ship;
        private Integer id;
        private RessourceName name;
        private int amount;

        public ShipRessourceBluilder() {
        }

        public ShipRessourceBluilder(Ship ship) {
            this.ship = ship;
        }

        public ShipRessourceBluilder ship(Ship ship){
            this.ship=ship;
            return ShipRessourceBluilder.this;
        }
        public ShipRessourceBluilder name(RessourceName name){
            this.name=name;
            return ShipRessourceBluilder.this;
        }
        public ShipRessourceBluilder amount(int amount){
            this.amount=amount;
            return ShipRessourceBluilder.this;
        }

        public ShipRessource build(){
            return new ShipRessource(this);
        }
    }

}
