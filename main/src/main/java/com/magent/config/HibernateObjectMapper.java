package com.magent.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

/**
 * hibernate object mapper instead of standard spring mapper.
 * It allows don't get exception for lazy objects
 * see https://github.com/FasterXML/jackson-datatype-hibernate
 */
public class HibernateObjectMapper extends ObjectMapper {

    public HibernateObjectMapper() {
        registerModule(new Hibernate4Module());
    }
}
