<?php
header('Content-Type: application/json');

$response = [
    "prenom" => "Antoine",
    "message" => "Bonjour ! Mon prenom est Antoine"
];

echo json_encode($response);
?>
