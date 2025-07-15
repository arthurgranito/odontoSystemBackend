package com.system.odonto.repository;

import com.system.odonto.model.AgendaDiponivel;
import com.system.odonto.model.Dentista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AgendaDiponivelRepository extends JpaRepository<AgendaDiponivel, Long> {
    List<AgendaDiponivel> findByDentista(Dentista dentista);
    List<AgendaDiponivel> findByDentistaAndDataBetween(Dentista dentista, LocalDate dataInicio, LocalDate dataFim);
    List<AgendaDiponivel> findByDentistaAndDisponivelTrue(Dentista dentista);
} 