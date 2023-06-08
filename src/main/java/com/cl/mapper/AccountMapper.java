package com.cl.mapper;


import com.cl.pojo.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AccountMapper {

    void register(Account account);

    Account getAccountByPhone(@Param("phone") String phone);
}
