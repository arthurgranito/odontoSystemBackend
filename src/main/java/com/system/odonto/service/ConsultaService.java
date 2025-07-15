package com.system.odonto.service;

import com.renato.odonto.model.*;
import com.system.odonto.model.*;
import com.system.odonto.model.enums.ConsultaStatus;
import com.renato.odonto.repository.*;
import com.system.odonto.repository.AgendaDiponivelRepository;
import com.system.odonto.repository.ConsultaRepository;
import com.system.odonto.repository.PacienteRepository;
import com.system.odonto.repository.TipoConsultaRepository;
import com.system.odonto.shared.ConsultaDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConsultaService {
    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private TipoConsultaRepository tipoConsultaRepository;
    @Autowired
    private AgendaDiponivelRepository agendaDiponivelRepository;

    public List<ConsultaDTO> listar(Dentista dentista, String status, LocalDateTime dataInicio, LocalDateTime dataFim) {
        List<Consulta> consultas;
        if (status != null && dataInicio != null && dataFim != null) {
            consultas = consultaRepository.findByDentistaAndStatusAndDataHoraInicioBetween(dentista, status, dataInicio, dataFim);
        } else if (status != null) {
            consultas = consultaRepository.findByDentistaAndStatus(dentista, status);
        } else if (dataInicio != null && dataFim != null) {
            consultas = consultaRepository.findByDentistaAndDataHoraInicioBetween(dentista, dataInicio, dataFim);
        } else {
            consultas = consultaRepository.findByDentista(dentista);
        }
        return consultas.stream().map(ConsultaDTO::from).collect(Collectors.toList());
    }

    public Optional<ConsultaDTO> buscarPorId(Long id, Dentista dentista) {
        return consultaRepository.findById(id)
                .filter(c -> c.getDentista().getId().equals(dentista.getId()))
                .map(ConsultaDTO::from);
    }

    @Transactional
    public ConsultaDTO agendar(Long pacienteId, Long tipoConsultaId, Long agendaDisponivelId, String observacoes, Dentista dentista) {
        Paciente paciente = pacienteRepository.findById(pacienteId).orElseThrow(() -> new RuntimeException("Paciente não encontrado!"));
        TipoConsulta tipoConsulta = tipoConsultaRepository.findById(tipoConsultaId).orElseThrow(() -> new RuntimeException("Tipo de consulta não encontrado"));
        AgendaDiponivel agenda = agendaDiponivelRepository.findById(agendaDisponivelId)
            .filter(a -> a.getDentista().getId().equals(dentista.getId()) && a.getDisponivel())
            .orElseThrow(() -> new RuntimeException("Slot de agenda não disponível!"));

        // Garante que o slot não está vinculado a nenhuma consulta
        if (agenda.getConsulta() != null) {
            throw new RuntimeException("Este slot de agenda já está vinculado a uma consulta!");
        }

        // Cria e preenche a consulta
        Consulta consulta = new Consulta();
        consulta.setDentista(dentista);
        consulta.setPaciente(paciente);
        consulta.setTipoConsulta(tipoConsulta);
        consulta.setDataHoraInicio(LocalDateTime.of(agenda.getData(), agenda.getHoraInicio()));
        consulta.setDataHoraFim(LocalDateTime.of(agenda.getData(), agenda.getHoraFim()));
        consulta.setObservacoes(observacoes);
        consulta.setStatus(ConsultaStatus.AGENDADA);
        consulta.setValorCobrado(tipoConsulta.getPreco());

        // Sincroniza ambos os lados do relacionamento
        consulta.setAgendaDisponivel(agenda);
        agenda.setConsulta(consulta);
        agenda.setDisponivel(false);

        // Salva a consulta primeiro
        consulta = consultaRepository.saveAndFlush(consulta);
        // Depois salva a agenda já vinculada
        agendaDiponivelRepository.saveAndFlush(agenda);

        return ConsultaDTO.from(consulta);
    }

    public Optional<ConsultaDTO> atualizar(Long id, ConsultaDTO dto, Dentista dentista) {
        return consultaRepository.findById(id)
                .filter(c -> c.getDentista().getId().equals(dentista.getId()))
                .map(consulta -> {
                    consulta.setObservacoes(dto.observacoes());
                    consulta.setValorCobrado(dto.valorCobrado());
                    consulta.setStatus(dto.status());
                    return ConsultaDTO.from(consultaRepository.save(consulta));
                });
    }

    public Optional<ConsultaDTO> concluir(Long id, Dentista dentista) {
        return consultaRepository.findById(id)
                .filter(c -> c.getDentista().getId().equals(dentista.getId()))
                .map(consulta -> {
                    consulta.setStatus(ConsultaStatus.CONCLUIDA);
                    consulta.setDataConclusao(LocalDateTime.now());
                    return ConsultaDTO.from(consultaRepository.save(consulta));
                });
    }

    public Optional<ConsultaDTO> cancelar(Long id, Dentista dentista) {
        return consultaRepository.findById(id)
                .filter(c -> c.getDentista().getId().equals(dentista.getId()))
                .map(consulta -> {
                    consulta.setStatus(ConsultaStatus.CANCELADA);
                    consulta.getAgendaDisponivel().setDisponivel(true);
                    agendaDiponivelRepository.save(consulta.getAgendaDisponivel());
                    return ConsultaDTO.from(consultaRepository.save(consulta));
                });
    }

    public Optional<ConsultaDTO> descancelar(Long id, Dentista dentista) {
        return consultaRepository.findById(id)
                .filter(c -> c.getDentista().getId().equals(dentista.getId()))
                .filter(c -> c.getStatus() == ConsultaStatus.CANCELADA)
                .map(consulta -> {
                    // Só "descancela" se o slot estiver disponível
                    if (!consulta.getAgendaDisponivel().getDisponivel()) {
                        throw new RuntimeException("O slot já está ocupado por outra consulta.");
                    }
                    consulta.setStatus(ConsultaStatus.AGENDADA);
                    consulta.getAgendaDisponivel().setDisponivel(false);
                    agendaDiponivelRepository.save(consulta.getAgendaDisponivel());
                    return ConsultaDTO.from(consultaRepository.save(consulta));
                });
    }

    @Transactional
    public Optional<ConsultaDTO> reagendarCompleto(Long consultaId, ConsultaDTO dto, Dentista dentista) {
        return consultaRepository.findById(consultaId)
                .filter(c -> c.getDentista().getId().equals(dentista.getId()))
                .map(consulta -> {
                    // Atualiza paciente
                    if (dto.paciente().id() != null) {
                        Paciente paciente = pacienteRepository.findById(dto.paciente().id())
                            .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
                        consulta.setPaciente(paciente);
                    }
                    // Atualiza tipo de consulta
                    if (dto.tipoConsulta().id() != null) {
                        TipoConsulta tipoConsulta = tipoConsultaRepository.findById(dto.tipoConsulta().id())
                            .orElseThrow(() -> new RuntimeException("Tipo de consulta não encontrado"));
                        consulta.setTipoConsulta(tipoConsulta);
                        consulta.setValorCobrado(tipoConsulta.getPreco());
                    }
                    // Atualiza observações
                    if (dto.observacoes() != null) {
                        consulta.setObservacoes(dto.observacoes());
                    }
                    // Atualiza agenda/horário
                    if (dto.agendaDisponivel().id() != null &&
                        (consulta.getAgendaDisponivel() == null || !consulta.getAgendaDisponivel().getId().equals(dto.agendaDisponivel().id()))) {
                        // Só libera o slot antigo se mudou de horário
                        AgendaDiponivel agendaAntiga = consulta.getAgendaDisponivel();
                        if (agendaAntiga != null) {
                            agendaAntiga.setDisponivel(true);
                            agendaAntiga.setConsulta(null);
                            agendaDiponivelRepository.save(agendaAntiga);
                        }
                        AgendaDiponivel novaAgenda = agendaDiponivelRepository.findById(dto.agendaDisponivel().id())
                                .filter(a -> a.getDentista().getId().equals(dentista.getId()) && a.getDisponivel())
                                .orElseThrow(() -> new RuntimeException("Novo slot de agenda não disponível"));
                        novaAgenda.setDisponivel(false);
                        novaAgenda.setConsulta(consulta);
                        agendaDiponivelRepository.save(novaAgenda);
                        consulta.setAgendaDisponivel(novaAgenda);
                        consulta.setDataHoraInicio(LocalDateTime.of(novaAgenda.getData(), novaAgenda.getHoraInicio()));
                        consulta.setDataHoraFim(LocalDateTime.of(novaAgenda.getData(), novaAgenda.getHoraFim()));
                    }
                    consultaRepository.save(consulta);
                    return ConsultaDTO.from(consulta);
                });
    }

    public boolean remover(Long id, Dentista dentista) {
        Consulta consulta = consultaRepository.findById(id).orElseThrow(() -> new RuntimeException("Consulta não encontrada!"));

        AgendaDiponivel agenda = consulta.getAgendaDisponivel();

        if (agenda != null) {
            agenda.setConsulta(null);
            agenda.setDisponivel(true);
            agendaDiponivelRepository.save(agenda);
        }

        consultaRepository.delete(consulta);
        return true;
    }
}