package de.bremen.model;
/**
 * @author Miguel Caceres & Kevin Rey & Liam Hurwitz
 * 09.05.2020
 */
public class Player {

    Integer id;

    String name;

    String password;

    public Player(String name, String password) {

        this.name = name;
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

}
