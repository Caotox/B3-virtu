package com.ingnum.rentalservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class BonjourController {

    @Value("${customer.service.url}")
    private String customerServiceUrl;

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
}
