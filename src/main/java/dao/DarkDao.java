/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Entities.Darklist;
import Util.MainTableUtil;
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
import static viewClientDarklist.DarklistManagerViewClient.tbMainViewLst;

/**
 *
 * @author Eduardo.Fernando
 */
public class DarkDao extends LstDao {

    public DarkDao() {
    }

    public DarkDao(LocalDate DataProducao, File ListFile, JTable Tabela) {
        super(DataProducao, ListFile, Tabela);
    }

   

    @Override
    public boolean verificartSeEstaEmLista(LocalDate DataAbertura, LocalDate DataFechamento) {

        boolean MaiorOUMaiorAbertura = DataProducao.isEqual(DataAbertura) || DataProducao.isAfter(DataAbertura);
        boolean MenorQueFechamentoOUIGUAL = DataProducao.isEqual(DataFechamento) || DataProducao.isBefore(DataFechamento);

        return (MaiorOUMaiorAbertura && MenorQueFechamentoOUIGUAL);
    }

    /**
     *
     * @param Raw
     * @return
     */
    @Override
    public Darklist retornaObjetoLst(String Raw) {

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
                    verificartSeEstaEmLista(AberturaAsLc, FechamentoAsLc)
            );

        }

        return null;
    }

    @Override
    public List<Darklist> Listas() throws FileNotFoundException, IOException {

        List<Darklist> Darks = new ArrayList<>();

        FileReader Fr = new FileReader(this.ListFile);
        BufferedReader bf = new BufferedReader(Fr);
        String Linha = bf.readLine();

        while (Linha != null) {

            if (!(Linha.contains("[NumItems]"))) {

                Darks.add(retornaObjetoLst(Linha));

            }

            Linha = bf.readLine();

        }

        return Darks;
    }

    @Override
    public List<Darklist> getStatus() throws IOException {

        List<Darklist> Darks = Listas().stream()
                .collect(Collectors.toList());

        return Darks;

    }

    /**
     *
     * @throws IOException
     * @throws Exception
     */
    @Override
    public void carregarLista() throws IOException, Exception {

        Modelo = (DefaultTableModel) Tabela.getModel();
        Modelo.setNumRows(0);

        getStatus().forEach((var x) -> {

            String allowChange = "No permitido cambios";

            if (x != null) {

                if (x.isStatus()) {

                    allowChange = "Listo para Cambio";

                }

                Modelo.addRow(new Object[]{
                    x.getId(),
                    x.getDataAbertura(),
                    x.getDataFechamento(),
                    x.getComentario(),
                    x.isStatus(),
                    allowChange

                });

            }

        });

        new MainTableUtil(tbMainViewLst).ajustarFormataColunasTabelaConteudo();

    }

    public static void main(String[] args) throws FileNotFoundException, IOException {

//        new DarklistDao1(LocalDate.parse("2023-05-16").plusDays(1)).getStatus().forEach(x -> {
//
//            System.out.println(x);
//
//        });
    }

}
