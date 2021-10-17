/*
 *  The scanner definition for COOL.
 */
import java_cup.runtime.Symbol;


class CoolLexer implements java_cup.runtime.Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 128;
	private final int YY_EOF = 129;

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
    private AbstractSymbol filename;
    void set_filename(String fname) {
	filename = AbstractTable.stringtable.addString(fname);
    }
    AbstractSymbol curr_filename() {
	return filename;
    }
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private boolean yy_at_bol;
	private int yy_lexical_state;

	CoolLexer (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	CoolLexer (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private CoolLexer () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;

/*  Stuff enclosed in %init{ %init} is copied verbatim to the lexer
 *  class constructor, all the extra initialization you want to do should
 *  go here.  Don't remove or modify anything that was there initially. */
    // empty for now
	}

	private boolean yy_eof_done = false;
	private final int STRING = 1;
	private final int YYINITIAL = 0;
	private final int SINGLE_LINE_COMMENT = 2;
	private final int MULTI_LINE_COMMENT = 3;
	private final int yy_state_dtrans[] = {
		0,
		59,
		80,
		83
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NOT_ACCEPT,
		/* 60 */ YY_NO_ANCHOR,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NO_ANCHOR,
		/* 63 */ YY_NO_ANCHOR,
		/* 64 */ YY_NO_ANCHOR,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NO_ANCHOR,
		/* 67 */ YY_NO_ANCHOR,
		/* 68 */ YY_NO_ANCHOR,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NO_ANCHOR,
		/* 71 */ YY_NO_ANCHOR,
		/* 72 */ YY_NO_ANCHOR,
		/* 73 */ YY_NO_ANCHOR,
		/* 74 */ YY_NO_ANCHOR,
		/* 75 */ YY_NO_ANCHOR,
		/* 76 */ YY_NO_ANCHOR,
		/* 77 */ YY_NO_ANCHOR,
		/* 78 */ YY_NO_ANCHOR,
		/* 79 */ YY_NO_ANCHOR,
		/* 80 */ YY_NOT_ACCEPT,
		/* 81 */ YY_NO_ANCHOR,
		/* 82 */ YY_NO_ANCHOR,
		/* 83 */ YY_NOT_ACCEPT,
		/* 84 */ YY_NO_ANCHOR,
		/* 85 */ YY_NO_ANCHOR,
		/* 86 */ YY_NO_ANCHOR,
		/* 87 */ YY_NO_ANCHOR,
		/* 88 */ YY_NO_ANCHOR,
		/* 89 */ YY_NO_ANCHOR,
		/* 90 */ YY_NO_ANCHOR,
		/* 91 */ YY_NO_ANCHOR,
		/* 92 */ YY_NO_ANCHOR,
		/* 93 */ YY_NO_ANCHOR,
		/* 94 */ YY_NO_ANCHOR,
		/* 95 */ YY_NO_ANCHOR,
		/* 96 */ YY_NO_ANCHOR,
		/* 97 */ YY_NO_ANCHOR,
		/* 98 */ YY_NO_ANCHOR,
		/* 99 */ YY_NO_ANCHOR,
		/* 100 */ YY_NO_ANCHOR,
		/* 101 */ YY_NO_ANCHOR,
		/* 102 */ YY_NO_ANCHOR,
		/* 103 */ YY_NO_ANCHOR,
		/* 104 */ YY_NO_ANCHOR,
		/* 105 */ YY_NO_ANCHOR,
		/* 106 */ YY_NO_ANCHOR,
		/* 107 */ YY_NO_ANCHOR,
		/* 108 */ YY_NO_ANCHOR,
		/* 109 */ YY_NO_ANCHOR,
		/* 110 */ YY_NO_ANCHOR,
		/* 111 */ YY_NO_ANCHOR,
		/* 112 */ YY_NO_ANCHOR,
		/* 113 */ YY_NO_ANCHOR,
		/* 114 */ YY_NO_ANCHOR,
		/* 115 */ YY_NO_ANCHOR,
		/* 116 */ YY_NO_ANCHOR,
		/* 117 */ YY_NO_ANCHOR,
		/* 118 */ YY_NO_ANCHOR,
		/* 119 */ YY_NO_ANCHOR,
		/* 120 */ YY_NO_ANCHOR,
		/* 121 */ YY_NO_ANCHOR,
		/* 122 */ YY_NO_ANCHOR,
		/* 123 */ YY_NO_ANCHOR,
		/* 124 */ YY_NO_ANCHOR,
		/* 125 */ YY_NO_ANCHOR,
		/* 126 */ YY_NO_ANCHOR,
		/* 127 */ YY_NO_ANCHOR,
		/* 128 */ YY_NO_ANCHOR,
		/* 129 */ YY_NO_ANCHOR,
		/* 130 */ YY_NO_ANCHOR,
		/* 131 */ YY_NO_ANCHOR,
		/* 132 */ YY_NO_ANCHOR,
		/* 133 */ YY_NO_ANCHOR,
		/* 134 */ YY_NO_ANCHOR,
		/* 135 */ YY_NO_ANCHOR,
		/* 136 */ YY_NO_ANCHOR,
		/* 137 */ YY_NO_ANCHOR,
		/* 138 */ YY_NO_ANCHOR,
		/* 139 */ YY_NO_ANCHOR,
		/* 140 */ YY_NO_ANCHOR,
		/* 141 */ YY_NO_ANCHOR,
		/* 142 */ YY_NO_ANCHOR,
		/* 143 */ YY_NO_ANCHOR,
		/* 144 */ YY_NO_ANCHOR,
		/* 145 */ YY_NO_ANCHOR,
		/* 146 */ YY_NO_ANCHOR,
		/* 147 */ YY_NO_ANCHOR,
		/* 148 */ YY_NO_ANCHOR,
		/* 149 */ YY_NO_ANCHOR,
		/* 150 */ YY_NO_ANCHOR,
		/* 151 */ YY_NO_ANCHOR,
		/* 152 */ YY_NO_ANCHOR,
		/* 153 */ YY_NO_ANCHOR,
		/* 154 */ YY_NO_ANCHOR,
		/* 155 */ YY_NO_ANCHOR,
		/* 156 */ YY_NO_ANCHOR,
		/* 157 */ YY_NO_ANCHOR,
		/* 158 */ YY_NO_ANCHOR,
		/* 159 */ YY_NO_ANCHOR,
		/* 160 */ YY_NO_ANCHOR,
		/* 161 */ YY_NO_ANCHOR,
		/* 162 */ YY_NO_ANCHOR,
		/* 163 */ YY_NO_ANCHOR,
		/* 164 */ YY_NO_ANCHOR,
		/* 165 */ YY_NO_ANCHOR,
		/* 166 */ YY_NO_ANCHOR,
		/* 167 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"58,59:8,60,3,59,60,61,59:18,60,59,57,59:5,23,24,28,25,37,27,36,29,38:10,35," +
"34,31,1,2,59,26,39,40,41,42,43,10,40,44,45,40:2,46,40,47,48,49,40,50,51,15," +
"52,53,54,40:3,59:4,56,59,6,55,4,18,8,9,55,13,11,55:2,5,55,12,17,19,55,14,7," +
"21,22,16,20,55:3,32,59,33,30,59,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,168,
"0,1,2,1:2,3,4,5,1:3,6,7,1:2,8,1:6,9,1,10,1,11:2,12,11,1:5,11:7,13,11:7,1:9," +
"14,15,16,13:2,17,13:8,11,13:5,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32," +
"33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57," +
"58,59,11,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81," +
"82,83,84,85,86,87,88,89,90,91,92,93,94,11,13,95,96,97,98,99,100,101,102,61")[0];

	private int yy_nxt[][] = unpackFromString(103,62,
"1,2,3,4,5,115,157:2,159,60,6,81,117,157:2,61,84,86,157,161,163,165,157,7,8," +
"9,10,11,12,13,14,15,16,17,18,19,20,21,22,158:2,160,158,162,158,82,116,118,8" +
"5,164,158:4,166,157,3,23,3:2,24:2,-1:64,25,-1:63,157,167,119,157:3,121,157:" +
"4,121,157:7,-1:15,121,119,121:6,123,121:8,157,121,-1:9,158:7,62,158:11,-1:1" +
"5,158:7,62,158:11,-1:33,30,-1:60,31,-1:58,32,-1:38,33,-1:25,34,-1:72,22,-1:" +
"39,24,-1:43,24:2,-1:4,157:6,121,157:4,121,157:7,-1:15,121:17,157,121,-1:9,1" +
"57:6,121,157:2,149,157,121,157:7,-1:15,121:6,149,121:10,157,121,-1:9,158:19" +
",-1:15,158:19,-1:5,1,50:2,51,50:53,52,53,50:2,-1:5,157:2,131,157:3,121,26,1" +
"57:3,121,157:7,-1:15,121,131,121:5,26,121:9,157,121,-1:9,158:9,120,158:9,-1" +
":15,158:6,120,158:12,-1:9,158:9,142,158:9,-1:15,158:6,142,158:12,-1:29,58,-" +
"1:37,1,54:2,55,54:57,-1:5,157:3,133,157,27:2,157,28,157:2,121,157:7,-1:15,1" +
"21:9,28,121:3,133,121:3,157,121,-1:9,158:3,130,158,63:2,158,64,158:10,-1:15" +
",158:9,64,158:3,130,158:5,-1:5,1,56:2,57,56:24,79,56:32,-1:5,157:6,121,157:" +
"4,121,84,157:6,-1:15,121:17,157,121,-1:3,24:2,-1:4,158:5,65:2,158:12,-1:15," +
"158:19,-1:9,157:5,29:2,157:4,121,157:7,-1:15,121:17,157,121,-1:9,158:11,66," +
"158:5,66,158,-1:15,158:19,-1:9,157:6,121,157:4,35,157:5,35,157,-1:15,121:17" +
",157,121,-1:9,158:16,67,158:2,-1:15,158:16,67,158:2,-1:9,157:6,121,157:4,12" +
"1,157:4,36,157:2,-1:15,121:16,36,157,121,-1:9,158:11,68,158:5,68,158,-1:15," +
"158:19,-1:9,157:6,121,157:4,37,157:5,37,157,-1:15,121:17,157,121,-1:9,158:8" +
",42,158:10,-1:15,158:9,42,158:9,-1:9,157:4,38,157,121,157:4,121,157:7,-1:15" +
",121:5,38,121:11,157,121,-1:9,158:4,69,158:14,-1:15,158:5,69,158:13,-1:9,15" +
"7:6,121,157:4,121,157:3,39,157:3,-1:15,121:11,39,121:5,157,121,-1:9,158:4,7" +
"1,158:14,-1:15,158:5,71,158:13,-1:9,157:4,40,157,121,157:4,121,157:7,-1:15," +
"121:5,40,121:11,157,121,-1:9,72,158:18,-1:15,158:3,72,158:15,-1:9,41,157:5," +
"121,157:4,121,157:7,-1:15,121:3,41,121:13,157,121,-1:9,158:15,70,158:3,-1:1" +
"5,158:11,70,158:7,-1:9,157,43,157:4,121,157:4,121,157:7,-1:15,121:8,43,121:" +
"8,157,121,-1:9,158,74,158:17,-1:15,158:8,74,158:10,-1:9,157:6,121,157,73,15" +
"7:2,121,157:7,-1:15,121:9,73,121:7,157,121,-1:9,158:3,75,158:15,-1:15,158:1" +
"3,75,158:5,-1:9,157:4,44,157,121,157:4,121,157:7,-1:15,121:5,44,121:11,157," +
"121,-1:9,158:4,76,158:14,-1:15,158:5,76,158:13,-1:9,157:3,45,157:2,121,157:" +
"4,121,157:7,-1:15,121:13,45,121:3,157,121,-1:9,158:14,77,158:4,-1:15,158:4," +
"77,158:14,-1:9,157:4,46,157,121,157:4,121,157:7,-1:15,121:5,46,121:11,157,1" +
"21,-1:9,158:3,78,158:15,-1:15,158:13,78,158:5,-1:9,157:4,47,157,121,157:4,1" +
"21,157:7,-1:15,121:5,47,121:11,157,121,-1:9,157:6,121,157:4,121,157:2,48,15" +
"7:4,-1:15,121:4,48,121:12,157,121,-1:9,157:3,49,157:2,121,157:4,121,157:7,-" +
"1:15,121:13,49,121:3,157,121,-1:9,157:4,88,157,121,157:4,121,157,125,157:5," +
"-1:15,121:5,88,121:4,125,121:6,157,121,-1:9,158:4,87,158:8,132,158:5,-1:15," +
"158:5,87,158:4,132,158:8,-1:9,157:4,90,157,121,157:4,121,157,92,157:5,-1:15" +
",121:5,90,121:4,92,121:6,157,121,-1:9,158:4,89,158:8,91,158:5,-1:15,158:5,8" +
"9,158:4,91,158:8,-1:9,157:3,94,157:2,121,157:4,121,157:7,-1:15,121:13,94,12" +
"1:3,157,121,-1:9,158:4,93,158:14,-1:15,158:5,93,158:13,-1:9,158:2,138,158:1" +
"6,-1:15,158,138,158:17,-1:9,157:2,143,157:3,121,157:4,121,157:7,-1:15,121,1" +
"43,121:15,157,121,-1:9,158:3,95,158:15,-1:15,158:13,95,158:5,-1:9,157:6,121" +
",157:4,121,157,96,157:5,-1:15,121:10,96,121:6,157,121,-1:9,158:3,97,158:15," +
"-1:15,158:13,97,158:5,-1:9,157:3,98,157:2,121,157:4,121,157:7,-1:15,121:13," +
"98,121:3,157,121,-1:9,158:2,99,158:16,-1:15,158,99,158:17,-1:9,157:2,100,15" +
"7:3,121,157:4,121,157:7,-1:15,121,100,121:15,157,121,-1:9,158:12,140,158:6," +
"-1:15,158:15,140,158:3,-1:9,157,145,157:4,121,157:4,121,157:7,-1:15,121:8,1" +
"45,121:8,157,121,-1:9,158:13,101,158:5,-1:15,158:10,101,158:8,-1:9,157:6,12" +
"1,157:4,121,147,157:6,-1:15,121:15,147,121,157,121,-1:9,158:13,103,158:5,-1" +
":15,158:10,103,158:8,-1:9,157:6,121,157:4,121,157,102,157:5,-1:15,121:10,10" +
"2,121:6,157,121,-1:9,158:7,144,158:11,-1:15,158:7,144,158:11,-1:9,157:6,121" +
",151,157:3,121,157:7,-1:15,121:7,151,121:9,157,121,-1:9,158:3,105,158:15,-1" +
":15,158:13,105,158:5,-1:9,157:4,104,157,121,157:4,121,157:7,-1:15,121:5,104" +
",121:11,157,121,-1:9,158:13,146,158:5,-1:15,158:10,146,158:8,-1:9,157:6,121" +
",157:4,121,157:6,106,-1:15,121:14,106,121:2,157,121,-1:9,158:4,148,158:14,-" +
"1:15,158:5,148,158:13,-1:9,157:3,108,157:2,121,157:4,121,157:7,-1:15,121:13" +
",108,121:3,157,121,-1:9,158,107,158:17,-1:15,158:8,107,158:10,-1:9,157:3,11" +
"0,157:2,121,157:4,121,157:7,-1:15,121:13,110,121:3,157,121,-1:9,158:7,109,1" +
"58:11,-1:15,158:7,109,158:11,-1:9,157:6,121,157:4,121,157,153,157:5,-1:15,1" +
"21:10,153,121:6,157,121,-1:9,158:10,150,158:8,-1:15,158:12,150,158:6,-1:9,1" +
"57:4,154,157,121,157:4,121,157:7,-1:15,121:5,154,121:11,157,121,-1:9,158:7," +
"152,158:11,-1:15,158:7,152,158:11,-1:9,157,112,157:4,121,157:4,121,157:7,-1" +
":15,121:8,112,121:8,157,121,-1:9,158:11,111,158:5,111,158,-1:15,158:19,-1:9" +
",157:6,121,113,157:3,121,157:7,-1:15,121:7,113,121:9,157,121,-1:9,157:6,121" +
",157:3,155,121,157:7,-1:15,121:12,155,121:4,157,121,-1:9,157:6,121,156,157:" +
"3,121,157:7,-1:15,121:7,156,121:9,157,121,-1:9,157:6,121,157:4,114,157:5,11" +
"4,157,-1:15,121:17,157,121,-1:9,157,127,157,129,157:2,121,157:4,121,157:7,-" +
"1:15,121:8,127,121:4,129,121:3,157,121,-1:9,158,122,124,158:16,-1:15,158,12" +
"4,158:6,122,158:10,-1:9,157:6,121,157:4,121,157,135,157:5,-1:15,121:10,135," +
"121:6,157,121,-1:9,158,126,158,128,158:15,-1:15,158:8,126,158:4,128,158:5,-" +
"1:9,157:6,121,157:2,137,157,121,157:7,-1:15,121:6,137,121:10,157,121,-1:9,1" +
"58:13,134,158:5,-1:15,158:10,134,158:8,-1:9,157:6,121,157:2,139,141,121,157" +
":7,-1:15,121:6,139,121:5,141,121:4,157,121,-1:9,158:9,136,158:9,-1:15,158:6" +
",136,158:12,-1:5");

	public java_cup.runtime.Symbol next_token ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

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
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{
  return new Symbol(TokenConstants.EQ);
}
					case -3:
						break;
					case 3:
						{
  return new Symbol(TokenConstants.ERROR, yytext());
}
					case -4:
						break;
					case 4:
						{
  curr_lineno+=1;
}
					case -5:
						break;
					case 5:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -6:
						break;
					case 6:
						{
  AbstractSymbol typeIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.TYPEID, typeIdEntry);
}
					case -7:
						break;
					case 7:
						{
  return new Symbol(TokenConstants.LPAREN);
}
					case -8:
						break;
					case 8:
						{
  return new Symbol(TokenConstants.RPAREN);
}
					case -9:
						break;
					case 9:
						{
  return new Symbol(TokenConstants.PLUS);
}
					case -10:
						break;
					case 10:
						{
  return new Symbol(TokenConstants.AT);
}
					case -11:
						break;
					case 11:
						{
  return new Symbol(TokenConstants.MINUS);
}
					case -12:
						break;
					case 12:
						{
  return new Symbol(TokenConstants.MULT);
}
					case -13:
						break;
					case 13:
						{
  return new Symbol(TokenConstants.DIV);
}
					case -14:
						break;
					case 14:
						{
  return new Symbol(TokenConstants.NEG);
}
					case -15:
						break;
					case 15:
						{
  return new Symbol(TokenConstants.LT);
}
					case -16:
						break;
					case 16:
						{
  return new Symbol(TokenConstants.LBRACE);
}
					case -17:
						break;
					case 17:
						{
  return new Symbol(TokenConstants.RBRACE);
}
					case -18:
						break;
					case 18:
						{
  return new Symbol(TokenConstants.SEMI);
}
					case -19:
						break;
					case 19:
						{
  return new Symbol(TokenConstants.COLON);
}
					case -20:
						break;
					case 20:
						{
  return new Symbol(TokenConstants.DOT);
}
					case -21:
						break;
					case 21:
						{
  return new Symbol(TokenConstants.COMMA);
}
					case -22:
						break;
					case 22:
						{
  AbstractSymbol intEntry = AbstractTable.inttable.addString(yytext());
  return new Symbol(TokenConstants.INT_CONST, intEntry);
}
					case -23:
						break;
					case 23:
						{
  yybegin(STRING);
  // Start of a new String
  string_buf.setLength(0);
}
					case -24:
						break;
					case 24:
						{}
					case -25:
						break;
					case 25:
						{
  /* Sample lexical rule for "=>" arrow.
     Further lexical rules should be defined
     here, after the last %% separator */
  return new Symbol(TokenConstants.DARROW);
}
					case -26:
						break;
					case 26:
						{
  return new Symbol(TokenConstants.FI);
}
					case -27:
						break;
					case 27:
						{
  return new Symbol(TokenConstants.IF);
}
					case -28:
						break;
					case 28:
						{
  return new Symbol(TokenConstants.IN);
}
					case -29:
						break;
					case 29:
						{
  return new Symbol(TokenConstants.OF);
}
					case -30:
						break;
					case 30:
						{
  yybegin(MULTI_LINE_COMMENT);
}
					case -31:
						break;
					case 31:
						{
  yybegin(SINGLE_LINE_COMMENT);
}
					case -32:
						break;
					case 32:
						{
  // Returns the error for Unmatched *)
  return new Symbol(TokenConstants.ERROR, "Unmatched *)");
}
					case -33:
						break;
					case 33:
						{
  return new Symbol(TokenConstants.LE);
}
					case -34:
						break;
					case 34:
						{
  return new Symbol(TokenConstants.ASSIGN);
}
					case -35:
						break;
					case 35:
						{
  return new Symbol(TokenConstants.LET);
}
					case -36:
						break;
					case 36:
						{
  return new Symbol(TokenConstants.NEW);
}
					case -37:
						break;
					case 37:
						{
  return new Symbol(TokenConstants.NOT);
}
					case -38:
						break;
					case 38:
						{
  return new Symbol(TokenConstants.CASE);
}
					case -39:
						break;
					case 39:
						{
  return new Symbol(TokenConstants.LOOP);
}
					case -40:
						break;
					case 40:
						{
  return new Symbol(TokenConstants.ELSE);
}
					case -41:
						break;
					case 41:
						{
  return new Symbol(TokenConstants.ESAC);
}
					case -42:
						break;
					case 42:
						{
  return new Symbol(TokenConstants.THEN);
}
					case -43:
						break;
					case 43:
						{
  return new Symbol(TokenConstants.POOL);
}
					case -44:
						break;
					case 44:
						{
  return new Symbol(TokenConstants.BOOL_CONST, java.lang.Boolean.TRUE);
}
					case -45:
						break;
					case 45:
						{
  //[C][L][A][S][S]
  return new Symbol(TokenConstants.CLASS);
}
					case -46:
						break;
					case 46:
						{
  return new Symbol(TokenConstants.BOOL_CONST, java.lang.Boolean.FALSE);
}
					case -47:
						break;
					case 47:
						{
  return new Symbol(TokenConstants.WHILE);
}
					case -48:
						break;
					case 48:
						{
  return new Symbol(TokenConstants.ISVOID);
}
					case -49:
						break;
					case 49:
						{
  return new Symbol(TokenConstants.INHERITS);
}
					case -50:
						break;
					case 50:
						{
  string_buf.setLength(string_buf.length() + 1);
  string_buf.insert(string_buf.length()-1, yytext());
}
					case -51:
						break;
					case 51:
						{
  curr_lineno+=1;
  return new Symbol(TokenConstants.ERROR, "Unterminated string constant");
}
					case -52:
						break;
					case 52:
						{
  yybegin(YYINITIAL);
  AbstractSymbol strEntry = AbstractTable.stringtable.addString(string_buf.toString());
  return new Symbol(TokenConstants.STR_CONST, strEntry);
}
					case -53:
						break;
					case 53:
						{
  return new Symbol(TokenConstants.ERROR, "String contains null character");
}
					case -54:
						break;
					case 54:
						{}
					case -55:
						break;
					case 55:
						{
  // Return back to the initial state
  curr_lineno+=1;
  yybegin(YYINITIAL);
}
					case -56:
						break;
					case 56:
						{}
					case -57:
						break;
					case 57:
						{
  curr_lineno+=1;
}
					case -58:
						break;
					case 58:
						{
  yybegin(YYINITIAL);
}
					case -59:
						break;
					case 60:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -60:
						break;
					case 61:
						{
  AbstractSymbol typeIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.TYPEID, typeIdEntry);
}
					case -61:
						break;
					case 62:
						{
  return new Symbol(TokenConstants.FI);
}
					case -62:
						break;
					case 63:
						{
  return new Symbol(TokenConstants.IF);
}
					case -63:
						break;
					case 64:
						{
  return new Symbol(TokenConstants.IN);
}
					case -64:
						break;
					case 65:
						{
  return new Symbol(TokenConstants.OF);
}
					case -65:
						break;
					case 66:
						{
  return new Symbol(TokenConstants.LET);
}
					case -66:
						break;
					case 67:
						{
  return new Symbol(TokenConstants.NEW);
}
					case -67:
						break;
					case 68:
						{
  return new Symbol(TokenConstants.NOT);
}
					case -68:
						break;
					case 69:
						{
  return new Symbol(TokenConstants.CASE);
}
					case -69:
						break;
					case 70:
						{
  return new Symbol(TokenConstants.LOOP);
}
					case -70:
						break;
					case 71:
						{
  return new Symbol(TokenConstants.ELSE);
}
					case -71:
						break;
					case 72:
						{
  return new Symbol(TokenConstants.ESAC);
}
					case -72:
						break;
					case 73:
						{
  return new Symbol(TokenConstants.THEN);
}
					case -73:
						break;
					case 74:
						{
  return new Symbol(TokenConstants.POOL);
}
					case -74:
						break;
					case 75:
						{
  //[C][L][A][S][S]
  return new Symbol(TokenConstants.CLASS);
}
					case -75:
						break;
					case 76:
						{
  return new Symbol(TokenConstants.WHILE);
}
					case -76:
						break;
					case 77:
						{
  return new Symbol(TokenConstants.ISVOID);
}
					case -77:
						break;
					case 78:
						{
  return new Symbol(TokenConstants.INHERITS);
}
					case -78:
						break;
					case 79:
						{}
					case -79:
						break;
					case 81:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -80:
						break;
					case 82:
						{
  AbstractSymbol typeIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.TYPEID, typeIdEntry);
}
					case -81:
						break;
					case 84:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -82:
						break;
					case 85:
						{
  AbstractSymbol typeIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.TYPEID, typeIdEntry);
}
					case -83:
						break;
					case 86:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -84:
						break;
					case 87:
						{
  AbstractSymbol typeIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.TYPEID, typeIdEntry);
}
					case -85:
						break;
					case 88:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -86:
						break;
					case 89:
						{
  AbstractSymbol typeIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.TYPEID, typeIdEntry);
}
					case -87:
						break;
					case 90:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -88:
						break;
					case 91:
						{
  AbstractSymbol typeIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.TYPEID, typeIdEntry);
}
					case -89:
						break;
					case 92:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -90:
						break;
					case 93:
						{
  AbstractSymbol typeIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.TYPEID, typeIdEntry);
}
					case -91:
						break;
					case 94:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -92:
						break;
					case 95:
						{
  AbstractSymbol typeIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.TYPEID, typeIdEntry);
}
					case -93:
						break;
					case 96:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -94:
						break;
					case 97:
						{
  AbstractSymbol typeIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.TYPEID, typeIdEntry);
}
					case -95:
						break;
					case 98:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -96:
						break;
					case 99:
						{
  AbstractSymbol typeIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.TYPEID, typeIdEntry);
}
					case -97:
						break;
					case 100:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -98:
						break;
					case 101:
						{
  AbstractSymbol typeIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.TYPEID, typeIdEntry);
}
					case -99:
						break;
					case 102:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -100:
						break;
					case 103:
						{
  AbstractSymbol typeIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.TYPEID, typeIdEntry);
}
					case -101:
						break;
					case 104:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -102:
						break;
					case 105:
						{
  AbstractSymbol typeIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.TYPEID, typeIdEntry);
}
					case -103:
						break;
					case 106:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -104:
						break;
					case 107:
						{
  AbstractSymbol typeIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.TYPEID, typeIdEntry);
}
					case -105:
						break;
					case 108:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -106:
						break;
					case 109:
						{
  AbstractSymbol typeIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.TYPEID, typeIdEntry);
}
					case -107:
						break;
					case 110:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -108:
						break;
					case 111:
						{
  AbstractSymbol typeIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.TYPEID, typeIdEntry);
}
					case -109:
						break;
					case 112:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -110:
						break;
					case 113:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -111:
						break;
					case 114:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -112:
						break;
					case 115:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -113:
						break;
					case 116:
						{
  AbstractSymbol typeIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.TYPEID, typeIdEntry);
}
					case -114:
						break;
					case 117:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -115:
						break;
					case 118:
						{
  AbstractSymbol typeIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.TYPEID, typeIdEntry);
}
					case -116:
						break;
					case 119:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -117:
						break;
					case 120:
						{
  AbstractSymbol typeIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.TYPEID, typeIdEntry);
}
					case -118:
						break;
					case 121:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -119:
						break;
					case 122:
						{
  AbstractSymbol typeIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.TYPEID, typeIdEntry);
}
					case -120:
						break;
					case 123:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -121:
						break;
					case 124:
						{
  AbstractSymbol typeIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.TYPEID, typeIdEntry);
}
					case -122:
						break;
					case 125:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -123:
						break;
					case 126:
						{
  AbstractSymbol typeIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.TYPEID, typeIdEntry);
}
					case -124:
						break;
					case 127:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -125:
						break;
					case 128:
						{
  AbstractSymbol typeIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.TYPEID, typeIdEntry);
}
					case -126:
						break;
					case 129:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -127:
						break;
					case 130:
						{
  AbstractSymbol typeIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.TYPEID, typeIdEntry);
}
					case -128:
						break;
					case 131:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -129:
						break;
					case 132:
						{
  AbstractSymbol typeIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.TYPEID, typeIdEntry);
}
					case -130:
						break;
					case 133:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -131:
						break;
					case 134:
						{
  AbstractSymbol typeIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.TYPEID, typeIdEntry);
}
					case -132:
						break;
					case 135:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -133:
						break;
					case 136:
						{
  AbstractSymbol typeIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.TYPEID, typeIdEntry);
}
					case -134:
						break;
					case 137:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -135:
						break;
					case 138:
						{
  AbstractSymbol typeIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.TYPEID, typeIdEntry);
}
					case -136:
						break;
					case 139:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -137:
						break;
					case 140:
						{
  AbstractSymbol typeIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.TYPEID, typeIdEntry);
}
					case -138:
						break;
					case 141:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -139:
						break;
					case 142:
						{
  AbstractSymbol typeIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.TYPEID, typeIdEntry);
}
					case -140:
						break;
					case 143:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -141:
						break;
					case 144:
						{
  AbstractSymbol typeIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.TYPEID, typeIdEntry);
}
					case -142:
						break;
					case 145:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -143:
						break;
					case 146:
						{
  AbstractSymbol typeIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.TYPEID, typeIdEntry);
}
					case -144:
						break;
					case 147:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -145:
						break;
					case 148:
						{
  AbstractSymbol typeIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.TYPEID, typeIdEntry);
}
					case -146:
						break;
					case 149:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -147:
						break;
					case 150:
						{
  AbstractSymbol typeIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.TYPEID, typeIdEntry);
}
					case -148:
						break;
					case 151:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -149:
						break;
					case 152:
						{
  AbstractSymbol typeIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.TYPEID, typeIdEntry);
}
					case -150:
						break;
					case 153:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -151:
						break;
					case 154:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -152:
						break;
					case 155:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -153:
						break;
					case 156:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -154:
						break;
					case 157:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -155:
						break;
					case 158:
						{
  AbstractSymbol typeIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.TYPEID, typeIdEntry);
}
					case -156:
						break;
					case 159:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -157:
						break;
					case 160:
						{
  AbstractSymbol typeIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.TYPEID, typeIdEntry);
}
					case -158:
						break;
					case 161:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -159:
						break;
					case 162:
						{
  AbstractSymbol typeIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.TYPEID, typeIdEntry);
}
					case -160:
						break;
					case 163:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -161:
						break;
					case 164:
						{
  AbstractSymbol typeIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.TYPEID, typeIdEntry);
}
					case -162:
						break;
					case 165:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -163:
						break;
					case 166:
						{
  AbstractSymbol typeIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.TYPEID, typeIdEntry);
}
					case -164:
						break;
					case 167:
						{
  AbstractSymbol objIdEntry = AbstractTable.stringtable.addString(yytext());
  return new Symbol(TokenConstants.OBJECTID, objIdEntry);
}
					case -165:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
