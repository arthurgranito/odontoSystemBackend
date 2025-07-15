package com.system.odonto.repository;

import com.system.odonto.model.Paciente;
import com.system.odonto.model.Dentista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    List<Paciente> findByDentista(Dentista dentista);
} 