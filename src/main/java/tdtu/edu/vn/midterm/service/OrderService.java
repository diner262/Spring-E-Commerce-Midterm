package tdtu.edu.vn.midterm.service;

import org.springframework.stereotype.Service;
import tdtu.edu.vn.midterm.model.Order;

@Service
public interface OrderService {
    Order save(Order order);
}
