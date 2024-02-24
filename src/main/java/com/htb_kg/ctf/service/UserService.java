package com.htb_kg.ctf.service;

import com.htb_kg.ctf.dto.user.BusinessUserResponse;
import com.htb_kg.ctf.dto.user.UserResponse;
import com.htb_kg.ctf.entities.User;

import java.util.List;

public interface UserService {
    User getUsernameFromToken(String token);

    User nullableUserFromToken(String token);

    List<UserResponse> getAllUsers();

    void deleteUserById(Long userId);

    List<BusinessUserResponse> getAllBusinessRequests();

    void acceptUser(Boolean accept, Long userId);
}
