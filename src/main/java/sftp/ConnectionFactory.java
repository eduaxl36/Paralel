/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sftp;

import javax.swing.JOptionPane;

/**
 *
 * @author Eduardo.Fernando
 */
public class ConnectionFactory {

    public static void uploadLogdia(String data) throws Exception {

        ConfiguracoesSFTPModel sd = new ConfiguracoesSFTPModel("LATAM", 0, "regional.latam", "gDItMm7K", "sftp.kantaribopemedia.com", 22);
        SFTPConnection ser = new SFTPConnection(sd);

        ser.obterSessao();

        ser.Conexao();

        ser.uploadArquivo("C:\\teste\\Nova pasta\\"+data+"_log.csv", "/IMI/GerenciadorCambios/Panama/def/dkl/log/"+data+"_log.csv");

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
    
    
 public static void changeFlagToFalse()throws Exception {
 
 
         ConfiguracoesSFTPModel sd = new ConfiguracoesSFTPModel("LATAM", 0, "regional.latam", "gDItMm7K", "sftp.kantaribopemedia.com", 22);
        SFTPConnection ser = new SFTPConnection(sd);

        ser.obterSessao();

        ser.Conexao();

        ser.rename();
        
 
 
 }   
    
    
  public static void checkFlag() throws Exception {
      
      
        ConfiguracoesSFTPModel sd = new ConfiguracoesSFTPModel("LATAM", 0, "regional.latam", "gDItMm7K", "sftp.kantaribopemedia.com", 22);
        SFTPConnection ser = new SFTPConnection(sd);
        
        

        ser.obterSessao();

        ser.Conexao();
ser.checkFlag();
 
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

    public static void main(String[] args) throws Exception {

        checkFlag();

    }

}
