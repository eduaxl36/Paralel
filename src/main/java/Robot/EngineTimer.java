/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Robot;

import Adapter.Adapter;
import Adapter.AdapterDark;
import Adapter.AdapterWhite;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import pathManager.Roots;

/**
 *
 * @author Eduardo.Fernando
 */
public class EngineTimer {

    RoboOperations Operacao;

    MaillMachine EnvioEmail;

    public EngineTimer(RoboOperations Operacao, MaillMachine EnvioEmail) {
        this.Operacao = Operacao;
        this.EnvioEmail = EnvioEmail;
    }

    private ScheduledExecutorService scheduler;

    public void Timer(int initDel, int Intervalo) throws IOException, InterruptedException {

        scheduler = Executors.newSingleThreadScheduledExecutor();

        scheduler.scheduleAtFixedRate(() -> {

            System.out.println("Verificando.....");
            try {
                String validator = Operacao.checkFlag();

                if (validator.equals("tem")) {

                    Operacao.downloadFlagFile();
                    Operacao.mudarStatusFlag();
                    EnvioEmail.enviarEmail();

                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }, initDel, Intervalo, TimeUnit.SECONDS);

    }

    public static void main(String[] args) throws Exception {

        Adapter ds = new AdapterDark();
        ds.iniciaConexao();

        RoboOperations Rop = new RobotDarklistOperationsClient();
        MaillMachine ss = new MaillMachine("Darklist", Roots.DARK_ARQUIVO_TEMP_FLAG.getCaminho());
        new EngineTimer(Rop, ss).Timer(1, 30);

        Adapter ds2 = new AdapterWhite();
        ds2.iniciaConexao();

        RoboOperations Rop2 = new RobotWhitelistOperationsClient();
        MaillMachine ssd = new MaillMachine("Whitelist", Roots.ARQUIVO_TEMP_FLAG_WHITE.getCaminho());
        new EngineTimer(Rop2, ssd).Timer(1, 30);

//       
//
//    SchedulleDownload();
    }

}
