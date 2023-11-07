package com.cybersoft.cozaStore.controller;

import com.cybersoft.cozaStore.payload.response.BaseResponse;
import com.cybersoft.cozaStore.repository.SizeRepository;
import com.cybersoft.cozaStore.service.imp.SizeSeviceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/size")
public class SizeController {

    @Autowired
    private SizeSeviceImp sizeSeviceImp;

    @GetMapping("")
    public ResponseEntity<?> getAllSize(){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(sizeSeviceImp.getAllSize());
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }


}
