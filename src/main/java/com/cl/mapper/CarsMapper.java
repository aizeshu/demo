package com.cl.mapper;

import com.cl.pojo.Car;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CarsMapper {
    void add(Car car);

    List<Car> list(@Param("uid") String uid);

    int findIsAddCar(Car car);
}
