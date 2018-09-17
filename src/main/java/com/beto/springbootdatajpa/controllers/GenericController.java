package com.beto.springbootdatajpa.controllers;

import com.beto.springbootdatajpa.GenericApp;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

import org.springframework.core.env.Environment;

@PropertySource("classpath:cliente.properties")
public abstract class GenericController extends GenericApp {

    @Autowired
    protected Environment env;

    private static final Logger LOG = Logger.getLogger(GenericController.class);



    public void cleanUp(Path path)  {
        try {
            Files.delete(path);
        }catch (NoSuchFileException e){
            LOG.info(e.getMessage());
        }catch (DirectoryNotEmptyException e1){
            LOG.info(e1.getMessage());
        }catch (IOException io){
            LOG.info(io.getMessage());
        }

    }

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
