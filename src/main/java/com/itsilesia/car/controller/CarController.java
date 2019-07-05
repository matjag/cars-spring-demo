package com.itsilesia.car.controller;

import com.itsilesia.car.dto.CarSaveDto;
import com.itsilesia.car.dto.CarUpdateDto;
import com.itsilesia.car.model.Car;
import com.itsilesia.car.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/car")
public class CarController {

    @Autowired
    private CarService carService;


    @Secured("ROLE_CAR_LIST")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Car> list() {
        return carService.findAll();
    }

    @Secured("ROLE_CAR_CREATE")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Car create(@Valid @RequestBody CarSaveDto carSaveDto) {
        return carService.save(carSaveDto);
    }

    @Secured("ROLE_CAR_GET")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Car find(@PathVariable Long id) {
        return carService.findOne(id);
    }

    @Secured("ROLE_CAR_UPDATE")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Car update(@Valid @RequestBody CarUpdateDto carUpdateDto, @PathVariable Long id) {
        return carService.update(carUpdateDto, id);
    }

    @Secured("ROLE_CAR_DELETE")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        carService.delete(id);
    }
}

