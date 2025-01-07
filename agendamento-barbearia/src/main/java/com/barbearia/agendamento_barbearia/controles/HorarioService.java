package com.barbearia.agendamento_barbearia.controles;

import com.barbearia.agendamento_barbearia.modelos.Horario;
import com.barbearia.agendamento_barbearia.modelos.Horario.Status;
import com.barbearia.agendamento_barbearia.repositorios.HorarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class HorarioService {

    @Autowired
    private HorarioRepositorio horarioRepositorio;

    public void insereHorarios2025() {
        LocalTime[] horarios = {
                LocalTime.of(8, 0),
                LocalTime.of(8, 30),
                LocalTime.of(9, 0),
                LocalTime.of(9, 30),
                LocalTime.of(10, 0),
                LocalTime.of(10, 30),
                LocalTime.of(11, 0),
                LocalTime.of(11, 30),
                LocalTime.of(14, 0),
                LocalTime.of(14, 30),
                LocalTime.of(15, 0),
                LocalTime.of(15, 30),
                LocalTime.of(16, 0),
                LocalTime.of(16, 30),
                LocalTime.of(17, 0),
                LocalTime.of(17, 30),
                LocalTime.of(18, 0),
                LocalTime.of(18, 30),
                LocalTime.of(19, 0),
                LocalTime.of(19, 30)

        };

        for (int i = 0; i < 365; i++) {
            LocalDate data = LocalDate.of(2025, 1, 1).plusDays(i);
            for (LocalTime hora : horarios) {
                Horario novoHorario = new Horario();
                novoHorario.setHora(hora);
                novoHorario.setData(data);
                novoHorario.setStatus(Status.DISPONIVEL);
                horarioRepositorio.save(novoHorario);
            }
        }
    }
}
