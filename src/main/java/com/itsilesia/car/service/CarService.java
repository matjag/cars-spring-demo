package com.itsilesia.car.service;

import com.itsilesia.car.model.Car;
import com.itsilesia.car.dao.CarDao;
import com.itsilesia.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class CarService {

    @Autowired
    private CarDao carDao;

    public List<Car> findAll() {
        List<Car> cars = new ArrayList<>();
        carDao.findAll().iterator().forEachRemaining(car -> cars.add(car));
        return cars;
    }

    public Car findOne(long id) {
        return carDao.findById(id).orElseThrow(() -> new NotFoundException(id, Car.class));
    }

    public void delete(long id) {
        carDao.delete(carDao.findById(id)
                .orElseThrow(() -> new NotFoundException(id, Car.class))
        );
    }

    public Car save(Car newCar) {
        return carDao.save(newCar);
    }

    public Car update(Car newCar, Long id) {
        return carDao.findById(id)
                .map(car -> {
                    car.setBrand(newCar.getBrand() == null ? car.getBrand() : newCar.getBrand());
                    car.setPower(newCar.getPower() == null ? car.getPower() : newCar.getPower());
                    return carDao.save(car);
                })
                .orElseThrow(() -> new NotFoundException(id, Car.class));
    }
}
