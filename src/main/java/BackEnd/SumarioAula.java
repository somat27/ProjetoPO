/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author tomas
 */
public class SumarioAula implements Serializable {
    private String titulo;
    private String tipo;
    private String sumario;
    private Date data;

    public SumarioAula(String titulo, String tipo, String sumario, Date data) {
        this.titulo = titulo;
        this.tipo = tipo;
        this.sumario = sumario;
        this.data = data;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSumario() {
        return sumario;
    }

    public void setSumario(String sumario) {
        this.sumario = sumario;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
