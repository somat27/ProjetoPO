//projeto PO


package FrontEnd;

import BackEnd.*;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ProjetoPO { 
    public static void main(String[] args) throws Exception {
        Universidade universidade;
        Consola consola = new Consola();
        Ficheiro ficheiro = new Ficheiro("Repositorio.ser");
        
        if (!ficheiro.getFile().exists()) {
            universidade = new Universidade();
        } else {
            universidade = ficheiro.carregar_dados();
        }
        
        Administrador administrador = carregarOuCriarAdministrador();
        
        Menus menu = new Menus(universidade, consola, ficheiro, administrador);
        menu.MenuLogin();
    }
    
    private static Administrador carregarOuCriarAdministrador() { //private porque apenas é usado aqui !!
        Administrador administrador = Ficheiro.carregarAdministrador();

        if (administrador == null) {
            System.out.println("Administrador não encontrado. Criando um novo...");
            administrador = new Administrador("admin", "root");
            Ficheiro.salvarAdministrador(administrador);
        }

        return administrador;
    }
}
