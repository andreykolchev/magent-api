package com.magent.controller;

import com.magent.controller.interfaces.GeneralController;
import com.magent.domain.*;
import com.magent.domain.dto.UpdateDataDto;
import com.magent.domain.enums.UserRoles;
import com.magent.service.interfaces.DataService;
import com.magent.service.interfaces.GeneralService;
import com.magent.service.interfaces.TemplateTypeService;
import com.magent.service.interfaces.UserService;
import com.magent.utils.ariphmeticbeans.ComissionCalculatorImpl;
import com.magent.utils.validators.ImageValidatorImpl;
import com.magent.utils.validators.OnBoardingValidatorImpl;
import com.magent.utils.validators.interfaces.OnBoardingValidator;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/data")
public class DataControllerImpl implements GeneralController {

    @Autowired
    private DataService dataService;

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("onBoardingGeneralService")
    private GeneralService onBoardGenService;

    @Autowired
    @Qualifier("onBoardingValidatorImpl")
    private OnBoardingValidator onBoardingValidator;

    @Autowired
    private TemplateTypeService templateTypeService;

    /**
     * @param syncId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/")
    public ResponseEntity<UpdateDataDto> getData(@RequestParam(required = false) Long syncId) {
        User activeUser = getActiveUser(userService);
        UpdateDataDto dataDto = null;
        if (activeUser != null) {
            dataDto = dataService.getData(activeUser.getId(), syncId);
        }
        return getDefaultResponce(dataDto, HttpStatus.OK, HttpStatus.NOT_FOUND);
    }

    /**
     * @param dataDto
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/")
    public ResponseEntity<UpdateDataDto> updateData(@RequestBody UpdateDataDto dataDto) throws ComissionCalculatorImpl.FormulaNotFound, NotFoundException, ParseException {
        UpdateDataDto result = dataService.updateData(dataDto);
        return getDefaultResponce(result, HttpStatus.OK, HttpStatus.NOT_FOUND);
    }

    /**
     * @param name
     * @param control
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST, value = "/uploadFile", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadFile(@RequestParam("name") String name,
                                             @RequestParam(value = "controlId", required = false) String control,
                                             @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        String url = dataService.saveFile(name, control, file);
        return getDefaultResponce(url, HttpStatus.OK, HttpStatus.NOT_FOUND);
    }

    /**
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET, value = "/valueType")
    public ResponseEntity<List<ValueType>> getValueType() throws Exception {
        List<ValueType> valueTypes = new ArrayList<>(Arrays.asList(ValueType.values()));
        return new ResponseEntity<>(valueTypes, HttpStatus.OK);
    }

    /**
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET, value = "/assignmentStatus")
    public ResponseEntity<List<AssignmentStatus>> getAssignmentStatus() throws Exception {
        List<AssignmentStatus> assignmentStatuses = new ArrayList<>(Arrays.asList(AssignmentStatus.values()));
        return new ResponseEntity<>(assignmentStatuses, HttpStatus.OK);
    }

    /**
     * @return list of Onboarding entities for displaying on android app
     * @throws NotFoundException
     */
    @RequestMapping(method = RequestMethod.GET, value = "/onboards")
    public ResponseEntity<List<OnBoarding>> getOnBoardingInformation() throws NotFoundException {
        return new ResponseEntity(onBoardGenService.getAll(), HttpStatus.OK);
    }

    /**
     * @param id - in ds_onboards
     * @return OnBoarding entity
     * @throws NotFoundException
     */
    @RequestMapping(method = RequestMethod.GET, value = "/onboards/{id}")
    public ResponseEntity<OnBoarding> getOnboardById(@PathVariable("id") Long id) throws NotFoundException {
        return new ResponseEntity<>((OnBoarding) onBoardGenService.getById(id), HttpStatus.OK);
    }

    /**
     * @param onBoarding - see OnBoarding.class
     * @return - updated entity
     * @throws IOException
     * @throws ImageValidatorImpl.NotCorrectImageExtension
     * @throws OnBoardingValidatorImpl.InvalidOnboardEntity
     */
    @ApiOperation(value = "create OnBoard entity", notes = "allowed only png and svg formats." +
            " Image height and width must be 170x170 pixels if image will be with another size returned 400 Status." +
            " Image must have full file name with extension for validation and for creating on android side. " +
            " Max length of content is 50 characters, min length 0 characters. " +
            " Max lenght of description is 250 characters, min 0 characters.")
    @RequestMapping(method = RequestMethod.POST, value = "/onboards")
    public ResponseEntity<OnBoarding> createOnBoardEntity(@RequestBody OnBoarding onBoarding) throws IOException, ImageValidatorImpl.NotCorrectImageExtension, OnBoardingValidatorImpl.InvalidOnboardEntity, ValidationException {
        if (onBoardingValidator.isOnBoardEntityValid(onBoarding))
            return new ResponseEntity<>((OnBoarding) onBoardGenService.save(onBoarding), HttpStatus.CREATED);
        else throw new OnBoardingValidatorImpl.InvalidOnboardEntity("Invalid image entity");
    }

    @ApiOperation(value = "update Onboard entity.", notes = "Description see POST method")
    @RequestMapping(method = RequestMethod.PUT, value = "/onboards")
    public ResponseEntity<OnBoarding> updateOnBoardEntity(@RequestBody OnBoarding onBoarding) throws IOException, ImageValidatorImpl.NotCorrectImageExtension, OnBoardingValidatorImpl.InvalidOnboardEntity, ValidationException {
        if (onBoardingValidator.isOnBoardEntityValid(onBoarding))
            return new ResponseEntity<>((OnBoarding) onBoardGenService.save(onBoarding), HttpStatus.OK);
        else throw new OnBoardingValidatorImpl.InvalidOnboardEntity("Invalid image entity");
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/onboards/{id}")
    public ResponseEntity deleteOnboardEntity(@PathVariable("id") Long id) {
        onBoardGenService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/template-types")
    public List<TemplateType> getAll() throws NotFoundException {
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        return templateTypeService.getTemplateTypesForMobApp(UserRoles.getByString(authorities.get(0).toString()).getRoleId());
    }

    /**
     * End-point returned balance of current user as String representation
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/user-balance")
    public ResponseEntity<String> getCurrentUserBalance() {
        String login = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return getDefaultResponce(userService.getAccountBalanceByUserLogin(login), HttpStatus.OK, HttpStatus.NOT_FOUND);
    }
}
