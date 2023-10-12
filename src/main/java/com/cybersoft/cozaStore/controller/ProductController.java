package com.cybersoft.cozaStore.controller;

import com.cybersoft.cozaStore.service.imp.ProductSeviceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductSeviceImp productSeviceImp;

    @GetMapping("")
    public ResponseEntity<?> getAllProduct(){

        return new ResponseEntity<>("Hello product", HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> insertProduct(@RequestParam String name,
                                           @RequestParam MultipartFile file, @RequestParam double price,
                                           @RequestParam int quantity, @RequestParam int idColor,
                                           @RequestParam int idSize, @RequestParam int idCategory) throws IOException {

        boolean isSussecc = productSeviceImp.insertProduct(name, file, price, quantity, idColor, idSize, idCategory);

        return new ResponseEntity<>("Insert Product", HttpStatus.OK);
    }
}
