package com.cybersoft.cozaStore.service.imp;

import com.cybersoft.cozaStore.payload.request.SignUpRequest;
import com.cybersoft.cozaStore.payload.response.CategoryResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface CategoryServiceImp {
    boolean insertCategory(String name, Date createDate) throws IOException;
    List<CategoryResponse> getAllCategory();
}
