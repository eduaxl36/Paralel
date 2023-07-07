/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Entities.Lista;

import java.io.File;

import java.time.LocalDate;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author eduardo.fernando
 */
public abstract class LstDao {

    protected LocalDate DataProducao;
    protected File ListFile;
    protected JTable Tabela;
    protected DefaultTableModel Modelo;

    public LstDao() {

    }

    public LstDao(LocalDate DataProducao, File ListFile, JTable Tabela) {
        this.DataProducao = DataProducao;
        this.ListFile = ListFile;
        this.Tabela = Tabela;

    }

    public abstract boolean verificartSeEstaEmLista(LocalDate DataAbertura, LocalDate DataFechamento);

    public abstract Lista retornaObjetoLst(String Raw);

    public abstract List<? extends Lista> Listas() throws Exception;

    public abstract List<? extends Lista> getStatus() throws Exception;

    public abstract void carregarLista() throws Exception;

    public LocalDate getDataProducao() {
        return DataProducao;
    }

    public void setDataProducao(LocalDate DataProducao) {
        this.DataProducao = DataProducao;
    }

    public File getListFile() {
        return ListFile;
    }

    public void setListFile(File ListFile) {
        this.ListFile = ListFile;
    }

    public JTable getTabela() {
        return Tabela;
    }

    public void setTabela(JTable Tabela) {
        this.Tabela = Tabela;
    }

    public DefaultTableModel getModelo() {
        return Modelo;
    }

    public void setModelo(DefaultTableModel Modelo) {
        this.Modelo = Modelo;
    }

    @Override
    public String toString() {
        return "LstDao{" + "DataProducao=" + DataProducao + ", ListFile=" + ListFile + ", Tabela=" + Tabela + ", Modelo=" + Modelo + '}';
    }
    
    
    
    
    
    

}
