/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Entities.Vel;
import Entities.VelGroup;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;


/**
 *
 * @author Eduardo.Fernando
 */
public class VelGroupDao {

    private VelDao ArquivoVel;

    public VelGroupDao(VelDao ArquivoVel) {
        this.ArquivoVel = ArquivoVel;
    }

    public boolean validarPresenca(long Domicilio) {

        boolean Validacao = this.ArquivoVel.Vels().stream().filter(x -> x.getId()==Domicilio)
                .anyMatch(x -> x.getId() == Domicilio);

        return Validacao;
    }

    public VelGroup getGrupoVel(long Domicilio) {

        
        
        if (validarPresenca(Domicilio)) {

            List<Vel> collect = this.ArquivoVel.Vels().stream().filter(x -> x.getJustificativa().contains("_HH"))
                    .filter(x -> x.getId() == Domicilio)
                    .collect(Collectors.toList());

            if (collect.isEmpty()) {

                return new VelGroup(Domicilio, false, "Não Rejeitado");

            } else {

                return new VelGroup(Domicilio, true, collect.get(0).getRegra());

            }

        } else {

            return new VelGroup(Domicilio, true, "Nao encontrado no vel");

        }

    }

    public static void main(String[] args) {

        VelDao vd = new VelDao(new File("c:/teste/20230427.vel"));
        VelGroupDao dao = new VelGroupDao(vd);
        System.out.println(dao.getGrupoVel(45));
        
//        1550
//1731
//2010


    }

}
