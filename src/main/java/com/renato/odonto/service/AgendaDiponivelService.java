package com.renato.odonto.service;

import com.renato.odonto.exception.IntegrityException;
import com.renato.odonto.model.AgendaDiponivel;
import com.renato.odonto.model.Consulta;
import com.renato.odonto.model.Dentista;
import com.renato.odonto.model.EscalaPadrao;
import com.renato.odonto.repository.AgendaDiponivelRepository;
import com.renato.odonto.repository.EscalaPadraoRepository;
import com.renato.odonto.repository.ConsultaRepository;
import com.renato.odonto.shared.AgendaDiponivelDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AgendaDiponivelService {
    @Autowired
    private AgendaDiponivelRepository agendaDiponivelRepository;
    @Autowired
    private EscalaPadraoRepository escalaPadraoRepository;
    @Autowired
    private ConsultaRepository consultaRepository;

    public List<AgendaDiponivelDTO> listarDisponiveis(Dentista dentista, LocalDate dataInicio, LocalDate dataFim) {
        List<AgendaDiponivel> agendas;
        if (dataInicio != null && dataFim != null) {
            agendas = agendaDiponivelRepository.findByDentistaAndDataBetween(dentista, dataInicio, dataFim);
        } else {
            agendas = agendaDiponivelRepository.findByDentistaAndDisponivelTrue(dentista);
        }
        return agendas.stream().map(AgendaDiponivelDTO::from).collect(Collectors.toList());
    }

    public Optional<AgendaDiponivelDTO> buscarPorId(Long id, Dentista dentista) {
        return agendaDiponivelRepository.findById(id).filter(a -> a.getDentista().equals(dentista)).map(AgendaDiponivelDTO::from);
    }

    public AgendaDiponivelDTO atualizarDisponibilidade(Long id, boolean disponivel, Dentista dentista) {
        AgendaDiponivel agenda = agendaDiponivelRepository.findById(id).filter(a -> a.getDentista().equals(dentista)).orElseThrow();
        agenda.setDisponivel(disponivel);
        return AgendaDiponivelDTO.from(agendaDiponivelRepository.save(agenda));
    }

    public boolean remover(Long id, Dentista dentista) {
        return agendaDiponivelRepository.findById(id).filter(a -> a.getDentista().equals(dentista)).map(a -> {
            agendaDiponivelRepository.delete(a);
            return true;
        }).orElse(false);
    }

    public List<AgendaDiponivelDTO> gerarAgenda(Long dentistaId, Long escalaId, LocalDate dataInicio, LocalDate dataFim) {
        EscalaPadrao escala = escalaPadraoRepository.findById(escalaId).orElseThrow(() -> new IllegalArgumentException("Escala não encontrada"));
        Dentista dentista = escala.getDentista();
        List<AgendaDiponivel> slots = new ArrayList<>();
        for (LocalDate data = dataInicio; !data.isAfter(dataFim); data = data.plusDays(1)) {
            // Só gera para o dia da semana da escala
            if (escala.getDiaSemana().ordinal() + 1 == data.getDayOfWeek().getValue()) {
                LocalTime hora = escala.getHoraInicio();
                while (!hora.isAfter(escala.getHoraFim().minusMinutes(escala.getIntervaloMinutos()))) {
                    AgendaDiponivel slot = new AgendaDiponivel();
                    slot.setDentista(dentista);
                    slot.setData(data);
                    slot.setHoraInicio(hora);
                    slot.setHoraFim(hora.plusMinutes(escala.getIntervaloMinutos()));
                    slot.setDisponivel(true);
                    slot.setEscala(escala);
                    slots.add(slot);
                    hora = hora.plusMinutes(escala.getIntervaloMinutos());
                }
            }
        }
        List<AgendaDiponivel> salvos = agendaDiponivelRepository.saveAll(slots);
        return salvos.stream().map(AgendaDiponivelDTO::from).collect(Collectors.toList());
    }

    public Map<String, Integer> removerPorPeriodo(Long dentistaId, Long escalaId, LocalDate dataInicio, LocalDate dataFim) {
        List<AgendaDiponivel> slots = agendaDiponivelRepository.findAll().stream().filter(a -> a.getDentista() != null && a.getDentista().getId().equals(dentistaId)).filter(a -> a.getEscala() != null && a.getEscala().getId().equals(escalaId)).filter(a -> a.getData() != null && !a.getData().isBefore(dataInicio) && !a.getData().isAfter(dataFim)).collect(Collectors.toList());

        int horariosExcluidos = slots.size();
        List<Consulta> consultas = slots.stream().map(s -> s.getConsulta()).filter(Objects::nonNull).toList();

        if (!consultas.isEmpty()) {
            throw new IntegrityException("Existem consultas nesse período! Desmarque-as primeiro!");
        }

        agendaDiponivelRepository.deleteAll(slots); // agora exclui em cascata as consultas

        return Map.of("horariosExcluidos", horariosExcluidos);
    }
} 