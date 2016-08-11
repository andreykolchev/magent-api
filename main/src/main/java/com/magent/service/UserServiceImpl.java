package com.magent.service;

import com.magent.domain.*;
import com.magent.domain.dto.ChangePasswordDto;
import com.magent.domain.enums.UserRoles;
import com.magent.repository.*;
import com.magent.service.interfaces.SmsService;
import com.magent.service.interfaces.TimeIntervalService;
import com.magent.service.interfaces.UserService;
import com.magent.utils.SecurityUtils;
import com.magent.utils.dateutils.DateUtils;
import com.magent.utils.validators.UserValidatorImpl;
import com.magent.utils.validators.interfaces.GeneralValidator;
import com.magent.utils.validators.interfaces.UserValidator;
import javassist.NotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.bind.ValidationException;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import static com.magent.domain.enums.TimeIntervalConstants.OTP_INTERVAL_NAME;
import static com.magent.domain.enums.TimeIntervalConstants.TMP_UNREGISTERED_USER_INTERVAL;

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

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private TimeIntervalService timeIntervalService;

    @Autowired
    private SmsPasswordRepository smsPasswordRepository;

    @Autowired
    private DateUtils dateUtils;

    @PersistenceContext
    EntityManager entityManager;

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
    public boolean changePassword(Long id, ChangePasswordDto chPassDto) throws ValidationException {
        UserPersonal userPersonal = userPersonalRepository.findByIdAndPassword(id, SecurityUtils.hashPassword(chPassDto.getOldPassword()));
        if (userPersonal != null) {
            if (Objects.nonNull(chPassDto.getNewPassword())) {
                if (!generalValidator.isPasswordValid(chPassDto.getNewPassword()))
                    throw new ValidationException("password not correct, min 6 characters and one of them with big letter or number or spec symbol");
                String newPwd = SecurityUtils.hashPassword(chPassDto.getNewPassword());
                userPersonal.setPassword(SecurityUtils.hashPassword(newPwd));
                userPersonalRepository.saveAndFlush(userPersonal);
                return true;
            }
        }
        return false;
    }

    //from client otp number flew already 1 time hashed
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserPersonal changePassword(String login, String password, String otp) throws ValidationException, UserValidatorImpl.UserIsBlockedException {
        Long userId = userRepository.findByLogin(login).getId();
        SmsPassword smsPassword = smsPasswordRepository.findOne(userId);
        //validation steps
        if (!smsPassword.getSmsPass().equals(SecurityUtils.hashPassword(otp)))
            throw new ValidationException("wrong otp number");
        userValidator.checkForBlock(login);
        if (!generalValidator.isPasswordValid(password))
            throw new ValidationException("password not correct, min 6 characters and one of them with big letter or number or spec symbol");

        UserPersonal personal = userRepository.findByLogin(login).getUserPersonal();
        String pass = SecurityUtils.hashPassword(password);
        personal.setPassword(SecurityUtils.hashPassword(pass));
        smsPasswordRepository.delete(smsPassword);
        return userPersonalRepository.save(personal);

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
    public boolean isPasswordCorrect(String login, String pass) throws UserValidatorImpl.UserIsBlockedException {
        //checks for block
        userValidator.checkForBlock(login);
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
        if (!generalValidator.isPasswordValid(temporaryUser.getHashedPwd()))
            throw new ValidationException("password not correct, min 6 characters and one of them with big letter or number or spec symbol");

        return smsService.sendConfirmationAndSaveUser(temporaryUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User confirmRegistration(String login, String otp) throws NotFoundException {
        TemporaryUser tmpUser = temporaryUserRepository.getByLoginAndOtp(login, otp);
        if (Objects.isNull(tmpUser)) throw new NotFoundException("current user not found");

        User user = new User(tmpUser);
        user = userRepository.save(user);
        deviceRepository.save(user.getDevices());
        accountRepository.save(new Account(user));
        userPersonalRepository.save(new UserPersonal(user.getId(), tmpUser.getHashedPwd()));

        //delete from temp users
        TemporaryUser temporaryUser = temporaryUserRepository.getByLogin(login);
        if (Objects.nonNull(temporaryUser)) temporaryUserRepository.delete(temporaryUser);

        return user;
    }

    public String getAccountBalanceByUserLogin(String login) {
        return accountRepository.getAccountByUserLogin(login).getAccountBalance().toString();
    }

    @Override
    public List<UserPersonal> getBlockedUsers(String sqlDate, String timeFromConfig) {
        Session session = entityManager.unwrap(Session.class);
        String queryStr = "SELECT usr_pers.* FROM ma_user_personal usr_pers WHERE usr_pers.usr_pers_block_expires+" + "'" + timeFromConfig + "'" + "<" + "'" + sqlDate + "'";
        Query query = session.createSQLQuery(queryStr).addEntity(UserPersonal.class);
        return query.list();
    }

    @Override
    public List<UserPersonal> setToZeroForgotPassword(String sqlDate, String timeFromConfig) {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT user_pers.* FROM ma_user_personal user_pers WHERE user_pers.usr_pers_for_pwd_expire+" + "'" + timeFromConfig + "'" + "<" + "'" + sqlDate + "'";
        Query query = session.createSQLQuery(queryString).addEntity(UserPersonal.class);
        return query.list();
    }

    @Override
    public List<TemporaryUser> getUsersWithExpiredTerm(String sqlDate, String timeFromConfig) {
        Session session = entityManager.unwrap(Session.class);
        String queryStr = "SELECT tmp.* FROM ma_temporary_user  tmp WHERE tmp.tmp_confirm_expiry+" + "'" + timeFromConfig + "'" + "<" + "'" + sqlDate + "'";
        Query query = session.createSQLQuery(queryStr).addEntity(TemporaryUser.class);
        return query.list();
    }

    @Override
    public String getEndSmsPeriod() throws ParseException {
        return dateUtils.converToTimeStamp(timeIntervalService.getByName(TMP_UNREGISTERED_USER_INTERVAL.toString()).getTimeInterval(), TMP_UNREGISTERED_USER_INTERVAL);
    }
}
