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
    private List<Aluno> alunos = new ArrayList<>();
    private Professor diretorCurso;

    public Curso(String designacao, Professor diretorCurso) {
        this.designacao = designacao;
        this.diretorCurso = diretorCurso;
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
    
    public void removerAluno(String numeroMecanografico) {
        alunos.removeIf(aluno -> aluno.getNumeroMecanografico().equals(numeroMecanografico));
    }

    public void removerUC(String designacao) {
        ucs.removeIf(uc -> uc.getDesignacao().equals(designacao));
    }
    
    public int getNumeroProfessores() {
        int count = 0;
        for (UnidadeCurricular uc : ucs) {
            count += uc.getEquipaDocente().size();
        }
        return count;
    }
}
