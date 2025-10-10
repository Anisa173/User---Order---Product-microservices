package com.microservice.user.controller;


import com.microservice.user.dto.UserRequest;
import com.microservice.user.dto.UserResponse;
import com.microservice.user.model.User;
import com.microservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public List<UserResponse> addNewOne(@RequestBody UserRequest us) {
        return Collections.singletonList(userService.addNewOne(us));
    }

    @GetMapping("/{userId}")
    public UserResponse getUserById(@PathVariable("userId") Integer userId)
    {  log.info("Request received foe user with Id:" + userId);
       log.trace("This is Trace level--Used for very detailed logs ");
       log.debug("This is Debug level --- Used for development debugging");
       log.warn("This is Warn level ---Something might have happend but it's not critical");
       log.error("This is ERROR Level---Somthing failures");
        return userService.getUSERById(userId);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> modifyUser(@PathVariable("userId") Integer userId, @RequestBody UserResponse user) {
        boolean updated = userService.modifyUser(user, userId);
        if (updated) {
            return ResponseEntity.ok("User updated succesfully");
        }
        return ResponseEntity.notFound().build();

    }


}
