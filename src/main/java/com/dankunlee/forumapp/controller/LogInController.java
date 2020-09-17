package com.dankunlee.forumapp.controller;

import com.dankunlee.forumapp.dto.UserDTO;
import com.dankunlee.forumapp.entity.User;
import com.dankunlee.forumapp.repository.UserRepository;
import com.dankunlee.forumapp.utils.Password;
import com.dankunlee.forumapp.utils.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class LogInController {
    @Autowired
    UserRepository userRepository;

    @PostMapping("/api/login")
    @ResponseBody
    public String login(@RequestBody UserDTO userDTO, HttpSession session) {
        String username = userDTO.getUsername();
        String rawPassword = userDTO.getPassword();
        String encryptedPassword = Password.encode(rawPassword, Password.randomSalt);

        User user = userRepository.findByUsernameAndPassword(username, encryptedPassword);

        if (user == null) {
            session.invalidate();
            return "Fail";
        }
        session.setAttribute(Session.SESSION_ID, username);
        return "Logged In";
    }

    @PostMapping("/api/logout")
    @ResponseBody
    public String logout(HttpSession session) {
        session.invalidate();
        return "Logged Out";
    }
}
