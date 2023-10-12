package com.cybersoft.cozaStore.service;

import com.cybersoft.cozaStore.entity.*;
import com.cybersoft.cozaStore.repository.ProductRepositoty;
import com.cybersoft.cozaStore.service.imp.ProductSeviceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@Service
public class ProductSevice implements ProductSeviceImp {
    @Autowired
    private ProductRepositoty productRepositoty;

    @Value("${root.folder}")
    private String rootFolder;
    @Override
    public boolean insertProduct(String name, MultipartFile file, double price, int quantity, int idColor, int idSize, int idCategory) throws IOException {

        String pathImage= rootFolder + "\\" + file.getOriginalFilename();

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
        productEntity.setQuanity(quantity);

        ColorEntity colorEntity = new ColorEntity();
        colorEntity.setId(idColor);
        productEntity.setColor(colorEntity);

        SizeEntity sizeEntity = new SizeEntity();
        sizeEntity.setId(idSize);
        productEntity.setSize(sizeEntity);

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(idCategory);
        productEntity.setCategory(categoryEntity);

        productRepositoty.save(productEntity);
        return false;
    }
}
