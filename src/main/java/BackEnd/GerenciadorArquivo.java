/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tomas
 */
public class GerenciadorArquivo {
    public static void salvarObjetos(List<Object> objetos, String nomeArquivo) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            outputStream.writeObject(objetos);
            System.out.println("Objetos salvos com sucesso em '" + nomeArquivo + "'.");
        } catch (IOException e) {
            System.out.println("Erro ao salvar objetos em '" + nomeArquivo + "'.");
            e.printStackTrace();
        }
    }

    public static List<Object> carregarObjetos(String nomeArquivo) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            Object objetoLido = inputStream.readObject();

            if (objetoLido instanceof List) {
                return (List<Object>) objetoLido;
            } else {
                // Se o arquivo não contiver uma lista, retornar uma lista vazia
                return new ArrayList<>();
            }
        } catch (IOException | ClassNotFoundException e) {
            // Não há objetos no arquivo ou ocorreu um erro durante a leitura
            return new ArrayList<>();
        }
    }
    
    public static void adicionarProfessor(List<Object> objetos, Professor novoProfessor) {
        objetos.add(novoProfessor);
    }
    public static void removerProfessor(List<Object> objetos, String numeroMecanografico) {
        objetos.removeIf(objeto -> objeto instanceof Professor && ((Professor) objeto).getNumeroMecanografico().equals(numeroMecanografico));
    }
    
    public static void adicionarCurso(List<Object> objetos, Curso novocurso) {
        objetos.add(novocurso);
    }
    public static void removerCurso(List<Object> objetos, String designacaoCurso) {
        objetos.removeIf(objeto -> objeto instanceof Curso && ((Curso) objeto).getDesignacao().equals(designacaoCurso));
    }
}

