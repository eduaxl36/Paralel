/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import static viewClientDarklist.DarklistManagerViewClient.tbMainViewLst;

/**
 *
 * @author eduardo.fernando
 */
public class ViewDarkController {

    public boolean validarSeProucaoJaFoi(long Domicilio) {

        for (int i = 0; i < tbMainViewLst.getRowCount(); i++) {

            if (Long.parseLong(tbMainViewLst.getValueAt(i, 0).toString()) == Domicilio) {

                if (tbMainViewLst.getValueAt(i, 4).toString().equals("true")) {

                    return true;

                }

            }

        }

        return false;

    }

}
