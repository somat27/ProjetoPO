/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tomas
 */
public class Universidade implements Serializable {

    private List<Professor> professores = new ArrayList<>();
    private List<Curso> cursos = new ArrayList<>();

    public Universidade() {
    }

    public void adicionarProfessor(Professor professor) {
        professores.add(professor);
    }

    public List<Professor> getProfessores() {
        return professores;
    }

    public void adicionarCurso(Curso curso) {
        cursos.add(curso);
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void adicionarUC(UnidadeCurricular uc, Curso curso) {
        if (curso != null) {
            curso.adicionarUC(uc);
        } else {
            System.out.println("Erro: Curso nulo. Não foi possível adicionar UC.");
        }
    }

    public void adicionarAluno(Aluno aluno, Curso curso) {
        curso.adicionarAluno(aluno);
    }

    public List<UnidadeCurricular> getUCs() {
        List<UnidadeCurricular> ucs = new ArrayList<>();
        for (Curso curso : cursos) {
            ucs.addAll(curso.getUCs());
        }
        return ucs;
    }

    public List<Aluno> getAlunos() {
        List<Aluno> alunos = new ArrayList<>();
        for (Curso curso : cursos) {
            alunos.addAll(curso.getAlunos());
        }
        return alunos;
    }

    public boolean removerProfessor(String numeroMecanografico) {
        return professores.removeIf(professor -> professor.getNumeroMecanografico().equals(numeroMecanografico));
    }

    public boolean alterarInformacoesProfessor(String numeroMecanografico, String novoNome) {
        for (Professor professor : professores) {
            if (professor.getNumeroMecanografico().equals(numeroMecanografico)) {
                professor.setNome(novoNome);
                return true;
            }
        }
        return false;
    }

    public void removerAluno(String numeroMecanografico) {
        cursos.forEach(curso -> curso.removerAluno(numeroMecanografico));
    }

    public void removerUC(String designacao) {
        cursos.forEach(curso -> curso.removerUC(designacao));
    }

    public Professor encontrarProfessor(String numeroMecanografico) {
        for (Professor professor : professores) {
            if (professor.getNumeroMecanografico().equals(numeroMecanografico)) {
                return professor;
            }
        }
        return null;
    }

    public boolean verificarProfessor(String numeroMecanografico) {
        for (Professor professor : professores) {
            if (professor.getNumeroMecanografico().equals(numeroMecanografico)) {
                return true;
            }
        }
        return false;
    }

    public Curso encontrarCurso(String nomeCurso) {
        for (Curso curso : cursos) {
            if (curso.getDesignacao().equals(nomeCurso)) {
                return curso;
            }
        }
        return null;
    }

    public void removerCurso(String designacaoCurso) {
        Curso cursoRemover = null;
        for (Curso curso : cursos) {
            if (curso.getDesignacao().equals(designacaoCurso)) {
                cursoRemover = curso;
                break;
            }
        }

        if (cursoRemover != null) {
            cursos.remove(cursoRemover);
        }
    }

    public Curso encontrarCursoPorDesignacao(String designacaoCurso) {
        for (Curso curso : cursos) {
            if (curso.getDesignacao().equals(designacaoCurso)) {
                return curso;
            }
        }
        return null;
    }

    public UnidadeCurricular encontrarUCporDesignacao(String designacao) {
        for (Curso curso : cursos) {
            List<UnidadeCurricular> ucs = curso.getUCs();
            for (UnidadeCurricular uc : ucs) {
                if (uc.getDesignacao().equalsIgnoreCase(designacao)) {
                    return uc;
                }
            }
        }
        return null;
    }

    public UnidadeCurricular encontrarUCporCurso(Curso curso, String designacaoCurso) {
        List<UnidadeCurricular> ucs = curso.getUCs();
        for (UnidadeCurricular uc : ucs) {
            if (uc.getDesignacao().equalsIgnoreCase(designacaoCurso)) {
                return uc;
            }
        }
        return null;
    }

    public boolean eDiretorDeCurso(Professor professor) {
        for (Curso curso : cursos) {
            if (curso.getDiretorCurso() != null && curso.getDiretorCurso().equals(professor)) {
                return true;
            }
        }
        return false;
    }

    public boolean eRegenteDeUC(Professor professor) {
        List<UnidadeCurricular> UCs = getUCs();
        for (UnidadeCurricular uc : UCs) {
            if (uc.getRegente() != null && uc.getRegente().equals(professor)) {
                return true;
            }
        }
        return false;
    }

}
