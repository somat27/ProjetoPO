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
public class Curso implements Serializable {
    private String designacao;
    private Professor diretorCurso;
    private List<UnidadeCurricular> ucs;
    private List<Aluno> alunos;

    public Curso(String designacao, Professor diretorCurso) {
        this.designacao = designacao;
        this.diretorCurso = diretorCurso;
        this.ucs = new ArrayList<>();
        this.alunos = new ArrayList<>();
    }
    
    public String getDesignacao() {
        return designacao;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }

    public Professor getDiretorCurso() {
        return diretorCurso;
    }

    public void setDiretorCurso(Professor diretorCurso) {
        this.diretorCurso = diretorCurso;
    }

    public List<UnidadeCurricular> getUcs() {
        return ucs;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }
    
    // Métodos para adicionar e remover UCs e alunos do curso
    public void adicionarUC(UnidadeCurricular uc) {
        ucs.add(uc);
    }

    public void removerUC(UnidadeCurricular uc) {
        ucs.remove(uc);
    }

    public void adicionarAluno(Aluno aluno) {
        alunos.add(aluno);
    }

    public void removerAluno(Aluno aluno) {
        alunos.remove(aluno);
    }
    
    /*
        Diretor de Curso
        Talvez usar um metodo para ir buscar a lista do curso e adicionar remover a partir de la
        Remover a parte do curso do aluno e apenas deixar o numero mecatrnico
    */
    // Alterar Designação do curso
    // Listar numero de professores do curso
    // Listar numero de alunos do curso 
}
