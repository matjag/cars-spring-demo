package com.itsilesia.car.dto;

import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
public class CarSaveDto {

    @NotNull
    private String brand;

    @NotNull
    @Min(1)
    private Integer power;
}
