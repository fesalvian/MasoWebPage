package com.MasoWebPage.backend.services;

import com.MasoWebPage.backend.models.Produto;
import com.MasoWebPage.backend.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto cadastrar(Produto produto){
        System.out.println(produto.getUrlsImagens());
        if (produto.getUrlsImagens() == null) produto.setUrlsImagens(new ArrayList<>());
        return produtoRepository.save(produto);
    }


    public Produto atualizar(Produto produto, String id) {
        Optional<Produto> opt = produtoRepository.findById(id);
        if(opt.isPresent()){

            Produto produtoCarregado = opt.get();
            produtoCarregado.atualiza(produto);//pensar numa forma de melhorar tal atualizacao
            return produtoRepository.save(produtoCarregado);
        }else{
            throw new RuntimeException("produto nao encontrado");
        }
    }

    public Page<Produto> buscaTodos(Pageable paginacao) {
        return produtoRepository.findAll(paginacao);
    }

    public void deletar(String id) {
        produtoRepository.deleteById(id);
    }

    public Produto buscaPorId(String id) {
        return produtoRepository.findById(id).get();
    }
}
