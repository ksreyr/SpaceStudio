package org.spacestudio.resources;

import org.spacestudio.model.Player;
import org.spacestudio.openapi.space.model.Ship;

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


@Path("/players")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PlayerResource {

    private Set<Player> players = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

    public PlayerResource() {
        players.add(new Player("Flying Snowstorm", "Winter player"));
        players.add(new Player("Pineapple Express", "Tropical player"));
    }

    @GET
    public Set<Player> list() {
        return players;
    }

    @POST
    public Set<Player> add(Player player) {
        players.add(player);
        return players;
    }

    @DELETE
    public Set<Player> delete(Player player) {
        players.removeIf(existingPlayer -> existingPlayer.getName().contentEquals(player.getName()));
        return players;
    }
}