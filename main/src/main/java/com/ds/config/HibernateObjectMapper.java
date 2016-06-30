package com.ds.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

/**
 * @apiNote hibernate object mapper instead of standart spring mapper 
 */
public class HibernateObjectMapper extends ObjectMapper {
    public HibernateObjectMapper() {
        registerModule(new Hibernate4Module());
    }
}
