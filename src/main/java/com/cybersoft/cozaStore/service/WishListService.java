package com.cybersoft.cozaStore.service;

import com.cybersoft.cozaStore.entity.ProductEntity;
import com.cybersoft.cozaStore.entity.UserEntity;
import com.cybersoft.cozaStore.entity.WishListEntity;
import com.cybersoft.cozaStore.payload.response.WishListResponse;
import com.cybersoft.cozaStore.repository.ProductRepository;
import com.cybersoft.cozaStore.repository.UserRepository;
import com.cybersoft.cozaStore.repository.WishListRepository;
import com.cybersoft.cozaStore.service.imp.WishListServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishListService implements WishListServiceImp {

    @Autowired
    private WishListRepository wishListRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public boolean addWishList(int idProduct, int idUser) {
        Optional<UserEntity> userEntities = userRepository.findById(idUser);
        Optional<ProductEntity> productEntities = productRepository.findById(idProduct);

        if(productEntities.isPresent() && userEntities.isPresent()){
            WishListEntity wishList;

            List<WishListEntity> existingWishList= wishListRepository.findByUserId(userEntities.get().getId());

            wishList = new WishListEntity();
            wishList.setUser(userEntities.get());
            wishList.setProduct(productEntities.get());

            wishListRepository.save(wishList);


        }


        return false;
    }

    @Override
    public boolean deleteWishList(int id) {
        if (wishListRepository.existsById(id)) {
            wishListRepository.deleteById(id); // Xóa sản phẩm
            return true; // Trả về true nếu xóa thành công
        } else {
            return false; // Trả về false nếu không tìm thấy sản phẩm để xóa
        }
    }

    @Override
    public List<WishListResponse> getWishListByIdUser(int idUser) {
        return null;
    }
}
