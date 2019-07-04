package com.itsilesia.car;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Car {

    @Id
    @GeneratedValue
    private Long id;

    private String brand;
    private Integer power;

    Car() {
    }

    Car(String brand, Integer power) {
        this.brand = brand;
        this.power = power;
    }
}
