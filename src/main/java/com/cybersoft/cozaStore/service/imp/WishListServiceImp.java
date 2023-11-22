package com.cybersoft.cozaStore.service.imp;

import com.cybersoft.cozaStore.payload.request.WishlistRequest;
import com.cybersoft.cozaStore.payload.response.WishListResponse;

import java.util.List;

public interface WishListServiceImp {
    boolean addWishList(int idProduct, int idUser);
    boolean deleteWishList(int id);

    List<WishListResponse> getWishListByIdUser(int idUser);

}
