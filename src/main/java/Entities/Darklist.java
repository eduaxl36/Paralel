/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import java.time.LocalDate;


/**
 *
 * @author Eduardo.Fernando
 */
public class Darklist extends Lista {

    public Darklist(long Id, LocalDate DataAbertura, LocalDate DataFechamento, String Comentario, boolean Status) {
        super(Id, DataAbertura, DataFechamento, Comentario, Status);
    }

}
