package com.cybersoft.cozaStore.service;

import com.cybersoft.cozaStore.entity.CategoryEntity;
import com.cybersoft.cozaStore.payload.response.CategoryResponse;
import com.cybersoft.cozaStore.repository.CategoryRepository;
import com.cybersoft.cozaStore.service.imp.CategoryServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class CategoryService implements CategoryServiceImp {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public boolean insertCategory(String name, Date createDate) throws IOException {

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(name);
        categoryEntity.setCreateDate(createDate);

        categoryRepository.save(categoryEntity);

        return false;
    }

    @Override
    public List<CategoryResponse> getAllCategory() {

        List<CategoryEntity> list = categoryRepository.findAll();
        List<CategoryResponse> responseList = new ArrayList<>();
        for (CategoryEntity item: list) {
            CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.setId(item.getId());
            categoryResponse.setName(item.getName());

            responseList.add(categoryResponse);
        }

        return responseList;
    }


}
