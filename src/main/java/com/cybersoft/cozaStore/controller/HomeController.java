package com.cybersoft.cozaStore.controller;

import com.cybersoft.cozaStore.payload.response.BaseResponse;
import com.cybersoft.cozaStore.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private HomeService homeService;
    @GetMapping("/category")
    public ResponseEntity<?> getAllCategory(){

        BaseResponse response = new BaseResponse();
        response.setData(homeService.getAllCategory());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
