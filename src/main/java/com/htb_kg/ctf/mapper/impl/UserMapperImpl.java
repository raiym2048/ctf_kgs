package com.htb_kg.ctf.mapper.impl;

import com.htb_kg.ctf.dto.rank.RankingResponse;
import com.htb_kg.ctf.dto.user.BusinessUserResponse;
import com.htb_kg.ctf.dto.user.UserResponse;
import com.htb_kg.ctf.entities.Hacker;
import com.htb_kg.ctf.entities.User;
import com.htb_kg.ctf.mapper.FileMapper;
import com.htb_kg.ctf.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapperImpl implements UserMapper {
    @Autowired
    private  FileMapper fileMapper;
    @Override
    public List<UserResponse> toDtoS(List<User> all) {
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user:  all){
            userResponses.add(toDto(user));
        }
        return userResponses;
    }

    @Override
    public UserResponse toDto(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(user.getId());
        userResponse.setEmail(user.getEmail());
        userResponse.setNickname(user.getNickname());
        userResponse.setRole(user.getRole());
        userResponse.setLogo_image(user.getLogo_image()!=null? fileMapper.toDto(user.getLogo_image()): null);
        return userResponse;
    }

    @Override
    public List<RankingResponse> toRanking(List<Hacker> allByOrderByPointsAsc) {
        List<RankingResponse> rankingResponses = new ArrayList<>();
        for (Hacker hacker: allByOrderByPointsAsc){
            rankingResponses.add(toRank(hacker));
        }
        return rankingResponses;
    }

    @Override
    public List<BusinessUserResponse> toDtoSBusiness(List<User> allByToBusiness) {
        List<BusinessUserResponse> businessUserResponses = new ArrayList<>();
        for (User user: allByToBusiness){
            businessUserResponses.add(toDtoBusiness(user));
        }
        return businessUserResponses;
    }

    private BusinessUserResponse toDtoBusiness(User user) {
        BusinessUserResponse businessUserResponse = new BusinessUserResponse();
        businessUserResponse.setId(user.getId());
        businessUserResponse.setCreatingDate(user.getCreationDate());
        businessUserResponse.setSentDate(user.getBusinessFileSentDate());
        businessUserResponse.setEmail(user.getEmail());
        businessUserResponse.setAccessFile(user.getAccessToBusiness()!=null? fileMapper.toDto(
                user.getAccessToBusiness()):null);

        return businessUserResponse;
    }

    private RankingResponse toRank(Hacker hacker) {
        RankingResponse rankingResponse = new RankingResponse();
        rankingResponse.setNickname(hacker.getUser().getNickname());
        rankingResponse.setLogo_image(hacker.getUser().getLogo_image()
                !=null? fileMapper.toDto(hacker.getUser().getLogo_image()):null);
        rankingResponse.setPoints(hacker.getPoints());
        return rankingResponse;
    }
}
