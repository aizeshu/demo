package com.cl.mapper;

import com.cl.pojo.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GoodsMapper {

    Goods findGoodsById(@Param("gid") String gid);

    List<Goods> findGoodsByKeyWords(@Param("keywords") String keywords);

    void insertGoods(Goods goods);

    Goods getGoodsDetailsByHash(@Param("hash") String hash);

    void updateOwn(@Param("data_hash") String data_hash, @Param("buy_id") String buy_id);

    List<Goods> findAllGoods();

    List<Goods> findGoodsByCate(@Param("cate_id") String cate_id);

    List<Goods> getGoodsDetailsByUid(@Param("uid") String uid);

    void updateClickNum(@Param("data_hash") String pic_hash);

    List<Goods> getRecommendGoods();
}
