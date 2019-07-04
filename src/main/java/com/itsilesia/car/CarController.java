package com.itsilesia.car;

import com.itsilesia.car.exceptions.CarNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarRepository repository;

    CarController(CarRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<Car> list() {
        return StreamSupport
                .stream(repository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Car create(@RequestBody Car newCar) {
        return repository.save(newCar);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Car find(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new CarNotFoundException(id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Car update(@RequestBody Car newCar, @PathVariable Long id) {
        return repository.findById(id)
                .map(car -> {
                    car.setBrand(newCar.getBrand() == null ? car.getBrand() : newCar.getBrand());
                    car.setPower(newCar.getPower() == null ? car.getPower() : newCar.getPower());
                    return repository.save(car);
                })
                .orElseThrow(() -> new CarNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long id) {
        repository.delete(repository.findById(id)
                .orElseThrow(() -> new CarNotFoundException(id))
        );

    }
}
