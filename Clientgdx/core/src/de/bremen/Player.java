package de.bremen;



/**
 * @author Miguel Caceres
 * 09.05.2020
 */
//@Entity
//@Table(name = "PLAYER")
public class Player {

//    @Column(name = "id")
//    @Id
    Integer id;

//    @Column(name = "name")
    String name;

//    @Column(name = "password")
    String password;

    public Player(Integer id, String name, String password) {
        this.id = id;
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
