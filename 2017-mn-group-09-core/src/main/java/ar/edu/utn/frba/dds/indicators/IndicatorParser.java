package ar.edu.utn.frba.dds.indicators;

import ar.edu.utn.frba.dds.exception.ParserErrorException;
import ar.edu.utn.frba.dds.models.indicator.*;
import ar.edu.utn.frba.dds.parser.ExpressionBaseListener;
import ar.edu.utn.frba.dds.parser.ExpressionLexer;
import ar.edu.utn.frba.dds.parser.ExpressionParser;
import ar.edu.utn.frba.dds.utils.ConfigurationUtils;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.BitSet;
import java.util.Locale;

/**
 * Created by TATIANA on 12/5/2017.
 */
public class IndicatorParser {

    public static final String accountPrefix = ConfigurationUtils.getConfiguration("ar.edu.utn.frba.dds.indicator.accountPrefix");
    private static final ANTLRErrorListener _listener = createErrorListener();

    private static ANTLRErrorListener createErrorListener() {
        return new ANTLRErrorListener() {
            public void syntaxError(final Recognizer<?, ?> arg0, final Object obj, final int line, final int position, final String message, final RecognitionException ex) {
                throw new ParserErrorException(String.format(Locale.ROOT, "Exception parsing expression: '%s' on line %s, position %s", message, line, position));
            }

            public void reportContextSensitivity(final Parser arg0, final DFA arg1, final int arg2, final int arg3, final int arg4, final ATNConfigSet arg5) {
            }

            public void reportAttemptingFullContext(final Parser arg0, final DFA arg1, final int arg2, final int arg3, final BitSet arg4, final ATNConfigSet arg5) {
            }

            public void reportAmbiguity(final Parser arg0, final DFA arg1, final int arg2, final int arg3, final boolean arg4, final BitSet arg5, final ATNConfigSet arg6) {
            }
        };
    }

    public static void checkExpression(String s) {
        ANTLRInputStream antlrInputStream = new ANTLRInputStream(s);
        ExpressionLexer lexer = new ExpressionLexer(antlrInputStream);
        lexer.removeErrorListeners();
        lexer.addErrorListener(_listener);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ExpressionParser parser = new ExpressionParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(_listener);
        ParseTree tree = parser.calc();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(new ExpressionBaseListener(), tree);
    }

    public IndicatorExpression analize(String s) {
        ANTLRInputStream antlrInputStream = new ANTLRInputStream(s);
        ExpressionLexer lexer = new ExpressionLexer(antlrInputStream);
        lexer.removeErrorListeners();
        lexer.addErrorListener(_listener);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ExpressionParser parser = new ExpressionParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(_listener);
        return visit(parser.expr());
    }

    private IndicatorExpression visit(final ExpressionParser.ExprContext context) {
        if (context.indicator() != null) {
            if (context.indicator().getText().startsWith(accountPrefix)) {
                return new IndicatorExpressionAccount(context.indicator().getText().substring(accountPrefix.length()));
            } else {
                return new IndicatorExpressionIndicator(context.indicator().getText());
            }
        } else if (context.number() != null) { //Just a number
            return new IndicatorExpressionNumber(Double.parseDouble(context.number().getText()));
        } else if (context.BR_CLOSE() != null) { //Expression between brackets
            return visit(context.expr(0));
        } else if (context.TIMES() != null) { //Expression * expression
            return new IndicatorExpressionOperation(visit(context.expr(0)), visit(context.expr(1)), IndicatorOperatorEnum.TIMES);
        } else if (context.DIV() != null) { //Expression / expression
            return new IndicatorExpressionOperation(visit(context.expr(0)), visit(context.expr(1)), IndicatorOperatorEnum.DIV);
        } else if (context.PLUS() != null) { //Expression + expression
            return new IndicatorExpressionOperation(visit(context.expr(0)), visit(context.expr(1)), IndicatorOperatorEnum.PLUS);
        } else if (context.MINUS() != null) { //Expression - expression
            return new IndicatorExpressionOperation(visit(context.expr(0)), visit(context.expr(1)), IndicatorOperatorEnum.MINUS);
        } else {
            throw new ParserErrorException();
        }
    }
}
