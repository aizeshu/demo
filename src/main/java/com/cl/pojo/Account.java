package com.cl.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Account implements Serializable {
    private String uid;
    private String private_key;
    private String public_key;
    private String address;
    private String password;
    private String iv;
    private String phone;
    private String nick_name;
}
