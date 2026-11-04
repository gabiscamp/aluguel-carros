package com.aluguelcarros.repository;

import com.aluguelcarros.model.Agente;
import com.aluguelcarros.model.enums.TipoAgente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgenteRepository extends JpaRepository<Agente, Long> {
    Optional<Agente> findByEmail(String email);
    Optional<Agente> findByCnpj(String cnpj);
    List<Agente> findByTipoAgente(TipoAgente tipoAgente);
    boolean existsByCnpj(String cnpj);
}
