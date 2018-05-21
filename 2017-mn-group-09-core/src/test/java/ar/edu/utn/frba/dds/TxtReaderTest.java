package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.controllers.TxtReader;
import ar.edu.utn.frba.dds.exception.InvalidFileFormatException;
import ar.edu.utn.frba.dds.utils.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by pablomorrone on 12/6/17.
 */
public class TxtReaderTest {

    private String wrongPath;
    private String wrongExtension;
    private String rightPath;
    private TxtReader txtReader;


    @Before
    public void setUp() throws Exception {
        wrongPath = "/files/noExisteArchivo.txt";
        wrongExtension = "/files/empresas.csv";
        rightPath = "/files/identifier.txt";

        txtReader = new TxtReader();
    }

    @Test(expected = InvalidFileFormatException.class)
    public void testFileNotFound() throws Exception {
        this.txtReader.parse(wrongPath);
    }

    @Test(expected = InvalidFileFormatException.class)
    public void testInvalidFormat() throws Exception {
        this.txtReader.parse(FileUtils.filePath(this.getClass(), wrongExtension));
    }

    @Test
    public void testRightFile() throws Exception {
        List<String> lines = this.txtReader.parse(FileUtils.filePath(this.getClass(), rightPath));
        assertEquals(lines.size(),2);
    }
}
