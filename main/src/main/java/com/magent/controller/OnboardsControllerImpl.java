package com.magent.controller;

import com.magent.controller.interfaces.GeneralController;
import com.magent.domain.OnBoarding;
import com.magent.servicemodule.service.interfaces.GeneralService;
import com.magent.servicemodule.utils.validators.ImageValidatorImpl;
import com.magent.servicemodule.utils.validators.OnBoardingValidatorImpl;
import com.magent.servicemodule.utils.validators.interfaces.OnBoardingValidator;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.util.List;

/**
 * Created on 01.08.2016.
 */
@RestController
@RequestMapping("/onboards")
public class OnboardsControllerImpl implements GeneralController {


    @Autowired
    @Qualifier("onBoardingGeneralService")
    private GeneralService onBoardGenService;

    @Autowired
    @Qualifier("onBoardingValidatorImpl")
    private OnBoardingValidator onBoardingValidator;

    /**
     * @return list of Onboarding entities for displaying on android app
     * @throws NotFoundException
     */
    @RequestMapping(method = RequestMethod.GET, value = "/")
    public ResponseEntity<List<OnBoarding>> getOnBoardingInformation() throws NotFoundException {
        return new ResponseEntity(onBoardGenService.getAll(), HttpStatus.OK);
    }

    /**
     * @param id - in ds_onboards
     * @return OnBoarding entity
     * @throws NotFoundException
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
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
    @RequestMapping(method = RequestMethod.POST, value = "/")
    public ResponseEntity<OnBoarding> createOnBoardEntity(@RequestBody OnBoarding onBoarding) throws IOException, ImageValidatorImpl.NotCorrectImageExtension, OnBoardingValidatorImpl.InvalidOnboardEntity, ValidationException {
        if (onBoardingValidator.isOnBoardEntityValid(onBoarding))
            return new ResponseEntity<>((OnBoarding) onBoardGenService.save(onBoarding), HttpStatus.CREATED);
        else throw new OnBoardingValidatorImpl.InvalidOnboardEntity("Invalid image entity");
    }

    @ApiOperation(value = "update Onboard entity.", notes = "Description see POST method")
    @RequestMapping(method = RequestMethod.PUT, value = "/")
    public ResponseEntity<OnBoarding> updateOnBoardEntity(@RequestBody OnBoarding onBoarding) throws IOException, ImageValidatorImpl.NotCorrectImageExtension, OnBoardingValidatorImpl.InvalidOnboardEntity, ValidationException {
        if (onBoardingValidator.isOnBoardEntityValid(onBoarding))
            return new ResponseEntity<>((OnBoarding) onBoardGenService.save(onBoarding), HttpStatus.OK);
        else throw new OnBoardingValidatorImpl.InvalidOnboardEntity("Invalid image entity");
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity deleteOnboardEntity(@PathVariable("id") Long id) {
        onBoardGenService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
