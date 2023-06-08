package com.cl.utils;

import java.nio.charset.StandardCharsets;

public class DigestUtils {

    public static String getMd5Password(String password, String iv) {
        password = org.springframework.util.DigestUtils.md5DigestAsHex((password + iv).getBytes(StandardCharsets.UTF_8));
        return password;
    }
}
