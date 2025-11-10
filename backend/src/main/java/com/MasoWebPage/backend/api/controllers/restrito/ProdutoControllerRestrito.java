package com.MasoWebPage.backend.api.controllers.restrito;

import com.MasoWebPage.backend.api.dto.ProdutoDTO;
import com.MasoWebPage.backend.models.Produto;
import com.MasoWebPage.backend.services.ProdutoService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
    @RequestMapping("/restrito/produto")
@PreAuthorize("@AuthUtil.isADM()")
public class ProdutoControllerRestrito {

    @Autowired
    private ProdutoService produtoService;


    @PostMapping
    public ResponseEntity<ProdutoDTO> cadastrar(@RequestBody ProdutoDTO produto, UriComponentsBuilder uriBuilder) {

        System.out.println(produto.urlsImagens());

        Produto produtoCadastrado = produtoService.cadastrar(new Produto(produto));
        var uri = uriBuilder.path("/produto/{id}").buildAndExpand(produtoCadastrado.getId()).toUri();

        return ResponseEntity.created(uri).body(new ProdutoDTO(produtoCadastrado));

    }

    @PutMapping
    public ResponseEntity<ProdutoDTO> atualizar(@RequestBody Produto produto, @PathParam("id") String id) {


        Produto produtoCadastrado = produtoService.atualizar(produto, id);
        return ResponseEntity.ok(new ProdutoDTO(produtoCadastrado));

    }


    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id) {

        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
