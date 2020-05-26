package de.spaceStudio.server.model;

import javax.persistence.*;

@Entity
@Table(name = "Universe")
public class Universe  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

}
