package com.barbearia.agendamento_barbearia.repositorios;

import com.barbearia.agendamento_barbearia.modelos.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {
}
