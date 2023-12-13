/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FrontEnd;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
/**
 *
 * @author tomas
 */
public class Consola {
    private final Scanner Leitor = new Scanner(System.in);
    
    public void erroOpcaoInvalida() {
        System.err.println("\nErro\nNumero invalido\nPor favor insira um numero dos indicados\nPressione enter para continuar...");
        Leitor.nextLine();
    }

    public void escreverSeparador() {
        System.err.println("-------------------------------------------------");
        System.err.flush(); //limpar o terminal 
    }

    public void escreverErro(String aviso) {
        System.err.println(aviso);
        System.err.flush(); //limpar o terminal 
    }

    public void escreverFrase(String frase) {
        System.out.println(frase);
        System.out.flush(); //limpar o terminal 
    }

    public String lerString(String frase) {
        System.out.print(frase);
        return Leitor.nextLine();
    }

    public String lerStringLowerCase(String frase) {
        System.out.print(frase);
        String input = Leitor.nextLine();
        return input.toLowerCase();
    }

    public String lerStringUpperCase(String frase) {
        System.out.print(frase);
        String input = Leitor.nextLine();
        return input.toUpperCase();
    }

    public int lerInteiro(String string) {
        Integer numero = null;
        String texto;

        do {
            System.out.print(string);
            texto = Leitor.nextLine();

            try {
                numero = Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                escreverErro(texto);
                erroOpcaoInvalida();
            }

        } while (numero == null);

        return numero;
    }
    
    public void PressEntertoContinue(){
        System.out.println("Pressione Enter para continuar...");
        Leitor.nextLine();
    }
    
    public String getHoraAtual() {
        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return agora.format(formatter);
    }
}
