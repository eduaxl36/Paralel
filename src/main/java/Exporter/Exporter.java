/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exporter;

import javax.swing.JTable;

/**
 *
 * @author eduardo.fernando
 */
public class Exporter {

    public String tratarComentario(String Comentario) {

        return " " + Comentario;

    }

    public String tratarNumero(String Numero, int Parametro) {

        int TotalCaracteres = Numero.length();

        int Total = Parametro - TotalCaracteres + 1;

        String Espacos = "";

        for (int i = 0; i < Total; i++) {

            Espacos += " ";

        }

        return Espacos + Numero;

    }

    public String construirLinha(String Dom, String Inicio, String Fim, String Comment) {

        return    tratarNumero(Dom, 9) + ","
                + tratarNumero("-1", 5) + ","
                + tratarNumero("-1", 5) + ","
                + tratarNumero(Inicio, 10) + ","
                + tratarNumero(Fim, 10) + ","
                + tratarNumero("0", 5) + ","
                + tratarNumero("-1", 5) + ","
                + tratarComentario(Comment);

    }

    public String montarContador(JTable Tabela) {

        return "[NumItems]=" + tratarNumero("" + Tabela.getRowCount(), 9);

    }

    public String tratarTabela(JTable Tabela) {

        StringBuilder Sb = new StringBuilder();

        for (int i = 0; i < Tabela.getRowCount(); i++) {

            String Domicilio = Tabela.getValueAt(i, 0).toString();
            String Inicio = Tabela.getValueAt(i, 1).toString().replaceAll("-", "");
            String Fim = Tabela.getValueAt(i, 2).toString().replaceAll("-", "");
            String Comment = Tabela.getValueAt(i, 3).toString();

            Sb.append(construirLinha(Domicilio, Inicio, Fim, Comment)).append("\n");

        }

        return Sb.toString();
    }

    public String montarDarkList(JTable Tabela) {

        return montarContador(Tabela) + "\n"
                + tratarTabela(Tabela);

    }

}
