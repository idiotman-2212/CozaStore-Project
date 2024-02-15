package com.cybersoft.cozaStore.service;

import com.cybersoft.cozaStore.entity.*;
import com.cybersoft.cozaStore.payload.request.ProductRequest1;
import com.cybersoft.cozaStore.payload.response.ProductResponse;
import com.cybersoft.cozaStore.repository.*;
import com.cybersoft.cozaStore.service.imp.ProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService implements ProductServiceImp {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private SizeRepository sizeRepository;

    @Value("${root.folder}")
    private String rootFolder;
    @Override
    public boolean insertProduct(String name, MultipartFile file, double price, int quanity,
                                 int idColor, int idSize, int idCategory, String description) throws IOException {

        String pathImage= rootFolder + "/" + file.getOriginalFilename();

        Path path = Paths.get(rootFolder);
        Path pathImageCopy = Paths.get(pathImage);
        if(!Files.exists(path)){
            Files.createDirectory(path);
        }

        Files.copy(file.getInputStream(), pathImageCopy, StandardCopyOption.REPLACE_EXISTING);

        ProductEntity productEntity =  new ProductEntity();
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
        return true;
    }

    public void insertProductResponse(ProductRequest1 productRequest) {
        try {
            // Save ColorEntity
            ColorEntity colorEntity = colorRepository.findByName(productRequest.getColorName());
            if (colorEntity == null) {
                colorEntity = new ColorEntity();
                colorEntity.setName(productRequest.getColorName());
                try {
                    colorEntity = colorRepository.save(colorEntity);
                } catch (Exception e) {
                    // Log the exception
                    e.printStackTrace();
                }
            }

// Save SizeEntity
            SizeEntity sizeEntity = sizeRepository.findByName(productRequest.getSizeName());
            if (sizeEntity == null) {
                sizeEntity = new SizeEntity();
                sizeEntity.setName(productRequest.getSizeName());
                try {
                    sizeEntity = sizeRepository.save(sizeEntity);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

// Save CategoryEntity
            CategoryEntity categoryEntity = categoryRepository.findByName(productRequest.getCategoryName());
            if (categoryEntity == null) {
                categoryEntity = new CategoryEntity();
                categoryEntity.setName(productRequest.getCategoryName());
                try {
                    categoryEntity = categoryRepository.save(categoryEntity);
                }
               catch (Exception e){
                    e.printStackTrace();
               }
            }

            // Save ProductEntity
            String pathImage = rootFolder + "/" + productRequest.getFile().getOriginalFilename();
            Path path = Paths.get(rootFolder);
            Path pathImageCopy = Paths.get(pathImage);

            if (!Files.exists(path)) {
                Files.createDirectory(path);
            }

            Files.copy(productRequest.getFile().getInputStream(), pathImageCopy, StandardCopyOption.REPLACE_EXISTING);

            ProductEntity productEntity = new ProductEntity();
            productEntity.setName(productRequest.getName());
            productEntity.setImage(productRequest.getFile().getOriginalFilename());
            productEntity.setPrice(productRequest.getPrice());
            productEntity.setQuanity(productRequest.getQuanity());
            productEntity.setDescription(productRequest.getDesc());
            productEntity.setColor(colorEntity);
            productEntity.setSize(sizeEntity);
            productEntity.setCategory(categoryEntity);
            productEntity.setCreateDate(new Date());

            productRepository.save(productEntity);

        } catch (IOException | DataAccessException ex) {
            // Handle exceptions appropriately (log or return an error response)
            ex.printStackTrace(); // Print the exception stack trace (you can replace this with logging)
        }
    }

    @Override
    public List<ProductResponse> getAllProduct() {
        List<ProductEntity> list = productRepository.findAll();
        List<ProductResponse> responseList = new ArrayList<>();

        for (ProductEntity item : list) {
            ProductResponse productResponse = new ProductResponse();
            productResponse.setId(item.getId());
            productResponse.setName(item.getName());
            productResponse.setImage(item.getImage());
            productResponse.setDescription(item.getDescription());

            // Kiểm tra giá trị null trước khi truy cập và gọi doubleValue()
            if (item.getPrice() != null) {
                productResponse.setPrice(item.getPrice().doubleValue());
            } else {
                // Xử lý khi giá trị là null, ví dụ đặt giá trị mặc định
                productResponse.setPrice(0.0); // hoặc bỏ qua
            }
            productResponse.setQuantity(item.getQuanity());

            // Lấy thông tin màu, kích thước, và danh mục từ các đối tượng thực tế
            ColorEntity colorEntity = item.getColor();
            productResponse.setColorName(colorEntity != null ? colorEntity.getName() : "");

            SizeEntity sizeEntity = item.getSize();
            productResponse.setSizeName(sizeEntity != null ? sizeEntity.getName() : "");

            CategoryEntity categoryEntity = item.getCategory();
            productResponse.setCategoryName(categoryEntity != null ? categoryEntity.getName() : "");

            productResponse.setCreateDate(item.getCreateDate());

            responseList.add(productResponse);
        }

        return responseList;
    }

    @Override
    public Page<ProductResponse> getAllProductsPage(Integer pageNo) {
        int pageSize = 5; // Số sản phẩm trên mỗi trang
        List<ProductResponse> allProducts = getAllProduct();

        // Phân trang dữ liệu
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), allProducts.size());

        List<ProductResponse> sublist = allProducts.subList(start, end);

        // Trả về trang dữ liệu
        return new PageImpl<>(sublist, pageable, allProducts.size());
    }

    @Override
    public List<ProductResponse> getProductById(int id) {
        Optional<ProductEntity> productOptional = productRepository.findById(id);

        // Kiểm tra xem productOptional có giá trị tồn tại không
        if (productOptional.isPresent()) {
            ProductEntity productEntity = productOptional.get();
            ProductResponse productResponse = new ProductResponse();
            productResponse.setId(productEntity.getId());
            productResponse.setName(productEntity.getName());
            productResponse.setImage(productEntity.getImage());
            productResponse.setDescription(productEntity.getDescription());
            productResponse.setPrice(productEntity.getPrice());
            productResponse.setCreateDate(productEntity.getCreateDate());

            List<ProductResponse> productResponses = new ArrayList<>();
            productResponses.add(productResponse);

            return productResponses;
        } else {
            // Trả về danh sách rỗng nếu không tìm thấy sản phẩm
            return Collections.emptyList();
        }
    }

    @Override
    public boolean deleteProductById(int idProduct) {
        // Kiểm tra xem sản phẩm có tồn tại trong cơ sở dữ liệu không
        if (productRepository.existsById(idProduct)) {

            productRepository.deleteById(idProduct); // Xóa sản phẩm
            return true; // Trả về true nếu xóa thành công
        } else {
            return false; // Trả về false nếu không tìm thấy sản phẩm để xóa
        }
    }

    @Override
    public boolean updateProductById(int idProduct, String name, MultipartFile file, String description,
                                     double price, int quanity, int idColor, int idSize, int idCategory) throws IOException {
        Optional<ProductEntity> productOptional = productRepository.findById(idProduct);
        List<ProductResponse> responseList = new ArrayList<>();

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


            return true;
        } else {
            return false;
        }
    }


    @Override
    public List<ProductResponse> getProductByName(String productName) {
        List<ProductEntity> productList = productRepository.findByNameContaining(productName);
        List<ProductResponse> responseList = new ArrayList<>();

        for (ProductEntity item : productList) {
            ProductResponse productResponse = new ProductResponse();
            productResponse.setId(item.getId());
            productResponse.setName(item.getName());
            productResponse.setImage(item.getImage());
            productResponse.setDescription(item.getDescription());
            productResponse.setPrice(item.getPrice());
            productResponse.setCreateDate(item.getCreateDate());

            responseList.add(productResponse);
        }

        return responseList;
    }

    @Override
    public List<ProductResponse> searchProducts(String keyword) {
        List<ProductEntity> productList = productRepository.searchProducts(keyword);

        if (productList == null) {
            // Xử lý trường hợp productList là null
            return Collections.emptyList();
        }

        return productList.stream()
                .map(this::convertToProductResponse)
                .collect(Collectors.toList());
    }

    private ProductResponse convertToProductResponse(ProductEntity productEntity) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(productEntity.getId());
        productResponse.setName(productEntity.getName());
        productResponse.setImage(productEntity.getImage());
        productResponse.setDescription(productEntity.getDescription());
        productResponse.setPrice(productEntity.getPrice());
        productResponse.setCreateDate(productEntity.getCreateDate());

        return productResponse;
    }


}
