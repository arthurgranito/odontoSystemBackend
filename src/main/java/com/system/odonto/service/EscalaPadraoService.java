package com.system.odonto.service;

import com.system.odonto.model.Dentista;
import com.system.odonto.model.EscalaPadrao;
import com.system.odonto.repository.EscalaPadraoRepository;
import com.system.odonto.shared.EscalaPadraoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EscalaPadraoService {
    @Autowired
    private EscalaPadraoRepository escalaPadraoRepository;

    public List<EscalaPadraoDTO> listarTodos(Dentista dentista) {
        return escalaPadraoRepository.findByDentista(dentista)
                .stream().map(EscalaPadraoDTO::from).collect(Collectors.toList());
    }

    public Optional<EscalaPadraoDTO> buscarPorId(Long id, Dentista dentista) {
        return escalaPadraoRepository.findById(id)
                .filter(e -> e.getDentista().equals(dentista))
                .map(EscalaPadraoDTO::from);
    }

    public EscalaPadraoDTO criar(EscalaPadraoDTO dto, Dentista dentista) {
        EscalaPadrao escala = new EscalaPadrao();
        escala.setDiaSemana(dto.diaSemana());
        escala.setHoraInicio(dto.horaInicio());
        escala.setHoraFim(dto.horaFim());
        escala.setIntervaloMinutos(dto.intervaloMinutos());
        escala.setDentista(dentista);
        return EscalaPadraoDTO.from(escalaPadraoRepository.save(escala));
    }

    public Optional<EscalaPadraoDTO> atualizar(Long id, EscalaPadraoDTO dto, Dentista dentista) {
        return escalaPadraoRepository.findById(id)
                .filter(e -> e.getDentista().getId().equals(dentista.getId()))
                .map(escala -> {
                    escala.setDiaSemana(dto.diaSemana());
                    escala.setHoraInicio(dto.horaInicio());
                    escala.setHoraFim(dto.horaFim());
                    escala.setIntervaloMinutos(dto.intervaloMinutos());
                    return EscalaPadraoDTO.from(escalaPadraoRepository.save(escala));
                });
    }

    public boolean remover(Long id, Dentista dentista) {
        return escalaPadraoRepository.findById(id)
                .filter(e -> e.getDentista().getId().equals(dentista.getId()))
                .map(e -> { escalaPadraoRepository.delete(e);
                    return true; })
                .orElse(false);
    }
} 