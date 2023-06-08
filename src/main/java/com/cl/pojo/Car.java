package com.cl.pojo;

import lombok.Data;

import java.io.Serializable;

/***
 * 购物车实体类
 *
 */
@Data
public class Car implements Serializable {
    private String cid;
    private String gid;
    private String uid;
}
