/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TableUtils;

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
public class ConnectionFactory {

    public static void uploadLog() throws Exception {

        ConfiguracoesSFTPModel sd = new ConfiguracoesSFTPModel("LATAM", 0, "regional.latam", "gDItMm7K", "sftp.kantaribopemedia.com", 22);
        SFTPConnection ser = new SFTPConnection(sd);

        ser.obterSessao();

        ser.Conexao();

        ser.uploadArquivo("C:\\teste\\Nova pasta\\logDia.txt", "/IMI/GerenciadorCambios/Panama/logDia.txt");

    }
    public static void uploadDarklist(String data) throws Exception {

        ConfiguracoesSFTPModel sd = new ConfiguracoesSFTPModel("LATAM", 0, "regional.latam", "gDItMm7K", "sftp.kantaribopemedia.com", 22);
        SFTPConnection ser = new SFTPConnection(sd);

        ser.obterSessao();

        ser.Conexao();

        ser.uploadArquivo("\\\\kimbrspp565\\ProducaoLatam\\PA\\1.Telepanel\\database\\definitivo\\cf\\PaRt\\spdark.lst", "/IMI/GerenciadorCambios/Panama/def/dkl/originals/"+data+"_spdark.lst");

    }
    public static void uploadLogdia(String data) throws Exception {

        ConfiguracoesSFTPModel sd = new ConfiguracoesSFTPModel("LATAM", 0, "regional.latam", "gDItMm7K", "sftp.kantaribopemedia.com", 22);
        SFTPConnection ser = new SFTPConnection(sd);

        ser.obterSessao();

        ser.Conexao();

        ser.uploadArquivo("C:\\teste\\Nova pasta\\" + data + "_log.csv", "/IMI/GerenciadorCambios/Panama/def/dkl/log/" + data + "_log.csv");

    }

    public static void downloadLogdia() throws Exception {

        ConfiguracoesSFTPModel sd = new ConfiguracoesSFTPModel("LATAM", 0, "regional.latam", "gDItMm7K", "sftp.kantaribopemedia.com", 22);
        SFTPConnection ser = new SFTPConnection(sd);

        ser.obterSessao();

        ser.Conexao();

        ser.downloadArquivo("/IMI/GerenciadorCambios/Panama/logDia.txt", "C:\\teste\\Nova pasta\\logDia.txt");

    }

    public static void downloadLoglistFile(String LogFile) throws Exception {

        ConfiguracoesSFTPModel sd = new ConfiguracoesSFTPModel("LATAM", 0, "regional.latam", "gDItMm7K", "sftp.kantaribopemedia.com", 22);
        SFTPConnection ser = new SFTPConnection(sd);

        ser.obterSessao();

        ser.Conexao();

        ser.downloadArquivo("/IMI/GerenciadorCambios/Panama/def/dkl/log/" + LogFile, "C:\\teste\\Nova pasta\\" + LogFile.toString());

    }

    public static void downloadDarklistFileEdition(String DarklistFile) throws Exception {
        downloadLogdia();

        ConfiguracoesSFTPModel sd = new ConfiguracoesSFTPModel("LATAM", 0, "regional.latam", "gDItMm7K", "sftp.kantaribopemedia.com", 22);
        SFTPConnection ser = new SFTPConnection(sd);

        ser.obterSessao();

        ser.Conexao();

        ser.downloadArquivo("/IMI/GerenciadorCambios/Panama/def/dkl/originals/" + DarklistFile, "C:\\teste\\Nova pasta\\" + DarklistFile.toString());

    }

    public static void downloadDarklistFile(String DarklistFile) throws Exception {
        downloadLogdia();

        ConfiguracoesSFTPModel sd = new ConfiguracoesSFTPModel("LATAM", 0, "regional.latam", "gDItMm7K", "sftp.kantaribopemedia.com", 22);
        SFTPConnection ser = new SFTPConnection(sd);

        ser.obterSessao();

        ser.Conexao();

        ser.downloadArquivo("/IMI/GerenciadorCambios/Panama/def/dkl/originals/" + DarklistFile, "C:\\teste\\Nova pasta\\" + DarklistFile.toString().replaceAll("\\d{8}_", ""));

    }

    public static String captarUltimoDiaProducao() {

        List<LocalDate> datas = new ArrayList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        File pasta = new File("\\\\kimbrspp565\\ProducaoLatam\\PA\\1.Telepanel\\database\\definitivo\\out\\PaRt\\");
        File[] arquivos = pasta.listFiles((dir, nome) -> nome.matches("\\d{8}\\.vsl"));

        for (File x : arquivos) {

            datas.add(LocalDate.parse(x.getName().substring(0, 8), formatter));

        }

        Collections.sort(datas);

        return Collections.max(datas).toString().replaceAll("-", "");

    }

    public static void criarArquivoLog() throws IOException, Exception {

        FileUtils.write(new File("C:\\teste\\Nova pasta\\logDia.txt"), captarUltimoDiaProducao());

        uploadLog();
         uploadDarklist(captarUltimoDiaProducao());
       

    }

    public static void main(String[] args) throws Exception {

        criarArquivoLog();

    }

}
