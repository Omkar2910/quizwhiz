package com.quizwhiz.service;

import com.quizwhiz.model.User;
import com.quizwhiz.model.UserRole;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
@Service
public interface UserService {
    public User createUser(User user, Set<UserRole> userRoles) throws Exception;
    public List<User> getUsers();

    public User getUserByUsername(String username);

    public void deleteUserById(Long userId);
}
