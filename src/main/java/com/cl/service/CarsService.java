package com.cl.service;

import com.cl.pojo.Car;

import java.util.List;

public interface CarsService {
    void add(Car car);

    List<Car> list(String uid);

    int findIsAddCar(Car car);
}
