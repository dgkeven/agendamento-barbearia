package com.barbearia.agendamento_barbearia.repositorios;

import com.barbearia.agendamento_barbearia.modelos.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HorarioRepositorio extends JpaRepository<Horario, Long> {
    List<Horario> findByStatus(Horario.Status status);
}
