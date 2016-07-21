package com.magent.service.interfaces;

import com.magent.domain.TemporaryUser;
import com.magent.domain.User;
import com.magent.domain.dto.ChangePasswordDto;
import javassist.NotFoundException;

import javax.xml.bind.ValidationException;
import java.util.List;

/**
 * Created by artomov.ihor on 11.05.2016.
 */
public interface UserService {
    List<User> getUsersByFilter(String filter) throws NotFoundException;
    boolean changePassword(Long id, ChangePasswordDto chPassDto);
    User findUserByLogin(String login);
    List<User> getUsersForBalanceReport();
    boolean isPasswordCorrect(String login,String pass);
    TemporaryUser isNewUserSaved(TemporaryUser temporaryUser) throws ValidationException;
    User confirmRegistration(String login,String otp) throws NotFoundException;
    String getAccountBalanceByUserLogin(String login);
}
