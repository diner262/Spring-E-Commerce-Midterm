package tdtu.edu.vn.midterm.service;

import org.springframework.stereotype.Service;
import tdtu.edu.vn.midterm.dto.UserDto;
import tdtu.edu.vn.midterm.model.User;

import java.util.List;

@Service
public interface UserService {

    void registerUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();

}
