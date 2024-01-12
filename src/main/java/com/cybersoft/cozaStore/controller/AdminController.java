package com.cybersoft.cozaStore.controller;

import com.cybersoft.cozaStore.entity.*;
import com.cybersoft.cozaStore.payload.request.ProductRequest;
import com.cybersoft.cozaStore.payload.request.SignUpRequest;
import com.cybersoft.cozaStore.repository.CategoryRepository;
import com.cybersoft.cozaStore.repository.ProductRepository;
import com.cybersoft.cozaStore.repository.RoleRepository;
import com.cybersoft.cozaStore.repository.UserRepository;
import com.cybersoft.cozaStore.service.imp.CategoryServiceImp;
import com.cybersoft.cozaStore.service.imp.ProductServiceImp;
import com.cybersoft.cozaStore.service.imp.RoleServiceImp;
import com.cybersoft.cozaStore.service.imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;


@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    CategoryServiceImp categoryService;

    @Autowired
    ProductServiceImp productService;

    @Autowired
    UserServiceImp userService;

    @Autowired
    RoleServiceImp roleService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Value("${root.folder}")
    private String rootFolder;

    @GetMapping("")
    public String adminHome() {
        return "adminPage";
    }//page admin home

    //Accounts
    @GetMapping("/users")
    public String getAcc(Model model) {
        model.addAttribute("users", userService.getAllUser());
        //model.addAttribute("roles", roleService.getAllRole());
        return "users";
    }

    @GetMapping("/users/add")
    public String getUserAdd(Model model) {
        model.addAttribute("userDTO", new SignUpRequest());
        model.addAttribute("roles", roleService.getAllRoles());
        return "usersAdd";
    }

    @PostMapping("/users/add")
    public String postUserAdd(@ModelAttribute("userDTO") SignUpRequest userDTO) {
        UserEntity user = new UserEntity();
        user.setEmail(userDTO.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user.setUsername(userDTO.getUserName());

        try {
            userRepository.save(user);
        } catch (Exception e) {
            System.out.println("Them that bai " + e.getLocalizedMessage());
        }

        return "redirect:/users/add?error";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.deleteUserById(id);
        return "redirect:/users";
    }//delete 1 user

    @GetMapping("/users/update/{id}")
    public String updateUser(@PathVariable int id, Model model) {
        Optional<UserEntity> opUser = userRepository.findById(id);
        if (opUser.isPresent()) {
            UserEntity user = opUser.get();
            SignUpRequest userDTO = new SignUpRequest();
            userDTO.setEmail(user.getEmail());
            userDTO.setPassword("");
            userDTO.setUserName(user.getUsername());

            RoleEntity roleIds = new RoleEntity();
            roleIds.setId(id);

            userDTO.getIdRole();

            model.addAttribute("userDTO", userDTO);
            model.addAttribute("roles", roleService.getAllRoles());
            return "usersAdd";
        } else {
            return "404";
        }

    }

    //Categories session
    @GetMapping("/categories")
    public String getCat(Model model) {
        model.addAttribute("categories", categoryService.getAllCategory());
        return "category";
    }//view all categories

    @GetMapping("/categories/add")
    public String getCatAdd(Model model) {
        model.addAttribute("category", new CategoryEntity());
        return "categoriesAdd";
    }//form add new category

    @PostMapping("/categories/add")
    public String postCatAdd(@ModelAttribute("category") CategoryEntity category){
        categoryService.insertCategory(String.valueOf(category));
        return "redirect:/categories";
    }//form add new category > do add

    @GetMapping("/categories/delete/{id}")
    public String deleteCat(@PathVariable int id) {
        categoryService.deleteCategoryById(id);
        return "redirect:/category";
    }//delete 1 category

    @GetMapping("/categories/update/{id}")
    public String updateCat(@PathVariable int id, Model model) {
        Optional<CategoryEntity> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            model.addAttribute("category", category.get());
            return "categoriesAdd";
        } else {
            return "404";
        }
    }//form edit category, fill old data into form

    //Products session
    @GetMapping("/product")
    public String getPro(Model model) {
        model.addAttribute("products", productService.getAllProduct());
        return "productsAdmin";
    }//view all products

    @GetMapping("/products/add")
    public String getProAdd(Model model) {
        model.addAttribute("productDTO", new ProductEntity());
        model.addAttribute("categories", categoryService.getAllCategory());
        return "productsAdd";
    }// form add new product

    @PostMapping("/products/add")
    public String postProAdd(@ModelAttribute("productDTO") ProductRequest productDTO,
                             @RequestParam String name,
                             @RequestParam MultipartFile file, @RequestParam double price,
                             @RequestParam int quanity, @RequestParam int idColor,
                             @RequestParam int idSize, @RequestParam int idCategory, @RequestParam String description) throws IOException {
        String pathImage = rootFolder + "/" + file.getOriginalFilename();

        Path path = Paths.get(rootFolder);
        Path pathImageCopy = Paths.get(pathImage);
        if (!Files.exists(path)) {
            Files.createDirectory(path);
        }

        Files.copy(file.getInputStream(), pathImageCopy, StandardCopyOption.REPLACE_EXISTING);

        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(name);
        productEntity.setImage(file.getOriginalFilename());
        productEntity.setPrice(price);
        productEntity.setQuanity(quanity);
        productEntity.setDescription(description);


        ColorEntity colorEntity = new ColorEntity();
        colorEntity.setId(idColor);
        productEntity.setColor(colorEntity);

        SizeEntity sizeEntity = new SizeEntity();
        sizeEntity.setId(idSize);
        productEntity.setSize(sizeEntity);

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(idCategory);
        productEntity.setCategory(categoryEntity);

        productEntity.setCreateDate(new Date());

        productRepository.save(productEntity);
        return "redirect:/productsAdd";
    }//form add new product > do add

    @GetMapping("/products/delete/{id}")
    public String deletePro(@PathVariable int id) {
        productService.deleteProductById(id);
        return "redirect:/product";
    }//delete 1 product

    @GetMapping("/products/update/{id}")
    public String updatePro(@PathVariable int idProduct, @RequestParam String name,
                            @RequestParam MultipartFile file, @RequestParam String description, @RequestParam double price,
                            @RequestParam int quanity, @RequestParam int idColor, @RequestParam int idSize, @RequestParam int idCategory, Model model) throws IOException {
        Optional<ProductEntity> productOptional = productRepository.findById(idProduct);
            if (productOptional.isPresent()) {
                ProductEntity productEntity = productOptional.get();

                // Xoá ảnh cũ
                String oldImage = productEntity.getImage();
                if (oldImage != null) {
                    Files.deleteIfExists(Paths.get(rootFolder, oldImage));
                }
                // Lưu ảnh mới
                String newImage = file.getOriginalFilename();
                Path newPathImageCopy = Paths.get(rootFolder, newImage);
                Files.copy(file.getInputStream(), newPathImageCopy, StandardCopyOption.REPLACE_EXISTING);

                productEntity.setName(name);
                productEntity.setImage(file.getOriginalFilename());
                productEntity.setPrice(price);
                productEntity.setQuanity(quanity);
                productEntity.setDescription(description);


                ColorEntity colorEntity = new ColorEntity();
                colorEntity.setId(idColor);
                productEntity.setColor(colorEntity);

                SizeEntity sizeEntity = new SizeEntity();
                sizeEntity.setId(idSize);
                productEntity.setSize(sizeEntity);

                CategoryEntity categoryEntity = new CategoryEntity();
                categoryEntity.setId(idCategory);
                productEntity.setCategory(categoryEntity);

                productEntity.setCreateDate(new Date());

                productRepository.save(productEntity);

                model.addAttribute("productDTO");
                model.addAttribute("categories", categoryService.getAllCategory());
                return "productsAdd";
            } else {
                return "404";
            }
        }
}