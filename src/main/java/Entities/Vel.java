/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author Eduardo.Fernando
 */
public class Vel {

    private long Id;
    private String Regra;
    private String Justificativa;

    public Vel() {
    }

    public Vel(long Id, String Regra, String Justificativa) {
        this.Id = Id;
        this.Regra = Regra;
        this.Justificativa = Justificativa;

    }

    public long getId() {
        return Id;
    }

    public void setId(long Id) {
        this.Id = Id;
    }

    public String getRegra() {
        return Regra;
    }

    public void setRegra(String Regra) {
        this.Regra = Regra;
    }

    public String getJustificativa() {
        return Justificativa;
    }

    public void setJustificativa(String Justificativa) {
        this.Justificativa = Justificativa;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (int) (this.Id ^ (this.Id >>> 32));
        hash = 79 * hash + Objects.hashCode(this.Regra);
        hash = 79 * hash + Objects.hashCode(this.Justificativa);

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
        final Vel other = (Vel) obj;
        if (this.Id != other.Id) {
            return false;
        }

        if (!Objects.equals(this.Regra, other.Regra)) {
            return false;
        }
        return Objects.equals(this.Justificativa, other.Justificativa);
    }

    @Override
    public String toString() {
        return "Vel{" + "Id=" + Id + ", Regra=" + Regra + ", Justificativa=" + Justificativa + '}';
    }

}
