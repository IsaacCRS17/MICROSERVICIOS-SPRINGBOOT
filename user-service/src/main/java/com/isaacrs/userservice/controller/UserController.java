package com.isaacrs.userservice.controller;

import com.isaacrs.userservice.entity.User;
import com.isaacrs.userservice.model.Bike;
import com.isaacrs.userservice.model.Car;
import com.isaacrs.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    // Obtiene todos los usuarios
    @GetMapping("/user")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAll();
        if (users.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(users);
    }

    // Obtiene un usuario por su ID
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") int id) {
        User user = userService.getUserById(id);
        if (user == null)
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(user);
    }

    // Crea un nuevo usuario
    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User userNew = userService.save(user);
        if (userNew == null)
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(userNew);
    }

    // Obtiene todos los coches de un usuario por su ID
    @GetMapping("/user/cars/{userId}")
    public ResponseEntity<List<Car>> getCarsByUserId(@PathVariable("userId") int userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        List<Car> cars = userService.getCars(userId);
        return ResponseEntity.ok(cars);
    }

    // Obtiene todas las bicicletas de un usuario por su ID
    @GetMapping("/user/bikes/{userId}")
    public ResponseEntity<List<Bike>> getBikesByUserId(@PathVariable("userId") int userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        List<Bike> bikes = userService.getBikes(userId);
        return ResponseEntity.ok(bikes);
    }
    // Crea un nuevo car al usuario
    @PostMapping("/user/savecar/{userId}")
    public ResponseEntity<Car> saveCar(@PathVariable("userId") int userId, @RequestBody Car car){
        if(userService.getUserById(userId) == null) {
            return ResponseEntity.notFound().build();
        }
        Car carNew = userService.saveCar(userId, car);
        return ResponseEntity.ok(carNew);
    }
    // Crea una nueva bike al usuario
    @PostMapping("/user/savebike/{userId}")
    public ResponseEntity<Bike> saveBike(@PathVariable("userId") int userId, @RequestBody Bike bike){
        if(userService.getUserById(userId) == null) {
            return ResponseEntity.notFound().build();
        }
        Bike bikeNew = userService.saveBike(userId, bike);
        return ResponseEntity.ok(bikeNew);
    }
    // Obtiene todas las bicicletas y carros de un usuario por su ID
    @GetMapping("/user/getAll/{userId}")
    public ResponseEntity<Map<String, Object>> getAllVehicles(@PathVariable("userId") int userId){
        Map<String, Object> result = userService.getUserAndVehicles(userId);
        return ResponseEntity.ok(result);
    }
}
