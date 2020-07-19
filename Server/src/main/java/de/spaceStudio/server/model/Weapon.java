package de.spaceStudio.server.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

import javax.persistence.*;

@Entity
@Inheritance(
        strategy = InheritanceType.TABLE_PER_CLASS
)
@JsonIdentityInfo(generator= JSOGGenerator.class)
public class Weapon {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    @OneToOne
    private Section objectiv;

    private int hitRate;

    private int damage;

    private long coolDown;
    private long lastShot;

    private String img;

    @ManyToOne
    private Section section;



    public Section getObjectiv() {
        return objectiv;
    }

    public void setObjectiv(Section objectiv) {
        this.objectiv = objectiv;
    }

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

    public Weapon() {
    }
    public Weapon(WeaponBuilder weaponBuilder){
       setId(weaponBuilder.id);
       setHitRate(weaponBuilder.hitRate);
       setDamage(weaponBuilder.damage);
       setImg(weaponBuilder.img);
       setName(weaponBuilder.name);
       setSection(weaponBuilder.section);
       setObjectiv(weaponBuilder.objectiv);
    }
    public static WeaponBuilder WeaponBuilder(){
        return new WeaponBuilder();
    }

    public long getCoolDown() {
        return coolDown;
    }

    public void setCoolDown(int coolDown) {
        this.coolDown = coolDown;
    }

    public long getLastShot() {
        return lastShot;
    }

    public void setLastShot(long lastShot) {
        this.lastShot = lastShot;
    }

    public  static class WeaponBuilder{
        private Integer id;
        private String name;
        private int hitRate;
        private int damage;
        private String img;
        private Section section;
        private  Section objectiv;

        public WeaponBuilder() {
        }

        public WeaponBuilder(Integer id,
                             String name,
                             int hitRate,
                             int damage,
                             String img,
                             Section section,
                             Section objectiv)
        {
            this.id = id;
            this.name = name;
            this.hitRate = hitRate;
            this.damage = damage;
            this.img = img;
            this.section = section;
            this.objectiv=objectiv;

        }
        public WeaponBuilder id(int id){
            this.id= id;
            return WeaponBuilder.this;
        }
        public WeaponBuilder name(String name){
            this.name= name;
            return WeaponBuilder.this;
        }
        public WeaponBuilder hitRate(int hitRate){
            this.hitRate= hitRate;
            return WeaponBuilder.this;
        }
        public WeaponBuilder damage(int damage){
            this.damage= damage;
            return WeaponBuilder.this;
        }
        public WeaponBuilder img(String img){
            this.img= img;
            return WeaponBuilder.this;
        }
        public WeaponBuilder section(Section section){
            this.section= section;
            return WeaponBuilder.this;
        }
        public WeaponBuilder objectiv(Section objectiv){
            this.objectiv= objectiv;
            return WeaponBuilder.this;
        }
        public Weapon build(){
            return new Weapon(this);
        }
    }

}
