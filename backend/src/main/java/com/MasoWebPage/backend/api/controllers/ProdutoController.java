package com.MasoWebPage.backend.api.controllers;

import com.MasoWebPage.backend.api.dto.ProdutoDTO;
import com.MasoWebPage.backend.models.Produto;
import com.MasoWebPage.backend.services.CustomUserDetailsService;
import com.MasoWebPage.backend.services.ProdutoService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private CustomUserDetailsService detailsService;

    @SneakyThrows
    @PostMapping
    public ResponseEntity<ProdutoDTO> cadastrar(@RequestBody Produto produto,  UriComponentsBuilder uriBuilder) {

        if (detailsService.isADM()) {
            Produto produtoCadastrado = produtoService.cadastrar(produto);
            var uri = uriBuilder.path("/produto/{id}").buildAndExpand(produtoCadastrado.getId()).toUri();

            return ResponseEntity.created(uri).body(new ProdutoDTO(produto));
        }else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }



}
