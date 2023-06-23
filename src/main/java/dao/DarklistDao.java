/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;


import Entities.Darklist;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Eduardo.Fernando
 */
public class DarklistDao {

    private LocalDate DataProducao;
    private List<Long> Domicilios;

    public DarklistDao() {
    }

    public DarklistDao(LocalDate DataProducao, List<Long> Domicilios) {
        this.DataProducao = DataProducao;
        this.Domicilios = Domicilios;
    }

    
    public LocalDate getCloseableDate(String ProduccionDate){
    
    
        return LocalDate.parse(ProduccionDate).plusDays(1);
    
    
    }

    public File abrirArquivo() {

        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Arquivo DarkList", "lst");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            return chooser.getSelectedFile();

        }

        return null;
    }

    public boolean verificartSeEstaEmDark(LocalDate DataFechamento) {

        boolean producaoAntesFechamento = this.DataProducao.isBefore(DataFechamento);
        boolean producaoIgualFechamento = this.DataProducao.isEqual(DataFechamento);

        return producaoAntesFechamento || producaoIgualFechamento;
    }

    public Darklist retornaObjetoDark(String Raw) {

        String[] RawLines = Raw.split(",");

        if (RawLines[0].trim().matches("\\d{1,}")) {

            Long Domicilio = Long.valueOf(RawLines[0].trim());
            String Abertura = RawLines[3].trim();
            String Fechamento = RawLines[4].trim().replaceAll("-1", "20501231");
            String Observacao = RawLines[7].trim();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate AberturaAsLc = LocalDate.parse(Abertura, formatter);
            LocalDate FechamentoAsLc = LocalDate.parse(Fechamento, formatter);

            return new Darklist(
                    Domicilio,
                    AberturaAsLc,
                    FechamentoAsLc,
                    Observacao,
                    verificartSeEstaEmDark(FechamentoAsLc)
            );

        }

        return null;
    }

    public List<Darklist> DarkLists() throws FileNotFoundException, IOException {

        List<Darklist> Darks = new ArrayList<>();

        File Arq = new DarklistDao().abrirArquivo();
        FileReader Fr = new FileReader(Arq);
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
                .filter(x -> this.Domicilios.contains(x.getId()))
                .filter(x -> x.isStatus() == true)
                .collect(Collectors.toList());

        return Darks;

    }

    public static void main(String[] args) throws FileNotFoundException, IOException {

        new DarklistDao(LocalDate.parse("2023-05-16").plusDays(1), Arrays.asList(new Long[]{777777L})).getStatus().forEach(x -> {

            System.out.println(x);

        });

    }

}
