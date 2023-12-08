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
        curso.adicionarUC(uc);
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
}
