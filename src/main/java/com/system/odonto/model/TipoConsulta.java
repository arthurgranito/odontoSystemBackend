package com.system.odonto.model;

import com.system.odonto.shared.TipoConsultaDTO;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_tipo_consulta")
public class TipoConsulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nome;

    private Double preco;
    private Integer duracaoMinutos;

    @OneToMany(mappedBy = "tipoConsulta")
    private List<Consulta> consultas = new ArrayList<>();

    @ManyToOne
    private Dentista dentista;

    public static TipoConsulta from(TipoConsultaDTO tipoConsulta) {
        return new TipoConsulta(tipoConsulta.id(), tipoConsulta.nome(), tipoConsulta.preco(), tipoConsulta.duracaoEstimadaMinutos(), Dentista.from(tipoConsulta.dentistaDTO()));
    }

    public TipoConsulta() {
    }

    public TipoConsulta(Long id, String nome, Double preco, Integer duracaoMinutos, Dentista dentista) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.duracaoMinutos = duracaoMinutos;
        this.dentista = dentista;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Integer getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public void setDuracaoMinutos(Integer duracaoMinutos) {
        this.duracaoMinutos = duracaoMinutos;
    }

    public Dentista getDentista() {
        return dentista;
    }

    public void setDentista(Dentista dentista) {
        this.dentista = dentista;
    }
}
