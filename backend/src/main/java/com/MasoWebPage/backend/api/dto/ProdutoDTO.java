package com.MasoWebPage.backend.api.dto;

import com.MasoWebPage.backend.models.Produto;

import java.util.List;

public record ProdutoDTO(


        String id,
        String nome,
        String descricao,
        String colecao,

        List<String> urlsImagens
) {
        public ProdutoDTO(Produto produto){
          this(produto.getId(), produto.getNome(), produto.getDescricao(), produto.getColecao(), produto.getUrlsImagens());
        }


}

