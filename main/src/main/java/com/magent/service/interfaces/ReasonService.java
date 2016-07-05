package com.magent.service.interfaces;

import com.magent.domain.Reason;

import java.util.List;

/**
 * @since 29/04/2016
 * Service for specific requests
 */
public interface ReasonService {
    /**
     * @param id parent_id from ds_reason see db and Reason.class
     * @return collection of Reason
     * @see Reason, ReasonRepository
     */
    List<Reason> getListByParentId(Long id);
}
