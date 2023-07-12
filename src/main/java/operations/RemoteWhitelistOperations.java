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
public class RemoteWhitelistOperations extends RemoteOperations {


    public RemoteWhitelistOperations(ConfiguracoesSFTPModel ModeloConexao) throws JSchException, InterruptedException {
        super(ModeloConexao);

    }

    @Override
    public void downloadArquivoLogHistorico(String LogFile) throws Exception {

        Connection.downloadArquivo(Roots.PASTA_FTP_LOG_WHITE.getCaminho() + LogFile, Roots.PASTA_TEMP_LOG_FILE_WHITE.getCaminho() + LogFile);

    }

    @Override
    public void uploadLogdia(String data) throws Exception {

        Connection.uploadArquivo(Roots.PASTA_TEMP_LOG_FILE_WHITE.getCaminho() + "/" + data + "_log.csv", Roots.PASTA_FTP_LOG_WHITE.getCaminho() + "/" + data + "_log.csv");

    }

    @Override
    public Map obterListaArquivosLogDataArquivo() throws InterruptedException, IOException, ParseException, Exception {

        return Connection.obterListaArquivo(Roots.PASTA_FTP_LOG_WHITE.getCaminho());

    }

    @Override
    public void uploadLogAlteracoes(JTable Tabela, String DataProducao) {
        int resposta = JOptionPane.showConfirmDialog(null, "Desea enviar los cambios para la equipe regional?", "Confirmacion", JOptionPane.YES_OPTION);

        if (resposta == JOptionPane.YES_OPTION) {
            new Thread(() -> {
                try {

                    Pbar.Progresso.setVisible(true);

                    String arquivoSalvoLog = Roots.PASTA_TEMP_LOG_FILE_WHITE.getCaminho() + DataProducao.replaceAll("-", "") + "_log.csv";

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


        Connection.downloadArquivo(Roots.PASTA_FTP_WHITE.getCaminho() + DarklistFile, Roots.PASTA_TEMP_FILE_WHITE.getCaminho());

    }

    @Override
    public Map obterListaArquivosDataArquivo() throws InterruptedException, IOException, ParseException, Exception {

        return Connection.obterListaArquivo(Roots.PASTA_FTP_WHITE.getCaminho());

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

            if (!(result.equals("null"))) {

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
    public void gerarFlag(String Dia) throws IOException {

        Set<entidadeFlag> flag = obterListaDeFlags(Dia);
        new File(Roots.ARQUIVO_TEMP_FLAG_WHITE.getCaminho()).delete();
        ArrayList<entidadeFlag> list = new ArrayList<>(flag);
        final String mensagem = String.format("""
                                              Solicitacion para el cambio en Whitelist
                                              
                                              El user: %s 
                                              Fecha del Whitelist : %s
                                              """, list.get(0).getAutor(), Dia);

        FileUtils.write(new File(Roots.ARQUIVO_TEMP_FLAG_WHITE.getCaminho()), mensagem, StandardCharsets.UTF_8, true);

        flag.forEach(x -> {

            try {

                FileUtils.write(new File(Roots.ARQUIVO_TEMP_FLAG_WHITE.getCaminho()),
                        """
                        
                        Hogar: """ + x.getId() + "\n"
                        + "Fecha Inicio: " + x.getDataAbertura() + "\n"
                        + "Fecha Cerramiento:" + x.getDataFechamento() + "\n"
                        + "Obs. : " + x.getComment() + "\n"
                        + "Autor : " + x.getAutor() + "\n"
                        + "Cambio Relizado : " + x.getTipoCambio() + "\n\n",
                        StandardCharsets.UTF_8, true);
            } catch (IOException ex) {
            
            }

        });

    }

    
      @Override
    public void contestarFlag(String Dia) throws IOException {

        Set<entidadeFlag> flag = obterListaDeFlags(Dia);
        new File(Roots.ARQUIVO_TEMP_FLAG_WHITE.getCaminho()).delete();
        ArrayList<entidadeFlag> list = new ArrayList<>(flag);
        final String mensagem = String.format("""
                                              Los cambios abajo fueron hechos en Whitelist
                                              
                                              El user: %s 
                                              Fecha del Darklist : %s
                                              """, list.get(0).getAutor(), Dia);

        FileUtils.write(new File(Roots.ARQUIVO_TEMP_FLAG_WHITE.getCaminho()), mensagem, StandardCharsets.UTF_8, true);

        flag.forEach(x -> {

            try {

                FileUtils.write(new File(Roots.ARQUIVO_TEMP_FLAG_WHITE.getCaminho()),
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

        Connection.uploadArquivo(Roots.ARQUIVO_TEMP_FLAG_WHITE.getCaminho(), Roots.PASTA_FTP_FLAG_WHITE.getCaminho());

    }

    @Override
    public void uploadUltimoDiaDaListaLiteral(String data) throws Exception {

        Connection.uploadArquivo(Roots.ARQUIVO_LST_SERVIDOR_WHITE.getCaminho(), Roots.PASTA_FTP_WHITE.getCaminho() + "spwhite.lst".replaceAll("spwhite.lst", data + "_spwhite.lst"));

    }

    @Override
    public void uploadDiaProducaoNumeralLabel() throws Exception {

 
        Connection.uploadArquivo(Roots.PRODUCAO_DIARIA_DIA_TEMP_WHITE.getCaminho(), Roots.LITERAL_FTP_PRODUCAO_DIARIA_WHITE.getCaminho());

    }

    @Override
    public void downloadDiaProducaoNumeralLabel() throws Exception {

        Connection.downloadArquivo(Roots.LITERAL_FTP_PRODUCAO_DIARIA_WHITE.getCaminho(), Roots.PRODUCAO_DIARIA_DIA_TEMP_WHITE.getCaminho());

    }

}
