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
public class UnidadeCurricular implements Serializable {
    private String designacao;
    private Professor regente;
    private List<Professor> equipeDocente;
    private List<SumarioAula> sumarios;
    private List<Aluno> alunos;

    public UnidadeCurricular(String designacao, Professor regente) {
        this.designacao = designacao;
        this.regente = regente;
        this.equipeDocente = new ArrayList<>();
        this.sumarios = new ArrayList<>();
        this.alunos = new ArrayList<>();
    }
    
    public String getDesignacao() {
        return designacao;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }

    public Professor getRegente() {
        return regente;
    }

    public void setRegente(Professor regente) {
        this.regente = regente;
    }

    public List<Professor> getEquipeDocente() {
        return equipeDocente;
    }

    public List<SumarioAula> getSumarios() {
        return sumarios;
    }
    
    public List<Aluno> getAlunos() {
        return alunos;
    }

    // Método para adicionar sumário
    public void adicionarSumario(SumarioAula sumario) {
        sumarios.add(sumario);
    }

    // Método para remover sumário (se necessário)
    public void removerSumario(SumarioAula sumario) {
        sumarios.remove(sumario);
    }
    
    // Métodos para adicionar e remover professores da equipe docente
    public void adicionarProfessor(Professor professor) {
        equipeDocente.add(professor);
    }

    public void removerProfessor(Professor professor) {
        equipeDocente.remove(professor);
    }
    
    public void adicionarAluno(Aluno aluno) {
        alunos.add(aluno);
    }

    public void removerAluno(Aluno aluno) {
        alunos.remove(aluno);
    }
    
    /*
        Regente de UC
        Talvez usar um metodo para ir buscar a lista do curso e adicionar remover a partir de la
        Remover a parte do curso do aluno e apenas deixar o numero mecatrnico
    */
    // Adicionar alunos a unidade curricular ao qual é regente
    // Remover alunos a unidade curricular ao qual é regente
    // Consulta assiduidade de determinado aluno. 
}
