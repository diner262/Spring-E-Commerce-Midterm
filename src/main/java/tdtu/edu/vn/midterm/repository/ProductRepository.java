package tdtu.edu.vn.midterm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tdtu.edu.vn.midterm.model.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE CONCAT(p.name, p.brand, p.graphicCard, p.price," +
            " p.processor, p.ram, p.rom, p.screenSize, p.weight) LIKE %?1%")
    public List<Product> search(String keyword);
}
