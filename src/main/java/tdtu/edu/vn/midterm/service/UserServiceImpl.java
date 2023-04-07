package tdtu.edu.vn.midterm.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tdtu.edu.vn.midterm.dto.UserDto;
import tdtu.edu.vn.midterm.model.Role;
import tdtu.edu.vn.midterm.model.User;
import tdtu.edu.vn.midterm.repository.RoleRepository;
import tdtu.edu.vn.midterm.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void registerUser(UserDto userToRegister) {
        User user = new User();
        user.setUsername(userToRegister.getUsername());
        user.setEmail(userToRegister.getEmail());
        user.setPassword(passwordEncoder.encode(userToRegister.getPassword()));

        Role role = roleRepository.findByName("ROLE_USER");
        if (role == null) {
            role = checkRoleExist();
        }
        user.setRoles(List.of(role));

        userRepository.save(user);
    }

    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_USER");
        return roleRepository.save(role);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAllCustomer() {
        return userRepository.findAll().stream()
                .filter(user -> user.getRoles().contains(roleRepository.findByName("ROLE_USER")))
                .collect(Collectors.toList());
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
