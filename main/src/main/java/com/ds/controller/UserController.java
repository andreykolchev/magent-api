package com.ds.controller;

import com.ds.controller.interfaces.GeneralController;
import com.ds.domain.User;
import com.ds.domain.dto.ChangePasswordDto;
import com.ds.service.interfaces.GeneralService;
import com.ds.service.interfaces.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Oleg Danyliuk on 18.02.2015.
 */
@RestController
@RequestMapping(value = "/users")
public class UserController implements GeneralController {

    @Autowired
    @Qualifier("userGeneralService")
    private GeneralService userServiceGen;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public List<User> getList() throws NotFoundException {
        return userServiceGen.getAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) throws NotFoundException {
        User user = (User) userServiceGen.getById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/byFilter")
    public ResponseEntity<List<User>> getUsersByFilter(@RequestParam("filter") String filter) throws NotFoundException {
        List<User> users = userService.getUsersByFilter(filter);
        return getDefaultResponce(users,HttpStatus.OK , HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public ResponseEntity<User> edit(@PathVariable("id") Long id, @RequestBody User user) throws NotFoundException {
        User updated = (User) userServiceGen.update(user, id);
        return getDefaultResponce(updated,HttpStatus.OK , HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}/changepassword")
    public ResponseEntity<Boolean> changePassword(@PathVariable("id") Long id, @RequestBody ChangePasswordDto chPassDto) {
        return new ResponseEntity<>(userService.changePassword(id, chPassDto), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/")
    public ResponseEntity<User> create(@RequestBody User user) {
        User newUser = (User) userServiceGen.save(user);
        return getDefaultResponce(newUser,HttpStatus.CREATED , HttpStatus.NOT_FOUND);
    }

}
