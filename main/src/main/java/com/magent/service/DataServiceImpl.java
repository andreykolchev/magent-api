package com.magent.service;


import com.magent.domain.*;
import com.magent.domain.dto.UpdateDataDto;
import com.magent.repository.*;
import com.magent.service.interfaces.DataService;
import com.magent.utils.ariphmeticbeans.ComissionCalculator;
import com.magent.utils.ariphmeticbeans.ComissionCalculatorImpl;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
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

@Service
@Transactional(readOnly = true)
public class DataServiceImpl implements DataService {

    public static final String JPG_EXTENSION = ".jpg";

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

    @SuppressFBWarnings("EC_UNRELATED_TYPES")
    @Override
    public UpdateDataDto getData(Long userId, Long syncId) {

        List<Assignment> assignmentList;
        if (syncId == null || syncId.equals(0L)) {
            assignmentList = assignmentRepository.findAllByUserId(userId);
        } else {
            assignmentList = assignmentRepository.findAllByUserIdAndLastChange(userId, syncId);
        }
        for (Assignment assignment : assignmentList) {
            initializeControls(assignment);
        }
        UpdateDataDto result = new UpdateDataDto();
        result.setSyncId(new Date().getTime());
        result.setAssignments(assignmentList);
        return result;
    }


    @SuppressFBWarnings("NS_DANGEROUS_NON_SHORT_CIRCUIT")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UpdateDataDto updateData(UpdateDataDto dataDto) throws ComissionCalculatorImpl.FormulaNotFound, NotFoundException, ParseException {
        //SecurityUtils.checkPrincipalIsNull(DataServiceImpl.class); 
        UpdateDataDto result = new UpdateDataDto();
        if (dataDto != null) {
            List<Assignment> persistedAssignmentList = new ArrayList<>();
            List<Assignment> assignmentList = dataDto.getAssignments();
            if (assignmentList != null) {
                try {
                    assignmentRepository.save(assignmentList);
                } catch (Exception e) {
                    return null;
                }
                for (Assignment assignment : assignmentList) {
                    if (Objects.nonNull(assignment.getAttributes())) {
                        List<AssignmentAttribute> attributeList = assignmentAttributeRepository.getByAssignmentId(assignment.getId());
                        try {
                            assignmentAttributeRepository.save(assignment.getAttributes());
                            //calculate possible commission logic by template
                            if (isFormulaPresent(attributeList)) {
                                if (isCommissionPresent(attributeList)) {
                                    Double commission = calculateCommision(attributeList);
                                    if (commission != null) {
                                        AssignmentAttribute attribute = assignmentAttributeRepository.getCommssionCost(assignment.getId(), ValueType.COMMISSION_COST);
                                        attribute.setValue(String.valueOf(commission));
                                        assignmentAttributeRepository.save(attribute);
                                    }
                                }
                            }
                        } catch (Exception e) {
                            return null;
                        }

                        if (assignment.getStatus().equals(AssignmentStatus.COMPLETE) & isFormulaPresent(attributeList)) {
                            //transaction logic
                            addTransaction(assignment);
                        }

                    }
                    if (Objects.nonNull(assignment.getTasks())) {
                        for (AssignmentTask assignmentTask : assignment.getTasks()) {
                            if (Objects.nonNull(assignmentTask.getControls())) {
                                try {
                                    assignmentTaskControlRepository.save(assignmentTask.getControls());
                                } catch (Exception e) {
                                    return null;
                                }
                            }
                        }
                    }
                    Assignment persistedAssignment = assignmentRepository.findOne(assignment.getId());
                    initializeControls(persistedAssignment);
                    persistedAssignmentList.add(persistedAssignment);
                }
                result.setAssignments(persistedAssignmentList);
            }
        }
        return result;
    }

    @SuppressFBWarnings("PATH_TRAVERSAL_IN")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveFile(String fileName, String control, MultipartFile file) throws IOException {

        if (file != null && !file.isEmpty()) {
            byte[] fileBytes = file.getBytes();
            String url = uploadPath + (new Date().getTime()) + fileName;
            try {

                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(url)));
                stream.write(fileBytes);
                stream.close();
                return url;
            } catch (Exception e) {
                throw e;
            }

        } else {
            throw new IllegalArgumentException("Can't upload. The file is empty.");
        }
    }

    //additional logic for commission calculation
    private Double calculateCommision(List<AssignmentAttribute> attributeList) throws ComissionCalculatorImpl.FormulaNotFound {
        Number commissionSum = comissionCalculator.calculateCommission(attributeList, 2);
        return commissionSum.doubleValue();
    }

    private boolean isFormulaPresent(List<AssignmentAttribute> attributeList) {
        return findValueType(attributeList, ValueType.FORMULA);
    }

    private boolean isCommissionPresent(List<AssignmentAttribute> attributeList) {
        return findValueType(attributeList, ValueType.COMMISSION_COST);
    }

    //this void is only for change balance in update transaction
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

    //looking in attribute list current value type
    private boolean findValueType(List<AssignmentAttribute> attributeList, ValueType valueType) {
        for (AssignmentAttribute attribute : attributeList) {
            if (attribute.getValueType().equals(valueType)) {
                return true;
            }
        }
        return false;
    }

    //initialize controls
    private void initializeControls(Assignment assignment) {
        for (AssignmentTask task : assignment.getTasks()) {
            Hibernate.initialize(task.getControls());
        }
    }
}
