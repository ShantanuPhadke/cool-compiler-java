/*
 *  The scanner definition for COOL.
 */

import java_cup.runtime.Symbol;
 
%%

%{

/*  Stuff enclosed in %{ %} is copied verbatim to the lexer class
 *  definition, all the extra variables/functions you want to use in the
 *  lexer actions should go here.  Don't remove or modify anything that
 *  was there initially.  */

    // Max size of string constants
    static int MAX_STR_CONST = 1025;

    // For assembling string constants
    StringBuffer string_buf = new StringBuffer();

    private int curr_lineno = 1;
    int get_curr_lineno() {
  return curr_lineno;
    }

    // For keeping track of comment depth (nested comment blocks)
    private int curr_comment_depth = 0;
    int get_curr_comment_depth() {
       return curr_comment_depth;
    }

    private AbstractSymbol filename;

    void set_filename(String fname) {
  filename = AbstractTable.stringtable.addString(fname);
    }

    AbstractSymbol curr_filename() {
  return filename;
    }
%}

%state STRING_LENGTH_ERROR
%state NULL_CHARACTER_STRING_ERROR
%state NEWLINE_STRING_ERROR
%state STRING
%state SINGLE_LINE_COMMENT
%state MULTI_LINE_COMMENT

DIGIT = [0-9]
INTEGER = {DIGIT}+
CAPITAL = [A-Z]
LOWER = [a-z]
LETTER = ({CAPITAL}|{LOWER})

TYPE_IDENTIFIER = {CAPITAL}({LETTER}|{DIGIT}|_)*
OBJECT_IDENTIFIER = {LOWER}({LETTER}|{DIGIT}|_)*
NEWLINE = \n
FORMFEED = \f
CARRIAGE_RETURN = \r
TAB = \t
VERTICAL_TAB = \013
BACKSPACE = \b
NULL_CHARACTER = \0
SPACE = " "
WHITESPACE = ({SPACE}|{FORMFEED}|{CARRIAGE_RETURN}|{TAB}|{VERTICAL_TAB})+
STRING = ({LETTER}|{DIGIT}|{WHITESPACE})*
SINGLELINE_COMMENT_START = --
MULTILINE_COMMENT_START = "(*"
MULTILINE_COMMENT_END = "*)"
BACKSLASH = \\
ESCAPED_CHARACTER = {BACKSLASH}([^b]|[^t]|[^n]|[^f])
ESCAPED_NEWLINE = {BACKSLASH}{NEWLINE}

C = [Cc]
L = [Ll]
A = [Aa]
S = [Ss]
E = [Ee]
I = [Ii]
F = [Ff]
f = f
V = [Vv]
O = [Oo]
D = [Dd]
W = [Ww]
H = [Hh]
N = [Nn]
P = [Pp]
R = [Rr]
U = [Uu]
T = [Tt]
t = t

CLASS = {C}{L}{A}{S}{S}
ELSE = {E}{L}{S}{E}
FALSE = {f}{A}{L}{S}{E}
FI = {F}{I}
IF = {I}{F}
IN = {I}{N}
INHERITS = {I}{N}{H}{E}{R}{I}{T}{S}
ISVOID = {I}{S}{V}{O}{I}{D}
LET = {L}{E}{T}
LOOP = {L}{O}{O}{P}
POOL = {P}{O}{O}{L}
THEN = {T}{H}{E}{N}
WHILE = {W}{H}{I}{L}{E}
CASE = {C}{A}{S}{E}
ESAC = {E}{S}{A}{C}
NEW = {N}{E}{W}
OF = {O}{F}
NOT = {N}{O}{T}
TRUE = {t}{R}{U}{E}

%init{

/*  Stuff enclosed in %init{ %init} is copied verbatim to the lexer
 *  class constructor, all the extra initialization you want to do should
 *  go here.  Don't remove or modify anything that was there initially. */

    // empty for now
%init}

%eofval{

/*  Stuff enclosed in %eofval{ %eofval} specifies java code that is
 *  executed when end-of-file is reached.  If you use multiple lexical
 *  states and want to do something special if an EOF is encountered in
 *  one of those states, place your code in the switch statement.
 *  Ultimately, you should return the EOF symbol, or your lexer won't
 *  work.  */

    switch(yy_lexical_state) {
    case YYINITIAL:
  /* nothing special to do in the initial state */
  break;
  /* If necessary, add code for other states here, e.g:
     case COMMENT:
     ...
     break;
  */
    case MULTI_LINE_COMMENT:
      yybegin(YYINITIAL);
      return new Symbol(TokenConstants.ERROR, "EOF in Comment");
    case STRING:
      yybegin(YYINITIAL);
      return new Symbol(TokenConstants.ERROR, "EOF in String Constant");
    }
    return new Symbol(TokenConstants.EOF);
%eofval}

%class CoolLexer
%cup

%%
<YYINITIAL>"=>" {
  /* Sample lexical rule for "=>" arrow.
     Further lexical rules should be defined
     here, after the last %% separator */
  return new Symbol(TokenConstants.DARROW);
}

<YYINITIAL>{VERTICAL_TAB} {
  curr_lineno+=1;
}

<YYINITIAL>{CARRIAGE_RETURN} {
  curr_lineno+=1;
}

<YYINITIAL>{NEWLINE} {
  curr_lineno+=1;
}

<YYINITIAL>{CLASS} {
  //[C][L][A][S][S]
  return new Symbol(TokenConstants.CLASS);
}

<YYINITIAL>{ELSE} {
  return new Symbol(TokenConstants.ELSE);
}

<YYINITIAL>{FALSE} {
  return new Symbol(TokenConstants.BOOL_CONST, java.lang.Boolean.FALSE);
}

<YYINITIAL>{FI} {
  return new Symbol(TokenConstants.FI);
}

<YYINITIAL>{IF} {
  return new Symbol(TokenConstants.IF);
}

<YYINITIAL>{IN} {
  return new Symbol(TokenConstants.IN);
}

<YYINITIAL>{INHERITS} {
  return new Symbol(TokenConstants.INHERITS);
}

<YYINITIAL>{ISVOID} {
  return new Symbol(TokenConstants.ISVOID);
}

<YYINITIAL>{LET} {
  return new Symbol(TokenConstants.LET);
}

<YYINITIAL>{LOOP} {
  return new Symbol(TokenConstants.LOOP);
}

<YYINITIAL>{POOL} {
  return new Symbol(TokenConstants.POOL);
}

<YYINITIAL>{THEN} {
  return new Symbol(TokenConstants.THEN);
}

<YYINITIAL>{WHILE} {
  return new Symbol(TokenConstants.WHILE);
}

<YYINITIAL>{CASE} {
  return new Symbol(TokenConstants.CASE);
}

<YYINITIAL>{ESAC} {
  return new Symbol(TokenConstants.ESAC);
}

<YYINITIAL>{NEW} {
  return new Symbol(TokenConstants.NEW);
}

<YYINITIAL>{OF} {
  return new Symbol(TokenConstants.OF);
}

<YYINITIAL>{NOT} {
  return new Symbol(TokenConstants.NOT);
}

<YYINITIAL>{TRUE} {
  return new Symbol(TokenConstants.BOOL_CONST, java.lang.Boolean.TRUE);
}

<YYINITIAL>"(" {
  return new Symbol(TokenConstants.LPAREN);
}

<YYINITIAL>")" {
  return new Symbol(TokenConstants.RPAREN);
}

<YYINITIAL>"+" {
  return new Symbol(TokenConstants.PLUS);
}

<YYINITIAL>"@" {
  return new Symbol(TokenConstants.AT);
}

<YYINITIAL>"-" {
  return new Symbol(TokenConstants.MINUS);
}

<YYINITIAL>"*" {
  return new Symbol(TokenConstants.MULT);
}

<YYINITIAL>"/" {
  return new Symbol(TokenConstants.DIV);
}

<YYINITIAL>"~" {
  return new Symbol(TokenConstants.NEG);
}

<YYINITIAL>"<" {
  return new Symbol(TokenConstants.LT);
}

<YYINITIAL>"<=" {
  return new Symbol(TokenConstants.LE);
}

<YYINITIAL>"=" {
  return new Symbol(TokenConstants.EQ);
}

<YYINITIAL>"<-" {
  return new Symbol(TokenConstants.ASSIGN);
}

<YYINITIAL>"{" {
  return new Symbol(TokenConstants.LBRACE);
}

<YYINITIAL>"}" {
  return new Symbol(TokenConstants.RBRACE);
}

<YYINITIAL>";" {
  return new Symbol(TokenConstants.SEMI);
}

<YYINITIAL>":" {
  return new Symbol(TokenConstants.COLON);
}

<YYINITIAL>"." {
  return new Symbol(TokenConstants.DOT);
}

<YYINITIAL>"," {
  return new Symbol(TokenConstants.COMMA);
}

<YYINITIAL>{INTEGER} {
  AbstractSymbol intEntry = AbstractTable.inttable.addString(yytext());
  return new Symbol(TokenConstants.INT_CONST, intEntry);
}

<YYINITIAL>{TYPE_IDENTIFIER} {
  AbstractSymbol typeIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.TYPEID, typeIdEntry);
}

<YYINITIAL>{OBJECT_IDENTIFIER} {
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}

<YYINITIAL>\" {
  yybegin(STRING);
  // Start of a new String
  string_buf.setLength(0);
}

<STRING>\" {
  yybegin(YYINITIAL);
  AbstractSymbol strEntry = AbstractTable.stringtable.addString(string_buf.toString());
  return new Symbol(TokenConstants.STR_CONST, strEntry);
}

<STRING>\\\" {
  if (string_buf.length() >= MAX_STR_CONST-1) {
    yybegin(STRING_LENGTH_ERROR);
    return new Symbol(TokenConstants.ERROR, "String constant too long");
  } else {
    string_buf.append(yytext().substring(1));
  }
}

<STRING>\\{NEWLINE} {
  if (string_buf.length() >= MAX_STR_CONST-1) {
    yybegin(STRING_LENGTH_ERROR);
    return new Symbol(TokenConstants.ERROR, "String constant too long");
  } else {
    string_buf.append(yytext().substring(1));
    // Still go to the next line even though the newline is escaped
    curr_lineno+=1;
  }
}

<STRING>\\t {
  if (string_buf.length() >= MAX_STR_CONST-1) {
    yybegin(STRING_LENGTH_ERROR);
    return new Symbol(TokenConstants.ERROR, "String constant too long");
  } else {
    string_buf.append("\t");
  }
}

<STRING>\\f {
  if (string_buf.length() >= MAX_STR_CONST-1) {
    yybegin(STRING_LENGTH_ERROR);
    return new Symbol(TokenConstants.ERROR, "String constant too long");
  } else {
    string_buf.append("\f");
  }
}

<STRING>\\n {
  if (string_buf.length() >= MAX_STR_CONST-1) {
    yybegin(STRING_LENGTH_ERROR);
    return new Symbol(TokenConstants.ERROR, "String constant too long");
  } else {
    string_buf.append("\n");
  }
}

<STRING>\\b {
  if (string_buf.length() >= MAX_STR_CONST-1) {
    yybegin(STRING_LENGTH_ERROR);
    return new Symbol(TokenConstants.ERROR, "String constant too long");
  } else {
    string_buf.append("\b");
  }
}

<STRING>{VERTICAL_TAB} {
   if (string_buf.length() >= MAX_STR_CONST-1) {
    yybegin(STRING_LENGTH_ERROR);
    return new Symbol(TokenConstants.ERROR, "String constant too long");
   } else {
     string_buf.append(yytext());
   }
}

<STRING>{CARRIAGE_RETURN} {
   if (string_buf.length() >= MAX_STR_CONST-1) {
    yybegin(STRING_LENGTH_ERROR);
    return new Symbol(TokenConstants.ERROR, "String constant too long");
   } else {
     string_buf.append(yytext());
   }
}

<STRING>\\{NULL_CHARACTER} {
  yybegin(NULL_CHARACTER_STRING_ERROR);
  return new Symbol(TokenConstants.ERROR, "String contains escaped null character");
}

<STRING>{NULL_CHARACTER} {
  yybegin(NULL_CHARACTER_STRING_ERROR);
  return new Symbol(TokenConstants.ERROR, "String contains null character");
}
 
<STRING>{NEWLINE} {
  curr_lineno+=1;
  yybegin(YYINITIAL);
  //yybegin(NEWLINE_STRING_ERROR);
  return new Symbol(TokenConstants.ERROR, "Unterminated string constant");
}

<STRING>{ESCAPED_CHARACTER} {
  if (string_buf.length() >= MAX_STR_CONST-1) {
    yybegin(STRING_LENGTH_ERROR);
    return new Symbol(TokenConstants.ERROR, "String constant too long");
  } else {
    string_buf.append(yytext().substring(1));
  }
}

<STRING>\\{DIGIT} {
  if (string_buf.length() >= MAX_STR_CONST-1) {
    yybegin(STRING_LENGTH_ERROR);
    return new Symbol(TokenConstants.ERROR, "String constant too long");
  } else {
    string_buf.append(yytext().substring(1));
  }
}

<STRING>\\ {
  if (string_buf.length() >= MAX_STR_CONST-1) {
    yybegin(STRING_LENGTH_ERROR);
    return new Symbol(TokenConstants.ERROR, "String constant too long");
  } else {
    string_buf.append(yytext());
  }
}

<STRING>. {
  if (string_buf.length() >= MAX_STR_CONST-1) {
    yybegin(STRING_LENGTH_ERROR);
    return new Symbol(TokenConstants.ERROR, "String constant too long");
  } else {
    string_buf.append(yytext());
    //return new Symbol(TokenConstants.ERROR, "" + string_buf.length() + ", " + yytext());
  }
}

<STRING_LENGTH_ERROR>\" {
  yybegin(YYINITIAL);
}

<STRING_LENGTH_ERROR> . {}

<NULL_CHARACTER_STRING_ERROR>\" {
  yybegin(YYINITIAL);
}

<NULL_CHARACTER_STRING_ERROR>{NEWLINE} {
  curr_lineno+=1;
  yybegin(YYINITIAL);
}

<NULL_CHARACTER_STRING_ERROR>. {}

<YYINITIAL>{SINGLELINE_COMMENT_START} {
  yybegin(SINGLE_LINE_COMMENT);
}

<SINGLE_LINE_COMMENT>. {}

<SINGLE_LINE_COMMENT>{NEWLINE} {
  // Return back to the initial state
  curr_lineno+=1;
  yybegin(YYINITIAL);
}

<YYINITIAL>{MULTILINE_COMMENT_START} {
  curr_comment_depth+=1;
  yybegin(MULTI_LINE_COMMENT);
}

<YYINITIAL>{MULTILINE_COMMENT_END} {
  // Returns the error for Unmatched *)
  return new Symbol(TokenConstants.ERROR, "Unmatched *)");
}

<MULTI_LINE_COMMENT>{NEWLINE} {
  curr_lineno+=1;
}

<MULTI_LINE_COMMENT>{CARRIAGE_RETURN} {
  curr_lineno+=1;
}

<MULTI_LINE_COMMENT>{MULTILINE_COMMENT_START} {
  curr_comment_depth+=1;
}

<MULTI_LINE_COMMENT>{MULTILINE_COMMENT_END} {
  curr_comment_depth-=1;
  if (get_curr_comment_depth() == 0) {
    yybegin(YYINITIAL);
  }
}

<MULTI_LINE_COMMENT>. {}

<YYINITIAL>{WHITESPACE} {}

. {
  return new Symbol(TokenConstants.ERROR, yytext());
}

.                               { /* This rule should be the very last
                                     in your lexical specification and
                                     will match match everything not
                                     matched by other lexical rules. */
                                  System.err.println("LEXER BUG - UNMATCHED: " + yytext()); }
