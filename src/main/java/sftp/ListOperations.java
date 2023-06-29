/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sftp;

import com.jcraft.jsch.JSchException;
import java.io.File;
import java.util.Map;


/**
 *
 * @author eduardo.fernando
 */
public class ListOperations {

    private final RemoteOperations Remote;
    private final File LstFile;
    

    public ListOperations(File LstFile) throws JSchException, InterruptedException {

        this.LstFile = LstFile;
        Remote = new RemoteOperations(new ConfiguracoesSFTPModel("LATAM", 0, "regional.latam", "gDItMm7K", "sftp.kantaribopemedia.com", 22));

    }

    public void downloadArquivoLst() throws Exception {

        Remote.downloadArquivoLst(LstFile.getName());

    }
    
    
    
    public Map obterListaArquivosDataArquivo() throws Exception{
    
    
    return Remote.obterListaArquivosDataArquivo();
    
    
    }
    
    
    
     

}
