package com.ingnum.rentalservice.controller;

import com.ingnum.rentalservice.model.Car;
import com.ingnum.rentalservice.repository.CarRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class BonjourController {

    private static final Logger logger = LoggerFactory.getLogger(BonjourController.class);

    @Value("${customer.service.url}")
    private String customerServiceUrl;

    private final CarRepository carRepository;

    // Injection du repository par constructeur (recommandé)
    public BonjourController(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    // Initialisation de la base de données au démarrage
    @PostConstruct
    public void initDatabase() {
        if (carRepository.count() == 0) {
            logger.info("Initializing database with cars...");
            carRepository.save(new Car("AA-123-BB", "Renault", 45.0));
            carRepository.save(new Car("CC-456-DD", "Peugeot", 50.0));
            carRepository.save(new Car("EE-789-FF", "Citroën", 48.0));
            carRepository.save(new Car("GG-012-HH", "BMW", 80.0));
            carRepository.save(new Car("II-345-JJ", "Mercedes", 90.0));
            logger.info("Database initialized with {} cars", carRepository.count());
        } else {
            logger.info("Database already contains {} cars", carRepository.count());
        }
    }

    @GetMapping("/bonjour")
    public String bonjour() {
        return "bonjour";
    }

    @GetMapping("/bonjour-php")
    public String bonjourPhp() {
        RestTemplate restTemplate = new RestTemplate();
        String name = restTemplate.getForObject(customerServiceUrl, String.class);
        return "bonjour " + name;
    }

    @GetMapping("/customer/{name}")
    public String customer(@PathVariable String name) {
        RestTemplate restTemplate = new RestTemplate();
        String url = customerServiceUrl + "/customers/" + name + "/address";
        String response = restTemplate.getForObject(url, String.class);
        return response;
    }

    // Nouveau endpoint : récupérer toutes les voitures depuis la base de données
    @GetMapping("/cars")
    public List<Car> getCars() {
        logger.info("Fetching all cars from database");
        return carRepository.findAll();
    }

    // Nouveau endpoint : récupérer une voiture par sa plaque
    @GetMapping("/cars/{plateNumber}")
    public Car getCar(@PathVariable String plateNumber) {
        logger.info("Fetching car with plate number: {}", plateNumber);
        return carRepository.findById(plateNumber)
                .orElseThrow(() -> new RuntimeException("Car not found with plate number: " + plateNumber));
    }
}
