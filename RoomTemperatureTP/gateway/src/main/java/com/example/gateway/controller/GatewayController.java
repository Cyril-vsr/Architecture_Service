package com.example.gateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GatewayController {

    @GetMapping("/")
    public String windows() {
        return "windows"; // Refers to src/main/resources/templates/index.html
    }

    @GetMapping("/index")
    public String index() {
        return "index"; // Refers to src/main/resources/templates/index.html
    }
}
