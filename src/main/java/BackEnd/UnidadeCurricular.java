/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tomas
 */
public class UnidadeCurricular implements Serializable {
    private String designacao;
    private Professor regente;
    private List<Professor> equipeDocente = new ArrayList<>();
    private List<SumarioAula> sumarios = new ArrayList<>();

    public UnidadeCurricular(String designacao, Professor regente) {
        this.designacao = designacao;
        this.regente = regente;
    }

    // Adicione outros métodos e atributos conforme necessário

    public String getDesignacao() {
        return designacao;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }

    public Professor getRegente() {
        return regente;
    }

    public void setRegente(Professor regente) {
        this.regente = regente;
    }
    
    public void adicionarSumario(SumarioAula sumario) {
        sumarios.add(sumario);
    }

    public List<SumarioAula> getSumarios() {
        return sumarios;
    }

    public List<SumarioAula> consultarSumariosPorTipoAula(String tipoAula) {
        // Filtrar sumários por tipo de aula
        return sumarios.stream()
                .filter(sumario -> sumario.getTipo().equals(tipoAula))
                .toList();
    }

    public List<Professor> getEquipaDocente() {
        return equipeDocente;
    }

    public void adicionarEquipaDocente(Professor professor) {
        equipeDocente.add(professor);
    }

    public void removerEquipaDocente(Professor professor) {
        equipeDocente.remove(professor);
    }
}
