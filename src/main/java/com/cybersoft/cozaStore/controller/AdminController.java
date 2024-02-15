package com.cybersoft.cozaStore.controller;

import com.cybersoft.cozaStore.entity.*;
import com.cybersoft.cozaStore.payload.request.ProductRequest;
import com.cybersoft.cozaStore.payload.request.ProductRequest1;
import com.cybersoft.cozaStore.payload.request.SignUpRequest;
import com.cybersoft.cozaStore.payload.response.CategoryResponse;
import com.cybersoft.cozaStore.payload.response.ProductResponse;
import com.cybersoft.cozaStore.payload.response.UserResponse;
import com.cybersoft.cozaStore.repository.*;
import com.cybersoft.cozaStore.service.imp.CategoryServiceImp;
import com.cybersoft.cozaStore.service.imp.ProductServiceImp;
import com.cybersoft.cozaStore.service.imp.RoleServiceImp;
import com.cybersoft.cozaStore.service.imp.UserServiceImp;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
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
        RoleEntity roleEntity = new RoleEntity();
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setUsername(userDTO.getUserName());

        // Lấy vai trò từ userDTO và thiết lập cho roleEntity
        if (userDTO.getIdRole() != null) {
            int roleId = userDTO.getIdRole();
            roleEntity = roleService.getRoleById(roleId);
        }
        // Kiểm tra xem roleEntity có tồn tại hay không
        if (roleEntity == null) {
            System.out.println("Vai trò không tồn tại");
            // Xử lý tùy thuộc vào yêu cầu của bạn (ví dụ: có thể quay trở lại trang trước đó hoặc hiển thị thông báo lỗi)
            return "redirect:/admin/users";
        }

        // Thiết lập vai trò cho người dùng
        user.setRole(roleEntity);
        try {
            userRepository.save(user);
        } catch (Exception e) {
            System.out.println("Thêm thất bại " + e.getMessage());
            // Có thể thêm log để theo dõi lỗi
            return "redirect:/admin/users?error=true";
        }
        return "redirect:/admin/users";
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
                userDTO.setIdRole(user.getRole().getId());
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

            // Kiểm tra mật khẩu mới có được cung cấp hay không
            if (signUpRequest.getPassword() != null && !signUpRequest.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
            }

            user.setUsername(signUpRequest.getUserName());

            // Cập nhật vai trò (roles)
            if (signUpRequest.getIdRole() != null) {
                int roleId = signUpRequest.getIdRole();
                RoleEntity userRole = roleService.getRoleById(roleId);
                if (userRole != null) {
                    user.setRole(userRole);
                } else {
                    System.out.println("Vai trò không tồn tại");
                    // Xử lý tùy thuộc vào yêu cầu của bạn (ví dụ: có thể quay trở lại trang trước đó hoặc hiển thị thông báo lỗi)
                    return "redirect:/admin/users";
                }
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

    @GetMapping("users/search")
    public String searchUser(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
        List<UserEntity> list;

        if (keyword != null && !keyword.isEmpty()) {
            list = userRepository.searchUsers(keyword);

            if (list.isEmpty()) {
                model.addAttribute("noResults", true);
            }
        } else {
            list = userRepository.findAll();
        }
        model.addAttribute("list", list);
        model.addAttribute("keyword", keyword);
        return "users";
    }//search user

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
    public String getProduct(Model model, @RequestParam(name = "pageNo", defaultValue ="1") Integer pageNo ) {
        Page<ProductResponse> products = productServiceImp.getAllProductsPage(pageNo);
        if(pageNo != null){
            model.addAttribute("totalPage", products.getTotalPages());
            model.addAttribute("currentPage", pageNo);
            model.addAttribute("products", products);

        }else {
            model.addAttribute("error", "Không thể lấy danh sách sản phẩm.");
        }
        return "productsAdmin";
    }//view all products

    @GetMapping("/product/search")
    public String searchProduct(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
        List<ProductResponse> productList = productServiceImp.searchProducts(keyword);
        model.addAttribute("products", productList);
        model.addAttribute("keyword", keyword);
        return "productsAdmin";
    }//search product

    @GetMapping("/product/add")
    public String getProductAdd(Model model) {
        model.addAttribute("productRequest", new ProductEntity());
        model.addAttribute("categories", categoryService.getAllCategory());
        List<SizeEntity> sizes = sizeRepository.findAll();
        List<ColorEntity> colors = colorRepository.findAll();

        model.addAttribute("sizes", sizes);
        model.addAttribute("colors", colors);
        return "productsAdd";
    }

    @PostMapping("/product/add")
    public String postProductAdd(@ModelAttribute("productRequest") @Valid ProductRequest1 productRequest,
                                 BindingResult result,
                                 Model model) throws IOException {

        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategory());
            List<SizeEntity> sizes = sizeRepository.findAll();
            List<ColorEntity> colors = colorRepository.findAll();
            model.addAttribute("sizes", sizes);
            model.addAttribute("colors", colors);
            return "productsAdd";
        }

        productServiceImp.insertProductResponse(productRequest);

        return "redirect:/admin/product";
    }//form add new product > do add

    @GetMapping("/product/delete/{id}")
    public String deleteProductById(@PathVariable int id) {
        productServiceImp.deleteProductById(id);
        return "redirect:/admin/product";
    }//delete 1 product

    @GetMapping("/product/update/{id}")
    public String updateProductById(@PathVariable("id") String idStr, Model model) {
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            return "redirect:/error"; // Hoặc trả về lỗi phù hợp
        }
        // Tìm kiếm sản phẩm theo ID
        Optional<ProductEntity> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            ProductEntity productEntity = productOptional.get();
            model.addAttribute("productRequest", productEntity);
            model.addAttribute("categories", categoryService.getAllCategory());
            List<SizeEntity> sizes = sizeRepository.findAll();
            List<ColorEntity> colors = colorRepository.findAll();
            model.addAttribute("sizes", sizes);
            model.addAttribute("colors", colors);

            return "productsEdit"; // Tên view để hiển thị form cập nhật
        } else {
            // Xử lý trường hợp không tìm thấy sản phẩm
            // Ví dụ: redirect về trang 404
            return "redirect:/404";
        }
    }

    @PostMapping("/product/update/{id}")
    public String processUpdateProduct(@ModelAttribute("productRequest") ProductRequest1 productDTO,
                                       @PathVariable String idStr,
                                       @RequestParam MultipartFile file,
                                       @RequestParam String colorName,
                                       @RequestParam String sizeName,
                                       @RequestParam String categoryName,
                                       @RequestParam String desc,
                                       Model model) throws IOException {
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            return "redirect:/error";
        }

        Optional<ProductEntity> productOptional = productRepository.findById(id);

        if (productOptional.isPresent()) {
            ProductEntity productEntity = productOptional.get();

            String rootFolder = "src/main/resources/static/images";

            // Cập nhật thông tin sản phẩm
            productEntity.setName(productDTO.getName());
            productEntity.setPrice(productDTO.getPrice());
            productEntity.setQuanity(productDTO.getQuanity());
            productEntity.setDescription(productDTO.getDesc());

            // Lưu ảnh nếu người dùng chọn ảnh mới
            if (!file.isEmpty()) {
                String oldImage = productEntity.getImage();
                if (oldImage != null) {
                    Files.deleteIfExists(Paths.get(rootFolder, oldImage));
                }

                String newImage = file.getOriginalFilename();
                Path newPathImageCopy = Paths.get(rootFolder, newImage);
                try (InputStream inputStream = file.getInputStream()) {
                    Files.copy(inputStream, newPathImageCopy, StandardCopyOption.REPLACE_EXISTING);
                }
                productEntity.setImage(newImage);
            }

            // Lấy đối tượng từ cơ sở dữ liệu thay vì tạo mới
            ColorEntity colorEntity = colorRepository.findByName(colorName);
            SizeEntity sizeEntity = sizeRepository.findByName(sizeName);
            CategoryEntity categoryEntity = categoryRepository.findByName(categoryName);

            productEntity.setColor(colorEntity);
            productEntity.setSize(sizeEntity);
            productEntity.setCategory(categoryEntity);

            productRepository.save(productEntity);

            return "redirect:/admin/product";
        } else {
            return "404";
        }
    }

}