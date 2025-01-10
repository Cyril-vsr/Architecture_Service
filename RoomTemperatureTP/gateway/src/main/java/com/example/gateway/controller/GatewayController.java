package com.example.gateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GatewayController {


    @GetMapping("/")
    public String index() {
        return "index"; // Correspond à src/main/resources/templates/room_and_window.html
    }

    //@GetMapping("/windows")
    //public String windows() {
    //    return "windows"; // Refers to src/main/resources/templates/index.html
    //}

    //@GetMapping("/rooms")
   // public String rooms() {
   //     return "rooms"; // Refers to src/main/resources/templates/index.html
   // }
}
