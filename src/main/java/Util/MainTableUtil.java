/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import static viewClient.DarklistManagerViewClient.tbMainViewDarkList;

/**
 *
 * @author eduardo.fernando
 */
public final class MainTableUtil {

    JPopupMenu PopMenu;

    JMenuItem MenuItem1;
    JMenuItem MenuItem2;
    JMenuItem MenuItem3;
    JMenuItem MenuItem4;

    ImageIcon Img1;
    ImageIcon Img2;
    ImageIcon Img3;
    ImageIcon Img4;

    public MainTableUtil() {

        PopMenu = new JPopupMenu();

        montaMenuCompleto();

    }

    public void construirLabels() {

        MenuItem1 = new JMenuItem("Anadir hogar en Darklist");
        MenuItem2 = new JMenuItem("Cambiar");
        MenuItem3 = new JMenuItem("Borrar Linea");
        MenuItem4 = new JMenuItem("Aprovar Cambio");

    }

    public void montarIcones() {

        Img1 = new ImageIcon(getClass().getResource("/img/darklist.png"));
        Img2 = new ImageIcon(getClass().getResource("/img/allow.png"));
        Img3 = new ImageIcon(getClass().getResource("/img/delete.png"));
        Img4 = new ImageIcon(getClass().getResource("/img/allow.png"));

    }

    public void adaptarIconesMenu() {

        MenuItem1.setIcon(Img1);
        MenuItem2.setIcon(Img2);
        MenuItem3.setIcon(Img3);
        MenuItem4.setIcon(Img4);

    }

    public void ajustaMenuComSeparadores() {

        PopMenu.add(MenuItem1);
        PopMenu.addSeparator();
        PopMenu.add(MenuItem2);
        PopMenu.addSeparator();
        PopMenu.add(MenuItem3);
        PopMenu.addSeparator();
        PopMenu.add(MenuItem4);
        PopMenu.addSeparator();

    }

    public void montaMenuCompleto() {

        montarIcones();
        construirLabels();
        adaptarIconesMenu();
        ajustaMenuComSeparadores();

    }

    public void mostrarMenuFlutuante(Component component,
            int x, int y,
            ActionListener Acao1,
            ActionListener Acao2,
            JTable Tabela,
            boolean AprovacaoCambio,
            boolean AprovacaoNuevaLinea,
            boolean NegarCambio
    ) {

        MenuItem2.addActionListener((e) -> {
            Acao1.actionPerformed(e);

        });
        MenuItem1.addActionListener((e) -> {
            Acao2.actionPerformed(e);

        });

        MenuItem3.addActionListener((ActionEvent e) -> new Util(Tabela).removerLinha());

        MenuItem4.addActionListener((ActionEvent e) -> Tabela.setValueAt("Cambio Aprovado", Tabela.getSelectedRow(), 5));

        MenuItem4.setEnabled(AprovacaoCambio);

        MenuItem3.setEnabled(AprovacaoNuevaLinea);

        MenuItem2.setEnabled(NegarCambio);

        PopMenu.show(component, x, y);
    }

}
