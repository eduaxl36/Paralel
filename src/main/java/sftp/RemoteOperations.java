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
import pathManager.Roots;
import viewClient.MenuFile;

/**
 *
 * @author Eduardo.Fernando
 */
public class RemoteOperations {

    private ConfiguracoesSFTPModel ModeloConexao = null;
    private SFTPConnection Connection = null;

    public RemoteOperations(ConfiguracoesSFTPModel ModeloConexao) throws JSchException, InterruptedException {

        this.ModeloConexao = ModeloConexao;
        this.Connection = new SFTPConnection(ModeloConexao);

        Connection.obterSessao();
        Connection.Conexao();
    }

    public void downloadArquivoLogHistorico(String LogFile) throws Exception {

        Connection.downloadArquivo(Roots.PASTA_FTP_LOG.getCaminho() + LogFile, Roots.PASTA_TEMP_LOG_FILE.getCaminho() + LogFile);

    }

    public void uploadLogdia(String data) throws Exception {

        Connection.uploadArquivo(Roots.PASTA_TEMP_LOG_FILE.getCaminho() + "/" + data + "_log.csv", Roots.PASTA_FTP_LOG.getCaminho() + "/" + data + "_log.csv");

    }

    public Map obterListaArquivosLogDataArquivo() throws InterruptedException, IOException, ParseException, Exception {

        return Connection.obterListaArquivo(Roots.PASTA_FTP_LOG.getCaminho());

    }

    public void uploadLogAlteracoes(JTable Tabela, String DataProducao) {
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

    public void uploadFlag() throws Exception {

        Connection.uploadArquivo(Roots.ARQUIVO_TEMP_FLAG.getCaminho(), Roots.PASTA_FTP_FLAG.getCaminho());

    }

    public void downloadArquivoLst(String DarklistFile) throws Exception {

        Connection.downloadArquivo(Roots.PASTA_FTP_DARKLIST.getCaminho() + DarklistFile, Roots.PASTA_TEMP_DARK_FILE.getCaminho());

    }

    public Map obterListaArquivosDataArquivo() throws InterruptedException, IOException, ParseException, Exception {

        return Connection.obterListaArquivo(Roots.PASTA_FTP_DARKLIST.getCaminho());

    }

    public void uploadUltimoDiaDaListaLiteral(String data) throws Exception {

        Connection.uploadArquivo(Roots.ARQUIVO_LST_SERVIDOR.getCaminho(), Roots.PASTA_FTP_DARKLIST.getCaminho() + "spdark.lst".replaceAll("spdark.lst", data + "_spdark.lst"));

    }

    public void uploadDiaProducaoNumeralLabel() throws Exception {

        Connection.uploadArquivo(Roots.PRODUCAO_DIARIA_DIA_TEMP.getCaminho(), Roots.LITERAL_FTP_PRODUCAO_DIARIA.getCaminho());

    }

    public void downloadDiaProducaoNumeralLabel() throws Exception {

        Connection.downloadArquivo(Roots.LITERAL_FTP_PRODUCAO_DIARIA.getCaminho(), Roots.PRODUCAO_DIARIA_DIA_TEMP.getCaminho());

    }

}
