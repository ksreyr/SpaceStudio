package de.spaceStudio.server.model;


import javax.persistence.*;
import java.util.Objects;

@Entity
public class CrewMember {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne
    private Section currentSection;

    private int health;

    private String name;

    private String img;

    private Role role;

    private int skillCounter;

    private int roundsToDestination;

    public CrewMember() {
    }

    public CrewMember(CrewMemberBuilder builder) {
        setId(builder.id);
        setImg(builder.img);
        setHealth(builder.health);
        setName(builder.name);
        setCurrentSection(builder.currentSection);
        setRole(builder.role);
        this.skillCounter = 0;
        this.roundsToDestination  = 0;
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

    public static CrewMemberBuilder crewMemberBuilder(){
        return new CrewMemberBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrewMember that = (CrewMember) o;
        return id.equals(that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public int getSkillCounter() {
        return skillCounter;
    }

    public void setSkillCounter(int skillCounter) {
        this.skillCounter = skillCounter;
    }

    public int getRoundsToDestination() {
        return roundsToDestination;
    }

    public void setRoundsToDestination(int roundsToDestination) {
        this.roundsToDestination = roundsToDestination;
    }

    public static class CrewMemberBuilder{

        private Integer id;
        private Section currentSection;
        private int health;
        private String img;
        private Role role;
        private String name;

        public CrewMemberBuilder() {
        }

        public CrewMemberBuilder(Integer id, Section currentSection,
                                 String name, int health, String img, Role role) {
            this.id = id;
            this.currentSection = currentSection;
            this.health = health;
            this.img = img;
            this.role = role;
            this.name=name;
        }


        public CrewMemberBuilder id(Integer id){
            this.id=id;
            return CrewMemberBuilder.this;
        }

        public CrewMemberBuilder currentSection(Section currentSection){
            this.currentSection=currentSection;
            return CrewMemberBuilder.this;
        }
        public CrewMemberBuilder name(String name){
            this.name=name;
            return CrewMemberBuilder.this;
        }
        public CrewMemberBuilder health(int health){
            this.health=health;
            return CrewMemberBuilder.this;
        }
        public CrewMemberBuilder img(String img){
            this.img=img;
            return CrewMemberBuilder.this;
        }
        public CrewMemberBuilder role(Role role){
            this.role=role;
            return CrewMemberBuilder.this;
        }
        public CrewMember buildCrewMember(){
            return new CrewMember(this);
        }


    }
}
