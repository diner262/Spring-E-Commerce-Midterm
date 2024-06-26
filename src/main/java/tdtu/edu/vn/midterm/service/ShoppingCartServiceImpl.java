package tdtu.edu.vn.midterm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tdtu.edu.vn.midterm.model.CartItem;
import tdtu.edu.vn.midterm.model.Product;
import tdtu.edu.vn.midterm.model.ShoppingCart;
import tdtu.edu.vn.midterm.repository.CartItemRepository;
import tdtu.edu.vn.midterm.repository.ShoppingCartRepository;

import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ProductService productService;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Override
    public ShoppingCart addToCartFirstTime(Long id, String sessionToken, Integer quantity) {
        ShoppingCart shoppingCart = new ShoppingCart();
        CartItem cartItem = new CartItem();

        cartItem.setQuantity(quantity);
        cartItem.setProduct(productService.getById(id));
        double totalPrice = cartItem.getProduct().getPrice() * cartItem.getQuantity();
        cartItem.setTotalPrice(totalPrice);

        shoppingCart.getCartItems().add(cartItem);
        shoppingCart.setTokenSession(sessionToken);

        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart addToExistingShoppingCart(Long id, String sessionToken, Integer quantity) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByTokenSession(sessionToken);
        Product p = productService.getById(id);
        boolean isExistInCart = false;

        if (shoppingCart != null) {
            List<CartItem> cartItems = shoppingCart.getCartItems();
            for (CartItem cartItem : cartItems) {
                if(cartItem.getProduct().equals(p)) {
                    isExistInCart = true;
                    cartItem.setQuantity(cartItem.getQuantity() + quantity);
                    return shoppingCartRepository.saveAndFlush(shoppingCart);
                }
            }
        }

        if (shoppingCart != null && !isExistInCart) {
            CartItem cartItem = new CartItem();
            cartItem.setProduct(p);
            cartItem.setQuantity(quantity);
            cartItem.setProduct(p);
            double totalPrice = cartItem.getProduct().getPrice() * cartItem.getQuantity();
            cartItem.setTotalPrice(totalPrice);

            shoppingCart.getCartItems().add(cartItem);
            return shoppingCartRepository.saveAndFlush(shoppingCart);
        }

        return this.addToCartFirstTime(id, sessionToken, quantity);
    }

    @Override
    public ShoppingCart getByTokenSession(String tokenSession) {
        return shoppingCartRepository.findByTokenSession(tokenSession);
    }

    @Override
    public ShoppingCart removeCartItem(Long id, String sessionToken) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByTokenSession(sessionToken);
        List<CartItem> items = shoppingCart.getCartItems();
        CartItem cartItem = null;
        for(CartItem item : items) {
            if(item.getId().equals(id)) {
                cartItem = item;
            }
        }
        items.remove(cartItem);
        cartItemRepository.delete(cartItem);
        shoppingCart.setCartItems(items);
        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public void clearShoppingCart(String tokenSession) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByTokenSession(tokenSession);
        shoppingCartRepository.delete(shoppingCart);
    }

}

