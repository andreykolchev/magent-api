package com.magent.servicemodule.service.impl;

import com.magent.authmodule.utils.SecurityUtils;
import com.magent.authmodule.utils.validators.UserValidatorImpl;
import com.magent.authmodule.utils.validators.interfaces.UserValidator;
import com.magent.domain.*;
import com.magent.domain.dto.ChangePasswordDto;
import com.magent.domain.enums.UserRoles;
import com.magent.repository.*;
import com.magent.servicemodule.service.interfaces.SmsService;
import com.magent.servicemodule.service.interfaces.TimeIntervalService;
import com.magent.servicemodule.service.interfaces.UserService;
import com.magent.servicemodule.utils.dateutils.ServiceDateUtils;
import com.magent.servicemodule.utils.validators.interfaces.GeneralValidator;
import javassist.NotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.bind.ValidationException;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import static com.magent.domain.enums.TimeIntervalConstants.TMP_UNREGISTERED_USER_INTERVAL;

/**
 * service for User operations.
 */
@Service
@Transactional(readOnly = true)
class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GeneralValidator generalValidator;

    @Value("${sms.send.real}")
    private boolean isSmsGateActive;

    @Autowired
    @Qualifier("smsServiceImpl")
    private SmsService smsService;

    @Autowired
    @Qualifier("smsDemoServiceImpl")
    private SmsService demoSmsService;

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
    private ServiceDateUtils dateUtils;

    @PersistenceContext
    EntityManager entityManager;

    /**
     * get users by filter
     * @param filter
     * @return list of Users by filter
     * @throws NotFoundException if not found any param:id,login,role
     */
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

    /**
     * change user password
     * @param id
     * @param chPassDto
     * @return boolean
     * @throws ValidationException if new password not valid
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean changePassword(Long id, ChangePasswordDto chPassDto) throws ValidationException {
        UserPersonal userPersonal = userPersonalRepository.findByIdAndPassword(id, SecurityUtils.hashPassword(chPassDto.getOldPassword()));
        if (userPersonal != null) {
            if (Objects.nonNull(chPassDto.getNewPassword())) {
                if (!generalValidator.isPasswordValid(chPassDto.getNewPassword()))
                    throw new ValidationException("password not correct, min 6 characters and one of them with big letter or number");
                String newPwd = SecurityUtils.hashPassword(chPassDto.getNewPassword());
                userPersonal.setPassword(SecurityUtils.hashPassword(newPwd));
                userPersonalRepository.saveAndFlush(userPersonal);
                return true;
            }
        }
        return false;
    }

    /**
     * @param login
     * @param password
     * @param otp
     * @return UserPersonal
     * @throws ValidationException if otp number or password not valid
     * @throws UserValidatorImpl.UserIsBlockedException if userValidator.checkForBlock(login) throws exception
     */
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
            throw new ValidationException("password not correct, min 6 characters and one of them with big letter or number");

        UserPersonal personal = userRepository.findByLogin(login).getUserPersonal();
        String pass = SecurityUtils.hashPassword(password);
        personal.setPassword(SecurityUtils.hashPassword(pass));
        smsPasswordRepository.delete(smsPassword);
        return userPersonalRepository.save(personal);

    }

    /**
     *
     * @param login
     * @return User
     */
    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public List<User> getAllUsersWithAccount() {
        return userRepository.getAllUsersWithAccount();
    }

    @Override
    public boolean isPasswordCorrect(String login, String pass) throws UserValidatorImpl.UserIsBlockedException {
        //checks for block
        userValidator.checkForBlock(login);
        String passFromLoginForm = SecurityUtils.hashPassword(pass);
        boolean res = userRepository.findByLogin(login).getUserPersonal().getPassword().equals(passFromLoginForm);
        if (!res) userValidator.addOneWrongEnter(login);
        return res;
    }


    /**
     * password comes already hashed from frontend
     * @param temporaryUser TemporaryUser
     * @return Confirmation
     * @throws ValidationException if one of params not valid: Name, Last name, login, password
     * @throws ParseException if sender.sendConfirmationAndSaveUser(temporaryUser) throws exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String isNewUserSaved(TemporaryUser temporaryUser) throws ValidationException, ParseException {
        if (!generalValidator.isEmailValid(temporaryUser.getEmail())) throw new ValidationException("email not valid");
        if (!generalValidator.isNameCorrect(temporaryUser.getFirstName()))
            throw new ValidationException("Name not valid. Name must start with big letter and mustn't contains numbers min length 2 characters");
        if (!generalValidator.isNameCorrect(temporaryUser.getLastName()))
            throw new ValidationException("Last name not valid.Last name must start with big letter and mustn't contains numbers and min length 2 characters");
        if (!generalValidator.isPhoneValid(temporaryUser.getUsername()))
            throw new ValidationException("login not valid. Login must be phone number");
        if (!generalValidator.isPasswordValid(temporaryUser.getHashedPwd()))
            throw new ValidationException("password not correct, min 6 characters and one of them with big letter or number");
        //demo verification
        SmsService sender = isSmsGateActive ? smsService : demoSmsService;
        return (String) sender.sendConfirmationAndSaveUser(temporaryUser);
    }

    /**
     *
     * @param login
     * @param otp
     * @return User
     * @throws NotFoundException if TemporaryUser not found by login and otp
     */
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

    /**
     *
     * @param login
     * @return Account balance (String)
     */
    public String getAccountBalanceByUserLogin(String login) {
        return accountRepository.getAccountByUserLogin(login).getAccountBalance().toString();
    }

    /**
     *
     * @param sqlDate
     * @param timeFromConfig
     * @return list of blocked users (UserPersonal)
     */
    @Override
    public List<UserPersonal> getBlockedUsers(String sqlDate, String timeFromConfig) {
        Session session = entityManager.unwrap(Session.class);
        String queryStr = "SELECT usr_pers.* FROM ma_user_personal usr_pers WHERE usr_pers.usr_pers_block_expires+" + "'" + timeFromConfig + "'" + "<" + "'" + sqlDate + "'";
        Query query = session.createSQLQuery(queryStr).addEntity(UserPersonal.class);
        return query.list();
    }

    /**
     *
     * @param sqlDate
     * @param timeFromConfig
     * @return list of users
     */
    @Override
    public List<UserPersonal> setToZeroForgotPassword(String sqlDate, String timeFromConfig) {
        Session session = entityManager.unwrap(Session.class);
        String queryString = "SELECT user_pers.* FROM ma_user_personal user_pers WHERE user_pers.usr_pers_for_pwd_expire+" + "'" + timeFromConfig + "'" + "<" + "'" + sqlDate + "'";
        Query query = session.createSQLQuery(queryString).addEntity(UserPersonal.class);
        return query.list();
    }

    /**
     *
     * @param sqlDate
     * @param timeFromConfig
     * @return list of users
     */
    @Override
    public List<TemporaryUser> getUsersWithExpiredTerm(String sqlDate, String timeFromConfig) {
        Session session = entityManager.unwrap(Session.class);
        String queryStr = "SELECT tmp.* FROM ma_temporary_user  tmp WHERE tmp.tmp_confirm_expiry+" + "'" + timeFromConfig + "'" + "<" + "'" + sqlDate + "'";
        Query query = session.createSQLQuery(queryStr).addEntity(TemporaryUser.class);
        return query.list();
    }

    /**
     *
     * @return get time stamp when OTP will die
     * @throws ParseException if dateUtils.convertToTimeStamp() throws exception
     */
    @Override
    public String getEndSmsPeriod() throws ParseException {
        return dateUtils.convertToTimeStamp(timeIntervalService.getByName(TMP_UNREGISTERED_USER_INTERVAL.toString()).getTimeInterval(), TMP_UNREGISTERED_USER_INTERVAL);
    }
}
