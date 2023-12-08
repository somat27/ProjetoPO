package FrontEnd;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import BackEnd.*;

/**
 *
 * @author tomas
 */
public class Menus {
    private Universidade universidade;
    private Consola consola;
    private Ficheiro ficheiro;
    private Administrador administrador;
    
    public Menus(Universidade universidade, Consola consola, Ficheiro ficheiro, Administrador administrador) {
        this.universidade = universidade;
        this.consola = consola;
        this.ficheiro = ficheiro;
        this.administrador = administrador;
    }
    
    public void MenuLogin() {
        System.out.println("Bem-vindo ao sistema!");
        String usernameInput = null;
        String passwordInput = null;
        boolean loggedIn = false;
        while (!loggedIn) {
            System.out.print("Username: ");
            usernameInput = consola.lerString();
            System.out.print("Password: ");
            passwordInput = consola.lerString();

            if (usernameInput.equals(administrador.getUsername()) && passwordInput.equals(administrador.getPassword())) {
                consola.escreverFrase("Login bem-sucedido!");
                loggedIn = true;
            } else {
                consola.escreverAviso("Credenciais inválidas. Tente novamente.");
            }
        }
        if (usernameInput.equals(administrador.getUsername()) && loggedIn) {
            MenuAdministrador(); 
        }
    }
    
    public void MenuAdministrador() {
        System.out.println("Menu Administrador");
        
        System.out.println("Lista de Professores:");
        for (Professor professor : universidade.getProfessores()) {
            System.out.println(professor.getNome());
        }

        System.out.println("\nLista de UCs:");
        for (UnidadeCurricular uc : universidade.getUCs()) {
            System.out.println(uc.getDesignacao());
        }

        System.out.println("\nLista de Alunos:");
        for (Aluno aluno : universidade.getAlunos()) {
            System.out.println(aluno.getNome());
        }
    }
}
