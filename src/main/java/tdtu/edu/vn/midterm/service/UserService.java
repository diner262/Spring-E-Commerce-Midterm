package tdtu.edu.vn.midterm.service;

import org.springframework.stereotype.Service;
import tdtu.edu.vn.midterm.model.User;

@Service
public interface UserService {

    void registerUser(User user);

}
