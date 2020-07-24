package de.spaceStudio.server.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.List;
//TODO: @MAPPING_SUPER_CLASS
//or it is not created a table in the DB
//Link to reference Sprint Documentation:
// https://www.baeldung.com/hibernate-inheritance

@Entity
@Inheritance(
        strategy = InheritanceType.TABLE_PER_CLASS
)
@NamedQuery(name = "Section.findByShip", query = "SELECT p FROM Section p WHERE  p.ship = ?1")
@JsonIdentityInfo(generator= JSOGGenerator.class)
public class Section {
    @Id
    //@JsonValue
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    private Ship ship;

    @Enumerated(EnumType.STRING)
    private Role role;

    @NonNull
    private SectionTyp sectionTyp;

    private String img;

    @ManyToMany
    //@JsonBackReference
    private List<Section> connectingTo;

    private float oxygen;

    private int powerRequired;

    // Negativ for Consumption, Positive for Generating
    private  int powerCurrent;

    private boolean usable;

    private float xPos;

    private float yPos;

    private double lastUpdated;

    public Section() {
    }
    public Section(SectionBuilder builder) {
        setId(builder.id);
        setImg(builder.img);
        setOxygen(builder.oxygen);
        setPowerCurrent(builder.powerCurrent);
        setRole(builder.role);
        setPowerCurrent(builder.powerCurrent);
        setPowerRequired(builder.powerRequired);
        setShip(builder.ship);
        setUsable(builder.usable);
        setConnectingTo(builder.connectingTo);
        setSectionTyp(builder.sectionTyp);
        setxPos(builder.xPos);
        setyPos(builder.yPos);
    }

    @NonNull
    public SectionTyp getSectionTyp() {
        return sectionTyp;
    }

    public void setSectionTyp(@NonNull SectionTyp sectionTyp) {
        this.sectionTyp = sectionTyp;
    }

    public List<Section> getConnectingTo() {
        return connectingTo;
    }

    public void setConnectingTo(List<Section> connectingTo) {
        this.connectingTo = connectingTo;
    }

    public int getPowerCurrent() {
        return powerCurrent;
    }

    public void setPowerCurrent(int powerCurrent) {
        this.powerCurrent = powerCurrent;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public float getOxygen() {
        return oxygen;
    }

    public void setOxygen(float oxygen) {
        this.oxygen = oxygen;
    }

    public int getPowerRequired() {
        return powerRequired;
    }

    public void setPowerRequired(int powerRequired) {
        this.powerRequired = powerRequired;
    }

    public boolean getUsable() {
        return usable;
    }

    public void setUsable(boolean usable) {
        this.usable = usable;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public static SectionBuilder sectionBuilder(){
        return new SectionBuilder();
    }

    public float getyPos() {
        return yPos;
    }

    public void setyPos(float yPos) {
        this.yPos = yPos;
    }

    public float getxPos() {
        return xPos;
    }

    public void setxPos(float xPos) {
        this.xPos = xPos;
    }

    public double getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(double lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public static class SectionBuilder{
        private Integer id;
        private Ship ship;
        private Role role;
        private String img;
        private List<Section> connectingTo;
        private float oxygen;
        private int powerRequired;
        private  int powerCurrent;
        private boolean usable;
        private SectionTyp sectionTyp;
        private float yPos;
        private float xPos;

        public SectionBuilder id(Integer id){
            this.id=id;
            return SectionBuilder.this;
        }
        public SectionBuilder sectionTyp(SectionTyp sectionTyp){
            this.sectionTyp=sectionTyp;
            return SectionBuilder.this;
        }
        public SectionBuilder ship(Ship ship){
            this.ship=ship;
            return SectionBuilder.this;
        }
        public SectionBuilder role(Role role){
            this.role=role;
            return SectionBuilder.this;
        }
        public SectionBuilder img(String img){
            this.img=img;
            return SectionBuilder.this;
        }
        public SectionBuilder connectingTo(List<Section> connectingTo){
            this.connectingTo=connectingTo;
            return SectionBuilder.this;
        }
        public SectionBuilder oxygen(float oxygen){
            this.oxygen=oxygen;
            return SectionBuilder.this;
        }
        public SectionBuilder powerRequired(int powerRequired){
            this.powerCurrent=powerRequired;
            return SectionBuilder.this;
        }
        public SectionBuilder powerCurrent(int powerCurrent){
            this.powerCurrent=powerCurrent;
            return SectionBuilder.this;
        }
        public SectionBuilder usable(boolean usable){
            this.usable=usable;
            return SectionBuilder.this;
        }
        public SectionBuilder yPos(float yPos){
            this.yPos=yPos;
            return SectionBuilder.this;
        }
        public SectionBuilder xPos(float xPos){
            this.xPos=xPos;
            return SectionBuilder.this;
        }
        public Section buildSection(){
            return new Section(this);
        }
        public SectionBuilder pos(float x, float y) {
            this.yPos = y;
            this.xPos = x;
            return SectionBuilder.this;
        }
    }

}
