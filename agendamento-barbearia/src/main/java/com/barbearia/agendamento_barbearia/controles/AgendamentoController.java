package com.barbearia.agendamento_barbearia.controles;

import com.barbearia.agendamento_barbearia.modelos.Agendamento;
import com.barbearia.agendamento_barbearia.repositorios.AgendamentoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendamento")
@CrossOrigin(origins = "http://localhost:8080/agendamento")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    @Autowired
    private AgendamentoRepositorio agendamentoRepositorio;

    @PostMapping
    public ResponseEntity<String> criarAgendamento(@RequestBody Agendamento agendamento) {
        System.out.println("Recebido agendamento: " + agendamento);
        try {
            String mensagem = agendamentoService.criarAgendamento(agendamento);
            return ResponseEntity.ok(mensagem);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro interno no servidor.");
        }
    }


    @GetMapping
    public ResponseEntity<List<Agendamento>> listarAgendamentos() {
        List<Agendamento> agendamentos = agendamentoRepositorio.findAll(); // Utilizando o repositório
        return ResponseEntity.ok(agendamentos);
    }
}
