/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package log;

import pathManager.Manager;
import com.jcraft.jsch.JSchException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map;
import sftp.ConfiguracoesSFTPModel;
import sftp.RemoteOperations;

/**
 *
 * @author eduardo.fernando
 */
public class LogOperations {

    private final RemoteOperations Remote;
    private final String ArquivoLog;
    private String DataProducaoCorrente;
   

    public LogOperations(String ArquivoLog, String DataProducaoCorrente) throws JSchException, InterruptedException {

        Remote = new RemoteOperations(new ConfiguracoesSFTPModel("LATAM", 0, "regional.latam", "gDItMm7K", "sftp.kantaribopemedia.com", 22));
        this.ArquivoLog = ArquivoLog;
    }

    public void downloadLog() throws Exception {

        Remote.downloadArquivoLogHistorico(ArquivoLog);

    }

    public void uploadArquivoLog() throws Exception {

        Remote.uploadLogdia(DataProducaoCorrente);

    }
    
    
        public Map obterListaArquivosDataArquivo() throws InterruptedException, IOException, ParseException, Exception {

        return Remote.obterListaArquivosLogDataArquivo();

    }
}
