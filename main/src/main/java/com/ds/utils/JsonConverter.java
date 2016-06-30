package com.ds.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import org.apache.log4j.Logger;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

/**
 * @author artomov.ihor
 *         Json lazy for hibernate
 */
@Service
public class JsonConverter<T> {
    private final Logger LOGGER=Logger.getLogger(JsonConverter.class);
    private ObjectMapper mapperWithModule(boolean forceLazyLoading) {
        return new ObjectMapper().registerModule(hibernateModule(forceLazyLoading));
    }

    private Hibernate4Module hibernateModule(boolean forceLazyLoading) {
        Hibernate4Module mod = new Hibernate4Module();
        mod.configure(Hibernate4Module.Feature.FORCE_LAZY_LOADING, forceLazyLoading);
        return mod;
    }

    private ObjectMapper mapper = mapperWithModule(false);

    /**
     *
     * @param value object which will be converted to Json without LazyInit exception
     * @return Json as String
     * @throws JsonProcessingException
     */
    public String getJsonAsStringRepresent(T value) throws JsonProcessingException {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
    }

    /**
     *
     * @param value object which will be converted to Json without LazyInit exception
     * @param modelClass class to reconvert again
     * @return
     * @throws IOException
     */
    @Transactional
    public T convert(T value, Class modelClass) {
        try {
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
            return (T) mapper.readValue(json, modelClass);
        }catch (Exception e){
            LOGGER.warn("exception in convert "+e);
            return null;
        }
    }

    public List<T> convertList(T value)  {
        try {
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
            JSONParser parser = new JSONParser();
            return (List<T>) parser.parse(json);
        }catch (Exception e){
            LOGGER.warn("exception in convertList "+e);
            return null;
        }
    }


}