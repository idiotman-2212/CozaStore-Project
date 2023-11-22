package com.cybersoft.cozaStore.api;

import com.cybersoft.cozaStore.entity.BlogEntity;
import com.cybersoft.cozaStore.entity.UserEntity;
import com.cybersoft.cozaStore.payload.response.BaseResponse;
import com.cybersoft.cozaStore.payload.response.BlogResponse;
import com.cybersoft.cozaStore.repository.BlogRepository;
import com.cybersoft.cozaStore.service.imp.BlogServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/blog")
public class ApiBlogController {

    @Autowired
    private BlogServiceImp blogServiceImp;

    @Autowired
    private BlogRepository blogRepository;

    @GetMapping("")
    public ResponseEntity<?> getAllBlog(){
        List<BlogResponse> responseList = blogServiceImp.getAllBlog();
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage("Get all blog");
        baseResponse.setData(responseList);
        baseResponse.setStatusCode(200);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> addBlog(@RequestParam String title, @RequestParam String content,
                                     @RequestParam String image, @RequestParam int idUser) {
        if (title == null || content == null) {
            BaseResponse errorResponse = new BaseResponse();
            errorResponse.setStatusCode(400);
            errorResponse.setMessage("Title and content cannot be null");

            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        boolean isSuccess = blogServiceImp.insertBlog(title,content, image, idUser);

        BaseResponse baseResponse = new BaseResponse();
        if (isSuccess) {
            baseResponse.setMessage("Insert Blog Successfully");
            baseResponse.setStatusCode(200);
            baseResponse.setData(isSuccess); // Nếu cần trả về dữ liệu blog sau khi thêm
        } else {
            baseResponse.setMessage("Failed to insert Blog");
            baseResponse.setStatusCode(500);
        }

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable int id){
        boolean isDeleted = blogServiceImp.deleteBlogById(id);
        if (isDeleted){
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setMessage("Blog deleted successfully");
            baseResponse.setStatusCode(200);
            baseResponse.setData(baseResponse);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Blog not found or unable to delete", HttpStatus.OK);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBlog(@PathVariable int id, @RequestParam String title, @RequestParam String content,
                                        @RequestParam String image, @RequestParam int idUser) {
        // Gọi phương thức cập nhật từ service
        boolean isUpdated = blogServiceImp.updateBlogById(id, title, content,
                image, idUser);

        // Kiểm tra xem cập nhật thành công hay không
        if (isUpdated) {
            return new ResponseEntity<>("Blog updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to update blog", HttpStatus.BAD_REQUEST);
        }
    }
}
