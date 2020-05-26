package de.spaceStudio.server.model;


import javax.persistence.*;

@Entity(name = "Crewmember")
public class CrewMember {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    private Section currentSection;

    private int health;

    private String img;

    private Role role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Section getCurrentSection() {
        return currentSection;
    }

    public void setCurrentSection(Section currentSection) {
        this.currentSection = currentSection;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
