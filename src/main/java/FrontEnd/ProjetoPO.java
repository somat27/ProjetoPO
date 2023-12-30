//projeto PO
// O fernando esteve aqui!!!
package FrontEnd;

import BackEnd.*;

public class ProjetoPO {

    private static Administrador carregarOuCriarAdministrador() { //private porque apenas é usado aqui !!
        Administrador administrador = Ficheiro.carregarAdministrador();

        if (administrador == null) {
            System.out.println("Administrador não encontrado. Criando um novo...");
            administrador = new Administrador("admin", "root");
            Ficheiro.salvarAdministrador(administrador);
        }

        return administrador;
    }

    public static void main(String[] args) throws Exception {
        Universidade universidade;
        Consola consola = new Consola();
        Ficheiro ficheiro = new Ficheiro("Repositorio.ser");

        if (!ficheiro.getFile().exists()) {
            universidade = new Universidade();
        } else {
            universidade = ficheiro.carregar_dados();
            if (universidade == null) { // se não houver nada no ficheiro
                universidade = new Universidade();
            }
        }

        Administrador administrador = carregarOuCriarAdministrador();
        Menus menu = new Menus(universidade, consola, ficheiro, administrador);
        menu.MenuLogin();
    }
}
