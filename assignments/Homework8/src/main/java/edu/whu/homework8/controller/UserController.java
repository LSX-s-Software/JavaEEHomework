package edu.whu.homework8.controller;

import edu.whu.homework8.dao.UserRepository;
import edu.whu.homework8.entity.User;
import edu.whu.homework8.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("{userName}")
    @PreAuthorize("hasAuthority('admin') or #userName == authentication.name")
    public User getUser(@PathVariable String userName){
        return userService.getUser(userName);
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('admin')")
    public List<User> findAllUser(){
        return userService.getAllUsers();
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('admin')")
    public User addUser(@RequestBody User user){
        return userService.addUser(user);
    }
}
