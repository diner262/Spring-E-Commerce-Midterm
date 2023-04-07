package tdtu.edu.vn.midterm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tdtu.edu.vn.midterm.model.Order;
import tdtu.edu.vn.midterm.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;


    @Override
    public Order save(Order order) {
        return this.orderRepository.save(order);
    }
}
