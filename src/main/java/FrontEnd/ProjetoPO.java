//projeto PO


package FrontEnd;

import BackEnd.*;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ProjetoPO { 
    /**
     * Carregar base de dados
     */ 
    public static void carregarFicheiro(){
        
    }
    /**
     * Sistema de Login e Verificação
     */
    private static String verificarConta(String username, String password) {
        if(username.equals("admin") && password.equals("root")) {
            return "Admin";
        }else {
            return username;
        }
    }
    public static void paginaLogin() {
        Scanner scanner = new Scanner(System.in);
        ASCIIGenerator Logo = new ASCIIGenerator("LEGSI", 300, 100); // Ainda a ser testado
        System.out.println("\n\tGestão de Depertamento");
        System.out.println("\n");
        System.out.println("Login no Sistema de Depertamento");
        System.out.print("Numero Mecanografico: ");
        String username = scanner.nextLine();
        String password = "123";
        if(username.equals("admin")) {
            System.out.print("Password: ");
            password = scanner.nextLine();
        }
        if (verificarConta(username, password).equals("Admin")) {
            System.out.println("Login bem-sucedido!");
            PaginaMenus Menu = new PaginaMenus();
            Menu.Administrador();
        } else {
            try {
                System.out.println("Login falhou. Tente novamente.");
                Thread.sleep(3000);
                paginaLogin();
            } catch (InterruptedException e) {
                // Handle the exception if the sleep is interrupted
            }            
        }
    }
    /**
     * Main
     */
    public static void main(String[] args) {        
        /**
         * Base de dados
         */
        Professor professor1 = new Professor("Professor 1", "D10000", "01/01/2020");
        Professor professor2 = new Professor("Professor 2", "D10001", "01/01/2019");
        Curso curso1 = new Curso("LEGSI", professor1);
        UnidadeCurricular uc1 = new UnidadeCurricular("PO", professor1);
        UnidadeCurricular uc2 = new UnidadeCurricular("PMS", professor2);
        Aluno aluno1 = new Aluno("Aluno 1", "A103810", curso1);
        Aluno aluno2 = new Aluno("Aluno 2", "A103811", curso1);
        SumarioAula sumario1 = new SumarioAula("Aula 1", "Teórica", "Resumo da aula teórica", new Date(), professor1);
        SumarioAula sumario2 = new SumarioAula("Aula 2", "Prática", "Resumo da aula prática", new Date(), professor2);
        uc1.adicionarProfessor(professor1);
        uc1.adicionarProfessor(professor2);
        curso1.adicionarUC(uc1);
        curso1.adicionarUC(uc2);
        curso1.adicionarAluno(aluno1);
        curso1.adicionarAluno(aluno2);
        uc1.adicionarSumario(sumario1);
        uc1.adicionarSumario(sumario2);

        // Carregar objetos do arquivo
        List<Object> objetosLidos = GerenciadorArquivo.carregarObjetos("basededados.ser");

        // Adicionar objetos a lista de objetos
        GerenciadorArquivo.adicionarProfessor(objetosLidos, professor1);
        GerenciadorArquivo.adicionarProfessor(objetosLidos, professor2);
        GerenciadorArquivo.adicionarCurso(objetosLidos, curso1);
        
        System.out.println("Lista:");
        for (Object objetos : objetosLidos) {
            if (objetos instanceof Professor) {
                Professor x = (Professor) objetos;
                System.out.println("\t" + x.getNome() + " - " + x.getNumeroMecanografico());
            }
            if (objetos instanceof Curso) {
                Curso x = (Curso) objetos;
                System.out.println("\t" + x.getDesignacao() + " - " + x.getDiretorCurso().getNome());
            }
        } 
        
        // Remover um professor específico pelo número mecanográfico
        GerenciadorArquivo.removerProfessor(objetosLidos, "D10000");
        GerenciadorArquivo.removerCurso(objetosLidos, "LEGSI");

        // Salvar a lista de objetos atualizada no mesmo arquivo
        GerenciadorArquivo.salvarObjetos(objetosLidos, "basededados.ser");

        System.out.println("Lista:");
        for (Object objetos : objetosLidos) {
            if (objetos instanceof Professor) {
                Professor x = (Professor) objetos;
                System.out.println("\t" + x.getNome() + " - " + x.getNumeroMecanografico());
            }
            if (objetos instanceof Curso) {
                Curso x = (Curso) objetos;
                System.out.println("\t" + x.getDesignacao() + " - " + x.getDiretorCurso().getNome());
            }
        } 
        
//        for (Aluno aluno : curso1.getAlunos()) {
//            System.out.println("\tNome Aluno: " + aluno.getNome());
//            System.out.println("\tNumero Aluno: " + aluno.getNumeroMecanografico());
//            System.out.println("\tCurso Aluno: " + aluno.getCurso().getDesignacao());
//        }
//        System.out.println("Diretor Curso: " + curso1.getDiretorCurso().getNome());
//        System.out.println("UC's do Curso:");
//        for (UnidadeCurricular uc : curso1.getUcs()) {
//            System.out.println("Designação: " + uc.getDesignacao());
//            System.out.println("Regente: " + uc.getRegente().getNome());
//            for (Professor docente : uc.getEquipeDocente()) {
//                System.out.println("\tDocente: " + docente.getNome());
//            }
//            System.out.println("Sumários:");
//            for (SumarioAula sumario : uc.getSumarios()) {
//                System.out.println("\tTítulo: " + sumario.getTitulo());
//                System.out.println("\tTipo: " + sumario.getTipo());
//                System.out.println("\tResumo: " + sumario.getResumo());
//                System.out.println("\tData: " + sumario.getData());
//                System.out.println("\tProfessor: " + sumario.getProfessor().getNome());
//                System.out.println();
//            }
//        }
        /**
         * Teste de Login
         */
        //paginaLogin();
    }
}
    