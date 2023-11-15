/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

import java.util.List;
import java.io.Serializable;

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
    
    public String getDataInicioFuncoes() {
        return dataInicioFuncoes;
    }
    public void setDataInicioFuncoes(String dataInicioFuncoes) {
        this.dataInicioFuncoes = dataInicioFuncoes;
    }
    
    public void adicionarUC(UnidadeCurricular uc) {
        servicoDocente.add(uc);
    }
    public void removerUC(UnidadeCurricular uc) {
        servicoDocente.remove(uc);
    }
    
    // Criar Sumario
    // Consultar lista de sumarios por UC e por tipo de aula
    // Consultar serviço docente 
}
