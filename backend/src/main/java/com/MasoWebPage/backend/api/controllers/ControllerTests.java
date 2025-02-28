package com.MasoWebPage.backend.api.controllers;

import com.MasoWebPage.backend.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tests")
public class ControllerTests {

    @Autowired
    private UsuarioService detailsService;


    @GetMapping
    public String testeUm(){
        return "hello world";
    }
}
