package com.cl.service.impl;

import com.cl.mapper.CarsMapper;
import com.cl.pojo.Car;
import com.cl.service.CarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarsServiceImpl implements CarsService {
    @Autowired
    private CarsMapper carsMapper;

    @Override
    public void add(Car car) {
        carsMapper.add(car);
    }

    @Override
    public List<Car> list(String uid) {
        return carsMapper.list(uid);
    }

    @Override
    public int findIsAddCar(Car car) {
        return carsMapper.findIsAddCar(car);
    }
}
