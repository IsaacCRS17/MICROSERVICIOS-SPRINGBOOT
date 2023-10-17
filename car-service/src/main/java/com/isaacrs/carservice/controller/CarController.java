package com.isaacrs.carservice.controller;

import com.isaacrs.carservice.entity.Car;
import com.isaacrs.carservice.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CarController {
    @Autowired
    CarService carService;
    @GetMapping("/car")
    public ResponseEntity<List<Car>> getAll(){
        List<Car> cars = carService.getAll();
        if (cars.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/car/{id}")
    public ResponseEntity<Car> getById(@PathVariable("id") int id){
        Car car = carService.getUserById(id);
        if (car==null)
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(car);
    }

    @PostMapping("/car")
    public ResponseEntity<Car> save(@RequestBody Car car){
        Car carNew = carService.save(car);
        if (carNew==null)
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(carNew);
    }

    @GetMapping("/car/byuser/{userId}")
    public ResponseEntity<List<Car>> getByUserId(@PathVariable("userId") int userId){
        List<Car> cars = carService.byUserId(userId);
        return ResponseEntity.ok(cars);
    }
}
