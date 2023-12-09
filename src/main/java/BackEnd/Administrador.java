/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author tomas
 */
public class Administrador implements Serializable {
    private String username;
    private String password;

    public Administrador(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    
    // Adicionar um novo professor à lista de professores da Universidade
    public void adicionarProfessor(Universidade universidade, Professor novoProfessor) {
        universidade.adicionarProfessor(novoProfessor);
        System.out.println("Professor adicionado com sucesso à Universidade.");
    }

    // Apagar um professor da lista de professores da Universidade
    public void apagarProfessor(Universidade universidade, String numeroMecanografico) {
        if (universidade.removerProfessor(numeroMecanografico)) {
            System.out.println("Professor removido com sucesso da Universidade.");
        } else {
            System.out.println("Professor não encontrado na lista da Universidade.");
        }
    }

    // Alterar informações de um professor na lista de professores da Universidade
    public void alterarInformacoesProfessor(Universidade universidade, String numeroMecanografico, Professor novasInformacoes) {
        if (universidade.alterarInformacoesProfessor(numeroMecanografico, novasInformacoes)) {
            System.out.println("Informações do professor alteradas com sucesso na Universidade.");
        } else {
            System.out.println("Professor não encontrado na lista da Universidade.");
        }
    }
}
