package com.cybersoft.cozaStore.api;

import com.cybersoft.cozaStore.payload.response.BaseResponse;
import com.cybersoft.cozaStore.service.ColorService;
import com.cybersoft.cozaStore.service.imp.SizeSeviceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/color")
public class ApiColorController {

    @Autowired
    private ColorService colorService;

    @GetMapping("")
    public ResponseEntity<?> getAllSize(){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(colorService.getAllColor());
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }


}
