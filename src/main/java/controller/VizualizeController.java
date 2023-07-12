/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import static Adapter.Adapter.Remote;
import dao.DarkDao;
import dao.WhiteDao;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.table.DefaultTableModel;
import operations.RemoteDarklistOperations;
import static viewClientDarklist.DarklistManagerViewClient.Adaptador;
import static viewClientDarklist.Visualize.tbMainViewDarkList;

/**
 *
 * @author eduardo.fernando
 */
public class VizualizeController {
    
    
   public void loadListEditMode(String Data, String DarklistFile) throws IOException, Exception {

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd");

        String DarklistLocal = new File(Adaptador.getPastaTempFile()).toString();

        if (Remote instanceof RemoteDarklistOperations) {

            new DarkDao(LocalDate.parse(Data, fmt).plusDays(1), new File(DarklistLocal + "/" + DarklistFile).getParentFile(), tbMainViewDarkList).getStatus().forEach(x -> {

                DefaultTableModel df = (DefaultTableModel) tbMainViewDarkList.getModel();

                String allowChange = "No permitido cambios";

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

            });

        } else {

            new WhiteDao(LocalDate.parse(Data, fmt).plusDays(1), new File(DarklistLocal + "/" + DarklistFile).getParentFile(), tbMainViewDarkList).getStatus().forEach(x -> {

                DefaultTableModel df = (DefaultTableModel) tbMainViewDarkList.getModel();

                String allowChange = "No permitido cambios";

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

            });

        }

    } 
    
    
    
    
    
    
    
    
    
    
    
}
