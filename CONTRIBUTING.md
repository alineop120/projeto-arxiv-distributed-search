# 🛠️ Guia de Contribuição

Obrigado por contribuir com este projeto de **Sistema de Busca Distribuído em Java**!  
Aqui estão algumas diretrizes para manter o repositório organizado.

---

## 📌 Requisitos

- Ter o Java 11 ou superior instalado
- Clonar o repositório
- Compilar com `javac` ou usar Maven

## 🧱 Estrutura Recomendada
```yaml
projetc-distributed-search/
├── cliente/
├── servidorA/
├── servidorB/
├── servidorC/
├── dados/
├── utils/
├── CONTRIBUTING.md # Esse arquivo
└── README.md
```

## 📝 Padrão de Commits

Use mensagens de commit semânticas:

| Prefixo      | Quando usar                          |
|--------------|--------------------------------------|
| `feat:`      | Nova funcionalidade                  |
| `fix:`       | Correção de bugs                     |
| `refactor:`  | Refatoração de código                |
| `docs:`      | Atualização de documentação          |
| `test:`      | Adição de testes                     |
| `chore:`     | Manutenção (ex: .gitignore, configs) |

### Exemplo:

```yaml
git commit -m "feat: adicionar ServidorB com busca em artigosB.json"
```

## ✅ Checklist ao contribuir

- Código compila sem erros 
- Segue o padrão de pastas e nomes 
- Commit possui mensagem clara e prefixo semântico 
- Evite subir arquivos `.class`, `.log`, `.swp`, etc.

> Se tiver dúvidas, abra uma issue ou envie uma mensagem. Boa contribuição! 🚀