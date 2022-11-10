package edu.whu.homework8.service;

import edu.whu.homework8.dao.UserRepository;
import edu.whu.homework8.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Cacheable(cacheNames = "user", key = "#name", condition = "#name != null")
    public User getUser(String name){
        Optional<User> u = userRepository.findById(name);
        return u.isEmpty() ? null : u.get();
    }

    public User addUser(User user){
        return userRepository.save(user);
    }

    @CacheEvict(cacheNames = "user", key = "#name")
    public User updatePassword(User user){
        return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }


}
