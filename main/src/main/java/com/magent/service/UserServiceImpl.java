package com.magent.service;

import com.magent.domain.Account;
import com.magent.domain.TemporaryUser;
import com.magent.domain.User;
import com.magent.domain.UserPersonal;
import com.magent.domain.dto.ChangePasswordDto;
import com.magent.domain.enums.UserRoles;
import com.magent.repository.*;
import com.magent.service.interfaces.SmsService;
import com.magent.service.interfaces.UserService;
import com.magent.utils.SecurityUtils;
import com.magent.utils.validators.interfaces.GeneralValidator;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.ValidationException;
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

    @Autowired
    private GeneralValidator generalValidator;

    @Autowired
    private SmsService smsService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TemporaryUserRepository temporaryUserRepository;

    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private UserPersonalRepository userPersonalRepository;

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
                default:
                    throw new NotFoundException(" filter parameters incorrect ");
            }
        }
        return userRepository.findUsersByFilter(id, login, u_role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean changePassword(Long id, ChangePasswordDto chPassDto) {
        UserPersonal userPersonal = userPersonalRepository.findByIdAndPassword(id, SecurityUtils.hashPassword(chPassDto.getOldPassword()));
        if (userPersonal != null) {
            if (Objects.nonNull(chPassDto.getNewPassword())) {
                userPersonal.setPassword(SecurityUtils.hashPassword(chPassDto.getNewPassword()));
                userPersonalRepository.saveAndFlush(userPersonal);
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
    public boolean isPasswordCorrect(String login, String pass) {
        String passFromLoginForm = SecurityUtils.hashPassword(pass);
        return userRepository.findByLogin(login).getUserPersonal().getPassword().equals(passFromLoginForm);
    }

    //password comes already hashed from frontend
    @Override
    @Transactional(rollbackFor = Exception.class)
    public TemporaryUser isNewUserSaved(TemporaryUser temporaryUser) throws ValidationException {
        if (!generalValidator.isEmailValid(temporaryUser.getEmail())) throw new ValidationException("email not valid");
        if (!generalValidator.isNameCorrect(temporaryUser.getFirstName()))
            throw new ValidationException("Name not valid. Name must start with big letter and mustn't contains numbers min length 2 characters");
        if (!generalValidator.isNameCorrect(temporaryUser.getLastName()))
            throw new ValidationException("Last name not valid.Last name must start with big letter and mustn't contains numbers and min length 2 characters");
        if (!generalValidator.isPhoneValid(temporaryUser.getUsername()))
            throw new ValidationException("login not valid. Login must be phone number");

        return smsService.sendConfirmationAndSaveUser(temporaryUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User confirmRegistration(String login, String otp) throws NotFoundException {
        TemporaryUser tmpUser=temporaryUserRepository.getByLoginAndOtp(login, otp);
        if (Objects.isNull(tmpUser))throw new NotFoundException("current user not found");

        User user=new User(tmpUser);
        user=userRepository.save(user);
        deviceRepository.save(user.getDevices());
        accountRepository.save(new Account(user));
        userPersonalRepository.save(new UserPersonal(user.getId(),tmpUser.getHashedPwd()));

        //delete from temp users
        TemporaryUser temporaryUser=temporaryUserRepository.getByLogin(login);
        if (Objects.nonNull(temporaryUser))temporaryUserRepository.delete(temporaryUser);

        return user;
    }

    public String getAccountBalanceByUserLogin(String login){
        return accountRepository.getAccountByUserLogin(login).getAccountBalance().toString();
    }
}
