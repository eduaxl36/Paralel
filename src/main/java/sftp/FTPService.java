/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sftp;


import java.io.IOException;

/**
 *
 * @author Eduardo.Fernando
 */
public abstract class FTPService {

    ConfiguracoesSFTPModel Model;
    
    public FTPService() {
    }

    public FTPService(ConfiguracoesSFTPModel Model) {
        this.Model = Model;
    }

 
    public abstract void downloadArquivo(String Remoto, String Local) throws Exception ;

    public abstract void uploadArquivo(String Local, String Remoto) throws Exception ;

    public abstract boolean validaDestinoRemoto(String Remoto) throws Exception ;
    
     public abstract void getValidConexion() throws Exception ;
     
     public abstract void closeConexion() throws Exception ;

    public ConfiguracoesSFTPModel getModel() {
        return Model;
    }

    public void setModel(ConfiguracoesSFTPModel Model) {
        this.Model = Model;
    }
    
    
    
    

}
