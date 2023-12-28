package FrontEnd;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import BackEnd.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tomas
 */
public class Menus {

    private Universidade universidade;
    private Consola consola;
    private Ficheiro ficheiro;
    private Administrador administrador;

    public Menus(
            Universidade universidade,
            Consola consola,
            Ficheiro ficheiro,
            Administrador administrador
    ) {
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
    }

    public void MenuAdministrador() throws InterruptedException {
        consola.escreverFrase("Menu Administrador\n");

        int opcao = 0;
        do {
            consola.escreverFrase("1. Gestão de Professores");
            consola.escreverFrase("2. Gestão de Cursos");
            consola.escreverFrase("3. Gestão de Unidades Curriculares");
            consola.escreverFrase("4. Listar cursos/UCs/alunos/professores");
            consola.escreverFrase("5. Atribuir direção de curso/regência de UC");
            consola.escreverFrase("6. Sair");

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
                case 4:
                    menuListagem();
                    break;
                case 5:
                    menuAtribuir();
                case 6:
                    consola.escreverFrase("Sair do Menu Administrador.");
                    break;
                default:
                    consola.escreverErro("Opção inválida. Tente novamente.");
            }
        } while (opcao != 5);
    }

    private void menuGestaoProfessores() throws InterruptedException {
        int opcao = 0;
        String numeroMecanografico;
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
                    numeroMecanografico = GerarNumeroMecanografico(1);
                    String dataInicioFuncoes = consola.lerString("Data Início funções (dd/MM/yyyy): ");

                    Professor novoProfessor = new Professor(nome, numeroMecanografico, dataInicioFuncoes);
                    universidade.adicionarProfessor(novoProfessor);
                    ficheiro.guarda_dados(universidade);

                    consola.escreverFrase("Professor adicionado com sucesso com o numero: " + numeroMecanografico + "!");
                    break;
                case 2:
                    listarProfessores();
                    numeroMecanografico = consola.lerString("Número Mecanográfico do Professor a Remover: ");
                    if (universidade.removerProfessor(numeroMecanografico)) {
                        consola.escreverFrase("Professor removido com sucesso!");
                        ficheiro.guarda_dados(universidade);
                    } else {
                        consola.escreverErro("Professor não encontrado.");
                    }
                    break;
                case 3:
                    boolean professorEncontrado = false;

                    while (!professorEncontrado) {
                        listarProfessores();
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
                                    listarProfessores();
                                    ficheiro.guarda_dados(universidade);
                                    break;
                                case 2:
                                    String novaDataContrato = consola.lerString("Nova Data de Contrato (dd/MM/yyyy): ");
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
                            professorEncontrado = true; // Encerra o loop se as operações foram realizadas
                        } else {
                            consola.escreverErro("Professor não encontrado. Tente novamente.");
                        }
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

    private void menuGestaoCursos() throws InterruptedException {
        int opcao = 0;
        String nomeCurso;
        Curso curso;
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

                        // Verificar se o curso com o mesmo nome já existe
                        if (universidade.encontrarCurso(nomeCurso) != null) {
                            consola.escreverErro("Já existe um curso com o mesmo nome. Por favor, escolha outro nome.");
                        }
                    } while (universidade.encontrarCurso(nomeCurso) != null);

                    listarProfessores();
                    String numeroMecanograficoDiretor = consola.lerString("Número Mecanográfico do Diretor do Curso: ");
                    Professor diretorCurso = universidade.encontrarProfessor(numeroMecanograficoDiretor);

                    if (diretorCurso != null) {
                        Curso novoCurso = new Curso(nomeCurso, diretorCurso);

                        // Adicionar o novo curso à universidade
                        universidade.adicionarCurso(novoCurso);

                        consola.escreverFrase("Curso adicionado com sucesso!");
                        ficheiro.guarda_dados(universidade);
                    } else {
                        consola.escreverErro("Diretor de Curso não encontrado.");
                    }
                    break;

                case 2:
                    nomeCurso = consola.lerString("Nome do Curso a Remover: ");
                    curso = universidade.encontrarCursoPorDesignacao(nomeCurso);

                    if (curso != null) {
                        universidade.removerCurso(curso.getDesignacao());
                        consola.escreverFrase("Curso removido com sucesso!");
                        ficheiro.guarda_dados(universidade);
                    } else {
                        consola.escreverErro("Curso não encontrado.");
                    }

                    break;
                case 3:
                    boolean cursoEncontrado = false;

                    do {
                        listarCursos();
                        nomeCurso = consola.lerString("Nome do Curso: ");
                        curso = universidade.encontrarCursoPorDesignacao(nomeCurso);

                        if (curso != null) {
                            String novaDesignacao = consola.lerString("Nova Designação: ");
                            curso.setDesignacao(novaDesignacao);

                            consola.escreverFrase("Informações do Curso alteradas com sucesso!");
                            ficheiro.guarda_dados(universidade);
                            cursoEncontrado = true;
                        } else {
                            consola.escreverErro("Curso não encontrado. Certifique-se de que digitou corretamente o nome do curso.");
                        }
                    } while (!cursoEncontrado);
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
        UnidadeCurricular ucs;
        String designacaoUC;
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
                            listarProfessores();
                            String numeroMecRegente = consola.lerString("Digite o número mecanográfico do regente da UC: ");
                            Professor regente = universidade.encontrarProfessor(numeroMecRegente);

                            if (regente != null) {
                                listarCursos();
                                String designacaoCurso = consola.lerString("Digite a designação do curso para associar a UC: ");
                                Curso cursoAssociar = universidade.encontrarCursoPorDesignacao(designacaoCurso);

                                if (cursoAssociar != null) {
                                    // Criar UC
                                    UnidadeCurricular novaUC = new UnidadeCurricular(nomeUC, regente);

                                    // Adicionar UC ao curso
                                    universidade.adicionarUC(novaUC, cursoAssociar);

                                    consola.escreverFrase("UC criada com sucesso!");
                                    ficheiro.guarda_dados(universidade);
                                } else {
                                    consola.escreverFrase("Curso não encontrado. Não foi possível criar UC.");
                                }
                            } else {
                                consola.escreverFrase("Regente não encontrado. Não foi possível criar UC.");
                            }
                        }
                    } while (nomeRepetido); // Repete se já existe uma UC com o mesmo nome
                    break;

                case 2:
                    consola.escreverFrase("\nRemover UC");

                    listarUCs();

                    designacaoUC = consola.lerString("Digite o nome da UC para remover: ");

                    UnidadeCurricular ucRemover = universidade.encontrarUCporDesignacao(designacaoUC);

                    if (ucRemover != null) {

                        universidade.removerUC(ucRemover.getDesignacao());

                        consola.escreverFrase("UC removida com sucesso!");
                        ficheiro.guarda_dados(universidade);
                    } else {
                        consola.escreverFrase("UC não encontrada.");
                    }
                    break;
                case 3:
                    consola.escreverFrase("\nEditar UC");

                    listarUCs();

                    if (!universidade.getUCs().isEmpty()) {

                        designacaoUC = consola.lerString("Digite a designação da UC para editar: ");

                        UnidadeCurricular ucEditar = universidade.encontrarUCporDesignacao(designacaoUC);

                        if (ucEditar != null) {

                            exibirInformacoesUC(ucEditar);

                            int opcaoEdicao = exibirMenuEdicaoUC();

                            switch (opcaoEdicao) {
                                case 1:

                                    String novoNomeUC = consola.lerString("Digite o novo nome para a UC: ");
                                    ucEditar.setDesignacao(novoNomeUC);
                                    consola.escreverFrase("Nome da UC editado com sucesso!");
                                    ficheiro.guarda_dados(universidade);
                                    break;

                                case 2:

                                    listarCursosAssociados(ucEditar);
                                    String designacaoCurso = consola.lerString("Digite a designação do curso para desassociar a UC: ");
                                    Curso cursoDesassociar = universidade.encontrarCursoPorDesignacao(designacaoCurso);

                                    if (cursoDesassociar != null) {
                                        cursoDesassociar.removerUC(ucEditar.getDesignacao());
                                        consola.escreverFrase("UC desassociada do curso com sucesso!");
                                        ficheiro.guarda_dados(universidade);
                                    } else {
                                        consola.escreverFrase("Curso não encontrado.");
                                    }
                                    break;

                                default:
                                    consola.escreverFrase("Opção inválida.");
                            }
                        } else {
                            consola.escreverFrase("UC não encontrada.");
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

    private void listarCursos() {
        List<Curso> cursos = universidade.getCursos();
        consola.escreverFrase("Lista de Cursos:");
        for (Curso curso : cursos) {
            consola.escreverFrase("\t" + curso.getDesignacao());
        }
    }

    private void listarProfessores() {
        List<Professor> professores = universidade.getProfessores();
        consola.escreverFrase("Lista de Professores:");
        for (Professor professor : professores) {
            consola.escreverFrase("\tNúmero Mecanográfico: " + professor.getNumeroMecanografico() + "\tNome: " + professor.getNome());
        }
    }

    private void listarUCs() {
        List<Curso> cursos = universidade.getCursos();
        consola.escreverFrase("Lista de Unidades Curriculares (UCs):");
        for (Curso curso : cursos) {
            List<UnidadeCurricular> ucs = curso.getUCs();
            for (UnidadeCurricular uc : ucs) {
                consola.escreverFrase("\t" + uc.getDesignacao());
            }
        }
    }

    private void listarCursosAssociados(UnidadeCurricular uc) {
        List<Curso> cursosAssociados = new ArrayList<>();

        // Encontrar cursos associados à UC
        for (Curso curso : universidade.getCursos()) {
            if (curso.getUCs().contains(uc)) {
                cursosAssociados.add(curso);
            }
        }

        // Listar cursos associados
        if (!cursosAssociados.isEmpty()) {
            consola.escreverFrase("\nCursos associados à UC " + uc.getDesignacao() + ":");
            for (int i = 0; i < cursosAssociados.size(); i++) {
                consola.escreverFrase((i + 1) + "- " + cursosAssociados.get(i).getDesignacao());
            }
        } else {
            consola.escreverFrase("A UC não está associada a nenhum curso.");
        }
    }

    public static void exibirInformacoesUC(UnidadeCurricular uc) {
        System.out.println("Informações da UC:");
        System.out.println("Designação: " + uc.getDesignacao());
        System.out.println("Regente: " + uc.getRegente().getNome());

        List<Professor> equipeDocente = uc.getEquipaDocente();
        System.out.println("Equipe Docente:");
        for (Professor professor : equipeDocente) {
            System.out.println("- " + professor.getNome());
        }

        List<SumarioAula> sumarios = uc.getSumarios();
        System.out.println("Sumários de Aula:");
        for (SumarioAula sumario : sumarios) {
            System.out.println("- Tipo: " + sumario.getTipo() + ", Data: " + sumario.getData());
        }
    }

    public int exibirMenuEdicaoUC() {
        consola.escreverFrase("\nMenu de Edição de Unidades Curriculares (UCs)");
        consola.escreverFrase("1. Editar nome da UC");
        consola.escreverFrase("2. Desassociar UC de um curso");
        // Adicione mais opções de edição conforme necessário
        consola.escreverFrase("0. Voltar");

        return consola.lerInteiro("Escolha uma opção: ");
    }

    private String GerarNumeroMecanografico(int TIPO) {
        String UltimaNumeroMecanografico = "";
        String proximoNumeroMecanografico = "";
        String maiorNumeroMecanografico = "";
        if (TIPO == 1) { // professores
            List<Professor> professores = universidade.getProfessores();
            if (!professores.isEmpty()) {
                Professor ultimoProfessor = professores.get(professores.size() - 1);
                UltimaNumeroMecanografico = ultimoProfessor.getNumeroMecanografico();
                String parteNumerica = UltimaNumeroMecanografico.substring(1);
                int numeroInteiro = Integer.parseInt(parteNumerica);
                numeroInteiro++;
                proximoNumeroMecanografico = UltimaNumeroMecanografico.substring(0, 1) + String.format("%06d", numeroInteiro);
            } else {
                proximoNumeroMecanografico = "D000001";
            }
        } else if (TIPO == 2) { // alunos
            List<Curso> listaCursos = universidade.getCursos();
            for (Curso curso : listaCursos) {
                List<Aluno> listaAlunos = curso.getAlunos();
                if (!listaAlunos.isEmpty()) {
                    for (Aluno aluno : listaAlunos) {
                        String numeroMecanografico = aluno.getNumeroMecanografico();
                        if (numeroMecanografico.compareTo(maiorNumeroMecanografico) > 0) {
                            maiorNumeroMecanografico = numeroMecanografico;
                        }
                    }
                } else {
                    proximoNumeroMecanografico = "A000001";
                    break;
                }
            }
            if (!proximoNumeroMecanografico.equals("A000001")) {
                String parteNumerica = maiorNumeroMecanografico.substring(1);
                int numeroInteiro = Integer.parseInt(parteNumerica);
                numeroInteiro++;
                proximoNumeroMecanografico = maiorNumeroMecanografico.substring(0, 1) + String.format("%06d", numeroInteiro);
            }
        }
        return proximoNumeroMecanografico;
    }

    private void menuListagem() {
        int opcao;
        String nomeCurso;
        Curso curso;
        List<Curso> cursos;
        do {
            consola.escreverFrase("1. Listar Cursos");
            consola.escreverFrase("2. Listar Unidades Curriculares (UCs)");
            consola.escreverFrase("3. Listar Alunos");
            consola.escreverFrase("4. Listar Professores");
            consola.escreverFrase("0. Sair");

            opcao = consola.lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    listarCursos();
                    break;
                case 2:
                    listarCursos();
                    String nomeCursoListarUCs = consola.lerString("Digite o nome do curso para listar as UCs: ");
                    Curso cursoListarUCs = universidade.encontrarCurso(nomeCursoListarUCs);

                    if (cursoListarUCs != null) {
                        List<UnidadeCurricular> ucs = cursoListarUCs.getUCs();
                        consola.escreverFrase("\nLista de UCs do Curso " + cursoListarUCs.getDesignacao() + ":");
                        for (UnidadeCurricular uc : ucs) {
                            consola.escreverFrase(uc.getDesignacao());
                        }
                    } else {
                        consola.escreverErro("Curso não encontrado.");
                    }
                    break;

                case 3:
                    cursos = universidade.getCursos();
                    consola.escreverFrase("Lista de Alunos:");
                    for (Curso c : cursos) {
                        List<Aluno> alunos = c.getAlunos();
                        for (Aluno aluno : alunos) {
                            consola.escreverFrase("\t" + aluno.getNome());
                        }
                    }
                    break;
                case 4:
                    listarProfessores();
                    break;
                case 0:
                    consola.escreverFrase("A voltar ao Menu Administrador.");
                    break;
                default:
                    consola.escreverErro("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private void menuAtribuir() {
        int opcao = 0;
        do {
            consola.escreverFrase("1. Atribuir Direção de Curso");
            consola.escreverFrase("2. Atribuir Regência de UC");
            consola.escreverFrase("0. Sair");

            opcao = consola.lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    atribuirDirecaoCurso();
                    break;
                case 2:
                    atribuirRegenciaUC();
                    break;
                case 0:
                    consola.escreverFrase("A voltar ao Menu Administrador.");
                    break;
                default:
                    consola.escreverErro("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private void atribuirDirecaoCurso() {
        String numeroMecanograficoProfessor = consola.lerString("Número Mecanográfico do Professor: ");
        Professor professor = universidade.encontrarProfessor(numeroMecanograficoProfessor);

        if (professor != null) {
            consola.escreverFrase("Escolha o Curso para atribuir a Direção:");
            listarCursos();

            String designacaoCurso = consola.lerString("Designação do Curso: ");
            Curso curso = universidade.encontrarCursoPorDesignacao(designacaoCurso);

            if (curso != null) {
                curso.setDiretorCurso(professor);
                consola.escreverFrase("Direção de Curso atribuída com sucesso!");
                ficheiro.guarda_dados(universidade);
            } else {
                consola.escreverErro("Curso não encontrado.");
            }
        } else {
            consola.escreverErro("Professor não encontrado.");
        }
    }

    private void atribuirRegenciaUC() {
        String numeroMecanograficoProfessor = consola.lerString("Número Mecanográfico do Professor: ");
        Professor professor = universidade.encontrarProfessor(numeroMecanograficoProfessor);

        if (professor != null) {
            consola.escreverFrase("Escolha a UC para atribuir a Regência:");
            listarUCs();

            String designacaoUC = consola.lerString("Designação da UC: ");
            UnidadeCurricular uc = universidade.encontrarUCporDesignacao(designacaoUC);

            if (uc != null) {
                uc.setRegente(professor);
                consola.escreverFrase("Regência de UC atribuída com sucesso!");
                ficheiro.guarda_dados(universidade);
            } else {
                consola.escreverErro("UC não encontrada.");
            }
        } else {
            consola.escreverErro("Professor não encontrado.");
        }
    }

    public void MenuProfessor(Professor professor) throws InterruptedException {
        List<UnidadeCurricular> servicoDocente = professor.getServicoDocente();
        if (servicoDocente.size() < 1) {
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
                        System.out.println(alunos.getNome() + "->" + alunos.getNumeroMecanografico());
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
                    System.out.println("Designação do curso alterada para: " + novaDesignacao);
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
            consola.escreverFrase("\nMenu Diretor de Curso:");
            consola.escreverFrase("1. Adicionar aluno ao curso.");
            consola.escreverFrase("2. Remover aluno do curso.");
            consola.escreverFrase("3. Consultar assiduidade de determinado aluno.");
            consola.escreverFrase("0. Voltar");
            opcao = consola.lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    String nomeAluno = consola.lerString("Nome do aluno:");
                    String numeroMecanografico = GerarNumeroMecanografico(2);
                    Aluno novoAluno = new Aluno(nomeAluno, numeroMecanografico);
                    cursoAssociado.adicionarAluno(novoAluno);
                    ficheiro.guarda_dados(universidade);
                    consola.escreverFrase("Aluno " + nomeAluno + " adicionado ao curso com o numero: " + numeroMecanografico + ".");
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
                            if (aluno.getNumeroMecanografico().equals(numeroAlunoPresenca)) {
                                encontrado = true;
                                break;
                            }
                        }
                        if (!encontrado) {
                            System.out.println("Aluno nao encontrado, Tente novamente!");
                        }
                    }
                    int presencaAluno = 0;
                    List<SumarioAula> sumarios = UnidadeCurricularAssociada.getSumarios();
                    int Totalsumarios = UnidadeCurricularAssociada.getSumarios().size();
                    List<Aluno> presencas = null;
                    for (SumarioAula sumario : sumarios) {
                        presencas = sumario.getListaAlunos();
                        for (Aluno aluno : presencas) {
                            if (aluno.getNumeroMecanografico().equals(numeroAlunoPresenca)) {
                                presencaAluno++;
                            }
                        }
                    }
                    System.out.println(
                            "O aluno tem " + presencaAluno + "/" + Totalsumarios + " presencas"
                    );
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
