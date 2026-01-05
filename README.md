Antoine ROCQ

#Liste des commandes effectuées :
Après l'installation de Java, puis :
```bash
cd RentalService
./gradlew build
```
Puis :
```java
java -jar build/libs/RentalService-0.0.1-SNAPSHOT.jar  
```
Et accéder à :
http://localhost:8080/bonjour

Après cela, on reste dans RentalService et :
```bash
touch Dockerfile
nano Dockerfile
```
Et on y met le contenu suivant :
```java
FROM eclipse-temurin:21-jre-jammy
VOLUME /tmp
EXPOSE 8080
ADD ./build/libs/RentalService-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
```
Ensuite :
```bash
docker build –t rental-virtu .
```
Et enfin :
```bash
docker run -p 8080:8080 rental-virtu
```
Et accéder à :
http://localhost:8080/bonjour

## Microservice PHP

Création du dossier PHPService :
```bash
mkdir PHPService
cd PHPService
```

Création du fichier index.php :
```bash
touch index.php
nano index.php
```
Et on y met le contenu suivant :
```php
<?php
header('Content-Type: application/json');

$response = [
    "prenom" => "Antoine",
    "message" => "Bonjour ! Mon prenom est Antoine"
];

echo json_encode($response);
?>
```

Création du Dockerfile :
```bash
touch Dockerfile
nano Dockerfile
```
Et on y met le contenu suivant :
```dockerfile
FROM php:8.2-apache

COPY index.php /var/www/html/

EXPOSE 80
```

Construction de l'image Docker :
```bash
docker build -t phpservice .
```

Test du service :
```bash
docker run -p 8080:80 phpservice
```
Et accéder à :
http://localhost:8080/index.php

Publication sur Docker Hub :
```bash
docker login
docker tag phpservice:latest caotox/phpservice:latest
docker push caotox/phpservice:latest
```

## Communication entre microservices

Modification du fichier BonjourController.java pour ajouter la route /bonjour-php :
```bash
nano RentalService/src/main/java/com/ingnum/rentalservice/controller/BonjourController.java
```

Rebuild du projet Java :
```bash
cd RentalService
./gradlew clean build
```

Création du docker-compose.yml à la racine :
```bash
cd ..
touch docker-compose.yml
nano docker-compose.yml
```
Et on y met le contenu suivant :
```yaml
version: '3.8'

services:
  rental-service:
    build: ./RentalService
    container_name: rental-service
    ports:
      - "8080:8080"
    networks:
      - app-network
    depends_on:
      - php-service

  php-service:
    build: ./PHPService
    container_name: php-service
    ports:
      - "8081:80"
    networks:
      - app-network

networks:
  app-network:
    driver: bridge
```

Lancement des services avec docker-compose :
```bash
docker-compose up --build
```

Tests :
- http://localhost:8080/bonjour
- http://localhost:8081/index.php
- http://localhost:8080/bonjour-php (communication Java -> PHP)

Arrêter les conteneurs :
```bash
docker-compose down
```

Mise à jour de l'image Java sur Docker Hub :
```bash
docker build -t caotox/rental-service:latest ./RentalService
docker push caotox/rental-service:latest
```

## Communication entre vos deux microservices via HTTP

Modification du fichier application.properties pour ajouter l'URL du service PHP :
```bash
nano RentalService/src/main/resources/application.properties
```
Et on y ajoute :
```properties
customer.service.url=http://php-service
```

Modification du BonjourController.java pour ajouter la communication HTTP :
```bash
nano RentalService/src/main/java/com/ingnum/rentalservice/controller/BonjourController.java
```
Et on y ajoute :
```java
@Value("${customer.service.url}")
private String customerServiceUrl;

@GetMapping("/bonjour-php")
public String bonjourPhp() {
    RestTemplate restTemplate = new RestTemplate();
    String name = restTemplate.getForObject(customerServiceUrl, String.class);
    return "bonjour " + name;
}
```

Rebuild du projet :
```bash
cd RentalService
./gradlew clean build
```

Reconstruction et relancement des conteneurs :
```bash
cd ..
docker-compose up --build
```

Test de la communication HTTP entre microservices :
- http://localhost:8080/bonjour-php (Java appelle PHP via HTTP)