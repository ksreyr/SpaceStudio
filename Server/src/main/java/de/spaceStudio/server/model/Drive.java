package de.spaceStudio.server.model;

import javax.persistence.*;

@Entity
public class Drive {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int speed;

    @OneToOne
    private Section section;

    public Drive() {
    }

    public Drive(DriveBuilder driveBuilder) {
        setSpeed(driveBuilder.speed);
        setSection(driveBuilder.section);
    }

    public static DriveBuilder driveBuilder() {
        return new DriveBuilder();
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public static class DriveBuilder {

        private int speed;
        private Section section;

        public DriveBuilder() {

        }

        public DriveBuilder(int speed, Section section) {
            this.speed = speed;
            this.section = section;
        }

        public DriveBuilder speed(Integer speed) {
            this.speed = speed;
            return DriveBuilder.this;
        }

        public DriveBuilder section(Section section) {
            this.section = section;
            return DriveBuilder.this;
        }

        public Drive build() {
            return new Drive(this);
        }
    }
}
