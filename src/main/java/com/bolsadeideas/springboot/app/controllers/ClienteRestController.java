package com.bolsadeideas.springboot.app.controllers;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.service.IClienteService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ClienteRestController {
    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private IClienteService clienteService;

    @RequestMapping(value = "/cliente", method = RequestMethod.GET)
    public List<Cliente> listar() {
        return clienteService.findAll();
    }

}
