package com.system.odonto.repository;

import com.system.odonto.model.EscalaPadrao;
import com.system.odonto.model.Dentista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EscalaPadraoRepository extends JpaRepository<EscalaPadrao, Long> {
    List<EscalaPadrao> findByDentista(Dentista dentista);
} 