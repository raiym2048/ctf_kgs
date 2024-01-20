package com.htb_kg.ctf.mapper;

import com.htb_kg.ctf.dto.rank.RankingResponse;
import com.htb_kg.ctf.dto.user.BusinessUserResponse;
import com.htb_kg.ctf.dto.user.UserResponse;
import com.htb_kg.ctf.entities.Hacker;
import com.htb_kg.ctf.entities.User;

import java.util.List;

public interface UserMapper {
    List<UserResponse> toDtoS(List<User> all);

    UserResponse toDto(User user);

    List<RankingResponse> toRanking(List<Hacker> allByOrderByPointsAsc);

    List<BusinessUserResponse> toDtoSBusiness(List<User> allByToBusiness);
}
