package com.renato.odonto.repository;

import com.renato.odonto.model.TipoConsulta;
import com.renato.odonto.model.Dentista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoConsultaRepository extends JpaRepository<TipoConsulta, Long> {
    List<TipoConsulta> findByDentista(Dentista dentista);
} 