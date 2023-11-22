package com.cybersoft.cozaStore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @GetMapping("/hello")
    public String home() {
        System.out.println("Hello method call");
        return "index";
    }
}