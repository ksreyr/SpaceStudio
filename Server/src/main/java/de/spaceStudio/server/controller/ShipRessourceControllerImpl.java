package de.spaceStudio.server.controller;

import com.google.gson.Gson;
import de.spaceStudio.server.model.Player;
import de.spaceStudio.server.model.RessourceName;
import de.spaceStudio.server.model.Ship;
import de.spaceStudio.server.model.ShipRessource;
import de.spaceStudio.server.repository.PlayerRepository;
import de.spaceStudio.server.repository.ShipRepository;
import de.spaceStudio.server.repository.ShipRessourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

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
        Player player = playerRepository.findByName(shipRessource.getShip().getOwner().getName()).get();
        Ship ship = shipRepository.findByOwner(player).get();
        shipRessource.setShip(ship);
        ShipRessource shipRessource1 = shipRessourceRepository.save(shipRessource);
        Gson gson = new Gson();
        return gson.toJson(shipRessource1.getId());
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

    @Override
    public String getResourcebyShip(Ship ship) {
        shipRessourceRepository.findByShip(ship);
        Gson gson = new Gson();
        List<ShipRessource> shipRessource = shipRessourceRepository.findByShip(ship).get();
        return gson.toJson(shipRessource);
    }

    @Override
    public Boolean buyFor(Integer id, Integer amount) {
        Optional<Ship> ship = shipRepository.findById(id);

        if (ship.isEmpty()) {
            Optional<List<ShipRessource>> resources = shipRessourceRepository.findByShip(ship.get());
            Optional<ShipRessource> money = Optional.empty();
            for (ShipRessource res :
                    resources.get()) {
                if (res.getName().equals(RessourceName.GOLD));
                money =  Optional.of(res);
            }
            if (money.isPresent()) {
                if (money.get().getAmount() > amount) {
                    money.get().setAmount(money.get().getAmount() - amount);
                    shipRessourceRepository.save(money.get());
                    return true;
                }
            }
        }
        return false;
    }
}
