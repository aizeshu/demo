package com.cl.controller;

import com.cl.pojo.Account;
import com.cl.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 基类
 */
@Controller
public class BaseController {

    @Autowired
    private RedisService redisService;


    public Account getAccountByToken(String token) throws Exception {
        Account account = (Account) redisService.get(token);
        if (null == account) {
            throw new Exception("用户未登录");
        }
        //根据token查询redis
        return account;
    }

    public String getUid(String token) throws Exception {
        Account accountByToken = getAccountByToken(token);
        return accountByToken.getUid();
    }

    public String getAddress(String token) throws Exception {
        Account accountByToken = getAccountByToken(token);
        return accountByToken.getAddress();
    }

    public String getPhone(String token) throws Exception {
        Account accountByToken = getAccountByToken(token);
        return accountByToken.getPhone();
    }

}
