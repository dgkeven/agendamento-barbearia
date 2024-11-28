package com.barbearia.agendamento_barbearia.repositorios;

import com.barbearia.agendamento_barbearia.modelos.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendamentoRepositorio extends JpaRepository<Agendamento, Long> {
    boolean existsByDataAndHora(String data, String hora);
}
