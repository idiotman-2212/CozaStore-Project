package com.cybersoft.cozaStore.controller;

import com.cybersoft.cozaStore.entity.CategoryEntity;
import com.cybersoft.cozaStore.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.stream.StreamSupport;

@Controller
public class HomeController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/")
    public String home(Model model) {
        Iterable<CategoryEntity> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);

        // Chuyển đổi tên danh mục thành class và thêm vào model
        model.addAttribute("categoryClasses", getCategoryClasses(categories));

        return "index";
    }

    // Hàm chuyển đổi tên danh mục thành class
    private String[] getCategoryClasses(Iterable<CategoryEntity> categories) {
        return StreamSupport.stream(categories.spliterator(), false)
                .map(category -> category.getName().toLowerCase())
                .toArray(String[]::new);
    }
}