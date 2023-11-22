package com.cybersoft.cozaStore.api;

import com.cybersoft.cozaStore.payload.response.BaseResponse;
import com.cybersoft.cozaStore.payload.response.CategoryResponse;
import com.cybersoft.cozaStore.repository.CategoryRepository;
import com.cybersoft.cozaStore.service.imp.CategoryServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/category")
public class ApiCategoryController {
    @Autowired
    private CategoryServiceImp categoryServiceImp;

    @Autowired
    private CategoryRepository categoryRepository;


    @GetMapping("")
    public ResponseEntity<?> getAllCategory(){
        List<CategoryResponse> responseList = categoryServiceImp.getAllCategory();
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage("Get all category");
        baseResponse.setData(responseList);
        baseResponse.setStatusCode(200);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/{idCategory}")
    public ResponseEntity<?> getCategoryById(@PathVariable int idCategory){
        List<CategoryResponse> responseList = categoryServiceImp.getCategoryById(idCategory);
        if (categoryRepository.existsById(idCategory)) {
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setMessage("Get category by id: " + idCategory);
            baseResponse.setData(responseList);
            baseResponse.setStatusCode(200);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }else{
            BaseResponse errorResponse = new BaseResponse();
            errorResponse.setStatusCode(404);
            errorResponse.setMessage("Category with id '" + idCategory + "' not found.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

        }

    }

    @PostMapping("")
    public ResponseEntity<?> addCategory(@RequestParam String name) throws IOException {
        // Kiểm tra xem danh mục đã tồn tại hay chưa
        if (categoryRepository.existsByName(name)) {
            BaseResponse errorResponse = new BaseResponse();
            errorResponse.setStatusCode(400);
            errorResponse.setMessage("Category with name '" + name + "' already exists.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        // Nếu chưa tồn tại, thêm mới danh mục
        boolean isSuccess = categoryServiceImp.insertCategory(name);

        BaseResponse successResponse = new BaseResponse();
        successResponse.setStatusCode(200);
        successResponse.setData(isSuccess);
        successResponse.setMessage("Insert Category Successfully");

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<?> updateCategory(@PathVariable int categoryId, @RequestParam String newName) {
        List<CategoryResponse> updatedCategory = categoryServiceImp.updateCategory(categoryId, newName);

        if (updatedCategory != null) {
            BaseResponse successResponse = new BaseResponse();
            successResponse.setStatusCode(200);
            successResponse.setData(updatedCategory);
            successResponse.setMessage("Update Category Successfully");

            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } else {
            BaseResponse errorResponse = new BaseResponse();
            errorResponse.setStatusCode(404);
            errorResponse.setMessage("Category with id '" + categoryId + "' not found.");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable int id){
        boolean isDelete = categoryServiceImp.deleteCategoryById(id);
        if(isDelete){
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setStatusCode(200);
            baseResponse.setMessage("Delete category successfully.");
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Category not found or unalbe to delete.", HttpStatus.OK);
        }
    }

    @GetMapping("/search")
    public BaseResponse searchCategories(@RequestParam String query){
        BaseResponse baseResponse = new BaseResponse();
        List<CategoryResponse> responseList = categoryServiceImp.searchCategories(query);

        if(responseList.isEmpty()){
            baseResponse.setMessage("No category for the given query.");
        }else{
            baseResponse.setData(responseList);
            baseResponse.setMessage("Category found successfully.");

        }
        return baseResponse;
    }
}
