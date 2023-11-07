package com.cybersoft.cozaStore.controller;

import com.cybersoft.cozaStore.payload.response.BaseResponse;
import com.cybersoft.cozaStore.service.imp.CategoryServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryServiceImp categoryServiceImp;

    @GetMapping("")
    public ResponseEntity<?> getAllCategory(){
        BaseResponse baseresponse = new BaseResponse();
        baseresponse.setData(categoryServiceImp.getAllCategory());

        return new ResponseEntity<>("", HttpStatus.OK);
    }

}
