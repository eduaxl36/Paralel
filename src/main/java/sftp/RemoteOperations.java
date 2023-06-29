/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sftp;


import Util.MainTableUtil;
import pathManager.Manager;
import com.jcraft.jsch.JSchException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import msgs.Pbar;
import viewClient.MenuFile;

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
    public static final String LISTA_DARKLSIT = Manager.getRoot().get("caminho_ftp_darklist");

    public static final String ARQUIVO_DARKLIST_REMOTO = Manager.getRoot().get("caminho_ftp_darklist");
    public static final String ARQUIVO_DARKLIST_LOCAL = Manager.getRoot().get("caminho_oficial_temp_darkFile_servidor");
    public static final String ARQUIVO_DARKLIST_LOCAL_TEMP = Manager.getRoot().get("caminho_local_temp_darkFile");
    private static String LISTA_LOG= Manager.getRoot().get("caminho_ftp_log");
    
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

        Connection.uploadArquivo(LOCAL_LOG + "/" + data + "_log.csv", REMOTE_LOG + "/" + data + "_log.csv");

    }

    public void downloadNumeralDia() throws Exception {

        Connection.downloadArquivo(PRODUCAO_DIA_REMOTO, PRODUCAO_DIA_LOCAL);

    }

    public void downloadArquivoLogHistorico(String LogFile) throws Exception {

        Connection.downloadArquivo(REMOTE_LOG + LogFile, LOCAL_LOG + LogFile);

    }

    public void downloadArquivoLst(String DarklistFile) throws Exception {

        Connection.downloadArquivo(ARQUIVO_DARKLIST_REMOTO + DarklistFile, ARQUIVO_DARKLIST_LOCAL_TEMP);

    }

    public Map obterListaArquivosDataArquivo() throws InterruptedException, IOException, ParseException, Exception {

        return Connection.obterListaArquivo(LISTA_DARKLSIT);

    }
    public Map obterListaArquivosLogDataArquivo() throws InterruptedException, IOException, ParseException, Exception {

        return Connection.obterListaArquivo(LISTA_LOG);

    }
    
    
       public void uploadLogAlteracoes(JTable Tabela,String DataProducao) {
        int resposta = JOptionPane.showConfirmDialog(null, "Desea enviar los cambios para la equipe regional?", "Confirmacion", JOptionPane.YES_OPTION);

        if (resposta == JOptionPane.YES_OPTION) {
            new Thread(() -> {
                try {

                
                    
                    Pbar.Progresso.setVisible(true);
                    
                    String arquivoSalvoLog = Manager.getRoot().get("caminho_local_temp_logFile") + DataProducao.replaceAll("-", "") + "_log.csv";
                    new MainTableUtil(Tabela).exportarConteudoParaCsv(arquivoSalvoLog);
                    uploadLogdia(DataProducao.replaceAll("-", ""));
                    
                    Pbar.Progresso.setVisible(false);
                } catch (Exception ex) {
                    Logger.getLogger(MenuFile.class.getName()).log(Level.SEVERE, null, ex);
                }
            }).start();
        } else if (resposta == JOptionPane.NO_OPTION) {
            JOptionPane.showMessageDialog(null, "Voce selecionou 'Não'.");
        }
    }
    
    

}
