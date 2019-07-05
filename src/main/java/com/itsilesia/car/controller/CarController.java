package com.itsilesia.car.controller;

import com.itsilesia.car.model.Car;
import com.itsilesia.car.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/car")
public class CarController {

    private final CarService carService;


    CarController(CarService service){this.carService = service;}

    @Secured("ROLE_CAR_LIST")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<Car> list() {
       return carService.findAll();
    }

    @Secured("ROLE_CAR_CREATE")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Car create(@Valid @RequestBody Car newCar) {
        return carService.save(newCar);
    }

    @Secured("ROLE_CAR_GET")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Car find(@PathVariable Long id) {
      return  carService.findOne(id);
    }

    @Secured("ROLE_CAR_UPDATE")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Car update(@RequestBody Car newCar, @PathVariable Long id) {
        return carService.update(newCar, id);
    }

    @Secured("ROLE_CAR_DELETE")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long id) {
        carService.delete(id);
    }
}

