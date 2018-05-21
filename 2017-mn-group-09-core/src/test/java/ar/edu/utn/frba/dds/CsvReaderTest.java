package ar.edu.utn.frba.dds;
import ar.edu.utn.frba.dds.controllers.CsvReader;
import ar.edu.utn.frba.dds.exception.InvalidFileFormatException;
import ar.edu.utn.frba.dds.utils.FileUtils;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by gasti on 16/04/2017.
 */
public class CsvReaderTest {

    private String wrongPath1;
    private String wrongPath2;
    private String wrongPath3;
    private String rightPath;
    private CsvReader csvReader;


    @Before
    public void setUp() throws Exception {
        wrongPath1 = "";
        wrongPath2 = "/files/noExisteArchivo.csv";
        wrongPath3 = "/files/empresasExcel.xls";
        rightPath = "/files/empresas.csv";

        csvReader = new CsvReader();
    }

    @Test(expected = InvalidFileFormatException.class)
    public void testEmptyPath() throws Exception {
        this.csvReader.parse(wrongPath1);
    }

    @Test(expected = InvalidFileFormatException.class)
    public void testFileNotFound() throws Exception {
        this.csvReader.parse(wrongPath2);
    }

    @Test(expected = InvalidFileFormatException.class)
    public void testInvalidFormat() throws Exception {
        this.csvReader.parse(FileUtils.filePath(this.getClass(), wrongPath3));
    }

    @Test
    public void testRightFile() throws Exception {
        List<String[]> lines = this.csvReader.parse(FileUtils.filePath(this.getClass(), rightPath));
        assertEquals(lines.size(),7);
    }
}