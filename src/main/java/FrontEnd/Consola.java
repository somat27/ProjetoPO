/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FrontEnd;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import BackEnd.Aluno;
import BackEnd.Curso;
import BackEnd.Professor;
import BackEnd.SumarioAula;
import BackEnd.UnidadeCurricular;
import BackEnd.Universidade;

/**
 *
 * @author tomas
 */
public class Consola {

    private final Scanner Leitor = new Scanner(System.in);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void erroOpcaoInvalida() {
        System.err.println("\nErro\nNumero invalido\nPor favor insira um numero dos indicados\nPressione enter para continuar...");
        Leitor.nextLine();
    }

    public void escreverSeparador() {
        System.err.println("-------------------------------------------------");
        System.err.flush(); //limpar o terminal 
    }

    public void escreverErro(String aviso) {
        System.err.println(aviso);
        System.err.flush(); //limpar o terminal 
    }

    public void escreverFrase(String frase) {
        System.out.println(frase);
        System.out.flush(); //limpar o terminal 
    }

    public String lerString(String frase) {
        System.out.print(frase);
        return Leitor.nextLine();
    }

    public String lerStringLowerCase(String frase) {
        System.out.print(frase);
        String input = Leitor.nextLine();
        return input.toLowerCase();
    }

    public String lerStringUpperCase(String frase) {
        System.out.print(frase);
        String input = Leitor.nextLine();
        return input.toUpperCase();
    }

    public int lerInteiro(String string) {
        Integer numero = null;
        String texto;

        do {
            System.out.print(string);
            texto = Leitor.nextLine();

            try {
                numero = Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                escreverErro(texto);
                erroOpcaoInvalida();
            }

        } while (numero == null);

        return numero;
    }

    public void PressEntertoContinue() {
        System.out.println("\nPressione Enter para continuar...");
        Leitor.nextLine();
    }

    public String getHoraAtual() {
        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return agora.format(formatter);
    }

    // METODOS PARA VERIFICAÇÃO DE HORAS
    public LocalDate VerificarSeValida(String dataIntroduzida) {
        try {
            String[] parteDatas = dataIntroduzida.split("/");
            int dia = Integer.parseInt(parteDatas[0]);
            LocalDate data = LocalDate.parse(dataIntroduzida, formatter);
            boolean bissexto = data.isLeapYear();
            YearMonth d = YearMonth.of(data.getYear(), data.getMonth());
            //Verifica se o ano e bissexto

            if (bissexto == true) {
                if (data.getMonthValue() == 2) {
                    if (dia > 29) {
                        return null;
                    }
                } else if (!d.isValidDay(dia)) {
                    return null;
                }
            } else {
                if (data.getMonthValue() == 2) {
                    if (dia > 28) {
                        return null;
                    }
                } else if (!d.isValidDay(dia)) {
                    return null;
                }
            }
            return data;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean VerificarFormato(String data) {
        try {
            LocalDate.parse(data, formatter);
            return true;
        } catch (Exception e) {
            System.err.println("Formato errado (01/01/2023)");
            return false;
        }
    }

    // METODOS PARA VERIFICAÇÃO DE HORAS

    // METODOS PARA O MENU ADMINISTRADOR
    public void listarCursos(Universidade universidade) {
        List<Curso> cursos = universidade.getCursos();
        escreverFrase("Lista de Cursos:");
        for (Curso curso : cursos) {
            escreverFrase("\t" + curso.getDesignacao());
        }
    }

    public void listarProfessores(Universidade universidade) {
        List<Professor> professores = universidade.getProfessores();
        escreverFrase("Lista de Professores:");
        for (Professor professor : professores) {
            escreverFrase("\tNúmero Mecanográfico: " + professor.getNumeroMecanografico() + "\tNome: " + professor.getNome());
        }
    }

    public void listarUCs(Universidade universidade) {
        List<Curso> cursos = universidade.getCursos();
        escreverFrase("Lista de Unidades Curriculares (UCs):");
        for (Curso curso : cursos) {
            List<UnidadeCurricular> ucs = curso.getUCs();
            for (UnidadeCurricular uc : ucs) {
                escreverFrase("\t" + uc.getDesignacao());
            }
        }
    }

    public void listarUCsCurso(Curso curso) {
        escreverFrase("Lista de Unidades Curriculares (UCs):");
        List<UnidadeCurricular> ucs = curso.getUCs();
        for (UnidadeCurricular uc : ucs) {
            escreverFrase("\t" + uc.getDesignacao());
        }
    }

    public void listarAlunosUC(Curso curso) {
        List<Aluno> listaAlunos = curso.getAlunos();
        for (Aluno aluno : listaAlunos) {
            escreverFrase("\tNúmero Mecanográfico: " + aluno.getNumeroMecanografico() + "\tNome: " + aluno.getNome());
        }
    }

    public boolean verificarAlunoCurso(Curso curso, String numeroAlunoPresenca) {
        List<Aluno> listaAlunos = curso.getAlunos();
        for (Aluno aluno : listaAlunos) {
            if (aluno.getNumeroMecanografico().equals(numeroAlunoPresenca)) {
                return true;
            }
        }
        return false;
    }

    public void listarCursosAssociados(Universidade universidade, UnidadeCurricular uc) {
        List<Curso> cursosAssociados = new ArrayList<>();

        for (Curso curso : universidade.getCursos()) {
            if (curso.getUCs().contains(uc)) {
                cursosAssociados.add(curso);
            }
        }
        
        if (!cursosAssociados.isEmpty()) {
            escreverFrase("\nCursos associados à UC " + uc.getDesignacao() + ":");
            for (int i = 0; i < cursosAssociados.size(); i++) {
                escreverFrase((i + 1) + "- " + cursosAssociados.get(i).getDesignacao());
            }
        } else {
            escreverFrase("A UC não está associada a nenhum curso.");
        }
    }

    public List<Professor> listarProfessoresDisponiveis(Universidade universidade, Professor diretorAtual) {
        List<Professor> professoresDisponiveis = new ArrayList<>();
    
        for (Professor professor : universidade.getProfessores()) {
            if (!universidade.eDiretorDeCurso(professor)) {
                professoresDisponiveis.add(professor);
            }
        }
    
        return professoresDisponiveis;
    }
    

    public List<Professor> listarProfessoresRegentesDisponiveis(Universidade universidade) {
        List<Professor> professoresRegentesDisponiveis = new ArrayList<>();

        for (Professor professor : universidade.getProfessores()) {
            if (!universidade.eRegenteDeUC(professor)) {
                professoresRegentesDisponiveis.add(professor);
            }
        }
    
        return professoresRegentesDisponiveis;
    }    

    public List<Professor> guardarProfessoresDisponiveisUC(Universidade universidade, UnidadeCurricular uc) {
        List<Professor> professoresDisponiveis = new ArrayList<>();
        
        for (Professor professor : universidade.getProfessores()) {
            List<UnidadeCurricular> listaUCS = professor.getServicoDocente();
            if (!listaUCS.contains(uc)) {
                professoresDisponiveis.add(professor);
            }
        }
        
        return professoresDisponiveis;
    }

    public void exibirInformacoesUC(UnidadeCurricular uc) {
        System.out.println("Informações da UC:");
        System.out.println("\tDesignação: " + uc.getDesignacao());
        System.out.println("\tRegente: Número Mecanográfico: " + uc.getRegente().getNumeroMecanografico() + " | Nome: " + uc.getRegente().getNome());

        List<Professor> equipeDocente = uc.getEquipaDocente();
        System.out.println("\tEquipa Docente:");
        for (Professor professor : equipeDocente) {
            System.out.println("\t- Número Mecanográfico: " + professor.getNumeroMecanografico() + " | Nome: " + professor.getNome());
        }

        List<SumarioAula> sumarios = uc.getSumarios();
        System.out.println("\tSumários de Aula:");
        for (SumarioAula sumario : sumarios) {
            System.out.println("\t- Tipo: " + sumario.getTipo() + ", Data: " + sumario.getData());
        }
    }

    public void removerDiretorAtualDaLista(Universidade universidade, Professor diretorAtual) {
        List<Professor> professores = universidade.getProfessores();
    
        for (Professor professor : professores) {
            if (diretorAtual != null && professor.getNumeroMecanografico().equals(diretorAtual.getNumeroMecanografico())) {
                professores.remove(professor);
            }
        }
    }
    
    public void removerRegenteAtualDaLista(Universidade universidade, Professor regenteAtual) {
        List<Professor> professores = universidade.getProfessores();
    
        for (Professor professor : professores) {
            if (regenteAtual != null && professor.getNumeroMecanografico().equals(regenteAtual.getNumeroMecanografico())) {
                professores.remove(professor);
            }
        }
    }

    public String GerarNumeroMecanografico(Universidade universidade, int TIPO) {
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
            boolean alunosEncontrados = false;
            for (Curso curso : listaCursos) {
                List<Aluno> listaAlunos = curso.getAlunos();
                if (!listaAlunos.isEmpty()) {
                    alunosEncontrados = true;
                    for (Aluno aluno : listaAlunos) {
                        String numeroMecanografico = aluno.getNumeroMecanografico();
                        if (numeroMecanografico.compareTo(maiorNumeroMecanografico) > 0) {
                            maiorNumeroMecanografico = numeroMecanografico;
                        }
                    }
                }
            }
            if (alunosEncontrados) {
                int numero = Integer.parseInt(maiorNumeroMecanografico.substring(1));
                numero++;
                return String.format("A%06d", numero);
            } else {
                proximoNumeroMecanografico = "A000001";
            }
        }
        return proximoNumeroMecanografico;
    }
}
