package com.magent.servicemodule.service;

import com.magent.domain.TemplateType;
import com.magent.domain.enums.UserRoles;
import com.magent.servicemodule.config.ServiceModuleServiceConfig;
import com.magent.servicemodule.service.interfaces.GeneralService;
import com.magent.servicemodule.service.interfaces.TemplateTypeService;
import com.magent.servicemodule.utils.EntityGenerator;
import javassist.NotFoundException;
import javassist.tools.web.BadHttpRequest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;

/**
 * Created by artomov.ihor on 19.07.2016.
 */
public class TmpTypeServiceImplTest extends ServiceModuleServiceConfig {
    @Autowired
    private TemplateTypeService templateTypeService;
    @Autowired
    @Qualifier("templateTypeGeneralService")
    private GeneralService templateTypeGen;

    @Test
    @Sql("classpath:data.sql")
    public void testUpdate() throws NotFoundException, BadHttpRequest {
        TemplateType type = (TemplateType) templateTypeGen.save(EntityGenerator.generateTestTemplateType());
        type.setUserRolesList(Arrays.asList(UserRoles.ADMIN, UserRoles.BACK_OFFICE_EMPLOYEE));
        TemplateType res = templateTypeService.update(type, type.getId());
        Assert.assertEquals(2, res.getUserRolesList().size());
    }

    @Test(expected = NotFoundException.class)
    @Sql("classpath:data.sql")
    public void testDelete() throws NotFoundException {
        TemplateType type = (TemplateType) templateTypeGen.save(EntityGenerator.generateTestTemplateType());
        templateTypeGen.delete(type.getId());

        Assert.assertNull(templateTypeGen.getById(type.getId()));
    }
    @Test
    @Sql("classpath:data.sql")
    public void testChild() throws NotFoundException {
        TemplateType templateType = (TemplateType) templateTypeGen.save(EntityGenerator.generateTestTemplateType());
        templateTypeGen.save(EntityGenerator.generateChildTemplateType(templateType.getId()));
        TemplateType templateType1= (TemplateType) templateTypeGen.getById(templateType.getId());
        Assert.assertNotNull(templateType1.getChildTemplatesTypes());
        Assert.assertEquals(1,templateType1.getChildTemplatesTypes().size());
    }
}
