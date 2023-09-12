package com.quizwhiz.controller;

import com.quizwhiz.exception.UserNotFoundException;
import com.quizwhiz.model.Role;
import com.quizwhiz.model.User;
import com.quizwhiz.model.UserRole;
import com.quizwhiz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<User>> getUser() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(@PathVariable("username") String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @PostMapping("/")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            Set<UserRole> roles = new HashSet<>();
            Role role = new Role(45L, "NORMAL");
            UserRole userRole = new UserRole(user, role);
            roles.add(userRole);
            User createdUser = userService.createUser(user, roles);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("User creation failed: " + e.getMessage());
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable("userId") Long userId) {

        try {
            userService.deleteUserById(userId);
            return ResponseEntity.ok("User deleted Successfully");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

}
