package com.cl.service;

import com.cl.pojo.Goods;

import java.util.List;

public interface GoodsService {

    Goods details(String gid);

    List<Goods> keywords(String keywords);

    void create(Goods goods);

    Goods getGoodsDetailsByHash(String hash);

    void updateOwn(String data_hash, String buy_id);

    List<Goods> selectAllGoods();

    List<Goods> selectCate(String cate_id);

    List<Goods> getGoodsDetailsByUid(String uid);

    void updateClickNum(String data_hash);

    List<Goods> getRecommendGoods();
}
