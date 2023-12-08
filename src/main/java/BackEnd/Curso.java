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
    private List<UnidadeCurricular> ucs = new ArrayList<>();
    private List<Aluno> alunos = new ArrayList<>();  // Adicionando lista de alunos
    private Professor diretorCurso;

    public Curso(String designacao) {
        this.designacao = designacao;
    }

    // Adicione outros métodos e atributos conforme necessário

    public String getDesignacao() {
        return designacao;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }

    public List<UnidadeCurricular> getUCs() {
        return ucs;
    }

    public void setUCs(List<UnidadeCurricular> ucs) {
        this.ucs = ucs;
    }

    public Professor getDiretorCurso() {
        return diretorCurso;
    }

    public void setDiretorCurso(Professor diretorCurso) {
        this.diretorCurso = diretorCurso;
    }

    public void adicionarAluno(Aluno aluno) {
        alunos.add(aluno);
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }
    
    public void adicionarUC(UnidadeCurricular uc) {
        ucs.add(uc);
    }
}
