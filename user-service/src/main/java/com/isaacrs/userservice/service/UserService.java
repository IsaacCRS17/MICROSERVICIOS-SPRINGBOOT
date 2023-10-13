package com.isaacrs.userservice.service;

import com.isaacrs.userservice.entity.User;
import com.isaacrs.userservice.feignclients.BikeFeignClient;
import com.isaacrs.userservice.feignclients.CarFeignClient;
import com.isaacrs.userservice.model.Bike;
import com.isaacrs.userservice.model.Car;
import com.isaacrs.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servicio que maneja la lógica de negocio relacionada con los usuarios.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CarFeignClient carFeignClient;

    @Autowired
    private BikeFeignClient bikeFeignClient;

    /**
     * Obtiene todos los usuarios.
     *
     * @return Lista de usuarios.
     */
    public List<User> getAll() {
        return userRepository.findAll();
    }

    /**
     * Obtiene un usuario por su ID.
     *
     * @param id ID del usuario.
     * @return Usuario correspondiente al ID dado.
     */
    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * Guarda un nuevo usuario en la base de datos.
     *
     * @param user Usuario a guardar.
     * @return Usuario guardado.
     */
    public User save(User user) {
        User userNew = userRepository.save(user);
        return userNew;
    }

    /**
     * Obtiene todos los coches de un usuario por su ID utilizando un servicio externo.
     *
     * @param userId ID del usuario.
     * @return Lista de coches del usuario.
     */
    public List<Car> getCars(int userId) {
        List<Car> cars = restTemplate.getForObject("http://localhost:8002/car/byuser/" + userId, List.class);
        return cars;
    }

    /**
     * Obtiene todas las bicicletas de un usuario por su ID utilizando un servicio externo.
     *
     * @param userId ID del usuario.
     * @return Lista de bicicletas del usuario.
     */
    public List<Bike> getBikes(int userId) {
        List<Bike> bikes = restTemplate.getForObject("http://localhost:8003/bike/byuser/" + userId, List.class);
        return bikes;
    }

    /**
     * Guarda un nuevo coche para un usuario utilizando un servicio externo.
     *
     * @param userId ID del usuario.
     * @param car    Coche a guardar.
     * @return Coche guardado.
     */
    public Car saveCar(int userId, Car car) {
        car.setUserId(userId);
        Car carNew = carFeignClient.save(car);
        return carNew;
    }

    /**
     * Guarda una nueva bicicleta para un usuario utilizando un servicio externo.
     *
     * @param userId ID del usuario.
     * @param bike   Bicicleta a guardar.
     * @return Bicicleta guardada.
     */
    public Bike saveBike(int userId, Bike bike) {
        bike.setUserId(userId);
        Bike bikeNew = bikeFeignClient.save(bike);
        return bikeNew;
    }

    public Map<String, Object> getUserAndVehicles(int userId) {
        Map<String, Object> result = new HashMap<>();
        User user = userRepository.findById(userId).orElse(null);
        if(user == null) {
            result.put("Mensaje", "no existe el usuario");
            return result;
        }
        result.put("User", user);
        List<Car> cars = carFeignClient.getCars(userId);
        if (cars.isEmpty())
            result.put("Cars", "ese user no tiene carros");
        else
            result.put("Cars", cars);
        List<Bike> bikes = bikeFeignClient.getBikes(userId);
        if (bikes.isEmpty())
            result.put("Bikes", "ese user no tiene motos");
        else
            result.put("Bikes", bikes);
        return result;
    }
}
