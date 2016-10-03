package com.magent.servicemodule.service;


import com.magent.authmodule.utils.SecurityUtils;
import com.magent.authmodule.utils.validators.UserValidatorImpl;
import com.magent.domain.TemporaryUser;
import com.magent.domain.User;
import com.magent.domain.UserPersonal;
import com.magent.domain.dto.ChangePasswordDto;
import com.magent.repository.UserPersonalRepository;
import com.magent.servicemodule.config.ServiceModuleServiceConfig;
import com.magent.servicemodule.service.interfaces.GeneralService;
import com.magent.servicemodule.service.interfaces.SmsService;
import com.magent.servicemodule.service.interfaces.TimeIntervalService;
import com.magent.servicemodule.service.interfaces.UserService;
import com.magent.servicemodule.utils.EntityGenerator;
import com.magent.servicemodule.utils.dateutils.ServiceDateUtils;
import javassist.NotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.jdbc.Sql;

import javax.xml.bind.ValidationException;
import java.text.ParseException;
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
public class UserServiceImplTest extends ServiceModuleServiceConfig {
    @Autowired
    @Qualifier("userServiceImpl")
    UserService userService;

    @Autowired
    @Qualifier("userGeneralService")
    GeneralService userGenService;

    @Autowired
    private ServiceDateUtils dateUtils;

    @Autowired
    private TimeIntervalService timeIntervalService;

    @Autowired
    @Qualifier("smsDemoServiceImpl")
    private SmsService smsService;
    @Autowired
    private UserPersonalRepository userPersonalRepository;

    private String login = "user1";
    private String pass = "user1";

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

    @Test
    @Sql("classpath:data.sql")
    public void testGetUsersByFilterGetById() throws Exception {
        String filterPattern = "id:1";
        List<User> userList = userService.getUsersByFilter(filterPattern);
        Assert.assertNotNull(userList);
        Assert.assertTrue("check for size", userList.size() > 0);
        userList.forEach(user -> {
            Long userIdExpected = 1L;
            Assert.assertEquals(userIdExpected, user.getId());
        });

    }

    @Test
    @Sql("classpath:data.sql")
    public void testGetUsersByFilterGetByRole() throws Exception {
        String filterPattern = "role:ADMIN";
        List<User> userList = userService.getUsersByFilter(filterPattern);
        Assert.assertNotNull(userList);
        Assert.assertTrue("check for size", userList.size() > 0);
        for (User user : userList) {
            Assert.assertEquals("ADMIN", user.getRole().name());
        }

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

    /**
     * Method : changePassword(String login, String password, String otp)
     * current void work only if sms.send.real properties is false
     * logic:
     * - first of all user must get otp number from smsService - method sendForgotPassword(String toPhone)
     * - after that user can use method UserPersonal changePassword(String login, String password, String otp) from UserService
     */
    @Test
    @Sql("classpath:data.sql")
    public void ChangePasswordUserPersonalTest() throws ValidationException, ParseException, UserValidatorImpl.UserIsBlockedException {

        String otp = smsService.sendForgotPassword(login);
        String newPassword = "testPwd";
        String hashedNewPwd = SecurityUtils.hashPassword(newPassword);
        UserPersonal userPersonal = userService.changePassword(login, newPassword, SecurityUtils.hashPassword(otp));

        Assert.assertEquals(SecurityUtils.hashPassword(hashedNewPwd), userPersonal.getPassword());
        Assert.assertEquals(SecurityUtils.hashPassword(hashedNewPwd), userPersonalRepository.getByLogin(login).getPassword());

    }

    @Test
    @Sql("classpath:data.sql")
    public void testIsPasswordCorrect() throws UserValidatorImpl.UserIsBlockedException {
        pass = SecurityUtils.hashPassword(pass);
        Assert.assertTrue(userService.isPasswordCorrect(login, pass));
    }

    @Test
    @Sql("classpath:data.sql")
    public void checkRegistrationOnServiceLevelTest() throws ValidationException, ParseException, NotFoundException {
        //test signUp
        TemporaryUser temporaryUser = EntityGenerator.generateUserForRegistration();
        String otp = userService.isNewUserSaved(temporaryUser);
        //confirm registration
        userService.confirmRegistration(temporaryUser.getUsername(),SecurityUtils.hashPassword(otp));
        Assert.assertNotNull(userService.findByLogin(temporaryUser.getUsername()));
    }

    @Test
    @Sql("classpath:data.sql")
    public void getBlockedUsersTest() {
        String timeInt = timeIntervalService.getByName(BLOCK_INTERVAL.toString()).getTimeInterval();
        String date = dateUtils.formatToSqlDateTimeInterval(new Date());
        List<UserPersonal> userPersonalList = userService.getBlockedUsers(date,
                dateUtils.convertToTimeStamp(timeInt, BLOCK_INTERVAL));
        Assert.assertEquals(1, userPersonalList.size());
    }

    @Test
    @Sql("classpath:data.sql")
    public void getUsersWithExpiredTermTest() {
        String timeInt = timeIntervalService.getByName(TMP_UNREGISTERED_USER_INTERVAL.toString()).getTimeInterval();
        String date = dateUtils.formatToSqlDateTimeInterval(new Date());
        List<TemporaryUser> temporaryUserList = userService.getUsersWithExpiredTerm(date, dateUtils.convertToTimeStamp(timeInt, TMP_UNREGISTERED_USER_INTERVAL));
        Assert.assertEquals(1, temporaryUserList.size());
    }
} 
