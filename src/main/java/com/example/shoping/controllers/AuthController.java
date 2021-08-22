package com.example.shoping.controllers;

import com.example.shoping.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/success")
    public String getSuccessPage() {
        return "success";
    }

    @GetMapping("/logout")
    public String getLogoutPage() {
        return "logout";
    }

    @GetMapping("/sing-up")
    public String getSingUpPage() {
        return "sing_up";
    }

    @PostMapping("/sing-up")
    public String singUp(@RequestBody Map<String, String> body) {
        userService.singUp(body.get("username"), body.get("password"));
        return "/login";
    }

}
