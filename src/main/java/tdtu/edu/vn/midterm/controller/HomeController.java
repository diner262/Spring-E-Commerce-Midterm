package tdtu.edu.vn.midterm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tdtu.edu.vn.midterm.model.Product;
import tdtu.edu.vn.midterm.model.ShoppingCart;
import tdtu.edu.vn.midterm.service.ProductService;
import tdtu.edu.vn.midterm.service.ShoppingCartService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller(value = "/")
@RequestMapping(value = "/")
public class HomeController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    // Home page
    @GetMapping(value = {"/", "/home"})
    public String index(HttpServletRequest request, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails userDetails) {
            String username = userDetails.getUsername();
            model.addAttribute("username", username);
        }
        String sessionToken = (String) request.getSession(true).getAttribute("sessionToken");
        if (sessionToken != null) {
            ShoppingCart shoppingCart = shoppingCartService.getByTokenSession(sessionToken);
            if (shoppingCart != null) {
                model.addAttribute("cartItems", shoppingCart.getCartItems());
            }
        }

        List<Product> productList = productService.getAll();
        model.addAttribute("products", productList);
        return "index";
    }

    // Login page
    @GetMapping(value = "/login")
    public String login(Model model, String error, String logout) {
        return "account/login";
    }

    // Logout
    @GetMapping(value = "/logout")
    public String logout(Model model) {
        return "logout";
    }

}
