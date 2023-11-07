package com.cybersoft.cozaStore.service;

import com.cybersoft.cozaStore.entity.CartEntity;
import com.cybersoft.cozaStore.entity.ProductEntity;
import com.cybersoft.cozaStore.entity.UserEntity;
import com.cybersoft.cozaStore.payload.request.CartRequest;
import com.cybersoft.cozaStore.payload.response.CartResponse;
import com.cybersoft.cozaStore.repository.CartRepository;
import com.cybersoft.cozaStore.repository.ProductRepository;
import com.cybersoft.cozaStore.repository.UserRepository;
import com.cybersoft.cozaStore.service.imp.CartServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService implements CartServiceImp {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean insertProductIntoCart(CartRequest cartRequest) {

        CartEntity cart = new CartEntity();

        Optional<ProductEntity> product = productRepository.findById(cartRequest.getIdProduct());
        Optional<UserEntity> user = userRepository.findById(cartRequest.getIdUser());

        if(product.isPresent() && user.isPresent()){

            cart.setProduct(product.get());
            cart.setUser(user.get());
            cart.setQuanity(cartRequest.getQuanity());

            try {
                cartRepository.save(cart);
                return true;

            } catch (Exception e){
                System.out.printf("Error: " + e);
                return false;
            }
        }

        return false;
    }

    @Override
    public List<CartResponse> getCart(int idUser) {
        List<CartEntity> cartEntities = cartRepository.findAll();
        List<CartResponse> cartResponses = new ArrayList<>();
        for (CartEntity c: cartEntities){
            if (c.getUser().getId() == idUser){
                CartResponse cartTemp = new CartResponse();
                cartTemp.setCart(c.getId());
                cartTemp.setQuanity(c.getQuanity());
                cartTemp.setNameProduct(c.getProduct().getName());

                cartResponses.add(cartTemp);

            }
        }

        return cartResponses;
    }

    @Override
    public boolean deleteProductById(int idProduct) {
        if (productRepository.existsById(idProduct)) {
            productRepository.deleteById(idProduct); // Xóa sản phẩm
            return true; // Trả về true nếu xóa thành công
        } else {
            return false; // Trả về false nếu không tìm thấy sản phẩm để xóa
        }}

    @Override
    public boolean deleteCartByIdUser(int idUser) {
        if(cartRepository.existsById(idUser)){
            cartRepository.deleteById(idUser);
            return true;
        }else{
            return false;
        }
    }
}
