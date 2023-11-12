package com.cybersoft.cozaStore.service;

import com.cybersoft.cozaStore.entity.*;
import com.cybersoft.cozaStore.payload.response.ProductResponse;
import com.cybersoft.cozaStore.repository.CartRepository;
import com.cybersoft.cozaStore.repository.CategoryRepository;
import com.cybersoft.cozaStore.repository.ProductRepository;
import com.cybersoft.cozaStore.service.imp.ProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements ProductServiceImp {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Value("${root.folder}")
    private String rootFolder;
    @Override
    public boolean insertProduct(String name, MultipartFile file, double price, int quanity, int idColor, int idSize, int idCategory, String description) throws IOException {

        String pathImage= rootFolder + "/" + file.getOriginalFilename();

        Path path = Paths.get(rootFolder);
        Path pathImageCopy = Paths.get(pathImage);
        if(!Files.exists(path)){
            Files.createDirectory(path);
        }

        Files.copy(file.getInputStream(), pathImageCopy, StandardCopyOption.REPLACE_EXISTING);

        ProductEntity productEntity =  new ProductEntity();
        productEntity.setName(name);
        productEntity.setImage(file.getOriginalFilename());
        productEntity.setPrice(price);
        productEntity.setQuanity(quanity);
        productEntity.setDescription(description);

        ColorEntity colorEntity = new ColorEntity();
        colorEntity.setId(idColor);
        productEntity.setColor(colorEntity);

        SizeEntity sizeEntity = new SizeEntity();
        sizeEntity.setId(idSize);
        productEntity.setSize(sizeEntity);

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(idCategory);
        productEntity.setCategory(categoryEntity);

        productRepository.save(productEntity);
        return true;
    }

    @Override
    public List<ProductResponse> getAllProduct() {

        List<ProductEntity> list = productRepository.findAll();
        List<ProductResponse> responseList = new ArrayList<>();
        for (ProductEntity item: list) {
            ProductResponse productResponse = new ProductResponse();
            productResponse.setId(item.getId());
            productResponse.setName(item.getName());
            productResponse.setImage(item.getImage());
            productResponse.setDesc(item.getDescription());
            productResponse.setPrice(item.getPrice());

            responseList.add(productResponse);
        }

        return responseList;
    }

    @Override
    public List<ProductResponse> getProductById(int id) {
        Optional<ProductEntity> productOptional = productRepository.findById(id);

        // Kiểm tra xem productOptional có giá trị tồn tại không
        if (productOptional.isPresent()) {
            ProductEntity productEntity = productOptional.get();
            ProductResponse productResponse = new ProductResponse();
            productResponse.setId(productEntity.getId());
            productResponse.setName(productEntity.getName());
            productResponse.setImage(productEntity.getImage());
            productResponse.setDesc(productEntity.getDescription());
            productResponse.setPrice(productEntity.getPrice());

            List<ProductResponse> productResponses = new ArrayList<>();
            productResponses.add(productResponse);

            return productResponses;
        } else {
            // Trả về danh sách rỗng nếu không tìm thấy sản phẩm
            return Collections.emptyList();
        }
    }

    @Override
    public boolean deleteProductById(int idProduct) {
        // Kiểm tra xem sản phẩm có tồn tại trong cơ sở dữ liệu không
        if (productRepository.existsById(idProduct)) {

            productRepository.deleteById(idProduct); // Xóa sản phẩm
            return true; // Trả về true nếu xóa thành công
        } else {
            return false; // Trả về false nếu không tìm thấy sản phẩm để xóa
        }
    }

    @Override
    public boolean updateProductById(int idProduct, String name, MultipartFile file, String description, double price, int quanity, int idColor, int idSize, int idCategory) throws IOException {
        Optional<ProductEntity> productOptional = productRepository.findById(idProduct);
        List<ProductResponse> responseList = new ArrayList<>();

        if (productOptional.isPresent()) {
            ProductEntity productEntity = productOptional.get();

            // Xoá ảnh cũ
            String oldImage = productEntity.getImage();
            if (oldImage != null) {
                Files.deleteIfExists(Paths.get(rootFolder, oldImage));
            }
            // Lưu ảnh mới
            String newImage = file.getOriginalFilename();
            Path newPathImageCopy = Paths.get(rootFolder, newImage);
            Files.copy(file.getInputStream(), newPathImageCopy, StandardCopyOption.REPLACE_EXISTING);

            productEntity.setName(name);
            productEntity.setImage(file.getOriginalFilename());
            productEntity.setPrice(price);
            productEntity.setQuanity(quanity);
            productEntity.setDescription(description);

            ColorEntity colorEntity = new ColorEntity();
            colorEntity.setId(idColor);
            productEntity.setColor(colorEntity);

            SizeEntity sizeEntity = new SizeEntity();
            sizeEntity.setId(idSize);
            productEntity.setSize(sizeEntity);

            CategoryEntity categoryEntity = new CategoryEntity();
            categoryEntity.setId(idCategory);
            productEntity.setCategory(categoryEntity);

            productRepository.save(productEntity);


            return true;
        } else {
            return false;
        }
    }


    @Override
    public List<ProductResponse> getProductByName(String productName) {
        List<ProductEntity> productList = productRepository.findByNameContaining(productName);
        List<ProductResponse> responseList = new ArrayList<>();

        for (ProductEntity item : productList) {
            ProductResponse productResponse = new ProductResponse();
            productResponse.setId(item.getId());
            productResponse.setName(item.getName());
            productResponse.setImage(item.getImage());
            productResponse.setDesc(item.getDescription());
            productResponse.setPrice(item.getPrice());

            responseList.add(productResponse);
        }

        return responseList;
    }

}
