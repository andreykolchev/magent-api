package com.magent.controller;

import com.magent.controller.interfaces.GeneralController;
import com.magent.domain.Reason;
import com.magent.servicemodule.service.interfaces.GeneralService;
import com.magent.servicemodule.service.interfaces.ReasonService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @since 29/04/2016
 */
@RestController
@RequestMapping(value = "/reasons")
public class ReasonControllerImpl implements GeneralController {

    @Autowired
    @Qualifier("reasonGeneralService")
    private GeneralService reasonServiceGeneral;

    @Autowired
    private ReasonService reasonService;

    /**
     * @return Reasons enums as List from db see ds_reason table and Reason.class
     */
    @RequestMapping(name = "/", method = RequestMethod.GET)
    public List<Reason> getList() throws NotFoundException {
        return reasonServiceGeneral.getAll();
    }

    /**
     * @param id of Reason enums class
     * @return Reason from db
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Reason> getReasonById(
            @PathVariable("id") Long id) throws NotFoundException {
        Reason reason = (Reason) reasonServiceGeneral.getById(id);
        return new ResponseEntity<>(reason, HttpStatus.OK);
    }

    /**
     * @param reason Reason enums as JSON object from client
     * @return created Reason enums
     */
    @RequestMapping(method = RequestMethod.POST, value = "/")
    public ResponseEntity<Reason> create(@RequestBody Reason reason) {
        Reason newReason = (Reason) reasonServiceGeneral.save(reason);
        return getDefaultResponce(newReason, HttpStatus.CREATED, HttpStatus.NOT_FOUND);
    }

    /**
     * @param reason - Reason enums as JSON object from client
     * @return updated enums
     * @throws NotFoundException if enums with current id not found in db thows NOT_FOUND exception
     */
    @SuppressFBWarnings("RCN_REDUNDANT_NULLCHECK_WOULD_HAVE_BEEN_A_NPE")
    @RequestMapping(method = RequestMethod.PUT, value = "/")
    public ResponseEntity<Reason> update(@RequestBody Reason reason) throws NotFoundException {
        Reason updateReason = (Reason) reasonServiceGeneral.update(reason, reason.getId());
        return getDefaultResponce(updateReason, HttpStatus.OK, HttpStatus.NOT_FOUND);
    }

    /**
     * @param id of Reason enums class
     * @return HttpStatus
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        reasonServiceGeneral.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * @param id  parent_id column @see Reason
     * @return  List of Reasons
     */
    @RequestMapping(value = "/{id}/children", method = RequestMethod.GET)
    public ResponseEntity<List<Reason>> getListByParentId(
            @PathVariable("id") Long id) {
        List<Reason> reasons = reasonService.getListByParentId(id);
        HttpStatus status = reasons.size() == 0 ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity(reasons, status);
    }

}
