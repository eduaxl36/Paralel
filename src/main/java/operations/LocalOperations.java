/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operations;

/**
 *
 * @author eduardo.fernando
 */
public interface LocalOperations {

    public String captarUltimoDiaProducao();

    public void criarDataProducao() throws Exception;

    public void carregarLog(String LogLocalFile) throws Exception;

}
