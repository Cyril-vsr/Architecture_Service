package com.example.gateway;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@Service
public class UserService {

    private final RestTemplate restTemplate;

    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String createUser(String name, String email, String role) {
        String url = "http://localhost:8081/users";  // L'URL du User Service

        // Créer un corps de la requête (payload)
        String userPayload = "{ \"name\": \"" + name + "\", \"email\": \"" + email + "\", \"role\": \"" + role + "\" }";
        
        // Créer un en-tête HTTP avec Content-Type: application/json
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        
        // Créer un HttpEntity contenant le corps et les en-têtes
        HttpEntity<String> entity = new HttpEntity<>(userPayload, headers);

        // Envoyer la requête POST avec RestTemplate
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        
        return response.getBody();  // Retourner la réponse du serveur
    }

    public String createRequest(String userEmail, String description) {
        String url = "http://localhost:8082/requests";  // L'URL du Request Service
    
        // Créer un corps de la requête (payload)
        String requestPayload = "{ \"userEmail\": \"" + userEmail + "\", \"description\": \"" + description + "\", \"status\": \"PENDING\" }"; // Valeur par défaut pour status
    
        // Créer un en-tête HTTP avec Content-Type: application/json
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
    
        // Créer un HttpEntity contenant le corps et les en-têtes
        HttpEntity<String> entity = new HttpEntity<>(requestPayload, headers);
    
        // Envoyer la requête POST avec RestTemplate
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    
        return response.getBody();  // Retourner la réponse du serveur
    }
    

    public String createFeedback(String userName, String feedbackText) {
        String url = "http://localhost:8083/feedbacks";
        // Créer un corps de la requête (payload) en s'assurant que les champs nécessaires sont inclus
        String feedbackPayload = "{ \"userName\": \"" + userName + "\", \"feedbackText\": \"" + feedbackText + "\" }";
    
        // Créer un en-tête HTTP avec Content-Type: application/json
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
    
        // Créer un HttpEntity contenant le corps et les en-têtes
        HttpEntity<String> entity = new HttpEntity<>(feedbackPayload, headers);
    
        // Envoyer la requête POST avec RestTemplate
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    
        return response.getBody();  // Retourner la réponse du serveur
    }
    
}
