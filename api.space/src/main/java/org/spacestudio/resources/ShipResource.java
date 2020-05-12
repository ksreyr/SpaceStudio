package org.spacestudio.resources;

import org.spacestudio.model.Ship;
import org.spacestudio.model.Ship;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

@Path("/ships")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ShipResource {

    private Set<Ship> ships = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

    public ShipResource() {
        ships.add(new Ship("Flying Snowstorm", "Winter ship"));
        ships.add(new Ship("Pineapple Express", "Tropical ship"));
    }

    @GET
    public Set<Ship> list() {
        return ships;
    }

    @POST
    public Set<Ship> add(Ship ship) {
        ships.add(ship);
        return ships;
    }

    @DELETE
    public Set<Ship> delete(Ship ship) {
        ships.removeIf(existingShip -> existingShip.getName().contentEquals(ship.getName()));
        return ships;
    }
}