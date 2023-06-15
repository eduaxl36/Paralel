/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import Util.UtilTable;
import Entities.VelGroup;
import static View.Check.tbData;
import dao.VelDao;
import dao.VelGroupDao;
import java.io.File;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Eduardo.Fernando
 */
public class CheckController {

    public static List<VelGroup> dadosReportes() {

        
//        File VelFile = new File(DtPick.getDate().toString().replaceAll("-", "")+".vel");
//        VelGroupDao VDao = new VelGroupDao(new VelDao(VelFile));

        return null;

    }

    public static void preencherTabela() {

        DefaultTableModel Model = (DefaultTableModel) tbData.getModel();
        UtilTable AjustaTabela = UtilTable.getInstance();
        
        AjustaTabela.cleanTable(tbData);
        
   

        AjustaTabela.realizaAjuste(tbData);

    }

    public static void main(String[] args) {

    }

}
