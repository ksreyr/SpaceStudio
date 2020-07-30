package de.spaceStudio.server.model;

import javax.persistence.*;

/**
 * @author Miguel Caceres, Santiago Rey
 * modified 06.08.2020
 */
@Entity
@Inheritance(
        strategy = InheritanceType.TABLE_PER_CLASS
)
public class Ressource {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private RessourceName name;
    private int amount;

    public Ressource(RessourceBluider ressourceBluider) {
        setId(ressourceBluider.id);
        setName(ressourceBluider.name);
        setAmount(ressourceBluider.amount);
    }
    public Ressource() {

    }

    public static RessourceBluider builderRessource() {
        return new RessourceBluider();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RessourceName getName() {
        return name;
    }

    public void setName(RessourceName name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public static class RessourceBluider {

        private Integer id;
        private RessourceName name;
        private int amount;

        public RessourceBluider() {
        }

        public RessourceBluider(Integer id, RessourceName name, int amount) {
            this.id = id;
            this.name = name;
            this.amount = amount;
        }

        public RessourceBluider id(Integer id) {
            this.id = id;
            return RessourceBluider.this;
        }

        public RessourceBluider name(RessourceName name) {
            this.name = name;
            return RessourceBluider.this;
        }

        public RessourceBluider amount(int amount) {
            this.amount = amount;
            return RessourceBluider.this;
        }

        public Ressource buildRessource() {
            return new Ressource(this);
        }
    }

}
