package com.quizwhiz.service.impl;

import com.quizwhiz.exception.UserNotFoundException;
import com.quizwhiz.model.User;
import com.quizwhiz.model.UserRole;
import com.quizwhiz.repository.RoleRepository;
import com.quizwhiz.repository.UserRepository;
import com.quizwhiz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    //creating user
    @Override
    public User createUser(User user, Set<UserRole> userRoles) throws Exception {
        user.setProfileImage("default.png");
        User local = userRepository.findByUsername(user.getUsername());
        if(local != null){
            System.out.println("User already exists");
            throw new Exception("User with "+user.getUsername()+" username is already present !!");
        }else{

            for (UserRole ur: userRoles){
                roleRepository.save(ur.getRole());
            }
            user.getUserRoles().addAll(userRoles);
            local = this.userRepository.save(user);
        }

        return local;
    }

    //Get all users
    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    //Get user by username
    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void deleteUserById(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException(userId));

        userRepository.delete(user);
    }

}
