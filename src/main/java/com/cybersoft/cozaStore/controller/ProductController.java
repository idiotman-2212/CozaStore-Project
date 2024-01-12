package com.cybersoft.cozaStore.controller;

import com.cybersoft.cozaStore.entity.ProductEntity;
import com.cybersoft.cozaStore.payload.response.ProductResponse;
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

    @PostMapping("/search")
    public String searchProduct(@RequestParam(name = "search-product", required = false) String query, Model model) {
        List<ProductResponse> list;

        if (query != null && !query.isEmpty()) {
            list = productServiceImp.searchProducts(query);
        } else {
            list = productServiceImp.getAllProduct();
        }
        model.addAttribute("list", list);
        model.addAttribute("query", query);
        return "index";
    }

}
