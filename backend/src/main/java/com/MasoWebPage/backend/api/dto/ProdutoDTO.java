package com.MasoWebPage.backend.api.dto;

import com.MasoWebPage.backend.models.Produto;
import java.util.List;

public record ProdutoDTO(

        String id,
        String nome,
        String descricao,
        String ambiente,
        String capaUrl,
        List<String> imagensUrls,
        List<String> tags,
        List<String> coresSelecionadas

) {

    public ProdutoDTO(Produto produto){
        this(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getAmbiente(),
                produto.getCapaUrl(),
                produto.getImagensUrls(),
                produto.getTags(),
                produto.getCoresSelecionadas()
        );
    }
}
