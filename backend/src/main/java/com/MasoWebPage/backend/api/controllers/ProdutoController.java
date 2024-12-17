package com.MasoWebPage.backend.api.controllers;

import com.MasoWebPage.backend.api.dto.ProdutoDTO;
import com.MasoWebPage.backend.models.Produto;
import com.MasoWebPage.backend.services.CustomUserDetailsService;
import com.MasoWebPage.backend.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private CustomUserDetailsService detailsService;


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
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> atualizar(@RequestBody Produto produto,@PathVariable String id) {

        if (detailsService.isADM()) {
            Produto produtoCadastrado = produtoService.atualizar(produto, id);

            return ResponseEntity.ok(new ProdutoDTO(produto));
        }else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping
    public ResponseEntity<Page<ProdutoDTO>> buscaTodos(@PageableDefault(size = 10) Pageable paginacao){
        Page<Produto> produtos = produtoService.buscaTodos(paginacao);
        return ResponseEntity.ok( produtos.map(ProdutoDTO::new));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id){
        if(detailsService.isADM()){
            produtoService.deletar(id);
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/oi")
    public ResponseEntity<String> oi(){
        if (detailsService.isADM()) {

            return ResponseEntity.ok("oi adm");
        }else{
            return ResponseEntity.ok("oi user comum");
        }
    }

}
