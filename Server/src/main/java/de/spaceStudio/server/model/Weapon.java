package de.spaceStudio.server.model;

import javax.persistence.*;
import java.security.PublicKey;

@Entity
@Inheritance(
        strategy = InheritanceType.TABLE_PER_CLASS
)
public class Weapon {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    @OneToOne
    private Section objectiv;

    private float hitRate;

    private int damage;

    private int warmUp;
    private int warmUpTime;

    private int magazineSize;
    private int currentBullets;


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

    public float getHitRate() {
        return hitRate;
    }

    public void setHitRate(float hitRate) {
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
       setWarmUpTime(weaponBuilder.warmUp);
       setWarmUp(weaponBuilder.warmUp);
       setMagazineSize(weaponBuilder.magazinSize);
       setCurrentBullets(weaponBuilder.magazinSize);
    }

    public static WeaponBuilder WeaponBuilder(){
        return new WeaponBuilder();
    }

    public int getWarmUp() {
        return warmUp;
    }

    public void setWarmUp(int coolDown) {
        this.warmUp = coolDown;
    }

    public int getCurrentBullets() {
        return currentBullets;
    }

    public void setCurrentBullets(int lastShot) {
        this.currentBullets = lastShot;
    }

    public int getMagazineSize() {
        return magazineSize;
    }

    public void setMagazineSize(int magazineSize) {
        this.magazineSize = magazineSize;
    }

    public int getWarmUpTime() {
        return warmUpTime;
    }

    public void setWarmUpTime(int warmUpTime) {
        this.warmUpTime = warmUpTime;
    }

    public  static class WeaponBuilder{
        public int warmUp;
        private Integer id;
        private String name;
        private int hitRate;
        private int damage;
        private String img;
        private Section section;
        private  Section objectiv;
        public int magazinSize;

        public WeaponBuilder() {
        }

        public WeaponBuilder(Integer id,
                             String name,
                             int hitRate,
                             int damage,
                             String img,
                             Section section,
                             Section objectiv,
                             int warmUp,
                             int magazinSize)
        {
            this.id = id;
            this.name = name;
            this.hitRate = hitRate;
            this.damage = damage;
            this.img = img;
            this.section = section;
            this.objectiv=objectiv;
            this.warmUp = warmUp;
            this.magazinSize = magazinSize;

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

        public WeaponBuilder warmUp(int coolDown){
            this.warmUp = coolDown;
            return WeaponBuilder.this;
        }

        public WeaponBuilder magazinSize(int magazinSize) {
            this.magazinSize = magazinSize;
            return WeaponBuilder.this;
        }


        public Weapon build(){
            return new Weapon(this);
        }
    }

}
