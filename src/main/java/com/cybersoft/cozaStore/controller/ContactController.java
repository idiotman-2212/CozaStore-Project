package com.cybersoft.cozaStore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contact")
public class ContactController {
    @GetMapping("")
    public String contact(){
        return "contact";
    }
}
