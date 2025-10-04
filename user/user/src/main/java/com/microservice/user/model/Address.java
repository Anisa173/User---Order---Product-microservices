package com.microservice.user.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
   @OneToOne(mappedBy = "address",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
   @JsonBackReference
    private User useri;

}
