package com.cybersoft.cozaStore.service;

import com.cybersoft.cozaStore.entity.CartEntity;
import com.cybersoft.cozaStore.entity.ProductEntity;
import com.cybersoft.cozaStore.entity.UserEntity;
import com.cybersoft.cozaStore.payload.request.CartRequest;
import com.cybersoft.cozaStore.payload.response.CartResponse;
import com.cybersoft.cozaStore.payload.response.CartSummaryResponse;
import com.cybersoft.cozaStore.repository.CartRepository;
import com.cybersoft.cozaStore.repository.ProductRepository;
import com.cybersoft.cozaStore.repository.UserRepository;
import com.cybersoft.cozaStore.service.imp.CartServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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

    @Value("${BASE_URL}")
    private String baseURL;

    @Value("${STRIPE_SECRET_KEY}")
    private String apiKey;

    @Override
    public boolean insertProductIntoCart(CartRequest cartRequest) {

        Optional<ProductEntity> product = productRepository.findById(cartRequest.getIdProduct());
        Optional<UserEntity> user = userRepository.findById(cartRequest.getIdUser());

        if(product.isPresent() && user.isPresent()){

            CartEntity cart;
            // Kiểm tra xem sản phẩm đã tồn tại trong giỏ hàng của người dùng hay chưa
            Optional<CartEntity> existingCartItem = cartRepository.findByUserIdAndProductId(user.get().getId(), product.get().getId());

            if (existingCartItem.isPresent()) {
                // Nếu sản phẩm đã tồn tại, tăng số lượng
                cart = existingCartItem.get();
                cart.setQuanity(cart.getQuanity() + cartRequest.getQuanity());

            }else {
                // Nếu sản phẩm chưa tồn tại, tạo một mục mới
                cart = new CartEntity();
                cart.setProduct(product.get());
                cart.setUser(user.get());
                cart.setQuanity(cartRequest.getQuanity());
                cart.setCreateDate(new Date());
            }

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
    public List<CartResponse> getCartByIdUser(int idUser) {
        List<CartEntity> cartEntities = cartRepository.findAll();
        List<CartResponse> cartResponses = new ArrayList<>();

        for (CartEntity c : cartEntities) {
            if (c.getUser().getId() == idUser) {
                CartResponse cartTemp = new CartResponse();
                cartTemp.setIdCart(c.getId());
                cartTemp.setQuanity(c.getQuanity());
                cartTemp.setNameProduct(c.getProduct().getName());
                cartTemp.setNameUser(c.getUser().getUsername());

                // Tính tổng tiền cho từng sản phẩm trong giỏ hàng
                cartTemp.setTotalCostProduct(c.getProduct().getPrice() * c.getQuanity());
                cartResponses.add(cartTemp);
            }
        }

            return cartResponses;
        }

    @Override
    public List<CartResponse> getCartByIdUsers(int idUser) {
        List<CartEntity> cartEntities = cartRepository.findByUserId(idUser);
        List<CartResponse> cartResponses = new ArrayList<>();
        double totalCostCart = 0;
        CartResponse cartTemp = new CartResponse();
        CartSummaryResponse cartSummaryResponse = new CartSummaryResponse();
        for (CartEntity c : cartEntities) {

            cartTemp.setIdCart(c.getId());
            cartTemp.setQuanity(c.getQuanity());
            cartTemp.setNameProduct(c.getProduct().getName());
            cartTemp.setNameUser(c.getUser().getUsername());

            // Calculate the total price for each product
            double productTotalCost = c.getProduct().getPrice() * c.getQuanity();
            cartTemp.setTotalCostProduct(productTotalCost);

            // Add the product total cost to the overall total cost
            totalCostCart += productTotalCost;
            cartSummaryResponse.setTotalCostCart(totalCostCart);

            cartResponses.add(cartTemp);
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

    @Override
    public boolean deleteCartById(int id) {
        if(cartRepository.existsById(id)){
            cartRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean updateCart(int id, int id_product, int quanity, int id_user) {
    Optional<CartEntity> optionalCart = cartRepository.findById(id);
    List<CartResponse> cartResponses = new ArrayList<>();

    if(optionalCart.isPresent()){
        CartEntity cartEntity = optionalCart.get();
        cartEntity.setId(id);
        cartEntity.setQuanity(quanity);
        cartEntity.setCreateDate(new Date());

        UserEntity userEntity = new UserEntity();
        userEntity.setId(id_user);
        cartEntity.setUser(userEntity);

        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(id_product);
        cartEntity.setProduct(productEntity);

        cartRepository.save(cartEntity);

        return true;
    }else{
        return false;
    }


    }

}
