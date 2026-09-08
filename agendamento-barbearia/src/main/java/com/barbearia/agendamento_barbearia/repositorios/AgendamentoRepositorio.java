package com.barbearia.agendamento_barbearia.repositorios;

import com.barbearia.agendamento_barbearia.modelos.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgendamentoRepositorio extends JpaRepository<Agendamento, Long> {
    boolean existsByDataAndHora(String data, String hora);

    /** Agendamentos de um dia especifico. Usado para calcular os horarios livres. */
    List<Agendamento> findByData(String data);

    /**
     * Como data ("yyyy-MM-dd") e hora ("HH:mm") sao gravadas com largura fixa,
     * a ordenacao alfabetica coincide com a ordenacao cronologica.
     */
    List<Agendamento> findAllByOrderByDataAscHoraAsc();
}
