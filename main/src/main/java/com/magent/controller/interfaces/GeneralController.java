package com.magent.controller.interfaces;

import com.magent.domain.User;
import com.magent.servicemodule.service.interfaces.UserService;
import com.magent.utils.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created  on 17.06.2016.
 */
public interface GeneralController {

    default User getActiveUser(UserService userService) {
        SecurityUtils.checkPrincipalIsNull(this.getClass());
        return userService.findByLogin((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
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
