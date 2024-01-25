package com.cybersoft.cozaStore.controller;

import com.cybersoft.cozaStore.entity.ProductEntity;
import com.cybersoft.cozaStore.payload.response.ProductResponse;
import com.cybersoft.cozaStore.service.ProductService;
import com.cybersoft.cozaStore.service.imp.ProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductServiceImp productServiceImp;

    @Autowired
    private ProductService productService;

    @GetMapping("")
    public String product(Model model) {
        List<ProductResponse> list = productServiceImp.getAllProduct();
        model.addAttribute("list", list);
        return "product";
    }

    @GetMapping("/detail")
    public String productDetail() {
        return "product-detail";
    }

    @GetMapping("/search")
    public String searchProduct(@RequestParam("keyword") String keyword, Model model) {
        List<ProductResponse> list;

        if (keyword != null && !keyword.isEmpty()) {
            list = productService.searchProducts(keyword);
        } else {
            list = productService.getAllProduct();
        }
        model.addAttribute("lists", list);
        model.addAttribute("keyword", keyword);
        return "index";
    }

}
