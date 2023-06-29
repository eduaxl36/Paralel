/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Entities.Log;
import Util.MainTableUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Eduardo.Fernando
 */
public class LogDao {

    private LocalDate DataProducao;
    private File LogFile;
    private JTable Tabela;
    private DefaultTableModel Modelo;
    
    
    public LogDao() {

    }

    public LogDao(LocalDate DataProducao, File DarkListFile) {
        this.DataProducao = DataProducao;
        this.LogFile = DarkListFile;
    }

    public LogDao(LocalDate DataProducao, File LogFile, JTable Tabela) {
        this.DataProducao = DataProducao;
        this.LogFile = LogFile;
        this.Tabela = Tabela;
        Modelo = (DefaultTableModel) Tabela.getModel();
    }
    
    
       
    public void preencherTabela() throws Exception{

        
        Logs().forEach(x -> {

            if (!(x.getAlteracaoRealizada().contains("a"))) {

                Modelo.addRow(new Object[]{
                    x.getId(),
                    x.getDataAbertura(),
                    x.getDataFechamento(),
                    x.getComentario(),
                    x.isStatus(),
                    "-",
                    "-",
                    x.getDiferencaDatas(),
                    "-"

                });

            } else {

                Modelo.addRow(new Object[]{
                    x.getId(),
                    x.getDataAbertura(),
                    x.getDataFechamento(),
                    x.getComentario(),
                    x.isStatus(),
                    x.getDescStatus(),
                    x.getAutorAlteracao(),
                    x.getDiferencaDatas(),
                    x.getAlteracaoRealizada()

                });

            }

        });

        new MainTableUtil(Tabela).ajustarFormataColunasTabelaConteudo();
    
    
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
                boolean enDark = Boolean.parseBoolean(RawLines[4].trim());
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

  


}
