package com.system.odonto.repository;

import com.system.odonto.model.TipoConsulta;
import com.system.odonto.model.Dentista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoConsultaRepository extends JpaRepository<TipoConsulta, Long> {
    List<TipoConsulta> findByDentista(Dentista dentista);
} 