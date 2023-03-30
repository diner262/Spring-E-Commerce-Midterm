package tdtu.edu.vn.midterm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tdtu.edu.vn.midterm.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
