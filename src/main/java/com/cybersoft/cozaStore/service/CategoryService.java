package com.cybersoft.cozaStore.service;

import com.cybersoft.cozaStore.entity.CategoryEntity;
import com.cybersoft.cozaStore.payload.response.CategoryResponse;
import com.cybersoft.cozaStore.repository.CategoryRepository;
import com.cybersoft.cozaStore.service.imp.CategoryServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class CategoryService implements CategoryServiceImp {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public boolean insertCategory(String name) {
        try {
            // Kiểm tra xem name có giá trị hợp lệ không
            if (name == null || name.isEmpty()) {
                // Log hoặc ném một exception nếu name không hợp lệ
                throw new IllegalArgumentException("Category name cannot be null or empty");
            }

            // Kiểm tra xem đã tồn tại category với name này chưa
            if (categoryRepository.existsByName(name)) {
                // Log hoặc ném một exception nếu đã tồn tại category với name này
                throw new IllegalStateException("Category with this name already exists");
            }

            // Tạo mới CategoryEntity và lưu vào cơ sở dữ liệu
            CategoryEntity categoryEntity = new CategoryEntity();
            categoryEntity.setName(name);
            categoryRepository.save(categoryEntity);

            return true;
        } catch (Exception e) {
            // Log lỗi để có thể theo dõi và xử lý sau này
            return false;
        }
    }


    @Override
    public List<CategoryResponse> getAllCategory() {

        List<CategoryEntity> list = categoryRepository.findAll();
        List<CategoryResponse> responseList = new ArrayList<>();
        for (CategoryEntity item: list) {
            CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.setId(item.getId());
            categoryResponse.setName(item.getName());
            categoryResponse.setCreateDate(item.getCreateDate());

            responseList.add(categoryResponse);
        }

        return responseList;
    }

    @Override
    public List<CategoryResponse> getCategoryById(int idCategory) {
        Optional<CategoryEntity> categoryOptional = categoryRepository.findById(idCategory);

        if (categoryOptional.isPresent()) {
            CategoryEntity categoryEntity = categoryOptional.get();

            CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.setId(categoryEntity.getId());
            categoryResponse.setName(categoryEntity.getName());
            categoryResponse.setCreateDate(categoryEntity.getCreateDate());

            return Collections.singletonList(categoryResponse);
        } else {
            // Trả về danh sách rỗng hoặc xử lý tùy thuộc vào yêu cầu của bạn
            return Collections.emptyList();
        }
    }

    @Override
    public List<CategoryResponse> updateCategory(int categoryId, String newName) {
        Optional<CategoryEntity> categoryOptional = categoryRepository.findById(categoryId);
        List<CategoryResponse> responseList = new ArrayList<>();

        if (categoryOptional.isPresent()) {
            CategoryEntity categoryEntity = categoryOptional.get();
            categoryEntity.setName(newName);
            categoryRepository.save(categoryEntity);

            // Tạo một CategoryResponse từ CategoryEntity đã được cập nhật
            CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.setId(categoryEntity.getId());
            categoryResponse.setName(categoryEntity.getName());
            categoryResponse.setCreateDate(categoryEntity.getCreateDate());

            // Thêm CategoryResponse vào danh sách và trả về danh sách đó
            responseList.add(categoryResponse);
            return responseList;
        } else {
            // Nếu không tìm thấy danh mục, có thể ném một exception hoặc xử lý tùy thuộc vào yêu cầu của bạn.
            return null;
        }
    }



}
