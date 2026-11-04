package com.aluguelcarros.repository;

import com.aluguelcarros.model.Automovel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutomovelRepository extends JpaRepository<Automovel, Long> {
    List<Automovel> findByDisponivelTrue();
    Optional<Automovel> findByPlaca(String placa);
    Optional<Automovel> findByMatricula(String matricula);
}
