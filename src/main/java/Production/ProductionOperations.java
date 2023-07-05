/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Production;

import pathManager.Manager;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import static sftp.Inicializacao.Remote;
import static sftp.Inicializacao.iniciaConexao;

/**
 *
 * @author Eduardo.Fernando
 */
public class ProductionOperations {


    private static ProductionOperations Instancia;

    private ProductionOperations() {
    }

    public static ProductionOperations getInstance() throws Exception {

        if (Instancia == null) {
            synchronized (ProductionOperations.class) {
                if (Instancia == null) {
                    Instancia = new ProductionOperations();
                }
            }
        }
        return Instancia;

    }

    public String captarUltimoDiaProducao() {

        List<LocalDate> datas = new ArrayList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
             
        
        File pasta = new File(Manager.getRoot().get("caminho_oficial_out_telepanel_panama_servidor"));
        
        File[] arquivos = pasta.listFiles((dir, nome) -> nome.matches("\\d{8}\\.vsl"));

        for (File x : arquivos) {

            datas.add(LocalDate.parse(x.getName().substring(0, 8), formatter));

        }

        Collections.sort(datas);

        return Collections.max(datas).toString().replaceAll("-", "");

    }

    public void criarDataProducao() throws IOException, Exception {

        FileUtils.write(new File(Manager.getRoot().get("caminho_local_temp_producao_dia")), captarUltimoDiaProducao());

    }
    
    
    
    public void uploadDiaProducao(){
    
    
        try {
            Remote.uploadDiaProducao();
           uploadUltimoDark(captarUltimoDiaProducao());
        } catch (Exception ex) {
            Logger.getLogger(ProductionOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    
    }
    
    
    
    
    public void uploadUltimoDark(String data) throws Exception{
    
    
    Remote.uploadUltimoDiaLST(data);
    
    
    }
    
    
    
    
    
    
    public void downloadUltimpDiaDark(){
    
    
        try {
            Remote.downloadArquivoLst(captarUltimoDiaProducao());
        } catch (Exception ex) {
            Logger.getLogger(ProductionOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    }
    
    
    
    
    
    public static void main(String[] args) throws Exception {
        
         iniciaConexao();
        
        new ProductionOperations().criarDataProducao();
        new ProductionOperations().uploadDiaProducao();
      
        
        
        
//        new ProductionOperations().downloadUltimpDiaDark();
        
        
    }
    
    

}
