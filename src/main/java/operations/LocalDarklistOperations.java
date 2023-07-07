/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operations;

import dao.LogDao;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.io.FileUtils;
import pathManager.Roots;
import static viewClientDarklist.DarklistManagerViewClient.lblDtProd;
import static viewClientDarklist.DarklistManagerViewClient.tbMainViewLst;
/**
 *
 * @author eduardo.fernando
 */
public class LocalDarklistOperations implements LocalOperations {

    @Override
    public String captarUltimoDiaProducao() {

        List<LocalDate> datas = new ArrayList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        File pasta = new File(Roots.DARK_PASTA_PRODUCAO_OFICIAL.getCaminho());

        File[] arquivos = pasta.listFiles((dir, nome) -> nome.matches("\\d{8}\\.vsl"));

        for (File x : arquivos) {

            datas.add(LocalDate.parse(x.getName().substring(0, 8), formatter));

        }

        Collections.sort(datas);

        return Collections.max(datas).toString().replaceAll("-", "");

    }

    @Override
    public void criarDataProducao() throws IOException, Exception {

        FileUtils.write(new File(Roots.DARK_PRODUCAO_DIARIA_DIA_TEMP.getCaminho()), captarUltimoDiaProducao());

    }

    @Override
    public void carregarLog(String LogLocalFile) throws Exception {
        
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd");

        new LogDao(LocalDate.parse(lblDtProd.getText(), fmt).plusDays(1), new File(LogLocalFile), tbMainViewLst)
                .preencherTabela();

    }

}
