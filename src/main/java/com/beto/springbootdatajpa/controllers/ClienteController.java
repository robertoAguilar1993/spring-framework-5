package com.beto.springbootdatajpa.controllers;

import com.beto.springbootdatajpa.domain.ClienteProperties;
import com.beto.springbootdatajpa.entity.Cliente;
import com.beto.springbootdatajpa.service.IClienteService;
import com.beto.springbootdatajpa.util.paginator.PageRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;

@Controller
public class ClienteController extends  GenericController{

    @Autowired
    private IClienteService clienteService;


    @RequestMapping(value = "/{id}/cliente")
    public String verCliente(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash){
        Optional<Cliente> cliente = clienteService.findById(id);
        if(!validateObject(cliente)){
            flash.addFlashAttribute(getPropertyValue(ClienteProperties.CLIENTE_ERROR.getValue()),
                    "El cliente no existe en la base de datos");
            return getPropertyValue(ClienteProperties.CLIENTE_REDIRECT_CLIENTES.getValue());
        }
        model.put(getPropertyValue(ClienteProperties.CLIENTE_CLIENTE.getValue()), cliente);
        model.put(getPropertyValue(ClienteProperties.CLIENTE_TITULO.getValue()), "Detalle cliente: " + cliente.get().getNombre());

        return "clienteDetalle";
    }

    @RequestMapping(value = "/clientes", method = RequestMethod.GET)
    public String listar(@RequestParam(name = "page", defaultValue = "0")int page, Model model){
        Pageable pageRequest =  PageRequest.of(page,4);
        Page<Cliente> clientes = clienteService.findAll(pageRequest);
        PageRender<Cliente> pagesRender = new PageRender<>("/clientes",clientes);
        model.addAttribute(getPropertyValue(ClienteProperties.CLIENTE_TITULO.getValue()), "Listados de Cliente");
        model.addAttribute("clientes", clientes);
        model.addAttribute("page", pagesRender);
        return getPropertyValue(ClienteProperties.CLIENTE_CLIENTE.getValue());
    }
    @RequestMapping(value = "/clienteForm", method = RequestMethod.GET)
    public String crear (Map<String, Object> model){
        Cliente cliente = new Cliente();
        model.put(getPropertyValue(ClienteProperties.CLIENTE_TITULO.getValue()),
                getPropertyValue(ClienteProperties.CLIENTE_FORMULARIO_CLIENTE.getValue()));
        model.put(getPropertyValue(ClienteProperties.CLIENTE_CLIENTE.getValue()), cliente);
        return getPropertyValue(ClienteProperties.CLIENTE_REDIRECT_CLINETE_FORM.getValue());
    }
    @RequestMapping(value = "/cliente", method = RequestMethod.POST)
    public String guardar(@Valid Cliente cliente, BindingResult result, @RequestParam("file")MultipartFile foto,
                          RedirectAttributes flash, Model model){
        if(result.hasErrors()){
            model.addAttribute(getPropertyValue(ClienteProperties.CLIENTE_TITULO.getValue()),
                    getPropertyValue(ClienteProperties.CLIENTE_FORMULARIO_CLIENTE.getValue()));
            return getPropertyValue(ClienteProperties.CLIENTE_REDIRECT_CLINETE_FORM.getValue());
        }
        if(!foto.isEmpty()){
            if(cliente.getId() != null && cliente.getId() > 0
                    && cliente.getFoto() != null && cliente.getFoto().length()>0){

                Path rootPatch= Paths.get(
                        getPropertyValue(ClienteProperties.CLIENTE_ROOTPATH.getValue())+"/"+cliente.getFoto());
                File archivo = rootPatch.toFile();
                if(archivo !=null && isFileOrDirectory(archivo)){
                    cleanUp(rootPatch);
                }
            }
            try {
                byte[] bytes=foto.getBytes();
                Path rutaCompleta = Paths.get(getPropertyValue(ClienteProperties.CLIENTE_ROOTPATH.getValue())+"//"+
                        foto.getOriginalFilename());
                Files.write(rutaCompleta, bytes);
                flash.addFlashAttribute(getPropertyValue(ClienteProperties.CLIENTE_INFO.getValue()),
                        "Has subido correctamente '"+ foto.getOriginalFilename()+"'");
                cliente.setFoto(foto.getOriginalFilename());
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        String mensajeFlash = (cliente.getId() != null) ? "Cliente editado con exito":"Cliente creado con exito";
        clienteService.save(cliente);
        flash.addFlashAttribute(getPropertyValue(ClienteProperties.CLIENTE_SUCCESS.getValue()),mensajeFlash);
        return getPropertyValue(ClienteProperties.CLIENTE_REDIRECT_CLIENTES.getValue());
    }

    @RequestMapping(value = "/clienteForm/{id}", method = RequestMethod.GET)
    public String editar(@PathVariable(value = "id")Long id,RedirectAttributes flash, Map<String, Object> model){
        Optional<Cliente> cliente;
        if(id > 0){
            cliente = clienteService.findById(id);
            if(!validateObject(cliente)){
                flash.addFlashAttribute(getPropertyValue(ClienteProperties.CLIENTE_ERROR.getValue()),
                        "El id del cliente no existe en la base de datos");
                return getPropertyValue(ClienteProperties.CLIENTE_REDIRECT_CLIENTES.getValue());
            }
        }else{
            flash.addFlashAttribute(getPropertyValue(ClienteProperties.CLIENTE_ERROR.getValue()),
                    "El id del cliente no puede ser 0");
            return getPropertyValue(ClienteProperties.CLIENTE_REDIRECT_CLIENTES.getValue());
        }
        model.put(getPropertyValue(ClienteProperties.CLIENTE_TITULO.getValue()),
                getPropertyValue(ClienteProperties.CLIENTE_FORMULARIO_CLIENTE.getValue()));
        model.put("cliente", cliente);
        return getPropertyValue(ClienteProperties.CLIENTE_REDIRECT_CLINETE_FORM.getValue());
    }

    @RequestMapping(value = "/cliente/{id}", method = RequestMethod.GET)
    public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash){
        if(id > 0){
            Optional<Cliente> cliente = clienteService.findById(id);
            clienteService.delete(id);
            flash.addFlashAttribute(getPropertyValue(ClienteProperties.CLIENTE_SUCCESS.getValue()),
                    "Cliente eliminado con exito");
            Path rootPatch= Paths.get(getPropertyValue(ClienteProperties.CLIENTE_ROOTPATH.getValue())+
                    "/"+cliente.get().getFoto());
            File archivo = rootPatch.toFile();
            if(archivo !=null && isFileOrDirectory(archivo)){
                cleanUp(rootPatch);
                flash.addFlashAttribute(getPropertyValue(ClienteProperties.CLIENTE_INFO.getValue()),
                        "la foto se sue a la verga");
            }
        }

        return getPropertyValue(ClienteProperties.CLIENTE_REDIRECT_CLIENTES.getValue());
    }

    /**
     * @param file
     * @return
     */
     boolean isFileOrDirectory(File file){
        return file.exists() && file.canRead();
    }

}
