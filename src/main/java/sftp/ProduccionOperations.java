/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sftp;

import com.jcraft.jsch.JSchException;
import java.io.File;

/**
 *
 * @author eduardo.fernando
 */
public class ProduccionOperations {

    private final RemoteOperations Remote;

    public ProduccionOperations() throws JSchException, InterruptedException {

        Remote = new RemoteOperations(new ConfiguracoesSFTPModel("LATAM", 0, "regional.latam", "gDItMm7K", "sftp.kantaribopemedia.com", 22));

    }

    public void downloadNumeralProducaoDia() throws Exception {

        Remote.downloadNumeralDia();

    }

}
