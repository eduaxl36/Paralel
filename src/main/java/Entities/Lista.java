/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author eduardo.fernando
 */
public abstract class Lista {
    private long Id;
    private LocalDate DataAbertura;
    private LocalDate DataFechamento;
    private String Comentario;
    private boolean Status;

    public Lista(long Id, LocalDate DataAbertura, LocalDate DataFechamento, String Comentario, boolean Status) {
        this.Id = Id;
        this.DataAbertura = DataAbertura;
        this.DataFechamento = DataFechamento;
        this.Comentario = Comentario;
        this.Status = Status;
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

    @Override
    public String toString() {
        return "Darklist{" + "Id=" + Id + ", DataAbertura=" + DataAbertura + ", DataFechamento=" + DataFechamento + ", Comentario=" + Comentario + ", Status=" + Status + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + Objects.hashCode(this.DataAbertura);
        hash = 19 * hash + Objects.hashCode(this.DataFechamento);
        hash = 19 * hash + Objects.hashCode(this.Comentario);
        hash = 19 * hash + (this.Status ? 1 : 0);
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
        final Lista other = (Lista) obj;
        if (this.Id != other.Id) {
            return false;
        }
        if (this.Status != other.Status) {
            return false;
        }
        if (!Objects.equals(this.Comentario, other.Comentario)) {
            return false;
        }
        if (!Objects.equals(this.DataAbertura, other.DataAbertura)) {
            return false;
        }
        return Objects.equals(this.DataFechamento, other.DataFechamento);
    }

   
    
    
    
    
}
