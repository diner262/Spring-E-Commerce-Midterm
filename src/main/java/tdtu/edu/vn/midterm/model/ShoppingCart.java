package tdtu.edu.vn.midterm.model;

import lombok.Builder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shopping_cart")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;
    @Transient
    private int quantity;
    @Transient
    private double totalPrice;

    @OneToMany(cascade = CascadeType.ALL)
    private List<CartItem> cartItems = new ArrayList<CartItem>();

    private String tokenSession;

    public ShoppingCart() {
        cartItems = new ArrayList<CartItem>();
    }
    @Builder
    public ShoppingCart(Product product, int quantity, double totalPrice, String tokenSession) {
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.tokenSession = tokenSession;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return this.cartItems.size();
    }

    public double getTotalPrice() {
        double sum = 0.0;
        for (CartItem cartItem : this.cartItems) {
            sum += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }
        return sum;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public String getTokenSession() {
        return tokenSession;
    }

    public void setTokenSession(String tokenSession) {
        this.tokenSession = tokenSession;
    }
}
