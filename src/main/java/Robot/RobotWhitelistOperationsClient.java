/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Robot;
import pathManager.Roots;

/**
 *
 * @author Eduardo.Fernando
 */
public class RobotWhitelistOperationsClient implements RoboOperations {

    @Override
    public void downloadFlagFile() throws Exception {

        String FlagRemoteFile = Roots.PASTA_FTP_FLAG_WHITE.getCaminho() + "true.txt";
        String FlagFile = Roots.ARQUIVO_TEMP_FLAG_WHITE.getCaminho();

        Adapter.Adapter.Remote.downloadArquivo(FlagRemoteFile, FlagFile);

    }

    @Override
    public void mudarStatusFlag() throws Exception {

        Adapter.Adapter.Remote.deletarArquivo(Roots.PASTA_FTP_FLAG_WHITE.getCaminho() + "true.txt");

    }

    @Override
    public String checkFlag() throws Exception {

        return Adapter.Adapter.Remote.checkFlag(Roots.PASTA_FTP_FLAG_WHITE.getCaminho());

    }

}
