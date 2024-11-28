package com.barbearia.agendamento_barbearia.controles;

import com.barbearia.agendamento_barbearia.modelos.Agendamento;
import com.barbearia.agendamento_barbearia.repositorios.AgendamentoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepositorio agendamentoRepositorio;

    public String criarAgendamento(Agendamento agendamento) {
        boolean horarioOcupado = agendamentoRepositorio.existsByDataAndHora(agendamento.getData(), agendamento.getHora());

        if (horarioOcupado) {
            throw new IllegalArgumentException("Erro: Já existe um agendamento para este horário.");
        }

        agendamentoRepositorio.save(agendamento);

        return "Agendamento criado com sucesso!";
    }
}
