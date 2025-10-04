package com.microservice.user.controller;


import com.microservice.user.dto.UserRequest;
import com.microservice.user.dto.UserResponse;
import com.microservice.user.model.User;
import com.microservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/us")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public List<UserResponse> addNewOne(@RequestBody UserRequest us) {
        return Collections.singletonList(userService.addNewOne(us));
    }

    @GetMapping("/users/{userId}")
    public UserResponse getUserById(@PathVariable("userId") Integer userId) {
        return userService.getUSERById(userId);
    }

    @PutMapping("/userModified/{userId}")
    public ResponseEntity<String> modifyUser(@PathVariable("userId") Integer userId, @RequestBody UserResponse user) {
        boolean updated = userService.modifyUser(user, userId);
        if (updated) {
            return ResponseEntity.ok("User updated succesfully");
        }
        return ResponseEntity.notFound().build();

    }


}
