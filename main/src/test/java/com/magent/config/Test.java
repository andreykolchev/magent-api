package com.magent.config;

import javax.mail.internet.InternetAddress;
import javax.xml.bind.ValidationException;

/**
 * Created on 08.06.2016.
 */
public class Test {
    public static void main(String... args) {
//        String pass="user1";
//        String firstHash= SecurityUtils.hashPassword(pass);
//        String secondHash=SecurityUtils.hashPassword(firstHash);
//        System.out.println(secondHash);
//        System.out.println(firstHash);
//        byte[] imageBody = Files.readAllBytes(Paths.get(URI.create(String.valueOf(Thread.currentThread().getContextClassLoader().getResource("testimages/mAgent.png")))));
        /*ObjectMapper mapper=new ObjectMapper();
        byte[]bytes=mapper.writeValueAsBytes(EntityGenerator.getOnBoardPositiveSVG());
        System.out.println(new String(bytes));*/
//        new org.apache.crimson.parser.XMLReaderImpl();
        /*List<String> phonesList = new ArrayList<>(Arrays.asList("+380978090838", "+380632356941", "+480111234567"));
        for (String s : phonesList) {
            boolean res= Pattern.matches("^\\+[1-9]{1}[0-9]{3,14}$",s);
            System.out.println(res);
        } ;*/
    System.out.println(isMailValid("mr.freezetime@rambler.ru"));


    }

    private static boolean isMailValid(String email) {
        try {
            InternetAddress address = new InternetAddress(email);
            address.validate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
