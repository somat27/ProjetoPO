/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author tomas
 */
public class Professor extends Pessoa implements Serializable {
    private String dataInicioFuncoes;
    private List<UnidadeCurricular> servicoDocente;

    public Professor(String nome, String numeroMecanografico, String dataInicioFuncoes) {
        super(nome, numeroMecanografico);
        this.dataInicioFuncoes = dataInicioFuncoes;
    }

    // Adicione outros métodos e atributos conforme necessário

    public String getDataInicioFuncoes() {
        return dataInicioFuncoes;
    }

    public void setDataInicioFuncoes(String dataInicioFuncoes) {
        this.dataInicioFuncoes = dataInicioFuncoes;
    }

    public List<UnidadeCurricular> getServicoDocente() {
        return servicoDocente;
    }

    public void setServicoDocente(List<UnidadeCurricular> servicoDocente) {
        this.servicoDocente = servicoDocente;
    }
    
    public void criarSumario(UnidadeCurricular uc, String titulo, String tipo, String sumario) {
        // Verifica se o professor está associado à UC
        if (servicoDocente.contains(uc)) {
            SumarioAula novoSumario = new SumarioAula(titulo, tipo, sumario, new Date());
            uc.adicionarSumario(novoSumario);
            System.out.println("Sumário criado com sucesso.");
        } else {
            System.out.println("Erro: Professor não está associado à UC.");
        }
    }
}
