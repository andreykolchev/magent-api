package com.ds.service;

import com.ds.domain.Reason;
import com.ds.repository.ReasonRepository;
import com.ds.service.interfaces.ReasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author artomov.ihor
 * @since 29/04/2016
 */
@Service
@Transactional(readOnly = true)
public class ReasonServiceImpl implements ReasonService {
    @Autowired
    ReasonRepository reasonRepository;

    @Override
    public List<Reason> getListByParentId(Long id) {
        return reasonRepository.getListByParentId(id);
    }
}
