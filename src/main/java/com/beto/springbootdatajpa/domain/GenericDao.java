package com.beto.springbootdatajpa.domain;


public abstract class GenericDao {

    public boolean validateObject( Object obj){
        return obj != null;
    }

}
