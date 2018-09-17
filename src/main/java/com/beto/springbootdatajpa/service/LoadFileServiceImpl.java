package com.beto.springbootdatajpa.service;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class LoadFileServiceImpl extends GenericService implements  ILoadFileService{
    @Override
    public Resource load(String filename) {
        return null;
    }

    @Override
    public String copy(MultipartFile file) {


        return null;
    }

    @Override
    public boolean delete(String filename) {
        return false;
    }


}
