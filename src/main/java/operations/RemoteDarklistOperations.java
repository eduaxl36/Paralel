/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operations;

import Util.MainTableUtil;
import com.jcraft.jsch.JSchException;
import flag.entidadeFlag;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import msgs.Pbar;
import org.apache.commons.io.FileUtils;
import pathManager.Roots;
import sftp.ConfiguracoesSFTPModel;
import static viewClientDarklist.DarklistManagerViewClient.tbMainViewLst;
import viewClientDarklist.MenuFile;

/**
 *
 * @author Eduardo.Fernando
 */

public class RemoteDarklistOperations extends RemoteOperations {


    public RemoteDarklistOperations(ConfiguracoesSFTPModel ModeloConexao) throws JSchException, InterruptedException {
        super(ModeloConexao);

    }

    @Override
    public void downloadArquivoLogHistorico(String LogFile) throws Exception {

        Connection.downloadArquivo(Roots.DARK_PASTA_FTP_LOG.getCaminho() + LogFile, Roots.DARK_PASTA_TEMP_LOG_FILE.getCaminho() + LogFile);

    }

    @Override
    public void uploadLogdia(String data) throws Exception {

        Connection.uploadArquivo(Roots.DARK_PASTA_TEMP_LOG_FILE.getCaminho() + "/" + data + "_log.csv", Roots.DARK_PASTA_FTP_LOG.getCaminho() + "/" + data + "_log.csv");

    }

    @Override
    public Map obterListaArquivosLogDataArquivo() throws InterruptedException, IOException, ParseException, Exception {

        return Connection.obterListaArquivo(Roots.DARK_PASTA_FTP_LOG.getCaminho());

    }

    @Override
    public void uploadLogAlteracoes(JTable Tabela, String DataProducao) {
        int resposta = JOptionPane.showConfirmDialog(null, "Desea enviar los cambios para la equipe regional?", "Confirmacion", JOptionPane.YES_OPTION);

        if (resposta == JOptionPane.YES_OPTION) {
            new Thread(() -> {
                try {

                    Pbar.Progresso.setVisible(true);

                    String arquivoSalvoLog =  Roots.DARK_PASTA_TEMP_LOG_FILE.getCaminho() + DataProducao.replaceAll("-", "") + "_log.csv";
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

    @Override
    public void downloadArquivoLst(String DarklistFile) throws Exception {

        Connection.downloadArquivo(Roots.DARK_PASTA_FTP_DARKLIST.getCaminho() + DarklistFile, Roots.DARK_PASTA_TEMP_FILE.getCaminho());

    }

    @Override
    public Map obterListaArquivosDataArquivo() throws InterruptedException, IOException, ParseException, Exception {

        Map obterListaArquivo = Connection.obterListaArquivo(Roots.DARK_PASTA_FTP_DARKLIST.getCaminho());
                
        return Connection.obterListaArquivo(Roots.DARK_PASTA_FTP_DARKLIST.getCaminho());

    }

  
    @Override
    public Set<entidadeFlag> obterListaDeFlags(String dia) throws IOException {

        for (int i = 0; i < tbMainViewLst.getRowCount(); i++) {

            int dom = Integer.parseInt(tbMainViewLst.getValueAt(i, 0).toString());
            String value = String.valueOf(tbMainViewLst.getValueAt(i, 6));
            String comment = String.valueOf(tbMainViewLst.getValueAt(i, 3));
            String result = value != null && !value.isEmpty() ? value : "-";
            String valueTp = String.valueOf(tbMainViewLst.getValueAt(i, 8));
            String resultTp = valueTp != null && !valueTp.isEmpty() ? valueTp : "-";

            LocalDate DataAbertura = LocalDate.parse(tbMainViewLst.getValueAt(i, 1).toString());
            LocalDate DataFechamento = LocalDate.parse(tbMainViewLst.getValueAt(i, 2).toString());

            if ((result.contains("."))) {

                Flags.add(
                        new entidadeFlag(
                                dom,
                                result,
                                resultTp,
                                DataAbertura,
                                DataFechamento,
                                false,
                                comment)
                );

            }

        }

        return Flags;
    }

    
      @Override
    public void contestarFlag(String Dia) throws IOException {

        Set<entidadeFlag> flag = obterListaDeFlags(Dia);
        new File(Roots.DARK_ARQUIVO_TEMP_FLAG.getCaminho()).delete();
        ArrayList<entidadeFlag> list = new ArrayList<>(flag);
        final String mensagem = String.format("""
                                              Los cambios abajo fueron hechos en Darklist
                                              
                                              El user: %s 
                                              Fecha del Darklist : %s
                                              """, list.get(0).getAutor(), Dia);

        FileUtils.write(new File(Roots.DARK_ARQUIVO_TEMP_FLAG.getCaminho()), mensagem, StandardCharsets.UTF_8, true);

        flag.forEach(x -> {

            try {

                FileUtils.write(new File(Roots.DARK_ARQUIVO_TEMP_FLAG.getCaminho()),
                        """
                        
                        Hogar: """ + x.getId() + "\n"
                        + "Fecha Inicio: " + x.getDataAbertura() + "\n"
                        + "Fecha Cerramiento:" + x.getDataFechamento() + "\n"
                        + "Obs. : " + x.getComment() + "\n"
                        + "Autor : " + x.getAutor() + "\n"
                        + "Cambio Realizado : " + x.getTipoCambio() + "\n\n",
                        StandardCharsets.UTF_8, true);
            } catch (IOException ex) {
      
            }

        });

    }
    
    
    @Override
    public void gerarFlag(String Dia) throws IOException {

        Set<entidadeFlag> flag = obterListaDeFlags(Dia);
        new File(Roots.DARK_ARQUIVO_TEMP_FLAG.getCaminho()).delete();
        ArrayList<entidadeFlag> list = new ArrayList<>(flag);
        final String mensagem = String.format("""
                                              Solicitacion para el cambio en Darklist
                                              
                                              El user: %s 
                                              Fecha del Darklist : %s
                                              """, list.get(0).getAutor(), Dia);

        FileUtils.write(new File(Roots.DARK_ARQUIVO_TEMP_FLAG.getCaminho()), mensagem, StandardCharsets.UTF_8, true);

        flag.forEach(x -> {

            try {

                FileUtils.write(new File(Roots.DARK_ARQUIVO_TEMP_FLAG.getCaminho()),
                        """
                        
                        Hogar: """ + x.getId() + "\n"
                        + "Fecha Inicio: " + x.getDataAbertura() + "\n"
                        + "Fecha Cerramiento:" + x.getDataFechamento() + "\n"
                        + "Obs. : " + x.getComment() + "\n"
                        + "Autor : " + x.getAutor() + "\n"
                        + "Cambio Realizado : " + x.getTipoCambio() + "\n\n",
                        StandardCharsets.UTF_8, true);
            } catch (IOException ex) {
      
            }

        });

    }

    @Override
    public void uploadFlag() throws Exception {

        Connection.uploadArquivo(Roots.DARK_ARQUIVO_TEMP_FLAG.getCaminho(), Roots.DARK_PASTA_FTP_FLAG.getCaminho());

    }

    @Override
    public void uploadUltimoDiaDaListaLiteral(String data) throws Exception {

        Connection.uploadArquivo(Roots.DARK_ARQUIVO_LST_SERVIDOR.getCaminho(), Roots.DARK_PASTA_FTP_DARKLIST.getCaminho() + "spdark.lst".replaceAll("spdark.lst", data + "_spdark.lst"));

    }

    @Override
    public void uploadDiaProducaoNumeralLabel() throws Exception {

        Connection.uploadArquivo(Roots.DARK_PRODUCAO_DIARIA_DIA_TEMP.getCaminho(), Roots.DARK_LITERAL_FTP_PRODUCAO_DIARIA.getCaminho());

    }

    @Override
    public void downloadDiaProducaoNumeralLabel() throws Exception {

        Connection.downloadArquivo(Roots.DARK_LITERAL_FTP_PRODUCAO_DIARIA.getCaminho(), Roots.DARK_PRODUCAO_DIARIA_DIA_TEMP.getCaminho());

    }

    @Override
    public String checkFlag(String Caminho) throws Exception {
    
        return Connection.checkFlag(Caminho);
    
    }

 

}
