package com.example.shoping.services;

import com.example.shoping.models.Role;
import com.example.shoping.models.Status;
import com.example.shoping.models.User;
import com.example.shoping.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final String PASSWORD_EXPRESSION = "(?i)^(?=[a-z])(?=.*[0-9])([a-z0-9!@#$%\\^&*()_?+\\-=]){8,15}$";

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void update(User user) {
        userRepository.save(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public void deleteByUsername(String username) {
        User user = findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("User not found");

        delete(user);
    }

    public void blockByUsername(String username){
        User user = findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("user not found");

        user.setStatus(Status.BLOCKED);
        update(user);
    }

    public void unblockByUsername(String username){
        User user = findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("user not found");

        user.setStatus(Status.ACTIVE);
        update(user);
    }

    public void singUp(String username, String password){
        User user = findByUsername(username);
        if (user != null) {
            throw new BadCredentialsException("Username already exists");
        }
        if (!password.matches(PASSWORD_EXPRESSION)) {
            throw new BadCredentialsException("Bad password");
        }

        user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(Role.USER);
        user.setStatus(Status.ACTIVE);

        userRepository.save(user);
    }
}
