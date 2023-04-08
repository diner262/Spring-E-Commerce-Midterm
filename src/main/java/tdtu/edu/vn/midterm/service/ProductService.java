package tdtu.edu.vn.midterm.service;

import org.springframework.stereotype.Service;
import tdtu.edu.vn.midterm.model.Product;

import java.util.List;

@Service
public interface ProductService {
    void save(Product product);
    Product getById(Long id);
    List<Product> getAll();
    void delete(Long id);
    void update(Product product);
    List<Product> search(String keyword);
}
