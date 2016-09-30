package com.magent.servicemodule.service.impl;

import com.magent.domain.Reason;
import com.magent.repository.ReasonRepository;
import com.magent.servicemodule.service.interfaces.ReasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author artomov.ihor
 * @since 29/04/2016
 * service for Reason operations
 */
@Service
@Transactional(readOnly = true)
class ReasonServiceImpl implements ReasonService {
    @Autowired
    ReasonRepository reasonRepository;

    /**
     *
     * @param id parent_id from ds_reason see db and Reason.class
     * @return list of Reason
     */
    @Override
    public List<Reason> getListByParentId(Long id) {
        return reasonRepository.getListByParentId(id);
    }
}
