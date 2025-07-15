package com.system.odonto.repository;

import com.system.odonto.model.Consulta;
import com.system.odonto.model.Dentista;
import com.system.odonto.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    List<Consulta> findByDentista(Dentista dentista);
    List<Consulta> findByPaciente(Paciente paciente);
    List<Consulta> findByDentistaAndStatus(Dentista dentista, String status);
    List<Consulta> findByDentistaAndDataHoraInicioBetween(Dentista dentista, LocalDateTime inicio, LocalDateTime fim);
    List<Consulta> findByDentistaAndStatusAndDataHoraInicioBetween(Dentista dentista, String status, LocalDateTime inicio, LocalDateTime fim);
} 