package tdtu.edu.vn.midterm.service;

import org.springframework.stereotype.Service;
import tdtu.edu.vn.midterm.model.ShoppingCart;

@Service
public interface ShoppingCartService {
    ShoppingCart addToCartFirstTime(Long id, String sessionToken, Integer quantity);
    ShoppingCart addToExistingShoppingCart(Long id, String sessionToken, Integer quantity);
    ShoppingCart getByTokenSession(String tokenSession);
}
