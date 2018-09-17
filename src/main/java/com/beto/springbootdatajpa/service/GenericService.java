package com.beto.springbootdatajpa.service;

import com.beto.springbootdatajpa.GenericApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestParam;

@PropertySource("classpath:cliente.properties")
public class GenericService extends GenericApp {

    @Autowired
    protected Environment env;


    @Override
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

}
