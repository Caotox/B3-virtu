package com.ingnum.rentalservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cars")
public class Car {
    
    @Id
    private String plateNumber;
    private String brand;
    private double price;

    // Constructeur par défaut (requis par JPA)
    public Car() {
    }

    // Constructeur avec paramètres
    public Car(String plateNumber, String brand, double price) {
        this.plateNumber = plateNumber;
        this.brand = brand;
        this.price = price;
    }

    // Getters et Setters
    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
