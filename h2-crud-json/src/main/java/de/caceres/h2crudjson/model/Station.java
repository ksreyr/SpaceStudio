package de.caceres.h2crudjson.model;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "Station")
public class Station extends StopAbstract{

    @Column
    @MapKey
    private Map<String, Integer> market = new  HashMap<String, Integer>();


    @Column
    @MapKey
    private Map<String, Integer> resourcen = new HashMap<String, Integer>();

}
