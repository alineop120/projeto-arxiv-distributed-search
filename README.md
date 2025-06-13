# 🔍 Sistema Distribuído de Busca de Artigos Científicos (arXiv)

Este projeto implementa um sistema de busca distribuído que consulta artigos científicos do arXiv de forma paralela, simulando um ambiente cliente-servidor com divisão de dados. A busca é feita utilizando o algoritmo eficiente **Knuth-Morris-Pratt (KMP)** para localização de termos em texto.

---

## 📚 Sumário

- [📁 Estrutura do Projeto](#-estrutura-do-projeto)
- [💡 Objetivo](#-objetivo)
- [🏗️ Arquitetura](#-arquitetura)
- [🚀 Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [⚙️ Como Executar](#️-como-executar)
- [🧠 Funcionamento](#-funcionamento)
- [🔍 Algoritmo de Busca](#-algoritmo-de-busca)
- [🗂️ Arquivos de Dados](#️-arquivos-de-dados)
- [📈 Melhorias Aplicadas](#-melhorias-aplicadas)
- [📌 Exemplo de Consulta](#-exemplo-de-consulta)
- [🛠️ Contribuições](#️-contribuições)
- [👩‍💻 Autoria](#-autoria)
- [📄 Licença](#-licença)

---

## 📁 Estrutura do Projeto
```yaml
arxiv-distributed-search/
├── data/
│ ├── arxiv_part1.json
│ └── arxiv_part2.json
├── src/
│ └── br.ucb.arxivdistributed/
│ ├── client/ # Cliente que envia consultas
│ ├── serverA/ # Orquestrador (servidor A)
│ ├── serverB/ # Servidor B (dados parte 1)
│ ├── serverC/ # Servidor C (dados parte 2)
│ └── util/ # Utilitários e algoritmo de busca
```

---

## 💡 Objetivo

Dividir os dados de artigos científicos em duas partes e processar buscas de forma **paralela**, aumentando a eficiência e simulando um ambiente distribuído.

O sistema responde com os artigos cujo título ou resumo contenham o termo buscado.

---

## 🏗️ Arquitetura

Abaixo está o diagrama de comunicação do sistema:

<!--
![arquitetura-sistema-busca-distribuida](./images/arquitetura-sistema-busca-distribuida.png)
-->
<img src="./images/arquitetura-sistema-busca-distribuida.png" alt="Arquitetura do Sistema" width="60%" />

---

## 🚀 Tecnologias Utilizadas

- Java 17+
- Sockets TCP (Java)
- JSON (org.json)
- Algoritmo KMP (Knuth-Morris-Pratt)
- Multithreading com `ExecutorService`
- IntelliJ IDEA (recomendado)

---

## ⚙️ Como Executar

### 1. Pré-requisitos

- JDK 17 instalado
- Maven (se for usar dependência do `org.json`)
- Arquivos `arxiv_part1.json` e `arxiv_part2.json` em `data/`

---

### 2. Ordem de Execução

1. **Inicie o `ServerB`** (porta 5001)
2. **Inicie o `ServerC`** (porta 5002)
3. **Inicie o `ServerA`** (porta 5000 – orquestrador)
4. **Execute o `SearchClient`** e insira a consulta desejada

---

## 🧠 Funcionamento

- O **Cliente** envia uma consulta textual para o **Servidor A**
- O **Servidor A** repassa a consulta simultaneamente para os **servidores B e C**
- Cada servidor busca em seu próprio arquivo `.json` (parte 1 ou 2)
- Os resultados são retornados ao Servidor A, que os envia ao cliente

---

## 🔍 Algoritmo de Busca

O algoritmo de busca implementado é o **KMP (Knuth-Morris-Pratt)**, que é eficiente para encontrar substrings em textos grandes, evitando comparações redundantes.

---

## 🗂️ Arquivos de Dados

| Arquivo              | Servidor | Conteúdo                          |
|----------------------|----------|-----------------------------------|
| `arxiv_part1.json`   | B        | Primeira metade dos artigos       |
| `arxiv_part2.json`   | C        | Segunda metade dos artigos        |

---

## 📈 Melhorias Aplicadas

- ✅ **JSON pré-carregado em memória** para reduzir latência
- ✅ **Busca paralela com ExecutorService**
- ✅ **Mensagens claras e tratativas de erro**
- ✅ **Algoritmo KMP implementado para maior eficiência**

---

## 📌 Exemplo de Consulta

```text
🔍 Sistema de Busca Distribuído - Cliente
Digite uma palavra ou trecho para buscar em artigos científicos: aprendizado

[INFO] Encaminhando consulta para servidores B e C...

✅ Busca finalizada.
------ RESULTADOS ENCONTRADOS ------
Título: Advances in Machine Learning
Resumo: Este artigo explora métodos de aprendizado profundo...

------------ FIM DA BUSCA ----------
```

---

## 🛠️ Contribuições

Sugestões, melhorias ou novas funcionalidades são bem-vindas. Crie um fork ou envie uma issue! 😊

---

## 👩‍💻 Autoria

Projeto desenvolvido por estudantes da **Universidade Católica de Brasília (UCB)** para a disciplina de Programação Concorrente e Distribuída.

| Nome                                   | Matrícula   | Função no Projeto        | GitHub                                       |
|----------------------------------------|-------------|--------------------------|----------------------------------------------|
| *Ana Beatriz Cavalcante Amorim*        | UC23101592  | Cliente e Servidor A     | [@Anabamorim](https://github.com/Anabamorim) |
| *Aline Oliveira de Pinho*              | UC23101158  | Servidor B e JSON utils  | [@alineop120](https://github.com/alineop120) |
| *Cristiane Tamily de Menezes Carvalho* | UC10045998  | Servidor C e testes      | [@ctamilly](https://github.com/ctamilly)     |
| *Mateus Tirulli Rozeti*                | UC12345678  | Implementação do KMP     | [@Rozeti](https://github.com/Rozeti)         |

---

## 📄 Licença

Este projeto é de uso educacional e livre para modificações com atribuição.