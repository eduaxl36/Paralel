/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Robot;

import com.google.gson.Gson;

public class ModelSender {

    private String tipo;
    private String caminho;

    public ModelSender(String tipo, String caminho) {
        this.tipo = tipo;
        this.caminho = caminho;
    }

    public ModelSender() {
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    @Override
    public String toString() {
        return "ModelSender{" + "tipo=" + tipo + ", caminho=" + caminho + '}';
    }
}
