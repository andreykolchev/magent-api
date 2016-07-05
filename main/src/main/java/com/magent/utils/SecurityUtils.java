package com.magent.utils;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.util.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class SecurityUtils {

    private static final String SALT = "[B@7adf9f5f";

    @SuppressFBWarnings("DM_DEFAULT_ENCODING")
    public static String hashPassword(String password) {
        if (StringUtils.isEmpty(password)) {
            throw new IllegalArgumentException("password should not be empty");
        }

        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(SALT.getBytes());
            byte[] bytes = md.digest(password.getBytes());

            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            //Get complete hashed password in hex format
            return sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Gets Principal from {@code SecurityContextHolder}
     * and checks if Principal exists.
     * @param clazz
     * @return
     * @throws UnauthorizedUserException if {@code getPrincipal()} return null.
     */
    public static void checkPrincipalIsNull(Class clazz){
        if (Objects.isNull(SecurityContextHolder.getContext().getAuthentication().getPrincipal())){
            String message = "Username is unknown or null.";
            LoggerFactory.getLogger(clazz).error(message);
            throw new UnauthorizedUserException(message);
        }
    }


}
