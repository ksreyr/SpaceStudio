package de.spaceStudio.server.model;


import javax.persistence.*;

@Entity
public class CrewMember {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne
    private Section currentSection;

    private int health;

    private String img;

    private Role role;

    public CrewMember() {
    }

    public CrewMember(CrewMemberBuilder builder) {
        setId(builder.id);
        setImg(builder.img);
        setHealth(builder.health);
        setCurrentSection(builder.currentSection);
        setRole(builder.role);
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

    public static class CrewMemberBuilder{

        private Integer id;
        private Section currentSection;
        private int health;
        private String img;
        private Role role;

        public CrewMemberBuilder() {
        }

        public CrewMemberBuilder(Integer id, Section currentSection, int health, String img, Role role) {
            this.id = id;
            this.currentSection = currentSection;
            this.health = health;
            this.img = img;
            this.role = role;
        }


        public CrewMemberBuilder id(Integer id){
            this.id=id;
            return CrewMemberBuilder.this;
        }

        public CrewMemberBuilder currentSection(Section currentSection){
            this.currentSection=currentSection;
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
