package com.microservice.user.dto;


import com.microservice.user.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserResponse {
    private Integer id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String phone;
    private UserRole userRole ;
    private AddressDto address;



}
