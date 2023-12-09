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
        
//        Professor novoProfessor = new Professor("Professor 123", "123", "01/03/2022");
//        universidade.adicionarProfessor(novoProfessor);
//        
//        System.out.println("Lista de Professores antes da remoção:");
//        for (Professor professor : universidade.getProfessores()) {
//            System.out.println(professor.getNome());
//        }
//        
//        universidade.removerProfessor("789");
//
//        System.out.println("\nLista de Professores após a remoção:");
//        for (Professor professor : universidade.getProfessores()) {
//            System.out.println(professor.getNome());
//        }
//
//        ficheiro.guarda_dados(universidade);
    }
}
