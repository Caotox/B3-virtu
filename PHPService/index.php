<?php
header('Content-Type: text/plain');

$requestUri = $_SERVER['REQUEST_URI'];

if (preg_match('#^/customers/([^/]+)/address#', $requestUri, $matches)) {
    $name = urldecode($matches[1]);
    echo "L'adresse de " . $name . " est 3 rue de la Paix";
} else {
    echo "Tonio";
}
?>
