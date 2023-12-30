/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author tomas
 */
public class Professor extends Pessoa {
    private String dataInicioFuncoes;
    private List<UnidadeCurricular> servicoDocente = new ArrayList<>();

    public Professor(String nome, String numeroMecanografico, String dataInicioFuncoes) {
        super(nome, numeroMecanografico);
        this.dataInicioFuncoes = dataInicioFuncoes;
    }


    public String getDataInicioFuncoes() {
        return dataInicioFuncoes;
    }

    public void setDataInicioFuncoes(String dataInicioFuncoes) {
        this.dataInicioFuncoes = dataInicioFuncoes;
    }

    public List<UnidadeCurricular> getServicoDocente() {
        return servicoDocente;
    }

    public void adicionarAoServicoDocente(UnidadeCurricular uc) {
        servicoDocente.add(uc);
    }

    public void removerDoServicoDocente(UnidadeCurricular uc) {
        servicoDocente.remove(uc);
    }
    
    public void criarSumario(UnidadeCurricular uc, SumarioAula novoSumario) {
        // Verifica se o professor está associado à UC
        if (servicoDocente.contains(uc)) {
            uc.adicionarSumario(novoSumario);
            System.out.println("Sumário criado com sucesso.");
        } else {
            System.out.println("Erro: Professor não está associado à UC.");
        }
    }
}
