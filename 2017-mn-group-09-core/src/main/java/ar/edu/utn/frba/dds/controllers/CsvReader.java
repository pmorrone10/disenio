package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.exception.InvalidFileFormatException;
import ar.edu.utn.frba.dds.utils.FileUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pablomorrone on 11/4/17.
 */
public class CsvReader {

    //Podria convertirse en una interfaz en la que cada tipo de archivo que se lea distinto implemente estos metodos.

    public static List<String[]> parse(String path) throws InvalidFileFormatException {
        String line = "";
        String cvsSplitBy = ";";
        validateFile(path);
        List<String[]> lines = new ArrayList<String[]>();
        try (BufferedReader br = new BufferedReader(new java.io.FileReader(path))) {
            while ((line = br.readLine()) != null) {
                lines.add(line.split(cvsSplitBy));
            }
        } catch (IOException e) {
            throw new InvalidFileFormatException("Verifique que el archivo exista");
        }
        return lines;
    }

    public static void validateFile(String path) throws InvalidFileFormatException {
        if(!FileUtils.isCsvFile(new File(path))){
            throw new InvalidFileFormatException("El archivo debe ser csv. Cargue otro archivo");
        }
    }
}
