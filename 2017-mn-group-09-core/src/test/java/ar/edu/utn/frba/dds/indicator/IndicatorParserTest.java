package ar.edu.utn.frba.dds.indicator;
import ar.edu.utn.frba.dds.exception.ParserErrorException;
import ar.edu.utn.frba.dds.indicators.IndicatorParser;
import org.junit.Test;


/**
 * Created by TATIANA on 12/5/2017.
 */
public class IndicatorParserTest {

    @Test(expected = ParserErrorException.class)
    public void testParserCheckerWithEqueal() throws Exception {
        IndicatorParser.checkExpression("hola = chau + 3");
    }

    @Test(expected = ParserErrorException.class)
    public void testParserCheckerErrorIllegalIndicator() throws Exception {
        IndicatorParser.checkExpression("hola # 3");
    }

    @Test
    public void testParserChecker() throws Exception {
        IndicatorParser.checkExpression("hola - chau + 3");
    }
}
