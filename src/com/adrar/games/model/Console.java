package com.adrar.games.model;

public class Console {
    //Attributs

    private int id;
    private String name;
    private String manufacturer;

    //Constructeurs
    //Constructeur vide
    public Console() {}

    //Constructeur avec paramètres
    public Console(String name, String manufacturer)
    {
        this.name = name;
        this.manufacturer = manufacturer;
    }
    //Getter et Setters

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String toString()
    {
        return this.id + " : " + this.name + " : " + this.manufacturer;
    }
}
