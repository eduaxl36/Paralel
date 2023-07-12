/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Adapter;

import Exporter.Exporter;
import com.jcraft.jsch.JSchException;
import dao.DarkDao;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import operations.LocalDarklistOperations;
import operations.RemoteDarklistOperations;
import org.apache.commons.io.FileUtils;
import pathManager.Roots;
import sftp.ConfiguracoesSFTPModel;
import static viewClientDarklist.DarklistManagerViewClient.tbMainViewLst;

/**
 *
 * @author eduardo.fernando
 */
public class AdapterDark extends Adapter {

    private final String DARK_PASTA_TEMP_FILE = Roots.DARK_PASTA_TEMP_FILE.getCaminho();
    private final String DARK_PASTA_TEMP_LOG_FILE = Roots.DARK_PASTA_TEMP_LOG_FILE.getCaminho();
    private DarkDao Dao;

    @Override
    public String getPastaTempFile() {

        return DARK_PASTA_TEMP_FILE;

    }

    @Override
    public String getPastaTempLogFile() {

        return DARK_PASTA_TEMP_LOG_FILE;

    }

    @Override
    public void iniciaConexao() throws JSchException, InterruptedException {

        Remote = new RemoteDarklistOperations(new ConfiguracoesSFTPModel("LATAM", 0, "regional.latam", "gDItMm7K", "sftp.kantaribopemedia.com", 22));
        localOperations = new LocalDarklistOperations();

    }



}
