/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FrontEnd;

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

    public void escreverAviso(String aviso) {
        System.err.print(aviso + "\n");
        System.err.flush(); //limpar o terminal 
    }

    public void escreverFrase(String frase) {
        System.out.println(frase);
        System.out.flush(); //limpar o terminal 
    }

    public String lerString() {
        return Leitor.nextLine();
    }

    public String lerStringFrase(String frase) {
        escreverFrase(frase);
        return Leitor.nextLine();
    }

    public int lerInteiro(String string) {
        Integer numero = null;
        String texto;

        do {
            escreverFrase(string);
            texto = Leitor.nextLine();

            try {
                numero = Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                escreverAviso(texto);
                erroOpcaoInvalida();
            }

        } while (numero == null);

        return numero;
    }
}
