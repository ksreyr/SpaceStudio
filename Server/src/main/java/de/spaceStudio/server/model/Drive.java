package de.spaceStudio.server.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Drive extends Section {
    private int speed;

    public Drive() {
    }

    public Drive(DriveBuilder driveBuilder) {

    }
    public static DriveBuilder driveBuilder(){
        return new DriveBuilder();
    }
    public static class DriveBuilder{
        private Integer id;
        private Ship ship;
        private Role role;
        private String img;
        private List<Section> connectingTo;
        private float oxygen;
        private int powerRequired;
        private  int powerCurrent;
        private boolean usable;

        public DriveBuilder id(Integer id){
            this.id=id;
            return DriveBuilder.this;
        }
        public DriveBuilder ship(Ship ship){
            this.ship=ship;
            return DriveBuilder.this;
        }
        public DriveBuilder role(Role role){
            this.role=role;
            return DriveBuilder.this;
        }
        public DriveBuilder img(String img){
            this.img=img;
            return DriveBuilder.this;
        }
        public DriveBuilder connectingTo(List<Section> connectingTo){
            this.connectingTo=connectingTo;
            return DriveBuilder.this;
        }
        public DriveBuilder oxygen(float oxygen){
            this.oxygen=oxygen;
            return DriveBuilder.this;
        }
        public DriveBuilder powerRequired(int powerRequired){
            this.powerRequired=powerRequired;
            return DriveBuilder.this;
        }
        public DriveBuilder powerCurrent(int powerCurrent){
            this.powerCurrent=powerCurrent;
            return DriveBuilder.this;
        }
        public DriveBuilder usable(boolean usable){
            this.usable=usable;
            return DriveBuilder.this;
        }
        public Drive build(){
            return new Drive(this);
        }
    }
}
