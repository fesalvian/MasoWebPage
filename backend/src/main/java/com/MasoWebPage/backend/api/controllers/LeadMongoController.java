package com.MasoWebPage.backend.api.controllers;

import com.MasoWebPage.backend.models.LeadMongo;
import com.MasoWebPage.backend.repositories.LeadRepositoryMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lm")
public class LeadMongoController {

    @Autowired
    private LeadRepositoryMongo repositoryMongo;

    @GetMapping
    public List<LeadMongo> buscaTodos(){
        return  repositoryMongo.findAll();
    }
    @GetMapping("/a")
    public String busca(){
        return  "ola";
    }


    @PostMapping
    public void salva(@RequestBody LeadMongo leadMongo){
        repositoryMongo.save(leadMongo);
    }
}
