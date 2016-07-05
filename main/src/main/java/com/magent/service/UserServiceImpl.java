package com.magent.service;

import com.magent.domain.User;
import com.magent.domain.dto.ChangePasswordDto;
import com.magent.domain.enums.UserRoles;
import com.magent.repository.UserRepository;
import com.magent.service.interfaces.UserService;
import com.magent.utils.SecurityUtils;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Created by artomov.ihor on 11.05.2016.
 */
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public List<User> getUsersByFilter(String filter) throws NotFoundException {
        Long id = 0L;
        String login = "";
        Long u_role = null;
        String[] params = filter.split(Pattern.quote("|"));
        for (String param : params) {
            String name = param.split(Pattern.quote(":"))[0];
            String value = param.split(Pattern.quote(":"))[1];
            switch (name) {
                case "id":
                    id = Long.parseLong(value);
                    break;
                case "login":
                    login = value;
                    break;
                 case "role":
                    u_role = UserRoles.getByString(value).getRoleId();
                    break;
                 default:throw new NotFoundException(" filter parameters incorrect ");
            }
        }
        return userRepository.findUsersByFilter(id, login, u_role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean changePassword(Long id, ChangePasswordDto chPassDto) {
        User user = userRepository.findByIdAndPassword(id, SecurityUtils.hashPassword(chPassDto.getOldPassword()));
        if (user != null) {
            if (Objects.nonNull(chPassDto.getNewPassword())) {
                user.setPassword(SecurityUtils.hashPassword(chPassDto.getNewPassword()));
                userRepository.saveAndFlush(user);
                return true;
            }
        }
        return false;
    }

    @Override
    public User findUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public List<User> getUsersForBalanceReport() {
        return userRepository.getAllUsersWithAccount();
    }

    @Override
    public boolean isPasswordCorrect(String login,String pass) {
        String passFromLoginForm = SecurityUtils.hashPassword(pass);
        return userRepository.findByLogin(login).getPassword().equals(passFromLoginForm);
    }

}
