package com.beto.springbootdatajpa.service;

import com.beto.springbootdatajpa.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IClienteService {
    public List<Cliente> findAll();
    public Page<Cliente> findAll(Pageable pageable);
    public Optional<Cliente> findById(Long id);
    public void save(Cliente cliente);
    public void delete(Long id);
}
