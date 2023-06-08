package com.cl.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;


@Data
public class Goods {

    private String good_name;
    private int price;
    private int nums;
    private String create_id;//创建人id
    private String url;
    private int p_cate_id;
    private String file_name;
    private String suffix;

    //区块链相关 不返回给前端
    @JsonIgnore
    private String pic_hash;
    @JsonIgnore
    private String data_hash;
    //区块链上的gid
    private String gid;


    //人气推荐 - 点击次数
    private int clickNum;

    //本地数据库中的商品id
    @JsonIgnore
    private int sys_gid;

    private int types;
    @JsonIgnore
    private String own_id;
}
