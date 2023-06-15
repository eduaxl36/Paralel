/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewClient;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import sftp.ConfiguracoesSFTPModel;
import static sftp.ConnectionFactory.changeFlagToFalse;
import sftp.SFTPConnection;

/**
 *
 * @author Eduardo.Fernando
 */
public class TimerSFTP {
    
      private ScheduledExecutorService scheduler;
    
   public static String checkFlag() throws Exception {
      
      
        ConfiguracoesSFTPModel sd = new ConfiguracoesSFTPModel("LATAM", 0, "regional.latam", "gDItMm7K", "sftp.kantaribopemedia.com", 22);
        SFTPConnection ser = new SFTPConnection(sd);
        
        

        ser.obterSessao();

        ser.Conexao();
return ser.checkFlag();
 
    }   
      public void Timer(int initDel, int Intervalo) throws IOException, InterruptedException {

        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            try {
                String validator = checkFlag();
         
                
                if (validator.equals("tem")) {

     
                  changeFlagToFalse();
                   
               
                }

            } catch (Exception ex) {
               ex.printStackTrace();
            }
        }, initDel, Intervalo, TimeUnit.SECONDS);

    }
      
      
    public static void main(String[] args) throws Exception {
        new TimerSFTP().Timer(1, 10);
    }
  
      
}
