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
public class Log extends Lista {
    
    
    private String DescStatus;
    private String AutorAlteracao;
    private int DiferencaDatas;
    private String AlteracaoRealizada;

    public Log(long Id, LocalDate DataAbertura, LocalDate DataFechamento, String Comentario, boolean Status,String DescStatus, String AutorAlteracao, int DiferencaDatas, String AlteracaoRealizada) {
        super(Id, DataAbertura, DataFechamento, Comentario, Status);
        this.DescStatus = DescStatus;
        this.AutorAlteracao = AutorAlteracao;
        this.DiferencaDatas = DiferencaDatas;
        this.AlteracaoRealizada = AlteracaoRealizada;
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
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.DescStatus);
        hash = 17 * hash + Objects.hashCode(this.AutorAlteracao);
        hash = 17 * hash + this.DiferencaDatas;
        hash = 17 * hash + Objects.hashCode(this.AlteracaoRealizada);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Log other = (Log) obj;
        if (this.DiferencaDatas != other.DiferencaDatas) {
            return false;
        }
        if (!Objects.equals(this.DescStatus, other.DescStatus)) {
            return false;
        }
        if (!Objects.equals(this.AutorAlteracao, other.AutorAlteracao)) {
            return false;
        }
        return Objects.equals(this.AlteracaoRealizada, other.AlteracaoRealizada);
    }

    @Override
    public String toString() {
        return "Log{" + "DescStatus=" + DescStatus + ", AutorAlteracao=" + AutorAlteracao + ", DiferencaDatas=" + DiferencaDatas + ", AlteracaoRealizada=" + AlteracaoRealizada + '}';
    }


    
    
    
    
    
}
