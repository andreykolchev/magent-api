package com.magent.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.magent.utils.EntityGenerator;

import java.io.IOException;

/**
 * Created on 08.06.2016.
 */
public class Test {
    public static void main(String...args) throws IOException {
//        String pass="user1";
//        String firstHash= SecurityUtils.hashPassword(pass);
//        String secondHash=SecurityUtils.hashPassword(firstHash);
//        System.out.println(secondHash);
//        System.out.println(firstHash);
//        byte[] imageBody = Files.readAllBytes(Paths.get(URI.create(String.valueOf(Thread.currentThread().getContextClassLoader().getResource("testimages/mAgent.png")))));
        ObjectMapper mapper=new ObjectMapper();
        byte[]bytes=mapper.writeValueAsBytes(EntityGenerator.getOnBoardPossitivePng());
        System.out.println(new String(bytes));
    }
}
