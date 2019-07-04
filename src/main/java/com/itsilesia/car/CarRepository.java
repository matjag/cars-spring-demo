package com.itsilesia.car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface CarRepository extends CrudRepository<Car, Long> {
}
