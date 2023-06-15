/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import exceptions.DomicilioNaoEncontrado;
import Entities.Vel;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 *
 * @author Eduardo.Fernando
 */
public final class VelDao {

    private File VelFile;

    public VelDao(File VelFile) {
        this.VelFile = VelFile;
    }

    public List<Vel> Vels() {

        List<Vel> Vels = new ArrayList<>();

        String nomeArquivo = this.VelFile.getAbsolutePath();

        char delimitador = ',';

        try (CSVParser parser = new CSVParser(new FileReader(nomeArquivo), CSVFormat.DEFAULT.withDelimiter(delimitador).withHeader())) {

            for (CSVRecord record : parser) {

                Vels.add(getVelObject(record));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Vels;
    }

    public Vel getVelObject(CSVRecord record) {

        Vel Obj = new Vel(Integer.parseInt(record.get(0)), record.get(2), record.get(4));

        return Obj;

    }

    public List<String> converterStringEmList(String RawData) {

        List<String> Divisoes = Arrays.stream(RawData.split(","))
                .collect(Collectors.toList());

        return Divisoes;

    }

    public static void main(String[] args) throws DomicilioNaoEncontrado {

        VelDao vd = new VelDao(new File("c:/teste/20230427.vel"));

        System.out.println(vd.Vels());

    }

}
