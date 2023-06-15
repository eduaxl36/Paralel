/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flag;

import java.time.LocalDate;

/**
 *
 * @author Eduardo.Fernando
 */
public class entidadeFlag {
    
    
private int Id;    
private String Autor;
private String TipoCambio;
private LocalDate DataAbertura;
private LocalDate DataFechamento;
private boolean Flag;
private String Comment;

    public entidadeFlag() {
    }

    public entidadeFlag(int id,String Autor, String TipoCambio, LocalDate DataAbertura, LocalDate DataFechamento, boolean Flag,String Comment) {
        this.Autor = Autor;
        this.TipoCambio = TipoCambio;
        this.DataAbertura = DataAbertura;
        this.DataFechamento = DataFechamento;
        this.Flag = Flag;
        this.Comment =Comment;
        this.Id = id;
    }

    public String getAutor() {
        return Autor;
    }

    public void setAutor(String Autor) {
        this.Autor = Autor;
    }

    public String getTipoCambio() {
        return TipoCambio;
    }

    public void setTipoCambio(String TipoCambio) {
        this.TipoCambio = TipoCambio;
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

    public boolean isFlag() {
        return Flag;
    }

    public void setFlag(boolean Flag) {
        this.Flag = Flag;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String Comment) {
        this.Comment = Comment;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }


    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("entidadeFlag{");
        sb.append("Autor=").append(Autor);
        sb.append(", TipoCambio=").append(TipoCambio);
        sb.append(", DataAbertura=").append(DataAbertura);
        sb.append(", DataFechamento=").append(DataFechamento);
        sb.append(", Flag=").append(Flag);
        sb.append('}');
        return sb.toString();
    }
    
    
    
}
