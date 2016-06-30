package com.ds.service;

import com.ds.config.ServiceConfig;
import com.ds.domain.Reason;
import com.ds.service.interfaces.GeneralService;
import com.ds.service.interfaces.ReasonService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * ReasonServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>????. 18, 2016</pre>
 */
public class ReasonServiceImplTest extends ServiceConfig {

    @Autowired
    private ReasonService reasonService;
    @Autowired
    @Qualifier("reasonGeneralService")
    private GeneralService reasonGenService;
    private Long parentId = 1L;
    private String descEspected ;

    /**
     * Method: getListByParentId(Long id)
     */
    @Test
    public void testGetListByParentId() throws Exception {
        Reason reason= (Reason) reasonGenService.getById(3L);
        descEspected=reason.getDesc();

        Assert.assertNotNull(reasonService.getListByParentId(parentId));
        Assert.assertTrue(reasonService.getListByParentId(parentId).size()==1);
        Assert.assertSame(reasonService.getListByParentId(parentId).get(0).getParentId(),parentId);
        Assert.assertEquals("checking description ",reasonService.getListByParentId(parentId).get(0).getDesc(),descEspected );
    }


} 
