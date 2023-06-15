/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import java.util.Objects;

/**
 *
 * @author Eduardo.Fernando
 */
  public  class VelGroup {
      
        private Long id;
        private boolean Rejeitado;
        private String Motivo;

    public VelGroup(Long id, boolean Rejeitado, String Motivo) {
        this.id = id;
        this.Rejeitado = Rejeitado;
        this.Motivo = Motivo;
    }

    public VelGroup() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isRejeitado() {
        return Rejeitado;
    }

    public void setRejeitado(boolean Rejeitado) {
        this.Rejeitado = Rejeitado;
    }

    public String getMotivo() {
        return Motivo;
    }

    public void setMotivo(String Motivo) {
        this.Motivo = Motivo;
    }

    @Override
    public String toString() {
        return "VelGroup{" + "id=" + id + ", Rejeitado=" + Rejeitado + ", Motivo=" + Motivo + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.id);
        hash = 17 * hash + (this.Rejeitado ? 1 : 0);
        hash = 17 * hash + Objects.hashCode(this.Motivo);
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
        final VelGroup other = (VelGroup) obj;
        if (this.Rejeitado != other.Rejeitado) {
            return false;
        }
        if (!Objects.equals(this.Motivo, other.Motivo)) {
            return false;
        }
        return Objects.equals(this.id, other.id);
    }

        
        
        
        
    }