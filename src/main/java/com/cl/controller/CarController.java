package com.cl.controller;

import com.cl.domain.ResponseResult;
import com.cl.ipfs.IpfsService;
import com.cl.pojo.Car;
import com.cl.pojo.Goods;
import com.cl.service.CarsService;
import com.cl.service.GoodsService;
import com.cl.utils.JsonUtils;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple2;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车控制器
 */

@RequestMapping("car")
@RestController
@CrossOrigin
public class CarController extends BaseController {

    @Autowired
    private CarsService carsService;

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private IpfsService ipfsService;


    /**
     * 加入购物车 当前用户和商品id
     *
     * @return
     */
    @RequestMapping("/add")
    public ResponseResult addCar(String gid, String token) throws Exception {
        //通过session获取uid
        String uid = getUid(token);
        Car car = new Car();
        car.setUid(uid);
        car.setGid(gid);
        int num = carsService.findIsAddCar(car);
        if(num>0){
            throw new Exception("请勿重复加购物车");
        }
        carsService.add(car);
        return ResponseResult.success(null);
    }

    @RequestMapping("/list")
    public ResponseResult carList(String token) throws Exception {
        //通过session获取uid
        String uid = getUid(token);
        //todo 检查对应pid的藏品是否存在
        List<Car> carList = carsService.list(uid);
        List<Goods> result = new ArrayList<>();

        for (Car c : carList) {
            String gid = c.getGid();
            Goods good = goodsService.details(gid);
            byte[] bytes = ipfsService.downFromIpfs(good.getData_hash());
            result.add(JsonUtils.parseJson(bytes, Goods.class));
        }

        return ResponseResult.success(result);
    }
}
