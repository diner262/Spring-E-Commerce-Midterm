package tdtu.edu.vn.midterm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import tdtu.edu.vn.midterm.dto.UserDto;
import tdtu.edu.vn.midterm.model.User;
import tdtu.edu.vn.midterm.service.UserServiceImpl;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private UserServiceImpl userService;

    public RegisterController(UserServiceImpl userService) {
        super();
        this.userService = userService;
    }

    @ModelAttribute("user")
    public UserDto user() {
        return new UserDto();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "accounts/register";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") UserDto userDto) {
        userService.registerUser(userDto);
        return "redirect:/login";
    }
}
