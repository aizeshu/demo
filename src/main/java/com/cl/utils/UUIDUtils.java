package com.cl.utils;

import java.util.UUID;

public class UUIDUtils {

    public static String uuid() {
        return String.valueOf(UUID.randomUUID()).replace("-", "");
    }

    public static void main(String[] args) {
        System.out.println(uuid());
    }

}
