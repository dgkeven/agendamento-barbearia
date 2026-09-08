package com.barbearia.agendamento_barbearia.visao;

import com.barbearia.agendamento_barbearia.controles.AgendamentoService;
import com.barbearia.agendamento_barbearia.controles.HorarioService;
import com.barbearia.agendamento_barbearia.modelos.Agendamento;
import com.barbearia.agendamento_barbearia.repositorios.AgendamentoRepositorio;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Bean de apoio da tela de agendamento (JSF + PrimeFaces).
 *
 * <p>E um bean gerenciado pelo Spring: o JoinFaces registra o
 * {@code SpringBeanFacesELResolver}, entao o Faces resolve {@code #{agendamentoBean}}
 * direto no contexto do Spring. A anotacao {@link ViewScoped} e mapeada pelo
 * JoinFaces para o escopo "view" do Spring, ou seja, o estado do formulario vive
 * enquanto o usuario permanecer na mesma view.
 *
 * <p>A regra de negocio continua no {@link AgendamentoService}, o mesmo usado pela
 * API REST. Esta classe apenas adapta os tipos da tela ({@code LocalDate}) para o
 * formato gravado na entidade ({@code String}).
 */
@Component("agendamentoBean")
@ViewScoped
public class AgendamentoBean {

    private static final Logger log = LoggerFactory.getLogger(AgendamentoBean.class);

    /** Formato gravado na entidade, igual ao que o front-end antigo enviava. */
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter FORMATO_DATA_BR = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final AgendamentoService agendamentoService;
    private final AgendamentoRepositorio agendamentoRepositorio;

    // ----- campos do formulario -----
    private String nome;
    private String telefone;
    private LocalDate data;
    private String hora;

    // ----- dados exibidos -----
    private List<String> horariosDisponiveis = List.of();
    private List<Agendamento> agendamentos = List.of();

    public AgendamentoBean(AgendamentoService agendamentoService,
                          AgendamentoRepositorio agendamentoRepositorio) {
        this.agendamentoService = agendamentoService;
        this.agendamentoRepositorio = agendamentoRepositorio;
    }

    @PostConstruct
    public void iniciar() {
        this.data = LocalDate.now();
        atualizarHorariosDisponiveis();
        carregarAgendamentos();
    }

    // ------------------------------------------------------------------
    // Acoes da tela
    // ------------------------------------------------------------------

    /** Disparada via ajax quando o cliente troca a data. */
    public void aoTrocarData() {
        this.hora = null;
        atualizarHorariosDisponiveis();
    }

    public void salvar() {
        if (data == null || hora == null || hora.isBlank()) {
            adicionarMensagem(FacesMessage.SEVERITY_WARN,
                    "Dados incompletos", "Escolha a data e o horario do atendimento.");
            return;
        }

        String telefoneNumerico = somenteDigitos(telefone);
        if (telefoneNumerico.length() < 10 || telefoneNumerico.length() > 11) {
            adicionarMensagem(FacesMessage.SEVERITY_WARN,
                    "Telefone invalido", "Informe DDD + numero, com 10 ou 11 digitos.");
            return;
        }

        Agendamento agendamento = new Agendamento();
        agendamento.setNome(nome == null ? null : nome.trim());
        agendamento.setTelefone(telefoneNumerico);
        agendamento.setData(data.format(FORMATO_DATA));
        agendamento.setHora(hora);

        try {
            String mensagem = agendamentoService.criarAgendamento(agendamento);
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Tudo certo!", mensagem);
            limparCampos();
        }
        catch (IllegalArgumentException e) {
            // Horario ocupado: regra de negocio, nao e erro de sistema.
            adicionarMensagem(FacesMessage.SEVERITY_WARN, "Nao foi possivel agendar", e.getMessage());
        }
        catch (RuntimeException e) {
            log.error("Falha ao criar agendamento", e);
            adicionarMensagem(FacesMessage.SEVERITY_ERROR,
                    "Erro no servidor", "Nao foi possivel salvar agora. Tente novamente.");
        }

        atualizarHorariosDisponiveis();
        carregarAgendamentos();
    }

    public void cancelar(Agendamento agendamento) {
        try {
            String mensagem = agendamentoService.cancelarAgendamento(agendamento.getId());
            adicionarMensagem(FacesMessage.SEVERITY_INFO, "Cancelado", mensagem);
        }
        catch (IllegalArgumentException e) {
            adicionarMensagem(FacesMessage.SEVERITY_WARN, "Atencao", e.getMessage());
        }
        catch (RuntimeException e) {
            log.error("Falha ao cancelar agendamento {}", agendamento.getId(), e);
            adicionarMensagem(FacesMessage.SEVERITY_ERROR,
                    "Erro no servidor", "Nao foi possivel cancelar agora. Tente novamente.");
        }

        atualizarHorariosDisponiveis();
        carregarAgendamentos();
    }

    public void limpar() {
        limparCampos();
        atualizarHorariosDisponiveis();
    }

    // ------------------------------------------------------------------
    // Apoio
    // ------------------------------------------------------------------

    private void limparCampos() {
        this.nome = null;
        this.telefone = null;
        this.hora = null;
    }

    private void carregarAgendamentos() {
        this.agendamentos = agendamentoRepositorio.findAllByOrderByDataAscHoraAsc();
    }

    /**
     * Horarios livres = grade fixa da barbearia menos os que já estao reservados
     * para a data escolhida.
     */
    private void atualizarHorariosDisponiveis() {
        if (data == null) {
            this.horariosDisponiveis = List.of();
            return;
        }

        Set<String> ocupados = agendamentoRepositorio.findByData(data.format(FORMATO_DATA))
                .stream()
                .map(Agendamento::getHora)
                .collect(Collectors.toSet());

        this.horariosDisponiveis = HorarioService.HORARIOS_PADRAO.stream()
                .map(FORMATO_HORA::format)
                .filter(horario -> !ocupados.contains(horario))
                .toList();
    }

    private static String somenteDigitos(String valor) {
        return valor == null ? "" : valor.replaceAll("\\D", "");
    }

    private static void adicionarMensagem(FacesMessage.Severity severidade, String titulo, String detalhe) {
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(severidade, titulo, detalhe));
    }

    // ------------------------------------------------------------------
    // Formatadores usados na tabela
    // ------------------------------------------------------------------

    /** Converte "2026-09-07" em "07/09/2026" para exibicao. */
    public String formatarData(String dataIso) {
        if (dataIso == null || dataIso.isBlank()) {
            return "";
        }
        try {
            return LocalDate.parse(dataIso, FORMATO_DATA).format(FORMATO_DATA_BR);
        }
        catch (RuntimeException e) {
            return dataIso; // registro antigo em formato inesperado: mostra como esta
        }
    }

    /** Converte "11987654321" em "(11) 98765-4321" para exibicao. */
    public String formatarTelefone(String valor) {
        String digitos = somenteDigitos(valor);
        if (digitos.length() == 11) {
            return "(%s) %s-%s".formatted(digitos.substring(0, 2), digitos.substring(2, 7), digitos.substring(7));
        }
        if (digitos.length() == 10) {
            return "(%s) %s-%s".formatted(digitos.substring(0, 2), digitos.substring(2, 6), digitos.substring(6));
        }
        return valor == null ? "" : valor;
    }

    /** Impede escolher datas passadas no calendario. */
    public LocalDate getDataMinima() {
        return LocalDate.now();
    }

    public boolean isHorarioIndisponivel() {
        return horariosDisponiveis.isEmpty();
    }

    // ------------------------------------------------------------------
    // Getters / Setters
    // ------------------------------------------------------------------

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public List<String> getHorariosDisponiveis() {
        return horariosDisponiveis;
    }

    public List<Agendamento> getAgendamentos() {
        return agendamentos;
    }

    /** Exposto para eventual uso na tela (grade completa de atendimento). */
    public List<LocalTime> getGradeCompleta() {
        return HorarioService.HORARIOS_PADRAO;
    }
}
