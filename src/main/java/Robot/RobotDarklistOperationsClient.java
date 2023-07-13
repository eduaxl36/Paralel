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
public class RobotDarklistOperationsClient implements RoboOperations {

    @Override
    public void downloadFlagFile() throws Exception {

        String FlagRemoteFile = Roots.DARK_PASTA_FTP_FLAG.getCaminho() + "true.txt";
        String FlagFile = Roots.DARK_ARQUIVO_TEMP_FLAG.getCaminho();

        Adapter.Adapter.Remote.downloadArquivo(FlagRemoteFile, FlagFile);

    }

    @Override
    public void mudarStatusFlag() throws Exception {

        Adapter.Adapter.Remote.deletarArquivo(Roots.DARK_PASTA_FTP_FLAG.getCaminho() + "true.txt");

    }

    @Override
    public String checkFlag() throws Exception {

        return Adapter.Adapter.Remote.checkFlag(Roots.DARK_PASTA_FTP_FLAG.getCaminho());

    }

}
