FROM php:8.2-apache

RUN a2enmod rewrite

COPY index.php /var/www/html/
COPY .htaccess /var/www/html/

EXPOSE 80

