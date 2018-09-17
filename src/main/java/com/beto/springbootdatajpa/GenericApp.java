package com.beto.springbootdatajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestParam;

@PropertySource("classpath:cliente.properties")
public abstract class GenericApp {

    @Autowired
    protected Environment env;

    public boolean validateObject( Object obj){
        return obj != null;
    }

    public String getPropertyValue(@RequestParam("key") String key)
    {
        String returnValue = "No value";

        String keyValue = env.getProperty(key);

        if( validateString(keyValue))
        {
            returnValue = keyValue;
        }
        return returnValue;
    }

    public boolean validateString(String obj){
        return obj!= null && !obj.isEmpty();
    }

}
