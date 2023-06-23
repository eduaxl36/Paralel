/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.kantar.sftp;

import br.com.kantar.pathManager.Manager;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Eduardo.Fernando
 */
public class SFTPOperations {

    ConfiguracoesSFTPModel SftpEntity;
    SFTPConnection InstanciaConexao;

    private static SFTPOperations Instancia;

    public SFTPOperations() throws JSchException, InterruptedException {

        SftpEntity = new ConfiguracoesSFTPModel("LATAM", 0, "regional.latam", "gDItMm7K", "sftp.kantaribopemedia.com", 22);
        InstanciaConexao = new SFTPConnection(SftpEntity);
        InstanciaConexao.obterSessao();
        InstanciaConexao.Conexao();
    }

    public static SFTPOperations getInstance() throws Exception {

        if (Instancia == null) {
            synchronized (SFTPOperations.class) {
                if (Instancia == null) {
                    Instancia = new SFTPOperations();
                }
            }
        }
        return Instancia;

    }

    public String captarUltimoDiaProducao() {

        List<LocalDate> datas = new ArrayList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        File pasta = new File(Manager.getRoot().get("caminho_oficial_out_telepanel_panama"));
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

    public void uploadDataAtualizadaProducao() throws Exception {

        this.InstanciaConexao.uploadArquivo(Manager.getRoot().get("caminho_local_temp_producao_dia"), Manager.getRoot().get("caminho_ftp_producao_dia_completo_arquivo"));

    }

    public void uploadFlag() throws Exception {

        this.InstanciaConexao.uploadArquivo(Manager.getRoot().get("caminho_local_temp_flag"), Manager.getRoot().get("caminho_ftp_flag") + "/true.txt");

    }

    public void uploadLogFile(String Data) throws Exception {

        this.InstanciaConexao.uploadArquivo(Manager.getRoot().get("caminho_local_temp_logFile") + Data + "_log.csv", Manager.getRoot().get("caminho_ftp_log") + Data + "_log.csv");

    }

    public static void main(String[] args) throws JSchException, InterruptedException, SftpException, Exception {

        SFTPOperations x = SFTPOperations.getInstance();
//        x.criarDataProducao();
//        x.uploadDataAtualizadaProducao();
//          x.uploadCurrentDarkList("20230619");

    }

}
