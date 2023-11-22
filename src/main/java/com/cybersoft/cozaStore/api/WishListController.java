package com.cybersoft.cozaStore.api;

import com.cybersoft.cozaStore.payload.response.BaseResponse;
import com.cybersoft.cozaStore.service.imp.WishListServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wishList")
public class WishListController {

    @Autowired
    private WishListServiceImp wishListServiceImp;

    @PostMapping("")
    public ResponseEntity<BaseResponse> addWishList(@RequestParam int idProduct, @RequestParam int idUser) {
        boolean isAdded = wishListServiceImp.addWishList(idProduct, idUser);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setData(isAdded ? "Insert successfully" : "Insert failed");
        baseResponse.setMessage("Insert wish list");

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWishListById(@PathVariable int id){
        BaseResponse baseResponse = new BaseResponse();
        boolean isDelete = wishListServiceImp.deleteWishList(id);
        baseResponse.setMessage("Delete WishList By id");
        baseResponse.setStatusCode(200);
        baseResponse.setData(isDelete? "Delete successfully" : "Delete failed");
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);

    }
}