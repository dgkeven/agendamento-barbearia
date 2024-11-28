package com.barbearia.agendamento_barbearia.repositorios;

import com.barbearia.agendamento_barbearia.modelos.Horario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HorarioRepositorio extends JpaRepository<Horario, Long> {
}