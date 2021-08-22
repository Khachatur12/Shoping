package com.example.shoping.rest;

import com.example.shoping.models.User;
import com.example.shoping.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/users")
@PreAuthorize("hasAuthority('admin:permission')")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public List<User> users() {
        return userService.findAll();
    }


    @GetMapping("/{username}")
    public User users(@PathVariable String username) {
        return userService.findByUsername(username);
    }


    @PostMapping
    public User create(@RequestBody User user) {
        userService.update(user);
        return user;
    }


    @DeleteMapping
    public void delete(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        userService.deleteByUsername(username);
    }


    @PostMapping("/block")
    public void blockByUsername(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        userService.blockByUsername(username);

        // delete from session
    }


    @PostMapping("/unblock")
    public void unblock(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        userService.unblockByUsername(username);
    }


}
