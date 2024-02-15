package com.htb_kg.ctf.service.impl;

import com.htb_kg.ctf.dto.RegisterHackerRequest;
import com.htb_kg.ctf.dto.auth.AuthenticationResponse;
import com.htb_kg.ctf.dto.auth.LoginRequest;
import com.htb_kg.ctf.dto.teacher.TeacherResponse;
import com.htb_kg.ctf.dto.user.UserResponse;
import com.htb_kg.ctf.entities.Hacker;
import com.htb_kg.ctf.entities.User;
import com.htb_kg.ctf.enums.Role;
import com.htb_kg.ctf.exception.BadCredentialsException;
import com.htb_kg.ctf.mapper.FileMapper;
import com.htb_kg.ctf.repositories.UserRepository;
import com.htb_kg.ctf.security.JwtService;
import com.htb_kg.ctf.service.AuthenticationService;
import com.htb_kg.ctf.service.FileDataService;
import com.htb_kg.ctf.service.UserService;
import com.htb_kg.ctf.service.emailSender.EmailSenderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.htb_kg.ctf.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final FileDataService fileDataService;
    private final FileMapper fileMapper;
    private final EmailSenderService emailSenderService;
    private final UserService userService;

    @Override
    public ResponseEntity<AuthenticationResponse> hackerRegister(RegisterHackerRequest request) {
        checkUsernameAndEmailIsExist(request.getEmail(), request.getNickname());
        User user = new User();
        if (request.getEmail().contains("@")) {
            user.setEmail(request.getEmail());
        }
        else {
            throw new BadCredentialsException("use @!");
        }
        if (request.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        user.setCreationDate(LocalDateTime.now().toString());

//        user.setCheckCode(-99);
//        emailSenderService.sendEmail(user.getEmail(),"the check code: ", ""+user.getCheckCode());
        user.setNickname(request.getNickname());

        Hacker hacker = new Hacker();
        user.setHacker(hacker);
        user.setRole(Role.HACKER);



        userRepository.save(user);
        return ResponseEntity.ok(convertAuthentication(user));
    }

    private Integer randomCode() {
        Random random = new Random();
        return random.nextInt(10000) + 1;
    }

    @Override
    public AuthenticationResponse login(LoginRequest request) {
        Optional<User> optionalAuth = userRepository.findByEmailOrNickname(request.getEmailOrNickname(), request.getEmailOrNickname());

        if (optionalAuth.isEmpty()) {
            throw new NotFoundException("User not found with email or nickname: " + request.getEmailOrNickname(), HttpStatus.BAD_GATEWAY);
        }
//        if (optionalAuth.get().getCheckCode() == -99)
//            throw new BadCredentialsException("the user do not access the code");

        User auth = optionalAuth.get();

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    auth.getEmail(),
                    request.getPassword()));

            // Set the authentication result into the SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);

            System.out.println("Success auth");
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Bad credentials");
        }

        Map<String, Object> extraClaims = new HashMap<>();

        String token = jwtService.generateToken(extraClaims, auth);

        String logoLetter = auth.getNickname().substring(0,1).toUpperCase();
        if (auth.getNickname().length()> 2)
            logoLetter = auth.getNickname().substring(0,2).toUpperCase();
        String[] colors = {"#FFD1DC","#EFA94A" , "#7FB5B5","#5D9B9B", "#77DD77", "#FF7514", "#FF9BAA"};

        return AuthenticationResponse.builder()
                .user(convertToUserResponse(auth))
                .accessToken(token)
                .logoLetter(logoLetter)
                .color(colors[randomColor(colors.length)])
                .build();
    }

    private int randomColor(int length) {
        Random random = new Random();

        // Next int will generate a number between 0 (inclusive) and 7 (exclusive)
        return random.nextInt(length);
    }

    private UserResponse convertToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(user.getId());
        if (user.getLogo_image()!=null)
            userResponse.setLogo_image(fileMapper.toDto(user.getLogo_image()));
        userResponse.setEmail(user.getEmail());
        userResponse.setNickname(user.getNickname());
        userResponse.setRole(user.getRole());
       // userResponse.setTeacherOrHacker(user.getRole().equals(Role.HACKER)? convertToResponseHacker(user): convertToResponseTeacher(user));
        return userResponse;
    }

    @Override
    public void checkCode(String email, Integer code) {
        Optional<User> user = userRepository.findByEmailOrNickname(email, email);
        if (user.isPresent()){
            if (user.get().getCheckCode().equals(code)){
                user.get().setCheckCode(-99);
                userRepository.save(user.get());
            }
            else {
                throw new BadCredentialsException("the code is not correct");
            }
        }
        else {
            throw new BadCredentialsException("User with this email is not present");
        }
    }

    @Override
    public void refreshPasswordSend(String emailOrNickname) {
        Optional<User> user = userRepository.findByEmailOrNickname(emailOrNickname, emailOrNickname);
        if (user.isEmpty())
            throw new NotFoundException("the user with email/nickname "+emailOrNickname+" is not exist!", HttpStatus.BAD_GATEWAY);
        int random =  randomCode();
        emailSenderService.sendEmail(user.get().getEmail(), "refresh code", "the code "+ random);
        user.get().setRefreshCode(random);
        userRepository.save(user.get());
    }

    @Override
    public Boolean checkRefreshCode(String emailOrNickname, Integer code) {
        Optional<User> user = userRepository.findByEmailOrNickname(emailOrNickname, emailOrNickname);
        if (user.isEmpty())
            throw new NotFoundException("the user with email/nickname "+emailOrNickname+" is not exist!", HttpStatus.BAD_GATEWAY);
        if (user.get().getRefreshCode().equals(code))
            return  true;
        return false;
    }

    @Override
    public void newPassword(String email, String password) {
        Optional<User> user = userRepository.findByEmailOrNickname(email, email);
        if (user.isEmpty())
            throw new NotFoundException("user with email/nickname "+email+" is not exist!",HttpStatus.BAD_GATEWAY);
        user.get().setPassword(passwordEncoder.encode(password));
        userRepository.save(user.get());
    }

    @Override
    public void deleteExceptoin(Long id) {
        if (userRepository.findById(id).isEmpty())
            throw new NotFoundException("wkhejdskl", HttpStatus.BAD_REQUEST);
    }


    private AuthenticationResponse convertAuthentication(User user) {
        AuthenticationResponse response = new AuthenticationResponse();

        response.setUser(convertToUserResponse(user));
        Map<String, Object> extraClaims = new HashMap<>();

        String token = jwtService.generateToken(extraClaims, user);        response.setAccessToken(token);
        return response;
    }
    private TeacherResponse convertToResponseTeacher(User user) {
        System.out.println("convert is working");
        TeacherResponse teacherResponse = new TeacherResponse();
        teacherResponse.setUserId(user.getId());
        teacherResponse.setEmail(user.getEmail());
        teacherResponse.setNickname(user.getNickname());
        teacherResponse.setRole(user.getRole());

        return teacherResponse;
    }

    private void checkUsernameAndEmailIsExist(String email, String nickname) {
        Optional<User> userEmail = userRepository.findByEmailOrNickname(email, nickname);
        if (userEmail.isPresent()) {
            System.out.println("\n\nemail is present\n\n");
            throw new BadCredentialsException("this email or nickname is already exists!");
        }
        else {
            System.out.println("\n\nemail is not present\n\n");

        }
    }
}
