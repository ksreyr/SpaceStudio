package de.spaceStudio.server.controller;

import de.spaceStudio.server.model.Ship;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface ShipController {
    @RequestMapping(value = "/ship/login", method = RequestMethod.POST)
    String loginUser(@RequestBody Ship ship);

    @RequestMapping(value = "/ships", method = RequestMethod.GET)
    List<Ship> getAllShips();

    @RequestMapping(value = "/ship/{id}", method = RequestMethod.GET)
    Ship getShip(@PathVariable Integer id);

    @RequestMapping(value = "/ship", method = RequestMethod.POST)
    String addShip(@RequestBody Ship ship);

    @RequestMapping(value = "/ship", method = RequestMethod.PUT)
    Ship updateShip(@RequestBody Ship ship);

    @RequestMapping(value = "/ship/{id}", method = RequestMethod.DELETE)
    String deleteShipById(@PathVariable Integer id);

    @RequestMapping(value = "/ships", method = RequestMethod.DELETE)
    String deleteAllShips();

    String hashPassword(String weakPassword);
}
