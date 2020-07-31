package de.spaceStudio.server.controller;

import com.google.gson.Gson;
import de.spaceStudio.server.model.*;
import de.spaceStudio.server.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ShopRessourceControllerImpl implements ShopRessourceController {
    @Autowired
    UniverseRepository universeRepository;
    @Autowired
    ShopRessourceRepository shopRessourceRepository;
    @Autowired
    StopAbstractRepository stopAbstractRepository;
    @Autowired
    StationRepository stationRepository;
    @Autowired
    ShipRepository shipRepository;
    @Autowired
    ShipRessourceRepository shipRessourceRepository;

    @Override
    public List<ShopRessource> getAllShopRessources() {
        return null;
    }

    @Override
    public ShopRessource getShopRessource(Integer id) {
        return null;
    }

    @Override
    public String addShopRessource(ShopRessource shopRessource) {
        Universe universe = universeRepository.findByName(shopRessource.getStation().getUniverse().getName()).get();
        List<StopAbstract> stopAbstracts = stopAbstractRepository.findByUniverse(universe).get();
        for (StopAbstract s :
                stopAbstracts) {
            if (s.getName().equals(shopRessource.getStation().getName())) {
                shopRessource.setStation((Station) s);
            }
        }
        shopRessourceRepository.save(shopRessource);
        return HttpStatus.CREATED.toString();
    }

    @RequestMapping(value = "/listressourcen", method = RequestMethod.POST)
    public String addShopRessources(@RequestBody List<ShopRessource> shopRessources) {
        List<ShopRessource> shopRessourceList = new ArrayList<>();
        for (ShopRessource s :
                shopRessources) {
            ShopRessource shopRessource = shopRessourceRepository.save(s);
            shopRessourceList.add(shopRessource);
        }
        Gson gson = new Gson();
        return gson.toJson(shopRessourceList);
    }

    @Override
    public ShopRessource updateShopRessource(ShopRessource shopRessource) {
        return null;
    }

    @Override
    public String deleteShopRessourceById(Integer id) {
        return null;
    }

    @Override
    public String deleteAllShopRessources() {
        return null;
    }

    @Override
    public String getShopRessourceByStop(@RequestBody StopAbstract stopAbstract) {
        StopAbstract station = stopAbstractRepository.findById(stopAbstract.getId()).get();
        List<ShopRessource> shopRessources = shopRessourceRepository.findByStation(station).get();
        Gson gson = new Gson();
        return gson.toJson(shopRessources);
    }

    @Override
    public String buyItem(List<ShopRessource> ressourceList) {

        ShopRessource shopRessource = ressourceList.get(0);
        StopAbstract station = shopRessource.getStation();
        Ship ship =new Ship();
        if(station.getShips().size()==1){
            ship=station.getShips().get(0);
        }else{
            ship=station.getShips().get(1);
        }

        List<ShipRessource> shipRessources = shipRessourceRepository.findByShip(ship).get();
        boolean control = false;

        for (ShipRessource sr :
                shipRessources) {
            if (shopRessource.getName().equals(sr.getName())) {
                sr.setAmount(shopRessource.getAmount() + sr.getAmount());
                control = true;
            }
            if (sr.getName().toString().equals("GOLD")) {
                sr.setAmount(sr.getAmount() - shopRessource.getPrice());
            }
            if(shopRessource.getName().toString().equals("ENERGIE")&&shopRessource.getAmount()>0){
                ship.setPower(ship.getPower()+1);
                shipRepository.save(ship);
            }
        }
        if (!control) {
            ShipRessource shipRessource = ShipRessource
                    .builderShipRessource()
                    .name(shopRessource.getName())
                    .amount(shopRessource.getAmount())
                    .ship(ship)
                    .build();
            shipRessourceRepository.save(shipRessource);

        }
        shopRessource.setAmount(0);
        shopRessource.setPrice(0);
        shopRessourceRepository.save(shopRessource);
        Gson gson = new Gson();
        shipRessources = shipRessourceRepository.findByShip(ship).get();
        return gson.toJson(shipRessources);
    }
}
