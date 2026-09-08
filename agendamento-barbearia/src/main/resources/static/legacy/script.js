document.getElementById("agendamentoForm").addEventListener("submit", async function (event) {
    event.preventDefault();

    const feedback = document.getElementById("feedback");
    feedback.textContent = "Aguarde...";
    feedback.style.color = "black";

    const nome = document.getElementById("nome").value.trim();
    const telefone = document.getElementById("telefone").value.trim();
    const data = document.getElementById("data").value;
    const hora = document.getElementById("hora").value;

    if (!nome || !telefone || !data || !hora) {
        feedback.textContent = "Todos os campos são obrigatórios.";
        feedback.style.color = "red";
        return;
    }

    const agendamento = { nome, telefone, data, hora };

    try {
        const response = await fetch("http://localhost:8080/agendamento", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(agendamento),
        });

        const message = await response.text();
        feedback.textContent = message;
        feedback.style.color = response.ok ? "green" : "red";

        if (response.ok) {
            document.getElementById("agendamentoForm").reset();
        }
    } catch (error) {
        feedback.textContent = "Erro ao conectar ao servidor.";
        feedback.style.color = "red";
    }
});
