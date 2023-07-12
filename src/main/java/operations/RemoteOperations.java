/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operations;

import com.jcraft.jsch.JSchException;
import flag.entidadeFlag;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.JTable;
import pathManager.Roots;
import sftp.ConfiguracoesSFTPModel;
import sftp.SFTPConnection;

/**
 *
 * @author eduardo.fernando
 */
public abstract class RemoteOperations {

    private ConfiguracoesSFTPModel ModeloConexao = null;
    SFTPConnection Connection = null;
    public static Set<entidadeFlag> Flags = new LinkedHashSet<>();

    public RemoteOperations(ConfiguracoesSFTPModel ModeloConexao) throws JSchException, InterruptedException {

        this.ModeloConexao = ModeloConexao;
        this.Connection = new SFTPConnection(ModeloConexao);

        Connection.obterSessao();
        Connection.Conexao();
    }

    public abstract void downloadArquivoLogHistorico(String LogFile) throws Exception;

    public abstract void uploadLogdia(String data) throws Exception;

    public abstract Map obterListaArquivosLogDataArquivo() throws Exception;

    public abstract void uploadLogAlteracoes(JTable Tabela, String DataProducao);

    public abstract void uploadFlag() throws Exception;

    public abstract void downloadArquivoLst(String DarklistFile) throws Exception;

    public abstract Map obterListaArquivosDataArquivo() throws Exception;

    public abstract Set<entidadeFlag> obterListaDeFlags(String dia) throws IOException;

    public abstract void gerarFlag(String Dia) throws IOException;
    public abstract void contestarFlag(String Dia) throws IOException;

    public abstract void uploadUltimoDiaDaListaLiteral(String data) throws Exception;

    public abstract void uploadDiaProducaoNumeralLabel() throws Exception;

    public abstract void downloadDiaProducaoNumeralLabel() throws Exception;

}
