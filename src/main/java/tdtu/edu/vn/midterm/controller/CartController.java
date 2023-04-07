package tdtu.edu.vn.midterm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tdtu.edu.vn.midterm.model.ShoppingCart;
import tdtu.edu.vn.midterm.model.User;
import tdtu.edu.vn.midterm.service.ShoppingCartService;
import tdtu.edu.vn.midterm.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
public class CartController {

    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private UserService userService;

    @GetMapping(value = "/cart")
    public String showCart(HttpServletRequest request, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails userDetails) {
            String username = userDetails.getUsername();
            User user = userService.findUserByEmail(username);
            model.addAttribute("username", user.getUsername());
        }

        String sessionToken = (String) request.getSession(true).getAttribute("sessionToken");

        if (sessionToken == null) {
            model.addAttribute("cartItems", new ShoppingCart().getCartItems());
            model.addAttribute("totalPrice", 0.0);
        } else {
            ShoppingCart shoppingCart = shoppingCartService.getByTokenSession(sessionToken);
            if (shoppingCart == null) {
                model.addAttribute("cartItems", new ShoppingCart().getCartItems());
                model.addAttribute("totalPrice", 0.0);
            } else {
                model.addAttribute("cartItems", shoppingCart.getCartItems());
                model.addAttribute("totalPrice", shoppingCart.getTotalPrice());
            }
        }

        return "cart/cart-shopping";
    }

    @PostMapping("/add")
    public String addToCart(HttpSession session, Model model,
                            @RequestParam("id") Long id, @RequestParam("quantity") Integer quantity) {
        String sessionToken = (String) session.getAttribute("sessionToken");

        if (sessionToken == null) {
            sessionToken = UUID.randomUUID().toString();
            session.setAttribute("sessionToken", sessionToken);
            shoppingCartService.addToCartFirstTime(id, sessionToken, quantity);
        } else {
            shoppingCartService.addToExistingShoppingCart(id, sessionToken, quantity);
        }
        return "redirect:/cart";
    }

    @GetMapping(value = "/cart/delete/{id}")
    public String removeCartItem(@PathVariable("id") Long id, HttpServletRequest request) {
        String sessionToken = (String) request.getSession(false).getAttribute("sessionToken");
        shoppingCartService.removeCartItem(id,sessionToken);

        return "redirect:/cart";
    }

    @GetMapping(value = "/cart/clearShoppingCart")
    public String clearShoppingCart(HttpServletRequest request) {
        String sessionToken = (String) request.getSession(true).getAttribute("sessionToken");
        shoppingCartService.clearShoppingCart(sessionToken);

        return "redirect:/cart";
    }
}
