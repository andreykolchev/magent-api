package com.magent.service.interfaces;

import com.magent.domain.User;
import com.magent.domain.dto.ChangePasswordDto;
import javassist.NotFoundException;

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

}
