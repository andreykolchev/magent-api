package com.magent.service;

import com.magent.config.ServiceConfig;
import com.magent.domain.TemporaryUser;
import com.magent.domain.User;
import com.magent.domain.UserPersonal;
import com.magent.domain.dto.ChangePasswordDto;
import com.magent.service.interfaces.GeneralService;
import com.magent.service.interfaces.TimeIntervalService;
import com.magent.service.interfaces.UserService;
import com.magent.utils.SecurityUtils;
import com.magent.reportmodule.utils.dateutils.DateUtils;
import com.magent.authmodule.utils.validators.UserValidatorImpl;
import javassist.NotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.jdbc.Sql;

import javax.xml.bind.ValidationException;
import java.util.Date;
import java.util.List;

import static com.magent.domain.enums.TimeIntervalConstants.BLOCK_INTERVAL;
import static com.magent.domain.enums.TimeIntervalConstants.TMP_UNREGISTERED_USER_INTERVAL;

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

    @Autowired
    private DateUtils dateUtils;

    @Autowired
    private TimeIntervalService timeIntervalService;

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
    public void testChangePassword() throws NotFoundException, ValidationException {
        //pre condition
        String pass = "user1";
        User testUser = (User) userGenService.getById(1L);
        String newUserPassword = "testPassword";
        ChangePasswordDto changePasswordDto = new ChangePasswordDto(testUser.getLogin(), SecurityUtils.hashPassword(pass), newUserPassword);
        //test begin
        Assert.assertTrue(userService.changePassword(testUser.getId(), changePasswordDto));
        String hashedPass = SecurityUtils.hashPassword(newUserPassword);
        User updated = (User) userGenService.getById(testUser.getId());
        String newPassFromBd = updated.getUserPersonal().getPassword();
        Assert.assertEquals("check is changed", SecurityUtils.hashPassword(hashedPass), newPassFromBd);

    }

    @Test
    @Sql("classpath:data.sql")
    public void testIsPasswordCorrect() throws UserValidatorImpl.UserIsBlockedException {
        String login = "user1";
        String pass = "user1";
        pass = SecurityUtils.hashPassword(pass);
        Assert.assertTrue(userService.isPasswordCorrect(login, pass));
    }

    @Test
    @Sql("classpath:data.sql")
    public void getBlockedUsersTest() {
        String timeInt = timeIntervalService.getByName(BLOCK_INTERVAL.toString()).getTimeInterval();
        String date = dateUtils.formatToSqlDateTimeInterval(new Date());
        List<UserPersonal> userPersonalList = userService.getBlockedUsers(date,
                dateUtils.converToTimeStamp(timeInt, BLOCK_INTERVAL));
        Assert.assertEquals(1, userPersonalList.size());
    }

    @Test
    @Sql("classpath:data.sql")
    public void getUsersWithExpiredTermTest() {
        String timeInt = timeIntervalService.getByName(TMP_UNREGISTERED_USER_INTERVAL.toString()).getTimeInterval();
        String date = dateUtils.formatToSqlDateTimeInterval(new Date());
        List<TemporaryUser> temporaryUserList = userService.getUsersWithExpiredTerm(date, dateUtils.converToTimeStamp(timeInt, TMP_UNREGISTERED_USER_INTERVAL));
        Assert.assertEquals(1, temporaryUserList.size());
    }
} 
