package FrontEnd;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import BackEnd.*;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

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
    
    public void MenuLogin() throws InterruptedException {
        consola.escreverFrase("Menu Login");
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
                consola.escreverFrase("Login bem-sucedido como Professor/Regente!\n");
                loggedIn = true;
            } else {
                consola.escreverErro("Credenciais inválidas. Tente novamente.\n");
            }
        }

        if (universidade.verificarProfessor(usernameInput)) {
            Professor professor = universidade.encontrarProfessor(usernameInput);
            Curso cursoAssociado = null;
            UnidadeCurricular UnidadeCurricularAssociada = null;
            List<Curso> cursos = universidade.getCursos();
            for (Curso curso : cursos) {
                List<UnidadeCurricular> ucs = curso.getUCs();
                for (UnidadeCurricular uc : ucs) {
                    if (uc.getRegente().equals(professor)) {
                        UnidadeCurricularAssociada = uc;
                    }
                }
                if (curso.getDiretorCurso() != null && curso.getDiretorCurso().equals(professor)) {
                    cursoAssociado = curso;
                }
            }
            int opcao = 0;
            do {
                consola.escreverFrase("\nMenu Login Professor:");
                consola.escreverFrase("1. Menu Professor");                
                if (UnidadeCurricularAssociada != null) consola.escreverFrase("2. Menu Regente Unidade Curricular");
                if (cursoAssociado != null) consola.escreverFrase("3. Menu Diretor de Curso");
                consola.escreverFrase("0. Sair");
                opcao = consola.lerInteiro("Escolha uma opção: "); 

                switch (opcao) {
                    case 1:
                        MenuProfessor(professor);
                        break;
                    case 2:
                        if (UnidadeCurricularAssociada != null) 
                            MenuRegenteUnidadeCurricular(professor);
                        else
                            consola.escreverErro("Opção inválida. Tente novamente.");   
                        break;
                    case 3:
                        if (cursoAssociado != null) 
                            MenuDiretorCurso(professor);
                        else
                            consola.escreverErro("Opção inválida. Tente novamente.");
                        break;
                    case 0:
                        consola.escreverFrase("Saindo...");
                        break;
                    default:
                        consola.escreverErro("Opção inválida. Tente novamente.");
                }
            } while (opcao != 0);
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
        Curso curso1 = new Curso("Ciência da Computação", professor1);
        Curso curso2 = new Curso("Engenharia de Software",professor2);

        universidade.adicionarCurso(curso1);
        universidade.adicionarCurso(curso2);

        // Adicionar unidades curriculares
        UnidadeCurricular uc1 = new UnidadeCurricular("Programação", professor1);
        UnidadeCurricular uc2 = new UnidadeCurricular("Banco de Dados", professor2);

        curso1.adicionarUC(uc1);
        curso1.adicionarUC(uc2);
        
        uc1.adicionarEquipaDocente(professor1);
        professor1.adicionarAoServicoDocente(uc1);
        uc2.adicionarEquipaDocente(professor2);
        professor2.adicionarAoServicoDocente(uc2);

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
    
    public void MenuProfessor(Professor professor) throws InterruptedException {
        List<UnidadeCurricular> servicoDocente = professor.getServicoDocente();
        if (servicoDocente.size() < 1){
            consola.escreverErro("Nao tens Disciplinas associadas a ti!");
            consola.PressEntertoContinue();
            MenuLogin();
        }
        List<SumarioAula> sumarios = null;
        int opcao = 0;
        do {
            consola.escreverFrase("\nMenu Professor:");
            consola.escreverFrase("1. Criar sumário");
            consola.escreverFrase("2. Consultar lista de sumários por UC");
            consola.escreverFrase("3. Consultar lista de sumários por tipo de aula");
            consola.escreverFrase("4. Consultar serviço docente");
            consola.escreverFrase("0. Voltar");
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
                    
                    UnidadeCurricular UnidadeEscolhida = servicoDocente.get(escolha - 1);

                    consola.escreverFrase("Criar Sumário:");

                    String titulo;
                    titulo = consola.lerString("\tTítulo da Aula:");

                    String tipoAula = "";
                    while (!tipoAula.equals("teorica") && !tipoAula.equals("pratica") && !tipoAula.equals("laboratorial")) {
                        tipoAula = consola.lerStringLowerCase("\tTipo de Aula (TEORICA, PRATICA, LABORATORIAL): ");
                    }

                    String conteudo;
                    conteudo = consola.lerString("\tConteúdo da Aula:");
                    
                    List<Aluno> assiduidadeAlunos = new ArrayList<>();;
                    Curso cursoAssociado = null;
                    List<Curso> cursos = universidade.getCursos();
                    for (Curso curso : cursos) {
                        List<UnidadeCurricular> ucs = curso.getUCs();
                        for (UnidadeCurricular uc : ucs) {
                            if (uc.equals(UnidadeEscolhida)) {
                                cursoAssociado = curso;
                                break;
                            }
                        }
                    }
                    for (Aluno alunos : cursoAssociado.getAlunos()) {
                        System.out.println(alunos.getNome()+"->"+alunos.getNumeroMecanografico());
                        String presenca = "";
                        while (!presenca.equals("sim") && !presenca.equals("nao")) {
                            presenca = consola.lerStringLowerCase("\tEste aluno esta presente? (SIM, NAO): ");
                        }
                        if(presenca.equals("sim")){
                            assiduidadeAlunos.add(alunos);
                        }
                    }
                    
                    SumarioAula novoSumario = new SumarioAula(titulo, tipoAula, conteudo, consola.getHoraAtual(), assiduidadeAlunos);

                    professor.criarSumario(UnidadeEscolhida, novoSumario);

                    ficheiro.guarda_dados(universidade);
                    
                    consola.PressEntertoContinue();
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
                    consola.PressEntertoContinue();
                    break;
                case 3:
                    String tipoSumario = "";
                    while (!tipoSumario.equals("teorica") && !tipoSumario.equals("pratica") && !tipoSumario.equals("laboratorial")) {
                        tipoSumario = consola.lerStringLowerCase("\tTipo de Aula (TEORICA, PRATICA, LABORATORIAL): ");
                    }
                    for (UnidadeCurricular uc : servicoDocente) {
                        sumarios = uc.consultarSumariosPorTipoAula(tipoSumario);
                        for (SumarioAula sumario : sumarios) {
                            consola.escreverSeparador();
                            consola.escreverFrase("Titulo: " + sumario.getTitulo());
                            consola.escreverFrase("Sumario: " + sumario.getSumario());
                            consola.escreverFrase("Data: " + sumario.getData());
                        }
                    }
                    consola.PressEntertoContinue();
                    break;
                case 4:
                    consola.escreverFrase("Serviço Docente:");
                    for (UnidadeCurricular uc : servicoDocente) {
                        consola.escreverFrase("\t" + uc.getDesignacao());
                    }
                    break;
                case 0:
                    consola.escreverFrase("Saindo do Menu Professor.");
                    break;
                default:
                    consola.escreverErro("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }
    
    public void MenuDiretorCurso(Professor professor) throws InterruptedException {
        Curso cursoAssociado = null;
        List<Curso> cursos = universidade.getCursos();
        for (Curso curso : cursos) {
            if (curso.getDiretorCurso() != null && curso.getDiretorCurso().equals(professor)) {
                cursoAssociado = curso;
                break;
            }
        }
        if (cursoAssociado == null){
            consola.escreverErro("Nao és diretor de nenhum curso!");
            consola.PressEntertoContinue();
            MenuLogin();
        }
        int opcao = 0;
        do {
            consola.escreverFrase("\nMenu Diretor de Curso:");
            consola.escreverFrase("1. Alterar designação do Curso.");
            consola.escreverFrase("2. Listar número de professores ou alunos por curso.");
            consola.escreverFrase("0. Voltar");
            opcao = consola.lerInteiro("Escolha uma opção: "); 

            switch (opcao) {
                case 1:
                    String novaDesignacao = " ";
                    novaDesignacao = consola.lerString("Escolha a nova designação para o curso " + cursoAssociado.getDesignacao() +":");
                    cursoAssociado.setDesignacao(novaDesignacao);
                    System.out.println("Designação do curso alterada para: " + novaDesignacao);
                    ficheiro.guarda_dados(universidade);
                    break;
                case 2:
                    int n_alunos = cursoAssociado.getAlunos().size();
                    consola.escreverFrase("Numero de alunos do curso " + cursoAssociado.getDesignacao() +": " + n_alunos );
                    int n_profs = cursoAssociado.getNumeroProfessores();
                    consola.escreverFrase("Numero de professores do curso " + cursoAssociado.getDesignacao() +": " + n_profs);
                    break;
                case 0:
                    consola.escreverFrase("Saindo do Menu Diretor de Curso.");
                    break;
                default:
                    consola.escreverErro("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }
    
    public void MenuRegenteUnidadeCurricular(Professor professor) throws InterruptedException {
        Curso cursoAssociado = null;
        UnidadeCurricular UnidadeCurricularAssociada = null;
        List<Curso> cursos = universidade.getCursos();
        for (Curso curso : cursos) {
            List<UnidadeCurricular> ucs = curso.getUCs();
            for (UnidadeCurricular uc : ucs) {
                if (uc.getRegente().equals(professor)) {
                    cursoAssociado = curso;
                    UnidadeCurricularAssociada = uc;
                    break;
                }
            }
        }
        if (UnidadeCurricularAssociada == null){
            consola.escreverErro("Nao és regente de nenhuma unidade curricular!");
            consola.PressEntertoContinue();
            MenuLogin();
        }    
        int opcao = 0;
        do {
            consola.escreverFrase("\nMenu Diretor de Curso:");
            consola.escreverFrase("1. Adicionar aluno ao curso.");
            consola.escreverFrase("2. Remover aluno do curso.");
            consola.escreverFrase("3. Consultar assiduidade de determinado aluno.");
            consola.escreverFrase("0. Voltar");
            opcao = consola.lerInteiro("Escolha uma opção: "); 

            switch (opcao) {
                case 1:
                    String nomeAluno = consola.lerString("Nome do aluno:");
                    String numeroAluno = consola.lerString("Número mecanográfico do aluno:");
                    Aluno novoAluno = new Aluno(nomeAluno, numeroAluno); 
                    cursoAssociado.adicionarAluno(novoAluno);
                    ficheiro.guarda_dados(universidade);
                    consola.escreverFrase("Aluno " + nomeAluno + " adicionado ao curso.");
                    break;
                case 2:
                    String numMecanograficoRemover = consola.lerString("Número mecanográfico do aluno a remover:");
                    Aluno alunoRemover = null;
                    for (Aluno aluno : cursoAssociado.getAlunos()) {
                        if (aluno.getNumeroMecanografico().equals(numMecanograficoRemover)) {
                            alunoRemover = aluno;
                            break;
                        }
                    }
                    if (alunoRemover != null) {
                        cursoAssociado.removerAluno(numMecanograficoRemover);
                        ficheiro.guarda_dados(universidade);
                        consola.escreverFrase("Aluno removido do curso.");
                    } else {
                        consola.escreverErro("Aluno não encontrado no curso.");
                    }
                    break;
                case 3:
                    boolean encontrado = false;
                    String numeroAlunoPresenca = null;
                    while (!encontrado) {
                        numeroAlunoPresenca = consola.lerString("Numero aluno:");
                        List<Aluno> listaAlunos = cursoAssociado.getAlunos();
                        for (Aluno aluno : listaAlunos) {
                            if (aluno.getNumeroMecanografico().equals(numeroAlunoPresenca)){
                                encontrado = true;
                                break;
                            }
                        }
                        if (!encontrado)
                            System.out.println("Aluno nao encontrado, Tente novamente!");
                    }
                    int presencaAluno = 0;
                    List<SumarioAula> sumarios = UnidadeCurricularAssociada.getSumarios();
                    int Totalsumarios = UnidadeCurricularAssociada.getSumarios().size();
                    List<Aluno> presencas = null;
                    for (SumarioAula sumario : sumarios) {
                        presencas = sumario.getListaAlunos();
                        for (Aluno aluno : presencas) {
                            if (aluno.getNumeroMecanografico().equals(numeroAlunoPresenca)){
                                presencaAluno++;
                            }
                        }
                    }
                    System.out.println("O aluno tem " + presencaAluno + "/" + Totalsumarios + " presencas");
                    break;
                case 0:
                    consola.escreverFrase("Saindo do Menu Regente Unidade Curricular.");
                    break;
                default:
                    consola.escreverErro("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }
}
