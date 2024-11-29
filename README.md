# **Projeto: Sistema de Agendamento para Barbearia**

Este projeto é uma aplicação web para gerenciar agendamentos de horários em uma barbearia. Ele inclui um back-end desenvolvido com **Spring Boot** e um front-end simples em **HTML, CSS e JavaScript**. O sistema permite cadastrar clientes, gerenciar horários disponíveis e criar agendamentos de forma eficiente.

---
## **Acesse**
http://localhost:8080/
![image](https://github.com/user-attachments/assets/fd7b08f7-4d03-4946-9d83-917f4b2d592a)



## **Funcionalidades**

1. **Back-End:**
   - API RESTful para gerenciar clientes, horários e agendamentos.
   - Validações no servidor para garantir a consistência dos dados.
   - Banco de dados MySQL para persistência.

2. **Front-End:**
   - Formulário interativo para agendamento de horários.
   - Validações no lado do cliente (campos obrigatórios, telefone válido, data/hora no futuro).
   - Feedback visual para erros e sucesso.

3. **Banco de Dados:**
   - Tabelas para clientes, horários e agendamentos.
   - Relacionamento entre tabelas para garantir integridade.

---

## **Tecnologias Utilizadas**

### **Back-End**
- **Linguagem:** Java
- **Framework:** Spring Boot
- **Banco de Dados:** MySQL
- **ORM:** JPA/Hibernate

### **Front-End**
- **HTML5, CSS3 e JavaScript**
- **Validação Dinâmica:** JavaScript

---

## **Pré-Requisitos**

1. **Java 17** ou superior.
2. **MySQL** instalado e rodando.
3. **Maven** para gerenciar as dependências.
4. Ferramenta para rodar o projeto:
   - **IDE:** IntelliJ IDEA, Eclipse ou outra.
   - **Cliente MySQL:** MySQL Workbench ou equivalente.

---

## **Configuração do Ambiente**

### **Banco de Dados**

1. Crie o banco de dados usando o script SQL:
   ```sql
   CREATE DATABASE BarbeariaAgendamento;
   ```

2. Execute o script completo fornecido na seção anterior para criar as tabelas e popular os horários iniciais.

---

### **Back-End**

1. Clone o repositório do projeto:
   ```bash
   git clone https://github.com/dgkeven/barbearia-agendamento.git
   ```

2. Acesse o diretório do projeto:
   ```bash
   cd barbearia-agendamento
   ```

3. Configure as credenciais do banco de dados no arquivo `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/BarbeariaAgendamento
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   ```

4. Compile e execute a aplicação:
   ```bash
   mvn spring-boot:run
   ```

---

### **Front-End**

1. Navegue até a pasta `frontend` e abra o arquivo `index.html` no navegador ou configure o front para ser servido por um servidor local.

---

## **Como Usar**

### **1. Front-End:**
- Preencha o formulário com o nome, telefone, data e hora.
- Clique no botão **"Agendar"**.
- Receba mensagens de feedback sobre o status do agendamento.

### **2. Back-End (API):**
- Acesse os endpoints via Postman ou similar:
  - `GET /agendamento/horarios`: Lista horários disponíveis.
  - `POST /agendamento`: Cria um novo agendamento.

---

## **Estrutura do Projeto**

```plaintext
├── src
│   ├── main
│   │   ├── java/com/barbearia/agendamento_barbearia
│   │   │   ├── controles          # Controllers (API REST)
│   │   │   ├── modelos            # Classes de domínio (Cliente, Horário, Agendamento)
│   │   │   ├── repositorios       # Repositórios JPA
│   │   │   ├── AgendamentoBarbeariaApplication.java
│   ├── resources
│   │   ├── application.properties # Configurações do Spring Boot
│   │   ├── static                 # Arquivos estáticos (frontend)
│   │   ├── templates              # Templates para renderização (se usado)
├── frontend
│   ├── index.html                 # Página inicial
│   ├── styles.css                 # Estilos CSS
│   ├── script.js                  # Validações do formulário
```

---

## **Possíveis Melhorias**

1. Implementar autenticação para proteger os endpoints.
2. Criar uma interface administrativa para gerenciar horários e clientes.
3. Melhorar o front-end.
4. Adicionar suporte a envio de notificações via e-mail ou SMS.

---
