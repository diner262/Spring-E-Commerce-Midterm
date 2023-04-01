package tdtu.edu.vn.midterm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tdtu.edu.vn.midterm.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
