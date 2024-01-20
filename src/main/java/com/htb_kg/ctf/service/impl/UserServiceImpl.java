package com.htb_kg.ctf.service.impl;

import com.htb_kg.ctf.dto.user.BusinessUserResponse;
import com.htb_kg.ctf.dto.user.UserResponse;
import com.htb_kg.ctf.entities.Hacker;
import com.htb_kg.ctf.entities.Teacher;
import com.htb_kg.ctf.entities.User;
import com.htb_kg.ctf.enums.Role;
import com.htb_kg.ctf.exception.NotFoundException;
import com.htb_kg.ctf.mapper.UserMapper;
import com.htb_kg.ctf.repositories.FileRepository;
import com.htb_kg.ctf.repositories.HackerRepository;
import com.htb_kg.ctf.repositories.TeacherRepository;
import com.htb_kg.ctf.repositories.UserRepository;
import com.htb_kg.ctf.service.FileDataService;
import com.htb_kg.ctf.service.UserService;
import lombok.AllArgsConstructor;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final HackerRepository hackerRepository;
    private final TeacherRepository teacherRepository;
    private final FileRepository fileRepository;

    @Override
    public User getUsernameFromToken(String token) {

        String[] chunks = token.substring(7).split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        JSONParser jsonParser = new JSONParser();
        JSONObject object = null;
        try {
            object = (JSONObject) jsonParser.parse(decoder.decode(chunks[1]));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return userRepository.findByEmail(String.valueOf(object.get("sub"))).orElseThrow(() -> new RuntimeException("user can be null"));
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userMapper.toDtoS(userRepository.findAll());
    }

    @Override
    public void deleteUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty())
            throw new NotFoundException("user with id "+user+" is not exis!", HttpStatus.BAD_GATEWAY);
        userRepository.deleteById(userId);
    }

    @Override
    public List<BusinessUserResponse> getAllBusinessRequests() {

        return userMapper.toDtoSBusiness(userRepository.findAllByToBusiness(true));
    }

    @Override
    public void acceptUser(Boolean accept, Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty())
            throw new NotFoundException("user with id "+userId+" not found!",HttpStatus.BAD_GATEWAY);
        if (accept){

            user.get().setToBusiness(false);
            Teacher teacher = new Teacher();
            teacher.setProof(user.get().getAccessToBusiness());
            teacher.setUser(user.get());
            user.get().setTeacher(teacher);
            //hackerRepository.delete(user.get().getHacker());
            user.get().setHacker(null);
            user.get().setRole(Role.BUSINESS);
            teacherRepository.save(teacher);
            userRepository.save(user.get());
        }
    }


}
