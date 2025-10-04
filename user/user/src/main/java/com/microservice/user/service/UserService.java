package com.microservice.user.service;



import com.microservice.user.dto.UserRequest;
import com.microservice.user.dto.UserResponse;
import com.microservice.user.model.User;

import java.util.List;

public interface UserService {

    public List<UserResponse> getAllUsers();
    //public List<User> addNewOne(User user);
    public UserResponse addNewOne(UserRequest userR);


    public UserResponse getUSERById(Integer userId);

    public boolean modifyUser(UserResponse user, Integer userId);



}
