package com.isaacrs.bikeservice.controller;

import com.isaacrs.bikeservice.service.BikeService;
import com.isaacrs.bikeservice.entity.Bike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BikeController {
    @Autowired
    BikeService bikeService;
    @GetMapping("/bike")
    public ResponseEntity<List<Bike>> getAll(){
        List<Bike> bikes = bikeService.getAll();
        if (bikes.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(bikes);
    }

    @GetMapping("/bike/{id}")
    public ResponseEntity<Bike> getById(@PathVariable("id") int id){
        Bike bike = bikeService.getUserById(id);
        if (bike==null)
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(bike);
    }

    @PostMapping("/bike")
    public ResponseEntity<Bike> save(@RequestBody Bike bike){
        Bike bikeNew = bikeService.save(bike);
        if (bikeNew==null)
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(bikeNew);
    }

    @GetMapping("/bike/byuser/{userId}")
    public ResponseEntity<List<Bike>> getByUserId(@PathVariable("userId") int userId){
        List<Bike> bikes = bikeService.byUserId(userId);
        return ResponseEntity.ok(bikes);
    }
}
