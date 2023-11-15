/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

import java.io.Serializable;

/**
 *
 * @author tomas
 */
public class Aluno extends Pessoa implements Serializable {
    private Curso curso;

    public Aluno(String nome, String numeroMecanografico, Curso curso) {
        super(nome, numeroMecanografico);
        this.curso = curso;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
}

