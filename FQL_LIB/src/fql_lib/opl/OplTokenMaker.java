/* The following code was generated by JFlex 1.4.3 on 7/1/15 10:40 PM */

/*
 * Generated on 7/1/15 10:40 PM
 */
package fql_lib.opl;

import java.io.*;
import javax.swing.text.Segment;

import org.fife.ui.rsyntaxtextarea.*;


/**
 * 
 */

public class OplTokenMaker extends AbstractJFlexCTokenMaker {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int EOL_COMMENT = 4;
  public static final int YYINITIAL = 0;
  public static final int MLC = 2;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0,  0,  1,  1,  2, 2
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\17\1\7\1\0\1\17\1\15\22\0\1\17\1\24\1\14"+
    "\1\16\1\1\1\24\1\24\1\6\1\25\1\25\1\21\1\53\1\24"+
    "\1\54\1\23\1\20\1\4\3\4\4\4\2\3\1\36\1\24\1\15"+
    "\1\56\1\55\1\24\1\16\6\5\24\1\1\25\1\10\1\25\1\57"+
    "\1\2\1\0\1\41\1\13\1\44\1\40\1\35\1\32\1\47\1\26"+
    "\1\33\1\43\1\1\1\34\1\46\1\12\1\50\1\30\1\52\1\45"+
    "\1\31\1\27\1\11\1\42\1\37\1\1\1\51\1\1\1\22\1\57"+
    "\1\22\1\24\uff81\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\3\0\2\1\1\2\1\3\1\4\1\5\1\1\1\6"+
    "\1\7\10\1\1\6\1\10\1\11\5\10\1\12\3\10"+
    "\1\0\1\13\2\4\1\14\1\15\1\16\13\1\1\17"+
    "\11\0\1\4\1\20\1\4\13\1\11\0\1\4\5\1"+
    "\1\21\4\1\2\0\1\22\2\0\1\23\1\0\1\4"+
    "\2\1\1\24\5\1\5\0\1\4\12\1";

  private static int [] zzUnpackAction() {
    int [] result = new int[126];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\60\0\140\0\220\0\300\0\360\0\220\0\u0120"+
    "\0\u0150\0\u0180\0\220\0\220\0\u01b0\0\u01e0\0\u0210\0\u0240"+
    "\0\u0270\0\u02a0\0\u02d0\0\u0300\0\u0300\0\u0330\0\220\0\u0360"+
    "\0\u0390\0\u03c0\0\u03f0\0\u0420\0\220\0\u0450\0\u0480\0\u04b0"+
    "\0\u04e0\0\u0510\0\u0540\0\u0570\0\220\0\220\0\220\0\u05a0"+
    "\0\u05d0\0\u0600\0\u0630\0\u0660\0\u0690\0\u06c0\0\u06f0\0\u0720"+
    "\0\u0750\0\u0780\0\220\0\u07b0\0\u07e0\0\u0810\0\u0840\0\u0870"+
    "\0\u08a0\0\u08d0\0\u0900\0\u0930\0\u0960\0\220\0\u0990\0\u09c0"+
    "\0\u09f0\0\u0a20\0\u0a50\0\u0a80\0\u0ab0\0\u0ae0\0\u0b10\0\u0b40"+
    "\0\u0b70\0\u0ba0\0\u0bd0\0\u0c00\0\u0c30\0\u0c60\0\u0c90\0\u0cc0"+
    "\0\u0cf0\0\u0d20\0\u0d50\0\u0d80\0\u0db0\0\u0de0\0\u0e10\0\u0e40"+
    "\0\u0e70\0\300\0\u0ea0\0\u0ed0\0\u0f00\0\u0f30\0\u0f60\0\u0f90"+
    "\0\u0fc0\0\u0ff0\0\u1020\0\u1050\0\u1080\0\u10b0\0\u10e0\0\u1110"+
    "\0\300\0\u1140\0\u1170\0\u11a0\0\u11d0\0\u1200\0\u1230\0\u0fc0"+
    "\0\u1260\0\u1050\0\u1290\0\u12c0\0\u12f0\0\u1320\0\u1350\0\u1380"+
    "\0\u13b0\0\u13e0\0\u1410\0\u1440\0\u1470\0\u14a0";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[126];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\4\2\5\2\6\1\5\1\4\1\7\1\4\3\5"+
    "\1\10\2\4\1\11\1\12\1\13\1\14\2\4\1\14"+
    "\1\5\1\15\1\5\1\16\1\17\2\5\1\20\1\13"+
    "\1\5\1\21\2\5\1\22\2\5\1\23\4\5\1\13"+
    "\1\24\1\4\1\25\1\13\7\26\1\27\11\26\1\30"+
    "\4\26\1\31\3\26\1\32\4\26\1\33\20\26\7\34"+
    "\1\35\16\34\1\36\3\34\1\37\4\34\1\40\20\34"+
    "\61\0\5\5\2\0\1\41\3\5\12\0\10\5\1\0"+
    "\14\5\5\0\3\42\2\6\1\42\2\0\4\42\2\0"+
    "\1\42\7\0\10\42\1\0\14\42\5\0\7\10\1\43"+
    "\1\44\3\10\1\45\43\10\17\0\1\11\60\0\1\46"+
    "\1\47\37\0\5\5\2\0\1\41\3\5\12\0\1\50"+
    "\7\5\1\0\6\5\1\51\5\5\6\0\5\5\2\0"+
    "\1\41\3\5\12\0\10\5\1\0\11\5\1\52\1\53"+
    "\1\5\6\0\5\5\2\0\1\41\3\5\12\0\10\5"+
    "\1\0\11\5\1\54\2\5\6\0\5\5\2\0\1\41"+
    "\3\5\12\0\10\5\1\0\3\5\1\55\7\5\1\56"+
    "\6\0\5\5\2\0\1\41\3\5\12\0\7\5\1\57"+
    "\1\0\14\5\6\0\5\5\2\0\1\41\3\5\12\0"+
    "\10\5\1\0\2\5\1\60\11\5\6\0\5\5\2\0"+
    "\1\41\3\5\12\0\10\5\1\0\2\5\1\61\6\5"+
    "\1\62\2\5\62\0\1\13\2\0\7\26\1\0\11\26"+
    "\1\0\4\26\1\0\3\26\1\0\4\26\1\0\20\26"+
    "\20\0\1\63\66\0\1\64\57\0\1\65\3\0\1\66"+
    "\63\0\1\67\20\0\7\34\1\0\16\34\1\0\3\34"+
    "\1\0\4\34\1\0\20\34\27\0\1\70\57\0\1\71"+
    "\3\0\1\72\63\0\1\73\31\0\1\74\46\0\6\42"+
    "\2\0\4\42\2\0\1\42\7\0\10\42\1\0\14\42"+
    "\5\0\10\43\1\75\3\43\1\76\47\43\1\10\1\43"+
    "\1\10\1\0\1\10\1\77\3\10\12\43\1\10\2\43"+
    "\1\10\12\43\1\10\12\43\1\0\5\5\2\0\1\41"+
    "\3\5\12\0\7\5\1\100\1\0\14\5\6\0\5\5"+
    "\2\0\1\41\3\5\12\0\10\5\1\0\2\5\1\101"+
    "\11\5\6\0\5\5\2\0\1\41\3\5\12\0\10\5"+
    "\1\0\6\5\1\102\5\5\6\0\5\5\2\0\1\41"+
    "\3\5\12\0\10\5\1\0\7\5\1\103\4\5\6\0"+
    "\5\5\2\0\1\41\3\5\12\0\10\5\1\0\6\5"+
    "\1\104\5\5\6\0\5\5\2\0\1\41\3\5\12\0"+
    "\10\5\1\0\2\5\1\105\11\5\6\0\5\5\2\0"+
    "\1\41\1\106\2\5\12\0\10\5\1\0\14\5\6\0"+
    "\5\5\2\0\1\41\3\5\12\0\6\5\1\107\1\5"+
    "\1\0\14\5\6\0\5\5\2\0\1\41\3\5\12\0"+
    "\10\5\1\0\3\5\1\110\10\5\6\0\5\5\2\0"+
    "\1\41\3\5\12\0\2\5\1\111\5\5\1\0\14\5"+
    "\6\0\5\5\2\0\1\41\3\5\12\0\10\5\1\0"+
    "\1\5\1\112\12\5\34\0\1\113\60\0\1\114\63\0"+
    "\1\115\62\0\1\116\47\0\1\117\60\0\1\120\63\0"+
    "\1\121\62\0\1\122\23\0\3\123\5\0\1\123\16\0"+
    "\1\123\2\0\1\123\2\0\2\123\2\0\1\123\13\0"+
    "\7\43\1\0\53\43\3\124\2\43\1\75\2\43\1\124"+
    "\1\76\15\43\1\124\2\43\1\124\2\43\2\124\2\43"+
    "\1\124\13\43\1\0\5\5\2\0\1\41\3\5\12\0"+
    "\10\5\1\0\11\5\1\125\2\5\6\0\5\5\2\0"+
    "\1\41\1\5\1\126\1\5\12\0\10\5\1\0\14\5"+
    "\6\0\5\5\2\0\1\41\3\5\12\0\1\5\1\127"+
    "\6\5\1\0\14\5\6\0\5\5\2\0\1\41\2\5"+
    "\1\130\12\0\10\5\1\0\14\5\6\0\5\5\2\0"+
    "\1\41\3\5\12\0\10\5\1\0\2\5\1\131\11\5"+
    "\6\0\5\5\2\0\1\41\3\5\12\0\6\5\1\132"+
    "\1\5\1\0\14\5\6\0\5\5\2\0\1\41\3\5"+
    "\12\0\10\5\1\0\2\5\1\133\11\5\6\0\5\5"+
    "\2\0\1\41\3\5\12\0\1\5\1\134\6\5\1\0"+
    "\14\5\6\0\5\5\2\0\1\41\3\5\12\0\10\5"+
    "\1\0\2\5\1\135\11\5\6\0\5\5\2\0\1\41"+
    "\3\5\12\0\2\5\1\136\5\5\1\0\14\5\6\0"+
    "\5\5\2\0\1\41\3\5\12\0\7\5\1\105\1\0"+
    "\14\5\35\0\1\137\65\0\1\140\56\0\1\114\45\0"+
    "\1\141\64\0\1\142\65\0\1\143\56\0\1\120\45\0"+
    "\1\144\37\0\3\145\5\0\1\145\16\0\1\145\2\0"+
    "\1\145\2\0\2\145\2\0\1\145\13\0\3\43\3\146"+
    "\2\43\1\75\2\43\1\146\1\76\15\43\1\146\2\43"+
    "\1\146\2\43\2\146\2\43\1\146\13\43\1\0\5\5"+
    "\2\0\1\41\3\5\12\0\10\5\1\0\6\5\1\147"+
    "\5\5\6\0\5\5\2\0\1\41\3\5\12\0\3\5"+
    "\1\150\4\5\1\0\14\5\6\0\5\5\2\0\1\41"+
    "\3\5\12\0\3\5\1\151\4\5\1\0\14\5\6\0"+
    "\5\5\2\0\1\41\3\5\12\0\10\5\1\0\11\5"+
    "\1\152\2\5\6\0\5\5\2\0\1\41\3\5\12\0"+
    "\6\5\1\153\1\5\1\0\14\5\6\0\5\5\2\0"+
    "\1\41\3\5\12\0\1\5\1\154\6\5\1\0\14\5"+
    "\6\0\5\5\2\0\1\41\3\5\12\0\10\5\1\0"+
    "\2\5\1\132\11\5\6\0\5\5\2\0\1\41\3\5"+
    "\12\0\3\5\1\155\4\5\1\0\14\5\6\0\5\5"+
    "\2\0\1\41\3\5\12\0\5\5\1\156\2\5\1\0"+
    "\14\5\36\0\1\114\4\0\1\140\41\0\1\157\40\0"+
    "\1\141\1\160\3\141\1\160\2\0\3\141\2\0\1\160"+
    "\1\0\1\141\1\160\1\0\3\160\10\141\1\160\14\141"+
    "\2\160\1\0\1\160\32\0\1\120\4\0\1\143\41\0"+
    "\1\161\40\0\1\144\1\162\3\144\1\162\2\0\3\144"+
    "\2\0\1\162\1\0\1\144\1\162\1\0\3\162\10\144"+
    "\1\162\14\144\2\162\1\0\1\162\4\0\3\163\5\0"+
    "\1\163\16\0\1\163\2\0\1\163\2\0\2\163\2\0"+
    "\1\163\13\0\3\43\3\164\2\43\1\75\2\43\1\164"+
    "\1\76\15\43\1\164\2\43\1\164\2\43\2\164\2\43"+
    "\1\164\13\43\1\0\5\5\2\0\1\41\3\5\12\0"+
    "\10\5\1\0\12\5\1\132\1\5\6\0\5\5\2\0"+
    "\1\41\3\5\12\0\4\5\1\165\3\5\1\0\14\5"+
    "\6\0\5\5\2\0\1\41\3\5\12\0\6\5\1\127"+
    "\1\5\1\0\14\5\6\0\5\5\2\0\1\41\3\5"+
    "\12\0\6\5\1\151\1\5\1\0\14\5\6\0\5\5"+
    "\2\0\1\41\3\5\12\0\5\5\1\166\2\5\1\0"+
    "\14\5\6\0\5\5\2\0\1\41\3\5\12\0\10\5"+
    "\1\0\5\5\1\167\6\5\6\0\5\5\2\0\1\41"+
    "\1\5\1\170\1\5\12\0\10\5\1\0\14\5\25\0"+
    "\1\141\57\0\1\144\42\0\3\5\5\0\1\5\16\0"+
    "\1\5\2\0\1\5\2\0\2\5\2\0\1\5\13\0"+
    "\3\43\3\10\2\43\1\75\2\43\1\10\1\76\15\43"+
    "\1\10\2\43\1\10\2\43\2\10\2\43\1\10\13\43"+
    "\1\0\5\5\2\0\1\41\3\5\12\0\10\5\1\0"+
    "\11\5\1\171\2\5\6\0\5\5\2\0\1\41\3\5"+
    "\12\0\10\5\1\0\11\5\1\172\2\5\6\0\5\5"+
    "\2\0\1\41\3\5\12\0\10\5\1\0\6\5\1\173"+
    "\5\5\6\0\5\5\2\0\1\41\3\5\12\0\10\5"+
    "\1\0\10\5\1\132\3\5\6\0\5\5\2\0\1\41"+
    "\3\5\12\0\10\5\1\0\6\5\1\174\5\5\6\0"+
    "\5\5\2\0\1\41\1\5\1\127\1\5\12\0\10\5"+
    "\1\0\14\5\6\0\5\5\2\0\1\41\3\5\12\0"+
    "\5\5\1\175\2\5\1\0\14\5\6\0\5\5\2\0"+
    "\1\41\3\5\12\0\10\5\1\0\7\5\1\132\4\5"+
    "\6\0\5\5\2\0\1\41\3\5\12\0\2\5\1\176"+
    "\5\5\1\0\14\5\6\0\5\5\2\0\1\41\3\5"+
    "\12\0\1\5\1\132\6\5\1\0\14\5\5\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[5328];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\3\0\1\11\2\1\1\11\3\1\2\11\12\1\1\11"+
    "\5\1\1\11\3\1\1\0\3\1\3\11\13\1\1\11"+
    "\11\0\1\1\1\11\14\1\11\0\13\1\2\0\1\1"+
    "\2\0\1\1\1\0\11\1\5\0\13\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[126];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the textposition at the last state to be included in yytext */
  private int zzPushbackPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /* user code: */


	/**
	 * Constructor.  This must be here because JFlex does not generate a
	 * no-parameter constructor.
	 */
	public OplTokenMaker() {
	}


	/**
	 * Adds the token specified to the current linked list of tokens.
	 *
	 * @param tokenType The token's type.
	 * @see #addToken(int, int, int)
	 */
	private void addHyperlinkToken(int start, int end, int tokenType) {
		int so = start + offsetShift;
		addToken(zzBuffer, start,end, tokenType, so, true);
	}


	/**
	 * Adds the token specified to the current linked list of tokens.
	 *
	 * @param tokenType The token's type.
	 */
	private void addToken(int tokenType) {
		addToken(zzStartRead, zzMarkedPos-1, tokenType);
	}


	/**
	 * Adds the token specified to the current linked list of tokens.
	 *
	 * @param tokenType The token's type.
	 * @see #addHyperlinkToken(int, int, int)
	 */
	private void addToken(int start, int end, int tokenType) {
		int so = start + offsetShift;
		addToken(zzBuffer, start,end, tokenType, so, false);
	}


	/**
	 * Adds the token specified to the current linked list of tokens.
	 *
	 * @param array The character array.
	 * @param start The starting offset in the array.
	 * @param end The ending offset in the array.
	 * @param tokenType The token's type.
	 * @param startOffset The offset in the document at which this token
	 *        occurs.
	 * @param hyperlink Whether this token is a hyperlink.
	 */
	public void addToken(char[] array, int start, int end, int tokenType,
						int startOffset, boolean hyperlink) {
		super.addToken(array, start,end, tokenType, startOffset, hyperlink);
		zzStartRead = zzMarkedPos;
	}


	/**
	 * Returns the text to place at the beginning and end of a
	 * line to "comment" it in a this programming language.
	 *
	 * @return The start and end strings to add to a line to "comment"
	 *         it out.
	 */
	public String[] getLineCommentStartAndEnd() {
		return new String[] { "//", null };
	}


	/**
	 * Returns the first token in the linked list of tokens generated
	 * from <code>text</code>.  This method must be implemented by
	 * subclasses so they can correctly implement syntax highlighting.
	 *
	 * @param text The text from which to get tokens.
	 * @param initialTokenType The token type we should start with.
	 * @param startOffset The offset into the document at which
	 *        <code>text</code> starts.
	 * @return The first <code>Token</code> in a linked list representing
	 *         the syntax highlighted text.
	 */
	public Token getTokenList(Segment text, int initialTokenType, int startOffset) {

		resetTokenList();
		this.offsetShift = -text.offset + startOffset;

		// Start off in the proper state.
		int state = Token.NULL;
		switch (initialTokenType) {
						case Token.COMMENT_MULTILINE:
				state = MLC;
				start = text.offset;
				break;

			/* No documentation comments */
			default:
				state = Token.NULL;
		}

		s = text;
		try {
			yyreset(zzReader);
			yybegin(state);
			return yylex();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		}

	}


	/**
	 * Refills the input buffer.
	 *
	 * @return      <code>true</code> if EOF was reached, otherwise
	 *              <code>false</code>.
	 */
	private boolean zzRefill() {
		return zzCurrentPos>=s.offset+s.count;
	}


	/**
	 * Resets the scanner to read from a new input stream.
	 * Does not close the old reader.
	 *
	 * All internal variables are reset, the old input stream 
	 * <b>cannot</b> be reused (internal buffer is discarded and lost).
	 * Lexical state is set to <tt>YY_INITIAL</tt>.
	 *
	 * @param reader   the new input stream 
	 */
	public final void yyreset(Reader reader) {
		// 's' has been updated.
		zzBuffer = s.array;
		/*
		 * We replaced the line below with the two below it because zzRefill
		 * no longer "refills" the buffer (since the way we do it, it's always
		 * "full" the first time through, since it points to the segment's
		 * array).  So, we assign zzEndRead here.
		 */
		//zzStartRead = zzEndRead = s.offset;
		zzStartRead = s.offset;
		zzEndRead = zzStartRead + s.count - 1;
		zzCurrentPos = zzMarkedPos = zzPushbackPos = s.offset;
		zzLexicalState = YYINITIAL;
		zzReader = reader;
		zzAtBOL  = true;
		zzAtEOF  = false;
	}




  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public OplTokenMaker(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  public OplTokenMaker(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 146) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public org.fife.ui.rsyntaxtextarea.Token yylex() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL[zzCurrentPosL++];
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL[zzCurrentPosL++];
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 3: 
          { addNullToken(); return firstToken;
          }
        case 21: break;
        case 14: 
          { start = zzMarkedPos-2; yybegin(MLC);
          }
        case 22: break;
        case 5: 
          { addToken(Token.WHITESPACE);
          }
        case 23: break;
        case 16: 
          { addToken(Token.ERROR_STRING_DOUBLE);
          }
        case 24: break;
        case 17: 
          { addToken(Token.RESERVED_WORD);
          }
        case 25: break;
        case 7: 
          { addToken(Token.SEPARATOR);
          }
        case 26: break;
        case 1: 
          { addToken(Token.IDENTIFIER);
          }
        case 27: break;
        case 10: 
          { addToken(start,zzStartRead-1, Token.COMMENT_EOL); addNullToken(); return firstToken;
          }
        case 28: break;
        case 13: 
          { start = zzMarkedPos-2; yybegin(EOL_COMMENT);
          }
        case 29: break;
        case 4: 
          { addToken(Token.ERROR_STRING_DOUBLE); addNullToken(); return firstToken;
          }
        case 30: break;
        case 15: 
          { yybegin(YYINITIAL); addToken(start,zzStartRead+2-1, Token.COMMENT_MULTILINE);
          }
        case 31: break;
        case 12: 
          { addToken(Token.LITERAL_STRING_DOUBLE_QUOTE);
          }
        case 32: break;
        case 19: 
          { int temp=zzStartRead; addToken(start,zzStartRead-1, Token.COMMENT_EOL); addHyperlinkToken(temp,zzMarkedPos-1, Token.COMMENT_EOL); start = zzMarkedPos;
          }
        case 33: break;
        case 18: 
          { int temp=zzStartRead; addToken(start,zzStartRead-1, Token.COMMENT_MULTILINE); addHyperlinkToken(temp,zzMarkedPos-1, Token.COMMENT_MULTILINE); start = zzMarkedPos;
          }
        case 34: break;
        case 20: 
          { addToken(Token.RESERVED_WORD_2);
          }
        case 35: break;
        case 11: 
          { addToken(Token.ERROR_NUMBER_FORMAT);
          }
        case 36: break;
        case 2: 
          { addToken(Token.LITERAL_NUMBER_DECIMAL_INT);
          }
        case 37: break;
        case 6: 
          { addToken(Token.OPERATOR);
          }
        case 38: break;
        case 8: 
          { 
          }
        case 39: break;
        case 9: 
          { addToken(start,zzStartRead-1, Token.COMMENT_MULTILINE); return firstToken;
          }
        case 40: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            switch (zzLexicalState) {
            case EOL_COMMENT: {
              addToken(start,zzStartRead-1, Token.COMMENT_EOL); addNullToken(); return firstToken;
            }
            case 127: break;
            case YYINITIAL: {
              addNullToken(); return firstToken;
            }
            case 128: break;
            case MLC: {
              addToken(start,zzStartRead-1, Token.COMMENT_MULTILINE); return firstToken;
            }
            case 129: break;
            default:
            return null;
            }
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
