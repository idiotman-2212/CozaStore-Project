package com.cybersoft.cozaStore.service.imp;

import com.cybersoft.cozaStore.payload.response.BlogResponse;

import java.util.List;

public interface BlogServiceImp {
    boolean insertBlog(String title, String content,
                       String image, int idUser);

    List<BlogResponse> getAllBlog();

    List<BlogResponse> getBlogById(int id);

    boolean deleteBlogById(int id);

    boolean updateBlogById(int id, String title, String content,
                           String image,  int idUser);
}
