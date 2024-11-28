package com.barbearia.agendamento_barbearia.repositorios;

import com.barbearia.agendamento_barbearia.modelos.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendamentoRepositorio extends JpaRepository<Agendamento, Long> {
}
