package com.cybersoft.cozaStore.api;

import com.cybersoft.cozaStore.payload.response.BaseResponse;
import com.cybersoft.cozaStore.payload.response.OrderResponse;
import com.cybersoft.cozaStore.service.imp.OrderServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class ApiOrderController {
    @Autowired
    private OrderServiceImp orderServiceImp;
    @GetMapping("")
    public ResponseEntity<?> getAllOrder(){
        List<OrderResponse> orderResponses = orderServiceImp.getAllOrder();
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage("Get all order");
        baseResponse.setData(orderResponses);
        baseResponse.setStatusCode(200);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

}
