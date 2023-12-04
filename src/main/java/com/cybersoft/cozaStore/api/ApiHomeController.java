package com.cybersoft.cozaStore.api;

import com.cybersoft.cozaStore.payload.response.BaseResponse;
import com.cybersoft.cozaStore.repository.CategoryRepository;
import com.cybersoft.cozaStore.service.imp.CategoryServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/home")
public class ApiHomeController {

    @Autowired
    private CategoryServiceImp categoryServiceImp;

    @Autowired
    private CategoryRepository categoryRepository;
    @GetMapping("/category")
    public ResponseEntity<?> getNewestCategories(){

        BaseResponse response = new BaseResponse();
        response.setData(categoryServiceImp.getNewestCategories());
        response.setStatusCode(200);
        response.setMessage("Get categories newest");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
