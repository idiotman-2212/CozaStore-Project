package com.cybersoft.cozaStore.api;

import com.cybersoft.cozaStore.payload.response.BaseResponse;
import com.cybersoft.cozaStore.payload.response.ProductOrderResponse;
import com.cybersoft.cozaStore.payload.response.ProductResponse;
import com.cybersoft.cozaStore.service.imp.Product_OrderServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product_order")
public class ApiProductOrderController {

    @Autowired
    private Product_OrderServiceImp orderServiceImp;
    @GetMapping("")
    public ResponseEntity<?> getAllProductOrder(){
        List<ProductOrderResponse> product_orderRespons = orderServiceImp.getAllProductOrder();
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage("Get all product order");
        baseResponse.setData(product_orderRespons);
        baseResponse.setStatusCode(200);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> addProductOrder(ProductResponse productResponse) throws IllegalAccessException {
        boolean isAdd = orderServiceImp.insertProductOrder(productResponse);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage("Insert product order");
        baseResponse.setData(isAdd? "Insert susseccfully" : "Insert failed");
        baseResponse.setStatusCode(200);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
