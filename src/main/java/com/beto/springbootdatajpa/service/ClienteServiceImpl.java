package com.beto.springbootdatajpa.service;

import com.beto.springbootdatajpa.dao.ClienteDao;
import com.beto.springbootdatajpa.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements IClienteService{

    @Autowired
    private ClienteDao clienteDao;

    @Transactional(readOnly = true)
    @Override
    public List<Cliente> findAll() {
        return (List<Cliente>) clienteDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Cliente> findAll(Pageable pageable) {
        return  clienteDao.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Cliente> findById(Long id) {
        return clienteDao.findById(id);
    }

    @Transactional
    @Override
    public void save(Cliente cliente) {
        clienteDao.save(cliente);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        clienteDao.deleteById(id);
    }


}
