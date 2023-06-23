/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.kantar.sftp;

import br.com.kantar.sftp.ConfiguracoesSFTPModel;
import br.com.kantar.sftp.FTPService;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author Eduardo.Fernando
 */
public class SFTPConnection extends FTPService {

    private final String CRIPTOGRAFICA = "StrictHostKeyChecking";
    private final String PROTOCOLO = "sftp";
    private final String REPLY = "no";
    private Session Sessao;
    private ChannelSftp Canal;
    static Map<String, String> fileLog;

    public SFTPConnection(ConfiguracoesSFTPModel Model) {
        super(Model);
    }

    public Session obterSessao() throws JSchException, InterruptedException {

        for (int i = 0; i < 10; i++) {

            try {

                String KeyLocate = "c:/teste/key/Key.txt";
                JSch ConexaoInterna = new JSch();

                ConexaoInterna.setKnownHosts(KeyLocate);

                this.Sessao = ConexaoInterna.getSession(getModel().getUser(), getModel().getHost(), getModel().getPorta());

                this.Sessao.setPassword(getModel().getSenha());

                java.util.Properties config = new java.util.Properties();

                config.put(CRIPTOGRAFICA, REPLY);

                this.Sessao.setConfig(config);

                this.Sessao.connect();

                if (this.Sessao.isConnected()) {

                    break;

                }

            } catch (JSchException e) {

                if (i == 9) {

                    throw new JSchException("""
                                            Erro conexao com o SFTP: Houve uma falha de conex\u00e3o, favor verificar credencias ou se ha alguma instabilidade com o servidor 
                                            """ + getModel().getOwnerFTP() + " --> " + e.getMessage());

                }

            }
            System.out.println("Tentativa de conexao com o SFTP " + getModel().getOwnerFTP() + " Aguardando 15 segundos....");
            Thread.sleep(9000);

        }

        return this.Sessao;

    }

    public ChannelSftp Conexao() throws JSchException, InterruptedException {

        this.Canal = (ChannelSftp) this.Sessao.openChannel(PROTOCOLO);

        this.Canal.connect();

        return this.Canal;

    }

    @Override
    public void downloadArquivo(String Remoto, String Local) throws SftpException, JSchException, InterruptedException {

        this.Canal.get(Remoto, Local);

    }

    @Override
    public void uploadArquivo(String Local, String Remoto) throws SftpException, JSchException, InterruptedException {

         this.Canal.put(Local, Remoto);

    }
 
    public void delete(String CaminhoRemoto) throws SftpException, JSchException, InterruptedException {
      
          
        this.Canal.rm(CaminhoRemoto);

    }

    public String checkFlag(String PathFlagRemote) throws JSchException, InterruptedException, IOException, ParseException, SftpException, Exception {
  
        Vector<ChannelSftp.LsEntry> files = this.Canal.ls(PathFlagRemote);

        String fileName = "";

        for (ChannelSftp.LsEntry entry : files) {
            if (!entry.getAttrs().isDir()) {
                fileName = entry.getFilename();

                if (fileName.contains("true")) {
                    return "tem";
                }
            }
        }

        return "nao tem";
    }

    public Map uploadLogFile(String LogPath) throws JSchException, InterruptedException, IOException, ParseException, SftpException, Exception {

        Vector<ChannelSftp.LsEntry> files = this.Canal.ls(LogPath);

        fileLog = new HashMap<>();
        String fileName = "";

        for (ChannelSftp.LsEntry entry : files) {

            if (!entry.getAttrs().isDir()) {

                fileName = entry.getFilename();

                Date DarkListDate = new SimpleDateFormat("yyyyMMdd").parse(fileName.substring(0, 8));

                fileLog.put(new SimpleDateFormat("yyyyMMdd").format(DarkListDate), fileName);
            }
        }

        return fileLog;

    }


    @Override
    public boolean validaDestinoRemoto(String DestinoRemoto) {

        try {

            File Pasta = new File(DestinoRemoto);

            this.Canal.stat(Pasta.getParent().replace("\\", "/"));

        } catch (SftpException e) {

            if (e.id == ChannelSftp.SSH_FX_NO_SUCH_FILE) {
                throw new IllegalArgumentException("Error : Diretorio invalido ou Arquivo inexistente no SFTP!");

            }
        } catch (Exception ex) {
            throw new IllegalArgumentException("Error : Falha de conexao com o SFTP!" + ex);
        }
        return true;

    }

    @Override
    public void getValidConexion() throws Exception {

        obterSessao();
        Conexao();

    }

    @Override
    public void closeConexion() throws Exception {

        while (this.Sessao.isConnected()) {

            this.Sessao.disconnect();
            System.out.println("Aguardando Sessao desconectar...");

        }
        System.out.println("Sessao desconectada com sucesso!");

    }

}
