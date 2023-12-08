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

    public Aluno(String nome, String numeroMecanografico, UnidadeCurricular uc) {
        super(nome, numeroMecanografico);
        // Atribuir o curso com base na UC (exemplo simples, ajuste conforme necessário)
        this.curso = new Curso("Curso Padrão");
    }

    // Adicione outros métodos e atributos conforme necessário

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
}

