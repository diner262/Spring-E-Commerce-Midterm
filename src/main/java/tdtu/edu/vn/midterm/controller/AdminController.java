package tdtu.edu.vn.midterm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tdtu.edu.vn.midterm.model.Product;
import tdtu.edu.vn.midterm.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {

        return "admin/dashboard";
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public String products(Model model) {
        List<Product> productList = productService.getAll();
        model.addAttribute("products", productList);
        return "admin/products";
    }

    @RequestMapping(value = "/products/add", method = RequestMethod.GET)
    public String add_product(Model model) {
        List<Product> productList = productService.getAll();
        Product product = new Product();

        model.addAttribute("products", productList);
        model.addAttribute("product", product);

        return "admin/layouts/add-product";
    }

    @RequestMapping(value = "/products/add", method = RequestMethod.POST)
    public String save_product(@ModelAttribute("product") Product product) {
        product.setImage("img/sample.png");
        productService.save(product);
        System.out.println("Save product: " + product.toString());
        return "redirect:/admin/products";
    }

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
}
