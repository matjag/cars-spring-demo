package com.itsilesia.car;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private Integer power;

}
