package com.example.shopingcart.controller;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.shopingcart.model.Role;
import com.example.shopingcart.model.User;
import com.example.shopingcart.repository.RoleRepository;
import com.example.shopingcart.repository.UserRepository;
import com.example.shopingcart.security.PasswordEncoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin
@RequestMapping(value = "api/v1/")
public class Usercontroller {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RoleRepository roleRepo;


    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Usercontroller(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping(value = "/admin/user")
    public List<User> getListUser(){
        return userRepo.findAll();
    }

    @GetMapping(value = "/admin/user/role")
    public List<Role> getListRole(){
        return roleRepo.findAll();
    }

    @PostMapping(value = "/user/register")
    public ResponseEntity<User> register(@RequestBody User newUser){


        Role role = roleRepo.findByRoleName("USER");
        newUser.setPassWord(bCryptPasswordEncoder.encode(newUser.getPassWord()));
        newUser.getRoles().add(role);
        userRepo.save(newUser);
        return new ResponseEntity<>(newUser, HttpStatus.OK);


    }

    @PutMapping(value = "/admin/user")
    public ResponseEntity<User> updateUser(@RequestBody User updateUser, @RequestParam("rolename") String roleName){

        User _updateUser = userRepo.findByUserName(updateUser.getUserName());

        Role role = roleRepo.findByRoleName(roleName);
        updateUser.getRoles().add(role);
        updateUser.setId(updateUser.getId());
        updateUser.setUserName(updateUser.getUserName());
        updateUser.setPassWord(_updateUser.getPassWord());
        updateUser.setFullName(updateUser.getFullName());
        userRepo.save(updateUser);
        return new ResponseEntity<>(_updateUser, HttpStatus.OK);
    }


    @DeleteMapping(value = "/admin/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id){
        User deleteUser = userRepo.findById(id).get();
        userRepo.delete(deleteUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

// chuaw hieu chuc nang-----------------------------(co the la lam moi token khac cho user)
    @GetMapping(value = "/token/refresh")
    public void refreshToken (HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")){

            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String userName = decodedJWT.getSubject();
                User user = userRepo.findByUserName(userName);

                String accessToken = JWT.create()
                        .withSubject(user.getUserName())
                        .withExpiresAt(new Date(System.currentTimeMillis()+ 10*60*1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList()))
                        .sign(algorithm);



                Map<String , String> tokens = new HashMap<>();
                tokens.put("accessToken",accessToken);
                tokens.put("refreshToken",refreshToken);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),tokens);

            }catch (Exception exception){

                response.setHeader("Error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                //response.sendError(FORBIDDEN.value());

                Map<String , String> error = new HashMap<>();
                error.put("errorMsg",exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),error);
            }

        }else {
            throw new RuntimeException("Refresh token is missing");
        }
    }
}
