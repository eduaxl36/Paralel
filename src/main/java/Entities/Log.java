/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author Eduardo.Fernando
 */
public class Log {
    
    
    private long Id;
    private LocalDate DataAbertura;
    private LocalDate DataFechamento;
    private String Comentario;
    private boolean Status;
    private String DescStatus;
    private String AutorAlteracao;
    private int DiferencaDatas;
    private String AlteracaoRealizada;

    public Log() {
    }

    public Log(long Id, LocalDate DataAbertura, LocalDate DataFechamento, String Comentario, boolean Status, String DescStatus, String AutorAlteracao, int DiferencaDatas, String AlteracaoRealizada) {
        this.Id = Id;
        this.DataAbertura = DataAbertura;
        this.DataFechamento = DataFechamento;
        this.Comentario = Comentario;
        this.Status = Status;
        this.DescStatus = DescStatus;
        this.AutorAlteracao = AutorAlteracao;
        this.DiferencaDatas = DiferencaDatas;
        this.AlteracaoRealizada = AlteracaoRealizada;
    }

    public long getId() {
        return Id;
    }

    public void setId(long Id) {
        this.Id = Id;
    }

    public LocalDate getDataAbertura() {
        return DataAbertura;
    }

    public void setDataAbertura(LocalDate DataAbertura) {
        this.DataAbertura = DataAbertura;
    }

    public LocalDate getDataFechamento() {
        return DataFechamento;
    }

    public void setDataFechamento(LocalDate DataFechamento) {
        this.DataFechamento = DataFechamento;
    }

    public String getComentario() {
        return Comentario;
    }

    public void setComentario(String Comentario) {
        this.Comentario = Comentario;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean Status) {
        this.Status = Status;
    }

    public String getDescStatus() {
        return DescStatus;
    }

    public void setDescStatus(String DescStatus) {
        this.DescStatus = DescStatus;
    }

    public String getAutorAlteracao() {
        return AutorAlteracao;
    }

    public void setAutorAlteracao(String AutorAlteracao) {
        this.AutorAlteracao = AutorAlteracao;
    }

    public int getDiferencaDatas() {
        return DiferencaDatas;
    }

    public void setDiferencaDatas(int DiferencaDatas) {
        this.DiferencaDatas = DiferencaDatas;
    }

    public String getAlteracaoRealizada() {
        return AlteracaoRealizada;
    }

    public void setAlteracaoRealizada(String AlteracaoRealizada) {
        this.AlteracaoRealizada = AlteracaoRealizada;
    }

    @Override
    public String toString() {
        return "Log{" + "Id=" + Id + ", DataAbertura=" + DataAbertura + ", DataFechamento=" + DataFechamento + ", Comentario=" + Comentario + ", Status=" + Status + ", DescStatus=" + DescStatus + ", AutorAlteracao=" + AutorAlteracao + ", DiferencaDatas=" + DiferencaDatas + ", AlteracaoRealizada=" + AlteracaoRealizada + '}';
    }
    

    
    
    
    
    
    
}
