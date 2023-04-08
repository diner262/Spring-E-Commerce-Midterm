package tdtu.edu.vn.midterm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tdtu.edu.vn.midterm.model.Product;
import tdtu.edu.vn.midterm.model.ShoppingCart;
import tdtu.edu.vn.midterm.model.User;
import tdtu.edu.vn.midterm.service.ProductService;
import tdtu.edu.vn.midterm.service.ShoppingCartService;
import tdtu.edu.vn.midterm.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = "/")
public class HomeController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private UserService userService;

    // Home page
    @GetMapping(value = {"/", "/home"})
    public String index(HttpServletRequest request,
                        Model model, @Param("keyword") String keyword) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails userDetails) {
            User user = userService.findUserByEmail(userDetails.getUsername());
            model.addAttribute("username", user.getUsername());
        }
        String sessionToken = (String) request.getSession(true).getAttribute("sessionToken");
        if (sessionToken != null) {
            ShoppingCart shoppingCart = shoppingCartService.getByTokenSession(sessionToken);
            if (shoppingCart != null) {
                model.addAttribute("cartItems", shoppingCart.getCartItems());
            }
        }

        List<Product> productList;
        if (keyword != null) {
            productList = productService.search(keyword);
        } else {
            productList = productService.getAll();
        }
        model.addAttribute("products", productList);
        model.addAttribute("keyword", keyword);
        return "index";
    }

    // Login page
    @GetMapping(value = "/login")
    public String login(Model model) {
        return "account/login";
    }

    // Profile
    @GetMapping(value = "/profile")
    public String profile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails userDetails) {
            User user = userService.findUserByEmail(userDetails.getUsername());
            model.addAttribute("user", user);
        }
        return "info/profile";
    }

    // Update user
    @PostMapping(value = "/profile/update")
    public String updateUser(@ModelAttribute("user") User userUpdate,
                             RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails userDetails) {
            User currentUser = userService.findUserByEmail(userDetails.getUsername());
            currentUser.setUsername(userUpdate.getUsername());
            currentUser.setGender(userUpdate.getGender());
            currentUser.setAddress(userUpdate.getAddress());
            currentUser.setPhone(userUpdate.getPhone());
            currentUser.setAge(userUpdate.getAge());

            userService.updateUser(currentUser);
        }
        redirectAttributes.addFlashAttribute("message", "Update profile successfully");
        return "redirect:/profile";
    }

    // Error
    @GetMapping(value = "error")
    public String handleError(HttpServletRequest request) {
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
        return "error/404";
    }
}
