package com.magent.service;

import com.magent.config.ServiceConfig;
import com.magent.domain.User;
import com.magent.domain.dto.ChangePasswordDto;
import com.magent.service.interfaces.GeneralService;
import com.magent.service.interfaces.UserService;
import com.magent.utils.SecurityUtils;
import javassist.NotFoundException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.jdbc.Sql;

/**
 * UserServiceImpl Tester.
 *
 * @author artomov.ihor
 * @version 1.0
 * @since 11.05.2016
 */
public class UserServiceImplTest extends ServiceConfig {
    @Autowired
    UserService userService;

    @Autowired
    @Qualifier("userGeneralService")
    GeneralService userGenService;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getUsersByFilter(String filter)
     */
    @Test
    @Sql("classpath:data.sql")
    public void testGetUsersByFilter() throws Exception {
        String filterPattern = "login:user1";
        Assert.assertNotNull(userService.getUsersByFilter(filterPattern));
        Assert.assertTrue(userService.getUsersByFilter(filterPattern).size() > 0);
        Assert.assertEquals("checking login", userService.getUsersByFilter(filterPattern).get(0).getLogin(), "user1");
    }

    /**
     * Method :changePassword(Long id, ChangePasswordDto chPassDto);
     */
    @Test
    @Sql("classpath:data.sql")
    public void testChangePassword() throws NotFoundException {
        //pre condition
        String pass = "user1";
        User testUser = (User) userGenService.getById(1L);
        String newUserPassword = "testPassword";
        ChangePasswordDto changePasswordDto = new ChangePasswordDto(testUser.getLogin(), SecurityUtils.hashPassword(pass), newUserPassword);
        //test begin
        Assert.assertTrue(userService.changePassword(testUser.getId(), changePasswordDto));
        String hashedPass = SecurityUtils.hashPassword(newUserPassword);
        User updated = (User) userGenService.getById(testUser.getId());
        String newPassFromBd = updated.getPassword();
        Assert.assertEquals("check is changed", hashedPass, newPassFromBd);

    }

    @Test
    @Sql("classpath:data.sql")
    public void testIsPasswordCorrect() {
        String login = "user1";
        String pass = "user1";
        pass=SecurityUtils.hashPassword(pass);
        Assert.assertTrue(userService.isPasswordCorrect(login,pass));
    }
} 
