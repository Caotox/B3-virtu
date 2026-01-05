package com.ingnum.rentalservice.repository;

import com.ingnum.rentalservice.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, String> {
    // Spring Data JPA génère automatiquement l'implémentation !
    // Méthodes disponibles automatiquement :
    // - findAll() : récupère toutes les voitures
    // - findById(String plateNumber) : récupère une voiture par sa plaque
    // - save(Car car) : sauvegarde ou met à jour une voiture
    // - deleteById(String plateNumber) : supprime une voiture
    // - count() : compte le nombre de voitures
}
