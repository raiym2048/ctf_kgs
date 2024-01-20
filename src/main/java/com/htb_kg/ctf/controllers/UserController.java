package com.htb_kg.ctf.controllers;


import com.htb_kg.ctf.dto.user.UserResponse;
import com.htb_kg.ctf.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    private final UserService userService;
    @GetMapping("/getAllUsers")
    public List<UserResponse> users(){
        return userService.getAllUsers();
    }

    @DeleteMapping("/deleteUser/{userId}")
    public void deleteUser(@PathVariable Long userId){
        userService.deleteUserById(userId);
    }

}
