package tdtu.edu.vn.midterm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tdtu.edu.vn.midterm.model.Order;
import tdtu.edu.vn.midterm.model.Product;
import tdtu.edu.vn.midterm.repository.OrderRepository;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;


    @Override
    public Order save(Order order) {
        return this.orderRepository.save(order);
    }

    @Override
    public List<Order> getAll() {
        return this.orderRepository.findAll();
    }

    @Override
    public Order getById(Long id) {
        return this.orderRepository.getById(id);
    }

    @Override
    public void delete(Long id) {
        this.orderRepository.deleteById(id);
    }

    @Override
    public void update(Order order) {
        this.orderRepository.save(order);
    }
}
