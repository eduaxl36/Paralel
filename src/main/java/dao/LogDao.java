/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Entities.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Eduardo.Fernando
 */
public class LogDao {

    private LocalDate DataProducao;
    private File LogFile;

    public LogDao() {

    }

    public LogDao(LocalDate DataProducao, File DarkListFile) {
        this.DataProducao = DataProducao;
        this.LogFile = DarkListFile;
    }

    public List<Log> Logs() throws Exception {

        List<Log> Darks = new ArrayList<>();

        FileReader Fr = new FileReader(this.LogFile);
        BufferedReader bf = new BufferedReader(Fr);
        String Linha = bf.readLine();

        while (Linha != null) {

            Darks.add(retornaObjetoLog(Linha));

            Linha = bf.readLine();

        }
        
        Darks.remove(0);
        return Darks;
    }

    public Log retornaObjetoLog(String Raw) {

        String[] RawLines = Raw.split(";");

        if (RawLines[0].trim().matches("\\d{1,}")) {

 

                Long Domicilio = Long.valueOf(RawLines[0].trim());
                String Abertura = RawLines[1].trim();
                String Fechamento = RawLines[2].trim().replaceAll("(^\\-1$)", "20501231");
                String Observacao = RawLines[3].trim();
                boolean enDark = Boolean.valueOf(RawLines[4].trim());
                String tipoCambio = RawLines[5].trim();
                String LogAutor = RawLines[6].trim();
                int dif = 0;
                if (RawLines[7].trim().matches("\\d{1,}")) {

                    dif = Integer.parseInt(RawLines[7].trim());

                }

                String AcaoCapturada = RawLines[8].trim();

                
           
                
                
                return new Log(
                        Domicilio,
                        LocalDate.parse(Abertura),
                        LocalDate.parse(Fechamento),
                        Observacao,
                        enDark,
                        tipoCambio,
                        LogAutor,
                        dif,
                        AcaoCapturada);

            

        }

        return null;
    }

  

    public static void main(String[] args) throws FileNotFoundException, IOException, Exception {

        LogDao lgdao = new LogDao(LocalDate.now(), new File("C:\\teste\\20230606_log.csv"));

     
        lgdao.Logs().forEach(System.out::println);
        
        
//        new DarklistDao1(LocalDate.parse("2023-05-16").plusDays(1)).getStatus().forEach(x -> {
//
//            System.out.println(x);
//
//        });
    }

}
