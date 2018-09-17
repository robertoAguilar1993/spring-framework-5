package com.beto.springbootdatajpa.dao;

import com.beto.springbootdatajpa.entity.Cliente;
import org.springframework.data.repository.PagingAndSortingRepository;


/**
 * TODO capa dao para las opereciones del cliente
 */
public interface ClienteDao extends PagingAndSortingRepository<Cliente, Long>{


}
