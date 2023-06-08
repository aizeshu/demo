package com.cl.service;

import com.cl.pojo.Account;

public interface AccountService {

    Account register(Account account) throws Exception;

    String login(Account account) throws Exception;
}
