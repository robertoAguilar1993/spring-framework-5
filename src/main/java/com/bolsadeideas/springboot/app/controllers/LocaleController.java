package com.bolsadeideas.springboot.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LocaleController {

    @GetMapping("/locale")
    public String locale(HttpServletRequest request){
        String ultimaUri = request.getHeader("referer");
        return "redirect:".concat(ultimaUri);
    }
}
