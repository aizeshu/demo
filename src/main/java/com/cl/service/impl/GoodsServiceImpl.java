package com.cl.service.impl;

import com.cl.mapper.GoodsMapper;
import com.cl.pojo.Goods;
import com.cl.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public Goods details(String gid) {
        return goodsMapper.findGoodsById(gid);
    }

    @Override
    public List<Goods> keywords(String keywords) {
        return goodsMapper.findGoodsByKeyWords(keywords);
    }

    @Override
    public void create(Goods goods) {
        goodsMapper.insertGoods(goods);
    }

    @Override
    public Goods getGoodsDetailsByHash(String hash) {
        return goodsMapper.getGoodsDetailsByHash(hash);
    }

    @Override
    public void updateOwn(String data_hash, String buy_id) {
        goodsMapper.updateOwn(data_hash, buy_id);
    }

    @Override
    public List<Goods> selectAllGoods() {
        return goodsMapper.findAllGoods();
    }

    @Override
    public List<Goods> selectCate(String cate_id) {
        return goodsMapper.findGoodsByCate(cate_id);
    }

    @Override
    public List<Goods> getGoodsDetailsByUid(String uid) {
        return goodsMapper.getGoodsDetailsByUid(uid);
    }

    @Override
    public void updateClickNum(String data_hash) {
        goodsMapper.updateClickNum(data_hash);
    }

    @Override
    public List<Goods> getRecommendGoods() {
        return goodsMapper.getRecommendGoods();
    }
}
