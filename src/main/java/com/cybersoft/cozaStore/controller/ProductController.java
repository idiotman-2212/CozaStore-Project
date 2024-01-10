package com.cybersoft.cozaStore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductController {
    @GetMapping("")
    public String product(){
        return "product";
    }

    @GetMapping("/detail")
    public String productDetail(){
        return "product-detail";
    }
}
