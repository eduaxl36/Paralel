/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Adapter;

import Exporter.ExporterWhite;
import com.jcraft.jsch.JSchException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import operations.LocalWhiteOperations;
import operations.RemoteWhitelistOperations;
import org.apache.commons.io.FileUtils;
import pathManager.Roots;
import sftp.ConfiguracoesSFTPModel;
import static viewClientDarklist.DarklistManagerViewClient.tbMainViewLst;

/**
 *
 * @author eduardo.fernando
 */
public class AdapterWhite extends Adapter {
   
     private  final String PASTA_TEMP_FILE_WHITE = Roots.PASTA_TEMP_FILE_WHITE.getCaminho();
     private  final String PASTA_TEMP_LOG_FILE_WHITE = Roots.PASTA_TEMP_LOG_FILE_WHITE.getCaminho();     
     
     
     
     @Override
     public String getPastaTempFile(){
     
     return PASTA_TEMP_FILE_WHITE;
     
     }
     
     @Override
      public String getPastaTempLogFile(){
     
     return PASTA_TEMP_LOG_FILE_WHITE;
     
     }    
     

     @Override
     public void iniciaConexao() throws JSchException, InterruptedException{
     
     
            Remote = new RemoteWhitelistOperations(new ConfiguracoesSFTPModel("LATAM", 0, "regional.latam", "gDItMm7K", "sftp.kantaribopemedia.com", 22));
            localOperations = new LocalWhiteOperations();
     
     }
     
  
    
    
    
}
