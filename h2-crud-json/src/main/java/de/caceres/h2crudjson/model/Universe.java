package de.caceres.h2crudjson.model;

import javax.persistence.*;

@Entity
@Table(name = "Universe")
public class Universe  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

}
