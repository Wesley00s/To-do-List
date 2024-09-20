# Todo List App

Este é um aplicativo de lista de tarefas simples criado para Android. Ele permite que os usuários adicionem, editem e excluam tarefas de uma lista de afazeres. O layout se adapta automaticamente, exibindo uma instrução quando a lista está vazia e ocultando-a quando há tarefas disponíveis.

## Funcionalidades

- Adicionar novas tarefas.
- Editar tarefas existentes.
- Excluir tarefas da lista.
- Interface amigável que se adapta à quantidade de tarefas (exibe mensagem quando a lista está vazia).

## Tecnologias Utilizadas

- **Android SDK**: Desenvolvimento nativo para Android.
- **Java**: Linguagem de programação utilizada.
- **ViewBinding**: Para ligar visualizações do layout ao código.
- **RecyclerView**: Para exibição das tarefas em lista.
- **FloatingActionButton**: Botão para adicionar novas tarefas.
- **Material Design**: Uso de componentes e padrões de design do Google.

## Estrutura do Projeto

- **MainActivity.java**: A principal activity do app. Gerencia a exibição da lista de tarefas e as interações com o usuário, como adicionar, editar e excluir tarefas.
- **TodoAdapter.java**: Adapter responsável por ligar os dados das tarefas à `RecyclerView`.
- **Todo.java**: Modelo de dados para representar uma tarefa com ID, título e descrição.
- **AddTaskActivity.java**: Activity responsável por adicionar novas tarefas.
- **EditTaskActivity.java**: Activity responsável por editar uma tarefa existente.
- **activity_main.xml**: Layout da `MainActivity`, que contém a `RecyclerView` e as mensagens instrutivas.
- **item_todo.xml**: Layout de cada item de tarefa na lista.

## Instalação

### Pré-requisitos

- Android Studio instalado.
- JDK 8 ou superior.
- Um dispositivo ou emulador Android rodando Android 7.0 (Nougat) ou superior.

### Passos para Configuração

1. Clone este repositório:
   ```bash
   git clone https://github.com/Wesley00s/To-do-List.git
   
