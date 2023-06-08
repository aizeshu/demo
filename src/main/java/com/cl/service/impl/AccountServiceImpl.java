package com.cl.service.impl;

import com.cl.mapper.AccountMapper;
import com.cl.pojo.Account;
import com.cl.redis.RedisService;
import com.cl.service.AccountService;
import com.cl.utils.DigestUtils;
import com.cl.utils.UUIDUtils;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.CryptoType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private RedisService redisService;


    @Override
    public Account register(Account account) throws Exception {
        String phone = account.getPhone();
        String password = account.getPassword();
        Account selectAccount = accountMapper.getAccountByPhone(phone);
        if (null != selectAccount) {
            throw new Exception("手机号已经注册");
        }
        if (null == phone || null == password) {
            throw new Exception("手机号或密码不能为空");
        }
        //生成fisco bcos账户
        CryptoSuite cryptoSuite = new CryptoSuite(CryptoType.ECDSA_TYPE);
        CryptoKeyPair cryptoKeyPair = cryptoSuite.createKeyPair();
        String address = cryptoKeyPair.getAddress();
        String pubKey = cryptoKeyPair.getHexPublicKey();
        String priKey = cryptoKeyPair.getHexPrivateKey();
        account.setAddress(address);
        account.setPrivate_key(priKey);
        account.setPublic_key(pubKey);
        String uid = UUIDUtils.uuid();
        account.setUid(uid);
        String iv = UUIDUtils.uuid();
        account.setIv(iv);
        password = DigestUtils.getMd5Password(password, iv);
        account.setPassword(password);
        accountMapper.register(account);
        return account;
    }

    @Override
    public String login(Account account) throws Exception {
        //获取手机号
        String phone = account.getPhone();
        Account accountByPhone = accountMapper.getAccountByPhone(phone);

        if (null == accountByPhone) {
            throw new Exception("手机未注册");
        }

        String iv = accountByPhone.getIv();
        String truePassword = accountByPhone.getPassword();
        String inputPassword = account.getPassword();
        String md5Password = DigestUtils.getMd5Password(inputPassword, iv);
        if (!truePassword.equals(md5Password)) {
            throw new Exception("密码错误");
        }
        //生成token
        String token = UUIDUtils.uuid();
        //todo - token放入缓存
        redisService.set(token, accountByPhone);
        redisService.expire(token, 7200);
        return token;
    }
}
