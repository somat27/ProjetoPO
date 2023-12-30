package FrontEnd;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import BackEnd.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

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
                if (curso.getDiretorCurso() != null
                        && curso.getDiretorCurso().equals(professor)) {
                    cursoAssociado = curso;
                }
            }
            int opcao = 0;
            do {
                consola.escreverFrase("\nMenu Login Professor:");
                consola.escreverFrase("1. Menu Professor");
                if (UnidadeCurricularAssociada != null) {
                    consola.escreverFrase("2. Menu Regente Unidade Curricular");
                }
                if (cursoAssociado != null) {
                    consola.escreverFrase("3. Menu Diretor de Curso");
                }
                consola.escreverFrase("0. Sair");
                opcao = consola.lerInteiro("Escolha uma opção: ");

                switch (opcao) {
                    case 1:
                        MenuProfessor(professor);
                        break;
                    case 2:
                        if (UnidadeCurricularAssociada != null) {
                            MenuRegenteUnidadeCurricular(professor);
                        } else {
                            consola.escreverErro("Opção inválida. Tente novamente.");
                        }
                        break;
                    case 3:
                        if (cursoAssociado != null) {
                            MenuDiretorCurso(professor);
                        } else {
                            consola.escreverErro("Opção inválida. Tente novamente.");
                        }
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

        String resposta = consola.lerStringLowerCase("\nQuer sair do Programa? (SIM/NAO): ");
        if (resposta.equals("sim")) {
            System.out.println("Saindo do Programa...");
        } else {
            MenuLogin();
        }
    }

    // MENUS ADMINISTRADOR
    public void MenuAdministrador() throws InterruptedException {
        consola.escreverFrase("Menu Administrador\n");

        int opcao = 0;
        do {
            consola.escreverFrase("1. Gestão de Professores");
            consola.escreverFrase("2. Gestão de Cursos");
            consola.escreverFrase("3. Gestão de Unidades Curriculares");
            consola.escreverFrase("4. Listar cursos/UCs/alunos/professores");
            consola.escreverFrase("0. Sair");

            opcao = consola.lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    menuGestaoProfessores();
                    break;
                case 2:
                    menuGestaoCursos();
                    break;
                case 3:
                    menuGestaoUCs();
                    break;
                case 4:
                    menuListagem();
                    break;
                case 0:
                    consola.escreverFrase("Sair do Menu Administrador.");
                    break;
                default:
                    consola.escreverErro("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private void menuGestaoProfessores() throws InterruptedException {
        int opcao = 0;
        String numeroMecanografico;
        boolean resultado;
        LocalDate data1 = null;
        do {
            consola.escreverFrase("\nMenu Gestão de Professores:");
            consola.escreverFrase("1. Adicionar Professor");
            consola.escreverFrase("2. Remover Professor");
            consola.escreverFrase("3. Alterar Informações do Professor");
            consola.escreverFrase("0. Voltar");

            opcao = consola.lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    String nome = consola.lerString("Nome do Professor: ");
                    numeroMecanografico = consola.GerarNumeroMecanografico(universidade, 1);
                    String dataInicioFuncoes;
                    do {
                        dataInicioFuncoes = consola.lerString("Data Início funções (dd/MM/yyyy): ");
                        resultado = consola.VerificarFormato(dataInicioFuncoes);
                        if (resultado) {
                            data1 = consola.VerificarSeValida(dataInicioFuncoes);
                            if (data1 == null) {
                                consola.escreverFrase("Data inválida\n Insira como no exemplo: " + "01/01/2024");
                            }
                        } else {
                            consola.escreverFrase("Data inválida\n Insira como no exemplo: " + "01/01/2024");
                        }
                    } while (data1 == null);

                    Professor novoProfessor = new Professor(nome, numeroMecanografico, dataInicioFuncoes);
                    universidade.adicionarProfessor(novoProfessor);
                    ficheiro.guarda_dados(universidade);

                    consola.escreverFrase("Professor adicionado com sucesso com o numero: " + numeroMecanografico + "!");
                    consola.PressEntertoContinue();
                    break;
                case 2:
                    consola.listarProfessores(universidade);
                    numeroMecanografico = consola.lerString("Número Mecanográfico do Professor a Remover: ");
                    if (universidade.removerProfessor(numeroMecanografico)) {
                        consola.escreverFrase("Professor removido com sucesso!");
                        ficheiro.guarda_dados(universidade);
                    } else {
                        consola.escreverErro("Professor não encontrado.");
                    }
                    consola.PressEntertoContinue();
                    break;
                case 3:
                    consola.listarProfessores(universidade);
                    numeroMecanografico = consola.lerString("Número Mecanográfico do Professor: ");
                    Professor professor = universidade.encontrarProfessor(numeroMecanografico);

                    if (professor != null) {
                        consola.escreverFrase("Escolha a informação a ser alterada:");
                        consola.escreverFrase("1. Nome");
                        consola.escreverFrase("2. Data de Contrato");
                        consola.escreverFrase("0. Voltar");

                        opcao = consola.lerInteiro("Escolha uma opção: ");
                        switch (opcao) {
                            case 1:
                                String novoNome = consola.lerString("Novo Nome: ");
                                professor.setNome(novoNome);
                                consola.escreverFrase("Nome do Professor alterado com sucesso!");
                                ficheiro.guarda_dados(universidade);
                                break;
                            case 2:
                                String novaDataContrato;
                                do {
                                    novaDataContrato = consola.lerString("Nova Data de Contrato (dd/MM/yyyy): ");
                                    resultado = consola.VerificarFormato(novaDataContrato);
                                    if (resultado) {
                                        data1 = consola.VerificarSeValida(novaDataContrato);
                                        if (data1 == null) {
                                            consola.escreverFrase("Data inválida\n Insira como no exemplo: " + "01/01/2024");
                                        }
                                    } else {
                                        consola.escreverFrase("Data inválida\n Insira como no exemplo: " + "01/01/2024");
                                    }
                                } while (data1 == null);

                                professor.setDataInicioFuncoes(novaDataContrato);
                                consola.escreverFrase("Data de Contrato do Professor alterada com sucesso!");
                                ficheiro.guarda_dados(universidade);
                                break;
                            case 0:
                                consola.escreverFrase("A voltar ao Menu de Gestão de Professores.");
                                break;
                            default:
                                consola.escreverErro("Opção inválida. Tente novamente.");
                        }
                    } else {
                        consola.escreverErro("Professor não encontrado. Tente novamente.");
                    }
                    consola.PressEntertoContinue();
                    break;
                case 0:
                    consola.escreverFrase("A voltar ao Menu Administrador.");
                    break;
                default:
                    consola.escreverErro("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private void menuGestaoCursos() throws InterruptedException {
        int opcao = 0;
        String nomeCurso;
        Curso curso = null;
        List<Professor> professoresDisponiveis;
        do {
            consola.escreverFrase("\nMenu Gestão de Cursos:");
            consola.escreverFrase("1. Adicionar Curso");
            consola.escreverFrase("2. Remover Curso");
            consola.escreverFrase("3. Alterar Informações do Curso");
            consola.escreverFrase("0. Voltar");

            opcao = consola.lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    do {
                        nomeCurso = consola.lerString("Nome do Curso: ");

                        if (universidade.encontrarCurso(nomeCurso) != null) {
                            consola.escreverErro("Já existe um curso com o mesmo nome. Por favor, escolha outro nome.");
                        }
                    } while (universidade.encontrarCurso(nomeCurso) != null);

                    professoresDisponiveis = consola.listarProfessoresDisponiveis(universidade, null);

                    if (professoresDisponiveis.isEmpty()) {
                        consola.escreverFrase("Não há professores disponíveis para serem diretores de curso.\nA voltar ao Menu Gestão de Cursos.");
                        consola.PressEntertoContinue();
                        break;
                    }

                    Professor diretorCurso = null;
                    boolean diretorEncontrado = false;

                    do {
                        consola.escreverFrase("Professores Disponíveis para serem Diretores do Curso:");
                        for (Professor professor : professoresDisponiveis) {
                            consola.escreverFrase("\tNúmero Mecanográfico: " + professor.getNumeroMecanografico() + " | Nome: " + professor.getNome());
                        }

                        String numeroMecanograficoDiretor = consola.lerString("Número Mecanográfico do Diretor do Curso: ");

                        for (Professor professor : professoresDisponiveis) {
                            if (professor.getNumeroMecanografico().equals(numeroMecanograficoDiretor)) {
                                diretorCurso = professor;
                                diretorEncontrado = true;
                                break;
                            }
                        }

                        if (!diretorEncontrado) {
                            consola.escreverErro("Número mecanográfico inválido. Tente novamente.");
                        }

                    } while (!diretorEncontrado);

                    Curso novoCurso = new Curso(nomeCurso, diretorCurso);
                    universidade.adicionarCurso(novoCurso);

                    consola.escreverFrase("Curso adicionado com sucesso!");

                    consola.PressEntertoContinue();
                    break;

                case 2:
                    do {
                        consola.listarCursos(universidade);
                        nomeCurso = consola.lerString("Nome do Curso a Remover: ");
                        curso = universidade.encontrarCursoPorDesignacao(nomeCurso);

                        if (curso == null) {
                            consola.escreverErro("Curso não encontrado. Por favor, insira um nome de curso válido.");
                        }

                    } while (curso == null);

                    universidade.removerCurso(curso.getDesignacao());
                    consola.escreverFrase("Curso removido com sucesso!");
                    ficheiro.guarda_dados(universidade);
                    consola.PressEntertoContinue();
                    break;

                case 3:
                    consola.listarCursos(universidade);
                    nomeCurso = consola.lerString("Nome do Curso: ");
                    curso = universidade.encontrarCursoPorDesignacao(nomeCurso);

                    if (curso != null) {
                        Professor diretorAtual = curso.getDiretorCurso();

                        professoresDisponiveis = consola.listarProfessoresDisponiveis(universidade, diretorAtual);

                        if (professoresDisponiveis.isEmpty()) {
                            consola.escreverFrase("Nenhum professor disponível para ser diretor de curso.");
                            consola.PressEntertoContinue();
                        } else {
                            consola.escreverFrase("Diretor de Curso Atual: \nNúmero Mecanográfico: " + diretorAtual.getNumeroMecanografico() + " | Nome: " + diretorAtual.getNome());
                            consola.escreverFrase("Professores Disponíveis para serem Diretores do Curso:");

                            for (Professor professor : professoresDisponiveis) {
                                consola.escreverFrase("\tNúmero Mecanográfico: " + professor.getNumeroMecanografico() + " | Nome: " + professor.getNome());
                            }

                            boolean novoDiretorEncontrado = false;
                            while (!novoDiretorEncontrado) {
                                String novoDiretorNumeroMecanografico = consola.lerString("Número Mecanográfico do novo Diretor do Curso: ");

                                if (novoDiretorNumeroMecanografico.equals(diretorAtual.getNumeroMecanografico())) {
                                    consola.escreverErro("O diretor atual do curso não pode ser escolhido como novo diretor.");
                                } else {
                                    Professor novoDiretorCurso = universidade.encontrarProfessor(novoDiretorNumeroMecanografico);

                                    if (novoDiretorCurso != null && professoresDisponiveis.contains(novoDiretorCurso)) {
                                        curso.setDiretorCurso(novoDiretorCurso);
                                        consola.escreverFrase("Diretor do Curso alterado com sucesso!");
                                        ficheiro.guarda_dados(universidade);
                                        novoDiretorEncontrado = true;
                                    } else {
                                        consola.escreverErro("Número mecanográfico não encontrado ou não é um professor disponível. Tente novamente.");

                                        consola.escreverFrase("Professores Disponíveis para serem Diretores do Curso:");

                                        for (Professor professor : professoresDisponiveis) {
                                            consola.escreverFrase("\tNúmero Mecanográfico: " + professor.getNumeroMecanografico() + " | Nome: " + professor.getNome());
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        consola.escreverErro("Curso não encontrado.");
                    }
                    break;

                case 0:
                    consola.escreverFrase("A voltar ao Menu Administrador.");
                    break;
                default:
                    consola.escreverErro("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private void menuGestaoUCs() throws InterruptedException {
        int opcao = 0;
        String designacaoUC;
        String designacaoCurso;
        UnidadeCurricular uc;
        List<Professor> professoresRegentesDisponiveis;

        do {
            consola.escreverFrase("\nMenu de Gestão de Unidades Curriculares (UCs)");
            consola.escreverFrase("1. Criar UC");
            consola.escreverFrase("2. Remover UC");
            consola.escreverFrase("3. Editar UC");
            consola.escreverFrase("0. Voltar");

            opcao = consola.lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    consola.escreverFrase("\nCriar Nova UC");

                    String nomeUC;
                    boolean nomeRepetido;

                    do {
                        nomeUC = consola.lerString("Digite o nome da UC: ");
                        nomeRepetido = universidade.encontrarUCporDesignacao(nomeUC) != null;

                        if (nomeRepetido) {
                            consola.escreverFrase("Já existe uma UC com o mesmo nome. Por favor, escolha outro nome.");
                        } else {
                            professoresRegentesDisponiveis = consola.listarProfessoresRegentesDisponiveis(universidade);

                            if (professoresRegentesDisponiveis.isEmpty()) {
                                consola.escreverFrase("Não há professores disponíveis para serem regentes de UC.\nA voltar ao Menu de UCs.");
                                break;
                            }

                            boolean regenteEncontrado = false;
                            Professor regente = null;

                            do {
                                consola.escreverFrase("Professores Disponíveis para serem Regentes de UC:");

                                for (Professor professor : professoresRegentesDisponiveis) {
                                    if (!universidade.eRegenteDeUC(professor)) {
                                        consola.escreverFrase("\tNúmero Mecanográfico: " + professor.getNumeroMecanografico() + " | Nome: " + professor.getNome());
                                    }
                                }

                                String numeroMecRegente = consola.lerString("Digite o número mecanográfico do regente da UC: ");

                                for (Professor professor : professoresRegentesDisponiveis) {
                                    if (professor.getNumeroMecanografico().equals(numeroMecRegente)) {
                                        regente = professor;
                                        regenteEncontrado = true;
                                        break;
                                    }
                                }

                                if (!regenteEncontrado) {
                                    consola.escreverErro("Número mecanográfico inválido. Tente novamente.");
                                }
                            } while (!regenteEncontrado);

                            consola.listarCursos(universidade);

                            Curso cursoAssociar = null;
                            do {
                                designacaoCurso = consola.lerString("Digite a designação do curso para associar a UC: ");
                                cursoAssociar = universidade.encontrarCursoPorDesignacao(designacaoCurso);

                                if (cursoAssociar == null) {
                                    consola.escreverErro("Curso não encontrado. Tente novamente.");
                                    consola.listarCursos(universidade);
                                }
                            } while (cursoAssociar == null);

                            UnidadeCurricular novaUC = new UnidadeCurricular(nomeUC, regente);
                            novaUC.adicionarEquipaDocente(regente);
                            regente.adicionarAoServicoDocente(novaUC);

                            universidade.adicionarUC(novaUC, cursoAssociar);

                            consola.escreverFrase("UC criada com sucesso!");
                            ficheiro.guarda_dados(universidade);
                        }
                    } while (nomeRepetido);
                    consola.PressEntertoContinue();
                    break;
                case 2:
                    consola.escreverFrase("\nRemover UC");

                    consola.listarUCs(universidade);
                    boolean ucRemovida = false;

                    while (!ucRemovida) {
                        designacaoUC = consola.lerString("Digite o nome da UC para remover: ");

                        uc = universidade.encontrarUCporDesignacao(designacaoUC);

                        if (uc != null) {
                            universidade.removerUC(uc.getDesignacao());
                            consola.escreverFrase("UC removida com sucesso!");
                            ficheiro.guarda_dados(universidade);
                            ucRemovida = true;
                        } else {
                            consola.escreverFrase("UC não encontrada. Tente novamente.");
                        }
                    }

                    consola.PressEntertoContinue();
                    break;

                case 3:
                    consola.escreverFrase("\nEditar UC");

                    consola.listarCursos(universidade);

                    if (!universidade.getCursos().isEmpty()) {
                        designacaoCurso = consola.lerString("Qual Curso: ");

                        Curso curso = universidade.encontrarCursoPorDesignacao(designacaoCurso);

                        if (curso != null) {
                            consola.listarUCsCurso(curso);

                            designacaoUC = consola.lerString("Digite a designação do Curso para editar: ");

                            uc = universidade.encontrarUCporCurso(curso, designacaoUC);

                            if (uc != null) {
                                consola.exibirInformacoesUC(uc);

                                int opcaoEdicao;
                                do {
                                    consola.escreverFrase("\nMenu de Edição de Unidades Curriculares (UCs)");
                                    consola.escreverFrase("1. Editar nome da UC");
                                    consola.escreverFrase("2. Alterar Regente de UC");
                                    consola.escreverFrase("0. Voltar");

                                    opcaoEdicao = consola.lerInteiro("Escolha uma opção: ");

                                    switch (opcaoEdicao) {
                                        case 1:
                                            String novoNomeUC = consola.lerString("Digite o novo nome para a UC: ");
                                            uc.setDesignacao(novoNomeUC);
                                            consola.escreverFrase("Nome da UC editado com sucesso!");
                                            ficheiro.guarda_dados(universidade);
                                            consola.PressEntertoContinue();
                                            break;
                                        case 2:
                                            consola.escreverFrase("\nEditar Regente da UC");

                                            //consola.exibirInformacoesUC(uc);

                                            professoresRegentesDisponiveis = consola.listarProfessoresRegentesDisponiveis(universidade);

                                            if (!professoresRegentesDisponiveis.isEmpty()){
                                                consola.escreverFrase("Professores Disponíveis para serem Regentes de UC:");
                                                for (Professor professor : professoresRegentesDisponiveis) {
                                                    consola.escreverFrase("\tNúmero Mecanográfico: " + professor.getNumeroMecanografico() + " | Nome: " + professor.getNome());
                                                }

                                                boolean regenteEncontrado = false;
                                                Professor regenteAtual = uc.getRegente();

                                                do {
                                                    String numeroMecRegente = consola.lerString("Digite o número mecanográfico do novo regente da UC: ");
                                                    Professor novoRegente = universidade.encontrarProfessor(numeroMecRegente);

                                                    if (novoRegente != null && !novoRegente.equals(regenteAtual) && !universidade.eRegenteDeUC(novoRegente)) {
                                                        uc.setRegente(novoRegente);

                                                        uc.removerEquipaDocente(regenteAtual);
                                                        regenteAtual.removerDoServicoDocente(uc); 

                                                        uc.adicionarEquipaDocente(novoRegente);
                                                        novoRegente.adicionarAoServicoDocente(uc);

                                                        consola.escreverFrase("Regente da UC editado com sucesso!");
                                                        ficheiro.guarda_dados(universidade);
                                                        regenteEncontrado = true;
                                                    } else {
                                                        consola.escreverFrase("Número mecanográfico inválido. Tente novamente.");
                                                    }

                                                } while (!regenteEncontrado);
                                                break;
                                            } else {
                                                consola.escreverFrase("Não há professores disponíveis para serem regentes da UC.");
                                            }
                                            consola.PressEntertoContinue();
                                            break;
                                        case 0:
                                            consola.escreverFrase("A voltar ao menu");
                                            break;
                                        default:
                                            consola.escreverFrase("Opção inválida.");
                                    }
                                } while (opcaoEdicao != 0);
                            } else {
                                consola.escreverFrase("UC não encontrada.");
                            }
                        } else {
                            consola.escreverFrase("Curso não encontrado.");
                        }
                    } else {
                        consola.escreverFrase("Não há UCs disponíveis.");
                    }
                    break;
                case 0:
                    consola.escreverFrase("A sair do Menu de UCs.");
                    break;
                default:
                    consola.escreverErro("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private void menuListagem() {
        int opcao;
        List<Curso> cursos;
        do {
            consola.escreverFrase("1. Listar Cursos");
            consola.escreverFrase("2. Listar Unidades Curriculares");
            consola.escreverFrase("3. Listar Alunos");
            consola.escreverFrase("4. Listar Professores");
            consola.escreverFrase("0. Voltar");

            opcao = consola.lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    cursos = universidade.getCursos();
                    if (cursos.isEmpty()) {
                        consola.escreverFrase("Lista vazia, a voltar ao menu de listagem.");
                    } else {
                        consola.listarCursos(universidade);
                    }
                    consola.PressEntertoContinue();
                    break;
                case 2:
                    List<Curso> listaCursos = universidade.getCursos();
                    if (listaCursos.isEmpty()) {
                        consola.escreverFrase("Lista vazia, a voltar ao menu de listagem.");
                    } else {
                        consola.escreverFrase("Lista de Unidades Curriculares (UCs):");
                        for (Curso curso : listaCursos) {
                            consola.escreverFrase("\t" + curso.getDesignacao() + ":");
                            List<UnidadeCurricular> ucs = curso.getUCs();
                            for (UnidadeCurricular uc : ucs) {
                                consola.escreverFrase("\t-> " + uc.getDesignacao());
                            }
                        }
                    }
                    consola.PressEntertoContinue();
                    break;
                case 3:
                    cursos = universidade.getCursos();
                    boolean listaVaziaAlunos = true;
                    consola.escreverFrase("Lista de Alunos:");
                    for (Curso c : cursos) {
                        List<Aluno> alunos = c.getAlunos();
                        if (!alunos.isEmpty()) {
                            listaVaziaAlunos = false;
                            for (Aluno aluno : alunos) {
                                consola.escreverFrase("\tNúmero Mecanográfico: " + aluno.getNumeroMecanografico() + "\tNome: " + aluno.getNome());
                            }
                        }
                    }
                    if (listaVaziaAlunos) {
                        consola.escreverFrase("Lista vazia, a voltar ao menu de listagem.");
                    }
                    consola.PressEntertoContinue();
                    break;
                case 4:
                    List<Professor> professores = universidade.getProfessores();
                    if (professores.isEmpty()) {
                        consola.escreverFrase("Lista vazia, a voltar ao menu de listagem.");
                    } else {
                        consola.listarProfessores(universidade);
                    }
                    consola.PressEntertoContinue();
                    break;
                case 0:
                    consola.escreverFrase("A voltar ao Menu Administrador.");
                    break;
                default:
                    consola.escreverErro("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    // MENUS ADMINISTRADOR
    // MENUS PROFESSOR
    public void MenuProfessor(Professor professor) throws InterruptedException {
        List<UnidadeCurricular> servicoDocente = professor.getServicoDocente();
        if (servicoDocente.isEmpty()) {
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
                        consola.escreverFrase("\t" + (i + 1) + ". " + servicoDocente.get(i).getDesignacao());
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

                    List<Aluno> assiduidadeAlunos = new ArrayList<>();
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
                        consola.escreverFrase(alunos.getNome() + "->" + alunos.getNumeroMecanografico());
                        String presenca = "";
                        while (!presenca.equals("sim") && !presenca.equals("nao")) {
                            presenca = consola.lerStringLowerCase("\tEste aluno esta presente? (SIM, NAO): ");
                        }
                        if (presenca.equals("sim")) {
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

    // MENUS PROFESSOR
    // MENUS DIRETOR DE CURSO
    public void MenuDiretorCurso(Professor professor) throws InterruptedException {
        Curso cursoAssociado = null;
        List<Curso> cursos = universidade.getCursos();
        for (Curso curso : cursos) {
            if (curso.getDiretorCurso() != null && curso.getDiretorCurso().equals(professor)) {
                cursoAssociado = curso;
                break;
            }
        }
        if (cursoAssociado == null) {
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
                    novaDesignacao = consola.lerString("Escolha a nova designação para o curso " + cursoAssociado.getDesignacao() + ":");
                    cursoAssociado.setDesignacao(novaDesignacao);
                    consola.escreverFrase("Designação do curso alterada para: " + novaDesignacao);
                    ficheiro.guarda_dados(universidade);
                    break;
                case 2:
                    int n_alunos = cursoAssociado.getAlunos().size();
                    consola.escreverFrase("Numero de alunos do curso " + cursoAssociado.getDesignacao() + ": " + n_alunos);
                    int n_profs = cursoAssociado.getNumeroProfessores();
                    consola.escreverFrase("Numero de professores do curso " + cursoAssociado.getDesignacao() + ": " + n_profs);
                    break;
                case 0:
                    consola.escreverFrase("Saindo do Menu Diretor de Curso.");
                    break;
                default:
                    consola.escreverErro("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    // MENUS DIRETOR DE CURSO
    // MENUS REGENTES DA UNIDADE CURRICULAR
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
        if (UnidadeCurricularAssociada == null) {
            consola.escreverErro("Nao és regente de nenhuma unidade curricular!");
            consola.PressEntertoContinue();
            MenuLogin();
        }
        int opcao = 0;
        do {
            consola.escreverFrase("\nMenu Regente Unidade Curricular:");
            consola.escreverFrase("1. Adicionar aluno a Unidade Curricular.");
            consola.escreverFrase("2. Remover aluno da Unidade Curricular.");
            consola.escreverFrase("3. Consultar assiduidade de determinado aluno.");
            consola.escreverFrase("4. Adicionar Professor a Unidade Curricular.");
            consola.escreverFrase("5. Consultar Serviço Docente da Unidade Curricular.");
            consola.escreverFrase("0. Voltar");
            opcao = consola.lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    String nomeAluno = consola.lerString("Nome do aluno:");
                    String numeroMecanografico = consola.GerarNumeroMecanografico(universidade, 2);
                    Aluno novoAluno = new Aluno(nomeAluno, numeroMecanografico);
                    cursoAssociado.adicionarAluno(novoAluno);
                    ficheiro.guarda_dados(universidade);
                    consola.escreverFrase("Aluno " + nomeAluno + " adicionado ao curso com o numero: " + numeroMecanografico + ".");
                    consola.PressEntertoContinue();
                    break;
                case 2:
                    if (!cursoAssociado.getAlunos().isEmpty()) {
                        consola.listarAlunosUC(cursoAssociado);
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
                    } else {
                        consola.escreverErro("Não existem alunos.");
                    }
                    consola.PressEntertoContinue();
                    break;
                case 3:
                    int Totalsumarios = UnidadeCurricularAssociada.getSumarios().size();
                    if (Totalsumarios > 0) {
                        consola.listarAlunosUC(cursoAssociado);
                        boolean encontrado = false;
                        String numeroAlunoPresenca = null;
                        while (!encontrado) {
                            numeroAlunoPresenca = consola.lerString("Numero aluno:");
                            encontrado = consola.verificarAlunoCurso(cursoAssociado, numeroAlunoPresenca);
                            if (!encontrado) {
                                consola.escreverFrase("Aluno nao encontrado, Tente novamente!");
                            }
                        }
                        int presencaAluno = 0;
                        List<SumarioAula> sumarios = UnidadeCurricularAssociada.getSumarios();
                        List<Aluno> presencas = null;
                        for (SumarioAula sumario : sumarios) {
                            presencas = sumario.getListaAlunos();
                            for (Aluno aluno : presencas) {
                                if (aluno.getNumeroMecanografico().equals(numeroAlunoPresenca)) {
                                    presencaAluno++;
                                }
                            }
                        }
                        consola.escreverFrase("O aluno tem " + presencaAluno + "/" + Totalsumarios + " presencas");
                    } else {
                        consola.escreverFrase("Não existe sumarios registados");
                    }
                    consola.PressEntertoContinue();
                    break;
                case 4:
                    List<Professor> listaProfessoresDIsponiveis = consola.guardarProfessoresDisponiveisUC(universidade, UnidadeCurricularAssociada);
                    for (Professor lista : listaProfessoresDIsponiveis) {
                        System.out.println("\t- Número Mecanográfico: " + lista.getNumeroMecanografico() + " | Nome: " + lista.getNome());
                    }

                    String numeroProfessor = consola.lerString("Numero Professor para adicionar: ");
                    Professor processorEscolhido = null;
                    boolean professorEncontrado = false;
                    for (Professor listaProfessor : listaProfessoresDIsponiveis) {
                        if (listaProfessor.getNumeroMecanografico().equals(numeroProfessor)) {
                            professorEncontrado = true;
                            processorEscolhido = listaProfessor;
                            break; 
                        }
                    }

                    if (professorEncontrado) {
                        UnidadeCurricularAssociada.adicionarEquipaDocente(processorEscolhido);
                        processorEscolhido.adicionarAoServicoDocente(UnidadeCurricularAssociada);
                        ficheiro.guarda_dados(universidade);
                    } else {
                        System.out.println("Número mecanográfico não corresponde a um professor disponível.");
                    }
                    consola.PressEntertoContinue();
                    break;
                case 5:
                    for (Professor listaProfessor : UnidadeCurricularAssociada.getEquipaDocente()){
                        System.out.println("\t- Número Mecanográfico: " + listaProfessor.getNumeroMecanografico() + " | Nome: " + listaProfessor.getNome());
                    }
                    consola.PressEntertoContinue();
                    break;
                case 0:
                    consola.escreverFrase("Saindo do Menu Regente Unidade Curricular.");
                    break;
                default:
                    consola.escreverErro("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    // MENUS REGENTES DA UNIDADE CURRICULAR
}
