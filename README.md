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
