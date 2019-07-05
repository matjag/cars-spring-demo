package com.itsilesia.car.service;

import com.itsilesia.car.dto.CarSaveDto;
import com.itsilesia.car.dto.CarUpdateDto;
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

    public Car save(CarSaveDto carSaveDto) {
        Car newCar = new Car(carSaveDto.getBrand(), carSaveDto.getPower());
        return carDao.save(newCar);
    }

    public Car update(CarUpdateDto carUpdateDto, Long id) {
        return carDao.findById(id)
                .map(car -> {
                    car.setBrand(carUpdateDto.getBrand() == null ? car.getBrand() : carUpdateDto.getBrand());
                    car.setPower(carUpdateDto.getPower() == null ? car.getPower() : carUpdateDto.getPower());
                    return carDao.save(car);
                })
                .orElseThrow(() -> new NotFoundException(id, Car.class));
    }
}
