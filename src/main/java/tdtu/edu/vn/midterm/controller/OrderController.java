package tdtu.edu.vn.midterm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tdtu.edu.vn.midterm.model.*;
import tdtu.edu.vn.midterm.service.OrderDetailService;
import tdtu.edu.vn.midterm.service.OrderService;
import tdtu.edu.vn.midterm.service.ShoppingCartService;
import tdtu.edu.vn.midterm.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/cart/")
public class OrderController {
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private ShoppingCartService shoppingCartService;


    @GetMapping("/checkout")
    public String checkout(HttpServletRequest request, Model model, Principal principal) {
        String sessionToken = (String) request.getSession(false).getAttribute("sessionToken");

        if (principal == null) {
            return "redirect:/login";
        }

        ShoppingCart shoppingCart = shoppingCartService.getByTokenSession(sessionToken);
        if (sessionToken == null || shoppingCart == null) { 
            return "redirect:/cart";
        } else {
            // Add new order
            User user = userService.findUserByEmail(principal.getName());
            Order order = new Order();
            order.setUser(user);
            order.setDate(new Date());
            order.setStatus("In progress");
            order.setTotalPrice(shoppingCart.getTotalPrice());
            this.orderService.save(order);

            // Add new order detail
            List<CartItem> cartItems = shoppingCart.getCartItems();
            for (CartItem cartItem : cartItems) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrder(order);
                orderDetail.setProduct(cartItem.getProduct());
                orderDetail.setQuantity(cartItem.getQuantity());
                orderDetail.setTotalPrice(cartItem.getProduct().getPrice());
                this.orderDetailService.save(orderDetail);
            }
        }
        // Remove cart
        shoppingCartService.clearShoppingCart(sessionToken);
        model.addAttribute("thanks", "Thank you for shopping with us!");
        return "products/thanks";
    }
}
