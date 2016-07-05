package com.magent.controller;

import com.magent.utils.SecurityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by artomov.ihor on 16.06.2016.
 */
@RestController
@RequestMapping("/")
public class OtpController {

    @RequestMapping(value = "/hash/otp",method = RequestMethod.GET)
    public String hashOtp(@RequestParam("otp")String otp){
        return SecurityUtils.hashPassword(otp);
    }
}
