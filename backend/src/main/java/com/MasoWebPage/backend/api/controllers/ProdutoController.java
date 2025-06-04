package com.MasoWebPage.backend.api.controllers;

import com.MasoWebPage.backend.api.dto.ProdutoDTO;
import com.MasoWebPage.backend.models.Produto;
import com.MasoWebPage.backend.services.ProdutoService;
import com.MasoWebPage.backend.utils.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<Page<ProdutoDTO>> buscaTodos(@PageableDefault(size = 10) Pageable paginacao){
        Page<Produto> produtos = produtoService.buscaTodos(paginacao);
        return ResponseEntity.ok( produtos.map(ProdutoDTO::new));
    }

    @GetMapping("{id}")
    public ResponseEntity<ProdutoDTO> buscaPorId(@PathVariable String id){
         return ResponseEntity.ok(new ProdutoDTO(produtoService.buscaPorId(id)));
    }
}
