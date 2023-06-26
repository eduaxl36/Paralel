/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sftp;

import br.com.kantar.pathManager.Manager;
import com.jcraft.jsch.JSchException;
import javax.swing.JOptionPane;

/**
 *
 * @author Eduardo.Fernando
 */
public class RemoteOperations {

    private ConfiguracoesSFTPModel ModeloConexao = null;
    private SFTPConnection Connection = null;
    
    

    public static final String LOCAL_FLAG = Manager.getRoot().get("caminho_local_temp_flag");
    public static final String REMOTE_FLAG = Manager.getRoot().get("caminho_ftp_flag");

    public static final String LOCAL_LOG = Manager.getRoot().get("caminho_local_temp_logFile");
    public static final String REMOTE_LOG = Manager.getRoot().get("caminho_ftp_log");

    public static final String PRODUCAO_DIA_LOCAL = Manager.getRoot().get("caminho_local_temp_producao_dia");
    public static final String PRODUCAO_DIA_REMOTO = Manager.getRoot().get("caminho_ftp_producao_dia_completo_arquivo");

    public static final String ARQUIVO_DARKLIST_REMOTO = Manager.getRoot().get("caminho_ftp_darklist");
    public static final String ARQUIVO_DARKLIST_LOCAL = Manager.getRoot().get("caminho_oficial_temp_darkFile_servidor");
    public static final String ARQUIVO_DARKLIST_LOCAL_TEMP = Manager.getRoot().get("caminho_local_temp_darkFile");

    
    
    
    public RemoteOperations(ConfiguracoesSFTPModel ModeloConexao) throws JSchException, InterruptedException {

        this.ModeloConexao = ModeloConexao;
        this.Connection = new SFTPConnection(ModeloConexao);

        Connection.obterSessao();
        Connection.Conexao();
    }

    public RemoteOperations() {
    }

    public void downloadFlag() throws Exception {

        Connection.downloadArquivo(LOCAL_FLAG, REMOTE_FLAG);

    }

    public void uploadFlag() throws Exception {

        Connection.uploadArquivo(LOCAL_FLAG, REMOTE_FLAG);

    }

    public void uploadLogdia(String data) throws Exception {

        Connection.uploadArquivo(LOCAL_FLAG + "/" + data + "_log.csv", REMOTE_FLAG + "/" + data + "_log.csv");

    }

    public void downloadNumeralDia() throws Exception {

        Connection.downloadArquivo(PRODUCAO_DIA_REMOTO, PRODUCAO_DIA_LOCAL);

    }

    public void downloadArquivoLista(String LogFile) throws Exception {

        Connection.downloadArquivo(REMOTE_LOG + LogFile, LOCAL_LOG + LogFile);

    }


    public void downloadArquivoDarkListServidor(String DarklistFile) throws Exception {

        Connection.downloadArquivo(ARQUIVO_DARKLIST_REMOTO + DarklistFile, ARQUIVO_DARKLIST_LOCAL + DarklistFile.replaceAll("\\d{8}_", ""));

    }

    public void downloadArquivoDarkList(String DarklistFile) throws Exception {

       
        
        Connection.downloadArquivo(ARQUIVO_DARKLIST_REMOTO+DarklistFile, ARQUIVO_DARKLIST_LOCAL_TEMP);

    }

    public static void main(String[] args) throws Exception {

        ConfiguracoesSFTPModel sd = new ConfiguracoesSFTPModel("LATAM", 0, "regional.latam", "gDItMm7K", "sftp.kantaribopemedia.com", 22);

        RemoteOperations rp = new RemoteOperations(sd);
//        rp.downloadArquivoLista("20230619");
        
        
        
        
        
    }

}
