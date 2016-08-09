package com.magent.utils;


import com.magent.domain.*;
import com.magent.domain.dto.ChangePasswordDto;
import com.magent.domain.dto.UpdateDataDto;
import com.magent.domain.enums.UserRoles;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by artomov.ihor on 20.04.2016.
 */
public final class EntityGenerator {

    public static Assignment getNewAssignment() {
        Assignment assignment = new Assignment();
        assignment.setTemplateId(1L);
        assignment.setUserId(1L);
        assignment.setReasonId(1L);
        assignment.setStatus(AssignmentStatus.NEW);
        Date testDate = new Date();
        assignment.setDateEnd(testDate);
        assignment.setDateStart(testDate);
        assignment.setDeadLine(testDate);
        assignment.setDesc("TEST");
        assignment.setLastChange(LocalDate.now().toEpochDay());
        assignment.setLongitude(2.0);
        assignment.setLatitude(1.0);
        assignment.setVersion(1);
        return assignment;
    }

    public static Assignment getTemplateAssignment() {
        Assignment assignment = new Assignment();
        assignment.setStatus(AssignmentStatus.NEW);
        assignment.setTemplateId(1L);
        assignment.setUserId(1L);
        assignment.setDesc("TEST");
        return assignment;
    }

    public static Assignment getForUpdateAssignment() {
        Assignment assignment = new Assignment();
        assignment.setStatus(AssignmentStatus.NEW);
        assignment.setId(1L);
        assignment.setVersion(1);
        assignment.setTemplateId(1L);
        assignment.setUserId(1L);
        assignment.setDesc("TEST");
        return assignment;
    }

    public static AssignmentAttribute getNewAssignmentAttribute() {

        AssignmentAttribute assignmentAttribute = new AssignmentAttribute();
        assignmentAttribute.setId(1L);
        assignmentAttribute.setDesc("ComissionCalculatorImplTest Attribute 1");
        assignmentAttribute.setEditable(true);
        assignmentAttribute.setRequired(true);
        assignmentAttribute.setAssignmentId(1L);
        assignmentAttribute.setValue("ComissionCalculatorImplTest ComissionCalculatorImplTest ComissionCalculatorImplTest");
        assignmentAttribute.setValueType(ValueType.TEXT);

        return assignmentAttribute;
    }

    public static AssignmentTask getNewAssignmentTask() {
        AssignmentTask assignmentTask = new AssignmentTask();
        assignmentTask.setId(1L);
        assignmentTask.setVersion(1);
        assignmentTask.setDesc("ComissionCalculatorImplTest task 1");
        return assignmentTask;
    }

    public static AssignmentTaskControl getNewAssignmentTaskControl() {
        AssignmentTaskControl assignmentTaskControl = new AssignmentTaskControl();
        assignmentTaskControl.setId(1L);
        assignmentTaskControl.setDesc("ComissionCalculatorImplTest control 1");
        assignmentTaskControl.setValueType(ValueType.TEXT);
        assignmentTaskControl.setValue("Test value");
        return assignmentTaskControl;
    }


    public static UpdateDataDto getUpdateDataDto() {
        UpdateDataDto dataDto = new UpdateDataDto();
        Assignment assignment = new Assignment();
        assignment.setStatus(AssignmentStatus.NEW);
        List<Assignment> assignmentList = new ArrayList<>();
        Set<AssignmentAttribute> assignmentAttributes = new HashSet<>();
        Set<AssignmentTask> assignmentTasks = new HashSet<>();
        Set<AssignmentTaskControl> assignmentTaskControls = new HashSet<>();
        assignment.setId(1L);
        assignment.setVersion(1);
        assignment.setTemplateId(1L);
        assignment.setUserId(1L);
        assignment.setDesc("TEST");
        assignment.setLastChange(LocalDate.now().toEpochDay());

        AssignmentAttribute assignmentAttribute = getNewAssignmentAttribute();
        assignmentAttributes.add(assignmentAttribute);
        assignment.setAttributes(assignmentAttributes);

        AssignmentTask newAssignmentTask = getNewAssignmentTask();
        AssignmentTaskControl newAssignmentTaskControl = getNewAssignmentTaskControl();

        assignmentTaskControls.add(newAssignmentTaskControl);
        newAssignmentTask.setControls(assignmentTaskControls);

        assignmentTasks.add(newAssignmentTask);
        assignment.setTasks(assignmentTasks);

        assignmentList.add(assignment);
        dataDto.setAssignments(assignmentList);

        return dataDto;
    }

    public static UpdateDataDto getUpdateDataDtoTransaction() {
        UpdateDataDto dataDto = new UpdateDataDto();
        Assignment assignment = new Assignment();
        assignment.setStatus(AssignmentStatus.COMPLETE);
        List<Assignment> assignmentList = new ArrayList<>();
        Set<AssignmentAttribute> assignmentAttributes = new HashSet<>();
        Set<AssignmentTask> assignmentTasks = new HashSet<>();
        Set<AssignmentTaskControl> assignmentTaskControls = new HashSet<>();
        assignment.setId(1L);
        assignment.setVersion(1);
        assignment.setTemplateId(3L);
        assignment.setUserId(1L);
        assignment.setDesc("TEST");
        assignment.setLastChange(LocalDate.now().toEpochDay());

        AssignmentAttribute assignmentAttribute = getNewAssignmentAttribute();
        assignmentAttributes.add(assignmentAttribute);
        assignment.setAttributes(assignmentAttributes);

        AssignmentTask newAssignmentTask = getNewAssignmentTask();
        AssignmentTaskControl newAssignmentTaskControl = getNewAssignmentTaskControl();

        assignmentTaskControls.add(newAssignmentTaskControl);
        newAssignmentTask.setControls(assignmentTaskControls);

        assignmentTasks.add(newAssignmentTask);
        assignment.setTasks(assignmentTasks);

        assignmentList.add(assignment);
        dataDto.setAssignments(assignmentList);

        return dataDto;
    }

    public static UpdateDataDto getUpdateDataDtoForFullRegistrationFull() {
        UpdateDataDto dataDto = new UpdateDataDto();
        Assignment assignment = new Assignment();
        assignment.setStatus(AssignmentStatus.COMPLETE);
        List<Assignment> assignmentList = new ArrayList<>();
        Set<AssignmentAttribute> assignmentAttributes = new HashSet<>();
        Set<AssignmentTask> assignmentTasks = new HashSet<>();
        Set<AssignmentTaskControl> assignmentTaskControls = new HashSet<>();
        assignment.setId(1L);
        assignment.setVersion(1);
        assignment.setTemplateId(1L);
        assignment.setUserId(1L);
        assignment.setDesc("full registration description");
        assignment.setLastChange(LocalDate.now().toEpochDay());
        assignment.setStatus(AssignmentStatus.COMPLETE);

        AssignmentAttribute assignmentAttribute = getNewAssignmentAttribute();
        assignmentAttributes.add(assignmentAttribute);
        assignment.setAttributes(assignmentAttributes);

        AssignmentTask newAssignmentTask = getNewAssignmentTask();
        AssignmentTaskControl newAssignmentTaskControl = getNewAssignmentTaskControl();

        assignmentTaskControls.add(newAssignmentTaskControl);
        newAssignmentTask.setControls(assignmentTaskControls);

        assignmentTasks.add(newAssignmentTask);
        assignment.setTasks(assignmentTasks);

        assignmentList.add(assignment);
        dataDto.setAssignments(assignmentList);

        return dataDto;
    }

    public static Object getUpdateDevice() {
        Device device = new Device();
        device.setId("123132123123123");
        device.setGoogleId("2313123131312312");
        device.setLastChange(LocalDate.now().toEpochDay());
        return device;
    }


    public static Call getNewCall() {
        return new Call("+380441234567", "Tester calls", 60, new Date(), "ComissionCalculatorImplTest type", LocalDate.now().toEpochDay(), "d6190a7e8aea17ad", 1L, 1L);
    }

    public static Location getNewTestLocation() {
        return new Location(36.00, 28.00, new Date(), "d6190a7e8aea17ad", 1L);
    }

    public static Activity getNewTestActivity() {
        return new Activity("d6190a7e8aea17ad", 1L, new Date(), "com.test.ditanceSales", 100L);
    }

    public static Template getNewTestTemplate() {
        return new Template("test template", "template POST test",4L);
    }

    public static TemplateAttribute getNewTestTemplateAttribute() {
        return new TemplateAttribute("new attribute", ValueType.ADDRESS, null, false, true, 1L);
    }

    public static TemplateTask getNewTemplateTask() {
        return new TemplateTask("new TemplateTask", true, 5, 1L);
    }

    public static TemplateTaskControl getNewTemplateTaskControl() {
        return new TemplateTaskControl("new Desc", ValueType.ADDRESS, "by test", true, 5, 1L);
    }

    public static Reason getNewReasoon() {
        return new Reason(null, "test reason", "test desc");
    }

    public static List<Call> getNewCallList() {
        List<Call> calls = new ArrayList<>();
        calls.add(getNewCall());
        calls.add(getNewCall());
        return calls;
    }

    public static List<Activity> getNewActivityList() {
        List<Activity> activities = new ArrayList<>();
        activities.add(getNewTestActivity());
        activities.add(getNewTestActivity());
        return activities;
    }


    public static User getForUpdateUser(){
        User user = new User();
        user.setId(1L);
        user.setLogin("user1");
        user.setUserPersonal(new UserPersonal(user.getId(),"edd8279b8ebe50c5652ff42e32c3561dd6f85e93"));
        user.setRole(UserRoles.ADMIN);
        user.setEmail("test@test.com");
        user.setFirstName("ComissionCalculatorImplTest");
        user.setLastName("ComissionCalculatorImplTest");
        return user;
    }

    public static ChangePasswordDto getChangePasswordDto() {
        return new ChangePasswordDto("user1", "edd8279b8ebe50c5652ff42e32c3561dd6f85e93", "ValidPwd");
    }

    public static User getNewTestUser(){
        User user = new User();
        user.setLogin("user_test");
        user.setUserPersonal(new UserPersonal(user.getId(),"test"));
        user.setRole(UserRoles.ADMIN);
        user.setEmail("test@test.com");
        user.setFirstName("ComissionCalculatorImplTest");
        user.setLastName("ComissionCalculatorImplTest");
        return user;
    }

    public static OnBoarding getOnBoardPossitivePng() throws IOException {
        String pngFileName = "mAgent.png";
        byte[] imageBody = Files.readAllBytes(Paths.get(URI.create(String.valueOf(Thread.currentThread().getContextClassLoader().getResource("testimages/mAgent.png")))));
        String desc = "mAgent onboarding test";
        String content = "onboard content test";
        return new OnBoarding(imageBody, content, pngFileName, desc);
    }
    public static OnBoarding getOnBoardWrongImage() throws IOException {
        String pngFileName = "testimage.jpg";
        byte[] imageBody = Files.readAllBytes(Paths.get(URI.create(String.valueOf(Thread.currentThread().getContextClassLoader().getResource("testimages/testimage.png")))));
        String desc = "onboard test";
        String content = "onboard content";
        return new OnBoarding(imageBody, content, pngFileName, desc);
    }

    public static OnBoarding getOnBoardPositiveSVG() throws IOException {
        String svgFileName = "positiveSvg.svg";
        byte[] imageBody = Files.readAllBytes(Paths.get(URI.create(String.valueOf(Thread.currentThread().getContextClassLoader().getResource("testimages/positiveSvg.svg")))));
        String desc = "onboard test";
        String content = "onboard content";
        return new OnBoarding(imageBody, content, svgFileName, desc);
    }

    public static OnBoarding getOnBoardPositiveSVGWithNullFields() throws IOException {
        String svgFileName = "positiveSvg.svg";
        byte[] imageBody = Files.readAllBytes(Paths.get(URI.create(String.valueOf(Thread.currentThread().getContextClassLoader().getResource("testimages/positiveSvg.svg")))));
        return new OnBoarding(imageBody,svgFileName);
    }

    public static OnBoarding getOnBoardNegativeSVG() throws IOException {
        String pngFileName = "svgimage.svg";
        byte[] imageBody = Files.readAllBytes(Paths.get(URI.create(String.valueOf(Thread.currentThread().getContextClassLoader().getResource("testimages/svgimage.svg")))));
        String desc = "onboard test";
        String content = "onboard content";
        return new OnBoarding(imageBody, content, pngFileName, desc);
    }

    public static TemporaryUser generateUserForRegistration(){
        return new TemporaryUser("device","test@gmail.com","Tester","Testov","+380978090838",SecurityUtils.hashPassword("pass"));
    }

    public static TemplateType generateTestTemplateType(){
        return new TemplateType("test",Arrays.asList(UserRoles.ADMIN));
    }

    public static TemplateType generateTestTemplateTypeNegative(){
        return new TemplateType("test",Arrays.asList());
    }

    public static TemplateType generateChildTemplateType(Long parentId){
        return new TemplateType("child",parentId,Arrays.asList(UserRoles.ADMIN));
    }

}
