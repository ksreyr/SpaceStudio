package de.spaceStudio.server.model;

import javax.persistence.*;

@Entity
public class Universe  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

}
