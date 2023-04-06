package tdtu.edu.vn.midterm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tdtu.edu.vn.midterm.model.Product;
import tdtu.edu.vn.midterm.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ProductService productService;

    // Dashboard
    @GetMapping
    public String index(Model model) {
        return "admin/dashboard";
    }

    // Product Management
    @GetMapping(value = "/products")
    public String products(Model model) {
        List<Product> productList = productService.getAll();
        model.addAttribute("products", productList);

        return "admin/products";
    }

    // Add Product Page
    @GetMapping(value = "/products/add")
    public String add_product(Model model) {
        List<Product> productList = productService.getAll();
        Product product = new Product();

        model.addAttribute("products", productList);
        model.addAttribute("product", product);

        return "admin/layouts/add-product";
    }

    // Save Product
    @PostMapping(value = "/products/add")
    public String save_product(@ModelAttribute("product") Product product,
                               @RequestParam("fileImage") MultipartFile multipartFile) throws IOException {
        String fileNameUpload = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        String extension = fileNameUpload.substring(fileNameUpload.lastIndexOf("."));
        String fileName = product.getName() + extension;
        product.setImage(fileName);
        productService.save(product);

        String uploadDir = "src/main/resources/static/img/products/" + product.getId();
        Path uploadPath = Path.of(uploadDir);
        if(!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try {
            InputStream inputStream = multipartFile.getInputStream();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Could not store file " + fileName + ": " + e.getMessage());
        }
        return "redirect:/admin/products";
    }

    // Delete Product
    @PostMapping(value = "/products/delete")
    public String delete_product(HttpServletRequest request) {
        String id = request.getParameter("id-delete");
        System.out.println(id);
        if (id != null) {
            productService.delete(Long.parseLong(id));
        } else {
            System.out.println("ID is null");
        }
        return "redirect:/admin/products";
    }

    // Edit Product Page
    @GetMapping(value = "/products/edit/{id}")
    public String edit_product(@PathVariable(name = "id") Long id, Model model) {
        Product product = productService.getById(id);
        model.addAttribute("product", product);
        return "admin/layouts/edit-product";
    }

    // Update Product
    @PostMapping(value = "/products/edit/{id}")
    public String update_product(@ModelAttribute("product") Product product,
                                 @RequestParam("fileImage") MultipartFile multipartFile) throws IOException {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String fileNameUpload = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            String extension = fileNameUpload.substring(fileNameUpload.lastIndexOf("."));
            String fileName = product.getName() + extension;

            String uploadDir = "src/main/resources/static/img/products/" + product.getId();
            Path uploadPath = Path.of(uploadDir);
            if(!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try {
                InputStream inputStream = multipartFile.getInputStream();
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new IOException("Could not store file " + fileName + ": " + e.getMessage());
            }

            product.setImage(fileName);
        } else {
            String oldImage = productService.getById(product.getId()).getImage();
            product.setImage(oldImage);
        }
        productService.update(product);
        return "redirect:/admin/products";
    }

    // Detail Product Page
    @GetMapping(value = "/products/detail/{id}")
    public String detail_product(@PathVariable(name = "id") Long id, Model model) {
        Product product = productService.getById(id);
        model.addAttribute("product", product);
        return "admin/layouts/detail-product";
    }
}
