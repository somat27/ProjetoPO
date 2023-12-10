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
    public Aluno(String nome, String numeroMecanografico) {
        super(nome, numeroMecanografico);
    }
}

