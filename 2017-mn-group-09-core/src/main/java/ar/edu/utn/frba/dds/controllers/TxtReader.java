package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.exception.InvalidFileFormatException;
import ar.edu.utn.frba.dds.utils.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pablomorrone on 10/6/17.
 */
public class TxtReader {

    public static List<String> parse(String path) throws InvalidFileFormatException {
        String line = "";
        validateFile(path);
        List<String> lines = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new java.io.FileReader(path))) {
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new InvalidFileFormatException("Verifique que el archivo exista");
        }
        return lines;
    }

    public static void validateFile(String path) throws InvalidFileFormatException {
        if(!FileUtils.isTxtFile(new File(path))){
            throw new InvalidFileFormatException("El archivo debe ser txt. Cargue otro archivo");
        }
    }
}
