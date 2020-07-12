package de.spaceStudio.server.controller;

import com.google.gson.Gson;
import de.spaceStudio.server.model.ShopRessource;
import de.spaceStudio.server.model.Station;
import de.spaceStudio.server.model.StopAbstract;
import de.spaceStudio.server.model.Universe;
import de.spaceStudio.server.repository.ShopRessourceRepository;
import de.spaceStudio.server.repository.StopAbstractRepository;
import de.spaceStudio.server.repository.UniverseRepository;
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
        Universe universe=universeRepository.findByName(shopRessource.getStation().getUniverse().getName()).get();
        List <StopAbstract> stopAbstracts= stopAbstractRepository.findByUniverse(universe).get();
        for (StopAbstract s :
                stopAbstracts) {
            if(s.getName().equals(shopRessource.getStation().getName())){
                shopRessource.setStation((Station) s);
            }
        }
        shopRessourceRepository.save(shopRessource);
        return HttpStatus.CREATED.toString();
    }
    @RequestMapping(value = "/listressourcen", method = RequestMethod.POST)
    public String addShopRessources(@RequestBody List<ShopRessource> shopRessources) {
        /*Universe universe=universeRepository.findByName(shopRessource.getStation().getUniverse().getName()).get();
        List <StopAbstract> stopAbstracts= stopAbstractRepository.findByUniverse(universe).get();
        for (StopAbstract s :
                stopAbstracts) {
            if(s.getName().equals(shopRessource.getStation().getName())){
                shopRessource.setStation((Station) s);
            }
        }
        shopRessourceRepository.save(shopRessource);*/
        List<ShopRessource> shopRessourceList= new ArrayList<>();
        for (ShopRessource  s:
                shopRessources ) {
            ShopRessource shopRessource=shopRessourceRepository.save(s);
            shopRessourceList.add(shopRessource);
        }
        Gson gson=new Gson();
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
}
