package com.example.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gateway")
public class GatewayController {

    @Autowired
    private UserService userService;

    // Créer un utilisateur
    @PostMapping("/createUser")
    public String createUser(@RequestParam String name, @RequestParam String email, @RequestParam String role) {
        return userService.createUser(name, email, role);
    }

    // Créer une demande d'aide
    @PostMapping("/requests")
    public String createRequest(@RequestParam String userEmail, @RequestParam String description) {
        return userService.createRequest(userEmail, description);
    }

    // Créer un feedback
    @PostMapping("/feedbacks")
    public String createFeedback(@RequestParam String userName, @RequestParam String feedbackText) {
        return userService.createFeedback(userName, feedbackText);
    }
}
