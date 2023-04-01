package tdtu.edu.vn.midterm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tdtu.edu.vn.midterm.model.Product;
import tdtu.edu.vn.midterm.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public String products(Model model) {
        List<Product> productList = productService.getAll();
        model.addAttribute("products", productList);
        return "admin/products";
    }

    // Add Product Page
    @RequestMapping(value = "/products/add", method = RequestMethod.GET)
    public String add_product(Model model) {
        List<Product> productList = productService.getAll();
        Product product = new Product();

        model.addAttribute("products", productList);
        model.addAttribute("product", product);

        return "admin/layouts/add-product";
    }

    // Add Product
    @RequestMapping(value = "/products/add", method = RequestMethod.POST)
    public String save_product(@ModelAttribute("product") Product product) {
        product.setImage("img/sample.png");
        productService.save(product);
        System.out.println("Save product: " + product.toString());
        return "redirect:/admin/products";
    }

    // Delete Product
    @RequestMapping(value = "/products/delete", method = RequestMethod.POST)
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
    @RequestMapping(value = "/products/edit/{id}", method = RequestMethod.GET)
    public String edit_product(@PathVariable(name = "id") Long id, Model model) {
        Product product = productService.getById(id);
        model.addAttribute("product", product);
        return "admin/layouts/edit-product";
    }

    // Update Product
    @RequestMapping(value = "/products/edit/{id}", method = RequestMethod.POST)
    public String update_product(@ModelAttribute("product") Product product) {
        product.setImage("img/sample.png");
        productService.update(product);
        return "redirect:/admin/products";
    }

    // Detail Product Page
    @RequestMapping(value = "/products/detail/{id}", method = RequestMethod.GET)
    public String detail_product(@PathVariable(name = "id") Long id, Model model) {
        Product product = productService.getById(id);
        model.addAttribute("product", product);
        return "admin/layouts/detail-product";
    }
}
