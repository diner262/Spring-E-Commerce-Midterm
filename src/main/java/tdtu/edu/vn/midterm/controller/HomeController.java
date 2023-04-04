package tdtu.edu.vn.midterm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tdtu.edu.vn.midterm.model.Product;
import tdtu.edu.vn.midterm.service.ProductService;

import java.util.List;

@Controller(value = "/")
public class HomeController {
    @Autowired
    private ProductService productService;


    @RequestMapping(value = {"/","/home"}, method = RequestMethod.GET)
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails userDetails) {
            String username = userDetails.getUsername();
            model.addAttribute("username", username);
        }
        List<Product> productList = productService.getAll();
        model.addAttribute("products", productList);
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        return "account/login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(Model model) {
        return "logout";
    }


    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public String cart(Model model) {
        return "products/cart";
    }
}
