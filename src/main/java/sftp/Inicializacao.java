/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sftp;

import operations.RemoteDarklistOperations;
import com.jcraft.jsch.JSchException;
import operations.LocalDarklistOperations;

/**
 *
 * @author eduardo.fernando
 */
public class Inicializacao {
    
     public static RemoteDarklistOperations Remote;
     public static LocalDarklistOperations localDarkOperations;
     
     
     public static void iniciaConexao() throws JSchException, InterruptedException{
     
     
            Remote = new RemoteDarklistOperations(new ConfiguracoesSFTPModel("LATAM", 0, "regional.latam", "gDItMm7K", "sftp.kantaribopemedia.com", 22));
            localDarkOperations = new LocalDarklistOperations();
     
     }
    
    
}
