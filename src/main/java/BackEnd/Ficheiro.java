/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;


import java.io.*;

/**
 *
 * @author tomas
 */
public class Ficheiro {
    private final File file;

    public Ficheiro(String file) {
        this.file = new File(file);
    }

    public File getFile() {
        return file;
    }
    
    public static void guarda_dados(Universidade universidade) {
        try (ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream("Repositorio.bin"))) {
            objectOut.writeObject(universidade);
            System.out.println("O estado foi salvo com sucesso no arquivo.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Universidade carregar_dados() {
        Universidade universidade = null;
        
        try (ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream("Repositorio.bin"))) {
            universidade = (Universidade) objectIn.readObject();
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        return universidade;
    }
    
    public static void salvarAdministrador(Administrador administrador) {
        try (ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream("Administrador.bin"))) {
            objectOut.writeObject(administrador);
            System.out.println("Administrador salvo com sucesso no arquivo.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static Administrador carregarAdministrador() {
        try (ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream("Administrador.bin"))) {
            return (Administrador) objectIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
}

