package com.microservice.user.dto;


import com.microservice.user.model.User;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddressDto {

    private Long id;
    @Column(name = "city")
    private String city;
    @Column(name = "street")
    private String street;
    @Column(name = "zipcode")
    private String zipCode;
    @Column(name = "country")
    private String country;
    @Column(name = "state")
    private String state;

    private User useri;


}
