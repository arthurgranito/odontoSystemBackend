package com.renato.odonto.service;

import com.renato.odonto.model.Dentista;
import com.renato.odonto.model.TipoConsulta;
import com.renato.odonto.repository.TipoConsultaRepository;
import com.renato.odonto.shared.TipoConsultaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TipoConsultaService {
    @Autowired
    private TipoConsultaRepository tipoConsultaRepository;

    public List<TipoConsultaDTO> listarTodos(Dentista dentista) {
        return tipoConsultaRepository.findByDentista(dentista)
                .stream().map(TipoConsultaDTO::from).collect(Collectors.toList());
    }

    public Optional<TipoConsultaDTO> buscarPorId(Long id, Dentista dentista) {
        return tipoConsultaRepository.findById(id)
                .filter(tc -> tc.getDentista().equals(dentista))
                .map(TipoConsultaDTO::from);
    }

    public TipoConsultaDTO criar(TipoConsultaDTO dto, Dentista dentista) {
        TipoConsulta tipoConsulta = new TipoConsulta();
        tipoConsulta.setNome(dto.nome());
        tipoConsulta.setPreco(dto.preco());
        tipoConsulta.setDuracaoMinutos(dto.duracaoEstimadaMinutos());
        tipoConsulta.setDentista(dentista);
        return TipoConsultaDTO.from(tipoConsultaRepository.save(tipoConsulta));
    }

    public Optional<TipoConsultaDTO> atualizar(Long id, TipoConsultaDTO dto, Dentista dentista) {
        return tipoConsultaRepository.findById(id)
                .filter(tc -> tc.getDentista().getId().equals(dentista.getId()))
                .map(tipoConsulta -> {
                    tipoConsulta.setNome(dto.nome());
                    tipoConsulta.setPreco(dto.preco());
                    tipoConsulta.setDuracaoMinutos(dto.duracaoEstimadaMinutos());
                    return TipoConsultaDTO.from(tipoConsultaRepository.save(tipoConsulta));
                });
    }

    public boolean remover(Long id, Dentista dentista) {
        return tipoConsultaRepository.findById(id)
                .filter(tc -> tc.getDentista().getId().equals(dentista.getId()))
                .map(tc -> { tipoConsultaRepository.delete(tc); return true; })
                .orElse(false);
    }
} 