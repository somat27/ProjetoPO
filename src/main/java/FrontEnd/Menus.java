package FrontEnd;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import BackEnd.*;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.InputMismatchException;

/**
 *
 * @author tomas
 */
public class Menus {
    private Universidade universidade;
    private Consola consola;
    private Ficheiro ficheiro;
    private Administrador administrador;
    
    public Menus(Universidade universidade, Consola consola, Ficheiro ficheiro, Administrador administrador) {
        this.universidade = universidade;
        this.consola = consola;
        this.ficheiro = ficheiro;
        this.administrador = administrador;
    }
    
    public void MenuLogin() {
        String usernameInput = null;
        String passwordInput = null;
        boolean loggedIn = false;
        while (!loggedIn) {
            usernameInput = consola.lerString("Username (Número Mecanográfico): ");

            if (usernameInput.equals(administrador.getUsername())) {
                passwordInput = consola.lerString("Password: ");
                if (passwordInput.equals(administrador.getPassword())) {
                    consola.escreverFrase("Login bem-sucedido como Administrador!\n");
                    loggedIn = true;                    
                }
            } else if (universidade.verificarProfessor(usernameInput)) {
                // Se o input corresponde a um professor ou regente
                consola.escreverFrase("Login bem-sucedido como Professor/Regente!\n");
                loggedIn = true;
            } else {
                consola.escreverErro("Credenciais inválidas. Tente novamente.\n");
            }
        }

        if (universidade.verificarProfessor(usernameInput)) {
            MenuProfessor(universidade.encontrarProfessor(usernameInput));
        } else if (usernameInput.equals(administrador.getUsername())) {
            MenuAdministrador();
        }
    }
    
    public void MenuAdministrador() {
        consola.escreverFrase("Menu Administradorn\n");
        
        Professor professor1 = new Professor("João", "123", "01/01/2020");
        Professor professor2 = new Professor("Maria", "456", "02/02/2021");

        universidade.adicionarProfessor(professor1);
        universidade.adicionarProfessor(professor2);

        // Adicionar cursos
        Curso curso1 = new Curso("Ciência da Computação");
        Curso curso2 = new Curso("Engenharia de Software");

        universidade.adicionarCurso(curso1);
        universidade.adicionarCurso(curso2);

        // Adicionar unidades curriculares
        UnidadeCurricular uc1 = new UnidadeCurricular("Programação", professor1);
        UnidadeCurricular uc2 = new UnidadeCurricular("Banco de Dados", professor2);

        curso1.adicionarUC(uc1);
        curso2.adicionarUC(uc2);
        
        professor1.adicionarAoServicoDocente(uc1);
        professor1.adicionarAoServicoDocente(uc2);

        // Adicionar alunos
        Aluno aluno1 = new Aluno("Pedro", "789");
        Aluno aluno2 = new Aluno("Ana", "101");

        curso1.adicionarAluno(aluno1);
        curso1.adicionarAluno(aluno2);
        curso2.adicionarAluno(aluno2);
        
        consola.escreverFrase("\nLista de Professores:");
        for (Professor professor : universidade.getProfessores()) {
            consola.escreverFrase(professor.getNome());
        }
        
//        universidade.removerProfessor("789");
//
//        consola.escreverFrase("\nLista de Professores após a remoção:");
//        for (Professor professor : universidade.getProfessores()) {
//            consola.escreverFrase(professor.getNome());
//        }

        ficheiro.guarda_dados(universidade);
    }
    
    public void MenuProfessor(Professor professor) {
        List<UnidadeCurricular> servicoDocente = professor.getServicoDocente();
        List<SumarioAula> sumarios = null;
        int opcao = 0;
        do {
            consola.escreverFrase("\nMenu Professor:");
            consola.escreverFrase("1. Criar sumário");
            consola.escreverFrase("2. Consultar lista de sumários por UC");
            consola.escreverFrase("3. Consultar lista de sumários por tipo de aula");
            consola.escreverFrase("4. Consultar serviço docente");
            consola.escreverFrase("0. Sair");
            try {
                opcao = consola.lerInteiro("Escolha uma opção: "); 

                switch (opcao) {
                    case 1:
                        consola.escreverFrase("Escolha a Unidade Curricular para criar um Sumário:");
                        for (int i = 0; i < servicoDocente.size(); i++) {
                            consola.escreverFrase((i + 1) + ". " + servicoDocente.get(i).getDesignacao());
                        }

                        int escolha;
                        do {
                            escolha = consola.lerInteiro("Escolha o número correspondente à Unidade Curricular:");
                        } while (escolha < 1 || escolha > servicoDocente.size());
                        
                        consola.escreverFrase("Criar Sumário:");
                        
                        String titulo;
                        titulo = consola.lerString("\tTítulo da Aula:");

                        String tipoAula;
                        tipoAula = consola.lerStringLowerCase("\tTipo de Aula (TEORICA, PRATICA, LABORATORIAL): ");

                        String conteudo;
                        conteudo = consola.lerString("\tConteúdo da Aula:");

                        LocalDateTime agora = LocalDateTime.now();
                        Date date = java.sql.Timestamp.valueOf(agora);

                        SumarioAula novoSumario = new SumarioAula(titulo, tipoAula, conteudo, date);
                        
                        professor.criarSumario(servicoDocente.get(escolha - 1), novoSumario);
                        
                        ficheiro.guarda_dados(universidade);
                        break;
                    case 2:
                        for (int i = 0; i < servicoDocente.size(); i++) {
                            consola.escreverFrase(servicoDocente.get(i).getDesignacao() + ":");
                            sumarios = servicoDocente.get(i).getSumarios();
                            for (SumarioAula sumario : sumarios) {
                                consola.escreverFrase("\tTitulo: " + sumario.getTitulo());
                                consola.escreverFrase("\tTipo Aula: " + sumario.getTipo());
                                consola.escreverFrase("\tSumario: " + sumario.getSumario());
                                consola.escreverFrase("\tData: " + sumario.getData());
                                consola.escreverFrase("");
                            }
                        }
                        break;
                    case 3:
                        String tipoSumario;
                        tipoSumario = consola.lerStringLowerCase("Tipo de Aula (TEORICA, PRATICA, LABORATORIAL): ");
                        for (UnidadeCurricular uc : servicoDocente) {
                            sumarios = uc.consultarSumariosPorTipoAula(tipoSumario);
                            for (SumarioAula sumario : sumarios) {
                                consola.escreverSeparador();
                                consola.escreverFrase("Titulo: " + sumario.getTitulo());
                                consola.escreverFrase("Sumario: " + sumario.getSumario());
                                consola.escreverFrase("Data: " + sumario.getData());
                                consola.escreverSeparador();
                            }
                        }
                        break;
                    case 4:
                        consola.escreverFrase("Serviço Docente:");
                        for (UnidadeCurricular uc : servicoDocente) {
                            consola.escreverFrase("\t" + uc.getDesignacao());
                        }
                        consola.escreverSeparador();
                        break;
                    case 0:
                        consola.escreverFrase("Saindo do Menu Professor.");
                        break;
                    default:
                        consola.escreverErro("Opção inválida. Tente novamente.");
                }
            } catch (InputMismatchException e) {
                consola.escreverErro("Por favor, insira um número válido.");
            }
        } while (opcao != 0);
        
        //ficheiro.guarda_dados(universidade);
    }
}
