package de.spaceStudio.server.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Station extends StopAbstract{

    private int energyPrice;

    public Station(StationBuilder stationBuilder) {
    }

    public int getEnergyPrice() {
        return energyPrice;
    }

    public void setEnergyPrice(int energyPrice) {
        this.energyPrice = energyPrice;
    }

    public Station() {
    }

    public static StationBuilder stationBuilder(){
        return new StationBuilder();
    }
    public static class StationBuilder{
        private int energyPrice;
        private Integer id;
        private List<Ship> ship;
        private Universe universe;

        public StationBuilder energyPrice(int energyPrice){
            this.energyPrice=energyPrice;
            return StationBuilder.this;
        }
        public StationBuilder id(Integer id){
            this.id=id;
            return StationBuilder.this;
        }
        public StationBuilder ship(List<Ship> ship){
            this.ship=ship;
            return StationBuilder.this;
        }
        public StationBuilder universe(Universe universe){
            this.universe=universe;
            return StationBuilder.this;
        }
        public Station buildStation(){
            return new Station(this);
        }

    }
}
