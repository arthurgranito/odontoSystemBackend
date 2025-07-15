package com.renato.odonto.model.enums;

public enum DiaSemana {
    SEGUNDA_FEIRA(1, "Segunda-feira"),
    TERCA_FEIRA(2, "Terça-feira"),
    QUARTA_FEIRA(3, "Quarta-feira"),
    QUINTA_FEIRA(4, "Quinta-feira"),
    SEXTA_FEIRA(5, "Sexta-feira"),
    SABADO(6, "Sábado"),
    DOMINGO(7, "Domingo");

    private final int numero;
    private final String nomeCompleto;

    DiaSemana(int numero, String nomeCompleto) {
        this.numero = numero;
        this.nomeCompleto = nomeCompleto;
    }

    public int getNumero() {
        return numero;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public static DiaSemana getByNumero(int numero) {
        for (DiaSemana dia : DiaSemana.values()) {
            if (dia.getNumero() == numero) {
                return dia;
            }
        }
        throw new IllegalArgumentException("Número de dia da semana inválido: " + numero);
    }

    public static DiaSemana getByNomeCompleto(String nomeCompleto) {
        for (DiaSemana dia : DiaSemana.values()) {
            if (dia.getNomeCompleto().equalsIgnoreCase(nomeCompleto)) {
                return dia;
            }
        }
        throw new IllegalArgumentException("Nome de dia da semana inválido: " + nomeCompleto);
    }
}
