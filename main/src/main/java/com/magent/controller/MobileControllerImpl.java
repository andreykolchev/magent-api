package com.magent.controller;

import com.magent.controller.interfaces.GeneralController;
import com.magent.domain.*;
import com.magent.domain.dto.UpdateDataDto;
import com.magent.domain.enums.UserRoles;
import com.magent.service.interfaces.*;
import com.magent.utils.ariphmeticbeans.ComissionCalculatorImpl;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;


@RestController
@RequestMapping("/mobile")
public class MobileControllerImpl implements GeneralController {

    @Autowired
    private DataService dataService;

    @Autowired
    private UserService userService;

    @Autowired
    private TemplateTypeService templateTypeService;

    @Autowired
    private TrackingService trackingService;

    @Autowired
    @Qualifier("assignmentServiceImpl")
    AssignmentService assignmentService;

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

    /**
     * @return
     */
    @RequestMapping(value = "tracking/settings/", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Settings> getSettings() {
        User activeUser = getActiveUser(userService);
        Settings settings = null;
        if (activeUser != null) {
            settings = trackingService.getSettings(activeUser.getId());
        }
        HttpStatus status = settings == null ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity<>(settings, status);
    }

    /**
     * @param callList
     * @return
     */
    @RequestMapping(value = "tracking/calls/", method = RequestMethod.POST)
    public ResponseEntity createCalls(@RequestBody List<Call> callList) {
        User activeUser = getActiveUser(userService);
        if (activeUser != null) {
            trackingService.createCalls(callList, activeUser.getId());
        }
        HttpStatus status = activeUser == null ? HttpStatus.NOT_FOUND : HttpStatus.CREATED;
        return new ResponseEntity<>(status);
    }

    /**
     * @param location
     * @return
     */
    @RequestMapping(value = "tracking/locations/", method = RequestMethod.POST)
    public ResponseEntity createLocations(@RequestBody Location location) {
        User activeUser = getActiveUser(userService);
        if (activeUser != null) {
            trackingService.createLocations(location, activeUser.getId());
        }
        return getDefaultResponceStatusOnly(activeUser, HttpStatus.CREATED, HttpStatus.NOT_FOUND);
    }

    /**
     * @param activityList
     * @return
     */
    @RequestMapping(value = "tracking/apps/", method = RequestMethod.POST)
    public ResponseEntity createActivities(@RequestBody List<Activity> activityList) {
        User activeUser = getActiveUser(userService);
        if (activeUser != null) {
            trackingService.createActivities(activityList, activeUser.getId());
        }
        return getDefaultResponceStatusOnly(activeUser, HttpStatus.CREATED, HttpStatus.NOT_FOUND);
    }

    /**
     * endpoint create assignment for current user which sent this information
     * @param assignment
     * @return
     */
    @RequestMapping(value = "/assignments/createByTemplateId", method = RequestMethod.POST)
    public ResponseEntity<Assignment> createByTemplateId(@RequestBody Assignment assignment) throws NotFoundException {
        User activeUser = getActiveUser(userService);
        if (activeUser != null) {
            assignment.setUserId(activeUser.getId());
            return getDefaultResponce(assignmentService.createByTemplateId(assignment),HttpStatus.CREATED , HttpStatus.NOT_FOUND);
        }
        throw new NotFoundException("user not found in System");
    }
}
