package com.cartradevn.cartradevn.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index() {
        return "index"; // This should return the name of your home view (e.g., home.html)
    }
    @GetMapping("/fag")
    public String faq() {
        return "faq"; // This should return the name of your FAQ view (e.g., faq.html)
    }
}
