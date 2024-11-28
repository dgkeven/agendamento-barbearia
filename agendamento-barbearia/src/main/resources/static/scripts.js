const API_URL = "http://localhost:8080/agendamento";

// Carrega datas e horários disponíveis ao abrir a página
document.addEventListener("DOMContentLoaded", () => {
    carregarDatas();
});

// Função para carregar datas disponíveis
function carregarDatas() {
    fetch(API_URL)
        .then((response) => {
            if (!response.ok) throw new Error("Erro ao buscar horários disponíveis");
            return response.json();
        })
        .then((data) => {
            const datas = [...new Set(data.map((horario) => horario.data))];
            const dataSelect = document.getElementById("data");
            dataSelect.innerHTML = '<option value="">Selecione uma data</option>';

            datas.forEach((data) => {
                const option = document.createElement("option");
                option.value = data;
                option.textContent = data;
                dataSelect.appendChild(option);
            });

            dataSelect.addEventListener("change", () => {
                const dataSelecionada = dataSelect.value;
                carregarHorarios(dataSelecionada, data);
            });
        })
        .catch((error) => {
            console.error("Erro ao carregar datas:", error);
            alert("Erro ao carregar datas disponíveis.");
        });
}

// Função para carregar horários disponíveis com base na data selecionada
function carregarHorarios(dataSelecionada, horarios) {
    const horarioSelect = document.getElementById("horario");
    horarioSelect.innerHTML = '<option value="">Selecione um horário</option>';

    horarios
        .filter((horario) => horario.data === dataSelecionada && horario.status === "DISPONIVEL")
        .forEach((horario) => {
            const option = document.createElement("option");
            option.value = horario.id;
            option.textContent = horario.hora;
            horarioSelect.appendChild(option);
        });

    if (horarioSelect.children.length === 1) {
        alert("Nenhum horário disponível para esta data.");
    }
}

// Função para enviar agendamento
document.getElementById("agendamento-form").addEventListener("submit", (event) => {
    event.preventDefault();

    const nome = document.getElementById("nome").value;
    const telefone = document.getElementById("telefone").value;
    const horarioId = document.getElementById("horario").value;

    if (!horarioId) {
        alert("Selecione um horário!");
        return;
    }

    const agendamento = {
        cliente: { nome, telefone },
        horario: { id: horarioId },
    };

    fetch(API_URL, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(agendamento),
    })
        .then((response) => {
            if (response.ok) {
                document.getElementById("mensagem").textContent =
                    "Agendamento realizado com sucesso!";
                document.getElementById("agendamento-form").reset();
                carregarDatas(); // Atualiza datas e horários disponíveis
            } else {
                throw new Error("Erro ao agendar.");
            }
        })
        .catch((error) => {
            console.error("Erro ao realizar agendamento:", error);
            alert("Erro ao realizar agendamento.");
        });
});
