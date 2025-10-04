package com.microservice.user.service.ServiceImpl;


import com.microservice.user.dto.AddressDto;
import com.microservice.user.dto.UserRequest;
import com.microservice.user.dto.UserResponse;
import com.microservice.user.model.User;
import com.microservice.user.repository.UserRepository;
import com.microservice.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    // List<User> users = new ArrayList<User>();
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(c -> mapToUserResponse(c))
                .collect(Collectors.toList());

    }

    @Override
    public UserResponse addNewOne(UserRequest userR) {
        User user = toEntity(userR);
        User u = userRepository.save(user);
        return mapToUserResponse(u);
    }

    @Override
    public UserResponse getUSERById(Integer userId) {
        //for (User user : users) {
        //       if (user.getId().equals(userId)) {
        //           return user;
        //      }
        return userRepository.findById(userId).map(this::mapToUserResponse).orElseThrow();
    }


    @Override
    public boolean modifyUser(UserResponse user, Integer userId) {
        //   return users.stream()
        //           .filter(useri -> useri.getId().equals(userId))
        //           .findFirst()
        //           .map(existingUser -> {
        //               existingUser.setFirstName(user.getFirstName());
        //               existingUser.setLastName(user.getLastName());
        //               return true;
        //           }).orElse(false);
        return userRepository.findById(userId).map(existingUser -> {
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            userRepository.save(existingUser);
            return true;
        }).orElse(false);

    }

    private UserResponse mapToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhone(user.getPhone());
        userResponse.setUserRole(user.getUserRole());
        if (user.getAddress() != null) {
            AddressDto adr = new AddressDto();
            adr.setStreet(user.getAddress().getStreet());
            adr.setCity(user.getAddress().getCity());
            adr.setState(user.getAddress().getState());
            adr.setCountry(user.getAddress().getCountry());
            adr.setZipCode(user.getAddress().getZipCode());
            userResponse.setAddress(adr);
        }
        return userResponse;
    }

    private User toEntity(UserRequest userR) {
        User user = new User();
        user.setFirstName(userR.getFirstName());
        user.setLastName(userR.getLastName());
        user.setUsername(userR.getUsername());
        user.setEmail(userR.getEmail());
        user.setPhone(userR.getPhone());
        user.setUserRole(userR.getUserRole());
        return user;
    }


}