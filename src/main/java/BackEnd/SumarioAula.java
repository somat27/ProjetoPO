/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author tomas
 */
public class SumarioAula implements Serializable {
    private String titulo;
    private String tipo;
    private String resumo;
    private Date data;
    private Professor professor;
    private List<Aluno> alunos;
    
    public SumarioAula(String titulo, String tipo, String resumo, Date data, Professor professor) {
        this.titulo = titulo;
        this.tipo = tipo;
        this.resumo = resumo;
        this.data = data;
        this.professor = professor;
        this.alunos = new ArrayList<>();
    }
    
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
    
    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void marcarPresenca(Aluno aluno) {
        alunos.add(aluno);
    }
}
