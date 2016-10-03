package com.magent.servicemodule.service.impl;


import com.magent.domain.*;
import com.magent.domain.dto.UpdateDataDto;
import com.magent.repository.*;
import com.magent.servicemodule.service.interfaces.DataService;
import com.magent.servicemodule.utils.ariphmeticbeans.ComissionCalculator;
import com.magent.servicemodule.utils.ariphmeticbeans.ComissionCalculatorImpl;
import javassist.NotFoundException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.magent.domain.AssignmentStatus.COMPLETE;
import static com.magent.domain.AssignmentStatus.NEED_CONFIRMATION;

/**
 * service for Mobile application
 */
@Service
@Transactional(readOnly = true)
class DataServiceImpl implements DataService {

    @Value("${upload.file.path}")
    private String uploadPath;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    AssignmentAttributeRepository assignmentAttributeRepository;

    @Autowired
    AssignmentTaskControlRepository assignmentTaskControlRepository;

    @Autowired
    private ComissionCalculator comissionCalculator;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TemplateTypeRpository typeRpository;

    /**
     * @param userId id of user
     * @param syncId key for data synchronization (get only updates)
     * @return UpdateDataDto entity
     */
    @Override
    public UpdateDataDto getData(Long userId, Long syncId) {

        List<Assignment> assignmentList;
        if (syncId == null || syncId.equals(0L)) {
            assignmentList = assignmentRepository.findAllByUserId(userId);
        } else {
            assignmentList = assignmentRepository.findAllByUserIdAndLastChange(userId, syncId);
            initializeAttributesAndTasks(assignmentList);
        }
        for (Assignment assignment : assignmentList) {
            initializeControls(assignment);
            assignment.setTemplateTypeDescription(typeRpository.getByTemplateId(assignment.getTemplateId()).getDescription()
            );
        }
        UpdateDataDto result = new UpdateDataDto();
        result.setSyncId(new Date().getTime());
        result.setAssignments(assignmentList);
        return result;
    }

    /**
     * @param dataDto UpdateDataDto entity with predefined params
     * @return UpdateDataDto entity persisted in DB
     * @throws ComissionCalculatorImpl.FormulaNotFound
     * @throws NotFoundException
     * @throws ParseException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UpdateDataDto updateData(UpdateDataDto dataDto) throws ComissionCalculatorImpl.FormulaNotFound, NotFoundException, ParseException {
        UpdateDataDto result = new UpdateDataDto();
        if (dataDto != null) {
            List<Assignment> persistedAssignmentList = new ArrayList<>();
            List<Assignment> assignmentList = dataDto.getAssignments();
            if (assignmentList != null) {
                //if assignment contains first registration template
                changeAssignmentForFullRegistration(assignmentList);
                //persist
                assignmentRepository.save(assignmentList);
                for (Assignment assignment : assignmentList) {
                    //do operations in attributes
                    updateDataAttributesOperation(assignment);
                    updateDataTasks(assignment);
                    Assignment persistedAssignment = assignmentRepository.findOne(assignment.getId());
                    initializeControls(persistedAssignment);
                    persistedAssignmentList.add(persistedAssignment);
                }
                result.setAssignments(persistedAssignmentList);
            }
        }
        return result;
    }

    /**
     * @param fileName file name for saving on server
     * @param file     MultipartFile
     * @return url for saved file
     * @throws IOException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveFile(String fileName, MultipartFile file) throws IOException {

        if (file != null && !file.isEmpty()) {
            byte[] fileBytes = file.getBytes();
            String url = uploadPath + (new Date().getTime()) + fileName;

            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(url)));
            stream.write(fileBytes);
            stream.close();
            return url;

        } else {
            throw new IllegalArgumentException("Can't upload. The file is empty.");
        }
    }

    /**
     * additional logic for commission calculation
     *
     * @param attributeList list of AssignmentAttribute (data for calculating)
     * @return calculated commission
     * @throws ComissionCalculatorImpl.FormulaNotFound
     */
    private Double calculateCommision(List<AssignmentAttribute> attributeList) throws ComissionCalculatorImpl.FormulaNotFound {
        Number commissionSum = comissionCalculator.calculateCommission(attributeList, 2);
        return commissionSum.doubleValue();
    }

    /**
     * @param attributeList list of AssignmentAttribute (must contain AssignmentAttribute with formula type)
     * @return if one of AssignmentAttributes has formula type, return true
     * @see ValueType
     */
    private boolean isFormulaPresent(List<AssignmentAttribute> attributeList) {
        return findValueType(attributeList, ValueType.FORMULA);
    }

    /**
     * @param attributeList list of AssignmentAttribute (must contain AssignmentAttribute with commission type)
     * @return if one of AssignmentAttributes has commission type, return true
     * @see ValueType
     */
    private boolean isCommissionPresent(List<AssignmentAttribute> attributeList) {
        return findValueType(attributeList, ValueType.COMMISSION_COST);
    }


    /**
     * procedure for change balance of User in update transaction (Account.setAccountBalance)
     *
     * @param assignment Assignment contain AssignmentAttributes with types (FORMULA, COMMISSION_COST)
     * @throws NotFoundException
     * @throws ParseException
     */
    private void addTransaction(Assignment assignment) throws NotFoundException, ParseException {
        List<AssignmentAttribute> attributeList = assignmentAttributeRepository.getByAssignmentId(assignment.getId());
        Double commissionSum = null;
        for (AssignmentAttribute attribute : attributeList) {
            if (attribute.getValueType().equals(ValueType.COMMISSION_COST)) {
                commissionSum = Double.valueOf(attribute.getValue());
                break;
            }
        }
        if (commissionSum == null) {
            throw new NotFoundException("Commission summ not present");
        }
        User user = userRepository.findOne(assignment.getUserId());
        transactionRepository.save(new Transactions(user.getAccount().getAccountNumber(), new Date(), true, new BigDecimal(commissionSum)));
        Account account = user.getAccount();
        account.setAccountBalance((account.getAccountBalance() + commissionSum));
        accountRepository.save(account);
    }

    /**
     * looking in attribute list current value type
     *
     * @param attributeList list of AssignmentAttribute (must contain AssignmentAttribute with ValueType)
     * @param valueType
     * @return if one of AssignmentAttributes has valueType, return true
     */
    private boolean findValueType(List<AssignmentAttribute> attributeList, ValueType valueType) {
        for (AssignmentAttribute attribute : attributeList) {
            if (attribute.getValueType().equals(valueType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * initialize controls (lazy) of Assignment (Hibernate.initialize(Object proxy))
     *
     * @param assignment Assignment entity
     */
    private void initializeControls(Assignment assignment) {
        for (AssignmentTask task : assignment.getTasks()) {
            Hibernate.initialize(task.getControls());
        }
    }

    /**
     * attributes operation
     *
     * @param assignment Assignment entity for update
     * @throws NotFoundException
     * @throws ParseException
     * @throws ComissionCalculatorImpl.FormulaNotFound
     */
    @Transactional(rollbackFor = Exception.class)
    private void updateDataAttributesOperation(Assignment assignment) throws NotFoundException, ParseException, ComissionCalculatorImpl.FormulaNotFound {
        if (Objects.nonNull(assignment.getAttributes())) {
            List<AssignmentAttribute> attributeList = assignmentAttributeRepository.getByAssignmentId(assignment.getId());
            assignmentAttributeRepository.save(assignment.getAttributes());
            //calculate possible commission logic by template
            if (isFormulaPresent(attributeList)) {
                if (isCommissionPresent(attributeList)) {
                    Double commission = calculateCommision(attributeList);
                    AssignmentAttribute attribute = assignmentAttributeRepository.getCommssionCost(assignment.getId(), ValueType.COMMISSION_COST);
                    attribute.setValue(String.valueOf(commission));
                    assignmentAttributeRepository.save(attribute);
                }
                //full condition for calculate commission
                if (assignment.getStatus().equals(AssignmentStatus.COMPLETE)) {
                    //transaction logic
                    addTransaction(assignment);
                }
            }


        }
    }

    /**
     * tasks operation
     *
     * @param assignment Assignment entity for update
     */
    @Transactional(rollbackFor = Exception.class)
    private void updateDataTasks(Assignment assignment) {
        if (Objects.nonNull(assignment.getTasks())) {
            for (AssignmentTask assignmentTask : assignment.getTasks()) {
                if (Objects.nonNull(assignmentTask.getControls())) {
                    assignmentTaskControlRepository.save(assignmentTask.getControls());
                }
            }
        }
    }

    /**
     * full registration operation
     * set NEED_CONFIRMATION status for Assignment
     * if Assignment.status is COMPLETE and Assignment.templateId is fullRegTemplateId
     *
     * @param assignmentList list of Assignments for update
     */
    @Transactional(rollbackFor = Exception.class)
    private void changeAssignmentForFullRegistration(List<Assignment> assignmentList) {
        //template type 2 is always for full registration only.
        Long fullRegTemplateId = typeRpository.findOne(2L).getTemplate().getId();

        for (Assignment assignment : assignmentList) {
            if (assignment.getStatus().equals(COMPLETE) && assignment.getTemplateId().equals(fullRegTemplateId)) {
                assignment.setStatus(NEED_CONFIRMATION);
            }
        }
    }

    /**
     * initialize attributes and tasks of Assignment (Hibernate.initialize(Object proxy))
     *
     * @param assignmentList list of Assignments for initialize
     */
    @Transactional(readOnly = true)
    private void initializeAttributesAndTasks(List<Assignment> assignmentList) {
        for (Assignment assignment : assignmentList) {
            Hibernate.initialize(assignment.getAttributes());
            Hibernate.initialize(assignment.getTasks());
        }
    }

}
