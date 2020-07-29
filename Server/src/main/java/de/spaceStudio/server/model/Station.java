package de.spaceStudio.server.model;

import javax.persistence.Entity;
import java.util.List;

@Entity
public class Station extends StopAbstract {

    private int energyPrice;


    public Station(StationBuilder stationBuilder) {
        setName(stationBuilder.name);
        setUniverse(stationBuilder.universe);
        setId(stationBuilder.id);
        setShips(stationBuilder.ships);
        setEnergyPrice(stationBuilder.energyPrice);
    }

    public Station() {
    }

    public static StationBuilder stationBuilder() {
        return new StationBuilder();
    }

    public int getEnergyPrice() {
        return energyPrice;
    }

    public void setEnergyPrice(int energyPrice) {
        this.energyPrice = energyPrice;
    }

    public static class StationBuilder {
        private int energyPrice;
        private Integer id;
        private List<Ship> ships;
        private Universe universe;
        private String name;

        public StationBuilder energyPrice(int energyPrice) {
            this.energyPrice = energyPrice;
            return StationBuilder.this;
        }

        public StationBuilder name(String name) {
            this.name = name;
            return StationBuilder.this;
        }

        public StationBuilder id(Integer id) {
            this.id = id;
            return StationBuilder.this;
        }

        public StationBuilder ships(List<Ship> ships) {
            this.ships = ships;
            return StationBuilder.this;
        }

        public StationBuilder universe(Universe universe) {
            this.universe = universe;
            return StationBuilder.this;
        }

        public Station buildStation() {
            return new Station(this);
        }

    }
}
