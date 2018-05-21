grammar Expression;
/* Lexical rules */

calc: expr EOF;

expr
:BR_OPEN expr BR_CLOSE
|expr TIMES expr
|expr DIV expr
|expr PLUS expr
|expr MINUS expr
|number
|indicator
;

indicator: INDICATOR;

number: DECIMAL;

PLUS:   'plus'  | '+';
MINUS:  'minus' | '-';
TIMES:  'times' | '*';
DIV:    'div'   | '/';

DECIMAL : '-'?[0-9]+('.'[0-9]+)? ;

INDICATOR : [a-zA-Z_][a-zA-Z_0-9]* ;

BR_OPEN: '(';
BR_CLOSE: ')';

WS: [ \t\r\n]+ -> skip;