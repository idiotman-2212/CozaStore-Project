package com.cybersoft.cozaStore.api;

import com.cybersoft.cozaStore.payload.response.BaseResponse;
import com.cybersoft.cozaStore.payload.response.ProductResponse;
import com.cybersoft.cozaStore.repository.ProductRepository;
import com.cybersoft.cozaStore.service.imp.ProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ApiProductController {

    @Autowired
    private ProductServiceImp productServiceImp;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("")
    public ResponseEntity<?> getAllProduct(){

        List<ProductResponse> productResponseList = productServiceImp.getAllProduct();
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage("Get all product");
        baseResponse.setData(productResponseList);
        baseResponse.setStatusCode(200);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable int id){

        List<ProductResponse> productResponseList = productServiceImp.getProductById(id);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage("Get product by id");
        baseResponse.setData(productResponseList);
        baseResponse.setStatusCode(200);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/product/{productName}")
    public ResponseEntity<?> getProductById(@PathVariable String productName){

        List<ProductResponse> productResponseList = productServiceImp.getProductByName(productName);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage("Get product by product name");
        baseResponse.setData(productResponseList);
        baseResponse.setStatusCode(200);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> insertProduct(@RequestParam String name,
                                           @RequestParam MultipartFile file, @RequestParam double price,
                                           @RequestParam int quanity, @RequestParam int idColor,
                                           @RequestParam int idSize, @RequestParam int idCategory, @RequestParam String description) throws IOException {

        if(productRepository.existsByName(name)){
            BaseResponse errorResponse = new BaseResponse();
            errorResponse.setStatusCode(400);
            errorResponse.setMessage("Product with name '" + name+ " ' already exist");

            return new ResponseEntity<>( errorResponse, HttpStatus.BAD_REQUEST);
        }

        boolean isSuccess = productServiceImp.insertProduct(name, file, price, quanity, idColor, idSize, idCategory,description);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage("Insert Product Successfully");
        baseResponse.setStatusCode(200);
        baseResponse.setData(isSuccess);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{idProduct}")
    public ResponseEntity<?> deleteProductById(@PathVariable int idProduct){

        boolean isDeleted = productServiceImp.deleteProductById(idProduct);
        if (isDeleted){
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setMessage("Product deleted successfully");
            baseResponse.setStatusCode(200);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product not found or unable to delete", HttpStatus.OK);
        }
    }


    @PutMapping("/{idProduct}")
    public ResponseEntity<?> updateProductById(@PathVariable int idProduct, @RequestParam String name,
                                               @RequestParam MultipartFile file, @RequestParam String description, @RequestParam double price,
                                               @RequestParam int quanity, @RequestParam int idColor, @RequestParam int idSize, @RequestParam int idCategory
    ) throws IOException {
       boolean isUpdated = productServiceImp.updateProductById(idProduct, name, file, description, price, quanity,
                idColor, idSize, idCategory);

        if (isUpdated) {
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setMessage("Product updated successfully");
            baseResponse.setStatusCode(200);
            baseResponse.setData(isUpdated);

            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        } else {
            BaseResponse errorResponse = new BaseResponse();
            errorResponse.setMessage("Product with id '" + idProduct + " ' not found");
            errorResponse.setStatusCode(404);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }


}
