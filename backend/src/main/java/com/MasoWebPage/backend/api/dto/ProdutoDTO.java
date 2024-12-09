package com.MasoWebPage.backend.api.dto;

import com.MasoWebPage.backend.models.Produto;

public record ProdutoDTO(


        String cor,
        String nome,
        String urlImagem
) {
        public ProdutoDTO(Produto produto){
          this(produto.getCor(), produto.getNome(), produto.getUrlImagem());
        }


}

