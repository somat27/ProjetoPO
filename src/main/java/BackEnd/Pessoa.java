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
public class Pessoa implements Serializable {
    private String nome;
    private String numeroMecanografico;

    public Pessoa(String nome, String numeroMecanografico) {
        this.nome = nome;
        this.numeroMecanografico = numeroMecanografico;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumeroMecanografico() {
        return numeroMecanografico;
    }

    public void setNumeroMecanografico(String numeroMecanografico) {
        this.numeroMecanografico = numeroMecanografico;
    }
}
