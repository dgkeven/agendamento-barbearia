# Agendamento de Barbearia

Este projeto consiste em uma aplicação de agendamento para barbearias, onde os usuários podem agendar um horário para atendimento, incluindo informações como nome, telefone, data e hora. O sistema utiliza o **Spring Boot** para o backend e **HTML, CSS e JavaScript** para o frontend.

---
## **Acesse**
http://localhost:8080/
--
![image](https://github.com/user-attachments/assets/fac7e322-946c-41de-8a39-a06b3e8b60bd)


## Tecnologias Utilizadas

- **Backend**: 
  - Java 11+
  - Spring Boot
  - Spring Data JPA
  - Banco de Dados: MySQL

- **Frontend**:
  - HTML5
  - CSS3
  - JavaScript

---

## Funcionalidades

### Backend
- **Agendamento**:
  - Criação de agendamentos com nome, telefone, data e hora.
  - Verificação de disponibilidade do horário.
  
- **Horários Disponíveis**:
  - Consulta dos horários disponíveis para agendamento.

### Frontend
- Formulário para cadastro de agendamentos:
  - Nome
  - Telefone
  - Data e Hora
  
- Feedback visual sobre o status do agendamento:
  - Mensagem de sucesso ou erro.

---

## Como Rodar o Projeto

### 1. Clonando o Repositório

Clone o repositório para sua máquina local:

```bash
git clone https://github.com/dgkeven/agendamento-barbearia.git
```

### 2. Configuração do Backend

#### Dependências
Certifique-se de ter o **JDK 11+** instalado e o **Maven** configurado para rodar o projeto.

#### Banco de Dados
O projeto pode ser configurado com **H2** ou **MySQL**.

- Para usar o H2, basta rodar o projeto sem fazer nenhuma alteração no `application.properties`.

- Para configurar o MySQL, edite o arquivo `src/main/resources/application.properties` e defina as configurações do banco de dados:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/BarbeariaCadastro
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 3. Rodando o Backend

No diretório raiz do projeto, execute o seguinte comando:

```bash
mvn spring-boot:run
```

O backend será iniciado na porta **8080** por padrão.

### 4. Configuração do Frontend

- Abra o arquivo `index.html` no seu navegador.
- O frontend deve ser servido localmente, na porta **5500** (ou a porta configurada para o seu servidor).

Caso use um servidor de desenvolvimento como o **Live Server** no VS Code, abra a pasta do frontend e inicie o servidor.

---

## Endpoints do Backend

### **POST /agendamento**
- **Descrição**: Cria um novo agendamento.
- **Corpo da Requisição**:

```json
{
    "nome": "João Silva",
    "telefone": "11987654321",
    "data": "2024-12-30",
    "hora": "15:00"
}
```

- **Resposta**:

- **Sucesso**:

```text
Agendamento criado com sucesso!
```

- **Erro**:

```text
Erro: Já existe um agendamento para este horário.
```

### **GET /agendamento**
- **Descrição**: Lista os horários disponíveis para agendamento.
- **Resposta**:

```json
[
    {
        "data": "2024-12-30",
        "hora": "10:00"
    },
    {
        "data": "2024-12-30",
        "hora": "15:00"
    }
]
```

---

## Configuração do CORS

O backend está configurado para aceitar requisições do frontend no endereço `http://127.0.0.1:5500`. Caso o frontend esteja em outra URL, altere a anotação `@CrossOrigin` no controlador `AgendamentoController`:

```java
@CrossOrigin(origins = "http://127.0.0.1:8080")
```

---

## Como Contribuir

1. Faça um fork deste repositório.
2. Crie uma branch para a sua feature.
3. Commit suas alterações.
4. Push para a branch.
5. Crie um pull request.

---


