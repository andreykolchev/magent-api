package com.ds.controller.interfaces;

import com.ds.domain.User;
import com.ds.service.interfaces.UserService;
import com.ds.utils.SecurityUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by artomov.ihor on 17.06.2016.
 */
public interface GeneralController {
    final Logger LOGGER = Logger.getLogger(GeneralController.class);

    default User getActiveUser(UserService userService) {
        SecurityUtils.checkPrincipalIsNull(this.getClass());
        return userService.findUserByLogin((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    default ResponseEntity getDefaultResponce(Object o, HttpStatus success, HttpStatus failure) {
        HttpStatus status = (o == null) ? failure : success;
        return new ResponseEntity<>(o, status);
    }

    default ResponseEntity getDefaultResponceStatusOnly(Object o, HttpStatus success, HttpStatus failure){
        HttpStatus status = (o == null) ? failure : success;
        return new ResponseEntity<>(status);
    }
}
