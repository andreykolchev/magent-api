package com.magent.config;

import com.magent.utils.SecurityUtils;


import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Created on 08.06.2016.
 */
public class Test {
    public static void main(String... args) throws IOException {
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
//    System.out.println(isMailValid("mr.freezetime@rambler.ru"));
        /*System.out.println(isNameCorrect("Igor"));
        System.out.println(isNameCorrect("Igor "));
        System.out.println(isNameCorrect("Igor Muhamed"));
        System.out.println(isNameCorrect("Игорь"));*/
       /* System.out.println(isPasswordValid("Merdoc"));//possitive
        System.out.println(isPasswordValid("neroku1"));//possitive
        System.out.println(isPasswordValid("nerok1"));//possitive
        System.out.println(isPasswordValid("openshiFt"));//possitive
        System.out.println(isPasswordValid("openshi%"));//possitive
        System.out.println(isPasswordValid("lambda"));//negative
        System.out.println(isPasswordValid("Lambd"));//negative*/
      /*  System.out.println(isNameCorrect("Igor"));//positive
        System.out.println(isNameCorrect("Taras"));
        System.out.println(isNameCorrect("tar"));//negative*/
        String test="merdoc";
        System.out.println(SecurityUtils.hashPassword("merdoc"));
        System.out.println(SecurityUtils.hashPassword(test));
        System.out.println(SecurityUtils.hashPassword(SecurityUtils.hashPassword(test)));
    }

    private static boolean isNameCorrect(String name) {
        return Pattern.matches("^[A-Z]{1}[a-z]{1,255}$", name);
    }

    private static boolean isPasswordValid(String pwd){
         return Pattern.matches("((?=.*[a-z])(?=.*[A-Z]|.*\\d|.*[@#$%]).{6,20})",pwd);
    }

}
