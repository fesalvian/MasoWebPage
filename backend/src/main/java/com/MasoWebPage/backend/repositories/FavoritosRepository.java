
package com.MasoWebPage.backend.repositories;

import com.MasoWebPage.backend.api.dto.ProdutoDTO;
import com.MasoWebPage.backend.models.Favoritos;
import com.MasoWebPage.backend.models.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public interface FavoritosRepository extends JpaRepository<Favoritos,Long> {

    @Query("SELECT new com.MasoWebPage.backend.models.Produto(p.id, p.nome, p.cor, p.url_imagem) " +
            "FROM Favoritos f JOIN f.produto p " +
            "WHERE f.lead.id = :idParametro")
    List<Produto> buscaProdutosFavoritos(@Param("idParametro") Long idParametro);
}
