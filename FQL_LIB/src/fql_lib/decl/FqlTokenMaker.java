/* The following code was generated by JFlex 1.4.3 on 5/30/14 10:52 PM */

/*
 * Generated on 5/30/14 10:52 PM
 */
package fql_lib.decl;

import java.io.*;

import javax.swing.text.Segment;

import org.fife.ui.rsyntaxtextarea.*;


/**
 * 
 */
@SuppressWarnings("unused")
public class FqlTokenMaker extends AbstractJFlexCTokenMaker {

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
    "\1\16\1\1\1\24\1\24\1\6\1\25\1\25\1\21\1\61\1\24"+
    "\1\62\1\23\1\20\1\4\1\56\1\56\1\4\4\4\2\3\1\36"+
    "\1\24\1\15\1\64\1\63\1\24\1\16\2\5\1\47\3\5\14\1"+
    "\1\50\7\1\1\25\1\10\1\25\1\65\1\2\1\0\1\41\1\13"+
    "\1\40\1\51\1\35\1\32\1\42\1\26\1\33\1\57\1\52\1\34"+
    "\1\46\1\12\1\43\1\30\1\53\1\44\1\31\1\27\1\11\1\54"+
    "\1\37\1\55\1\45\1\60\1\22\1\65\1\22\1\24\uff81\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\3\0\2\1\1\2\1\3\2\1\1\4\1\5\1\1"+
    "\1\6\1\7\20\1\1\6\1\10\1\11\5\10\1\12"+
    "\3\10\1\0\1\13\2\1\2\4\1\14\1\15\1\16"+
    "\1\17\11\1\1\17\23\1\1\20\11\0\1\4\1\21"+
    "\1\4\5\1\1\22\26\1\11\0\1\4\20\1\2\0"+
    "\1\23\2\0\1\24\1\0\1\4\6\1\1\17\4\1"+
    "\5\0\1\4\21\1";

  private static int [] zzUnpackAction() {
    int [] result = new int[190];
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
    "\0\0\0\66\0\154\0\242\0\330\0\u010e\0\242\0\u0144"+
    "\0\u017a\0\u01b0\0\u01e6\0\u021c\0\242\0\242\0\u0252\0\u0288"+
    "\0\u02be\0\u02f4\0\u032a\0\u0360\0\u0396\0\u03cc\0\u0402\0\u0438"+
    "\0\u046e\0\u04a4\0\u04da\0\u0510\0\u0546\0\u057c\0\u057c\0\u05b2"+
    "\0\242\0\u05e8\0\u061e\0\u0654\0\u068a\0\u06c0\0\242\0\u06f6"+
    "\0\u072c\0\u0762\0\u0798\0\u07ce\0\u0804\0\u083a\0\u0870\0\u08a6"+
    "\0\242\0\242\0\242\0\330\0\u08dc\0\u0912\0\u0948\0\u097e"+
    "\0\u09b4\0\u09ea\0\u0a20\0\u0a56\0\u0a8c\0\u0ac2\0\u0af8\0\u0b2e"+
    "\0\u0b64\0\u0b9a\0\u0bd0\0\u0c06\0\u0c3c\0\u0c72\0\u0ca8\0\u0cde"+
    "\0\u0d14\0\u0d4a\0\u0d80\0\u0db6\0\u0dec\0\u0e22\0\u0e58\0\u0e8e"+
    "\0\u0ec4\0\242\0\u0efa\0\u0f30\0\u0f66\0\u0f9c\0\u0fd2\0\u1008"+
    "\0\u103e\0\u1074\0\u10aa\0\u10e0\0\242\0\u1116\0\u114c\0\u1182"+
    "\0\u11b8\0\u11ee\0\u1224\0\330\0\u125a\0\u1290\0\u12c6\0\u12fc"+
    "\0\u1332\0\u1368\0\u139e\0\u13d4\0\u140a\0\u1440\0\u1476\0\u14ac"+
    "\0\u14e2\0\u1518\0\u154e\0\u1584\0\u15ba\0\u15f0\0\u1626\0\u165c"+
    "\0\u1692\0\u16c8\0\u16fe\0\u1734\0\u176a\0\u17a0\0\u17d6\0\u180c"+
    "\0\u1842\0\u1878\0\u18ae\0\u18e4\0\u191a\0\u1950\0\u1986\0\u19bc"+
    "\0\u19f2\0\u1a28\0\u1a5e\0\u1a94\0\u1aca\0\u1b00\0\u1b36\0\u1b6c"+
    "\0\u1ba2\0\u1bd8\0\u1c0e\0\u1c44\0\u1c7a\0\u1cb0\0\u1ce6\0\u1d1c"+
    "\0\u1d52\0\u1d88\0\u1dbe\0\u1df4\0\u1e2a\0\u1e60\0\u1e96\0\u1ecc"+
    "\0\u1f02\0\u1f38\0\u1f02\0\u1f6e\0\u1fa4\0\u1fda\0\u2010\0\u2046"+
    "\0\u1ce6\0\u207c\0\u1d88\0\u20b2\0\u20e8\0\u211e\0\u2154\0\u218a"+
    "\0\u21c0\0\u21f6\0\u222c\0\u2262\0\u2298\0\u22ce\0\u2304\0\u233a"+
    "\0\u2370\0\u23a6\0\u23dc\0\u2412\0\u2448\0\u247e";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[190];
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
    "\1\4\2\5\2\6\1\5\1\4\1\7\1\4\1\10"+
    "\1\11\1\5\1\12\2\4\1\13\1\14\1\15\1\16"+
    "\2\4\1\16\1\5\1\17\1\20\1\21\1\22\1\23"+
    "\1\5\1\24\1\15\1\5\1\25\1\26\1\5\1\27"+
    "\1\30\2\5\1\31\1\32\1\33\1\34\1\5\1\35"+
    "\1\5\1\6\2\5\1\15\1\36\1\4\1\37\1\15"+
    "\7\40\1\41\11\40\1\42\4\40\1\43\3\40\1\44"+
    "\4\40\1\45\26\40\7\46\1\47\16\46\1\50\3\46"+
    "\1\51\4\46\1\52\26\46\67\0\5\5\2\0\1\53"+
    "\3\5\12\0\10\5\1\0\22\5\5\0\3\54\2\6"+
    "\1\54\2\0\4\54\2\0\1\54\7\0\10\54\1\0"+
    "\17\54\1\6\2\54\6\0\5\5\2\0\1\53\1\5"+
    "\1\55\1\5\12\0\10\5\1\0\22\5\6\0\5\5"+
    "\2\0\1\53\3\5\12\0\10\5\1\0\4\5\1\56"+
    "\15\5\5\0\7\12\1\57\1\60\3\12\1\61\51\12"+
    "\17\0\1\13\66\0\1\62\1\63\45\0\5\5\2\0"+
    "\1\53\3\5\12\0\1\5\1\64\6\5\1\0\5\5"+
    "\1\65\14\5\6\0\5\5\2\0\1\53\3\5\12\0"+
    "\5\5\1\64\2\5\1\0\2\5\1\66\2\5\1\67"+
    "\14\5\6\0\5\5\2\0\1\53\1\5\1\70\1\5"+
    "\12\0\5\5\1\71\1\5\1\72\1\0\22\5\6\0"+
    "\5\5\2\0\1\53\1\73\2\5\12\0\3\5\1\56"+
    "\1\64\3\5\1\0\2\5\1\74\1\5\1\75\15\5"+
    "\6\0\5\5\2\0\1\53\1\5\1\76\1\5\12\0"+
    "\3\5\1\77\4\5\1\0\7\5\1\100\2\5\1\64"+
    "\7\5\6\0\5\5\2\0\1\53\3\5\12\0\10\5"+
    "\1\0\14\5\1\101\1\102\1\103\3\5\6\0\5\5"+
    "\2\0\1\53\1\104\2\5\12\0\1\105\7\5\1\0"+
    "\2\5\1\106\1\5\1\107\15\5\6\0\5\5\2\0"+
    "\1\53\1\5\1\70\1\5\12\0\2\5\1\110\5\5"+
    "\1\0\5\5\1\111\14\5\6\0\5\5\2\0\1\53"+
    "\1\5\1\64\1\112\12\0\10\5\1\0\5\5\1\64"+
    "\14\5\6\0\5\5\2\0\1\53\3\5\12\0\7\5"+
    "\1\113\1\0\2\5\1\114\17\5\6\0\5\5\2\0"+
    "\1\53\3\5\12\0\10\5\1\0\2\5\1\56\17\5"+
    "\6\0\5\5\2\0\1\53\3\5\12\0\7\5\1\56"+
    "\1\0\22\5\6\0\5\5\2\0\1\53\3\5\12\0"+
    "\7\5\1\115\1\0\4\5\1\116\15\5\6\0\5\5"+
    "\2\0\1\53\3\5\12\0\6\5\1\117\1\120\1\0"+
    "\22\5\6\0\5\5\2\0\1\53\3\5\12\0\10\5"+
    "\1\0\4\5\1\121\15\5\70\0\1\15\2\0\7\40"+
    "\1\0\11\40\1\0\4\40\1\0\3\40\1\0\4\40"+
    "\1\0\26\40\20\0\1\122\74\0\1\123\65\0\1\124"+
    "\3\0\1\125\71\0\1\126\26\0\7\46\1\0\16\46"+
    "\1\0\3\46\1\0\4\46\1\0\26\46\27\0\1\127"+
    "\65\0\1\130\3\0\1\131\71\0\1\132\37\0\1\133"+
    "\54\0\6\54\2\0\4\54\2\0\1\54\7\0\10\54"+
    "\1\0\22\54\6\0\5\5\2\0\1\53\3\5\12\0"+
    "\5\5\1\56\2\5\1\0\22\5\6\0\5\5\2\0"+
    "\1\53\3\5\12\0\1\5\1\64\6\5\1\0\22\5"+
    "\5\0\10\57\1\134\3\57\1\135\55\57\1\12\1\57"+
    "\1\12\1\0\1\12\1\136\3\12\12\57\1\12\2\57"+
    "\1\12\11\57\1\12\11\57\1\12\7\57\1\0\5\5"+
    "\2\0\1\53\1\137\2\5\12\0\10\5\1\0\2\5"+
    "\1\140\17\5\6\0\5\5\2\0\1\53\3\5\12\0"+
    "\1\5\1\141\6\5\1\0\22\5\6\0\5\5\2\0"+
    "\1\53\3\5\12\0\10\5\1\0\4\5\1\142\15\5"+
    "\6\0\5\5\2\0\1\53\3\5\12\0\10\5\1\0"+
    "\12\5\1\64\7\5\6\0\5\5\2\0\1\53\3\5"+
    "\12\0\10\5\1\0\3\5\1\143\16\5\6\0\5\5"+
    "\2\0\1\53\3\5\12\0\1\5\1\144\6\5\1\0"+
    "\22\5\6\0\5\5\2\0\1\53\1\5\1\145\1\5"+
    "\12\0\10\5\1\0\22\5\6\0\5\5\2\0\1\53"+
    "\3\5\12\0\6\5\1\146\1\5\1\0\22\5\6\0"+
    "\5\5\2\0\1\53\3\5\12\0\10\5\1\0\5\5"+
    "\1\147\14\5\6\0\5\5\2\0\1\53\3\5\12\0"+
    "\6\5\1\64\1\5\1\0\5\5\1\64\14\5\6\0"+
    "\5\5\2\0\1\53\3\5\12\0\10\5\1\0\4\5"+
    "\1\150\15\5\6\0\5\5\2\0\1\53\3\5\12\0"+
    "\2\5\1\151\5\5\1\0\22\5\6\0\5\5\2\0"+
    "\1\53\1\152\2\5\12\0\10\5\1\0\22\5\6\0"+
    "\5\5\2\0\1\53\3\5\12\0\10\5\1\0\2\5"+
    "\1\153\17\5\6\0\5\5\2\0\1\53\3\5\12\0"+
    "\5\5\1\154\2\5\1\0\22\5\6\0\5\5\2\0"+
    "\1\53\3\5\12\0\10\5\1\0\5\5\1\155\14\5"+
    "\6\0\5\5\2\0\1\53\3\5\12\0\10\5\1\0"+
    "\2\5\1\156\17\5\6\0\5\5\2\0\1\53\3\5"+
    "\12\0\1\5\1\157\6\5\1\0\22\5\6\0\5\5"+
    "\2\0\1\53\3\5\12\0\10\5\1\0\5\5\1\160"+
    "\4\5\1\64\1\161\6\5\6\0\5\5\2\0\1\53"+
    "\3\5\12\0\2\5\1\162\5\5\1\0\22\5\6\0"+
    "\5\5\2\0\1\53\3\5\12\0\10\5\1\0\5\5"+
    "\1\163\14\5\6\0\5\5\2\0\1\53\3\5\12\0"+
    "\10\5\1\0\20\5\1\164\1\5\6\0\5\5\2\0"+
    "\1\53\3\5\12\0\1\5\1\165\4\5\1\166\1\5"+
    "\1\0\22\5\6\0\5\5\2\0\1\53\1\5\1\167"+
    "\1\5\12\0\10\5\1\0\22\5\6\0\5\5\2\0"+
    "\1\53\3\5\12\0\6\5\1\170\1\5\1\0\22\5"+
    "\6\0\5\5\2\0\1\53\3\5\12\0\10\5\1\0"+
    "\7\5\1\64\12\5\6\0\5\5\2\0\1\53\3\5"+
    "\12\0\7\5\1\171\1\0\22\5\6\0\5\5\2\0"+
    "\1\53\3\5\12\0\10\5\1\0\5\5\1\172\14\5"+
    "\6\0\5\5\2\0\1\53\3\5\12\0\5\5\1\70"+
    "\2\5\1\0\22\5\34\0\1\173\66\0\1\174\71\0"+
    "\1\175\70\0\1\176\55\0\1\177\66\0\1\200\71\0"+
    "\1\201\70\0\1\202\31\0\3\203\5\0\1\203\16\0"+
    "\1\203\2\0\1\203\2\0\2\203\5\0\1\203\1\0"+
    "\1\203\4\0\1\203\7\0\7\57\1\0\61\57\3\204"+
    "\2\57\1\134\2\57\1\204\1\135\15\57\1\204\2\57"+
    "\1\204\2\57\2\204\5\57\1\204\1\57\1\204\4\57"+
    "\1\204\7\57\1\0\5\5\2\0\1\53\3\5\12\0"+
    "\7\5\1\64\1\0\22\5\6\0\5\5\2\0\1\53"+
    "\1\5\1\205\1\5\12\0\10\5\1\0\22\5\6\0"+
    "\5\5\2\0\1\53\3\5\12\0\1\64\7\5\1\0"+
    "\22\5\6\0\5\5\2\0\1\53\3\5\12\0\2\5"+
    "\1\64\5\5\1\0\22\5\6\0\5\5\2\0\1\53"+
    "\3\5\12\0\10\5\1\0\7\5\1\206\12\5\6\0"+
    "\5\5\2\0\1\53\3\5\12\0\10\5\1\0\1\5"+
    "\1\207\20\5\6\0\5\5\2\0\1\53\3\5\12\0"+
    "\3\5\1\137\4\5\1\0\22\5\6\0\5\5\2\0"+
    "\1\53\3\5\12\0\10\5\1\0\2\5\1\210\17\5"+
    "\6\0\5\5\2\0\1\53\3\5\12\0\10\5\1\0"+
    "\17\5\1\64\2\5\6\0\5\5\2\0\1\53\3\5"+
    "\12\0\6\5\1\211\1\5\1\0\22\5\6\0\5\5"+
    "\2\0\1\53\3\5\12\0\10\5\1\0\2\5\1\212"+
    "\17\5\6\0\5\5\2\0\1\53\3\5\12\0\6\5"+
    "\1\64\1\5\1\0\22\5\6\0\5\5\2\0\1\53"+
    "\3\5\12\0\3\5\1\213\4\5\1\0\22\5\6\0"+
    "\5\5\2\0\1\53\3\5\12\0\10\5\1\0\5\5"+
    "\1\214\14\5\6\0\5\5\2\0\1\53\3\5\12\0"+
    "\10\5\1\0\5\5\1\64\14\5\6\0\5\5\2\0"+
    "\1\53\3\5\12\0\7\5\1\215\1\0\22\5\6\0"+
    "\5\5\2\0\1\53\3\5\12\0\7\5\1\216\1\0"+
    "\22\5\6\0\5\5\2\0\1\53\3\5\12\0\6\5"+
    "\1\117\1\5\1\0\22\5\6\0\5\5\2\0\1\53"+
    "\3\5\12\0\6\5\1\214\1\5\1\0\22\5\6\0"+
    "\5\5\2\0\1\53\3\5\12\0\10\5\1\0\4\5"+
    "\1\217\15\5\6\0\5\5\2\0\1\53\3\5\12\0"+
    "\7\5\1\220\1\0\22\5\6\0\5\5\2\0\1\53"+
    "\1\221\2\5\12\0\10\5\1\0\22\5\6\0\5\5"+
    "\2\0\1\53\3\5\12\0\10\5\1\0\2\5\1\222"+
    "\17\5\6\0\5\5\2\0\1\53\3\5\12\0\10\5"+
    "\1\0\3\5\1\137\16\5\6\0\5\5\2\0\1\53"+
    "\3\5\12\0\1\5\1\206\6\5\1\0\22\5\6\0"+
    "\5\5\2\0\1\53\3\5\12\0\5\5\1\223\2\5"+
    "\1\0\22\5\6\0\5\5\2\0\1\53\1\5\1\224"+
    "\1\5\12\0\10\5\1\0\22\5\35\0\1\225\73\0"+
    "\1\226\64\0\1\174\53\0\1\227\72\0\1\230\73\0"+
    "\1\231\64\0\1\200\53\0\1\232\45\0\3\233\5\0"+
    "\1\233\16\0\1\233\2\0\1\233\2\0\2\233\5\0"+
    "\1\233\1\0\1\233\4\0\1\233\7\0\3\57\3\234"+
    "\2\57\1\134\2\57\1\234\1\135\15\57\1\234\2\57"+
    "\1\234\2\57\2\234\5\57\1\234\1\57\1\234\4\57"+
    "\1\234\7\57\1\0\5\5\2\0\1\53\3\5\12\0"+
    "\3\5\1\235\4\5\1\0\22\5\6\0\5\5\2\0"+
    "\1\53\3\5\12\0\10\5\1\0\2\5\1\64\17\5"+
    "\6\0\5\5\2\0\1\53\3\5\12\0\1\5\1\236"+
    "\6\5\1\0\22\5\6\0\5\5\2\0\1\53\3\5"+
    "\12\0\6\5\1\153\1\5\1\0\22\5\6\0\5\5"+
    "\2\0\1\53\3\5\12\0\5\5\1\237\2\5\1\0"+
    "\22\5\6\0\5\5\2\0\1\53\3\5\12\0\1\5"+
    "\1\240\6\5\1\0\22\5\6\0\5\5\2\0\1\53"+
    "\3\5\12\0\1\5\1\241\6\5\1\0\22\5\6\0"+
    "\5\5\2\0\1\53\3\5\12\0\10\5\1\0\6\5"+
    "\1\64\13\5\6\0\5\5\2\0\1\53\3\5\12\0"+
    "\10\5\1\0\3\5\1\242\16\5\6\0\5\5\2\0"+
    "\1\53\3\5\12\0\1\5\1\165\6\5\1\0\22\5"+
    "\6\0\5\5\2\0\1\53\3\5\12\0\10\5\1\0"+
    "\1\243\21\5\6\0\5\5\2\0\1\53\3\5\12\0"+
    "\10\5\1\0\1\5\1\244\20\5\6\0\5\5\2\0"+
    "\1\53\3\5\12\0\10\5\1\0\5\5\1\245\14\5"+
    "\6\0\5\5\2\0\1\53\3\5\12\0\1\5\1\246"+
    "\6\5\1\0\22\5\6\0\5\5\2\0\1\53\3\5"+
    "\12\0\3\5\1\247\4\5\1\0\22\5\6\0\5\5"+
    "\2\0\1\53\3\5\12\0\7\5\1\153\1\0\22\5"+
    "\36\0\1\174\4\0\1\226\47\0\1\250\46\0\1\227"+
    "\1\251\3\227\1\251\2\0\3\227\2\0\1\251\1\0"+
    "\1\227\1\251\1\0\3\251\10\227\1\251\22\227\2\251"+
    "\1\0\1\251\32\0\1\200\4\0\1\231\47\0\1\252"+
    "\46\0\1\232\1\253\3\232\1\253\2\0\3\232\2\0"+
    "\1\253\1\0\1\232\1\253\1\0\3\253\10\232\1\253"+
    "\22\232\2\253\1\0\1\253\4\0\3\254\5\0\1\254"+
    "\16\0\1\254\2\0\1\254\2\0\2\254\5\0\1\254"+
    "\1\0\1\254\4\0\1\254\7\0\3\57\3\255\2\57"+
    "\1\134\2\57\1\255\1\135\15\57\1\255\2\57\1\255"+
    "\2\57\2\255\5\57\1\255\1\57\1\255\4\57\1\255"+
    "\7\57\1\0\5\5\2\0\1\53\3\5\12\0\4\5"+
    "\1\256\3\5\1\0\22\5\6\0\5\5\2\0\1\53"+
    "\3\5\12\0\5\5\1\257\2\5\1\0\4\5\1\260"+
    "\15\5\6\0\5\5\2\0\1\53\3\5\12\0\7\5"+
    "\1\241\1\0\22\5\6\0\5\5\2\0\1\53\3\5"+
    "\12\0\5\5\1\261\2\5\1\0\22\5\6\0\5\5"+
    "\2\0\1\53\3\5\12\0\3\5\1\64\4\5\1\0"+
    "\22\5\6\0\5\5\2\0\1\53\3\5\12\0\10\5"+
    "\1\0\4\5\1\262\15\5\6\0\5\5\2\0\1\53"+
    "\3\5\12\0\1\5\1\243\6\5\1\0\22\5\6\0"+
    "\5\5\2\0\1\53\1\5\1\64\1\5\12\0\10\5"+
    "\1\0\22\5\6\0\5\5\2\0\1\53\3\5\12\0"+
    "\5\5\1\263\2\5\1\0\22\5\6\0\5\5\2\0"+
    "\1\53\3\5\12\0\6\5\1\264\1\5\1\0\22\5"+
    "\25\0\1\227\65\0\1\232\50\0\3\5\5\0\1\5"+
    "\16\0\1\5\2\0\1\5\2\0\2\5\5\0\1\5"+
    "\1\0\1\5\4\0\1\5\7\0\3\57\3\12\2\57"+
    "\1\134\2\57\1\12\1\135\15\57\1\12\2\57\1\12"+
    "\2\57\2\12\5\57\1\12\1\57\1\12\4\57\1\12"+
    "\7\57\1\0\5\5\2\0\1\53\3\5\12\0\10\5"+
    "\1\0\4\5\1\265\15\5\6\0\5\5\2\0\1\53"+
    "\3\5\12\0\10\5\1\0\4\5\1\266\15\5\6\0"+
    "\5\5\2\0\1\53\3\5\12\0\10\5\1\0\5\5"+
    "\1\144\14\5\6\0\5\5\2\0\1\53\3\5\12\0"+
    "\10\5\1\0\4\5\1\267\15\5\6\0\5\5\2\0"+
    "\1\53\3\5\12\0\10\5\1\0\5\5\1\270\14\5"+
    "\6\0\5\5\2\0\1\53\3\5\12\0\10\5\1\0"+
    "\4\5\1\271\15\5\6\0\5\5\2\0\1\53\3\5"+
    "\12\0\5\5\1\64\2\5\1\0\22\5\6\0\5\5"+
    "\2\0\1\53\3\5\12\0\10\5\1\0\5\5\1\272"+
    "\14\5\6\0\5\5\2\0\1\53\1\5\1\144\1\5"+
    "\12\0\10\5\1\0\22\5\6\0\5\5\2\0\1\53"+
    "\1\5\1\241\1\5\12\0\10\5\1\0\22\5\6\0"+
    "\5\5\2\0\1\53\3\5\12\0\10\5\1\0\6\5"+
    "\1\144\13\5\6\0\5\5\2\0\1\53\1\5\1\273"+
    "\1\5\12\0\10\5\1\0\22\5\6\0\5\5\2\0"+
    "\1\53\3\5\12\0\10\5\1\0\7\5\1\144\12\5"+
    "\6\0\5\5\2\0\1\53\3\5\12\0\10\5\1\0"+
    "\2\5\1\274\17\5\6\0\5\5\2\0\1\53\3\5"+
    "\12\0\6\5\1\275\1\5\1\0\22\5\6\0\5\5"+
    "\2\0\1\53\3\5\12\0\5\5\1\276\2\5\1\0"+
    "\22\5\6\0\5\5\2\0\1\53\3\5\12\0\10\5"+
    "\1\0\21\5\1\137\5\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[9396];
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
    "\3\0\1\11\2\1\1\11\5\1\2\11\22\1\1\11"+
    "\5\1\1\11\3\1\1\0\5\1\3\11\36\1\1\11"+
    "\11\0\1\1\1\11\35\1\11\0\21\1\2\0\1\1"+
    "\2\0\1\1\1\0\14\1\5\0\22\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[190];
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
	public FqlTokenMaker() {
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
  public FqlTokenMaker(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  public FqlTokenMaker(java.io.InputStream in) {
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
    while (i < 158) {
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
        case 17: 
          { addToken(Token.ERROR_STRING_DOUBLE);
          }
        case 24: break;
        case 18: 
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
        case 16: 
          { yybegin(YYINITIAL); addToken(start,zzStartRead+2-1, Token.COMMENT_MULTILINE);
          }
        case 31: break;
        case 12: 
          { addToken(Token.LITERAL_STRING_DOUBLE_QUOTE);
          }
        case 32: break;
        case 20: 
          { int temp=zzStartRead; addToken(start,zzStartRead-1, Token.COMMENT_EOL); addHyperlinkToken(temp,zzMarkedPos-1, Token.COMMENT_EOL); start = zzMarkedPos;
          }
        case 33: break;
        case 19: 
          { int temp=zzStartRead; addToken(start,zzStartRead-1, Token.COMMENT_MULTILINE); addHyperlinkToken(temp,zzMarkedPos-1, Token.COMMENT_MULTILINE); start = zzMarkedPos;
          }
        case 34: break;
        case 15: 
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
            case 191: break;
            case YYINITIAL: {
              addNullToken(); return firstToken;
            }
            case 192: break;
            case MLC: {
              addToken(start,zzStartRead-1, Token.COMMENT_MULTILINE); return firstToken;
            }
            case 193: break;
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
