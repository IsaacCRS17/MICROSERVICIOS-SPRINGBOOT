package com.isaacrs.userservice.controller;

import com.isaacrs.userservice.entity.User;
import com.isaacrs.userservice.model.Bike;
import com.isaacrs.userservice.model.Car;
import com.isaacrs.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    // Obtiene todos los usuarios
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAll();
        if (users.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(users);
    }

    // Obtiene un usuario por su ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") int id) {
        User user = userService.getUserById(id);
        if (user == null)
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(user);
    }

    // Crea un nuevo usuario
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User userNew = userService.save(user);
        if (userNew == null)
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(userNew);
    }

    // Obtiene todos los coches de un usuario por su ID
    @GetMapping("/cars/{userId}")
    public ResponseEntity<List<Car>> getCarsByUserId(@PathVariable("userId") int userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        List<Car> cars = userService.getCars(userId);
        return ResponseEntity.ok(cars);
    }

    // Obtiene todas las bicicletas de un usuario por su ID
    @GetMapping("/bikes/{userId}")
    public ResponseEntity<List<Bike>> getBikesByUserId(@PathVariable("userId") int userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        List<Bike> bikes = userService.getBikes(userId);
        return ResponseEntity.ok(bikes);
    }
}
