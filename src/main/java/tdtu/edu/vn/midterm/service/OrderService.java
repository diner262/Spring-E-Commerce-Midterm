package tdtu.edu.vn.midterm.service;

import org.springframework.stereotype.Service;
import tdtu.edu.vn.midterm.model.Order;

import java.util.List;

@Service
public interface OrderService {
    Order save(Order order);
    List<Order> getAll();
    Order getById(Long id);
    void delete(Long id);
    void update(Order order);
}
