package de.spaceStudio.server.model;

import javax.persistence.*;

@Entity
@Inheritance(
        strategy = InheritanceType.TABLE_PER_CLASS
)
public  class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String name;

    private String password;

    public Actor() {

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
