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

  public void guarda_dados(Universidade universidade) {
    try (
      ObjectOutputStream objectOut = new ObjectOutputStream(
        new FileOutputStream("Repositorio.ser", false)
      )
    ) { // false para substituir o ficheiro e nao acrescentar, visto que estamos a ler para a classe Universidade
      objectOut.writeObject(universidade);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public Universidade carregar_dados() {
    Universidade universidade = null;

    try (
      ObjectInputStream objectIn = new ObjectInputStream(
        new FileInputStream("Repositorio.ser")
      )
    ) {
      universidade = (Universidade) objectIn.readObject();
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }

    return universidade;
  }

  public static void salvarAdministrador(Administrador administrador) {
    try (
      ObjectOutputStream objectOut = new ObjectOutputStream(
        new FileOutputStream("Administrador.ser", false)
      )
    ) {
      objectOut.writeObject(administrador);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static Administrador carregarAdministrador() {
    try (
      ObjectInputStream objectIn = new ObjectInputStream(
        new FileInputStream("Administrador.ser")
      )
    ) {
      return (Administrador) objectIn.readObject();
    } catch (IOException | ClassNotFoundException e) {
      return null;
    }
  }
}
