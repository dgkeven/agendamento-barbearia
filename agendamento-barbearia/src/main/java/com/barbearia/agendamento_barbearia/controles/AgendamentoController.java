package com.barbearia.agendamento_barbearia.controles;

import com.barbearia.agendamento_barbearia.modelos.Agendamento;
import com.barbearia.agendamento_barbearia.modelos.Horario;
import com.barbearia.agendamento_barbearia.repositorios.AgendamentoRepositorio;
import com.barbearia.agendamento_barbearia.repositorios.HorarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendamento")

public class AgendamentoController {

    @Autowired
    private AgendamentoRepositorio agendamentoRepositorio;

    @Autowired
    private HorarioRepositorio horarioRepositorio;


    @GetMapping
    public List<Horario> listarHorarios() {
        return horarioRepositorio.findAll();
    }

    @PostMapping
    public String criarAgendamento(@RequestBody Agendamento agendamento) {
        Horario horario = agendamento.getHorario();
        horario.setStatus(Horario.Status.OCUPADO);
        horarioRepositorio.save(horario);

        agendamentoRepositorio.save(agendamento);
        return "Agendamento criado com sucesso!";
    }
}
