package com.magent.config;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created on 08.06.2016.
 */
public class Test {
    public static void main(String... args) throws IOException, ParseException {
//        String pass="user1";
//        String firstHash= SecurityUtils.hashPassword(pass);
//        String secondHash=SecurityUtils.hashPassword(firstHash);
//        System.out.println(secondHash);
//        System.out.println(firstHash);
//        byte[] imageBody = Files.readAllBytes(Paths.get(URI.create(String.valueOf(Thread.currentThread().getContextClassLoader().getResource("testimages/mAgent.png")))));
//        ObjectMapper mapper=new ObjectMapper();
//        byte[]bytes=mapper.writeValueAsBytes(EntityGenerator.getNewTestUser());
//        System.out.println(new String(bytes));
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
       /* String test="merdoc";
        System.out.println(SecurityUtils.hashPassword("merdoc"));
        System.out.println(SecurityUtils.hashPassword(test));
        System.out.println(SecurityUtils.hashPassword(SecurityUtils.hashPassword(test)));*/

        DateFormat dbFormat = new SimpleDateFormat("HH:mm");
        Date first=dbFormat.parse("01:00");
        Date second=dbFormat.parse("00:01");


        long more=first.getTime()+second.getTime();
        System.out.println(more);
        System.out.println(first.getTime());
        System.out.println(first.getTime()>more);
//        System.out.println(dbFormat.format(dbFormat.parse("00:11")));

//        System.out.println(TimeIntervalConstants.BLOCK_INTERVAL.toString());
    }

    private static boolean isNameCorrect(String name) {
        return Pattern.matches("^[A-Z]{1}[a-z]{1,255}$", name);
    }

    private static boolean isPasswordValid(String pwd){
         return Pattern.matches("((?=.*[a-z])(?=.*[A-Z]|.*\\d|.*[@#$%]).{6,20})",pwd);
    }

}
