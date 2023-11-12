package com.cybersoft.cozaStore.service.imp;

import com.cybersoft.cozaStore.payload.response.ProductResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductServiceImp {
    boolean insertProduct( String name, MultipartFile file,  double price, int quanity,  int idColor,
                           int idSize,  int idCategory, String description) throws IOException;
    List<ProductResponse> getAllProduct();

    List<ProductResponse> getProductById(int id);

    boolean deleteProductById(int idProduct);

    boolean updateProductById(int idProduct, String name,
                              MultipartFile file, String description, double price,
                              int quanity,int idColor, int idSize, int idCategory) throws IOException;

    List<ProductResponse> getProductByName(String productName);

}
