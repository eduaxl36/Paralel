/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;


import sftp.ConfiguracoesSFTPModel;
import sftp.RemoteOperations;


/**
 *
 * @author Eduardo.Fernando
 */
public class MenuFileController {

    private RemoteOperations Remote = null;

    public MenuFileController() throws Exception {

        Remote = new RemoteOperations(new ConfiguracoesSFTPModel("LATAM", 0, "regional.latam", "gDItMm7K", "sftp.kantaribopemedia.com", 22));

    }
    
    
    
    
    
    
    
    
    

}
