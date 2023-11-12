package com.cybersoft.cozaStore.service.imp;

import com.cybersoft.cozaStore.payload.response.CategoryResponse;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface CategoryServiceImp {
    boolean insertCategory(String name);

    List<CategoryResponse> getAllCategory();

    List<CategoryResponse> getCategoryById(int idCategory);

    List<CategoryResponse> updateCategory(int categoryId, String newName);
}
