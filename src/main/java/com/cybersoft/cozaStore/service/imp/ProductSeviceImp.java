package com.cybersoft.cozaStore.service.imp;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductSeviceImp {
    boolean insertProduct( String name, MultipartFile file,  double price, int quantity,  int idColor,
                           int idSize,  int idCategory) throws IOException;
}
