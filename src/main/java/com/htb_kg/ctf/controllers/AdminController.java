package com.htb_kg.ctf.controllers;


import com.htb_kg.ctf.dto.user.BusinessUserResponse;
import com.htb_kg.ctf.dto.user.UserResponse;
import com.htb_kg.ctf.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdminController {

    private final UserService userService;

    @GetMapping("/toBusiness")
    public List<BusinessUserResponse> getAllRequests(){
        return userService.getAllBusinessRequests();
    }

    @PostMapping("/accept/{userId}")
    public void acceptUser(@RequestParam(required = false) Boolean accept, @PathVariable Long userId){
        userService.acceptUser(accept,userId);
    }
}
