package de.spaceStudio.server.controller;

import com.google.gson.Gson;
import de.spaceStudio.server.model.Player;
import de.spaceStudio.server.model.Ship;
import de.spaceStudio.server.model.ShipRessource;
import de.spaceStudio.server.repository.PlayerRepository;
import de.spaceStudio.server.repository.ShipRepository;
import de.spaceStudio.server.repository.ShipRessourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ShipRessourceControllerImpl implements ShipRessourceController {
    @Autowired
    ShipRessourceRepository shipRessourceRepository;
    @Autowired
    ShipRepository shipRepository;
    @Autowired
    PlayerRepository playerRepository;
    @Override
    public List<ShipRessource> getAllShipRessources() {
        return null;
    }

    @Override
    public ShipRessource getShipRessource(Integer id) {
        return null;
    }

    @Override
    public String addShipRessource(ShipRessource shipRessource) {
        Player player= playerRepository.findByName(shipRessource.getShip().getOwner().getName()).get();
        Ship ship= shipRepository.findByOwner(player).get();
        shipRessource.setShip(ship);
        ShipRessource shipRessource1=shipRessourceRepository.save(shipRessource);
        Gson gson=new Gson();
        return  gson.toJson(shipRessource1.getId());
    }

    @Override
    public ShipRessource updateShipRessource(ShipRessource shipRessource) {
        return null;
    }

    @Override
    public String deleteShipRessourceById(Integer id) {
        return null;
    }

    @Override
    public String deleteAllShipRessources() {
        return null;
    }

    @Override
    public String updateEnergy(ShipRessource shipRessource) {
        return null;
    }
}
