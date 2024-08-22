package com.MasoWebPage.backend.api.dto;

public record ProdutoDTO(

        Long id,
        String cor,
        String nome,
        String urlImagem
) {
        public ProdutoDTO(Long id, String cor, String nome, String urlImagem){
            this.id = id;
            this.cor = cor;
            this.nome = nome;
            this.urlImagem = urlImagem;
        }
}

