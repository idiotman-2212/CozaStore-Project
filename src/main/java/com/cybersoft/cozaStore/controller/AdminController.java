package com.cybersoft.cozaStore.controller;

import com.cybersoft.cozaStore.entity.*;
import com.cybersoft.cozaStore.payload.request.ProductRequest;
import com.cybersoft.cozaStore.payload.request.SignUpRequest;
import com.cybersoft.cozaStore.payload.response.CategoryResponse;
import com.cybersoft.cozaStore.payload.response.ProductResponse;
import com.cybersoft.cozaStore.repository.*;
import com.cybersoft.cozaStore.service.imp.CategoryServiceImp;
import com.cybersoft.cozaStore.service.imp.ProductServiceImp;
import com.cybersoft.cozaStore.service.imp.RoleServiceImp;
import com.cybersoft.cozaStore.service.imp.UserServiceImp;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    private PasswordEncoder passwordEncoder;

    @Autowired
    CategoryServiceImp categoryService;

    @Autowired
    ProductServiceImp productServiceImp;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserServiceImp userService;

    @Autowired
    RoleServiceImp roleService;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

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
        model.addAttribute("roles", roleService.getAllRoles());
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
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
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
        return "redirect:/admin/users";
    }//delete 1 user

    @GetMapping("/users/update/{id}")
    public String getUserUpdate(@PathVariable int id, Model model) {
        Optional<UserEntity> opUser = userRepository.findById(id);

        if (opUser.isPresent()) {
            UserEntity user = opUser.get();
            SignUpRequest userDTO = new SignUpRequest();
            userDTO.setId(user.getId());
            userDTO.setEmail(user.getEmail());
            // Không hiển thị mật khẩu trong form để tránh lộ thông tin mật khẩu
            userDTO.setPassword(null);
            userDTO.setUserName(user.getUsername());

            // Lấy vai trò của người dùng
            if (user.getRole() != null) {
                userDTO.setIdRole(Collections.singletonList(user.getRole().getId()));
            }

            model.addAttribute("userDTO", userDTO);
            model.addAttribute("roles", roleService.getAllRoles());
            return "usersEdit";
        } else {
            return "404"; // Hoặc trang lỗi khác nếu người dùng không tồn tại.
        }
    }

    @PostMapping("/users/update/{id}")
    public String updateUser(@PathVariable int id, @ModelAttribute("userDTO") SignUpRequest signUpRequest) {
        Optional<UserEntity> opUser = userRepository.findById(id);

        if (opUser.isPresent()) {
            UserEntity user = opUser.get();
            // Cập nhật thông tin người dùng từ SignUpRequest
            user.setEmail(signUpRequest.getEmail());
            if (signUpRequest.getPassword() != null && !signUpRequest.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
            }
            user.setUsername(signUpRequest.getUserName());

            // Cập nhật vai trò (roles)
            if (signUpRequest.getIdRole() != null && !signUpRequest.getIdRole().isEmpty()) {
                RoleEntity userRole = roleService.getRoleById(signUpRequest.getIdRole().get(0));
                user.setRole(userRole);
            }

            // Lưu người dùng đã cập nhật vào cơ sở dữ liệu
            userRepository.save(user);
            // Chuyển hướng về trang danh sách người dùng sau khi cập nhật thành công
            return "redirect:/admin/users";
        } else {
            // Trang lỗi nếu không tìm thấy người dùng
            return "404";
        }
    }
//update user

    //Categories session
    @GetMapping("/categories")
    public String getCat(Model model) {
        model.addAttribute("categories", categoryService.getAllCategory());
        return "category";
    }//view all categories

    @GetMapping("/categories/search")
    public String searchCategory(@RequestParam("keyword") String keyword, Model model) {
        List<CategoryResponse> list = categoryService.getAllCategory();
        if (keyword != null && !keyword.isEmpty()) {
            list = categoryService.searchCategories(keyword);
        }
        model.addAttribute("list", list);
        model.addAttribute("keyword", keyword);
        return "category";
    }//search product

    @GetMapping("/categories/add")
    public String getAddCategory(Model model) {
        model.addAttribute("category", new CategoryEntity());
        return "categoriesAdd";
    }

    @PostMapping("/categories/add")
    public String postAddCategory(@ModelAttribute("category") CategoryEntity category, Model model) {
        boolean isCategoryAdded = categoryService.insertCategory(category.getName());

        if (isCategoryAdded) {
            return "redirect:/admin/categories";
        } else {
            model.addAttribute("error", "Failed to add category. Please try again.");
            return "categoriesAdd";
        }
    }//form add new category > do add

    @GetMapping("/categories/delete/{id}")
    public String deleteCat(@PathVariable int id) {
        categoryService.deleteCategoryById(id);
        return "redirect:/admin/categories";
    }//delete 1 category

    @GetMapping("/categories/update/{id}")
    public String updateCat(@PathVariable int id, Model model) {
        Optional<CategoryEntity> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            model.addAttribute("category", category.get());
            return "categoriesEdit";
        } else {
            return "404";
        }
    }//form edit category, fill old data into form

    @PostMapping("/categories/update/{id}")
    public String updateCategory(@ModelAttribute CategoryEntity updatedCategory) {
        Optional<CategoryEntity> existingCategory = categoryRepository.findById(updatedCategory.getId());

        if (existingCategory.isPresent()) {
            CategoryEntity categoryEntity = existingCategory.get();
            categoryEntity.setName(updatedCategory.getName());
            categoryRepository.save(categoryEntity);
            return "redirect:/admin/categories";
        } else {
            return "404";
        }
    }

    //Products session
    @GetMapping("/product")
    public String getPro(Model model) {
        model.addAttribute("products", productServiceImp.getAllProduct());
        return "productsAdmin";
    }//view all products

    @GetMapping("/product/search")
    public String searchProduct(@Param("keyword") String keyword, Model model) {
        List<ProductResponse> list;

        if (keyword != null && !keyword.isEmpty()) {
            list = productServiceImp.searchProducts(keyword);
        } else {
            list = productServiceImp.getAllProduct();
        }
        model.addAttribute("list", list);
        model.addAttribute("keyword", keyword);
        return "productsAdmin";
    }//search product

    @GetMapping("/product/add")
    public String getProductAdd(Model model) {
        model.addAttribute("productDTO", new ProductEntity());
        model.addAttribute("categories", categoryService.getAllCategory());
        List<SizeEntity> sizes = sizeRepository.findAll();
        List<ColorEntity> colors = colorRepository.findAll();

        model.addAttribute("sizes", sizes);
        model.addAttribute("colors", colors);
        return "productsAdd";
    }// form add new product

    @PostMapping("/product/add")
    public String postProductAdd(@ModelAttribute("productDTO") ProductRequest productDTO,
                                 @RequestParam String name,
                                 @RequestParam("file") MultipartFile file,
                                 @RequestParam double price,
                                 @RequestParam int quanity, // Sửa chính tả thành quantity
                                 @RequestParam String colorName,
                                 @RequestParam String sizeName,
                                 @RequestParam String categoryName,
                                 @RequestParam String description) throws IOException {

        // Kiểm tra giá trị null
        if (name == null || file == null || colorName == null || sizeName == null || categoryName == null || description == null) {
            // Xử lý lỗi hoặc thông báo lỗi
            return "redirect:/admin/product";
        }
        // Kiểm tra giá trị hợp lệ
        if (price <= 0 || quanity <= 0) {
            // Xử lý lỗi hoặc thông báo lỗi
            return "redirect:/admin/product";
        }

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
        colorEntity.setName(colorName);
        productEntity.setColor(colorEntity);

        SizeEntity sizeEntity = new SizeEntity();
        sizeEntity.setName(sizeName);
        productEntity.setSize(sizeEntity);

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(categoryName);
        productEntity.setCategory(categoryEntity);

        productEntity.setCreateDate(new Date());

        productRepository.save(productEntity);
        return "redirect:/admin/product";
    }//form add new product > do add

    @GetMapping("/product/delete/{id}")
    public String deleteProductById(@PathVariable int id) {
        productServiceImp.deleteProductById(id);
        return "redirect:/admin/product";
    }//delete 1 product

    @GetMapping("/product/update/{id}")
    public String updateProductById(@PathVariable int id, Model model) {
        Optional<ProductEntity> productOptional = productRepository.findById(id);

        if (productOptional.isPresent()) {
            ProductEntity productEntity = productOptional.get();
            model.addAttribute("productDTO", productEntity);
            model.addAttribute("categories", categoryService.getAllCategory());
            List<SizeEntity> sizes = sizeRepository.findAll();
            List<ColorEntity> colors = colorRepository.findAll();
            model.addAttribute("sizes", sizes);
            model.addAttribute("colors", colors);

            return "productsEdit"; // Tên view để hiển thị form cập nhật
        } else {
            return "404"; // Trả về trang lỗi nếu không tìm thấy sản phẩm
        }
    }

    @PostMapping("/product/update/{id}")
    public String processUpdateProduct(@ModelAttribute("productDTO") ProductEntity productDTO,
                                       @PathVariable int id,
                                       @RequestParam MultipartFile file,
                                       @RequestParam String colorName,
                                       @RequestParam String sizeName,
                                       @RequestParam String categoryName,
                                       @RequestParam String desc,
                                       Model model) throws IOException {
        Optional<ProductEntity> productOptional = productRepository.findById(id);

        if (productOptional.isPresent()) {
            ProductEntity productEntity = productOptional.get();

            // Kiểm tra nếu người dùng chọn ảnh mới
            if (!file.isEmpty()) {
                String oldImage = productEntity.getImage();
                if (oldImage != null) {
                    Files.deleteIfExists(Paths.get(rootFolder, oldImage));
                }

                String newImage = file.getOriginalFilename();
                Path newPathImageCopy = Paths.get(rootFolder, newImage);
                Files.copy(file.getInputStream(), newPathImageCopy, StandardCopyOption.REPLACE_EXISTING);
                productEntity.setImage(newImage);
            }

            // Cập nhật thông tin sản phẩm
            productEntity.setName(productDTO.getName());
            productEntity.setPrice(productDTO.getPrice());
            productEntity.setQuanity(productDTO.getQuanity());
            productEntity.setDescription(productDTO.getDescription());

            // Lấy đối tượng từ cơ sở dữ liệu thay vì tạo mới
            ColorEntity colorEntity = colorRepository.findByName(colorName);
            SizeEntity sizeEntity = sizeRepository.findByName(sizeName);
            CategoryEntity categoryEntity = categoryRepository.findByName(categoryName);

            productEntity.setColor(colorEntity);
            productEntity.setSize(sizeEntity);
            productEntity.setCategory(categoryEntity);

            // Lưu cập nhật vào cơ sở dữ liệu
            productRepository.save(productEntity);

            return "redirect:/admin/product";
        } else {
            return "404";
        }
    }
}