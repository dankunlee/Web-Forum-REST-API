package com.dankunlee.forumapp.controller;

import com.dankunlee.forumapp.entity.User;
import com.dankunlee.forumapp.repository.UserRepository;
import com.dankunlee.forumapp.utils.Password;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller // @RestController = @Controller + @ResponseBody
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/api/register")
    @ResponseBody
    public String register(@Valid @RequestBody User user) {
        String username = user.getUsername();
        String rawPassword = user.getPassword();
        String encryptedPassword = Password.encode(rawPassword, Password.randomSalt);
        String email = user.getEmail();

        if (username.equals("") || encryptedPassword.equals("") || email.equals(""))
            return "Empty Parameter is Given";

        if (userRepository.findByUsername(username) != null) return "Fail: Existing Username";
        if (userRepository.findByEmail(email) != null) return "Fail: Existing Email";

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(encryptedPassword);
        newUser.setEmail(email);
        userRepository.save(newUser);

        return "Success";
    }

}
