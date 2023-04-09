package tdtu.edu.vn.midterm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tdtu.edu.vn.midterm.model.Order;
import tdtu.edu.vn.midterm.model.Product;
import tdtu.edu.vn.midterm.model.User;
import tdtu.edu.vn.midterm.service.OrderService;
import tdtu.edu.vn.midterm.service.ProductService;
import tdtu.edu.vn.midterm.service.UserService;

import javax.servlet.RequestDispatcher;
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
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    // Dashboard
    @GetMapping
    public String index(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error/404";
            }
            else if(statusCode == HttpStatus.FORBIDDEN.value()) {
                return "error/403";
            }
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails userDetails) {
            User user = userService.findUserByEmail(userDetails.getUsername());
            model.addAttribute("username", user.getUsername());
        }
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

        return "admin/products/add-product";
    }

    // Save Product
    @PostMapping(value = "/products/add")
    public String save_product(@ModelAttribute("product") Product product,
                               @RequestParam("fileImage") MultipartFile multipartFile,
                               RedirectAttributes redirectAttributes) throws IOException {
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
        redirectAttributes.addFlashAttribute("message", "Add product successfully!");
        return "redirect:/admin/products";
    }

    // Delete Product
    @GetMapping(value = "/products/delete/{id}")
    public String delete_product(@PathVariable(name = "id") Long id,
                                 RedirectAttributes redirectAttributes) {
        System.out.println(id);
        if (id != null) {
            productService.delete(id);
        } else {
            System.out.println("ID is null");
        }
        redirectAttributes.addFlashAttribute("message", "Delete product successfully!");
        return "redirect:/admin/products";
    }

    // Edit Product Page
    @GetMapping(value = "/products/edit/{id}")
    public String edit_product(@PathVariable(name = "id") Long id, Model model) {
        Product product = productService.getById(id);
        model.addAttribute("product", product);
        return "admin/products/edit-product";
    }

    // Update Product
    @PostMapping(value = "/products/edit/{id}")
    public String update_product(@ModelAttribute("product") Product product,
                                 @RequestParam("fileImage") MultipartFile multipartFile,
                                 RedirectAttributes redirectAttributes) throws IOException {
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
        redirectAttributes.addFlashAttribute("message", "Update product successfully!");
        return "redirect:/admin/products";
    }

    // Detail Product Page
    @GetMapping(value = "/products/detail/{id}")
    public String detail_product(@PathVariable(name = "id") Long id, Model model) {
        Product product = productService.getById(id);
        model.addAttribute("product", product);
        return "admin/products/detail-product";
    }

    // Order Page
    @GetMapping(value = "/orders")
    public String orders(Model model) {
        List<Order> orderList = orderService.getAll();
        model.addAttribute("orders", orderList);
        return "admin/orders";
    }

    // Delete Order
    @GetMapping(value = "/orders/delete/{id}")
    public String delete_order(@PathVariable(name = "id") Long id,
                               RedirectAttributes redirectAttributes) {
        if (id != null) {
            orderService.delete(id);
        } else {
            System.out.println("ID is null");
        }
        redirectAttributes.addFlashAttribute("message", "Delete order successfully!");
        return "redirect:/admin/orders";
    }

    // Update Order Status
    @PostMapping(value = "/orders/update/{id}")
    public String update_status(@PathVariable Long id, HttpServletRequest request,
                                RedirectAttributes redirectAttributes) {
        if (id != null) {
            Order order = orderService.getById(id);
            String status = request.getParameter("status");
            System.out.println(status);
            order.setStatus(status);
            orderService.update(order);
        } else {
            System.out.println("ID is null");
        }
        redirectAttributes.addFlashAttribute("message", "Update order status successfully!");
        return "redirect:/admin/orders";
    }

    // Customer Page
    @GetMapping(value = "/customers")
    public String customers(Model model) {
        List<User> customerList = userService.findAllCustomer();
        model.addAttribute("customers", customerList);
        return "admin/customers";
    }

    // Edit Customer Page
    @GetMapping(value = "/customers/edit/{id}")
    public String edit_customer(@PathVariable(name = "id") Long id, Model model) {
        User customer = userService.findUserById(id);
        if (customer != null) {
            model.addAttribute("customer", customer);
        } else {
            System.out.println("ID is null");
        }
        return "admin/customers/edit-customer";
    }

    // Update Customer
    @PostMapping(value = "/customers/edit/{id}")
    public String update_customer(@ModelAttribute("customer") User customer,
                                  @PathVariable(name = "id") Long id,
                                  RedirectAttributes redirectAttributes) {
        User currentCustomer = userService.findUserById(id);
        if (currentCustomer != null) {
            currentCustomer.setUsername(customer.getUsername());
            currentCustomer.setEmail(customer.getEmail());
            currentCustomer.setPhone(customer.getPhone());
            currentCustomer.setAge(customer.getAge());
            currentCustomer.setGender(customer.getGender());
            currentCustomer.setAddress(customer.getAddress());
            userService.updateUser(currentCustomer);
        } else {
            System.out.println("ID is null");
        }
        redirectAttributes.addFlashAttribute("message", "Update customer successfully");
        return "redirect:/admin/customers";
    }

    // Delete Customer
//    @GetMapping(value = "/customers/delete")
//    public String delete_customer(HttpServletRequest request,
//                                  RedirectAttributes redirectAttributes) {
//        String id = request.getParameter("id-delete");
//        if (id != null) {
//            userService.deleteUser(Long.parseLong(id));
//        } else {
//            System.out.println("ID is null");
//        }
//        redirectAttributes.addFlashAttribute("message", "Delete customer successfully");
//        return "redirect:/admin/customers";
//    }
}
