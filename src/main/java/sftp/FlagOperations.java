package sftp;

import com.jcraft.jsch.JSchException;

public class FlagOperations {

    private final RemoteOperations Remote;

    public FlagOperations() throws JSchException, InterruptedException {

        Remote = new RemoteOperations(new ConfiguracoesSFTPModel("LATAM", 0, "regional.latam", "gDItMm7K", "sftp.kantaribopemedia.com", 22));

    }

    public void downloadArquivoFlag() throws Exception {

        Remote.downloadFlag();

    }

    public void uploadArquivoFlag() throws Exception {

        Remote.uploadFlag();

    }

}
