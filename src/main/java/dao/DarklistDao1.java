/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Entities.Darklist;
import Util.Util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import static viewClient.DarklistManagerViewClient.lblDtProd;
import static viewClient.DarklistManagerViewClient.tbMainViewDarkList;

/**
 *
 * @author Eduardo.Fernando
 */
public class DarklistDao1 {

    private LocalDate DataProducao;
    private File DarkListFile;

    public DarklistDao1() {

    }

    public DarklistDao1(LocalDate DataProducao, File DarkListFile) {
        this.DataProducao = DataProducao;
        this.DarkListFile = DarkListFile;
    }

    public boolean verificartSeEstaEmDark(LocalDate DataAbertura,LocalDate DataFechamento) {


        boolean MaiorOUMaiorAbertura = this.DataProducao.isEqual(DataAbertura)||this.DataProducao.isAfter(DataAbertura);
        boolean MenorQueFechamentoOUIGUAL = this.DataProducao.isEqual(DataFechamento)||this.DataProducao.isBefore(DataFechamento);
          
        
        return (MaiorOUMaiorAbertura&&MenorQueFechamentoOUIGUAL);
    }

    public Darklist retornaObjetoDark(String Raw) {

        String[] RawLines = Raw.split(",");

        if (RawLines[0].trim().matches("\\d{1,}")) {

            Long Domicilio = Long.valueOf(RawLines[0].trim());
            String Abertura = RawLines[3].trim();
            String Fechamento = RawLines[4].trim().replaceAll("-1", "20501231");
            String Observacao = RawLines[7].trim();
            
            
            byte[] bytes = Observacao.getBytes(Charset.forName("ISO-8859-1"));
           String observacaoANSI = new String(bytes, Charset.forName("UTF-8"));
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate AberturaAsLc = LocalDate.parse(Abertura, formatter);
            LocalDate FechamentoAsLc = LocalDate.parse(Fechamento, formatter);

            return new Darklist(
                    Domicilio,
                    AberturaAsLc,
                    FechamentoAsLc,
                    observacaoANSI,
                    verificartSeEstaEmDark(AberturaAsLc,FechamentoAsLc)
            );

        }

        return null;
    }

    public List<Darklist> DarkLists() throws FileNotFoundException, IOException {

        List<Darklist> Darks = new ArrayList<>();

        FileReader Fr = new FileReader(this.DarkListFile);
        BufferedReader bf = new BufferedReader(Fr);
        String Linha = bf.readLine();

        while (Linha != null) {

            if (!(Linha.contains("[NumItems]"))) {

                Darks.add(retornaObjetoDark(Linha));

            }

            Linha = bf.readLine();

        }

        return Darks;
    }

    public List<Darklist> getStatus() throws IOException {

        List<Darklist> Darks = DarkLists().stream()
                .collect(Collectors.toList());

        return Darks;

    }

    
    
    
      public  void carregarDarkList() throws IOException, Exception {

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd");

            getStatus().forEach((var x) -> {

            DefaultTableModel df = (DefaultTableModel) tbMainViewDarkList.getModel();

            String allowChange = "No permitido cambios";

            if (x != null) {

                if (x.isStatus()) {

                    allowChange = "Listo para Cambio";

                }

                df.addRow(new Object[]{
                    x.getId(),
                    x.getDataAbertura(),
                    x.getDataFechamento(),
                    x.getComentario(),
                    x.isStatus(),
                    allowChange

                });

            }

        });

        new Util(tbMainViewDarkList).ajustarFormataColunasTabelaConteudo();

    }
    
    
    
    
    
    
    
    
    
    
    
    
    public static void main(String[] args) throws FileNotFoundException, IOException {

//        new DarklistDao1(LocalDate.parse("2023-05-16").plusDays(1)).getStatus().forEach(x -> {
//
//            System.out.println(x);
//
//        });
    }

}
