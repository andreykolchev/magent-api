package com.magent.controller;

import com.magent.controller.interfaces.GeneralController;
import com.magent.domain.AssignmentStatus;
import com.magent.domain.ValueType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created on 01.08.2016.
 */
@RestController
@RequestMapping("/data")
public class CommonControllerImpl implements GeneralController {

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
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET, value = "/valueType")
    public ResponseEntity<List<ValueType>> getValueType() throws Exception {
        List<ValueType> valueTypes = new ArrayList<>(Arrays.asList(ValueType.values()));
        return new ResponseEntity<>(valueTypes, HttpStatus.OK);
    }
}
