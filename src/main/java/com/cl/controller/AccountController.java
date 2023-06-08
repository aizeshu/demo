package com.cl.controller;

import com.cl.domain.ResponseResult;
import com.cl.pojo.Account;
import com.cl.service.AccountService;
import com.cl.solidity.Accounts;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.math.BigInteger;

/**
 * 账户相关
 */
@RequestMapping("account")
@RestController
@CrossOrigin
public class AccountController extends BaseController {

    @Autowired
    private AccountService accountService;
    private BcosSDK bcosSDK;
    private Client client;

    private Accounts accounts;

    @PostConstruct
    public void init() throws ContractException {
        @SuppressWarnings("resource")
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath:fisco-bscos-config.xml");
        bcosSDK = context.getBean(BcosSDK.class);
        client = bcosSDK.getClient(1);
        //部署合约
        CryptoKeyPair cryptoKeyPair = client.getCryptoSuite().getCryptoKeyPair();
        accounts = Accounts.deploy(client, cryptoKeyPair);
    }

    //登录后返回账户外部地址以及账户余额
    @RequestMapping("login")
    public ResponseResult login(@RequestBody Account account) throws Exception {
        String token = accountService.login(account);
        return ResponseResult.success(token);
    }


    /***
     * 参数为手机和密码 密码为明文接收 接收后MD5 在与对应iv进行MD5 存到password
     * @param account
     * @return
     */
    @RequestMapping("register")
    public ResponseResult register(@RequestBody Account account) throws Exception {
        Account registerAccount = accountService.register(account);
        TransactionReceipt transactionReceipt = accounts.addAccount(registerAccount.getUid(), new BigInteger(String.valueOf(10000)));
        Tuple1<BigInteger> addAccountTuple = accounts.getAddAccountOutput(transactionReceipt);
        BigInteger result = addAccountTuple.getValue1();
        return ResponseResult.success(null);
    }

    //查询当前账户余额
    @RequestMapping("balance")
    public ResponseResult balance(String token) throws Exception {
        Account accountByToken = getAccountByToken(token);
        String uid = accountByToken.getUid();
        //调用合约查询当前用户余额
        TransactionReceipt transactionReceipt = accounts.selectBalance(uid);
        Tuple1<BigInteger> balanceTuple = accounts.getSelectBalanceOutput(transactionReceipt);
        BigInteger balance = balanceTuple.getValue1();
        return ResponseResult.success(balance);
    }

}
