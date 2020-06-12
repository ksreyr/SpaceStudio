package de.spaceStudio.server.model;

import javax.persistence.*;

@Entity
@Inheritance(
        strategy = InheritanceType.TABLE_PER_CLASS
)
public class Weapon {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private int hitRate;

    private int damage;

    private String img;

    @ManyToOne
    private Section section;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHitRate() {
        return hitRate;
    }

    public void setHitRate(int hitRate) {
        this.hitRate = hitRate;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
