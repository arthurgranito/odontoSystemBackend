package com.renato.odonto.service;

import com.renato.odonto.model.Dentista;
import com.renato.odonto.model.Paciente;
import com.renato.odonto.repository.PacienteRepository;
import com.renato.odonto.shared.PacienteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;

    public List<PacienteDTO> listarTodos(Dentista dentista) {
        return pacienteRepository.findByDentista(dentista)
                .stream().map(PacienteDTO::from).collect(Collectors.toList());
    }

    public Optional<PacienteDTO> buscarPorId(Long id, Dentista dentista) {
        return pacienteRepository.findById(id)
                .filter(p -> p.getDentista().getId().equals(dentista.getId()))
                .map(PacienteDTO::from);
    }

    public PacienteDTO criar(PacienteDTO dto, Dentista dentista) {
        Paciente paciente = new Paciente();
        paciente.setNome(dto.nome());
        paciente.setTelefone(dto.telefone());
        paciente.setEmail(dto.email());
        paciente.setDataNascimento(dto.dataNascimento());
        paciente.setObservacoes(dto.observacoes());
        paciente.setDentista(dentista);
        return PacienteDTO.from(pacienteRepository.save(paciente));
    }

    public Optional<PacienteDTO> atualizar(Long id, PacienteDTO dto, Dentista dentista) {
        return pacienteRepository.findById(id)
                .filter(p -> p.getDentista().getId().equals(dentista.getId()))
                .map(paciente -> {
                    paciente.setNome(dto.nome());
                    paciente.setTelefone(dto.telefone());
                    paciente.setEmail(dto.email());
                    paciente.setDataNascimento(dto.dataNascimento());
                    paciente.setObservacoes(dto.observacoes());
                    return PacienteDTO.from(pacienteRepository.save(paciente));
                });
    }

    public boolean remover(Long id, Dentista dentista) {
        return pacienteRepository.findById(id)
                .filter(p -> p.getDentista().getId().equals(dentista.getId()))
                .map(p -> { pacienteRepository.delete(p); return true; })
                .orElse(false);
    }
} 