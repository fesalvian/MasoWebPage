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

    @Autowired
    private AuthUtil authUtil;



    @PostMapping
    @PreAuthorize("@AuthUtil.isADM()")
    public ResponseEntity<ProdutoDTO> cadastrar(@RequestBody Produto produto,  UriComponentsBuilder uriBuilder) {


            Produto produtoCadastrado = produtoService.cadastrar(produto);
            var uri = uriBuilder.path("/produto/{id}").buildAndExpand(produtoCadastrado.getId()).toUri();

            return ResponseEntity.created(uri).body(new ProdutoDTO(produto));

    }
    @PutMapping("/{id}")
    @PreAuthorize("@AuthUtil.isADM()")
    public ResponseEntity<ProdutoDTO> atualizar(@RequestBody Produto produto,@PathVariable String id) {

            Produto produtoCadastrado = produtoService.atualizar(produto, id);
            return ResponseEntity.ok(new ProdutoDTO(produto));

    }

    @GetMapping
    public ResponseEntity<Page<ProdutoDTO>> buscaTodos(@PageableDefault(size = 10) Pageable paginacao){
        Page<Produto> produtos = produtoService.buscaTodos(paginacao);
        return ResponseEntity.ok( produtos.map(ProdutoDTO::new));
    }

    @GetMapping("{id}")
    public ResponseEntity<ProdutoDTO> buscaPorId(@PathVariable String id){
         return ResponseEntity.ok(new ProdutoDTO(produtoService.buscaPorId(id)));
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("@AuthUtil.isADM()")
    public ResponseEntity delete(@PathVariable String id){
            //produtoService.deletar(id);

            return ResponseEntity.ok(id);
    }


}
