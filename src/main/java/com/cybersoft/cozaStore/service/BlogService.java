package com.cybersoft.cozaStore.service;

import com.cybersoft.cozaStore.entity.BlogEntity;
import com.cybersoft.cozaStore.entity.CartEntity;
import com.cybersoft.cozaStore.entity.ProductEntity;
import com.cybersoft.cozaStore.entity.UserEntity;
import com.cybersoft.cozaStore.payload.response.BlogResponse;
import com.cybersoft.cozaStore.payload.response.ProductResponse;
import com.cybersoft.cozaStore.repository.BlogRepository;
import com.cybersoft.cozaStore.repository.UserRepository;
import com.cybersoft.cozaStore.service.imp.BlogServiceImp;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Service
public class BlogService implements BlogServiceImp {

   @Autowired
   private BlogRepository blogRepository;

   @Autowired
   private UserRepository userRepository;

    @Override
    public boolean insertBlog(String title, String content,
                              String image,  int idUser) {

        Optional<UserEntity> user = userRepository.findById(idUser);

        if( user.isPresent()){
           BlogEntity blogEntity;

            blogEntity = new BlogEntity();
            blogEntity.setUser(user.get());
            blogEntity.setCreateDate(new Date());
            blogEntity.setImage(image);
            blogEntity.setContent(content);
            blogEntity.setTitle(title);

            try {
                blogRepository.save(blogEntity);
                return true;

            } catch (Exception e){
                System.out.printf("Error: " + e);
                return false;
            }
        }

        return false;
    }

    @Override
    public List<BlogResponse> getAllBlog() {
        List<BlogEntity> list = blogRepository.findAll();
        List<BlogResponse> responseList = new ArrayList<>();
        for (BlogEntity item: list) {
            BlogResponse response = new BlogResponse();
            response.setId(item.getId());
            response.setTitle(item.getTitle());
            response.setImage(item.getImage());
            response.setContent(item.getContent());
            response.setIdUser(item.getUser().getId());
            response.setCreateDate(item.getCreateDate());


            responseList.add(response);
        }

        return responseList;
    }

    @Override
    public List<BlogResponse> getBlogById(int id) {
        Optional<BlogEntity> blog = blogRepository.findById(id);

        // Kiểm tra xem productOptional có giá trị tồn tại không
        if (blog.isPresent()) {
            BlogEntity blogEntity = blog.get();
            BlogResponse blogResponse = new BlogResponse();
            blogResponse.setId(blogEntity.getId());
            blogResponse.setTitle(blogEntity.getTitle());
            blogResponse.setImage(blogEntity.getImage());
            blogResponse.setContent(blogEntity.getContent());
            blogResponse.setIdUser(blogEntity.getUser().getId());
            blogResponse.setCreateDate(blogEntity.getCreateDate());

            List<BlogResponse> responseList = new ArrayList<>();
            responseList.add(blogResponse);

            return responseList;
        } else {
            // Trả về danh sách rỗng nếu không tìm thấy sản phẩm
            return Collections.emptyList();
        }
    }
    @Override
    public boolean deleteBlogById(int id) {
        // Kiểm tra xem sản phẩm có tồn tại trong cơ sở dữ liệu không
        if (blogRepository.existsById(id)) {

            blogRepository.deleteById(id); // Xóa sản phẩm
            return true; // Trả về true nếu xóa thành công
        } else {
            return false; // Trả về false nếu không tìm thấy sản phẩm để xóa
        }
    }

    @Override
    public boolean updateBlogById(int id, String title, String content,
                                  String image,  int idUser) {
        // Kiểm tra xem blog có tồn tại trong cơ sở dữ liệu không
        Optional<BlogEntity> blogOptional = blogRepository.findById(id);

        if (blogOptional.isPresent()) {
            // Blog tồn tại, thực hiện cập nhật
            BlogEntity blogEntity = blogOptional.get();
            blogEntity.setId(id);
            blogEntity.setTitle(title);
            blogEntity.setImage(image);
            blogEntity.setContent(content);
            UserEntity userEntity = new UserEntity();
            userEntity.setId(idUser);
            blogEntity.setUser(userEntity);
            blogEntity.setCreateDate(new Date());

            // Lưu thay đổi vào cơ sở dữ liệu
            try {
                blogRepository.save(blogEntity);
                return true;
            } catch (Exception e) {
                System.out.printf("Error updating blog: " + e);
                return false;
            }
        } else {
            // Blog không tồn tại
            return false;
        }
    }

}
